package org.apicrud.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apicrud.api.dto.StuffDTO;
import org.apicrud.api.dto.StuffListDTO;
import org.apicrud.api.dto.StuffUpdateDTO;
import org.apicrud.api.model.Category;
import org.apicrud.api.model.Status;
import org.apicrud.api.repository.CategoryRepository;
import org.apicrud.api.repository.StuffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.apicrud.api.model.Stuff;
import org.apicrud.api.dto.StuffCreateDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/stuff")
public class StuffController {

    private final StuffRepository repository;
    private final CategoryRepository categoryRepository;

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
    public ResponseEntity<Void> softDelete(@PathVariable Integer id) {
        int updated = repository.softDelete(id);

        return updated > 0 ? ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/edit")
    @Transactional
    public ResponseEntity<Stuff> editStuff(@PathVariable Integer id, @RequestBody @Valid StuffUpdateDTO dto) {
        Optional<Stuff> optionalStuff = repository.findByIdWithCategories(id);

        if (optionalStuff.isEmpty() || optionalStuff.get().getStatus() != Status.ON) {
            return ResponseEntity.notFound().build();
        }

        Stuff stuff = optionalStuff.get();

        // Atualiza campos básicos
        if (dto.name() != null) stuff.setName(dto.name());
        if (dto.description() != null) stuff.setDescription(dto.description());
        if (dto.location() != null) stuff.setLocation(dto.location());
        if (dto.photo() != null) stuff.setPhoto(dto.photo());

        // Atualiza categorias (agora usando dto.categories())
        if (dto.categories() != null) {
            stuff.getCategories().clear();
            repository.flush();

            Set<Category> newCategories = new HashSet<>(
                    categoryRepository.findAllById(dto.categories())
            );

            stuff.getCategories().addAll(newCategories);
        }

        return ResponseEntity.ok(repository.save(stuff));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Stuff> createStuff(@RequestBody @Valid StuffCreateDTO dto) {
        Stuff stuff = new Stuff();

        // Define os atributos do Stuff
        stuff.setName(dto.name());
        stuff.setDescription(dto.description());
        stuff.setLocation(dto.location());
        stuff.setPhoto(dto.photo());
        stuff.setStatus(Status.ON);

        // Associa as categorias (agora usando dto.categories())
        if (dto.categories() != null && !dto.categories().isEmpty()) {
            Set<Category> categories = new HashSet<>(
                    categoryRepository.findAllById(dto.categories())
            );
            stuff.setCategories(categories);
        }

        repository.save(stuff);
        return ResponseEntity.status(HttpStatus.CREATED).body(stuff);
    }

}