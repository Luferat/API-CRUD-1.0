package org.apicrud.api.dto;

import org.apicrud.api.model.Category;

public record CategoryDTO(
        Integer id,
        String name,
        String description
        // String field1,
        // String status
) {
    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
                // category.getField1(),
                // category.getStatus().name()
        );
    }
}