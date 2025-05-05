package movies.be.controller;

import lombok.RequiredArgsConstructor;
import movies.be.dto.CategoryDto;
import movies.be.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        logger.info("Received request to fetch all categories");
        List<CategoryDto> categories = categoryService.getAllCategories();
        logger.debug("Successfully retrieved {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        logger.info("Received request to fetch category with ID: {}", id);
        CategoryDto category = categoryService.getCategoryById(id);
        logger.debug("Successfully retrieved category with ID: {}", id);
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        logger.info("Received request to create category: {}", categoryDto.getName());
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        logger.debug("Successfully created category with ID: {}", createdCategory.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        logger.info("Received request to update category with ID: {}", id);
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        logger.debug("Successfully updated category with ID: {}", id);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Received request to delete category with ID: {}", id);
        categoryService.deleteCategory(id);
        logger.debug("Successfully deleted category with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}