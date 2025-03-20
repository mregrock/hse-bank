package ru.hse.bank.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DomainFactoryTest {

    private DomainFactory domainFactory;

    @BeforeEach
    void setUp() {
        domainFactory = new DomainFactory();
    }

    @Test
    void createBankAccount_ValidData_ShouldCreateAccount() {

        String name = "Тестовый счет";
        BigDecimal balance = new BigDecimal("1000.00");


        BankAccount account = domainFactory.createBankAccount(name, balance);


        assertNotNull(account);
        assertNotNull(account.getId());
        assertEquals(name, account.getName());
        assertEquals(balance, account.getBalance());
    }

    @Test
    void createBankAccount_NegativeBalance_ShouldThrowException() {

        final String name = "Test Account";
        final BigDecimal negativeBalance = new BigDecimal("-100");


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            domainFactory.createBankAccount(name, negativeBalance)
        );


        assertEquals("Начальный баланс не может быть отрицательным", exception.getMessage());
    }

    @Test
    void createCategory_ValidData_ShouldCreateCategory() {

        String name = "Тестовая категория";
        CategoryType type = CategoryType.EXPENSE;


        Category category = domainFactory.createCategory(name, type);


        assertNotNull(category);
        assertNotNull(category.getId());
        assertEquals(name, category.getName());
        assertEquals(type, category.getType());
    }

    @Test
    void createOperation_ValidData_ShouldCreateOperation() {

        CategoryType type = CategoryType.INCOME;
        UUID bankAccountId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("500.00");
        String description = "Тестовая операция";


        Operation operation = domainFactory.createOperation(type, bankAccountId, amount, description, categoryId);


        assertNotNull(operation);
        assertNotNull(operation.getId());
        assertEquals(type, operation.getType());
        assertEquals(bankAccountId, operation.getBankAccountId());
        assertEquals(categoryId, operation.getCategoryId());
        assertEquals(amount, operation.getAmount());
        assertEquals(description, operation.getDescription());
        assertNotNull(operation.getDate());
    }

    @Test
    void createOperation_ZeroAmount_ShouldThrowException() {

        final CategoryType type = CategoryType.INCOME;
        final UUID bankAccountId = UUID.randomUUID();
        final BigDecimal zeroAmount = BigDecimal.ZERO;
        final String description = "Test Operation";
        final UUID categoryId = UUID.randomUUID();


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            domainFactory.createOperation(type, bankAccountId, zeroAmount, description, categoryId)
        );


        assertEquals("Сумма операции должна быть положительной", exception.getMessage());
    }

    @Test
    void createOperation_NegativeAmount_ShouldThrowException() {

        final CategoryType type = CategoryType.INCOME;
        final UUID bankAccountId = UUID.randomUUID();
        final BigDecimal negativeAmount = new BigDecimal("-100");
        final String description = "Test Operation";
        final UUID categoryId = UUID.randomUUID();


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            domainFactory.createOperation(type, bankAccountId, negativeAmount, description, categoryId)
        );


        assertEquals("Сумма операции должна быть положительной", exception.getMessage());
    }
} 