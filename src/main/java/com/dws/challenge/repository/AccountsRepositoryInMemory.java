package com.dws.challenge.repository;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferMoney;
import com.dws.challenge.exception.DuplicateAccountIdException;
import com.dws.challenge.service.EmailNotificationService;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

}
