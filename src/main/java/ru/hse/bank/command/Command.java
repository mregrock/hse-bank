package ru.hse.bank.command;

public interface Command<T> {
    T execute();
} 