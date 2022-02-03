package org.toasthub.stockraider.stock;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toasthub.stockraider.common.FunctionalCalendar;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.enums.BarTimePeriod;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.trade.Trade;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBar;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBarsResponse;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarAdjustment;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarFeed;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.trade.StockTrade;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.trade.StockTradesResponse;
import net.jacobpeterson.alpaca.rest.AlpacaClientException;

@Service("StockMarketSvc")
public class StockMarketSvcImpl implements StockMarketSvc {

	@Override
	public void process(Request request, Response response) {
		String action = (String) request.getParams().get("action");
		
		switch (action) {
		case "STOCK_LIST":
			getMarketData(request, response);
			break;
		case "TEST":
			
			break;
		
		default:
			break;
		}
		
	}
	
	


	@Override
	public void sell(Request request, Response response) {
		
	}


	@Autowired
	protected AlpacaAPI alpacaAPI = null;
	
	// Constructors
	public StockMarketSvcImpl() {
	}
	
		
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}


	@Override
	public void getMarketData(Request request, Response response) {
		String stockName = (String) request.getParams().get("stockName");

		if ("".equals(stockName)) {
			response.addParam("error", "Stock name is empty");
			return;
		}
		try {    
		    // Get one hour, split-adjusted bars from 8 days ago market open
		    // to present day (20 minutes ago) from the SIP feed and print them out
		    StockBarsResponse stockNameBarsResponse = alpacaAPI.stockMarketData().getBars(
		            stockName,
		            ZonedDateTime.of(FunctionalCalendar.getPastYear(60 * 24 * 8),
					FunctionalCalendar.getPastMonth(60 * 24 * 8),
					FunctionalCalendar.getPastDay(60 * 24 * 8), 9, 30, 0, 0, ZoneId.of("America/New_York")),

		            ZonedDateTime.of(FunctionalCalendar.getPastYear(20),
					FunctionalCalendar.getPastMonth(20),
					FunctionalCalendar.getPastDay(20),
					FunctionalCalendar.getPastHour(20),
					FunctionalCalendar.getPastMinute(20), 0, 0, ZoneId.of("America/New_York")),

		            null,
		            null,
		            1,
		            BarTimePeriod.HOUR,
		            BarAdjustment.SPLIT,
		            BarFeed.SIP);
		    List<StockBar> stockBars = stockNameBarsResponse.getBars();
		    response.addParam("STOCKBARS", stockBars);
		    //.forEach(System.out::println);
	
		    // Get first 10 trades of past day at market open and print them out
		    StockTradesResponse stockNameTradesResponse = alpacaAPI.stockMarketData().getTrades(
		            stockName,
		            ZonedDateTime.of(FunctionalCalendar.getPastYear(60*24),
					FunctionalCalendar.getPastMonth(60*24),
					FunctionalCalendar.getPastDay(60*24), 9, 30, 0, 0, ZoneId.of("America/New_York")),

		            ZonedDateTime.of(FunctionalCalendar.getPastYear(60*24),
					FunctionalCalendar.getPastMonth(60*24),
					FunctionalCalendar.getPastDay(60*24), 9, 31, 0, 0, ZoneId.of("America/New_York")),
		            10,
		            null);
		    List<StockTrade> stockTrades = stockNameTradesResponse.getTrades();
		    response.addParam("TRADES", stockTrades);
		    // .forEach(System.out::println);
	
		    // Print out latest trade
		    Trade latestTrade = alpacaAPI.stockMarketData().getLatestTrade(stockName).getTrade();
		    response.addParam("TRADE", latestTrade);

		    
		} catch (AlpacaClientException exception) {
		    exception.printStackTrace();
		}
	}


	
}
