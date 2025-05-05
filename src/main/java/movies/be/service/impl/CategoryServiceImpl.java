package movies.be.service.impl;

import movies.be.dto.CategoryDto;
import movies.be.exception.ErrorMessages;
import movies.be.exception.MovieException;
import movies.be.model.Category;
import movies.be.repository.CategoryRepository;
import movies.be.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setActive(category.isActive());
        return dto;
    }

    private Category convertToEntity(CategoryDto dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .active(dto.isActive())
                .build();
    }

    private void validateCategoryData(CategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().trim().isEmpty()) {
            throw new MovieException(ErrorMessages.INVALID_CATEGORY_DATA_MESSAGE);
        }
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        logger.info("Fetching all categories");
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        logger.info("Fetching category with ID: {}", id);
        return categoryRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.CATEGORY_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        logger.info("Creating new category: {}", categoryDto.getName());
        validateCategoryData(categoryDto);

        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new MovieException(String.format(ErrorMessages.CATEGORY_ALREADY_EXISTS_MESSAGE, categoryDto.getName()));
        }

        Category category = convertToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        logger.info("Category created successfully with ID: {}", savedCategory.getId());
        return convertToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        logger.info("Updating category with ID: {}", id);
        validateCategoryData(categoryDto);

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new MovieException(String.format(ErrorMessages.CATEGORY_NOT_FOUND_MESSAGE, id)));

        if (!existingCategory.getName().equals(categoryDto.getName()) && categoryRepository.existsByName(categoryDto.getName())) {
            throw new MovieException(String.format(ErrorMessages.CATEGORY_ALREADY_EXISTS_MESSAGE, categoryDto.getName()));
        }

        existingCategory.setName(categoryDto.getName());
        existingCategory.setActive(categoryDto.isActive());
        Category updatedCategory = categoryRepository.save(existingCategory);
        logger.info("Category updated successfully with ID: {}", updatedCategory.getId());
        return convertToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        logger.info("Deleting category with ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new MovieException(String.format(ErrorMessages.CATEGORY_NOT_FOUND_MESSAGE, id));
        }
        categoryRepository.deleteById(id);
        logger.info("Category deleted successfully with ID: {}", id);
    }
}