package com.dobi.tradestore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dobi.tradestore.controller.TradeController;

@SpringBootTest
class TradestoreApplicationTests {

	@Autowired
	private TradeController tradeController;
	
	@Test
	void contextLoads() {
		assertThat(tradeController).isNotNull();
	}

}
