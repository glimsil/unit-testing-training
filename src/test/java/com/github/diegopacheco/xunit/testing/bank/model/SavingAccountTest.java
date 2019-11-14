package com.github.diegopacheco.xunit.testing.bank.model;

import com.github.diegopacheco.xunit.testing.bank.exception.InitialValueException;
import com.github.diegopacheco.xunit.testing.bank.exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingAccountTest {

    SavingAccount defaultAccount;
    SavingAccount defaultAccountTo;
    CheckingAccount defaultCheckingAccountTo;

    @BeforeEach
    public void before() throws InitialValueException {
        defaultAccount = new SavingAccount(new BigDecimal(1000D));
        defaultAccountTo = new SavingAccount(new BigDecimal(1000D));
        defaultCheckingAccountTo = new CheckingAccount(new BigDecimal(1000D));
    }

    @Test
    public void createNewCheckingAccountWithInitialValue500Test() throws InitialValueException {
        SavingAccount checkingAccount = new SavingAccount(new BigDecimal(500D));

        assertTrue(BigDecimal.valueOf(500).compareTo(checkingAccount.getAmount()) == 0 );
    }

    @Test
    public void createNewCheckingAccountWithInitialValueTooLongTest() throws InitialValueException {
        CheckingAccount checkingAccount = new CheckingAccount(new BigDecimal("100000000000000000000000"));
        assertTrue(new BigDecimal("100000000000000000000000").compareTo(checkingAccount.getAmount()) == 0 );
    }

    @Test
    public void withdrawValidAmount500Test() throws InitialValueException, InvalidAmountException {
        BigDecimal amount = defaultAccount.withdraw(BigDecimal.valueOf(500));
        assertTrue(BigDecimal.valueOf(490).compareTo(amount) == 0); // 2% taxes
        assertTrue(BigDecimal.valueOf(500).compareTo(defaultAccount.getAmount()) == 0 );
    }

    @Test
    public void withdrawNegativeAmount500Test() throws InitialValueException, InvalidAmountException {
        assertThrows(InvalidAmountException.class,
                ()->{
                    BigDecimal amount = defaultAccount.withdraw(BigDecimal.valueOf(-500));
                });
    }

    @Test
    public void transferValidAmount500Test() throws InitialValueException, InvalidAmountException {
        BigDecimal amount = defaultAccount.transfer(defaultAccountTo, BigDecimal.valueOf(500));
        assertTrue(BigDecimal.valueOf(475).compareTo(amount) == 0); // 5% taxes
        assertTrue(BigDecimal.valueOf(500).compareTo(defaultAccount.getAmount()) == 0 );
        assertTrue(BigDecimal.valueOf(1475).compareTo(defaultAccountTo.getAmount()) == 0 ); // get money after 5% taxes
    }

    @Test
    public void transferValidAmountToCheckingAccount500Test() throws InitialValueException, InvalidAmountException {
        BigDecimal amount = defaultAccount.transfer(defaultCheckingAccountTo, BigDecimal.valueOf(500));
        assertTrue(BigDecimal.valueOf(465).compareTo(amount) == 0); // 7% taxes
        assertTrue(BigDecimal.valueOf(500).compareTo(defaultAccount.getAmount()) == 0 );
        assertTrue(BigDecimal.valueOf(1465).compareTo(defaultCheckingAccountTo.getAmount()) == 0 ); // get money after 5% taxes
    }

    @Test
    public void createNewCheckingAccountWithInitialValue50ShouldThrowErrorTest() throws InitialValueException {
        assertThrows(InitialValueException.class,
                ()->{
                    CheckingAccount checkingAccount = new CheckingAccount(new BigDecimal(50D));
                });
    }

    @Test()
    public void createNewCheckingAccountWithInitialValueNegativeShouldThrowErrorTest() throws InitialValueException {
        assertThrows(InitialValueException.class,
                ()->{
                    CheckingAccount checkingAccount = new CheckingAccount(new BigDecimal(-50D));
                });
    }

    @Test
    public void createNewCheckingAccountWithInitialValueWithCentsTest() throws InitialValueException {
        CheckingAccount checkingAccount = new CheckingAccount(new BigDecimal("100.5"));
        assertTrue(new BigDecimal("100.5").compareTo(checkingAccount.getAmount()) == 0 );
    }

    @Test
    public void createNewCheckingAccountWithInitialValueWithCentsLessThan100Test() {
        assertThrows(InitialValueException.class,
                ()->{
                    CheckingAccount checkingAccount = new CheckingAccount(new BigDecimal("10.5"));
                });
    }

    @Test
    public void depositNegativeAmountTest() {
        assertThrows(InvalidAmountException.class,
                ()->{
                    defaultAccount.deposit(BigDecimal.valueOf(-100));
                });
    }

    @Test
    public void deposit100AmountTest() throws InvalidAmountException {
        defaultAccount.deposit(BigDecimal.valueOf(100));
        assertTrue(new BigDecimal("1100").compareTo(defaultAccount.getAmount()) == 0 );
    }

    @Test
    public void depositTooLongAmountTest() throws InvalidAmountException {
        defaultAccount.deposit(new BigDecimal("100000000000000000000"));
        assertTrue(new BigDecimal("100000000000000001000").compareTo(defaultAccount.getAmount()) == 0 );
    }

    @Test
    public void checkAmountTest() {
        assertTrue(new BigDecimal("1000").compareTo(defaultAccount.check()) == 0 );
    }

    @Test
    public void getRentTest() {
        defaultAccount.updateYield();
        assertTrue(new BigDecimal("1220").compareTo(defaultAccount.getAmount()) == 0 );
    }
}
