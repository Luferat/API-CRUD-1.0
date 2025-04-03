package org.apicrud.api.dto;

import org.apicrud.api.model.Stuff;
import java.time.LocalDateTime;
import java.util.List;

public record StuffDetailDTO(
        Integer id,
        LocalDateTime date,
        String name,
        String description,
        String location,
        String photo,
        PersonaSimpleDTO persona,
        List<CategoryDTO> categories
) {
    public static StuffDetailDTO fromEntity(Stuff stuff) {
        return new StuffDetailDTO(
                stuff.getId(),
                stuff.getDate(),
                stuff.getName(),
                stuff.getDescription(),
                stuff.getLocation(),
                stuff.getPhoto(),
                PersonaSimpleDTO.fromEntity(stuff.getPersona()),
                stuff.getCategories().stream()
                        .map(CategoryDTO::fromEntity)
                        .toList()
        );
    }
}
