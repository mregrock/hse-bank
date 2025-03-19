package ru.hse.bank.command.operation;

import ru.hse.bank.command.TimedCommand;
import ru.hse.bank.model.Operation;

/**
 * Represents a timed operation command.
 * This class extends the TimedCommand class and provides 
 * a constructor for initializing the command.
 */
public class TimedOperationCommand extends TimedCommand<Operation> {
  /**
   * Constructor for TimedOperationCommand.
   *
   * @param command the command to execute
   */
  public TimedOperationCommand(OperationCommand command) {
    super(command);
  }
}
