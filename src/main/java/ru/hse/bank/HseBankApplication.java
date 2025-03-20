package ru.hse.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for HseBank.
 * This class contains the main method and launches the application.
 */
@SpringBootApplication
public class HseBankApplication {
  /**
   * Main method for starting the application.
   *
   * @param args command line arguments
   */
  public static void main(final String[] args) {
    SpringApplication.run(HseBankApplication.class, args);
  }
} 