package ru.hse.bank.facade;

import org.springframework.stereotype.Service;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.CategoryType;

import java.util.*;

@Service
public class CategoryFacade {
    private final DomainFactory domainFactory;
    private final Map<UUID, Category> categories = new HashMap<>();

    public CategoryFacade(DomainFactory domainFactory) {
        this.domainFactory = domainFactory;
    }

    public Category createCategory(String name, CategoryType type) {
        Category category = domainFactory.createCategory(name, type);
        categories.put(category.getId(), category);
        return category;
    }

    public Category getCategory(UUID id) {
        return categories.get(id);
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }

    public List<Category> getCategoriesByType(CategoryType type) {
        return categories.values().stream()
                .filter(category -> category.getType() == type)
                .toList();
    }

    public void deleteCategory(UUID id) {
        categories.remove(id);
    }
} 