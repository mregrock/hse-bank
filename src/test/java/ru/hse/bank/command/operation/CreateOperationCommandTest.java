package ru.hse.bank.command.operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOperationCommandTest {

    @Mock
    private DomainFactory domainFactory;

    private UUID bankAccountId;
    private UUID categoryId;
    private CategoryType type;
    private BigDecimal amount;
    private String description;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bankAccountId = UUID.randomUUID();
        categoryId = UUID.randomUUID();
        type = CategoryType.EXPENSE;
        amount = new BigDecimal("100.00");
        description = "Test operation";
    }

    @Test
    void executeShouldCreateOperation() {

        Operation expectedOperation = new Operation();
        when(domainFactory.createOperation(type, bankAccountId, amount, description, categoryId))
                .thenReturn(expectedOperation);

        CreateOperationCommand command = new CreateOperationCommand(
                domainFactory, type, bankAccountId, amount, description, categoryId);


        Operation result = command.execute();


        assertNotNull(result);
        assertEquals(expectedOperation, result);
        verify(domainFactory, times(1)).createOperation(type, bankAccountId, amount, description, categoryId);
    }
} 