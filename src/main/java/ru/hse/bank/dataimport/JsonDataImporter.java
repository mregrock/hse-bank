package ru.hse.bank.dataimport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;

/**
 * JsonDataImporter is a class that imports data from a JSON file.
 * It extends the DataImporter class and implements the abstract methods
 * for parsing the data from the JSON file.
 */
@Component
public class JsonDataImporter extends DataImporter {
  private final ObjectMapper objectMapper;

  /**
   * Constructor for JsonDataImporter.
   *
   * @param objectMapperParam the Jackson object mapper
   * @param bankAccountFacadeParam the bank account facade to use for creating bank accounts
   * @param categoryFacadeParam the category facade to use for creating categories
   * @param operationFacadeParam the operation facade to use for creating operations
   */
  public JsonDataImporter(final ObjectMapper objectMapperParam, 
                       final BankAccountFacade bankAccountFacadeParam,
                       final CategoryFacade categoryFacadeParam, 
                       final OperationFacade operationFacadeParam) {
    super(bankAccountFacadeParam, categoryFacadeParam, operationFacadeParam);
    this.objectMapper = objectMapperParam;
  }

  @Override
  protected String readFile(final Path filePath) throws IOException {
    return Files.readString(filePath);
  }

  @Override
  protected List<BankAccount> parseAccounts(final String content) throws IOException {
    ImportData data = objectMapper.readValue(content, ImportData.class);
    return data.accounts;
  }

  @Override
  protected List<Category> parseCategories(final String content) throws IOException {
    ImportData data = objectMapper.readValue(content, ImportData.class);
    return data.categories;
  }

  @Override
  protected List<Operation> parseOperations(final String content) throws IOException {
    ImportData data = objectMapper.readValue(content, ImportData.class);
    return data.operations;
  }

  private static class ImportData {
    public List<BankAccount> accounts;
    public List<Category> categories;
    public List<Operation> operations;
  }
} 