package ru.hse.bank.command.operation;

import ru.hse.bank.model.Operation;

/**
 * Wrapper for an operation command that measures the execution time.
 * This class implements the OperationCommand interface and provides a constructor for
 * initializing the command with the actual operation command to execute.
 */
public class TimedOperationCommand implements OperationCommand {
  private final OperationCommand command;

  /**
   * Constructor for TimedOperationCommand.
   *
   * @param commandParam the operation command to execute and measure
   */
  public TimedOperationCommand(final OperationCommand commandParam) {
    this.command = commandParam;
  }

  /**
   * Executes the command and measures the execution time.
   *
   * @return the result of the command execution
   */
  @Override
  public Operation execute() {
    long startTime = System.currentTimeMillis();
    Operation result = command.execute();
    long endTime = System.currentTimeMillis();
    System.out.println("Выполнение команды заняло: " + (endTime - startTime) + " мс");
    return result;
  }
}
