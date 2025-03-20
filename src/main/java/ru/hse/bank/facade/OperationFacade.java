package ru.hse.bank.facade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

/**
 * Facade for operation management.
 * Provides a high-level interface for managing financial operations,
 * including operation creation, retrieval, and cancellation.
 */
@Service
public class OperationFacade {
  private final DomainFactory domainFactory;
  private final BankAccountFacade bankAccountFacade;
  private final Map<UUID, Operation> operations = new HashMap<>();

  /**
   * Constructor for OperationFacade.
   *
   * @param domainFactoryParam the domain factory to use for creating operations
   * @param bankAccountFacadeParam the bank account facade to use for updating balances
   */
  public OperationFacade(final DomainFactory domainFactoryParam, 
                         final BankAccountFacade bankAccountFacadeParam) {
    this.domainFactory = domainFactoryParam;
    this.bankAccountFacade = bankAccountFacadeParam;
  }

  /**
   * Creates a new operation with the specified parameters.
   *
   * @param type the type of the operation (INCOME or EXPENSE)
   * @param bankAccountId the ID of the bank account associated with the operation
   * @param amount the amount of the operation
   * @param description the description of the operation
   * @param categoryId the ID of the category associated with the operation
   * @return the newly created operation
   */
  public Operation createOperation(final CategoryType type, final UUID bankAccountId, 
                   final BigDecimal amount, final String description, final UUID categoryId) {
    Operation operation = domainFactory.createOperation(type, bankAccountId, amount, 
                                                        description, categoryId);
    bankAccountFacade.updateBalance(bankAccountId, operation);
    operations.put(operation.getId(), operation);
    return operation;
  }

  /**
   * Retrieves an operation by its ID.
   *
   * @param id the ID of the operation to retrieve
   * @return the operation with the specified ID, or null if it does not exist
   */
  public Operation getOperation(final UUID id) {
    return operations.get(id);
  }

  /**
   * Returns a list of all operations.
   *
   * @return list of all operations
   */
  public List<Operation> getAllOperations() {
    return new ArrayList<>(operations.values());
  }

  /**
   * Returns a list of operations for a specific account.
   *
   * @param bankAccountId the ID of the account
   * @return list of operations for the specified account
   */
  public List<Operation> getOperationsByAccount(final UUID bankAccountId) {
    return operations.values().stream()
        .filter(operation -> operation.getBankAccountId().equals(bankAccountId))
        .collect(Collectors.toList());
  }

  /**
   * Returns a list of operations for a specific category.
   *
   * @param categoryId the ID of the category
   * @return list of operations for the specified category
   */
  public List<Operation> getOperationsByCategory(final UUID categoryId) {
    return operations.values().stream()
        .filter(operation -> operation.getCategoryId().equals(categoryId))
        .collect(Collectors.toList());
  }

  /**
   * Returns a list of operations for a specific period.
   *
   * @param start the start date of the period
   * @param end the end date of the period
   * @return list of operations for the specified period
   */
  public List<Operation> getOperationsByPeriod(final LocalDateTime start, final LocalDateTime end) {
    return operations.values().stream()
        .filter(operation -> operation.getDate().isAfter(start) 
                && operation.getDate().isBefore(end))
        .collect(Collectors.toList());
  }

  /**
   * Deletes an operation by its ID.
   *
   * @param id the ID of the operation to delete
   */
  public void deleteOperation(final UUID id) {
    Operation operation = operations.get(id);
    if (operation != null) {
      Operation reverseOperation = domainFactory.createOperation(
          operation.getType() == CategoryType.INCOME ? CategoryType.EXPENSE : CategoryType.INCOME,
          operation.getBankAccountId(),
          operation.getAmount(),
          "Отмена операции: " + operation.getDescription(),
          operation.getCategoryId()
      );
      bankAccountFacade.updateBalance(operation.getBankAccountId(), reverseOperation);
      operations.remove(id);
    }
  }
}
