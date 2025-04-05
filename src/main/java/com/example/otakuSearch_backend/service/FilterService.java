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
     * All parameters are optional; if a parameter is not provided or is empty,
     * it will not be added to the filters map. This allows filtering by any combination
     * of parameters (e.g., filtering by year only, by types only, or a combination).
     *
     * @param genres  Optional list of anime genres (e.g., ["Action", "Adventure"]).
     * @param season  Optional anime season (e.g., "SUMMER").
     * @param year    Optional anime release year (use 0 to ignore).
     * @param formats    Optional list of anime formats (e.g., ["TV", "OVA", "Music", "TV_SHORT", "SPECIAL"]).
     * @param status  Optional anime status (e.g., "FINISHED", "ONGOING").
     * @return A map containing the filters to be used in the GraphQL query.
     */
        public Map<String, Object> buildFilterParams(List<String> genres, String season, int year, List<String> formats, String status) {
            Map<String, Object> filters = new HashMap<>();

            // Add genres if provided; join into a comma-separated string
            if (genres != null && !genres.isEmpty()) {
                filters.put("genres", genres); // already a List<String>
            }

            // Add season if provided
            if (season != null && !season.isEmpty()) {
                filters.put("season", season);
            }

            // Add year only if it's greater than 0
            if (year > 0) {
                filters.put("year", year);
            }

            // Add format if provided; join into a comma-separated string
            if (formats != null && !formats.isEmpty()) {
                filters.put("formats", formats); // List<String> or List<MediaFormat>
            }

            // Add status if provided
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
     * @param format The anime's format
     * @param status The status of the anime.
     * @return A Mono containing a Map with the filtered anime results.
     */
        public Mono<Map<String, Object>> filterAnime(List<String> genres, String season, int year, List<String> formats, String status) {

        // Build the filters map; missing values will simply not be added
        Map<String, Object> filters = buildFilterParams(genres, season, year, formats, status);

        // GraphQL query to retrieve anime based on filters
        String query = """
            query (
                $genres: [String], 
                $season: MediaSeason, 
                $year: Int, 
                $status: MediaStatus, 
                $formats: [MediaFormat]
            ) {
                Page(page: 1, perPage: 10) {
                    media(
                        genre_in: $genres, 
                        season: $season, 
                        seasonYear: $year, 
                        status: $status, 
                        sort: SCORE_DESC, 
                        format_in: $formats,
                        type: ANIME
                    ) {
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
