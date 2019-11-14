package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.db.AccountStorage;
import com.github.diegopacheco.xunit.testing.bank.exception.InitialValueException;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.CheckingAccount;
import com.github.diegopacheco.xunit.testing.bank.model.SavingAccount;
import com.github.diegopacheco.xunit.testing.bank.model.User;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;

import java.math.BigDecimal;

public class AccountService{
    Integer accountNumberSequence = 1;

    public Account create(AccountType accountType, BigDecimal initialAmount) throws InitialValueException {
        Account account;
        if(accountType == AccountType.CHECKING) {
            account = new CheckingAccount(initialAmount);
        } else {
            account = new SavingAccount(initialAmount);
        }
        return AccountStorage.save(account);
    }

}