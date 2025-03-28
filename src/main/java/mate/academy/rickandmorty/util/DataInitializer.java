package mate.academy.rickandmorty.util;

import java.util.List;
import mate.academy.rickandmorty.dto.external.ExternalCharacterDto;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.RickAndMortyClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final RickAndMortyClient client;
    private final CharacterRepository characterRepository;

    public DataInitializer(RickAndMortyClient client, CharacterRepository characterRepository) {
        this.client = client;
        this.characterRepository = characterRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDataOnApplicationStartup() {
        List<ExternalCharacterDto> fetched = client.getAllCharacters();

        List<Character> characters = fetched.stream()
                .map(this::toModel)
                .toList();

        characterRepository.saveAll(characters);
    }

    private Character toModel(ExternalCharacterDto character) {
        String externalId = character.id();
        return new Character(
                null,
                externalId,
                character.name(),
                character.status(),
                character.gender()
        );
    }
}
