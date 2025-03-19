package ru.hse.bank.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a bank account in the bank system.
 * This class contains information about the bank account, including its ID, name, and balance.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
  private UUID id;
  private String name;
  private BigDecimal balance;
} 