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
   * @param bankAccountFacadeParam the bank account facade
   * @param categoryFacadeParam the category facade
   * @param operationFacadeParam the operation facade
   */
  public DataExporter(final BankAccountFacade bankAccountFacadeParam,
             final CategoryFacade categoryFacadeParam,
             final OperationFacade operationFacadeParam) {
    this.bankAccountFacade = bankAccountFacadeParam;
    this.categoryFacade = categoryFacadeParam;
    this.operationFacade = operationFacadeParam;
  }

  /**
   * Exports data to a file.
   *
   * @param filePath the path to the file
   * @param visitor the data visitor
   * @throws IOException if an I/O error occurs
   */
  public void exportData(final Path filePath, final DataVisitor visitor) throws IOException {
    bankAccountFacade.getAllAccounts().forEach(visitor::visit);
    categoryFacade.getAllCategories().forEach(visitor::visit);
    operationFacade.getAllOperations().forEach(visitor::visit);

    String result = visitor.getResult();
    Files.writeString(filePath, result);
  }
} 