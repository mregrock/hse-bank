package ru.hse.bank.export;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataExporterTest {

    @TempDir
    Path tempDir;

    @Mock
    private BankAccountFacade bankAccountFacade;

    @Mock
    private CategoryFacade categoryFacade;

    @Mock
    private OperationFacade operationFacade;

    @Mock
    private DataVisitor dataVisitor;

    private DataExporter dataExporter;
    private Path exportFile;

    @BeforeEach
    void setUp() {
        dataExporter = new DataExporter(bankAccountFacade, categoryFacade, operationFacade);
        exportFile = tempDir.resolve("export-data.txt");
    }

    @Test
    void exportData_ShouldExportAllEntities() throws IOException {
        // Arrange
        BankAccount account1 = BankAccount.builder()
                .id(UUID.randomUUID())
                .name("Счет 1")
                .balance(new BigDecimal("1000.00"))
                .build();

        BankAccount account2 = BankAccount.builder()
                .id(UUID.randomUUID())
                .name("Счет 2")
                .balance(new BigDecimal("2000.00"))
                .build();

        Category category1 = Category.builder()
                .id(UUID.randomUUID())
                .name("Зарплата")
                .type(CategoryType.INCOME)
                .build();

        Category category2 = Category.builder()
                .id(UUID.randomUUID())
                .name("Продукты")
                .type(CategoryType.EXPENSE)
                .build();

        Operation operation1 = Operation.builder()
                .id(UUID.randomUUID())
                .type(CategoryType.INCOME)
                .bankAccountId(account1.getId())
                .categoryId(category1.getId())
                .amount(new BigDecimal("500.00"))
                .description("Зарплата")
                .date(LocalDateTime.now())
                .build();

        Operation operation2 = Operation.builder()
                .id(UUID.randomUUID())
                .type(CategoryType.EXPENSE)
                .bankAccountId(account1.getId())
                .categoryId(category2.getId())
                .amount(new BigDecimal("200.00"))
                .description("Покупка продуктов")
                .date(LocalDateTime.now())
                .build();

        List<BankAccount> accounts = Arrays.asList(account1, account2);
        List<Category> categories = Arrays.asList(category1, category2);
        List<Operation> operations = Arrays.asList(operation1, operation2);

        when(bankAccountFacade.getAllAccounts()).thenReturn(accounts);
        when(categoryFacade.getAllCategories()).thenReturn(categories);
        when(operationFacade.getAllOperations()).thenReturn(operations);
        when(dataVisitor.getResult()).thenReturn("Экспортированные данные");

        // Act
        dataExporter.exportData(exportFile, dataVisitor);

        // Assert
        verify(bankAccountFacade).getAllAccounts();
        verify(categoryFacade).getAllCategories();
        verify(operationFacade).getAllOperations();

        verify(dataVisitor).visit(account1);
        verify(dataVisitor).visit(account2);
        verify(dataVisitor).visit(category1);
        verify(dataVisitor).visit(category2);
        verify(dataVisitor).visit(operation1);
        verify(dataVisitor).visit(operation2);

        verify(dataVisitor).getResult();

        assertTrue(Files.exists(exportFile));
        String content = Files.readString(exportFile);
        assertEquals("Экспортированные данные", content);
    }
} 