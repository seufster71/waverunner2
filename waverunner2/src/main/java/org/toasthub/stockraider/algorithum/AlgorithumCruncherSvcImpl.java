package org.toasthub.stockraider.algorithum;

import java.util.concurrent.atomic.AtomicBoolean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toasthub.stockraider.common.Functions;
import org.toasthub.stockraider.model.algorithms.EMA;
import org.toasthub.stockraider.model.algorithms.LBB;
import org.toasthub.stockraider.model.algorithms.MACD;
import org.toasthub.stockraider.model.algorithms.SL;
import org.toasthub.stockraider.model.algorithms.SMA;
import org.toasthub.stockraider.orders.TradeBlasterDao;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBar;

@Service("AlgorithumCruncherSvc")
public class AlgorithumCruncherSvcImpl implements AlgorithumCruncherSvc {

	@Autowired
	protected AlpacaAPI alpacaAPI;

	@Autowired
	protected TradeBlasterDao tradeBlasterDao;

	final AtomicBoolean algorithumJobRunning = new AtomicBoolean(false);

	// Constructors
	public AlgorithumCruncherSvcImpl() {
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public void process(Request request, Response response) {
		String action = (String) request.getParams().get("action");

		switch (action) {
			case "ITEM":
				item(request, response);
				break;
			case "LIST":
				items(request, response);
				break;
			case "SAVE":
				save(request, response);
				break;
			case "DELETE":
				delete(request, response);
				break;
			case "BACKLOAD":
				backload(request, response);
				break;

			default:
				break;
		}

	}

	@Override
	public void save(Request request, Response response) {
	}

	@Override
	public void delete(Request request, Response response) {
		try {
			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}

	}

	@Override
	public void item(Request request, Response response) {
		try {

			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}

	}

	@Override
	public void items(Request request, Response response) {
		try {

			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}

	}

	@Override
	public void backload(Request request, Response response) {
		String stockName = "SPY";
		List<StockBar> stockBars = Functions.backloadBars(alpacaAPI, stockName);
		EMA ema13;
		EMA ema26;
		SMA sma;
		MACD macd;
		LBB lbb;
		SL sl;
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		List<SMA> smaList = new ArrayList<SMA>();
		List<MACD> macdList = new ArrayList<MACD>();
		List<LBB> lbbList = new ArrayList<LBB>();
		List<SL> slList = new ArrayList<SL>();
		List<EMA> emaList = new ArrayList<EMA>();
		if (stockBars != null)
			for (int i = 50; i < stockBars.size(); i++) {

				ema13 = new EMA(stockName);
				ema26 = new EMA(stockName);
				sma = new SMA(stockName);
				macd = new MACD(stockName);
				lbb = new LBB(stockName);
				sl = new SL(stockName);

				sma.initializer(stockBars.subList(i-50, i + 1), 50);
				if (!tradeBlasterDao.queryChecker(
						"SMA", sma.getEpochSeconds(), sma.getType(), sma.getStock())) 
						{
					sma.setValue(SMA.calculateSMA(sma.getStockBars()));
					smaList.add(sma);
				}

				ema26.initializer(stockBars.subList(i-26, i + 1), 26);
				if (!tradeBlasterDao.queryChecker(
						"EMA", ema26.getEpochSeconds(), ema26.getType(), ema26.getStock())) 
						{
					ema26.setValue(tradeBlasterDao.queryEMAValue(ema26));
					emaList.add(ema26);
				}

				ema13.initializer(stockBars.subList(i-13, i + 1), 13);
				if (!tradeBlasterDao.queryChecker(
						"EMA", ema13.getEpochSeconds(), ema13.getType(), ema13.getStock())) 
						{
					ema13.setValue(tradeBlasterDao.queryEMAValue(ema13));
					emaList.add(ema13);
				}

				macd.initializer(stockBars.subList(i-50, i + 1));
				if (!tradeBlasterDao.queryChecker(
						"MACD", macd.getEpochSeconds(), macd.getType(), macd.getStock())) 
						{
					macd.setValue(tradeBlasterDao.queryMACDValue(macd));
					macdList.add(macd);
				}

				lbb.initializer(stockBars.subList(i-50, i + 1), 50);
				if (!tradeBlasterDao.queryChecker(
						"LBB", lbb.getEpochSeconds(), lbb.getType(), lbb.getStock())) 
						{
					lbb.setValue(tradeBlasterDao.queryLBBValue(lbb));
					lbbList.add(lbb);
				}

				sl.initializer(stockBars.subList(i-50, i + 1));
				if (!tradeBlasterDao.queryChecker(
						"SL", sl.getEpochSeconds(), sl.getType(), sl.getStock())) 
						{
					sl.setValue(tradeBlasterDao.querySLValue(sl));
					slList.add(sl);
				}
			}
		map.put("EMA", emaList);
		map.put("SMA", smaList);
		map.put("MACD", macdList);
		map.put("SL", slList);
		map.put("LBB", lbbList);
		tradeBlasterDao.saveAll(map);
	}

	@Override
	public void load() {
		String stockName = "SPY";
		List<StockBar> stockBars = Functions.currentStockBars(alpacaAPI, stockName, 200);
		EMA ema13;
		EMA ema26;
		SMA sma;
		MACD macd;
		LBB lbb;
		SL sl;
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		List<SMA> smaList = new ArrayList<SMA>();
		List<MACD> macdList = new ArrayList<MACD>();
		List<LBB> lbbList = new ArrayList<LBB>();
		List<SL> slList = new ArrayList<SL>();
		List<EMA> emaList = new ArrayList<EMA>();
		if (stockBars != null)
			for (int i = 51; i < stockBars.size(); i++) {

				ema13 = new EMA(stockName);
				ema26 = new EMA(stockName);
				sma = new SMA(stockName);
				macd = new MACD(stockName);
				lbb = new LBB(stockName);
				sl = new SL(stockName);

				sma.initializer(stockBars.subList(0, i + 1), 50);
				if (!tradeBlasterDao.queryChecker(
						"SMA", sma.getEpochSeconds(), sma.getType(), sma.getStock())) {
					sma.setValue(SMA.calculateSMA(sma.getStockBars()));
					smaList.add(sma);
				}

				ema26.initializer(stockBars.subList(0, i + 1), 26);
				if (!tradeBlasterDao.queryChecker(
						"EMA", ema26.getEpochSeconds(), ema26.getType(), ema26.getStock())) {
					ema26.setValue(tradeBlasterDao.queryEMAValue(ema26));
					emaList.add(ema26);
				}

				ema13.initializer(stockBars.subList(0, i + 1), 13);
				if (!tradeBlasterDao.queryChecker(
						"EMA", ema13.getEpochSeconds(), ema13.getType(), ema13.getStock())) {
					ema13.setValue(tradeBlasterDao.queryEMAValue(ema13));
					emaList.add(ema13);
				}

				macd.initializer(stockBars.subList(0, i + 1));
				if (!tradeBlasterDao.queryChecker(
						"MACD", macd.getEpochSeconds(), macd.getType(), macd.getStock())) {
					macd.setValue(tradeBlasterDao.queryMACDValue(macd));
					macdList.add(macd);
				}

				lbb.initializer(stockBars.subList(0, i + 1), 50);
				if (!tradeBlasterDao.queryChecker(
						"LBB", lbb.getEpochSeconds(), lbb.getType(), lbb.getStock())) {
					lbb.setValue(tradeBlasterDao.queryLBBValue(lbb));
					lbbList.add(lbb);
				}

				sl.initializer(stockBars.subList(0, i + 1));
				if (!tradeBlasterDao.queryChecker(
						"SL", sl.getEpochSeconds(), sl.getType(), sl.getStock())) {
					sl.setValue(tradeBlasterDao.querySLValue(sl));
					slList.add(sl);
				}
			}
		map.put("EMA", emaList);
		map.put("SMA", smaList);
		map.put("MACD", macdList);
		map.put("SL", slList);
		map.put("LBB", lbbList);
		tradeBlasterDao.saveAll(map);
	}
}
