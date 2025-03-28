package mate.academy.rickandmorty.dto;

import mate.academy.rickandmorty.dto.external.ExternalCharacterDto;
import mate.academy.rickandmorty.dto.internal.InternalCharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {
    public Character toModel(ExternalCharacterDto character) {
        String externalId = character.id();
        return new Character(
                null,
                externalId,
                character.name(),
                character.status(),
                character.gender()
        );
    }

    public InternalCharacterDto toDto(Character character) {
        return new InternalCharacterDto(
                character.getId(),
                character.getExternalId(),
                character.getName(),
                character.getStatus(),
                character.getGender()
        );
    }
}
