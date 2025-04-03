package org.apicrud.api.controller;

import lombok.AllArgsConstructor;
import org.apicrud.api.dto.CategoryCreateDTO;
import org.apicrud.api.dto.CategoryDTO;
import org.apicrud.api.dto.CategoryUpdateDTO;
import org.apicrud.api.model.Category;
import org.apicrud.api.model.Status;
import org.apicrud.api.repository.CategoryRepository;
import org.apicrud.api.repository.StuffRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepository repository;
    private final StuffRepository stuffRepository;

    @GetMapping
    @ResponseBody
    public List<CategoryDTO> getAllCategories() {
        return repository.findByStatusOrderByNameAsc(Status.ON).stream().map(CategoryDTO::fromEntity).toList();
    }

    @PatchMapping("/{id}/delete")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        Optional<Category> categoryOpt = repository.findByIdAndStatus(id, Status.ON);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setStatus(Status.OFF);
            repository.save(category);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<String> editCategory(@PathVariable Integer id, @RequestBody CategoryUpdateDTO dto) {
        Optional<Category> categoryOpt = repository.findByIdAndStatus(id, Status.ON);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setName(dto.name());
            category.setDescription(dto.description());
            repository.save(category);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryCreateDTO dto) {
        try {
            Category category = new Category();
            category.setName(dto.name());
            category.setDescription(dto.description());
            category.setStatus(Status.ON);

            Category savedCategory = repository.save(category);
            return ResponseEntity.ok(CategoryDTO.fromEntity(savedCategory));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Integer id) {
        Optional<CategoryDTO> categoryDTO = repository.findByIdAndStatus(id, Status.ON).map(CategoryDTO::fromEntity);

        return categoryDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}