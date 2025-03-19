package ru.hse.bank.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.Operation;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.BankAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsFacadeTest {
  private AnalyticsFacade analyticsFacade;
  private OperationFacade operationFacade;
  private BankAccountFacade bankAccountFacade;
  private DomainFactory domainFactory;

  @BeforeEach
  void setUp() {
    domainFactory = new DomainFactory();
    bankAccountFacade = new BankAccountFacade(domainFactory);
    operationFacade = new OperationFacade(domainFactory, bankAccountFacade);
    analyticsFacade = new AnalyticsFacade(operationFacade);
  }

  @Test
  void calculateBalanceDifference_ShouldCalculateCorrectly() {
    String accountName = "Test Account";
    BigDecimal initialBalance = new BigDecimal("1000.00");
    BankAccount account = bankAccountFacade.createAccount(accountName, initialBalance);

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime start = now.minusDays(1);
    LocalDateTime end = now.plusDays(1);

    operationFacade.createOperation(
      CategoryType.INCOME,
      account.getId(),
      new BigDecimal("1000.00"),
      "Test income",
      UUID.randomUUID()
    );

    operationFacade.createOperation(
      CategoryType.EXPENSE,
      account.getId(),
      new BigDecimal("500.00"),
      "Test expense",
      UUID.randomUUID()
    );

    BigDecimal difference = analyticsFacade.calculateBalanceDifference(start, end);

    assertEquals(new BigDecimal("500.00"), difference);
  }

  @Test
  void groupOperationsByCategory_ShouldGroupCorrectly() {
    String accountName = "Test Account";
    BigDecimal initialBalance = new BigDecimal("1000.00");
    BankAccount account = bankAccountFacade.createAccount(accountName, initialBalance);

    UUID categoryId1 = UUID.randomUUID();
    UUID categoryId2 = UUID.randomUUID();
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime start = now.minusDays(1);
    LocalDateTime end = now.plusDays(1);

    operationFacade.createOperation(
      CategoryType.INCOME,
      account.getId(),
      new BigDecimal("1000.00"),
      "Test income 1",
      categoryId1
    );

    operationFacade.createOperation(
      CategoryType.EXPENSE,
      account.getId(),
      new BigDecimal("500.00"),
      "Test expense 1",
      categoryId1
    );

    operationFacade.createOperation(
      CategoryType.INCOME,
      account.getId(),
      new BigDecimal("2000.00"),
      "Test income 2",
      categoryId2
    );

    var result = analyticsFacade.groupOperationsByCategory(start, end);

    assertEquals(2, result.size());
    assertEquals(new BigDecimal("500.00"), result.get(categoryId1));
    assertEquals(new BigDecimal("2000.00"), result.get(categoryId2));
  }

  @Test
  void groupOperationsByType_ShouldGroupCorrectly() {
    String accountName = "Test Account";
    BigDecimal initialBalance = new BigDecimal("1000.00");
    BankAccount account = bankAccountFacade.createAccount(accountName, initialBalance);

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime start = now.minusDays(1);
    LocalDateTime end = now.plusDays(1);

    operationFacade.createOperation(
      CategoryType.INCOME,
      account.getId(),
      new BigDecimal("1000.00"),
      "Test income",
      UUID.randomUUID()
    );

    operationFacade.createOperation(
      CategoryType.EXPENSE,
      account.getId(),
      new BigDecimal("500.00"),
      "Test expense",
      UUID.randomUUID()
    );

    var result = analyticsFacade.groupOperationsByType(start, end);

    assertEquals(2, result.size());
    assertEquals(new BigDecimal("1000.00"), result.get(CategoryType.INCOME));
    assertEquals(new BigDecimal("500.00"), result.get(CategoryType.EXPENSE));
  }
} 