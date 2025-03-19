package ru.hse.bank.command.operation;

import ru.hse.bank.command.TimedCommand;
import ru.hse.bank.model.Operation;

public class TimedOperationCommand extends TimedCommand<Operation> {
    public TimedOperationCommand(OperationCommand command) {
        super(command);
    }
} 