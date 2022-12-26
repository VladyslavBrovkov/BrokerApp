package myapps.brokerapp.service;

import myapps.brokerapp.model.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account generateAccount();

    void withdrawMoney(Account account, BigDecimal money);

}
