package ru.hse.bank.command;

/**
 * Wrapper for a command that measures the execution time.
 * This class implements the Command interface and provides a constructor for
 * initializing the command with the actual command to execute.
 */
public class TimedCommand<T> implements Command<T> {
  private final Command<T> command;

  /**
   * Constructor for TimedCommand.
   *
   * @param commandParam the command to execute and measure
   */
  public TimedCommand(final Command<T> commandParam) {
    this.command = commandParam;
  }

  /**
   * Executes the command and measures the execution time.
   *
   * @return the result of the command execution
   */
  @Override
  public T execute() {
    long startTime = System.currentTimeMillis();
    T result = command.execute();
    long endTime = System.currentTimeMillis();
    System.out.println("Выполнение команды заняло: " + (endTime - startTime) + " мс");
    return result;
  }
} 