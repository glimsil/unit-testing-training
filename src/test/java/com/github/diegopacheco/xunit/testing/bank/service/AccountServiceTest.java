package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.exception.EntityDoesNotExistsException;
import com.github.diegopacheco.xunit.testing.bank.exception.InitialValueException;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.CheckingAccount;
import com.github.diegopacheco.xunit.testing.bank.model.SavingAccount;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AccountServiceTest {

    AccountService accountService = new AccountService();

    @Test
    public void createNewCheckingAccountForUserInitialValue100Test() throws EntityDoesNotExistsException, InitialValueException {
        Account account = accountService.create(AccountType.CHECKING, new BigDecimal(100D));

        Assertions.assertTrue(BigDecimal.valueOf(100).compareTo(account.getAmount()) == 0);
        Assertions.assertTrue(account instanceof CheckingAccount);
        Assertions.assertEquals(account.getId(), account.getId());
    }

    @Test
    public void createNewSavingAccountForUserInitialValue100Test() throws EntityDoesNotExistsException, InitialValueException {
        Account account = accountService.create(AccountType.SAVING, new BigDecimal(100D));

        Assertions.assertTrue(BigDecimal.valueOf(100).compareTo(account.getAmount()) == 0);
        Assertions.assertTrue(account instanceof SavingAccount);
        Assertions.assertEquals(account.getId(), account.getId());
    }
}