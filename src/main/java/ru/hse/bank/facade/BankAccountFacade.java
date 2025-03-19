package ru.hse.bank.facade;

import org.springframework.stereotype.Service;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Operation;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.command.account.CreateAccountCommand;
import ru.hse.bank.command.account.TimedAccountCommand;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BankAccountFacade {
    private final DomainFactory domainFactory;
    private final Map<UUID, BankAccount> accounts = new HashMap<>();

    public BankAccountFacade(DomainFactory domainFactory) {
        this.domainFactory = domainFactory;
    }

    public BankAccount createAccount(String name, BigDecimal initialBalance) {
        BankAccount account = domainFactory.createBankAccount(name, initialBalance);
        accounts.put(account.getId(), account);
        return account;
    }

    public BankAccount getAccount(UUID id) {
        return accounts.get(id);
    }

    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public void deleteAccount(UUID id) {
        accounts.remove(id);
    }

    public void updateBalance(UUID accountId, Operation operation) {
        BankAccount account = accounts.get(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        BigDecimal newBalance = account.getBalance();
        if (operation.getType() == CategoryType.INCOME) {
            newBalance = newBalance.add(operation.getAmount());
        } else {
            newBalance = newBalance.subtract(operation.getAmount());
        }

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }

        account.setBalance(newBalance);
    }
} 