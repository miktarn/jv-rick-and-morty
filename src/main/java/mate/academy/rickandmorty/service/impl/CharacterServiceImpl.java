package mate.academy.rickandmorty.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;

    @Override
    public Character getRandomCharacter() {
        long charactersAmount = characterRepository.count();
        if (charactersAmount == 0) {
            throw new RuntimeException("Not enough characters to process");
        }
        long randomId = new Random().nextLong(1, charactersAmount);
        return characterRepository.findById(randomId).orElseThrow(
                () -> new EntityNotFoundException("Cant find character with id %s"
                        .formatted(randomId))
        );
    }

    @Override
    public List<Character> getAllWhereNameContains(String name) {
        if (name == null || name.isEmpty()) {
            return characterRepository.findAll();
        }
        return characterRepository.findByNameContaining(name);
    }
}
