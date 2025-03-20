package ru.hse.bank.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Operation;
import ru.hse.bank.model.CategoryType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountFacadeTest {
  private BankAccountFacade bankAccountFacade;
  private DomainFactory domainFactory;

  @BeforeEach
  void setUp() {
    domainFactory = new DomainFactory();
    bankAccountFacade = new BankAccountFacade(domainFactory);
  }

  @Test
  void createAccount_ValidData_ShouldCreateAccount() {
    String name = "Test Account";
    BigDecimal initialBalance = new BigDecimal("1000.00");

    BankAccount account = bankAccountFacade.createAccount(name, initialBalance);

    assertNotNull(account);
    assertEquals(name, account.getName());
    assertEquals(initialBalance, account.getBalance());
  }

  @Test
  void createAccount_NegativeBalance_ShouldThrowException() {
    String name = "Test Account";
    BigDecimal negativeBalance = new BigDecimal("-1000.00");

    assertThrows(IllegalArgumentException.class, () -> 
      bankAccountFacade.createAccount(name, negativeBalance));
  }

  @Test
  void updateBalance_IncomeOperation_ShouldIncreaseBalance() {
    BankAccount account = bankAccountFacade.createAccount("Test Account", new BigDecimal("1000.00"));
    Operation operation = domainFactory.createOperation(
      CategoryType.INCOME,
      account.getId(),
      new BigDecimal("500.00"),
      "Test income",
      UUID.randomUUID()
    );

    bankAccountFacade.updateBalance(account.getId(), operation);

    assertEquals(new BigDecimal("1500.00"), account.getBalance());
  }

  @Test
  void updateBalance_ExpenseOperation_ShouldDecreaseBalance() {
    BankAccount account = bankAccountFacade.createAccount("Test Account", new BigDecimal("1000.00"));
    Operation operation = domainFactory.createOperation(
      CategoryType.EXPENSE,
      account.getId(),
      new BigDecimal("300.00"),
      "Test expense",
      UUID.randomUUID()
    );

    bankAccountFacade.updateBalance(account.getId(), operation);

    assertEquals(new BigDecimal("700.00"), account.getBalance());
  }

  @Test
  void updateBalance_AccountNotFound_ShouldThrowException() {
    UUID nonExistentAccountId = UUID.randomUUID();
    Operation operation = Operation.builder()
        .id(UUID.randomUUID())
        .type(CategoryType.INCOME)
        .amount(BigDecimal.TEN)
        .build();

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        bankAccountFacade.updateBalance(nonExistentAccountId, operation));
    assertEquals("Счет не найден", exception.getMessage());
  }

  @Test
  void updateBalance_InsufficientFunds_ShouldThrowException() {
    BigDecimal initialBalance = BigDecimal.TEN;
    BankAccount account = bankAccountFacade.createAccount("Test Account", initialBalance);
    BigDecimal amount = new BigDecimal("20");
    Operation operation = Operation.builder()
        .id(UUID.randomUUID())
        .type(CategoryType.EXPENSE)
        .amount(amount)
        .build();

    IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
        bankAccountFacade.updateBalance(account.getId(), operation));
    assertEquals("Недостаточно средств на счете", exception.getMessage());
  }
} 