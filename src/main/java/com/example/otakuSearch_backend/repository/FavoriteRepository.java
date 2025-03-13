package com.example.otakuSearch_backend.repository;

import com.example.otakuSearch_backend.models.Favorites;
import com.example.otakuSearch_backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {
    List<Favorites> findByUser(Users user); // Fetch all favorites for a given user
}
