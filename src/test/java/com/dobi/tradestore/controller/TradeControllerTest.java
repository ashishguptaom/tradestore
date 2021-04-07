package com.dobi.tradestore.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dobi.tradestore.entity.Trade;
import com.dobi.tradestore.service.TradeService;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TradeService tradeService;

	@Test
	public void testGetTrades() throws Exception {

		List<Trade> trades = new ArrayList<Trade>();
		Trade t1 = new Trade();
		t1.setTradeId("T1");
		t1.setBookId("B1");
		t1.setCounterPartyId("CP1");
		t1.setExpired("N");
		t1.setMaturityDate(LocalDate.of(2021, 04, 06));
		t1.setCreatedDate(LocalDate.of(2021, 04, 06));
		trades.add(t1);

		Optional<List<Trade>> optionalTrades = Optional.of(trades);
		when(tradeService.getAll()).thenReturn(optionalTrades);

		this.mockMvc.perform(get("/trades")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(
						"[{\"tradeId\":\"T1\",\"version\":0,\"counterPartyId\":\"CP1\",\"bookId\":\"B1\",\"maturityDate\":\"2021-04-06\",\"createdDate\":\"2021-04-06\",\"expired\":\"N\"}]")));

		verify(tradeService, times(1)).getAll();
	}

	@Test
	public void testGetTradesWithEmptyTradeList() throws Exception {

		List<Trade> trades = new ArrayList<Trade>();

		Optional<List<Trade>> optionalTrades = Optional.of(trades);
		when(tradeService.getAll()).thenReturn(optionalTrades);

		this.mockMvc.perform(get("/trades")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("[]")));

		verify(tradeService, times(1)).getAll();
	}

	@Test
	public void testCreateTrade() throws Exception {
		when(tradeService.validate(any(Trade.class))).thenReturn(true);

		this.mockMvc.perform(post("/trade").content(
				"{\"tradeId\":\"T1\",\"version\":1,\"counterPartyId\":\"CP1\",\"bookId\":\"B1\",\"maturityDate\":\"2099-04-05\",\"createdDate\":\"2021-04-06\",\"expired\":\"N\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

		verify(tradeService, times(1)).validate(any(Trade.class));
		verify(tradeService, times(1)).save(any(Trade.class));
	}

	@Test
	public void testCreateTradeWithLowerVersion() throws Exception {
		when(tradeService.validate(any(Trade.class))).thenReturn(false);

		this.mockMvc.perform(post("/trade").content(
				"{\"tradeId\":\"T1\",\"version\":1,\"counterPartyId\":\"CP1\",\"bookId\":\"B1\",\"maturityDate\":\"2099-04-05\",\"createdDate\":\"2021-04-06\",\"expired\":\"N\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNotAcceptable());

		verify(tradeService, times(1)).validate(any(Trade.class));
		verify(tradeService, never()).save(any(Trade.class));
	}

	@Test
	public void testCreateTradeWithInvalidMaturityDate() throws Exception {
		this.mockMvc.perform(post("/trade").content(
				"{\"tradeId\":\"T1\",\"version\":1,\"counterPartyId\":\"CP1\",\"bookId\":\"B1\",\"maturityDate\":\"2021-04-05\",\"createdDate\":\"2021-04-06\",\"expired\":\"N\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest());

		verify(tradeService, never()).validate(any(Trade.class));
		verify(tradeService, never()).save(any(Trade.class));
	}

	@Test
	public void testCreateTradeWithMissingTradeId() throws Exception {
		this.mockMvc.perform(post("/trade").content(
				"{\"version\":1,\"counterPartyId\":\"CP1\",\"bookId\":\"B1\",\"maturityDate\":\"2021-04-05\",\"createdDate\":\"2021-04-06\",\"expired\":\"N\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest());

		verify(tradeService, never()).validate(any(Trade.class));
		verify(tradeService, never()).save(any(Trade.class));
	}
}
