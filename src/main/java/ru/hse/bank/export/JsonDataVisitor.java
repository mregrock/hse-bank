package ru.hse.bank.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;

/**
 * Represents a JSON data visitor that visits bank accounts, categories, and operations.
 * This class implements the DataVisitor interface and provides a constructor for 
 * initializing the visitor.
 */
public class JsonDataVisitor implements DataVisitor {
  private final ObjectMapper objectMapper;
  private final List<BankAccount> accounts;
  private final List<Category> categories;
  private final List<Operation> operations;

  /**
   * Constructor for JsonDataVisitor.
   * Initializes the object mapper, and the lists of accounts, categories, and operations.
   */
  public JsonDataVisitor() {
    this.objectMapper = new ObjectMapper();
    this.accounts = new ArrayList<>();
    this.categories = new ArrayList<>();
    this.operations = new ArrayList<>();
  }

  /**
   * Visits a bank account and adds it to the list of accounts.
   *
   * @param account the bank account to visit
   */
  @Override
  public void visit(BankAccount account) {
    accounts.add(account);
  }

  /**
   * Visits a category and adds it to the list of categories.
   *
   * @param category the category to visit
   */
  @Override
  public void visit(Category category) {
    categories.add(category);
  }

  /**
   * Visits an operation and adds it to the list of operations.
   *
   * @param operation the operation to visit
   */
  @Override
  public void visit(Operation operation) {
    operations.add(operation);
  }

  /**
   * Returns the JSON representation of the exported data.
   *
   * @return the JSON representation of the exported data
   */
  @Override
  public String getResult() {
    try {
      return objectMapper.writeValueAsString(new ExportData(accounts, categories, operations));
    } catch (Exception exception) {
      throw new RuntimeException("Failed to convert data to JSON", exception);
    }
  }

  /**
   * Represents the exported data.
   * This record contains the lists of bank accounts, categories, and operations.
   */
  private record ExportData(
      List<BankAccount> accounts,
      List<Category> categories,
      List<Operation> operations
  ) {}
}