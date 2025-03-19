package ru.hse.bank.facade;

import org.springframework.stereotype.Service;
import ru.hse.bank.model.Operation;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.command.operation.CreateOperationCommand;
import ru.hse.bank.command.operation.TimedOperationCommand;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsFacade {
    private final OperationFacade operationFacade;

    public AnalyticsFacade(OperationFacade operationFacade) {
        this.operationFacade = operationFacade;
    }

    public BigDecimal calculateBalanceDifference(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = operationFacade.getOperationsByPeriod(start, end);
        return operations.stream()
                .map(operation -> operation.getType() == CategoryType.INCOME ? 
                    operation.getAmount() : operation.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<UUID, BigDecimal> groupOperationsByCategory(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = operationFacade.getOperationsByPeriod(start, end);
        return operations.stream()
                .collect(Collectors.groupingBy(
                    Operation::getCategoryId,
                    Collectors.reducing(
                        BigDecimal.ZERO,
                        operation -> operation.getType() == CategoryType.INCOME ? 
                            operation.getAmount() : operation.getAmount().negate(),
                        BigDecimal::add
                    )
                ));
    }

    public Map<CategoryType, BigDecimal> groupOperationsByType(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = operationFacade.getOperationsByPeriod(start, end);
        return operations.stream()
                .collect(Collectors.groupingBy(
                    Operation::getType,
                    Collectors.reducing(
                        BigDecimal.ZERO,
                        Operation::getAmount,
                        BigDecimal::add
                    )
                ));
    }
} 