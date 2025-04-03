package org.apicrud.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apicrud.api.dto.*;
import org.apicrud.api.model.Category;
import org.apicrud.api.model.Persona;
import org.apicrud.api.model.Status;
import org.apicrud.api.repository.CategoryRepository;
import org.apicrud.api.repository.PersonaRepository;
import org.apicrud.api.repository.StuffRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.apicrud.api.model.Stuff;

import java.util.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/stuff")
public class StuffController {

    private final StuffRepository repository;
    private final CategoryRepository categoryRepository;
    private final PersonaRepository personaRepository;

    @GetMapping
    public List<StuffDTO> getAll() {
        return repository.findByStatusOrderByDateDesc(Status.ON)
                .stream()
                .map(StuffDTO::fromEntity)
                .toList();
    }

    @GetMapping("/list")
    public List<StuffListDTO> getAllWithCategories() {
        return repository.findAllWithCategories(Status.ON)
                .stream()
                .map(StuffListDTO::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StuffListDTO> getById(@PathVariable Integer id) {
        Optional<StuffListDTO> stuffDTO = repository.findByIdAndStatus(id, Status.ON)
                .map(StuffListDTO::fromEntity);

        return stuffDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/delete")
    @Transactional
    public ResponseEntity<Map<String, Object>> softDelete(@PathVariable Integer id) {
        int updated = repository.softDelete(id);

        if (updated > 0) {
            Map<String, Object> successResponse = Map.of(
                    "status", "success",
                    "response", HttpStatus.OK.value()
            );
            return ResponseEntity.ok(successResponse); // 200 OK
        }

        Map<String, Object> errorResponse = Map.of(
                "status", "error",
                "response", HttpStatus.NOT_FOUND.value(),
                "message", "Stuff not found or already inactive"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @PutMapping("/{id}/edit")
    @Transactional
    public ResponseEntity<Map<String, Object>> editStuff(@PathVariable Integer id, @RequestBody @Valid StuffUpdateDTO dto) {
        Optional<Stuff> optionalStuff = repository.findByIdWithCategories(id);

        if (optionalStuff.isEmpty() || optionalStuff.get().getStatus() != Status.ON) {
            Map<String, Object> errorResponse = Map.of(
                    "status", "error",
                    "response", HttpStatus.NOT_FOUND.value(),
                    "message", "Stuff not found or inactive"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        Stuff stuff = optionalStuff.get();

        // Atualiza campos básicos
        if (dto.name() != null) stuff.setName(dto.name());
        if (dto.description() != null) stuff.setDescription(dto.description());
        if (dto.location() != null) stuff.setLocation(dto.location());
        if (dto.photo() != null) stuff.setPhoto(dto.photo());

        // Atualiza categorias
        if (dto.categories() != null) {
            stuff.getCategories().clear();
            repository.flush();
            Set<Category> newCategories = new HashSet<>(categoryRepository.findAllById(dto.categories()));
            stuff.getCategories().addAll(newCategories);
        }

        repository.save(stuff);

        Map<String, Object> successResponse = Map.of(
                "status", "success",
                "response", HttpStatus.OK.value()
        );

        return ResponseEntity.ok(successResponse);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, Object>> createStuff(@RequestBody @Valid StuffCreateDTO dto) {
        Stuff stuff = new Stuff();

        // Define os atributos do Stuff
        stuff.setName(dto.name());
        stuff.setDescription(dto.description());
        stuff.setLocation(dto.location());
        stuff.setPhoto(dto.photo());
        stuff.setStatus(Status.ON);

        // Associa as categorias
        if (dto.categories() != null && !dto.categories().isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(dto.categories()));
            stuff.setCategories(categories);
        }

        // Associa um Persona fixo (até termos autenticação)
        Persona persona = personaRepository.findById(1).orElse(null);
        if (persona == null) {
            Map<String, Object> errorResponse = Map.of(
                    "status", "error",
                    "response", HttpStatus.NOT_FOUND.value(),
                    "message", "Persona ID 1 não encontrado."
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        stuff.setPersona(persona);

        repository.save(stuff);

        Map<String, Object> successResponse = Map.of(
                "status", "success",
                "response", HttpStatus.CREATED.value(),
                "id", stuff.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }


    @GetMapping("/photo")
    public ResponseEntity<List<StuffPhotoDTO>> getStuffPhotos() {
        List<StuffPhotoDTO> stuffList = repository.findByStatusOrderByNameAsc(Status.ON)
                .stream()
                .map(StuffPhotoDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(stuffList);
    }

}