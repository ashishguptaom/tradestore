package com.dobi.tradestore.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dobi.tradestore.service.TradeService;

@Component
public class TradeExpiryScheduler {
	private static final Logger logger = LogManager.getLogger(TradeExpiryScheduler.class);
	private final TradeService tradeService;

	public TradeExpiryScheduler(TradeService tradeService) {
		this.tradeService = tradeService;
	}

	@Scheduled(cron = "${trade.expiry.cron}")
	public void checkExpiredTrade() {
		logger.info("checking expired trades");
		tradeService.updateTradeExpiryFlag();
	}
}
