package ru.hse.bank.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a category in the bank system.
 * This class contains information about the category, including its ID, name, and type.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
  private UUID id;
  private String name;
  private CategoryType type;
} 