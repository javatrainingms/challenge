package com.dws.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransferMoney {

	@NotNull
	@NotEmpty
	private String accountFromId;
	
	@NotNull
	@NotEmpty
	private String accountToId;

	@NotNull
	@Min(value = 0, message = "Transfer Ammount must be positive.")
	private BigDecimal transferAmmount;
	

	
	@JsonCreator
	public TransferMoney(@NotNull @JsonProperty("accountFromId") String accountFromId,
			@NotNull @JsonProperty("accountToId") String accountToId,
			@NotNull @Min(value = 0, message = "Transfer amount can not be less than zero") 
			@JsonProperty("amount") BigDecimal transferAmmount) {
		super();
		this.accountFromId = accountFromId;
		this.accountToId = accountToId;
		this.transferAmmount = transferAmmount;
	}



	public TransferMoney() {
		super();
	}
	
	
	
	
}
