package com.dws.challenge.repository;


import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferMoney;
import com.dws.challenge.domain.TransferRequest;
import com.dws.challenge.exception.AccountNotExistException;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.exception.OverDraftException;
import com.dws.challenge.service.EmailNotificationService;

@Repository
public class AccountsRepositoryInMemory implements AccountsRepository {

	private final Map<String, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void createAccount(Account account) throws DuplicateAccountIdException {
		Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
		if (previousAccount != null) {
			throw new DuplicateAccountIdException(
					"Account id " + account.getAccountId() + " already exists!");
		}
	}

	@Override
	public Account getAccount(String accountId) {
		return accounts.get(accountId);
	}

	@Override
	public void clearAccounts() {
		accounts.clear();
	}

	@Override
	public void transferMoney(TransferMoney transferMoney) {
		EmailNotificationService emailNotification=new EmailNotificationService();

		Account fromAccount=accounts.get(transferMoney.getAccountFromId());
		Account toAccount=accounts.get(transferMoney.getAccountToId());
		BigDecimal currentBalance=fromAccount.getBalance().subtract(transferMoney.getTransferAmmount());
		if(currentBalance.scale()>=0)
		{
			fromAccount.setBalance(currentBalance);
			emailNotification.notifyAboutTransfer(fromAccount, "Balance transfering from this account");

			toAccount.setBalance(toAccount.getBalance().add(currentBalance));
			emailNotification.notifyAboutTransfer(toAccount, "Balance transfering to this account");
		}

	}

	@Override
	public BigDecimal checkBalance(String accountId) {
		Account balanceCheck = getAccount(accountId);
		return balanceCheck.getBalance();
	}

	@Override
	public void transferBalances(TransferRequest transfer) {
		Account accountFrom =null;
		Account accountTo = null;
		accountFrom = getAccount(transfer.getAccountFromId());
		accountTo = getAccount(transfer.getAccountToId());
		if(accountFrom ==null)
		{
			throw new AccountNotExistException("Account with id:" + transfer.getAccountFromId() + " does not exist.");
		}
		if(accountTo ==null)
		{
			throw new AccountNotExistException("Account with id:" + transfer.getAccountToId() + " does not exist.");
		}

		if(accountFrom.getBalance().compareTo(transfer.getAmount()) < 0) {
			throw new OverDraftException("Account with id:" + accountFrom.getAccountId() + " does not have enough balance to transfer.");
		}

		accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
		accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));
	}


}
