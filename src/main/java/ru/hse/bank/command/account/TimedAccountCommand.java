package ru.hse.bank.command.account;

import ru.hse.bank.command.TimedCommand;
import ru.hse.bank.model.BankAccount;

public class TimedAccountCommand extends TimedCommand<BankAccount> {
    public TimedAccountCommand(AccountCommand command) {
        super(command);
    }
} 