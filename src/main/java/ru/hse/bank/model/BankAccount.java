package ru.hse.bank.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a bank account in the bank system.
 * This class contains information about the bank account, including its ID, name, and balance.
 */
public class BankAccount {
  private UUID id;
  private String name;
  private BigDecimal balance;

  public BankAccount() {
  }

  /**
   * Constructor for BankAccount.
   *
   * @param idParam the ID of the bank account
   * @param nameParam the name of the bank account
   * @param balanceParam the balance of the bank account
   */
  public BankAccount(final UUID idParam, final String nameParam, final BigDecimal balanceParam) {
    this.id = idParam;
    this.name = nameParam;
    this.balance = balanceParam;
  }

  public UUID getId() {
    return id;
  }

  public void setId(final UUID idParam) {
    this.id = idParam;
  }

  public String getName() {
    return name;
  }

  public void setName(final String nameParam) {
    this.name = nameParam;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(final BigDecimal balanceParam) {
    this.balance = balanceParam;
  }

  public static BankAccountBuilder builder() {
    return new BankAccountBuilder();
  }

  public static class BankAccountBuilder {
    private UUID id;
    private String name;
    private BigDecimal balance;

    public BankAccountBuilder id(final UUID idParam) {
      this.id = idParam;
      return this;
    }

    public BankAccountBuilder name(final String nameParam) {
      this.name = nameParam;
      return this;
    }

    public BankAccountBuilder balance(final BigDecimal balanceParam) {
      this.balance = balanceParam;
      return this;
    }

    public BankAccount build() {
      return new BankAccount(id, name, balance);
    }
  }
} 