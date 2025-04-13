package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.service.UserService;
import com.example.otakuSearch_backend.dto.FavoriteAnimeDTO;
import com.example.otakuSearch_backend.models.Favorites;
import com.example.otakuSearch_backend.models.Users;
import com.example.otakuSearch_backend.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling requests related to user's favorite anime.
 * Maps endpoints for adding and retrieving favorite anime entries.
 */
@RestController
@RequestMapping("/users/{userId}/favorites")
public class FavoritesController {

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private UserService userService;

    /**
     * GET endpoint to fetch all favorite anime for a specific user.
     *
     * @param userId The ID of the user whose favorites are being requested.
     * @return A list of FavoriteAnimeDTO containing lightweight anime info.
     */
    @GetMapping
    public List<FavoriteAnimeDTO> getFavoritesForUser(@PathVariable Long userId) {
        // Retrieve all Favorites entities for this user
        List<Favorites> favorites = favoritesService.getFavoritesByUserId(userId);

        // Convert each Favorites entity into a lightweight DTO
        return favorites.stream()
            .map(fav -> new FavoriteAnimeDTO(
                fav.getAnimeId(),
                fav.getTitle(),
                fav.getCoverImageUrl()
            ))
            .collect(Collectors.toList());
    }

    /**
     * POST endpoint to add an anime to a user's favorites list.
     *
     * @param userId   The ID of the user adding a favorite.
     * @param favorite The incoming favorite object from the client (JSON body).
     * @return The saved Favorites entity.
     */
    @PostMapping
    public Favorites addToFavorites(@PathVariable Long userId, @RequestBody Favorites favorite) {
        // 🧾 Log payload for debugging
        System.out.println("📥 Received request to add favorite for userId: " + userId);
        System.out.println("📦 Payload → animeId: " + favorite.getAnimeId() + ", title: " + favorite.getTitle());

        // Retrieve user entity and associate it with the favorite
        Users user = userService.getUserById(userId);
        favorite.setUser(user);

        // Save the favorite entry in the database
        return favoritesService.addFavorite(favorite);
    }
}