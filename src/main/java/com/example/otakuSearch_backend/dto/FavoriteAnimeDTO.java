package com.example.otakuSearch_backend.dto;

/**
 * DTO (Data Transfer Object) representing a user's favorite anime.
 * This class is used to send lightweight, clean data from the backend to the frontend
 * without exposing full entity relationships like Users or recursive mappings.
 */
public class FavoriteAnimeDTO {

    private Long favoriteId; // Unique ID of the User who likes the Anime
    private Long animeId;         // Unique ID of the anime
    private String title;         // Anime title (romaji or English)
    private String coverImageUrl; // URL for the cover image (used in mobile app)

    /**
     * Constructor used when mapping Favorites entity to DTO.
     *
     * @param animeId        ID of the anime
     * @param title          Anime title (romaji or English)
     * @param coverImageUrl  URL of the anime's cover image
     */
    public FavoriteAnimeDTO(Long favoriteId, Long animeId, String title, String coverImageUrl) {
        this.favoriteId = favoriteId;
        this.animeId = animeId;
        this.title = title;
        this.coverImageUrl = coverImageUrl;
    }

    // Getters for serialization (required for Jackson to convert to JSON)

    public Long getFavoriteId() { return favoriteId; }

    public Long getAnimeId() {
        return animeId;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }
}
