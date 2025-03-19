package ru.hse.bank.command.account;

import ru.hse.bank.model.BankAccount;
import ru.hse.bank.factory.DomainFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateAccountCommand implements AccountCommand {
    private final DomainFactory domainFactory;
    private final String name;
    private final BigDecimal balance;

    public CreateAccountCommand(DomainFactory domainFactory, String name, BigDecimal balance) {
        this.domainFactory = domainFactory;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public BankAccount execute() {
        return domainFactory.createBankAccount(name, balance);
    }
} 