package org.apicrud.api.dto;

import org.apicrud.api.model.Stuff;

public record StuffSimpleDTO(Integer id, String name, String photo, PersonaSimpleDTO persona) {
    public static StuffSimpleDTO fromEntity(Stuff stuff) {
        return new StuffSimpleDTO(
                stuff.getId(),
                stuff.getName(),
                stuff.getPhoto(),
                PersonaSimpleDTO.fromEntity(stuff.getPersona())
        );
    }
}
