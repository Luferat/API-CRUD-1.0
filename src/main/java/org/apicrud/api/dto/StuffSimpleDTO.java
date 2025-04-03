package org.apicrud.api.dto;

import org.apicrud.api.model.Stuff;

import java.util.List;

public record StuffSimpleDTO(
        Integer id,
        String name,
        String photo,
        PersonaSimpleDTO persona,
        List<CategoryDTO> categories
) {
    public static StuffSimpleDTO fromEntity(Stuff stuff) {
        return new StuffSimpleDTO(
                stuff.getId(),
                stuff.getName(),
                stuff.getPhoto(),
                PersonaSimpleDTO.fromEntity(stuff.getPersona()),
                stuff.getCategories().stream()
                        .map(CategoryDTO::fromEntity)
                        .toList() // Converte Set<Category> para List<CategoryDTO>
        );
    }
}
