package com.dobi.tradestore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dobi.tradestore.entity.Trade;
import com.dobi.tradestore.repository.TradeRepository;

@Service
public class TradeService {
	private static final Logger logger = LogManager.getLogger(TradeService.class);

	private TradeRepository tradeRepository;

	public TradeService(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}

	public Optional<List<Trade>> getAll() {
		return Optional.ofNullable(tradeRepository.findAll());
	}

	public Trade save(Trade trade) {
		return tradeRepository.save(trade);
	}

	public boolean validate(Trade trade) {
		Optional<Trade> existingTrade = tradeRepository.findById(trade.getTradeId());
		return existingTrade.isPresent() ? trade.getVersion() >= existingTrade.get().getVersion() : true;
	}

	public void updateTradeExpiryFlag() {
		Optional<List<Trade>> trades = tradeRepository.findNotExpiredTrades();
		if (trades.isPresent())
			trades.get().stream().filter(trade -> trade.getMaturityDate().isBefore(LocalDate.now()))
					.forEach(expiredTrade -> {
						logger.info("trade {} expiry date {} is lapsed, updating expired flag to 'Y'",
								expiredTrade.getTradeId(), expiredTrade.getMaturityDate());
						expiredTrade.setExpired("Y");
						save(expiredTrade);
					});
	}
}
