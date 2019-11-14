package com.github.diegopacheco.xunit.testing.bank.service;

import com.github.diegopacheco.xunit.testing.bank.db.UserStorage;
import com.github.diegopacheco.xunit.testing.bank.exception.EntityDoesNotExistsException;
import com.github.diegopacheco.xunit.testing.bank.exception.InitialValueException;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.CheckingAccount;
import com.github.diegopacheco.xunit.testing.bank.model.SavingAccount;
import com.github.diegopacheco.xunit.testing.bank.model.User;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class UserService {
    AccountService accountService = new AccountService();

    public User get(Integer userId) {
        return UserStorage.get(userId);
    }

    public User create(String name, String email) {
        User user = new User(name, email);
        return UserStorage.save(user);
    }

    public Account createBankAccount(Integer userId, AccountType accountType, BigDecimal initialAmount) throws EntityDoesNotExistsException, InitialValueException {
        User user = UserStorage.get(userId);
        if(null == user) {
            throw new EntityDoesNotExistsException("User with id " + userId + " does not exists");
        }
        Account account = accountService.create(accountType, initialAmount);
        if(accountType == AccountType.CHECKING) {
            user.setCheckingAccount((CheckingAccount) account);
        } else {
            user.setSavingAccount((SavingAccount) account);
        }
        UserStorage.save(user);
        return account;
    }

    public Collection<User> listUsers () {
        return UserStorage.getStorage().values();
    }
}
