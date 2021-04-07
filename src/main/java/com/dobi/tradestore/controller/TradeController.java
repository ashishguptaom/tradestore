package com.dobi.tradestore.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dobi.tradestore.entity.Trade;
import com.dobi.tradestore.exception.InvalidTradeException;
import com.dobi.tradestore.service.TradeService;

@RestController
public class TradeController {
	private static final Logger logger = LogManager.getLogger(TradeController.class);

	private final TradeService tradeService;

	public TradeController(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@PostMapping("/trade")
	public ResponseEntity<Object> createTrade(@Valid @RequestBody Trade trade) {
		Trade savedTrade = null;
		if (tradeService.validate(trade)) {
			trade.setExpired("N");
			savedTrade = tradeService.save(trade);
			logger.debug("trade {} saved successfully", savedTrade);
		} else {
			logger.info("trade version is lower than existing version");
			throw new InvalidTradeException("trade version is lower than existing version");
		}
		return ResponseEntity.ok(savedTrade);
	}

	@GetMapping("/trades")
	public ResponseEntity<List<Trade>> getTrades() {
		Optional<List<Trade>> trades = tradeService.getAll();
		return trades.isPresent() ? ResponseEntity.ok(trades.get()) : ResponseEntity.notFound().build();
	}

}
