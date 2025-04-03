package org.apicrud.api.dto;

import org.apicrud.api.model.Stuff;

public record StuffPhotoDTO(
        Integer id,
        String name,
        String photo
) {
    public static StuffPhotoDTO fromEntity(Stuff stuff) {
        return new StuffPhotoDTO(
                stuff.getId(),
                stuff.getName(),
                stuff.getPhoto()
        );
    }
}
