package org.apicrud.api.dto;

import org.apicrud.api.model.Stuff;
import java.util.Set;

public record StuffUpdateDTO(
        String name,
        String description,
        String location,
        String photo,
        // String field1,
        // String field2,
        Set<Integer> categories
) {
    public Stuff updateEntity(Stuff stuff) {
        if (name != null) stuff.setName(name);
        if (description != null) stuff.setDescription(description);
        if (location != null) stuff.setLocation(location);
        if (photo != null) stuff.setPhoto(photo);
        // if (field1 != null) stuff.setField1(field1);
        // if (field2 != null) stuff.setField2(field2);
        return stuff;
    }
}