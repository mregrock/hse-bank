package ru.hse.bank.console;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ru.hse.bank.command.Command;
import ru.hse.bank.command.TimedCommand;
import ru.hse.bank.command.account.CreateAccountCommand;
import ru.hse.bank.command.operation.CreateOperationCommand;
import ru.hse.bank.dataimport.JsonDataImporter;
import ru.hse.bank.export.DataExporter;
import ru.hse.bank.export.JsonDataVisitor;
import ru.hse.bank.facade.AnalyticsFacade;
import ru.hse.bank.facade.BankAccountFacade;
import ru.hse.bank.facade.CategoryFacade;
import ru.hse.bank.facade.OperationFacade;
import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.CategoryType;
import ru.hse.bank.model.Operation;

/**
 * Represents a console application that allows users to interact with the HseBank system.
 * This class implements the CommandLineRunner interface and provides a constructor for 
 * initializing the application with the necessary facades and components.
 */
@Component
public class ConsoleApplication implements CommandLineRunner {

  /**
   * A class that combines all application components.
   * Used to reduce the number of parameters in the constructor.
   */
  public static class ApplicationComponents {
    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;
    private final AnalyticsFacade analyticsFacade;
    private final DataExporter dataExporter;
    private final JsonDataImporter jsonDataImporter;
    private final DomainFactory domainFactory;
    private final ObjectMapper objectMapper;

    /**
     * Constructor for the inner class ApplicationComponents.
     *
     * @param bankAccountFacadeParam the bank account facade
     * @param categoryFacadeParam the category facade
     * @param operationFacadeParam the operation facade
     * @param analyticsFacadeParam the analytics facade
     * @param dataExporterParam the data exporter
     * @param jsonDataImporterParam the JSON data importer
     * @param domainFactoryParam the domain factory
     * @param objectMapperParam the Jackson object mapper
     */
    public ApplicationComponents(final BankAccountFacade bankAccountFacadeParam, 
                                final CategoryFacade categoryFacadeParam,
                                final OperationFacade operationFacadeParam,
                                final AnalyticsFacade analyticsFacadeParam,
                                final DataExporter dataExporterParam,
                                final JsonDataImporter jsonDataImporterParam,
                                final DomainFactory domainFactoryParam,
                                final ObjectMapper objectMapperParam) {
      this.bankAccountFacade = bankAccountFacadeParam;
      this.categoryFacade = categoryFacadeParam;
      this.operationFacade = operationFacadeParam;
      this.analyticsFacade = analyticsFacadeParam;
      this.dataExporter = dataExporterParam;
      this.jsonDataImporter = jsonDataImporterParam;
      this.domainFactory = domainFactoryParam;
      this.objectMapper = objectMapperParam;
    }
  }

  private final BankAccountFacade bankAccountFacade;
  private final CategoryFacade categoryFacade;
  private final OperationFacade operationFacade;
  private final AnalyticsFacade analyticsFacade;
  private final DataExporter dataExporter;
  private final JsonDataImporter jsonDataImporter;
  private final DomainFactory domainFactory;
  private final ObjectMapper objectMapper;
  private final Scanner scanner;

  /**
   * Constructor for ConsoleApplication.
   * Initializes the application with the necessary facades and components.
   *
   * @param components the application components
   */
  public ConsoleApplication(final ApplicationComponents components) {
    this.bankAccountFacade = components.bankAccountFacade;
    this.categoryFacade = components.categoryFacade;
    this.operationFacade = components.operationFacade;
    this.analyticsFacade = components.analyticsFacade;
    this.dataExporter = components.dataExporter;
    this.jsonDataImporter = components.jsonDataImporter;
    this.domainFactory = components.domainFactory;
    this.objectMapper = components.objectMapper;
    this.scanner = new Scanner(System.in);
  }

  /**
   * Runs the console application.
   *
   * @param args command line arguments
   */
  @Override
  public void run(final String... args) {
    while (true) {
      showMenu();
      String choice = scanner.nextLine();
      switch (choice) {
        case "1":
          createAccount();
          break;
        case "2":
          createCategory();
          break;
        case "3":
          createOperation();
          break;
        case "4":
          showAnalytics();
          break;
        case "5":
          exportData();
          break;
        case "6":
          importData();
          break;
        case "0":
          System.out.println("До свидания!");
          return;
        default:
          System.out.println("Неверный выбор. Попробуйте снова.");
      }
    }
  }

  /**
   * Displays the main menu.
   */
  private void showMenu() {
    System.out.println("\n=== ВШЭ-Банк: Модуль учета финансов ===");
    System.out.println("1. Создать счет");
    System.out.println("2. Создать категорию");
    System.out.println("3. Создать операцию");
    System.out.println("4. Показать аналитику");
    System.out.println("5. Экспортировать данные");
    System.out.println("6. Импортировать данные");
    System.out.println("0. Выход");
    System.out.print("Выберите действие: ");
  }

  /**
   * Creates a new bank account.
   */
  private void createAccount() {
    System.out.print("Введите название счета: ");
    final String name = scanner.nextLine();
    System.out.print("Введите начальный баланс: ");
    final BigDecimal balance = scanner.nextBigDecimal();
    scanner.nextLine();

    final Command<BankAccount> command = new CreateAccountCommand(domainFactory, name, balance);
    final Command<BankAccount> timedCommand = new TimedCommand<>(command);
    final BankAccount account = timedCommand.execute();

    bankAccountFacade.createAccount(name, balance);

    System.out.println("Счет создан: " + account.getId());
  }

  /**
   * Creates a new category.
   */
  private void createCategory() {
    System.out.print("Введите название категории: ");
    final String name = scanner.nextLine();
    System.out.println("Выберите тип категории:");
    System.out.println("1. Доход");
    System.out.println("2. Расход");
    final int typeChoice = scanner.nextInt();
    scanner.nextLine();

    final CategoryType type = typeChoice == 1 ? CategoryType.INCOME : CategoryType.EXPENSE;

    final Category category = categoryFacade.createCategory(name, type);
    System.out.println("Категория создана: " + category.getId());
  }

  /**
   * Creates a new operation.
   */
  private void createOperation() {
    try {
      System.out.println("Доступные счета:");
      bankAccountFacade.getAllAccounts().forEach(account -> 
          System.out.println(account.getId() + " - " + account.getName()));

      System.out.print("Введите ID счета: ");
      final String accountIdStr = scanner.nextLine();
      final UUID accountId;
      try {
        accountId = UUID.fromString(accountIdStr);
      } catch (IllegalArgumentException exception) {
        System.out.println("Ошибка: Некорректный формат UUID для счета. " 
            + "Пример правильного формата: f9e3f56d-09f1-4109-8443-36d0dc7a06ce");
        return;
      }

      System.out.println("Доступные категории:");
      categoryFacade.getAllCategories().forEach(category -> 
          System.out.println(category.getId() + " - " 
              + category.getName() + " (" + category.getType() + ")"));

      System.out.print("Введите ID категории: ");
      final String categoryIdStr = scanner.nextLine();
      final UUID categoryId;
      try {
        categoryId = UUID.fromString(categoryIdStr);
      } catch (IllegalArgumentException exception) {
        System.out.println("Ошибка: Некорректный формат UUID для категории. "
            + "Пример правильного формата: f9e3f56d-09f1-4109-8443-36d0dc7a06ce");
        return;
      }

      
      final Category category = categoryFacade.getCategory(categoryId);
      if (category == null) {
        System.out.println("Ошибка: Категория с указанным ID не найдена.");
        return;
      }

      System.out.print("Введите сумму: ");
      final BigDecimal amount;
      try {
        amount = scanner.nextBigDecimal();
        scanner.nextLine(); 
      } catch (Exception exception) {
        System.out.println("Ошибка: Неверный формат числа для суммы. "
            + "Используйте десятичный формат (например, 100.50)");
        scanner.nextLine(); 
        return;
      }

      System.out.print("Введите описание: ");
      final String description = scanner.nextLine();

      
      final CategoryType type = category.getType();
      System.out.println("Тип операции (на основе выбранной категории): " + type);

      try {
        final Command<Operation> command = new CreateOperationCommand(
            domainFactory,
            type,
            accountId,
            amount,
            description,
            categoryId
        );
        final Command<Operation> timedCommand = new TimedCommand<>(command);
        final Operation operation = timedCommand.execute();
        System.out.println("Операция создана: " + operation.getId());
      } catch (Exception exception) {
        System.out.println("Ошибка при создании операции: " + exception.getMessage());
      }
    } catch (Exception exception) {
      System.out.println("Произошла неожиданная ошибка: " + exception.getMessage());
      scanner.nextLine(); 
    }
  }

  /**
   * Shows the analytics for a given period.
   */
  private void showAnalytics() {
    System.out.println("Введите период анализа (в формате dd.MM.yyyy HH:mm:ss):");
    System.out.print("Начало: ");
    final String startStr = scanner.nextLine();
    System.out.print("Конец: ");
    final String endStr = scanner.nextLine();

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    final LocalDateTime start = LocalDateTime.parse(startStr, formatter);
    final LocalDateTime end = LocalDateTime.parse(endStr, formatter);

    final BigDecimal balanceDiff = analyticsFacade.calculateBalanceDifference(start, end);
    final Map<UUID, BigDecimal> byCategory = analyticsFacade.groupOperationsByCategory(start, end);
    final Map<CategoryType, BigDecimal> byType = analyticsFacade.groupOperationsByType(start, end);

    System.out.println("\n=== Аналитика ===");
    System.out.println("Разница баланса: " + balanceDiff);
    System.out.println("\nПо категориям:");
    byCategory.forEach((categoryId, amount) -> 
         System.out.println("Категория " + categoryId + ": " + amount));
    System.out.println("\nПо типам:");
    byType.forEach((type, amount) -> 
         System.out.println(type + ": " + amount));
  }

  /**
   * Exports data to a file.
   */
  private void exportData() {
    System.out.print("Введите путь для экспорта: ");
    final String inputPath = scanner.nextLine();

    
    final String path;
    if (inputPath.startsWith("~")) {
      final String homePath = System.getProperty("user.home");
      path = homePath + inputPath.substring(1);
    } else {
      path = inputPath;
    }

    try {
      final Path filePath = Path.of(path);
      
      final Path parent = filePath.getParent();
      if (parent != null) {
        java.nio.file.Files.createDirectories(parent);
      }

      dataExporter.exportData(filePath, new JsonDataVisitor(objectMapper));
      System.out.println("Данные экспортированы в: " + filePath.toAbsolutePath());
    } catch (IOException exception) {
      System.out.println("Ошибка при экспорте данных: " + exception.getMessage());
    }
  }

  /**
   * Imports data from a file.
   */
  private void importData() {
    System.out.print("Введите путь к файлу для импорта: ");
    final String inputPath = scanner.nextLine();

    
    final String path;
    if (inputPath.startsWith("~")) {
      final String homePath = System.getProperty("user.home");
      path = homePath + inputPath.substring(1);
    } else {
      path = inputPath;
    }

    try {
      final Path filePath = Path.of(path);
      jsonDataImporter.importData(filePath);
      System.out.println("Данные импортированы из: " + filePath.toAbsolutePath());
    } catch (IOException exception) {
      System.out.println("Ошибка при импорте данных: " + exception.getMessage());
    }
  }
}
