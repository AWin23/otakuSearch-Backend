package com.example.otakuSearch_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.core.ParameterizedTypeReference;
import java.util.Map;

/**
 * Service to handle execution of GraphQL queries to the AniList API.
 * This service abstracts the HTTP communication layer, making it reusable across different services.
 */
@Service
public class AniListGraphQLService {
    private final WebClient webClient;

    /**
     * Constructor to initialize WebClient.
     * 
     * @param webClientBuilder WebClient.Builder instance for configuring HTTP requests.
     */
    public AniListGraphQLService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://graphql.anilist.co")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    /**
     * Executes a GraphQL query against the AniList API.
     * 
     * @param query     The GraphQL query string.
     * @param variables A map containing GraphQL query variables.
     * @return A Mono containing the response data as a map.
     */
    public Mono<Map<String, Object>> executeQuery(String query, Map<String, Object> variables) {
        return webClient.post()
                .bodyValue(Map.of("query", query, "variables", variables))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}
