package ru.hse.bank.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.CategoryType;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryFacadeTest {
    
    @Mock
    private DomainFactory domainFactory;
    
    private CategoryFacade categoryFacade;
    
    @BeforeEach
    void setUp() {
        categoryFacade = new CategoryFacade(domainFactory);
    }
    
    @Test
    void createCategory_ShouldCreateAndStoreCategory() {
        // Arrange
        String name = "Еда";
        CategoryType type = CategoryType.EXPENSE;
        UUID categoryId = UUID.randomUUID();
        
        Category mockCategory = Category.builder()
                .id(categoryId)
                .name(name)
                .type(type)
                .build();
        
        when(domainFactory.createCategory(name, type)).thenReturn(mockCategory);
        
        // Act
        Category result = categoryFacade.createCategory(name, type);
        
        // Assert
        assertEquals(mockCategory, result);
        verify(domainFactory).createCategory(name, type);
        
        // Проверяем, что категория сохранена в фасаде
        assertEquals(mockCategory, categoryFacade.getCategory(categoryId));
    }
    
    @Test
    void getAllCategories_ShouldReturnAllCategories() {
        // Arrange
        Category category1 = createAndAddCategory("Зарплата", CategoryType.INCOME);
        Category category2 = createAndAddCategory("Транспорт", CategoryType.EXPENSE);
        Category category3 = createAndAddCategory("Кафе", CategoryType.EXPENSE);
        
        // Act
        List<Category> allCategories = categoryFacade.getAllCategories();
        
        // Assert
        assertEquals(3, allCategories.size());
        assertTrue(allCategories.contains(category1));
        assertTrue(allCategories.contains(category2));
        assertTrue(allCategories.contains(category3));
    }
    
    @Test
    void getCategoriesByType_ShouldReturnCategoriesOfSpecificType() {
        // Arrange
        Category incomeCategory1 = createAndAddCategory("Зарплата", CategoryType.INCOME);
        Category incomeCategory2 = createAndAddCategory("Проценты", CategoryType.INCOME);
        Category expenseCategory = createAndAddCategory("Транспорт", CategoryType.EXPENSE);
        
        // Act
        List<Category> incomeCategories = categoryFacade.getCategoriesByType(CategoryType.INCOME);
        List<Category> expenseCategories = categoryFacade.getCategoriesByType(CategoryType.EXPENSE);
        
        // Assert
        assertEquals(2, incomeCategories.size());
        assertTrue(incomeCategories.contains(incomeCategory1));
        assertTrue(incomeCategories.contains(incomeCategory2));
        
        assertEquals(1, expenseCategories.size());
        assertTrue(expenseCategories.contains(expenseCategory));
    }
    
    @Test
    void deleteCategory_ShouldRemoveCategoryFromFacade() {
        // Arrange
        Category category = createAndAddCategory("Кафе", CategoryType.EXPENSE);
        UUID categoryId = category.getId();
        
        // Act
        categoryFacade.deleteCategory(categoryId);
        
        // Assert
        assertNull(categoryFacade.getCategory(categoryId));
    }
    
    private Category createAndAddCategory(String name, CategoryType type) {
        UUID id = UUID.randomUUID();
        Category category = Category.builder()
                .id(id)
                .name(name)
                .type(type)
                .build();
        
        when(domainFactory.createCategory(name, type)).thenReturn(category);
        return categoryFacade.createCategory(name, type);
    }
} 