package com.github.diegopacheco.xunit.testing.bank.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void createUserWithNameAndEmailTest() {
        User user = new User("Name Test", "Email Test");
        assertEquals("Name Test", user.getName());
    }
}
