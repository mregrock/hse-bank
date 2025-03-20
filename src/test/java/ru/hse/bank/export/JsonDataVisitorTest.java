package ru.hse.bank.export;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JsonDataVisitorTest {
    
    private JsonDataVisitor jsonDataVisitor;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        jsonDataVisitor = new JsonDataVisitor(objectMapper);
    }
    
    @Test
    void visit_BankAccount_ShouldAddToAccounts() throws Exception {
        // Arrange
        UUID accountId = UUID.randomUUID();
        BankAccount account = BankAccount.builder()
                .id(accountId)
                .name("Тестовый счет")
                .balance(new BigDecimal("1000.00"))
                .build();
        
        // Act
        jsonDataVisitor.visit(account);
        String result = jsonDataVisitor.getResult();
        
        // Assert
        JsonNode jsonNode = objectMapper.readTree(result);
        assertTrue(jsonNode.has("accounts"));
        assertEquals(1, jsonNode.get("accounts").size());
        
        JsonNode accountNode = jsonNode.get("accounts").get(0);
        assertEquals(accountId.toString(), accountNode.get("id").asText());
        assertEquals("Тестовый счет", accountNode.get("name").asText());
        assertNotNull(accountNode.get("balance"));
        assertEquals(new BigDecimal("1000.00").stripTrailingZeros(), 
                     new BigDecimal(accountNode.get("balance").asText()).stripTrailingZeros());
    }
    
    @Test
    void visit_Category_ShouldAddToCategories() throws Exception {
        // Arrange
        UUID categoryId = UUID.randomUUID();
        Category category = Category.builder()
                .id(categoryId)
                .name("Зарплата")
                .type(CategoryType.INCOME)
                .build();
        
        // Act
        jsonDataVisitor.visit(category);
        String result = jsonDataVisitor.getResult();
        
        // Assert
        JsonNode jsonNode = objectMapper.readTree(result);
        assertTrue(jsonNode.has("categories"));
        assertEquals(1, jsonNode.get("categories").size());
        
        JsonNode categoryNode = jsonNode.get("categories").get(0);
        assertEquals(categoryId.toString(), categoryNode.get("id").asText());
        assertEquals("Зарплата", categoryNode.get("name").asText());
        assertEquals("INCOME", categoryNode.get("type").asText());
    }
    
    @Test
    void visit_Operation_ShouldAddToOperations() throws Exception {
        // Arrange
        UUID operationId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        
        Operation operation = Operation.builder()
                .id(operationId)
                .type(CategoryType.EXPENSE)
                .bankAccountId(accountId)
                .categoryId(categoryId)
                .amount(new BigDecimal("500.00"))
                .description("Покупка продуктов")
                .date(now)
                .build();
        
        // Act
        jsonDataVisitor.visit(operation);
        String result = jsonDataVisitor.getResult();
        
        // Assert
        JsonNode jsonNode = objectMapper.readTree(result);
        assertTrue(jsonNode.has("operations"));
        assertEquals(1, jsonNode.get("operations").size());
        
        JsonNode operationNode = jsonNode.get("operations").get(0);
        assertEquals(operationId.toString(), operationNode.get("id").asText());
        assertEquals("EXPENSE", operationNode.get("type").asText());
        assertEquals(accountId.toString(), operationNode.get("bankAccountId").asText());
        assertEquals(categoryId.toString(), operationNode.get("categoryId").asText());
        assertEquals(new BigDecimal("500.00").stripTrailingZeros(), 
                     new BigDecimal(operationNode.get("amount").asText()).stripTrailingZeros());
        assertEquals("Покупка продуктов", operationNode.get("description").asText());
        assertNotNull(operationNode.get("date"));
    }
    
    @Test
    void getResult_WithMultipleEntities_ShouldReturnValidJson() throws Exception {
        // Arrange
        BankAccount account = BankAccount.builder()
                .id(UUID.randomUUID())
                .name("Тестовый счет")
                .balance(new BigDecimal("1000.00"))
                .build();
        
        Category category = Category.builder()
                .id(UUID.randomUUID())
                .name("Продукты")
                .type(CategoryType.EXPENSE)
                .build();
        
        Operation operation = Operation.builder()
                .id(UUID.randomUUID())
                .type(CategoryType.EXPENSE)
                .bankAccountId(account.getId())
                .categoryId(category.getId())
                .amount(new BigDecimal("300.00"))
                .description("Покупка в супермаркете")
                .date(LocalDateTime.now())
                .build();
        
        // Act
        jsonDataVisitor.visit(account);
        jsonDataVisitor.visit(category);
        jsonDataVisitor.visit(operation);
        String result = jsonDataVisitor.getResult();
        
        // Assert
        JsonNode jsonNode = objectMapper.readTree(result);
        
        assertTrue(jsonNode.has("accounts"));
        assertTrue(jsonNode.has("categories"));
        assertTrue(jsonNode.has("operations"));
        
        assertEquals(1, jsonNode.get("accounts").size());
        assertEquals(1, jsonNode.get("categories").size());
        assertEquals(1, jsonNode.get("operations").size());
    }
} 