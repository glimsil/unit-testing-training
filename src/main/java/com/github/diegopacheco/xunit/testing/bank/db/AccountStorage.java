package com.github.diegopacheco.xunit.testing.bank.db;

import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.User;

import java.util.HashMap;
import java.util.Map;

public class AccountStorage {
    private static Integer id = 0;
    private static Map<Integer, Account> storage = new HashMap<>();

    public static Account save(Account account) {
        if(null == account.getId()) {
            account.setId(nextId());
        }
        storage.put(account.getId(), account);
        return account;
    }

    public static Account get(Integer id) {
        return storage.get(id);
    }

    public static Boolean delete(Integer id) {
        if(storage.containsKey(id)) {
            storage.remove(id);
            return true;
        }
        return false;
    }

    public static Map<Integer, Account> getStorage() {
        return storage;
    }

    private static Integer nextId() {
        return id++;
    }
}
