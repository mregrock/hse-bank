package ru.hse.bank.dataimport;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
public class JsonDataImporter extends DataImporter {
  private final ObjectMapper objectMapper;

  /**
   * Constructor for JsonDataImporter.
   *
   * @param bankAccountFacade the bank account facade to use for creating bank accounts
   * @param categoryFacade the category facade to use for creating categories
   * @param operationFacade the operation facade to use for creating operations
   */
  public JsonDataImporter(BankAccountFacade bankAccountFacade,
              CategoryFacade categoryFacade,
              OperationFacade operationFacade) {
    super(bankAccountFacade, categoryFacade, operationFacade);
    this.objectMapper = new ObjectMapper();
  }

  /**
   * Reads the content of a file.
   *
   * @param filePath the path to the file to read
   * @return the content of the file
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected String readFile(Path filePath) throws IOException {
    return Files.readString(filePath);
  }

  /**
   * Parses the accounts from the content.
   *
   * @param content the content of the file
   * @return the list of accounts
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected List<BankAccount> parseAccounts(String content) throws IOException {
    return objectMapper.readValue(content, objectMapper.getTypeFactory()
        .constructCollectionType(List.class, BankAccount.class));
  }

  /**
   * Parses the categories from the content.
   *
   * @param content the content of the file
   * @return the list of categories
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected List<Category> parseCategories(String content) throws IOException {
    return objectMapper.readValue(content, objectMapper.getTypeFactory()
        .constructCollectionType(List.class, Category.class));
  }

  /**
   * Parses the operations from the content.
   *
   * @param content the content of the file
   * @return the list of operations
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected List<Operation> parseOperations(String content) throws IOException {
    return objectMapper.readValue(content, objectMapper.getTypeFactory()
        .constructCollectionType(List.class, Operation.class));
  }
} 