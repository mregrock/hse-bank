package ru.hse.bank.export;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;

/**
 * Represents a data exporter that exports data to a file.
 * This class implements the DataExporter interface and provides a constructor for 
 * initializing the exporter with the necessary facades.
 */
@Component
public class DataExporter {
  private final BankAccountFacade bankAccountFacade;
  private final CategoryFacade categoryFacade;
  private final OperationFacade operationFacade;

  /**
   * Constructor for DataExporter.
   * Initializes the bank account facade, category facade, and operation facade.
   *
   * @param bankAccountFacade the bank account facade
   * @param categoryFacade the category facade
   */
  public DataExporter(BankAccountFacade bankAccountFacade,
             CategoryFacade categoryFacade,
             OperationFacade operationFacade) {
    this.bankAccountFacade = bankAccountFacade;
    this.categoryFacade = categoryFacade;
    this.operationFacade = operationFacade;
  }

  /**
   * Exports data to a file.
   *
   * @param filePath the path to the file
   * @param visitor the data visitor
   * @throws IOException if an I/O error occurs
   */
  public void exportData(Path filePath, DataVisitor visitor) throws IOException {
    bankAccountFacade.getAllAccounts().forEach(visitor::visit);
    categoryFacade.getAllCategories().forEach(visitor::visit);
    operationFacade.getAllOperations().forEach(visitor::visit);

    String result = visitor.getResult();
    Files.writeString(filePath, result);
  }
} 