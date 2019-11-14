package com.github.diegopacheco.xunit.testing.bank.scheduler;

import com.github.diegopacheco.xunit.testing.bank.db.AccountStorage;
import com.github.diegopacheco.xunit.testing.bank.model.Account;
import com.github.diegopacheco.xunit.testing.bank.model.SavingAccount;

import java.util.concurrent.CompletableFuture;

public class Scheduler {
    public void run() {
        CompletableFuture.runAsync(() -> {
            while(true) {
                System.out.println("Looking for yield.");
                for(Account account: AccountStorage.getStorage().values()) {
                    if(account instanceof SavingAccount) {
                        ((SavingAccount) account).updateYield();
                    }
                }

                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
