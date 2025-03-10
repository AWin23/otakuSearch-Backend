package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.service.SearchService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/anime")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Search for anime by title.
     * @param title  The title of the anime to search for.
     * @param genre  The anime genre to filter by (e.g., "Action").
     * @param season The season of the anime (e.g., "SUMMER").
     * @param year   The release year of the anime.
     * @param status The current status of the anime (e.g., "FINISHED").
     * @return A Mono containing search results.
     */
    @GetMapping("/search")
    public Mono<Map<String, Object>> searchAnime(
        @RequestParam String title,  // Mandatory title is required
        @RequestParam(required = false) String genre,
        @RequestParam(required = false) String season,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) String status) {
    // Use a ternary operator to handle potential null for year
    return searchService.searchAnime(title, genre, season, year != null ? year : 0, status);
    }
}
