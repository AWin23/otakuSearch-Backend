package com.example.otakuSearch_backend.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchService {

    private final AniListGraphQLService graphQLService;
    private final FilterService filterService;

    public SearchService(AniListGraphQLService graphQLService, FilterService filterService) {
        this.graphQLService = graphQLService;
        this.filterService = filterService;
    }

    /**
     * Searches for anime by title with optional filters.
     *
     * @param title  The title of the anime to search for.
     * @param genre  (Optional) The anime genre to filter by (e.g., "Action").
     * @param season (Optional) The season of the anime (e.g., "SUMMER").
     * @param year   (Optional) The release year of the anime.
     * @param status (Optional) The current status of the anime (e.g., "FINISHED").
     * @return A Mono containing the search results from AniList's GraphQL API.
     */
    public Mono<Map<String, Object>> searchAnime(String title, String genre, String season, int year, String status) {
    // Build filter parameters
    Map<String, Object> filters = new HashMap<>();
    filters.put("search", title);  // Add title as search term

    // Add other optional filters if provided
    if (genre != null && !genre.isEmpty()) filters.put("genre", genre);
    if (season != null && !season.isEmpty()) filters.put("season", season);
    if (year > 0) filters.put("year", year);
    if (status != null && !status.isEmpty()) filters.put("status", status);

    // GraphQL query with parameters as placeholders
    String query = """
      query ($search: String, $genre: String, $season: MediaSeason, $year: Int, $status: MediaStatus) {
        Page(page: 1, perPage: 10) {
          media(search: $search, genre: $genre, season: $season, seasonYear: $year, status: $status, type: ANIME) {
            id
            title {
              romaji
              english
            }
            episodes
            status
            genres
            coverImage {
              medium
              large
            }
          }
        }
      }
    """;

    // Log the query for debugging
    System.out.println("Searching for anime: " + title + ", Filters: " + filters);

    // Execute GraphQL request and return results
    return graphQLService.executeQuery(query, filters);
}

    
}
