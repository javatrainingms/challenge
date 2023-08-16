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
	private final String accountFromId;
	
	@NotNull
	@NotEmpty
	private final String accountToId;

	@NotNull
	@Min(value = 0, message = "Transfer Ammount must be positive.")
	private BigDecimal transferAmmount;
	

	@JsonCreator
	public TransferMoney(@JsonProperty("accountFromId") String accountFromId, @JsonProperty("accountToId") String accountToId,
			 @JsonProperty("transferAmmount")  BigDecimal transferAmmount) {
		super();
		this.accountFromId = accountFromId;
		this.accountToId = accountToId;
		this.transferAmmount = transferAmmount;
	}
}
