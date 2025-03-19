package ru.hse.bank.command.account;

import ru.hse.bank.command.TimedCommand;
import ru.hse.bank.model.BankAccount;

/**
 * Represents a timed account command.
 * This class extends the TimedCommand class and provides 
 * a constructor for initializing the command.
 */
public class TimedAccountCommand extends TimedCommand<BankAccount> {
  public TimedAccountCommand(AccountCommand command) {
    super(command);
  }
} 