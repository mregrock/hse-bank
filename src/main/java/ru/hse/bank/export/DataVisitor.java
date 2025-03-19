package ru.hse.bank.export;

import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;

/**
 * Represents a data visitor that visits bank accounts, categories, and operations.
 * This interface extends the DataVisitor interface and provides a constructor for 
 * initializing the visitor.
 */
public interface DataVisitor {
  void visit(BankAccount account);

  void visit(Category category);

  void visit(Operation operation);

  String getResult();
} 