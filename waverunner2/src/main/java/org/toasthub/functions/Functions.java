package org.toasthub.functions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.common.historical.bar.enums.BarTimePeriod;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBar;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarAdjustment;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.enums.BarFeed;
import net.jacobpeterson.alpaca.rest.AlpacaClientException;

public class Functions {

    // all moving averages return a moving average of days yielded in stockbars

    // returns List of StockBars from past minutesPast minutes to int minutes ago (+ 20 min delay)
    public static List<StockBar> functionalStockBars(AlpacaAPI alpacaAPI, String stockName, int minutesPast, int minutesAgo){
        try{
            return alpacaAPI.stockMarketData().getBars(
            stockName,
            ZonedDateTime.of(FunctionalCalendar.getPastYear(minutesAgo+ minutesPast),
            FunctionalCalendar.getPastMonth(minutesAgo +minutesPast),
            FunctionalCalendar.getPastDay(minutesAgo +minutesPast), 9, 30, 0, 0, ZoneId.of("America/New_York")),

		    ZonedDateTime.of(FunctionalCalendar.getPastYear(minutesAgo+20),
            FunctionalCalendar.getPastMonth(minutesAgo+20),
            FunctionalCalendar.getPastDay(minutesAgo+20),
            FunctionalCalendar.getPastHour(minutesAgo+20), 0, 0, 0, ZoneId.of("America/New_York")),
            null, 
            null, 
            1, 
            BarTimePeriod.DAY, 
            BarAdjustment.SPLIT, 
            BarFeed.SIP).getBars();
        }
        catch(AlpacaClientException exception) {
                exception.printStackTrace();
                return null;
        }}

    public static BigDecimal calculateSD(List<StockBar> stockBars) {
        double sum = 0.0, standardDeviation = 0.0;
        int length = stockBars.size();

        for (int i = 0; i < length; i++) {
            sum +=stockBars.get(i).getClose();
        }
        double mean = sum / length;

        for (int i = 0; i < length; i++) {
            standardDeviation += Math.pow(stockBars.get(i).getClose()- mean, 2);
        }
        return BigDecimal.valueOf(Math.sqrt(standardDeviation / length));
    }

    // calculates using typical price
    public static BigDecimal simpleMovingAverage(List<StockBar> stockBars) {
        BigDecimal sma = BigDecimal.valueOf(0);
        for (int i = 0; i < stockBars.size(); i++) {
        sma= sma.add(BigDecimal.valueOf(stockBars.get(i).getClose()));
        }
        sma = sma.divide(BigDecimal.valueOf(stockBars.size()), MathContext.DECIMAL32);
        return sma;
    }




    //uses bollinger bands within 2 standard deviations
    // public static BigDecimal OldupperBollingerBand(List<StockBar> stockBars) {
    //     BigDecimal sma = simpleMovingAverage(stockBars);
    //     sma = sma.add(calculateSD(stockBars));
    //     return sma.add((calculateSD(stockBars)));
    // }

    // public static BigDecimal oldLowerBollingerBand(List<StockBar> stockBars) {
    //     BigDecimal sma = simpleMovingAverage(stockBars);
    //     sma = sma.subtract(calculateSD(stockBars));
    //     return sma.subtract(calculateSD(stockBars));
    // }

    public static BigDecimal upperBollingerBand(List<StockBar> stockBars, int start, int end) {
        BigDecimal sma = simpleMovingAverage(stockBars);
        sma = sma.add(calculateSD(stockBars));
        return sma.add((calculateSD(stockBars)));
    }

    public static BigDecimal lowerBollingerBand(List<StockBar> stockBars) {
        BigDecimal sma = simpleMovingAverage(stockBars);
        sma = sma.subtract(calculateSD(stockBars));
        return sma.subtract(calculateSD(stockBars));
    }

    public static BigDecimal calculateEma(List<StockBar> stockBars, BigDecimal ema){
        BigDecimal initEma;
        BigDecimal finalEma;
        if (ema == null)
        initEma = simpleMovingAverage(stockBars);
        else
        initEma = ema;

        BigDecimal multiplier = BigDecimal.valueOf( 2.0/(stockBars.size()+1) );

        finalEma =
        ( BigDecimal.valueOf(stockBars.get(stockBars.size()-1).getClose()).multiply(multiplier , MathContext.DECIMAL32) ).add
        ( initEma.multiply((BigDecimal.valueOf(1).subtract(multiplier)) , MathContext.DECIMAL32 ));

        return finalEma;
    }
    //must be given stockbar list containg at least 26 periods
    public static BigDecimal calculateMACD(List<StockBar> stockBars){
        List<StockBar> trimmedStockBars = stockBars.subList(stockBars.size()-26, stockBars.size()-1);
        BigDecimal longEMA = calculateEma(trimmedStockBars, null);

        trimmedStockBars = stockBars.subList(stockBars.size()-13, stockBars.size()-1);
        BigDecimal shortEMA = calculateEma(trimmedStockBars, null);
        return shortEMA.subtract(longEMA);
    }
    //must be given stockbar list containing at least 35 periods
    public static BigDecimal signalLine(List<StockBar> stockBars){
        BigDecimal multiplier = BigDecimal.valueOf( 2.0/(8+1) );
        BigDecimal macdAverage = BigDecimal.ZERO;
        List<StockBar> stockBar;
        for(int i = 8 ; i > 0 ; i--){
            stockBar = stockBars.subList(0, stockBars.size()-i);
            macdAverage = macdAverage.add(calculateMACD(stockBar));
        }
        macdAverage = macdAverage.divide(BigDecimal.valueOf(8), MathContext.DECIMAL32);
        macdAverage = macdAverage.multiply((BigDecimal.ONE.subtract(multiplier)), MathContext.DECIMAL32);
        return calculateMACD(stockBars).multiply(multiplier).add(macdAverage);
    }


}
