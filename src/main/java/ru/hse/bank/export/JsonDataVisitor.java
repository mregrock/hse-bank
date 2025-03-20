package ru.hse.bank.export;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;

/**
 * Visitor for exporting data to JSON format.
 * Follows the Visitor pattern to handle different types of entities.
 */
@Component
public class JsonDataVisitor implements DataVisitor {
  private final List<BankAccount> accounts = new ArrayList<>();
  private final List<Category> categories = new ArrayList<>();
  private final List<Operation> operations = new ArrayList<>();
  private final ObjectMapper objectMapper;

  /**
   * Constructor for JsonDataVisitor.
   *
   * @param objectMapperParam the Jackson object mapper
   */
  public JsonDataVisitor(final ObjectMapper objectMapperParam) {
    this.objectMapper = objectMapperParam;
  }

  @Override
  public void visit(final BankAccount bankAccount) {
    accounts.add(bankAccount);
  }

  @Override
  public void visit(final Category category) {
    categories.add(category);
  }

  @Override
  public void visit(final Operation operation) {
    operations.add(operation);
  }

  @Override
  public String getResult() {
    try {
      ExportData exportData = new ExportData();
      exportData.accounts = accounts;
      exportData.categories = categories;
      exportData.operations = operations;
      return objectMapper.writeValueAsString(exportData);
    } catch (Exception exception) {
      throw new RuntimeException("Ошибка преобразования данных в JSON", exception);
    }
  }

  private static class ExportData {
    public List<BankAccount> accounts;
    public List<Category> categories;
    public List<Operation> operations;
  }
}
