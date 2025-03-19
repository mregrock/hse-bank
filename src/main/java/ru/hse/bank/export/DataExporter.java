package ru.hse.bank.export;

import org.springframework.stereotype.Component;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DataExporter {
    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;

    public DataExporter(BankAccountFacade bankAccountFacade,
                       CategoryFacade categoryFacade,
                       OperationFacade operationFacade) {
        this.bankAccountFacade = bankAccountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
    }

    public void exportData(Path filePath, DataVisitor visitor) throws IOException {
        bankAccountFacade.getAllAccounts().forEach(visitor::visit);
        categoryFacade.getAllCategories().forEach(visitor::visit);
        operationFacade.getAllOperations().forEach(visitor::visit);

        String result = visitor.getResult();
        Files.writeString(filePath, result);
    }
} 