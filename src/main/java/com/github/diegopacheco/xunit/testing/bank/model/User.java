package com.github.diegopacheco.xunit.testing.bank.model;


public class User {
    private Integer id;
    private String name;
    private String email;
    private CheckingAccount checkingAccount;
    private SavingAccount savingAccount;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public SavingAccount getSavingAccount() {
        return savingAccount;
    }

    public void setSavingAccount(SavingAccount savingAccount) {
        this.savingAccount = savingAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
