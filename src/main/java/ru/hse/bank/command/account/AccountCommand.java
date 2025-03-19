package ru.hse.bank.command.account;

import ru.hse.bank.command.Command;
import ru.hse.bank.model.BankAccount;

/**
 * Represents a command for an account.
 * This interface extends the Command interface and specifies the type of the account.
 */
public interface AccountCommand extends Command<BankAccount> {
} 