package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.service.UserService;
import com.example.otakuSearch_backend.dto.FavoriteAnimeDTO;
import com.example.otakuSearch_backend.models.Favorites;
import com.example.otakuSearch_backend.models.Users;
import com.example.otakuSearch_backend.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
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
                fav.getFavoriteId(),
                fav.getAnimeId(),
                fav.getTitle(),
                fav.getCoverImageUrl()
            ))
            .collect(Collectors.toList());
    }

    /**
     * ❌ DELETE /users/{userId}/favorites/{favoriteId}
     * 
     * Handles a DELETE request to remove a specific favorite anime for a user.
     * 
     * @param favoriteId The unique ID of the favorite anime to be deleted
     * @return HTTP 200 OK response upon successful deletion
     */
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Long favoriteId) {
        // 🧼 Delegate deletion logic to the service layer
        favoritesService.deleteFavoriteById(favoriteId);

        System.out.println("✅ Favorite anime with ID " + favoriteId + " deleted successfully.");


        // ✅ Return 200 OK with no body (indicates successful deletion)
        return ResponseEntity.ok().build();
    }


    /**
     * POST endpoint to add an anime to a user's favorites list.
     *
     * @param userId   The ID of the user adding a favorite.
     * @param favorite The incoming favorite object from the client (JSON body).
     * @return The saved Favorites entity.
     */
    @PostMapping
    public ResponseEntity<?> addToFavorites(@PathVariable Long userId, @RequestBody Favorites favorite) {
        try {
            Users user = userService.getUserById(userId);
            favorite.setUser(user);
            Favorites saved = favoritesService.addFavorite(favorite);

            FavoriteAnimeDTO responseDto = new FavoriteAnimeDTO(
                saved.getFavoriteId(),
                saved.getAnimeId(),
                saved.getTitle(),
                saved.getCoverImageUrl()
            );

            return ResponseEntity.ok(responseDto);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        }
    }


}