package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.service.AniListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController // Marks this class as a REST controller, handling HTTP requests
@RequestMapping("/anime") // Base URL for all endpoints in this controller
public class AnimeController {
    private final AniListService aniListService;

    // Constructor-based dependency injection for AniListService
    @Autowired
    public AnimeController(AniListService aniListService) {
        this.aniListService = aniListService;
    }

    /**
     * Endpoint to fetch trending anime from AniList API.  * 
     * @return A Mono containing a Map with trending anime data.
     */
    @GetMapping("/trending")
    public Mono<Map<String, Object>> getTrendingAnime() {
        return aniListService.getTrendingAnime();
    }

    /**
     * Endpoint to fetch popular anime for the current season.
     * 
     * @return A Mono containing a Map with popular anime data for the given season and year.
     */
    @GetMapping("/popular")
    public Mono<Map<String, Object>> getPopularThisSeason() {
        return aniListService.getPopularThisSeason();
    }

    /**
     * Endpoint to fetch upcoming anime for the next season.
     * @return A Mono containing a Map with popular anime data for the given season and year.
     */
    @GetMapping("/upcoming")
    public Mono<Map<String, Object>> getUpcomingNextSeason() {
        return aniListService.getUpcomingNextSeason();
    }
    
    /**
     * Endpoint to fetch all-time popular anime.
     * @return A Mono containing a Map with a list of all-time popular anime.
     */
    @GetMapping("/all-time-popular")
    public Mono<Map<String, Object>> getAllTimePopular() {
        return aniListService.getAllTimePopular();
    }

    /**
     * Endpoint to fetch the top 100 anime of all time.
     * @return A Mono containing a Map with the top 100 anime.
     */
    @GetMapping("/top100")
    public Mono<Map<String, Object>> getTop100Anime() {
        return aniListService.getTop100Anime();
    }


    /**
     * Endpoint to fetch specific anime from AniList API based on ID.
     * @param id The ID of the anime.
     * @return A Mono containing a Map with detailed anime data.
     */
    @GetMapping("/{id}")
    public Mono<Map<String, Object>> getSpecificAnime(@PathVariable int id) {
        return aniListService.getSpecificAnime(id);
    }
}


