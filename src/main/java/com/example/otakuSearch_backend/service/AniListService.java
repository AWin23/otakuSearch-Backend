package com.example.otakuSearch_backend.service;

import org.springframework.stereotype.Service;
import com.example.otakuSearch_backend.util.AnimeSeasonUtil;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class AniListService {
  private final AniListGraphQLService graphQLService;

  public AniListService(AniListGraphQLService graphQLService) {
    
      this.graphQLService = graphQLService;
    }

      // GraphQL Query to retrive trending anime
      public Mono<Map<String, Object>> getTrendingAnime() {
          String query = """
              query {
                Page(page: 1, perPage: 6) {
                  media(sort: TRENDING_DESC, type: ANIME) {
                    id
                    title {
                      romaji
                      english
                    }
                    episodes
                    status
                    coverImage {
                    medium
                    }
                  }
                }
              }
          """;
          return graphQLService.executeQuery(query, Map.of());
      }


      // fetch all popular anime this season
      public Mono<Map<String, Object>> getPopularThisSeason() {

      // Dynamically calculate the CURRRENT season and year
      String season = AnimeSeasonUtil.getCurrentSeason();
      int year = AnimeSeasonUtil.getCurrentYear();

        // GraphQL Query to retrive the popular animes this season
        String query = """
            query ($season: MediaSeason, $year: Int) {
              Page(page: 1, perPage: 6) {
                media(season: $season, seasonYear: $year, sort: POPULARITY_DESC, type: ANIME) {
                  id
                  title {
                    romaji
                    english
                  }
                  episodes
                  status
                  coverImage {
                  medium
                  }
                }
              }
            }
        """;
        System.out.println("Fetching popular anime for season: " + season + ", year: " + year);
        return graphQLService.executeQuery(query, Map.of("season", season, "year", year));
    }

    // fetch upcoming anime next season
    public Mono<Map<String, Object>> getUpcomingNextSeason() {

      // Dynamically calculate the upcoming season and year
      String season = AnimeSeasonUtil.getUpcomingSeason(AnimeSeasonUtil.getUpcomingYear());
      int year = AnimeSeasonUtil.getUpcomingYear();

      // GraphQL to query the upcoming animes for next season
      String query = """
        query ($season: MediaSeason, $year: Int) {
          Page(page: 1, perPage: 6) {
            media(season: $season, seasonYear: $year, sort: POPULARITY_DESC, type: ANIME) {
              id
              title {
                romaji
                english
              }
              episodes
              status
              coverImage {
              medium
              }
            }
          }
        }
      """;
      System.out.println("Fetching upcoming anime for season: " + season + ", year: " + year);
      return graphQLService.executeQuery(query, Map.of("season", season, "year", year));
  }


  // fetch all time popular anime list
  public Mono<Map<String, Object>> getAllTimePopular() {

    // GraphQL to query the all time popular anime
    String query = """
        query {
          Page(page: 1, perPage: 6) {
            media(sort: POPULARITY_DESC, type: ANIME) {
              id
              title {
                romaji
                english
              }
              episodes
              status
              coverImage {
              medium
              }
            }
          }
        }
    """;
    return graphQLService.executeQuery(query, Map.of());
}


// fetch the top 100 anime models. 
public Mono<Map<String, Object>> getTop100Anime() {

    // GraphQL query to get all the top 100 anime. 
    String query = """
        query {
          Page(page: 1, perPage: 6) {
            media(sort: SCORE_DESC, type: ANIME) {
              id
              title {
                romaji
                english
              }
              episodes
              status
              averageScore
              coverImage { 
              medium
              }
            }
          }
        }
    """;
    return graphQLService.executeQuery(query, Map.of());
  }

}
