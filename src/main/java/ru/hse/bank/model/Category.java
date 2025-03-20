package ru.hse.bank.model;

import java.util.UUID;

/**
 * Represents a category in the bank system.
 * This class contains information about the category, including its ID, name, and type.
 */
public class Category {
  private UUID id;
  private String name;
  private CategoryType type;

  public Category() {
  }

  /**
   * Constructor for Category.
   *
   * @param idParam the ID of the category
   * @param nameParam the name of the category
   * @param typeParam the type of the category
   */
  public Category(final UUID idParam, final String nameParam, final CategoryType typeParam) {
    this.id = idParam;
    this.name = nameParam;
    this.type = typeParam;
  }

  public UUID getId() {
    return id;
  }

  public void setId(final UUID idParam) {
    this.id = idParam;
  }

  public String getName() {
    return name;
  }

  public void setName(final String nameParam) {
    this.name = nameParam;
  }

  public CategoryType getType() {
    return type;
  }

  public void setType(final CategoryType typeParam) {
    this.type = typeParam;
  }

  public static CategoryBuilder builder() {
    return new CategoryBuilder();
  }

  public static class CategoryBuilder {
    private UUID id;
    private String name;
    private CategoryType type;

    public CategoryBuilder id(final UUID idParam) {
      this.id = idParam;
      return this;
    }

    public CategoryBuilder name(final String nameParam) {
      this.name = nameParam;
      return this;
    }

    public CategoryBuilder type(final CategoryType typeParam) {
      this.type = typeParam;
      return this;
    }

    public Category build() {
      return new Category(id, name, type);
    }
  }
} 