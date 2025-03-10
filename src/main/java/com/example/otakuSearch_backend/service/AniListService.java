package com.example.otakuSearch_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.core.ParameterizedTypeReference;
import java.util.Map;

@Service
public class AniListService {
    private final WebClient webClient;

    // connects the imported web client to here
    public AniListService(WebClient.Builder webClientBuilder) {
      this.webClient = webClientBuilder.build();
  }

  // functiont to fetch the anime via POST request of webClient
  private Mono<Map<String, Object>> fetchAnime(String query, Map<String, Object> variables) {
      return webClient.post()
              .bodyValue(Map.of("query", query, "variables", variables))
              .retrieve()
              .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
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
                  }
                }
              }
          """;
          return fetchAnime(query, Map.of());
      }


      // fetch all popular anime this season
      public Mono<Map<String, Object>> getPopularThisSeason(String season, int year) {

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
                }
              }
            }
        """;
        return fetchAnime(query, Map.of("season", season, "year", year));
    }

    // fetch upcoming anime next season
    public Mono<Map<String, Object>> getUpcomingNextSeason(String season, int year) {

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
              }
            }
          }
      """;
      return fetchAnime(query, Map.of("season", season, "year", year));
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
            }
          }
        }
    """;
    return fetchAnime(query, Map.of());
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
            }
          }
        }
    """;
    return fetchAnime(query, Map.of());
  }

}
