package ru.hse.bank.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    @Test
    void defaultConstructorShouldCreateEmptyOperation() {

        Operation operation = new Operation();


        assertNull(operation.getId());
        assertNull(operation.getType());
        assertNull(operation.getBankAccountId());
        assertNull(operation.getAmount());
        assertNull(operation.getDescription());
        assertNull(operation.getCategoryId());
        assertNull(operation.getDate());
    }

    @Test
    void constructorShouldInitializeAllFields() {

        UUID id = UUID.randomUUID();
        CategoryType type = CategoryType.EXPENSE;
        UUID bankAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("100.00");
        String description = "Покупка продуктов";
        UUID categoryId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();


        Operation operation = new Operation(id, type, bankAccountId, amount, description, categoryId, date);


        assertEquals(id, operation.getId());
        assertEquals(type, operation.getType());
        assertEquals(bankAccountId, operation.getBankAccountId());
        assertEquals(amount, operation.getAmount());
        assertEquals(description, operation.getDescription());
        assertEquals(categoryId, operation.getCategoryId());
        assertEquals(date, operation.getDate());
    }

    @Test
    void settersShouldChangeFields() {

        Operation operation = new Operation();
        UUID id = UUID.randomUUID();
        CategoryType type = CategoryType.INCOME;
        UUID bankAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("500.00");
        String description = "Зарплата";
        UUID categoryId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();


        operation.setId(id);
        operation.setType(type);
        operation.setBankAccountId(bankAccountId);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setCategoryId(categoryId);
        operation.setDate(date);


        assertEquals(id, operation.getId());
        assertEquals(type, operation.getType());
        assertEquals(bankAccountId, operation.getBankAccountId());
        assertEquals(amount, operation.getAmount());
        assertEquals(description, operation.getDescription());
        assertEquals(categoryId, operation.getCategoryId());
        assertEquals(date, operation.getDate());
    }

    @Test
    void builderShouldCreateOperation() {

        UUID id = UUID.randomUUID();
        CategoryType type = CategoryType.EXPENSE;
        UUID bankAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("250.00");
        String description = "Проезд";
        UUID categoryId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();


        Operation operation = Operation.builder()
                .id(id)
                .type(type)
                .bankAccountId(bankAccountId)
                .amount(amount)
                .description(description)
                .categoryId(categoryId)
                .date(date)
                .build();


        assertEquals(id, operation.getId());
        assertEquals(type, operation.getType());
        assertEquals(bankAccountId, operation.getBankAccountId());
        assertEquals(amount, operation.getAmount());
        assertEquals(description, operation.getDescription());
        assertEquals(categoryId, operation.getCategoryId());
        assertEquals(date, operation.getDate());
    }
} 