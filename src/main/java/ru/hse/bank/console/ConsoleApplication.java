package ru.hse.bank.console;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
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
  private final BankAccountFacade bankAccountFacade;
  private final CategoryFacade categoryFacade;
  private final OperationFacade operationFacade;
  private final AnalyticsFacade analyticsFacade;
  private final DataExporter dataExporter;
  private final JsonDataImporter jsonDataImporter;
  private final Scanner scanner;
  private final DomainFactory domainFactory;

  /**
   * Constructor for ConsoleApplication.
   * Initializes the application with the necessary facades and components.
   *
   * @param bankAccountFacade the bank account facade
   * @param categoryFacade the category facade
   */
  public ConsoleApplication(BankAccountFacade bankAccountFacade,
              CategoryFacade categoryFacade,
              OperationFacade operationFacade,
              AnalyticsFacade analyticsFacade,
              DataExporter dataExporter,
              JsonDataImporter jsonDataImporter,
              DomainFactory domainFactory) {
    this.bankAccountFacade = bankAccountFacade;
    this.categoryFacade = categoryFacade;
    this.operationFacade = operationFacade;
    this.analyticsFacade = analyticsFacade;
    this.dataExporter = dataExporter;
    this.jsonDataImporter = jsonDataImporter;
    this.domainFactory = domainFactory;
    this.scanner = new Scanner(System.in);
  }

  /**
   * Runs the console application.
   *
   * @param args command line arguments
   */
  @Override
  public void run(String... args) {
    while (true) {
      showMenu();
      int choice = scanner.nextInt();
      scanner.nextLine();

      try {
        switch (choice) {
          case 1 -> createAccount();
          case 2 -> createCategory();
          case 3 -> createOperation();
          case 4 -> showAnalytics();
          case 5 -> exportData();
          case 6 -> importData();
          case 0 -> {
            System.out.println("До свидания!");
            return;
          }
          default -> System.out.println("Неверный выбор. Попробуйте снова.");
        }
      } catch (Exception e) {
        System.out.println("Ошибка: " + e.getMessage());
        scanner.nextLine();
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
    String name = scanner.nextLine();
    System.out.print("Введите начальный баланс: ");
    BigDecimal balance = scanner.nextBigDecimal();
    scanner.nextLine();

    Command<BankAccount> command = new CreateAccountCommand(domainFactory, name, balance);
    Command<BankAccount> timedCommand = new TimedCommand<>(command);
    BankAccount account = timedCommand.execute();
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
    int typeChoice = scanner.nextInt();
    scanner.nextLine();

    CategoryType type = typeChoice == 1 ? CategoryType.INCOME : CategoryType.EXPENSE;
    categoryFacade.createCategory(name, type);
    System.out.println("Категория создана");
  }

  /**
   * Creates a new operation.
   */
  private void createOperation() {
    System.out.print("Введите ID счета: ");
    final UUID accountId = UUID.fromString(scanner.nextLine());
    System.out.print("Введите ID категории: ");
    final UUID categoryId = UUID.fromString(scanner.nextLine());
    System.out.print("Введите сумму: ");
    final BigDecimal amount = scanner.nextBigDecimal();
    scanner.nextLine();
    System.out.print("Введите описание: ");
    final String description = scanner.nextLine();
    System.out.println("Выберите тип операции:");
    System.out.println("1. Доход");
    System.out.println("2. Расход");
    final int typeChoice = scanner.nextInt();
    scanner.nextLine();

    CategoryType type = typeChoice == 1 ? CategoryType.INCOME : CategoryType.EXPENSE;
    Command<Operation> command = new CreateOperationCommand(
        domainFactory,
        type,
        accountId,
        amount,
        description,
        categoryId
    );
    Command<Operation> timedCommand = new TimedCommand<>(command);
    Operation operation = timedCommand.execute();
    System.out.println("Операция создана: " + operation.getId());
  }

  /**
   * Shows the analytics for a given period.
   */
  private void showAnalytics() {
    System.out.println("Введите период анализа (в формате dd.MM.yyyy HH:mm:ss):");
    System.out.print("Начало: ");
    String startStr = scanner.nextLine();
    System.out.print("Конец: ");
    String endStr = scanner.nextLine();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    LocalDateTime start = LocalDateTime.parse(startStr, formatter);
    LocalDateTime end = LocalDateTime.parse(endStr, formatter);

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
    String path = scanner.nextLine();
    try {
      dataExporter.exportData(Path.of(path), new JsonDataVisitor());
      System.out.println("Данные экспортированы");
    } catch (IOException e) {
      System.out.println("Ошибка при экспорте данных: " + e.getMessage());
    }
  }

  /**
   * Imports data from a file.
   */
  private void importData() {
    System.out.print("Введите путь к файлу для импорта: ");
    String path = scanner.nextLine();
    try {
      jsonDataImporter.importData(Path.of(path));
      System.out.println("Данные импортированы");
    } catch (IOException e) {
      System.out.println("Ошибка при импорте данных: " + e.getMessage());
    }
  }
}