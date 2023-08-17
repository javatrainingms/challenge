package com.dws.challenge.web;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dws.challenge.domain.TransferRequest;
import com.dws.challenge.domain.TransferResult;
import com.dws.challenge.exception.AccountNotExistException;
import com.dws.challenge.exception.CheckBalanceException;
import com.dws.challenge.exception.OverDraftException;
import com.dws.challenge.service.AccountsService;


@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
	
	
	@Autowired
	private AccountsService accountService;

	@PostMapping(consumes = { "application/json" })
	public ResponseEntity transferMoney(@RequestBody @Valid TransferRequest request) throws Exception {

		try {
			Executor executor = Executors.newFixedThreadPool(10);
	        long start = System.currentTimeMillis();
	        TransferResult result = new TransferResult();			
			 executor.execute(()->{
				 accountService.transferBalances(request);
					result.setAccountFromId(request.getAccountFromId());
					result.setBalanceAfterTransfer(accountService.checkBalance(request.getAccountFromId()));
						 
			 });
			long end = System.currentTimeMillis();
	        System.out.printf("The Amount transfer took %s ms%n", end - start);
	        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		} catch (AccountNotExistException | OverDraftException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.ACCEPTED);
		} catch (CheckBalanceException cbEx) {
			return new ResponseEntity<>(cbEx.getMessage(), HttpStatus.ACCEPTED);
		}
	}
}
