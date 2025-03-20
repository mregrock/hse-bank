package ru.hse.bank.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationFacadeTest {

    @Mock
    private DomainFactory domainFactory;

    @Mock
    private BankAccountFacade bankAccountFacade;

    private OperationFacade operationFacade;

    @BeforeEach
    void setUp() {
        operationFacade = new OperationFacade(domainFactory, bankAccountFacade);
    }

    @Test
    void createOperation_ShouldCreateOperationAndUpdateBalance() {

        UUID bankAccountId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        CategoryType type = CategoryType.INCOME;
        BigDecimal amount = new BigDecimal("100.00");
        String description = "Тестовая операция";

        Operation mockOperation = Operation.builder()
                .id(UUID.randomUUID())
                .type(type)
                .bankAccountId(bankAccountId)
                .categoryId(categoryId)
                .amount(amount)
                .description(description)
                .date(LocalDateTime.now())
                .build();

        when(domainFactory.createOperation(type, bankAccountId, amount, description, categoryId))
                .thenReturn(mockOperation);


        Operation result = operationFacade.createOperation(type, bankAccountId, amount, description, categoryId);


        assertEquals(mockOperation, result);
        verify(domainFactory).createOperation(type, bankAccountId, amount, description, categoryId);
        verify(bankAccountFacade).updateBalance(bankAccountId, mockOperation);
        assertEquals(mockOperation, operationFacade.getOperation(mockOperation.getId()));
    }

    @Test
    void getAllOperations_ShouldReturnAllOperations() {

        Operation operation1 = createAndAddOperation(CategoryType.INCOME, UUID.randomUUID(), 
                new BigDecimal("100.00"), "Зарплата", UUID.randomUUID());
        Operation operation2 = createAndAddOperation(CategoryType.EXPENSE, UUID.randomUUID(), 
                new BigDecimal("50.00"), "Кафе", UUID.randomUUID());


        List<Operation> allOperations = operationFacade.getAllOperations();


        assertEquals(2, allOperations.size());
        assertTrue(allOperations.contains(operation1));
        assertTrue(allOperations.contains(operation2));
    }

    @Test
    void getOperationsByAccount_ShouldReturnOperationsForSpecificAccount() {

        UUID accountId1 = UUID.randomUUID();
        UUID accountId2 = UUID.randomUUID();

        Operation operation1 = createAndAddOperation(CategoryType.INCOME, accountId1, 
                new BigDecimal("100.00"), "Операция 1", UUID.randomUUID());
        Operation operation2 = createAndAddOperation(CategoryType.EXPENSE, accountId1, 
                new BigDecimal("50.00"), "Операция 2", UUID.randomUUID());
        Operation operation3 = createAndAddOperation(CategoryType.EXPENSE, accountId2, 
                new BigDecimal("30.00"), "Операция 3", UUID.randomUUID());


        List<Operation> account1Operations = operationFacade.getOperationsByAccount(accountId1);
        List<Operation> account2Operations = operationFacade.getOperationsByAccount(accountId2);


        assertEquals(2, account1Operations.size());
        assertTrue(account1Operations.contains(operation1));
        assertTrue(account1Operations.contains(operation2));

        assertEquals(1, account2Operations.size());
        assertTrue(account2Operations.contains(operation3));
    }

    @Test
    void getOperationsByCategory_ShouldReturnOperationsForSpecificCategory() {

        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();

        Operation operation1 = createAndAddOperation(CategoryType.INCOME, UUID.randomUUID(), 
                new BigDecimal("100.00"), "Операция 1", categoryId1);
        Operation operation2 = createAndAddOperation(CategoryType.EXPENSE, UUID.randomUUID(), 
                new BigDecimal("50.00"), "Операция 2", categoryId2);
        Operation operation3 = createAndAddOperation(CategoryType.EXPENSE, UUID.randomUUID(), 
                new BigDecimal("30.00"), "Операция 3", categoryId1);


        List<Operation> category1Operations = operationFacade.getOperationsByCategory(categoryId1);
        List<Operation> category2Operations = operationFacade.getOperationsByCategory(categoryId2);


        assertEquals(2, category1Operations.size());
        assertTrue(category1Operations.contains(operation1));
        assertTrue(category1Operations.contains(operation3));

        assertEquals(1, category2Operations.size());
        assertTrue(category2Operations.contains(operation2));
    }

    @Test
    void getOperationsByPeriod_ShouldReturnOperationsInSpecificPeriod() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime tomorrow = now.plusDays(1);

        Operation pastOperation = createAndAddOperationWithDate(CategoryType.INCOME, UUID.randomUUID(), 
                new BigDecimal("100.00"), "Прошлая операция", UUID.randomUUID(), yesterday);
        Operation futureOperation = createAndAddOperationWithDate(CategoryType.EXPENSE, UUID.randomUUID(), 
                new BigDecimal("50.00"), "Будущая операция", UUID.randomUUID(), tomorrow);


        List<Operation> periodOperations = operationFacade.getOperationsByPeriod(yesterday.plusHours(1), tomorrow.minusHours(1));


        assertTrue(periodOperations.isEmpty());

        List<Operation> allOperations = operationFacade.getOperationsByPeriod(yesterday.minusDays(1), tomorrow.plusDays(1));
        assertEquals(2, allOperations.size());
        assertTrue(allOperations.contains(pastOperation));
        assertTrue(allOperations.contains(futureOperation));
    }

    @Test
    void deleteOperation_ShouldRemoveOperationAndCreateReverseOperation() {

        UUID bankAccountId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        Operation operation = createAndAddOperation(CategoryType.INCOME, bankAccountId, 
                new BigDecimal("100.00"), "Операция на удаление", categoryId);

        UUID operationId = operation.getId();

        Operation reverseOperation = Operation.builder()
                .id(UUID.randomUUID())
                .type(CategoryType.EXPENSE)
                .bankAccountId(bankAccountId)
                .categoryId(categoryId)
                .amount(new BigDecimal("100.00"))
                .description("Отмена операции: Операция на удаление")
                .date(LocalDateTime.now())
                .build();

        when(domainFactory.createOperation(
                CategoryType.EXPENSE, 
                bankAccountId, 
                new BigDecimal("100.00"), 
                "Отмена операции: Операция на удаление", 
                categoryId))
                .thenReturn(reverseOperation);


        operationFacade.deleteOperation(operationId);


        assertNull(operationFacade.getOperation(operationId));
        verify(bankAccountFacade).updateBalance(bankAccountId, reverseOperation);
    }

    private Operation createAndAddOperation(CategoryType type, UUID bankAccountId, 
                                           BigDecimal amount, String description, UUID categoryId) {
        Operation operation = Operation.builder()
                .id(UUID.randomUUID())
                .type(type)
                .bankAccountId(bankAccountId)
                .categoryId(categoryId)
                .amount(amount)
                .description(description)
                .date(LocalDateTime.now())
                .build();

        when(domainFactory.createOperation(type, bankAccountId, amount, description, categoryId))
                .thenReturn(operation);

        return operationFacade.createOperation(type, bankAccountId, amount, description, categoryId);
    }

    private Operation createAndAddOperationWithDate(CategoryType type, UUID bankAccountId, 
                                                  BigDecimal amount, String description, 
                                                  UUID categoryId, LocalDateTime date) {
        Operation operation = Operation.builder()
                .id(UUID.randomUUID())
                .type(type)
                .bankAccountId(bankAccountId)
                .categoryId(categoryId)
                .amount(amount)
                .description(description)
                .date(date)
                .build();

        when(domainFactory.createOperation(type, bankAccountId, amount, description, categoryId))
                .thenReturn(operation);

        return operationFacade.createOperation(type, bankAccountId, amount, description, categoryId);
    }
} 