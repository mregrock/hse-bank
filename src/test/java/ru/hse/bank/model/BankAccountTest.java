package ru.hse.bank.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void defaultConstructorShouldCreateEmptyBankAccount() {

        BankAccount account = new BankAccount();


        assertNull(account.getId());
        assertNull(account.getName());
        assertNull(account.getBalance());
    }

    @Test
    void constructorShouldInitializeAllFields() {

        UUID id = UUID.randomUUID();
        String name = "Текущий счет";
        BigDecimal balance = new BigDecimal("1000.00");


        BankAccount account = new BankAccount(id, name, balance);


        assertEquals(id, account.getId());
        assertEquals(name, account.getName());
        assertEquals(balance, account.getBalance());
    }

    @Test
    void settersShouldChangeFields() {

        BankAccount account = new BankAccount();
        UUID id = UUID.randomUUID();
        String name = "Накопительный счет";
        BigDecimal balance = new BigDecimal("5000.00");


        account.setId(id);
        account.setName(name);
        account.setBalance(balance);


        assertEquals(id, account.getId());
        assertEquals(name, account.getName());
        assertEquals(balance, account.getBalance());
    }

    @Test
    void builderShouldCreateBankAccount() {

        UUID id = UUID.randomUUID();
        String name = "Карточный счет";
        BigDecimal balance = new BigDecimal("3000.00");


        BankAccount account = BankAccount.builder()
                .id(id)
                .name(name)
                .balance(balance)
                .build();


        assertEquals(id, account.getId());
        assertEquals(name, account.getName());
        assertEquals(balance, account.getBalance());
    }
} 