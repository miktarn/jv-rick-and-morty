package mate.academy.rickandmorty.dto.internal;

public record InternalCharacterDto(
        Long id,
        String externalId,
        String name,
        String status,
        String gender
) {
}
