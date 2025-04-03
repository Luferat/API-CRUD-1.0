package org.apicrud.api.controller;

import lombok.AllArgsConstructor;
import org.apicrud.api.dto.CategoryCreateDTO;
import org.apicrud.api.dto.CategoryDTO;
import org.apicrud.api.dto.CategoryUpdateDTO;
import org.apicrud.api.dto.StuffDetailDTO;
import org.apicrud.api.model.Category;
import org.apicrud.api.model.Status;
import org.apicrud.api.model.Stuff;
import org.apicrud.api.repository.CategoryRepository;
import org.apicrud.api.repository.StuffRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Integer id) {
        Optional<Category> categoryOpt = repository.findByIdAndStatus(id, Status.ON);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setStatus(Status.OFF);
            repository.save(category);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "response", HttpStatus.OK.value()
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", "error",
                "response", HttpStatus.NOT_FOUND.value(),
                "message", "Category not found or already deleted"
        ));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Map<String, Object>> editCategory(@PathVariable Integer id, @RequestBody CategoryUpdateDTO dto) {
        Optional<Category> categoryOpt = repository.findByIdAndStatus(id, Status.ON);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setName(dto.name());
            category.setDescription(dto.description());
            repository.save(category);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "response", HttpStatus.OK.value()
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", "error",
                "response", HttpStatus.NOT_FOUND.value(),
                "message", "Category not found or inactive"
        ));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody CategoryCreateDTO dto) {
        try {
            Category category = new Category();
            category.setName(dto.name());
            category.setDescription(dto.description());
            category.setStatus(Status.ON);

            Category savedCategory = repository.save(category);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "id", savedCategory.getId(),
                    "response", HttpStatus.CREATED.value()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "response", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "Failed to create category"
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Integer id) {
        Optional<CategoryDTO> categoryDTO = repository.findByIdAndStatus(id, Status.ON).map(CategoryDTO::fromEntity);

        return categoryDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/stuff")
    public ResponseEntity<List<StuffDetailDTO>> getStuffByCategory(@PathVariable Integer id) {
        if (repository.findByIdAndStatus(id, Status.ON).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<StuffDetailDTO> stuffList = stuffRepository.findByCategoriesIdAndStatusOrderByNameAsc(id, Status.ON)
                .stream()
                .map(StuffDetailDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(stuffList);
    }

}