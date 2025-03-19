package ru.hse.bank.facade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.hse.bank.command.operation.CreateOperationCommand;
import ru.hse.bank.command.operation.TimedOperationCommand;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

/**
 * Facade for bank operations analytics.
 * Provides a high-level interface for analyzing financial operations,
 * including balance difference calculation and operations grouping by categories and types.
 */
@Service
public class AnalyticsFacade {
  private final OperationFacade operationFacade;

  public AnalyticsFacade(OperationFacade operationFacade) {
    this.operationFacade = operationFacade;
  }

  /**
   * Calculates the balance difference between two dates.
   *
   * @param start start date of the period
   * @param end end date of the period
   * @return balance difference (positive - increase, negative - decrease)
   */
  public BigDecimal calculateBalanceDifference(LocalDateTime start, LocalDateTime end) {
    List<Operation> operations = operationFacade.getOperationsByPeriod(start, end);
    return operations.stream()
        .map(operation -> operation.getType() == CategoryType.INCOME 
        ? operation.getAmount() : operation.getAmount().negate())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /**
   * Groups operations by categories for the specified period.
   *
   * @param start start date of the period
   * @param end end date of the period
   * @return map where key is category ID, value is sum of operations for that category
   */
  public Map<UUID, BigDecimal> groupOperationsByCategory(LocalDateTime start, LocalDateTime end) {
    List<Operation> operations = operationFacade.getOperationsByPeriod(start, end);
    return operations.stream()
        .collect(Collectors.groupingBy(
          Operation::getCategoryId,
          Collectors.reducing(
            BigDecimal.ZERO,
            operation -> operation.getType() == CategoryType.INCOME 
            ? operation.getAmount() : operation.getAmount().negate(),
            BigDecimal::add
          )
        ));
  }

  /**
   * Groups operations by type (income/expense) for the specified period.
   *
   * @param start start date of the period
   * @param end end date of the period
   * @return map where key is operation type, value is sum of operations for that type
   */
  public Map<CategoryType, BigDecimal> groupOperationsByType(LocalDateTime start,
                                                             LocalDateTime end) {
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