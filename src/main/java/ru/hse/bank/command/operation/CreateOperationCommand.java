package ru.hse.bank.command.operation;

import java.math.BigDecimal;
import java.util.UUID;

import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

/**
 * Represents a command for creating an operation.
 * This class implements the OperationCommand interface and provides a constructor
 * for initializing the command with the necessary parameters.
 */
public class CreateOperationCommand implements OperationCommand {
  private final DomainFactory domainFactory;
  private final CategoryType type;
  private final UUID bankAccountId;
  private final BigDecimal amount;
  private final String description;
  private final UUID categoryId;

  /**
   * Constructs a new CreateOperationCommand.
   *
   * @param domainFactoryParam the domain factory to use for creating the operation
   * @param typeParam the type of the operation
   * @param bankAccountIdParam the ID of the bank account
   * @param amountParam the amount of the operation
   * @param descriptionParam the description of the operation
   * @param categoryIdParam the ID of the category
   */
  public CreateOperationCommand(final DomainFactory domainFactoryParam,
                final CategoryType typeParam,
                final UUID bankAccountIdParam,
                final BigDecimal amountParam,
                final String descriptionParam,
                final UUID categoryIdParam) {
    this.domainFactory = domainFactoryParam;
    this.type = typeParam;
    this.bankAccountId = bankAccountIdParam;
    this.amount = amountParam;
    this.description = descriptionParam;
    this.categoryId = categoryIdParam;
  }

  /**
   * Executes the command and creates an operation.
   *
   * @return the created operation
   */
  @Override
  public Operation execute() {
    return domainFactory.createOperation(
      type,
      bankAccountId,
      amount,
      description,
      categoryId
    );
  }
} 