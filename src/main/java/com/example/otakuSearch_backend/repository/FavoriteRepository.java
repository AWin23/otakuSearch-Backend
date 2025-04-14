package com.example.otakuSearch_backend.repository;

import com.example.otakuSearch_backend.models.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {

    // 🔍 Custom method to find all favorites by a specific user's ID
    // This leverages Spring Data JPA's method naming conventions to auto-generate the query
    List<Favorites> findByUser_UserId(Long userId); // Fetch all favorites for a given user

    // Checks if a specific anime is already favorited by the user
    boolean existsByUser_UserIdAndAnimeId(Long userId, Long animeId);


}


