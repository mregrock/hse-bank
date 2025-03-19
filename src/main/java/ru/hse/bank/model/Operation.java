package ru.hse.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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