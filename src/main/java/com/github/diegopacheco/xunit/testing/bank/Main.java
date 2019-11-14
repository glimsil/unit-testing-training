package com.github.diegopacheco.xunit.testing.bank;

import com.github.diegopacheco.xunit.testing.bank.exception.InitialValueException;
import com.github.diegopacheco.xunit.testing.bank.exception.InvalidAmountException;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.User;
import com.github.diegopacheco.xunit.testing.bank.scheduler.Scheduler;
import com.github.diegopacheco.xunit.testing.bank.service.AccountService;
import com.github.diegopacheco.xunit.testing.bank.service.UserService;
import com.github.diegopacheco.xunit.testing.bank.type.AccountType;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new Scheduler().run();
        UserService userService = new UserService();
        AccountService accountService = new AccountService();
        Scanner scanner = new Scanner(System.in);
        String op;
        boolean userSelected = false;
        while(true) {
            User user = null;
            if(!userSelected) {
                System.out.println("1 - Create User");
                System.out.println("2 - List Users");
                System.out.println("3 - Select User");
                System.out.println("0 - Exit");
                op = scanner.nextLine();

                if (op.contains("1")) {
                    System.out.println("Digite o nome:");
                    String name = scanner.nextLine();
                    System.out.println("Digite o email:");
                    String email = scanner.nextLine();
                    user = userService.create(name, email);
                    System.out.println("User created with ID: " + user.getId());
                    userSelected = true;
                } else if (op.contains("2")) {
                    for (User u : userService.listUsers()) {
                        System.out.println(u.getId() + " | Name: " + u.getName() + ", Email: " + u.getEmail());
                    }
                    userSelected = false;
                } else if (op.contains("3")) {
                    System.out.println("Digite o ID:");
                    String id = scanner.nextLine();
                    user = userService.get(Integer.parseInt(id));
                    if (null == user) {
                        System.out.println("User does not exists");
                        userSelected = false;
                    } else {
                        userSelected = true;
                        continue;
                    }
                } else if (op.contains("0")) {
                    userSelected = false;
                    break;
                } else {
                    System.out.println("Invalid option");
                    userSelected = false;
                }
            }
            if(userSelected) {
                Account account;
                System.out.println("1 - Create Account");
                System.out.println("2 - Show accounts");
                System.out.println("3 - Transfer");
                System.out.println("4 - Withdraw");
                System.out.println("5 - Deposit");
                System.out.println("6 - Check");
                System.out.println("0 - Back");
                op = scanner.nextLine();
                if(op.contains("1")) {
                    System.out.println("Digite 1 para Checking e 2 para Saving:");
                    String type = scanner.nextLine();
                    if(type.contains("1")) {
                        try {
                            account = accountService.create(AccountType.CHECKING, BigDecimal.valueOf(100));
                            System.out.println(account.getId() + " | AccountNumber: " + account.getAccountNumber());
                        } catch (InitialValueException e) {
                            e.printStackTrace();
                        }
                    } else if(type.contains("2")) {
                        try {
                            account = accountService.create(AccountType.SAVING, BigDecimal.valueOf(100));
                            System.out.println(account.getId() + " | AccountNumber: " + account.getAccountNumber());
                        } catch (InitialValueException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Invalid option.");
                    }
                } else if(op.contains("2")) {
                    if(user.getCheckingAccount() != null) {
                        System.out.println("Checking account with ID " + user.getCheckingAccount().getId() + ", accountNumber: " + user.getCheckingAccount().getAccountNumber());
                    }
                    if (user.getSavingAccount() != null) {
                        System.out.println("Saving account with ID " + user.getSavingAccount().getId() + ", accountNumber: " + user.getSavingAccount().getAccountNumber());
                    }
                } else if (op.contains("3")) {
                    System.out.println("Digite o ID da conta de origem:");
                    String accountId = scanner.nextLine();
                    Account accountNow = accountService.get(Integer.valueOf(accountId));
                    if(accountNow == null) {
                        System.out.println("Invalid account.");
                        continue;
                    }
                    System.out.println("Digite o ID da conta de destino:");
                    accountId = scanner.nextLine();
                    Account accountTo = accountService.get(Integer.valueOf(accountId));
                    if(accountTo == null) {
                        System.out.println("Invalid account.");
                        continue;
                    }
                    System.out.println("Voce tem " + accountNow.getAmount() + " de saldo");
                    System.out.println("Digite o valor a ser transferido para a conta " + accountTo.getId() + ":");
                    String value = scanner.nextLine();
                    try {
                        accountNow.transfer(accountTo, new BigDecimal(value));
                    } catch (InvalidAmountException e) {
                        e.printStackTrace();
                    }
                } else if (op.contains("4")) {
                    System.out.println("Digite o ID da conta:");
                    String accountId = scanner.nextLine();
                    Account accountNow = accountService.get(Integer.valueOf(accountId));
                    if(accountNow == null) {
                        System.out.println("invalid account.");
                        continue;
                    }
                    System.out.println("Voce tem " + accountNow.getAmount() + " de saldo");
                    System.out.println("Digite o valor a ser sacado:");
                    String value = scanner.nextLine();

                    try {
                        accountNow.withdraw(new BigDecimal(value));
                    } catch (InvalidAmountException e) {
                        e.printStackTrace();
                    }
                } else if (op.contains("5")) {
                    System.out.println("Digite o ID da conta:");
                    String accountId = scanner.nextLine();
                    Account accountNow = accountService.get(Integer.valueOf(accountId));
                    System.out.println("Digite o valor a ser depositado:");
                    String value = scanner.nextLine();

                    try {
                        accountNow.deposit(new BigDecimal(value));
                    } catch (InvalidAmountException e) {
                        e.printStackTrace();
                    }
                } else if (op.contains("6")) {
                    System.out.println("Digite o ID da conta:");
                    String accountId = scanner.nextLine();
                    Account accountNow = accountService.get(Integer.valueOf(accountId));
                    System.out.println("Valor em Conta: " + accountNow.getAmount().toString());
                } else if (op.contains("0")) {
                    userSelected = false;
                    continue;
                } else {
                    System.out.println("Invalid option...");
                }
            }
        }
    }
}
