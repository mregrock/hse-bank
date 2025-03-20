package ru.hse.bank.command.operation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.hse.bank.model.Operation;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimedOperationCommandTest {

    @Mock
    private OperationCommand mockCommand;

    @Mock
    private Operation mockOperation;

    private TimedOperationCommand timedCommand;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        timedCommand = new TimedOperationCommand(mockCommand);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void executeShouldDelegateToWrappedCommand() {
        when(mockCommand.execute()).thenReturn(mockOperation);

        Operation result = timedCommand.execute();

        assertNotNull(result);
        assertSame(mockOperation, result);
        verify(mockCommand, times(1)).execute();
    }

    @Test
    void executeShouldMeasureTime() {
        when(mockCommand.execute()).thenReturn(mockOperation);


        timedCommand.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Выполнение команды заняло:"));
        assertTrue(output.contains("мс"));
    }

    @Test
    void executeShouldWorkWithSlowOperation() throws InterruptedException {

        when(mockCommand.execute()).thenAnswer(invocation -> {

            Thread.sleep(100);
            return mockOperation;
        });


        timedCommand.execute();


        String output = outputStream.toString();
        assertTrue(output.contains("Выполнение команды заняло:"));


        String timeString = output.substring(output.indexOf(":") + 1, output.indexOf("мс")).trim();
        int executionTime = Integer.parseInt(timeString);
        assertTrue(executionTime >= 100, "Execution time should be at least 100ms");
    }
} 