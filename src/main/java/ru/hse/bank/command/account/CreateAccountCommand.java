package ru.hse.bank.command.account;

import java.math.BigDecimal;
import java.util.UUID;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.BankAccount;

/**
 * Represents a command for creating a bank account.
 * This class implements the AccountCommand interface and provides 
 * a constructor for initializing the command with the necessary parameters.
 */
public class CreateAccountCommand implements AccountCommand {
  private final DomainFactory domainFactory;
  private final String name;
  private final BigDecimal balance;

  /**
   * Constructor for CreateAccountCommand.
   *
   * @param domainFactory the domain factory
   * @param name the name of the bank account
   * @param balance the balance of the bank account
   */
  public CreateAccountCommand(DomainFactory domainFactory, String name, BigDecimal balance) {
    this.domainFactory = domainFactory;
    this.name = name;
    this.balance = balance;
  }

  /**
   * Executes the command and creates a new bank account.
   *
   * @return the created bank account
   */
  @Override
  public BankAccount execute() {
    return domainFactory.createBankAccount(name, balance);
  }
} 