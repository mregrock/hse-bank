package ru.hse.bank.command.operation;

import ru.hse.bank.model.Operation;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.factory.DomainFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateOperationCommand implements OperationCommand {
    private final DomainFactory domainFactory;
    private final CategoryType type;
    private final UUID bankAccountId;
    private final BigDecimal amount;
    private final String description;
    private final UUID categoryId;

    public CreateOperationCommand(DomainFactory domainFactory,
                                CategoryType type,
                                UUID bankAccountId,
                                BigDecimal amount,
                                String description,
                                UUID categoryId) {
        this.domainFactory = domainFactory;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
    }

    @Override
    public Operation execute() {
        return domainFactory.createOperation(
            type,
            bankAccountId,
            amount,
            description,
            categoryId
        );
    }
} 