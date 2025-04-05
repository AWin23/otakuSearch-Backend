package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.service.FilterService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/anime")
public class FilterController {

    private final FilterService filterService;

    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    /**
     * Endpoint to filter anime based on various criteria.
     *
     * @param genrse  The anime genre to filter by.
     * @param season The anime season to filter by.
     * @param year   The anime release year.
     * @param formats The anime's format type
     * @param status The status of the anime.
     * @return A list of filtered anime results.
     */
    @GetMapping("/filter")
    public Mono<Map<String, Object>> filterAnime(
        @RequestParam(name = "genres", required = false) List<String> genres,
        @RequestParam(required = false) String season,
        @RequestParam(required = false, defaultValue = "0") int year,
        @RequestParam(name = "formats", required = false) List <String> formats,
        @RequestParam(required = false) String status) {

    // Convert the List<String> into a comma-separated string.
    return filterService.filterAnime(genres, season, year, formats, status);
}

    
}
