package ru.hse.bank.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTypeTest {

    @Test
    void shouldHaveExpectedValues() {

        assertEquals(2, CategoryType.values().length);
        assertEquals(CategoryType.INCOME, CategoryType.valueOf("INCOME"));
        assertEquals(CategoryType.EXPENSE, CategoryType.valueOf("EXPENSE"));
    }

    @Test
    void shouldConvertToString() {

        assertEquals("INCOME", CategoryType.INCOME.toString());
        assertEquals("EXPENSE", CategoryType.EXPENSE.toString());
    }
} 