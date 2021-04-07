package com.dobi.tradestore.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ConfigTest {
	private Config config = new Config();

	@Test
	public void testGetValidator() {
		assertNotNull(config.getValidator());
	}
	
	@Test
	public void testMessageSource() {
		assertNotNull(config.messageSource());
	}
}
