package org.toasthub.stockraider.algorithum;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;

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
		try {
			String stockName = "SPY";
			String date = "2021-08-09";
			List<StockBar> stockBars = Functions.dayTradingBars(alpacaAPI, stockName, date);
			EMA ema = new EMA(stockName);
			SMA sma = new SMA(stockName);
			MACD macd = new MACD(stockName);
			LBB lbb = new LBB(stockName);
			SL sl = new SL(stockName);
			for (int i = 51; i < stockBars.size(); i++) {
				sma.initializer(stockBars.subList(0, i+1) , 50);
				sma.setValue(SMA.calculateSMA(sma.getStockBars()));
				tradeBlasterDao.saveSMA(sma);
				ema.initializer(stockBars.subList(0, i+1) , 26);
				ema.setValue(tradeBlasterDao.queryEMAValue(ema));
				tradeBlasterDao.saveEMA(ema);
				ema.initializer(stockBars.subList(0, i+1), 13);
				ema.setValue(tradeBlasterDao.queryEMAValue(ema));
				tradeBlasterDao.saveEMA(ema);
				macd.initializer(stockBars.subList(0, i+1));
				macd.setValue(tradeBlasterDao.queryMACDValue(macd));
				tradeBlasterDao.saveMACD(macd);
				lbb.initializer(stockBars.subList(0, i+1), 50);
				lbb.setValue(tradeBlasterDao.queryLBBValue(lbb));
				tradeBlasterDao.saveLBB(lbb);
				sl.initializer(stockBars.subList(0 , i+1));
				sl.setValue(tradeBlasterDao.querySLValue(sl));
				tradeBlasterDao.saveSL(sl);
			}
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}
	}
	@Override
	public void load() {
		try {
			String stockName = "SPY";
			List<StockBar> stockBars = Functions.functionalCurrentStockBars(alpacaAPI, stockName, 200);
			EMA ema = new EMA(stockName);
			SMA sma = new SMA(stockName);
			MACD macd = new MACD(stockName);
			LBB lbb = new LBB(stockName);
			SL sl = new SL(stockName);
			for (int i = 51; i < stockBars.size(); i++) {
				sma.initializer(stockBars.subList(0, i+1) , 50);
				sma.setValue(SMA.calculateSMA(sma.getStockBars()));
				tradeBlasterDao.saveSMA(sma);
				ema.initializer(stockBars.subList(0, i+1) , 26);
				ema.setValue(tradeBlasterDao.queryEMAValue(ema));
				tradeBlasterDao.saveEMA(ema);
				ema.initializer(stockBars.subList(0, i+1), 13);
				ema.setValue(tradeBlasterDao.queryEMAValue(ema));
				tradeBlasterDao.saveEMA(ema);
				macd.initializer(stockBars.subList(0, i+1));
				macd.setValue(tradeBlasterDao.queryMACDValue(macd));
				tradeBlasterDao.saveMACD(macd);
				lbb.initializer(stockBars.subList(0, i+1), 50);
				lbb.setValue(tradeBlasterDao.queryLBBValue(lbb));
				tradeBlasterDao.saveLBB(lbb);
				sl.initializer(stockBars.subList(0 , i+1));
				sl.setValue(tradeBlasterDao.querySLValue(sl));
				tradeBlasterDao.saveSL(sl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
