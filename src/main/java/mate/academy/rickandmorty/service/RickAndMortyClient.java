package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import mate.academy.rickandmorty.dto.external.ExternalCharacterDto;
import org.springframework.stereotype.Component;

@Component
public class RickAndMortyClient {

    private static final String CHARACTER = "/character";
    private static final String BASE_URL = "https://rickandmortyapi.com/api";
    private static final String RESULTS = "results";
    private static final String INFO = "info";
    private static final String NEXT = "next";

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    /**
     * This method fetching characters from third party api in 20 characters batches
     *
     * @return list of over 800 characters
     */
    public List<ExternalCharacterDto> getAllCharacters() {
        List<ExternalCharacterDto> allCharacters = new ArrayList<>();
        String nextPageUrl = BASE_URL + CHARACTER;

        while (nextPageUrl != null) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(new URI(nextPageUrl))
                        .build();
                HttpResponse<String> response = httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

                JsonNode root = mapper.readTree(response.body());
                JsonNode results = root.get(RESULTS);
                List<ExternalCharacterDto> characters = mapper.readValue(
                        results.toString(), new TypeReference<>() {}
                );
                allCharacters.addAll(characters);

                JsonNode nextPageUrlNode = root.path(INFO).path(NEXT);
                nextPageUrl = nextPageUrlNode.isNull() ? null : nextPageUrlNode.asText();
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException("Failed to fetch characters", e);
            }
        }
        return allCharacters;
    }
}
