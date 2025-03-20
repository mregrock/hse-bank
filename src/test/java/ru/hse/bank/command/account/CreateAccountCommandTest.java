package ru.hse.bank.command.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.hse.bank.factory.DomainFactory;
import ru.hse.bank.model.BankAccount;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateAccountCommandTest {

    @Mock
    private DomainFactory domainFactory;

    @Mock
    private BankAccount mockBankAccount;

    private String accountName;
    private BigDecimal initialBalance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        accountName = "Test Account";
        initialBalance = new BigDecimal("1000.00");
    }

    @Test
    void executeShouldCreateBankAccount() {

        when(domainFactory.createBankAccount(accountName, initialBalance))
                .thenReturn(mockBankAccount);

        CreateAccountCommand command = new CreateAccountCommand(
                domainFactory, accountName, initialBalance);


        BankAccount result = command.execute();


        assertNotNull(result);
        assertSame(mockBankAccount, result);
        verify(domainFactory, times(1)).createBankAccount(accountName, initialBalance);
    }
} 