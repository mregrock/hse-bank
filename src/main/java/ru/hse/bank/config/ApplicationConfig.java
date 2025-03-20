package ru.hse.bank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.hse.bank.console.ConsoleApplication;
import ru.hse.bank.dataimport.JsonDataImporter;
import ru.hse.bank.export.DataExporter;
import ru.hse.bank.facade.AnalyticsFacade;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;
import ru.hse.bank.factory.DomainFactory;

/**
 * Configuration class for application components.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Creates a bean for ApplicationComponents.
     *
     * @param bankAccountFacade the bank account facade
     * @param categoryFacade the category facade
     * @param operationFacade the operation facade
     * @param analyticsFacade the analytics facade
     * @param dataExporter the data exporter
     * @param jsonDataImporter the JSON data importer
     * @param domainFactory the domain factory
     * @param objectMapper the Jackson object mapper
     * @return the application components
     */
    @Bean
    public ConsoleApplication.ApplicationComponents applicationComponents(
            BankAccountFacade bankAccountFacade,
            CategoryFacade categoryFacade,
            OperationFacade operationFacade,
            AnalyticsFacade analyticsFacade,
            DataExporter dataExporter,
            JsonDataImporter jsonDataImporter,
            DomainFactory domainFactory,
            ObjectMapper objectMapper) {
        
        return new ConsoleApplication.ApplicationComponents(
                bankAccountFacade,
                categoryFacade,
                operationFacade,
                analyticsFacade,
                dataExporter,
                jsonDataImporter,
                domainFactory,
                objectMapper
        );
    }
}
