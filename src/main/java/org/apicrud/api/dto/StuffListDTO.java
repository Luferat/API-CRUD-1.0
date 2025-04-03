package org.apicrud.api.dto;

import org.apicrud.api.model.Stuff;
import java.time.LocalDateTime;
import java.util.List;

public record StuffListDTO(
        Integer id,
        LocalDateTime date,
        String name,
        String description,
        String location,
        String photo,
        // String status,
        // String field1,
        // String field2,
        List<CategoryDTO> categories
) {
    public static StuffListDTO fromEntity(Stuff stuff) {
        return new StuffListDTO(
                stuff.getId(),
                stuff.getDate(),
                stuff.getName(),
                stuff.getDescription(),
                stuff.getLocation(),
                stuff.getPhoto(),
                // stuff.getStatus().name(),
                // stuff.getField1(),
                // stuff.getField2(),
                stuff.getCategories().stream().map(CategoryDTO::fromEntity).toList()
        );
    }
}