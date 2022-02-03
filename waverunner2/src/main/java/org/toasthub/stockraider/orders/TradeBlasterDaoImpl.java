/*
 * Copyright (C) 2020 The ToastHub Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.toasthub.stockraider.orders;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toasthub.stockraider.model.Trade;
import org.toasthub.utils.GlobalConstant;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

import org.toasthub.stockraider.model.algorithms.EMA;
import org.toasthub.stockraider.model.algorithms.LBB;
import org.toasthub.stockraider.model.algorithms.MACD;
import org.toasthub.stockraider.model.algorithms.SL;
import org.toasthub.stockraider.model.algorithms.SMA;

@Repository("TradeBlasterDao")
@Transactional()
public class TradeBlasterDaoImpl implements TradeBlasterDao {

	@Autowired
	protected EntityManager entityManager;

	@Override
	public void delete(Request request, Response response) throws Exception {
		if (request.containsParam(GlobalConstant.ITEMID) && !"".equals(request.getParam(GlobalConstant.ITEMID))) {

			Trade trade = (Trade) entityManager.getReference(Trade.class,
					new Long((Integer) request.getParam(GlobalConstant.ITEMID)));
			entityManager.remove(trade);

		} else {
			// utilSvc.addStatus(Response.ERROR, Response.ACTIONFAILED, "Missing ID",
			// response);
		}
	}

	@Override
	public void save(Request request, Response response) throws Exception {
		Trade trade = (Trade) request.getParam("item");
		entityManager.merge(trade);
	}

	@Override
	public void saveMACD(MACD macd) {
		Query query = queryBuilder("MACD");
		query.setParameter("epochSeconds", macd.getEpochSeconds());
		query.setParameter("stock", macd.getStock());
		query.setParameter("type", macd.getType());
		try {
			query.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				entityManager.merge(macd);
		}
	}

	@Override
	public void saveSL(SL sl) {
		Query query = queryBuilder("SL");
		query.setParameter("epochSeconds", sl.getEpochSeconds());
		query.setParameter("stock", sl.getStock());
		query.setParameter("type", sl.getType());
		try {
			query.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				entityManager.merge(sl);
		}
	}

	@Override
	public void saveEMA(EMA ema) {
		Query query = queryBuilder("EMA");
		query.setParameter("epochSeconds", ema.getEpochSeconds());
		query.setParameter("stock", ema.getStock());
		query.setParameter("type", ema.getType());
		try {
			query.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				entityManager.merge(ema);
		}
	}

	@Override
	public void saveLBB(LBB lbb) {
		Query query = queryBuilder("LBB");
		query.setParameter("epochSeconds", lbb.getEpochSeconds());
		query.setParameter("stock", lbb.getStock());
		query.setParameter("type", lbb.getType());
		try {
			query.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				entityManager.merge(lbb);
		}
	}

	@Override
	public void saveSMA(SMA sma) {
		Query query = queryBuilder("SMA");
		query.setParameter("epochSeconds", sma.getEpochSeconds());
		query.setParameter("stock", sma.getStock());
		query.setParameter("type", sma.getType());
		try {
			query.getSingleResult();
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				entityManager.merge(sma);
		}
	}

	@Override
	public void items(Request request, Response response) throws Exception {
		String queryStr = "SELECT DISTINCT x FROM Trade AS x ";

		boolean and = false;
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			if (!and) {
				queryStr += " WHERE ";
			}
			queryStr += "x.active =:active ";
			and = true;
		}
		if (request.containsParam("RUNSTATUS")) {
			if (!and) {
				queryStr += " WHERE ";
			}
			queryStr += "x.runStatus =:runStatus ";
			and = true;
		}

		Query query = entityManager.createQuery(queryStr);

		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		}
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("runStatus", (String) request.getParam("RUNSTATUS"));
		}

		@SuppressWarnings("unchecked")
		List<Trade> trades = query.getResultList();

		response.addParam(GlobalConstant.ITEMS, trades);

	}

	@Override
	public void itemCount(Request request, Response response) throws Exception {
		String queryStr = "SELECT COUNT(DISTINCT x) FROM Trade as x ";
		boolean and = false;
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			if (!and) {
				queryStr += " WHERE ";
			}
			queryStr += "x.active =:active ";
			and = true;
		}
		if (request.containsParam("RUNSTATUS")) {
			if (!and) {
				queryStr += " WHERE ";
			}
			queryStr += "x.runStatus =:runStatus ";
			and = true;
		}

		Query query = entityManager.createQuery(queryStr);

		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("active", (Boolean) request.getParam(GlobalConstant.ACTIVE));
		}
		if (request.containsParam(GlobalConstant.ACTIVE)) {
			query.setParameter("runStatus", (String) request.getParam("RUNSTATUS"));
		}

		Long count = (Long) query.getSingleResult();
		if (count == null) {
			count = 0l;
		}
		response.addParam(GlobalConstant.ITEMCOUNT, count);

	}

	@Override
	public void item(Request request, Response response) throws Exception {
		if (request.containsParam(GlobalConstant.ITEMID) && !"".equals(request.getParam(GlobalConstant.ITEMID))) {
			String queryStr = "SELECT x FROM Trade AS x WHERE x.id =:id";
			Query query = entityManager.createQuery(queryStr);

			query.setParameter("id", new Long((Integer) request.getParam(GlobalConstant.ITEMID)));
			Trade trade = (Trade) query.getSingleResult();

			response.addParam("item", trade);
		} else {
			// utilSvc.addStatus(RestResponse.ERROR, RestResponse.EXECUTIONFAILED,
			// prefCacheUtil.getPrefText("GLOBAL_SERVICE",
			// "GLOBAL_SERVICE_MISSING_ID",prefCacheUtil.getLang(request)), response);
		}
	}

	@Override
	public List<Trade> getAutomatedTrades(String runStatus) {
		String queryStr = "SELECT DISTINCT x FROM Trade AS x WHERE x.active =:active AND x.runStatus =:runStatus";

		Query query = entityManager.createQuery(queryStr);
		query.setParameter("active", true);
		query.setParameter("runStatus", "Yes");

		@SuppressWarnings("unchecked")
		List<Trade> trades = query.getResultList();

		return trades;
	}

	@Override
	public BigDecimal queryEMAValue(EMA ema) {
		Query query = queryBuilder("EMA");
		query.setParameter("epochSeconds", ema.getEpochSeconds() - 60);
		query.setParameter("type", ema.getType());
		query.setParameter("stock", ema.getStock());
		try {
			EMA prevEMA = (EMA) query.getSingleResult();
			return EMA.calculateEMA(ema.getStockBars(), prevEMA.getValue());
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				return EMA.calculateEMA(ema.getStockBars());
			else
				System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public BigDecimal querySLValue(SL sl) {
		Query query = queryBuilder("MACD");
		query.setParameter("type", "MACD");
		query.setParameter("stock", sl.getStock());
		try {
			MACD[] macdArr = new MACD[9];
			MACD macd;
			for (int i = 0; i < 9; i++) {
				query.setParameter("epochSeconds", sl.getEpochSeconds() - (60 * i));
				macd = (MACD) query.getSingleResult();
				macdArr[i] = macd;
			}
			return SL.calculateSL(macdArr);
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				return SL.calculateSL(sl.getStockBars());
			else
				System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public BigDecimal queryMACDValue(MACD macd) {
		Query query = queryBuilder("EMA");
		query.setParameter("epochSeconds", macd.getEpochSeconds());
		query.setParameter("stock", macd.getStock());
		try {
			query.setParameter("type", "26-period");
			EMA longEMA = (EMA) query.getSingleResult();
			query.setParameter("type", "13-period");
			EMA shortEMA = (EMA) query.getSingleResult();
			return shortEMA.getValue().subtract(longEMA.getValue());
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				return MACD.calculateMACD(macd.getStockBars());
			else
				System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public BigDecimal queryLBBValue(LBB lbb) {
		Query query = queryBuilder("SMA");
		query.setParameter("epochSeconds", lbb.getEpochSeconds());
		query.setParameter("type", lbb.getType());
		query.setParameter("stock", lbb.getStock());
		try {
			SMA sma = (SMA) query.getSingleResult();
			return LBB.calculateLBB(lbb.getStockBars(), sma.getValue());
		} catch (Exception e) {
			if (e.getMessage().equals("No entity found for query"))
				return LBB.calculateLBB(lbb.getStockBars());
			else
				System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public Query queryBuilder(String alg) {
		String queryStr = "SELECT DISTINCT x FROM " + alg + " AS x"
				+ " WHERE x.epochSeconds =:epochSeconds"
				+ " AND x.type =: type AND x.stock =:stock";
		return entityManager.createQuery(queryStr);
	}

	@Override
	public BigDecimal queryLatestAlgValue(String alg, String stock, String type) {
		Instant instant = Instant.now();
		instant.truncatedTo(ChronoUnit.MINUTES);
		Query query = queryBuilder(alg);
		query.setParameter("epochSeconds", instant.getEpochSecond());
		query.setParameter("type", type);
		query.setParameter("stock", stock);
		switch (alg) {
			case "SMA":
				SMA sma = (SMA) query.getSingleResult();
				return sma.getValue();

			case "MACD":
				MACD macd = (MACD) query.getSingleResult();
				return macd.getValue();

			case "SL":
				SL sl = (SL) query.getSingleResult();
				return sl.getValue();

			case "EMA":
				EMA ema = (EMA) query.getSingleResult();
				return ema.getValue();

			case "LBB":
				LBB lbb = (LBB) query.getSingleResult();
				return lbb.getValue();

			default:
				return null;
		}
	}
}
