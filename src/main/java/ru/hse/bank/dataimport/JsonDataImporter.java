package ru.hse.bank.dataimport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
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
@Component
public class JsonDataImporter extends DataImporter {
  private final ObjectMapper objectMapper;
  
  public JsonDataImporter(ObjectMapper objectMapper, BankAccountFacade bankAccountFacade,
                     CategoryFacade categoryFacade, OperationFacade operationFacade) {
    super(bankAccountFacade, categoryFacade, operationFacade);
    this.objectMapper = objectMapper;
  }

  @Override
  protected String readFile(Path filePath) throws IOException {
    return Files.readString(filePath);
  }

  @Override
  protected List<BankAccount> parseAccounts(String content) throws IOException {
    ImportData data = objectMapper.readValue(content, ImportData.class);
    return data.accounts;
  }

  @Override
  protected List<Category> parseCategories(String content) throws IOException {
    ImportData data = objectMapper.readValue(content, ImportData.class);
    return data.categories;
  }

  @Override
  protected List<Operation> parseOperations(String content) throws IOException {
    ImportData data = objectMapper.readValue(content, ImportData.class);
    return data.operations;
  }

  private static class ImportData {
    public List<BankAccount> accounts;
    public List<Category> categories;
    public List<Operation> operations;
  }
} 