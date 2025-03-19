package ru.hse.bank.dataimport;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;

/**
 * Abstract class for importing data from a file.
 * Provides a high-level interface for importing data from a file,
 * including reading the file, parsing the data, and creating the entities.
 */
@Component
public abstract class DataImporter {
  protected final BankAccountFacade bankAccountFacade;
  protected final CategoryFacade categoryFacade;
  protected final OperationFacade operationFacade;

  /**
   * Constructor for DataImporter.
   *
   * @param bankAccountFacade the bank account facade to use for creating bank accounts
   * @param categoryFacade the category facade to use for creating categories
   * @param operationFacade the operation facade to use for creating operations
   */
  protected DataImporter(BankAccountFacade bankAccountFacade,
             CategoryFacade categoryFacade,
             OperationFacade operationFacade) {
    this.bankAccountFacade = bankAccountFacade;
    this.categoryFacade = categoryFacade;
    this.operationFacade = operationFacade;
  }

  /**
   * Imports data from a file.
   *
   * @param filePath the path to the file to import
   * @throws IOException if an I/O error occurs
   */
  public final void importData(Path filePath) throws IOException {
    String content = readFile(filePath);
    List<BankAccount> accounts = parseAccounts(content);
    List<Category> categories = parseCategories(content);
    List<Operation> operations = parseOperations(content);

    for (BankAccount account : accounts) {
      bankAccountFacade.createAccount(account.getName(), account.getBalance());
    }

    for (Category category : categories) {
      categoryFacade.createCategory(category.getName(), category.getType());
    }

    for (Operation operation : operations) {
      operationFacade.createOperation(
          operation.getType(),
          operation.getBankAccountId(),
          operation.getAmount(),
          operation.getDescription(),
          operation.getCategoryId()
      );
    }
  }

  /**
   * Reads the content of a file.
   *
   * @param filePath the path to the file to read
   * @return the content of the file
   * @throws IOException if an I/O error occurs
   */
  protected abstract String readFile(Path filePath) throws IOException;

  /**
   * Parses the accounts from the content.
   *
   * @param content the content of the file
   * @return the list of accounts
   * @throws IOException if an I/O error occurs
   */
  protected abstract List<BankAccount> parseAccounts(String content) throws IOException;

  /**
   * Parses the categories from the content.
   *
   * @param content the content of the file
   * @return the list of categories
   * @throws IOException if an I/O error occurs
   */
  protected abstract List<Category> parseCategories(String content) throws IOException;

  /**
   * Parses the operations from the content.
   *
   * @param content the content of the file
   * @return the list of operations
   * @throws IOException if an I/O error occurs
   */
  protected abstract List<Operation> parseOperations(String content) throws IOException;
} 