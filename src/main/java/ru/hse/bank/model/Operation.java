package ru.hse.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an operation in the bank system.
 * This class contains information about the operation, 
 * including its ID, type, bank account ID, amount, description, category ID, and date.
 */
public class Operation {
  private UUID id;
  private CategoryType type;
  private UUID bankAccountId;
  private BigDecimal amount;
  private String description;
  private UUID categoryId;
  private LocalDateTime date;
  
  public Operation() {
  }
  
  public Operation(UUID id, CategoryType type, UUID bankAccountId, BigDecimal amount, 
                  String description, UUID categoryId, LocalDateTime date) {
    this.id = id;
    this.type = type;
    this.bankAccountId = bankAccountId;
    this.amount = amount;
    this.description = description;
    this.categoryId = categoryId;
    this.date = date;
  }
  
  public UUID getId() {
    return id;
  }
  
  public void setId(UUID id) {
    this.id = id;
  }
  
  public CategoryType getType() {
    return type;
  }
  
  public void setType(CategoryType type) {
    this.type = type;
  }
  
  public UUID getBankAccountId() {
    return bankAccountId;
  }
  
  public void setBankAccountId(UUID bankAccountId) {
    this.bankAccountId = bankAccountId;
  }
  
  public BigDecimal getAmount() {
    return amount;
  }
  
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public UUID getCategoryId() {
    return categoryId;
  }
  
  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
  }
  
  public LocalDateTime getDate() {
    return date;
  }
  
  public void setDate(LocalDateTime date) {
    this.date = date;
  }
  
  public static OperationBuilder builder() {
    return new OperationBuilder();
  }
  
  public static class OperationBuilder {
    private UUID id;
    private CategoryType type;
    private UUID bankAccountId;
    private BigDecimal amount;
    private String description;
    private UUID categoryId;
    private LocalDateTime date;
    
    public OperationBuilder id(UUID id) {
      this.id = id;
      return this;
    }
    
    public OperationBuilder type(CategoryType type) {
      this.type = type;
      return this;
    }
    
    public OperationBuilder bankAccountId(UUID bankAccountId) {
      this.bankAccountId = bankAccountId;
      return this;
    }
    
    public OperationBuilder amount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }
    
    public OperationBuilder description(String description) {
      this.description = description;
      return this;
    }
    
    public OperationBuilder categoryId(UUID categoryId) {
      this.categoryId = categoryId;
      return this;
    }
    
    public OperationBuilder date(LocalDateTime date) {
      this.date = date;
      return this;
    }
    
    public Operation build() {
      return new Operation(id, type, bankAccountId, amount, description, categoryId, date);
    }
  }
} 