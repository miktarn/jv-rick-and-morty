package mate.academy.rickandmorty.dto.internal;

public record InternalCharacterDto(
        String id,
        String externalId,
        String name,
        String status,
        String gender
) {
}
