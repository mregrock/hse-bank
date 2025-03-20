package ru.hse.bank.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimedCommandTest {
    
    @Mock
    private Command<String> mockCommand;
    
    private TimedCommand<String> timedCommand;
    
    @BeforeEach
    void setUp() {
        timedCommand = new TimedCommand<>(mockCommand);
    }
    
    @Test
    void execute_ShouldReturnResultFromWrappedCommand() {
        // Arrange
        String expectedResult = "Command Result";
        when(mockCommand.execute()).thenReturn(expectedResult);
        
        // Act
        String result = timedCommand.execute();
        
        // Assert
        assertEquals(expectedResult, result);
        verify(mockCommand).execute();
    }
} 