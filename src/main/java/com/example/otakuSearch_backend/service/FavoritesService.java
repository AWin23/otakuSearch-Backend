package com.example.otakuSearch_backend.service;

import java.util.List;
import com.example.otakuSearch_backend.models.Favorites;
import com.example.otakuSearch_backend.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritesService {

    @Autowired
    private FavoriteRepository favoritesRepository;
    /**
     * 💾 Saves a favorite anime to the database.
     *
     * @param favorite The Favorites entity containing animeId, title, cover image, and user association
     * @return The saved Favorites entity, including the generated favoriteId
     */
    public Favorites addFavorite(Favorites favorite) {

        Long userId = favorite.getUser().getUserId();
        Long animeId = favorite.getAnimeId();

        // ✅ Check if favorite already exists for this user and animeId
        boolean exists = favoritesRepository.existsByUser_UserIdAndAnimeId(userId, animeId);

        if (exists) {
            System.out.println("⚠️ Duplicate favorite detected → animeId: " + animeId + ", userId: " + userId);
            throw new IllegalStateException("This anime is already in the user's favorites list.");
        }

        Favorites saved = favoritesRepository.save(favorite); // 🧠 JPA handles insert logic here
        return saved;
    }

    /**
     * 📦 Retrieves all favorite anime entries for a specific user.
     *
     * @param userId The ID of the user whose favorites should be retrieved
     * @return A list of Favorites objects associated with the user
     */
    public List<Favorites> getFavoritesByUserId(Long userId) {
        return favoritesRepository.findByUser_UserId(userId); // 👈 Uses custom query method
    }


    /**
     * Deletes a favorite anime from the database by its ID.
     *
     * @param id The ID of the favorite anime to be deleted
     */
    public void deleteFavoriteById(Long favoriteId) {
        favoritesRepository.deleteById(favoriteId);
    }
}
