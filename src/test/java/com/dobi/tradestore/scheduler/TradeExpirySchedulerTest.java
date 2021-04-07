package com.dobi.tradestore.scheduler;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.dobi.tradestore.TradestoreApplication;

@SpringJUnitConfig(TradestoreApplication.class)
@TestPropertySource("classpath:application.properties")
public class TradeExpirySchedulerTest {

	@SpyBean
	private TradeExpiryScheduler tradeExpiryScheduler;
	
	@Test
	public void test() {
		await().atMost(Duration.TEN_SECONDS)
				.untilAsserted(() -> verify(tradeExpiryScheduler, atLeast(10)).checkExpiredTrade());
	}

}
