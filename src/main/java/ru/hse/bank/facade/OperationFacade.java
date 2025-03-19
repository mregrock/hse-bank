package ru.hse.bank.facade;

import org.springframework.stereotype.Service;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.Operation;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.command.operation.CreateOperationCommand;
import ru.hse.bank.command.operation.TimedOperationCommand;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperationFacade {
    private final DomainFactory domainFactory;
    private final BankAccountFacade bankAccountFacade;
    private final Map<UUID, Operation> operations = new HashMap<>();

    public OperationFacade(DomainFactory domainFactory, BankAccountFacade bankAccountFacade) {
        this.domainFactory = domainFactory;
        this.bankAccountFacade = bankAccountFacade;
    }

    public Operation createOperation(CategoryType type, UUID bankAccountId, 
                                   BigDecimal amount, String description, UUID categoryId) {
        Operation operation = domainFactory.createOperation(type, bankAccountId, amount, description, categoryId);
        bankAccountFacade.updateBalance(bankAccountId, operation);
        operations.put(operation.getId(), operation);
        return operation;
    }

    public Operation getOperation(UUID id) {
        return operations.get(id);
    }

    public List<Operation> getAllOperations() {
        return new ArrayList<>(operations.values());
    }

    public List<Operation> getOperationsByAccount(UUID bankAccountId) {
        return operations.values().stream()
                .filter(operation -> operation.getBankAccountId().equals(bankAccountId))
                .collect(Collectors.toList());
    }

    public List<Operation> getOperationsByCategory(UUID categoryId) {
        return operations.values().stream()
                .filter(operation -> operation.getCategoryId().equals(categoryId))
                .collect(Collectors.toList());
    }

    public List<Operation> getOperationsByPeriod(LocalDateTime start, LocalDateTime end) {
        return operations.values().stream()
                .filter(operation -> operation.getDate().isAfter(start) && operation.getDate().isBefore(end))
                .collect(Collectors.toList());
    }

    public void deleteOperation(UUID id) {
        Operation operation = operations.get(id);
        if (operation != null) {
            Operation reverseOperation = domainFactory.createOperation(
                operation.getType() == CategoryType.INCOME ? CategoryType.EXPENSE : CategoryType.INCOME,
                operation.getBankAccountId(),
                operation.getAmount(),
                "Отмена операции: " + operation.getDescription(),
                operation.getCategoryId()
            );
            bankAccountFacade.updateBalance(operation.getBankAccountId(), reverseOperation);
            operations.remove(id);
        }
    }
} 