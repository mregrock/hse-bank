package ru.hse.bank.command;

/**
 * Represents a command that can be executed and returns a result of type T.
 *
 * @param <T> the type of the result
 */
public interface Command<T> {
  T execute();
} 