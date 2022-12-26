package myapps.brokerapp.service.impl;

import myapps.brokerapp.model.Account;
import myapps.brokerapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public Account generateAccount() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1000));
        return account;
    }

    @Override
    public void withdrawMoney(Account account, BigDecimal money) {
        account.setBalance(account.getBalance().subtract(money));
    }


}
