package com.github.diegopacheco.xunit.testing.bank.db;

import com.github.diegopacheco.xunit.testing.bank.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    private static Integer id = 0;
    private static Map<Integer, User> storage = new HashMap<>();

    public static User save(User user) {
        if(null == user.getId()) {
            user.setId(nextId());
        }
        storage.put(user.getId(), user);
        return user;
    }

    public static User get(Integer id) {
        return storage.get(id);
    }

    public static Boolean delete(Integer id) {
        if(storage.containsKey(id)) {
            storage.remove(id);
            return true;
        }
        return false;
    }

    public static Map<Integer, User> getStorage() {
        return storage;
    }

    private static Integer nextId() {
        return id++;
    }
}
