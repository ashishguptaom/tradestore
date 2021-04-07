package com.dobi.tradestore.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dobi.tradestore.entity.Trade;
import com.dobi.tradestore.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

	private TradeService tradeService;

	@Mock
	private TradeRepository tradeRepository;

	@BeforeEach
	public void init() {
		this.tradeService = new TradeService(tradeRepository);
	}

	@Test
	public void testSave() {
		Trade trade = new Trade();
		when(tradeService.save(trade)).thenReturn(trade);
		tradeService.save(trade);
		verify(tradeRepository, times(1)).save(trade);
	}

	@Test
	public void testValidateWithLowerVersion() {
		Trade trade = new Trade();
		trade.setTradeId("T1");
		trade.setVersion(2);

		Trade existingTrade = new Trade();
		existingTrade.setTradeId("T1");
		existingTrade.setVersion(3);

		when(tradeRepository.findById("T1")).thenReturn(Optional.of(existingTrade));

		assertFalse(tradeService.validate(trade));
		verify(tradeRepository, times(1)).findById("T1");
	}

	@Test
	public void testValidate() {
		Trade trade = new Trade();
		trade.setTradeId("T1");
		trade.setVersion(4);

		Trade existingTrade = new Trade();
		existingTrade.setTradeId("T1");
		existingTrade.setVersion(3);

		when(tradeRepository.findById("T1")).thenReturn(Optional.of(existingTrade));

		assertTrue(tradeService.validate(trade));
		verify(tradeRepository, times(1)).findById("T1");
	}

	@Test
	public void testUpdateTradeExpiryFlag() {
		List<Trade> trades = new ArrayList<Trade>();
		Trade t1 = new Trade();
		t1.setTradeId("T1");
		t1.setExpired("N");
		t1.setMaturityDate(LocalDate.now().minusDays(1));
		trades.add(t1);

		Trade t2 = new Trade();
		t2.setTradeId("T2");
		t2.setExpired("N");
		t2.setMaturityDate(LocalDate.now().plusDays(2));
		trades.add(t2);
		Optional<List<Trade>> optionalTrades = Optional.of(trades);
		when(tradeRepository.findNotExpiredTrades()).thenReturn(optionalTrades);

		tradeService.updateTradeExpiryFlag();

		verify(tradeRepository, times(1)).findNotExpiredTrades();
		verify(tradeRepository, times(1)).save(t1);
		verify(tradeRepository, never()).save(t2);

	}
}
