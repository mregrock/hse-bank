package ru.hse.bank.model;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void defaultConstructorShouldCreateEmptyCategory() {

        Category category = new Category();


        assertNull(category.getId());
        assertNull(category.getName());
        assertNull(category.getType());
    }

    @Test
    void constructorShouldInitializeAllFields() {

        UUID id = UUID.randomUUID();
        String name = "Продукты";
        CategoryType type = CategoryType.EXPENSE;


        Category category = new Category(id, name, type);


        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(type, category.getType());
    }

    @Test
    void settersShouldChangeFields() {

        Category category = new Category();
        UUID id = UUID.randomUUID();
        String name = "Зарплата";
        CategoryType type = CategoryType.INCOME;


        category.setId(id);
        category.setName(name);
        category.setType(type);


        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(type, category.getType());
    }

    @Test
    void builderShouldCreateCategory() {

        UUID id = UUID.randomUUID();
        String name = "Транспорт";
        CategoryType type = CategoryType.EXPENSE;


        Category category = Category.builder()
                .id(id)
                .name(name)
                .type(type)
                .build();


        assertEquals(id, category.getId());
        assertEquals(name, category.getName());
        assertEquals(type, category.getType());
    }
} 