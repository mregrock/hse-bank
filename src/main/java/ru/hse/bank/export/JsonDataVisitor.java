package ru.hse.bank.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;

import java.util.ArrayList;
import java.util.List;

public class JsonDataVisitor implements DataVisitor {
    private final ObjectMapper objectMapper;
    private final List<BankAccount> accounts;
    private final List<Category> categories;
    private final List<Operation> operations;

    public JsonDataVisitor() {
        this.objectMapper = new ObjectMapper();
        this.accounts = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.operations = new ArrayList<>();
    }

    @Override
    public void visit(BankAccount account) {
        accounts.add(account);
    }

    @Override
    public void visit(Category category) {
        categories.add(category);
    }

    @Override
    public void visit(Operation operation) {
        operations.add(operation);
    }

    @Override
    public String getResult() {
        try {
            return objectMapper.writeValueAsString(new ExportData(accounts, categories, operations));
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert data to JSON", e);
        }
    }

    private record ExportData(
        List<BankAccount> accounts,
        List<Category> categories,
        List<Operation> operations
    ) {}
} 