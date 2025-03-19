package ru.hse.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an operation in the bank system.
 * This class contains information about the operation, 
 * including its ID, type, bank account ID, amount, description, category ID, and date.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
  private UUID id;
  private CategoryType type;
  private UUID bankAccountId;
  private BigDecimal amount;
  private String description;
  private UUID categoryId;
  private LocalDateTime date;
} 