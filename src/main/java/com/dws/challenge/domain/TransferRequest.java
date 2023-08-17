package com.dws.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransferRequest {
	
	@NotNull
	@NotEmpty
	private String accountFromId;

	@NotNull
	@NotEmpty
	private String accountToId;

	@NotNull
	@Min(value = 0, message = "Transfer amount can not be less than zero")
	private BigDecimal amount;

	@JsonCreator
	public TransferRequest(@NotNull @JsonProperty("accountFromId") String accountFromId,
			@NotNull @JsonProperty("accountToId") String accountToId,
			@NotNull @Min(value = 0, message = "Transfer amount can not be less than zero") @JsonProperty("amount") BigDecimal amount) {
		super();
		this.accountFromId = accountFromId;
		this.accountToId = accountToId;
		this.amount = amount;
	}
	
	@JsonCreator
	public TransferRequest() {
		super();
	}

}
