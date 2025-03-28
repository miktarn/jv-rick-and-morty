package mate.academy.rickandmorty.util;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterMapper;
import mate.academy.rickandmorty.dto.external.ExternalCharacterDto;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.RickAndMortyClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RickAndMortyClient client;
    private final CharacterRepository characterRepository;
    private final CharacterMapper mapper;

    @EventListener(ApplicationReadyEvent.class)
    public void initDataOnApplicationStartup() {
        List<ExternalCharacterDto> fetched = client.getAllCharacters();

        List<Character> characters = fetched.stream()
                .map(mapper::toModel)
                .toList();

        characterRepository.saveAll(characters);
    }
}
