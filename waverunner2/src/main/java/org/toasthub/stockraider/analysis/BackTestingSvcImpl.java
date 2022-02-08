package org.toasthub.stockraider.analysis;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toasthub.stockraider.common.BuySignals;
import org.toasthub.stockraider.common.Functions;
import org.toasthub.stockraider.common.Order;
import org.toasthub.stockraider.model.Backtest;
import org.toasthub.stockraider.orders.TradeBlasterDao;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBar;

@Service("BackTestingSvc")
public class BackTestingSvcImpl implements BackTestingSvc {

    @Autowired
    protected AlpacaAPI alpacaAPI;

    @Autowired
    protected BuySignals buySignals;

    @Autowired
    protected TradeBlasterDao tradeBlasterDao;

    // Constructors
    public BackTestingSvcImpl() {
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void process(Request request, Response response) {
        String action = (String) request.getParams().get("action");

        switch (action) {
            case "DEFAULT_BACK_TEST":
                backTest(request, response);
                break;

            default:
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void backTest(Request request, Response response) {
        Map<String , ?> map = (Map<String ,?>)request.getParam("ITEM");
        Backtest backtest = new Backtest();
        String stockName = (String) map.get("stock");
        String buySignal = (String) map.get("algorithum");
        String date = (String) map.get("calendar");
        BigDecimal orderAmount =  new BigDecimal((Integer)map.get("buyAmount"));
        BigDecimal trailingStopPercent = new BigDecimal((Double)map.get("trailingStopPercent"));
        BigDecimal maxProfit = new BigDecimal((Double)map.get("profitLimit"));
        backtest.setStock((String) map.get("stock"));
        backtest.setAlgorithum((String) map.get("algorithum"));
        backtest.setDate((String) map.get("calendar"));
        backtest.setBuyAmount(new BigDecimal((Integer)map.get("buyAmount")));
        backtest.setSellAmount(new BigDecimal((Integer)map.get("sellAmount")));
        backtest.setTrailingStopPercent(new BigDecimal((Double)map.get("trailingStopPercent")));
        backtest.setProfitLimit(new BigDecimal((Double)map.get("profitLimit")));
        backtest.setName((String)map.get("name"));



        if ("".equals(stockName)) {
            response.addParam("error", "Stock name is empty");
            return;
        }

        List<StockBar> stockBars = Functions.dayTradingBars(alpacaAPI, stockName, date);
        BigDecimal totalValue = BigDecimal.ZERO;
        BigDecimal moneySpent = BigDecimal.ZERO;
        BigDecimal stockPrice = BigDecimal.ZERO;
        List<Order> orders = new ArrayList<Order>(0);

        for (int i = 100; i < stockBars.size(); i++) {

            stockPrice = BigDecimal.valueOf(stockBars.get(i).getClose());

            if (buySignals.process(stockBars, i, buySignal, stockName)) {
                orders.add(new Order(orderAmount, null, null, trailingStopPercent,
                        maxProfit.multiply(stockPrice), stockPrice));
                moneySpent = moneySpent.add(orderAmount);
            }

            for (int f = orders.size() - 1; f >= 0; f--) {
                if (orders.get(f).getHighPrice().compareTo(stockPrice) < 0)
                    orders.get(f).setHighPrice(stockPrice);

                if ((stockPrice.compareTo(orders.get(f).getTotalProfit())) >= 0) {
                    totalValue = totalValue.add(orders.get(f).convertToDollars(stockPrice));
                    orders.remove(f);
                } else if ((stockPrice.divide(orders.get(f).getHighPrice(), MathContext.DECIMAL32))
                        .compareTo(orders.get(f).getTrailingStopPercent()) < 0) {
                    totalValue = totalValue.add(orders.get(f).convertToDollars(stockPrice));
                    orders.remove(f);
                }
            }
        }
        for (int i = 0; i < orders.size(); i++)
            totalValue = totalValue.add(orders.get(i).convertToDollars(stockPrice));

        backtest.setMoneySpent(moneySpent);
        backtest.setTotalValue(totalValue);
        tradeBlasterDao.saveBacktest(backtest);
        
    }
}
