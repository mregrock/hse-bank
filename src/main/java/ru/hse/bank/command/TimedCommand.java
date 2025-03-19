package ru.hse.bank.command;

import java.time.Duration;
import java.time.Instant;

public class TimedCommand<T> implements Command<T> {
    private final Command<T> command;

    public TimedCommand(Command<T> command) {
        this.command = command;
    }

    @Override
    public T execute() {
        Instant start = Instant.now();
        T result = command.execute();
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Выполнение команды заняло: " + duration.toMillis() + " мс");
        return result;
    }
} 