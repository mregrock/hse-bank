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
  
  public BankAccount(UUID id, String name, BigDecimal balance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
  }
  
  public UUID getId() {
    return id;
  }
  
  public void setId(UUID id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public BigDecimal getBalance() {
    return balance;
  }
  
  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
  
  public static BankAccountBuilder builder() {
    return new BankAccountBuilder();
  }
  
  public static class BankAccountBuilder {
    private UUID id;
    private String name;
    private BigDecimal balance;
    
    public BankAccountBuilder id(UUID id) {
      this.id = id;
      return this;
    }
    
    public BankAccountBuilder name(String name) {
      this.name = name;
      return this;
    }
    
    public BankAccountBuilder balance(BigDecimal balance) {
      this.balance = balance;
      return this;
    }
    
    public BankAccount build() {
      return new BankAccount(id, name, balance);
    }
  }
} 