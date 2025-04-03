package org.apicrud.api.dto;

import org.apicrud.api.model.Stuff;

public record StuffDTO(Integer id, String name) {

    public static StuffDTO fromEntity(Stuff stuff) {
        return new StuffDTO(stuff.getId(), stuff.getName());
    }
}