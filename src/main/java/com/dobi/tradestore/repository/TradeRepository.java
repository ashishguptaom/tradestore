package com.dobi.tradestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dobi.tradestore.entity.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String>, TradeRepositoryCustom {

}
