package com.example.otakuSearch_backend.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class FilterService {

    private final AniListGraphQLService graphQLService;

    /**
     * Constructor that injects the AniListGraphQLService.
     *
     * @param graphQLService Service responsible for executing GraphQL queries.
     */
    public FilterService(AniListGraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    /**
     * Builds a map of filter parameters for GraphQL queries.
     *
     * @param genre  The anime genre to filter by (e.g., "Action").
     * @param season The anime season to filter by (e.g., "SUMMER").
     * @param year   The anime release year (default 0 means ignored).
     * @param status The status of the anime (e.g., "FINISHED", "ONGOING").
     * @return A map containing the filters to be used in the GraphQL query.
     */
    public Map<String, Object> buildFilterParams(String genre, String season, int year, String status) {
        Map<String, Object> filters = new HashMap<>();

        // Add filters to the map only if they are provided
        if (genre != null && !genre.isEmpty()) {
            filters.put("genre", genre);
        }
        if (season != null && !season.isEmpty()) {
            filters.put("season", season);
        }
        if (year > 0) {  // Only add the year if it's greater than 0 (valid year)
            filters.put("year", year);
        }
        if (status != null && !status.isEmpty()) {
            filters.put("status", status);
        }

        return filters;
    }

    /**
     * Filters anime based on the provided criteria.
     *
     * @param genre  The anime genre to filter by.
     * @param season The anime season to filter by.
     * @param year   The anime release year.
     * @param status The status of the anime.
     * @return A Mono containing a Map with the filtered anime results.
     */
        public Mono<Map<String, Object>> filterAnime(String genre, String season, int year, String status) {
        // Build the filters map; missing values will simply not be added
        Map<String, Object> filters = buildFilterParams(genre, season, year, status);

        // GraphQL query to retrieve anime based on filters
        String query = """
            query ($genre: String, $season: MediaSeason, $year: Int, $status: MediaStatus) {
              Page(page: 1, perPage: 10) {
                media(genre: $genre, season: $season, seasonYear: $year, status: $status, sort: SCORE_DESC, type: ANIME) {
                  id
                  title {
                    romaji
                    english
                  }
                  episodes
                  status
                  genres
                  averageScore
                }
              }
            }
        """;

        // Log the filters for debugging purposes
        System.out.println("Filtering anime with parameters: " + filters);

        // Execute the query with the filters and return the results
        return graphQLService.executeQuery(query, filters);
    }
}
