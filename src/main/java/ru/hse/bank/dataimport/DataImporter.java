package ru.hse.bank.dataimport;

import org.springframework.stereotype.Component;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
public abstract class DataImporter {
    protected final BankAccountFacade bankAccountFacade;
    protected final CategoryFacade categoryFacade;
    protected final OperationFacade operationFacade;

    protected DataImporter(BankAccountFacade bankAccountFacade,
                         CategoryFacade categoryFacade,
                         OperationFacade operationFacade) {
        this.bankAccountFacade = bankAccountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
    }

    public final void importData(Path filePath) throws IOException {
        String content = readFile(filePath);
        List<BankAccount> accounts = parseAccounts(content);
        List<Category> categories = parseCategories(content);
        List<Operation> operations = parseOperations(content);

        for (BankAccount account : accounts) {
            bankAccountFacade.createAccount(account.getName(), account.getBalance());
        }

        for (Category category : categories) {
            categoryFacade.createCategory(category.getName(), category.getType());
        }

        for (Operation operation : operations) {
            operationFacade.createOperation(
                operation.getType(),
                operation.getBankAccountId(),
                operation.getAmount(),
                operation.getDescription(),
                operation.getCategoryId()
            );
        }
    }

    protected abstract String readFile(Path filePath) throws IOException;
    protected abstract List<BankAccount> parseAccounts(String content) throws IOException;
    protected abstract List<Category> parseCategories(String content) throws IOException;
    protected abstract List<Operation> parseOperations(String content) throws IOException;
} 