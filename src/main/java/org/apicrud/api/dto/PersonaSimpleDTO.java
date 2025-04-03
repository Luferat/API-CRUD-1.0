package org.apicrud.api.dto;

import org.apicrud.api.model.Persona;

public record PersonaSimpleDTO(Integer id, String name, String photo) {
    public static PersonaSimpleDTO fromEntity(Persona persona) {
        return (persona != null) ? new PersonaSimpleDTO(persona.getId(), persona.getName(), persona.getPhoto()) : null;
    }
}
