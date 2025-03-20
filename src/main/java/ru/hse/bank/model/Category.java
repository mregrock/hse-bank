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
  
  public Category(UUID id, String name, CategoryType type) {
    this.id = id;
    this.name = name;
    this.type = type;
  }
  
  public UUID getId() {
    return id;
  }
  
  public void setId(UUID id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public CategoryType getType() {
    return type;
  }
  
  public void setType(CategoryType type) {
    this.type = type;
  }
  
  public static CategoryBuilder builder() {
    return new CategoryBuilder();
  }
  
  public static class CategoryBuilder {
    private UUID id;
    private String name;
    private CategoryType type;
    
    public CategoryBuilder id(UUID id) {
      this.id = id;
      return this;
    }
    
    public CategoryBuilder name(String name) {
      this.name = name;
      return this;
    }
    
    public CategoryBuilder type(CategoryType type) {
      this.type = type;
      return this;
    }
    
    public Category build() {
      return new Category(id, name, type);
    }
  }
} 