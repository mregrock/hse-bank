package ru.hse.bank.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.CategoryType;

/**
 * Facade for category management operations.
 * Provides a high-level interface for managing transaction categories,
 * including category creation and retrieval.
 */
@Service
public class CategoryFacade {
  private final DomainFactory domainFactory;
  private final Map<UUID, Category> categories = new HashMap<>();

  /**
   * Constructor for CategoryFacade.
   *
   * @param domainFactoryParam the domain factory to use for creating categories
   */
  public CategoryFacade(final DomainFactory domainFactoryParam) {
    this.domainFactory = domainFactoryParam;
  }

  /**
   * Creates a new category with the specified name and type.
   *
   * @param name the name of the new category
   * @param type the type of the category (INCOME or EXPENSE)
   * @return the newly created category
   */
  public Category createCategory(final String name, final CategoryType type) {
    Category category = domainFactory.createCategory(name, type);
    categories.put(category.getId(), category);
    return category;
  }

  public Category getCategory(final UUID id) {
    return categories.get(id);
  }

  /**
   * Returns a list of all categories.
   *
   * @return list of all categories
   */
  public List<Category> getAllCategories() {
    return new ArrayList<>(categories.values());
  }

  /**
   * Returns a list of all categories of a specific type.
   *
   * @param type the type of the categories to retrieve
   * @return list of all categories of the specified type
   */
  public List<Category> getCategoriesByType(final CategoryType type) {
    return categories.values().stream()
        .filter(category -> category.getType() == type)
        .toList();
  }

  /**
   * Deletes a category by its ID.
   *
   * @param id the ID of the category to delete
   */
  public void deleteCategory(final UUID id) {
    categories.remove(id);
  }
} 