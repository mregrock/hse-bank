package ru.hse.bank.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Component;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

/**
 * Represents a domain factory that creates bank accounts, categories, and operations.
 * This class implements the DomainFactory interface and provides a constructor for 
 * initializing the factory.
 */
@Component
public class DomainFactory {
  /**
   * Creates a new bank account.
   *
   * @param name the name of the bank account
   * @param initialBalance the initial balance of the bank account
   * @return the created bank account
   */
  public BankAccount createBankAccount(String name, BigDecimal initialBalance) {
    if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Initial balance cannot be negative");
    }
    return BankAccount.builder()
        .id(UUID.randomUUID())
        .name(name)
        .balance(initialBalance)
        .build();
  }

  /**
   * Creates a new category.
   *
   * @param name the name of the category
   * @param type the type of the category
   * @return the created category
   */
  public Category createCategory(String name, CategoryType type) {
    return Category.builder()
        .id(UUID.randomUUID())
        .name(name)
        .type(type)
        .build();
  }

  /**
   * Creates a new operation.
   *
   * @param type the type of the operation
   * @param bankAccountId the ID of the bank account
   * @param amount the amount of the operation
   * @param description the description of the operation
   * @param categoryId the ID of the category
   * @return the created operation
   */
  public Operation createOperation(CategoryType type, UUID bankAccountId, 
                   BigDecimal amount, String description, UUID categoryId) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }
    return Operation.builder()
        .id(UUID.randomUUID())
        .type(type)
        .bankAccountId(bankAccountId)
        .amount(amount)
        .date(LocalDateTime.now())
        .description(description)
        .categoryId(categoryId)
        .build();
  }
}
