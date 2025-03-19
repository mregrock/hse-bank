package ru.hse.bank.export;

import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;

/**
 * Visitor interface for data processing.
 * Provides methods to visit different types of data objects.
 */
public interface DataVisitor {
  /**
   * Visits a bank account.
   *
   * @param account the bank account to visit
   */
  void visit(BankAccount account);

  /**
   * Visits a category.
   *
   * @param category the category to visit
   */
  void visit(Category category);

  /**
   * Visits an operation.
   *
   * @param operation the operation to visit
   */
  void visit(Operation operation);

  /**
   * Gets the result of the visit.
   *
   * @return the result of processing all visited objects
   */
  String getResult();
} 