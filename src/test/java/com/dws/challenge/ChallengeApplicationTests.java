package com.dws.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.dws.challenge.domain.Account;
import com.dws.challenge.domain.TransferRequest;
import com.dws.challenge.exception.AccountNotExistException;
import com.dws.challenge.exception.OverDraftException;
import com.dws.challenge.repository.AccountsRepository;
import com.dws.challenge.service.AccountsService;

@SpringBootTest
class ChallengeApplicationTests {

	@Mock
	AccountsRepository accRepo;
	
	@InjectMocks
	AccountsService accService;
	
	@Test
	public void testRetrieveBalance() {
		Account account =new Account("001", BigDecimal.ONE);
		accService.createAccount(account);
		when(accRepo.checkBalance("001")).thenReturn(account.getBalance());
		
		assertEquals(BigDecimal.ONE, accService.getAccount("001").getBalance());
	}
	
	@Test
	public void testRetrieveBalanceFromInvalidAccount() {
		when(accRepo.getAccount("001")).thenReturn(new Account("001", BigDecimal.ONE));
		
		accService.getAccount("001");
	}
	
	@Test
	public void testTransferBalance() throws Exception, Exception, Exception {
		String accountFromId = "001";
		String accountFromTo = "002";
		BigDecimal amount = new BigDecimal(10);
		
		TransferRequest request = new TransferRequest();
		request.setAccountFromId(accountFromId);
		request.setAccountToId(accountFromTo);
		request.setAmount(amount);
		
		Account accFrom = new Account(accountFromId, BigDecimal.TEN);
		Account accTo = new Account(accountFromId, BigDecimal.TEN);
		
		when(accRepo.getAccount(accountFromId)).thenReturn(accFrom);
		when(accRepo.getAccount(accountFromTo)).thenReturn(accTo);
		
		accService.transferBalances(request);
		
		assertEquals(BigDecimal.ZERO, accFrom.getBalance());
		assertEquals(BigDecimal.TEN.add(BigDecimal.TEN), accTo.getBalance());
	}
	
	@Test
	public void testOverdraftBalance() throws OverDraftException, AccountNotExistException {
		String accountFromId = "001";
		String accountFromTo = "002";
		BigDecimal amount = new BigDecimal(20);
		
		TransferRequest request = new TransferRequest();
		request.setAccountFromId(accountFromId);
		request.setAccountToId(accountFromTo);
		request.setAmount(amount);
		
		Account accFrom = new Account(accountFromId, BigDecimal.TEN);
		Account accTo = new Account(accountFromId, BigDecimal.TEN);
		
		when(accRepo.getAccount(accountFromId)).thenReturn(accFrom);
		when(accRepo.getAccount(accountFromTo)).thenReturn(accTo);
		
		accService.transferBalances(request);
	}


}
