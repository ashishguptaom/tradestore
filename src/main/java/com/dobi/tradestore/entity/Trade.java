package com.dobi.tradestore.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = "trades")
public class Trade {
	@NotBlank(message = "{tradeid.notblank}")
	@Id
	@Column(name = "trade_id")
	private String tradeId;

	@NotNull
	@Column(name = "version")
	private long version;

	@NotBlank(message = "{counterpartyid.notblank}")
	@Column(name = "counter_party_id")
	private String counterPartyId;

	@NotBlank(message = "{bookid.notblank}")
	@Column(name = "book_id")
	private String bookId;

	@FutureOrPresent(message = "{maturitydate.futureorpresent}")
	@Column(name = "maturity_date")
	@NotNull(message = "{maturitydate.notnull}")
	private LocalDate maturityDate;

	@Column(name = "created_date")
	@CreationTimestamp
	private LocalDate createdDate;

	@Column(name = "expired", columnDefinition = "varchar(1) default 'N'")
	private String expired;
}
