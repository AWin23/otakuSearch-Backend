package com.example.otakuSearch_backend.controller;

import com.example.otakuSearch_backend.service.UserService;
import com.example.otakuSearch_backend.models.Favorites;
import com.example.otakuSearch_backend.models.Users;
import com.example.otakuSearch_backend.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/favorites")
public class FavoritesController {

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private UserService userService; 

    @PostMapping
public Favorites addToFavorites(@PathVariable Long userId, @RequestBody Favorites favorite) {
    System.out.println("📥 Received request to add favorite for userId: " + userId);
    System.out.println("📦 Payload → animeId: " + favorite.getAnimeId() + ", title: " + favorite.getTitle());

    Users user = userService.getUserById(userId);
    favorite.setUser(user);
    return favoritesService.addFavorite(favorite);
}

}

