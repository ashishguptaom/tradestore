package com.dobi.tradestore.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

@Component
public class TradeRequestResponseLoggingFilter implements Filter {
	private static final Logger logger = LogManager.getLogger(TradeRequestResponseLoggingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			ThreadContext.put("reqId", UUID.randomUUID().toString());
			logger.info("Request  {} : {}", req.getMethod(), req.getRequestURI());
			chain.doFilter(request, response);
			logger.info("Response  {} : {}", res.getContentType(), res.getStatus());
		} finally {
			ThreadContext.clearMap();
		}
	}
}
