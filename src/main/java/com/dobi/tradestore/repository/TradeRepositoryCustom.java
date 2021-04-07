package com.dobi.tradestore.repository;

import java.util.List;
import java.util.Optional;

import com.dobi.tradestore.entity.Trade;

public interface TradeRepositoryCustom {
	Optional<List<Trade>> findNotExpiredTrades();
}
