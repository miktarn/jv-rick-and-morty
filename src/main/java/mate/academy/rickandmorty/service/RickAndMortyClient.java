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
import java.util.List;
import mate.academy.rickandmorty.dto.external.ExternalCharacterDto;
import org.springframework.stereotype.Component;

@Component
public class RickAndMortyClient {

    private static final String CHARACTER = "/character";
    private static final String BASE_URL = "https://rickandmortyapi.com/api";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String RESULTS = "results";

    HttpClient httpClient = HttpClient.newHttpClient();

    public List<ExternalCharacterDto> getAllCharacters() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(BASE_URL + CHARACTER))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());
            String resultsJson = root.get(RESULTS).toString();
            return mapper.readValue(resultsJson, new TypeReference<>() {});
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
