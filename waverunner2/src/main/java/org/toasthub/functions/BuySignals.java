package org.toasthub.functions;

import java.math.BigDecimal;
import java.util.List;

import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBar;

public class BuySignals {

    //signals return true if buy signal is present, false otherwise

    //measures whether a 15 day moving average is above a 50 day moving average 
    public static Boolean goldenCross(List<StockBar> stockBars, int start){
        List<StockBar> shortStockBars = stockBars.subList(start-15, start-1);
        BigDecimal shortMovingAverage = Functions.simpleMovingAverage(shortStockBars);
        List<StockBar> longStockBars = stockBars.subList(start-50, start-1);
        BigDecimal longMovingAverage = Functions.simpleMovingAverage(longStockBars);

        if (shortMovingAverage.compareTo(longMovingAverage) >0)
        return true;
        return false;
    }

    //indicates whether the 20 day sma touches or falls beneath the lower bollinger band
    public static Boolean touchesLBB(List<StockBar> stockBars, int start){
        List <StockBar> stockBar = stockBars.subList(start-20, start-1);
        BigDecimal sma = Functions.simpleMovingAverage(stockBar);
        BigDecimal lowerBollingerBand = Functions.lowerBollingerBand(stockBar);
        if(sma.compareTo(lowerBollingerBand)<=0)
        return true;
        return false;
    }
    
    //indicates whether or not the signal line is above the macd
    public static Boolean signalLineCross(List<StockBar> stockBars, int start){
        List<StockBar>stockBar = stockBars.subList(0, start);
        BigDecimal macD = Functions.calculateMACD(stockBar);
        BigDecimal signalLine = Functions.signalLine(stockBar);
        if( signalLine.compareTo(macD) > 0 )
        return true;
        return false;
    }
}
