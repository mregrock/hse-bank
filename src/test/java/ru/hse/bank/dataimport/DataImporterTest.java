package ru.hse.bank.dataimport;

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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataImporterTest {

    @TempDir
    Path tempDir;

    @Mock
    private BankAccountFacade bankAccountFacade;

    @Mock
    private CategoryFacade categoryFacade;

    @Mock
    private OperationFacade operationFacade;

    private TestDataImporter dataImporter;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        dataImporter = spy(new TestDataImporter(bankAccountFacade, categoryFacade, operationFacade));
        testFile = tempDir.resolve("test-data.txt");
        Files.writeString(testFile, "Test content");
    }

    @Test
    void importData_ShouldImportAccountsCategoriesAndOperations() throws IOException {
        // Arrange
        String testContent = "Тестовый контент";
        UUID accountId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        BankAccount account = BankAccount.builder()
                .id(accountId)
                .name("Тестовый счет")
                .balance(new BigDecimal("1000.00"))
                .build();

        Category category = Category.builder()
                .id(categoryId)
                .name("Тестовая категория")
                .type(CategoryType.INCOME)
                .build();

        Operation operation = Operation.builder()
                .id(UUID.randomUUID())
                .type(CategoryType.INCOME)
                .bankAccountId(accountId)
                .categoryId(categoryId)
                .amount(new BigDecimal("500.00"))
                .description("Тестовая операция")
                .date(now)
                .build();

        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(account);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        List<Operation> operations = new ArrayList<>();
        operations.add(operation);

        // Настраиваем моки с использованием spy
        doReturn(testContent).when(dataImporter).readFile(any(Path.class));
        doReturn(accounts).when(dataImporter).parseAccounts(testContent);
        doReturn(categories).when(dataImporter).parseCategories(testContent);
        doReturn(operations).when(dataImporter).parseOperations(testContent);

        // Act
        dataImporter.importData(testFile);

        
        verify(bankAccountFacade).createAccount("Тестовый счет", new BigDecimal("1000.00"));
        verify(categoryFacade).createCategory("Тестовая категория", CategoryType.INCOME);
        verify(operationFacade).createOperation(
                CategoryType.INCOME,
                accountId,
                new BigDecimal("500.00"),
                "Тестовая операция",
                categoryId
        );
    }

    
    private static class TestDataImporter extends DataImporter {

        public TestDataImporter(BankAccountFacade bankAccountFacade, 
                               CategoryFacade categoryFacade, 
                               OperationFacade operationFacade) {
            super(bankAccountFacade, categoryFacade, operationFacade);
        }

        @Override
        protected String readFile(Path filePath) throws IOException {
            return ""; 
        }

        @Override
        protected List<BankAccount> parseAccounts(String content) throws IOException {
            return new ArrayList<>(); 
        }

        @Override
        protected List<Category> parseCategories(String content) throws IOException {
            return new ArrayList<>(); 
        }

        @Override
        protected List<Operation> parseOperations(String content) throws IOException {
            return new ArrayList<>(); 
        }
    }
} 