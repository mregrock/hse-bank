package ru.hse.bank.factory;

import org.springframework.stereotype.Component;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;
import ru.hse.bank.model.CategoryType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DomainFactory {

    public BankAccount createBankAccount(String name, BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        return BankAccount.builder()
                .id(UUID.randomUUID())
                .name(name)
                .balance(initialBalance)
                .build();
    }

    public Category createCategory(String name, CategoryType type) {
        return Category.builder()
                .id(UUID.randomUUID())
                .name(name)
                .type(type)
                .build();
    }

    public Operation createOperation(CategoryType type, UUID bankAccountId, 
                                   BigDecimal amount, String description, UUID categoryId) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return Operation.builder()
                .id(UUID.randomUUID())
                .type(type)
                .bankAccountId(bankAccountId)
                .amount(amount)
                .date(LocalDateTime.now())
                .description(description)
                .categoryId(categoryId)
                .build();
    }
} 