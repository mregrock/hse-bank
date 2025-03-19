package ru.hse.bank.command.operation;

import ru.hse.bank.command.Command;
import ru.hse.bank.model.Operation;

/**
 * Represents a command that operates on an operation.
 * This interface extends the Command interface and specifies the type of the operation.
 */
public interface OperationCommand extends Command<Operation> {
} 