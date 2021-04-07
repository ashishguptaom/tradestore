package com.dobi.tradestore.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.dobi.tradestore.entity.Trade;

@Repository
public class TradeRepositoryImpl implements TradeRepositoryCustom {

	@PersistenceContext
	EntityManager em;

	@Override
	public Optional<List<Trade>> findNotExpiredTrades() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Trade> cq = cb.createQuery(Trade.class);

		Root<Trade> trade = cq.from(Trade.class);
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(cb.equal(trade.get("expired"), "N"));
		//predicates.add(cb.equal(trade.get("expired"), null));
		cq.where(predicates.toArray(new Predicate[0]));

		return Optional.ofNullable(em.createQuery(cq).getResultList());
	}

}
