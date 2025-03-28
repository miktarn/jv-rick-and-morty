package mate.academy.rickandmorty.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterMapper;
import mate.academy.rickandmorty.dto.internal.InternalCharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;
    private final CharacterMapper mapper;

    @GetMapping("/random")
    public InternalCharacterDto getRandomCharacter() {
        return mapper.toDto(characterService.getRandomCharacter());
    }

    @GetMapping
    public List<InternalCharacterDto> getAllWhereNameContains(@RequestParam String nameSubstring) {
        return characterService.getAllWhereNameContains(nameSubstring)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

}
