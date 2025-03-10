package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.service.FilterService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

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
     * @param genre  The anime genre to filter by.
     * @param season The anime season to filter by.
     * @param year   The anime release year.
     * @param status The status of the anime.
     * @return A list of filtered anime results.
     */
    @GetMapping("/filter")
    public Mono<Map<String, Object>> filterAnime(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String season,
            @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(required = false) String status) {
        return filterService.filterAnime(genre, season, year, status);
    }
}
