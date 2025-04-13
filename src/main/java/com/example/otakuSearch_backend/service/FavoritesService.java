package com.example.otakuSearch_backend.service;

import com.example.otakuSearch_backend.models.Favorites;
import com.example.otakuSearch_backend.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritesService {

    @Autowired
    private FavoriteRepository favoritesRepository;

    public Favorites addFavorite(Favorites favorite) {
        Favorites saved = favoritesRepository.save(favorite);
        return saved;
    }
    
}
