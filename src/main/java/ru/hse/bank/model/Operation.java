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

  /**
   * Constructor for Operation.
   *
   * @param idParam the ID of the operation
   * @param typeParam the type of the operation
   * @param bankAccountIdParam the ID of the bank account
   * @param amountParam the amount of the operation
   * @param descriptionParam the description of the operation
   * @param categoryIdParam the ID of the category
   * @param dateParam the date of the operation
   */
  public Operation(final UUID idParam, final CategoryType typeParam, final UUID bankAccountIdParam, 
                  final BigDecimal amountParam, final String descriptionParam, 
                  final UUID categoryIdParam, final LocalDateTime dateParam) {
    this.id = idParam;
    this.type = typeParam;
    this.bankAccountId = bankAccountIdParam;
    this.amount = amountParam;
    this.description = descriptionParam;
    this.categoryId = categoryIdParam;
    this.date = dateParam;
  }

  public UUID getId() {
    return id;
  }

  public void setId(final UUID idParam) {
    this.id = idParam;
  }

  public CategoryType getType() {
    return type;
  }

  public void setType(final CategoryType typeParam) {
    this.type = typeParam;
  }

  public UUID getBankAccountId() {
    return bankAccountId;
  }

  public void setBankAccountId(final UUID bankAccountIdParam) {
    this.bankAccountId = bankAccountIdParam;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(final BigDecimal amountParam) {
    this.amount = amountParam;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String descriptionParam) {
    this.description = descriptionParam;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(final UUID categoryIdParam) {
    this.categoryId = categoryIdParam;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(final LocalDateTime dateParam) {
    this.date = dateParam;
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

    public OperationBuilder id(final UUID idParam) {
      this.id = idParam;
      return this;
    }

    public OperationBuilder type(final CategoryType typeParam) {
      this.type = typeParam;
      return this;
    }

    public OperationBuilder bankAccountId(final UUID bankAccountIdParam) {
      this.bankAccountId = bankAccountIdParam;
      return this;
    }

    public OperationBuilder amount(final BigDecimal amountParam) {
      this.amount = amountParam;
      return this;
    }

    public OperationBuilder description(final String descriptionParam) {
      this.description = descriptionParam;
      return this;
    }

    public OperationBuilder categoryId(final UUID categoryIdParam) {
      this.categoryId = categoryIdParam;
      return this;
    }

    public OperationBuilder date(final LocalDateTime dateParam) {
      this.date = dateParam;
      return this;
    }

    public Operation build() {
      return new Operation(id, type, bankAccountId, amount, description, categoryId, date);
    }
  }
} 