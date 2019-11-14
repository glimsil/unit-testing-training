package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.db.UserStorage;
import com.github.diegopacheco.xunit.testing.bank.exception.EntityDoesNotExistsException;
import com.github.diegopacheco.xunit.testing.bank.exception.InitialValueException;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.User;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


public class UserServiceTest {

    UserService userService = new UserService();
    User defaultUser;

    @BeforeEach
    public void before() {
        User user = new User("Teste", "Email");
        user.setId(5000);
        defaultUser = UserStorage.save(user);
    }

    @Test
    public void createNewUserTest() {
        User user = userService.create("Teste", "Email");
        Assertions.assertEquals("Teste", user.getName());
        Assertions.assertEquals("Email", user.getEmail());
        Assertions.assertTrue(user.getId() != null);
    }

    @Test
    public void createNewCheckingAccountForUserInitialValue100Test() throws EntityDoesNotExistsException, InitialValueException {
        Account account = userService.createBankAccount(defaultUser.getId(), AccountType.CHECKING, new BigDecimal(100D));

        //Assertions.assertEquals("Email", user.getEmail());
        Assertions.assertTrue(defaultUser.getCheckingAccount() != null);
        Assertions.assertEquals(account.getId(), defaultUser.getCheckingAccount().getId());
        Assertions.assertTrue(BigDecimal.valueOf(100).compareTo(defaultUser.getCheckingAccount().getAmount()) == 0);
    }

    @Test
    public void createNewSavingAccountForUserInitialValue100Test() throws EntityDoesNotExistsException, InitialValueException {
        Account account = userService.createBankAccount(defaultUser.getId(), AccountType.SAVING, new BigDecimal(100D));

        //Assertions.assertEquals("Email", user.getEmail());
        Assertions.assertTrue(defaultUser.getSavingAccount() != null);
        Assertions.assertEquals(account.getId(), defaultUser.getSavingAccount().getId());
        Assertions.assertTrue(BigDecimal.valueOf(100).compareTo(defaultUser.getSavingAccount().getAmount()) == 0);
    }

    @Test
    public void createNewCheckingAccountForUserInitialValueTooLongTest() throws EntityDoesNotExistsException, InitialValueException {
        Account account = userService.createBankAccount(defaultUser.getId(), AccountType.CHECKING, new BigDecimal("10000000000000000000"));

        Assertions.assertTrue(defaultUser.getCheckingAccount() != null);
        Assertions.assertEquals(account.getId(), defaultUser.getCheckingAccount().getId());
        Assertions.assertTrue(new BigDecimal("10000000000000000000").compareTo(account.getAmount()) == 0);
    }

    @Test
    public void createNewSavingAccountForUserInitialValueTooLongTest() throws EntityDoesNotExistsException, InitialValueException {
        Account account = userService.createBankAccount(defaultUser.getId(), AccountType.SAVING, new BigDecimal("10000000000000000000"));

        Assertions.assertTrue(defaultUser.getSavingAccount() != null);
        Assertions.assertEquals(account.getId(), defaultUser.getSavingAccount().getId());
        Assertions.assertTrue(new BigDecimal("10000000000000000000").compareTo(account.getAmount()) == 0);
    }

}
