package com.dws.challenge.repository;

import java.math.BigDecimal;

import javax.validation.Valid;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferMoney;
import com.dws.challenge.domain.TransferRequest;
import com.dws.challenge.exception.DuplicateAccountIdException;

public interface AccountsRepository {

  void createAccount(Account account) throws DuplicateAccountIdException;

  Account getAccount(String accountId);

  void clearAccounts();

void transferMoney(TransferMoney transferMoney);

BigDecimal checkBalance(String accountId);

void transferBalances(@Valid TransferRequest request);
}
