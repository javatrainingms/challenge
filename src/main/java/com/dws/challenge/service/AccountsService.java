package com.dws.challenge.service;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferMoney;
import com.dws.challenge.domain.TransferRequest;
import com.dws.challenge.repository.AccountsRepository;

import lombok.Getter;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }
  
  public void transferMoney(TransferMoney transferMoney) {
	    this.accountsRepository.transferMoney(transferMoney);
	  }
  public BigDecimal checkBalance(String accountId) {
	  return this.accountsRepository.checkBalance(accountId);
  }

public void transferBalances( TransferRequest request) {
	this.accountsRepository.transferBalances(request);
}
}
