package ru.hse.bank.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

/**
 * Facade for bank account operations.
 * Provides a high-level interface for managing bank accounts,
 * including account creation and balance management.
 */
@Service
public class BankAccountFacade {
  private final DomainFactory domainFactory;
  private final Map<UUID, BankAccount> accounts = new HashMap<>();

  /**
   * Constructor for BankAccountFacade.
   *
   * @param domainFactoryParam the domain factory to use for creating bank accounts
   */
  public BankAccountFacade(final DomainFactory domainFactoryParam) {
    this.domainFactory = domainFactoryParam;
  }

  /**
   * Creates a new bank account with the specified name and initial balance.
   *
   * @param name the name of the new account
   * @param initialBalance the initial balance of the new account
   * @return the newly created bank account
   */
  public BankAccount createAccount(final String name, final BigDecimal initialBalance) {
    BankAccount account = domainFactory.createBankAccount(name, initialBalance);
    accounts.put(account.getId(), account);
    return account;
  }

  /**
   * Retrieves a bank account by its ID.
   *
   * @param id the ID of the bank account to retrieve
   * @return the bank account with the specified ID, or null if it does not exist
   */
  public BankAccount getAccount(final UUID id) {
    return accounts.get(id);
  }

  /**
   * Returns a list of all bank accounts.
   *
   * @return list of all bank accounts
   */
  public List<BankAccount> getAllAccounts() {
    return new ArrayList<>(accounts.values());
  }

  /**
   * Deletes a bank account by its ID.
   *
   * @param id the ID of the bank account to delete
   */
  public void deleteAccount(final UUID id) {
    accounts.remove(id);
  }

  /**
   * Updates the balance of an account based on an operation.
   *
   * @param accountId the ID of the account to update
   * @param operation the operation to apply to the account
   * @throws IllegalArgumentException if the account is not found
   * @throws IllegalStateException if the operation would result in a negative balance
   */
  public void updateBalance(final UUID accountId, final Operation operation) {
    BankAccount account = accounts.get(accountId);
    if (account == null) {
      throw new IllegalArgumentException("Счет не найден");
    }

    BigDecimal newBalance = account.getBalance();
    if (operation.getType() == CategoryType.INCOME) {
      newBalance = newBalance.add(operation.getAmount());
    } else {
      newBalance = newBalance.subtract(operation.getAmount());
    }

    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalStateException("Недостаточно средств на счете");
    }

    account.setBalance(newBalance);
  }
} 