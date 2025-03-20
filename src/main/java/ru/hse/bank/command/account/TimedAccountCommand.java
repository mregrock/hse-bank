package ru.hse.bank.command.account;

import ru.hse.bank.model.BankAccount;

/**
 * Wrapper for an account command that measures the execution time.
 * This class implements the AccountCommand interface and provides a constructor for
 * initializing the command with the actual account command to execute.
 */
public class TimedAccountCommand implements AccountCommand {
  private final AccountCommand command;

  /**
   * Constructor for TimedAccountCommand.
   *
   * @param commandParam the account command to execute and measure
   */
  public TimedAccountCommand(final AccountCommand commandParam) {
    this.command = commandParam;
  }

  /**
   * Executes the command and measures the execution time.
   *
   * @return the result of the command execution
   */
  @Override
  public BankAccount execute() {
    long startTime = System.currentTimeMillis();
    BankAccount result = command.execute();
    long endTime = System.currentTimeMillis();
    System.out.println("Выполнение команды заняло: " + (endTime - startTime) + " мс");
    return result;
  }
} 