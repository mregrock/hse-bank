package ru.hse.bank.command.account;

import java.math.BigDecimal;

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
   * @param domainFactoryParam the domain factory
   * @param nameParam the name of the bank account
   * @param balanceParam the balance of the bank account
   */
  public CreateAccountCommand(final DomainFactory domainFactoryParam, 
                             final String nameParam, 
                             final BigDecimal balanceParam) {
    this.domainFactory = domainFactoryParam;
    this.name = nameParam;
    this.balance = balanceParam;
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