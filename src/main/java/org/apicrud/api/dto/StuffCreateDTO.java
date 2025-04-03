package org.apicrud.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record StuffCreateDTO(
        @NotBlank String name,
        String description,
        String location,
        String photo,
        Set<Integer> categories
) {
}