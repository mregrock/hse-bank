package ru.hse.bank.dataimport;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonDataImporter extends DataImporter {
    private final ObjectMapper objectMapper;

    public JsonDataImporter(BankAccountFacade bankAccountFacade,
                          CategoryFacade categoryFacade,
                          OperationFacade operationFacade) {
        super(bankAccountFacade, categoryFacade, operationFacade);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected String readFile(Path filePath) throws IOException {
        return Files.readString(filePath);
    }

    @Override
    protected List<BankAccount> parseAccounts(String content) throws IOException {
        return objectMapper.readValue(content, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, BankAccount.class));
    }

    @Override
    protected List<Category> parseCategories(String content) throws IOException {
        return objectMapper.readValue(content, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Category.class));
    }

    @Override
    protected List<Operation> parseOperations(String content) throws IOException {
        return objectMapper.readValue(content, objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Operation.class));
    }
} 