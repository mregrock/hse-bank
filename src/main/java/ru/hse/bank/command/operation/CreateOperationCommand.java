package ru.hse.bank.command.operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
   * Constructor for CreateOperationCommand.
   *
   * @param domainFactory the domain factory to use for creating the operation
   * @param type the type of the operation
   * @param bankAccountId the ID of the bank account
   * @param amount the amount of the operation
   * @param description the description of the operation
   * @param categoryId the ID of the category
   */
  public CreateOperationCommand(DomainFactory domainFactory,
                CategoryType type,
                UUID bankAccountId,
                BigDecimal amount,
                String description,
                UUID categoryId) {
    this.domainFactory = domainFactory;
    this.type = type;
    this.bankAccountId = bankAccountId;
    this.amount = amount;
    this.description = description;
    this.categoryId = categoryId;
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