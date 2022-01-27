package org.toasthub.stockraider;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toasthub.Orders.Order;
import org.toasthub.functions.BuySignals;
import org.toasthub.functions.Functions;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

import net.jacobpeterson.alpaca.AlpacaAPI;
import net.jacobpeterson.alpaca.model.endpoint.marketdata.stock.historical.bar.StockBar;

@Service("BackTestingSvc")
public class BackTestingSvcImpl implements BackTestingSvc {

    @Autowired
    protected AlpacaAPI alpacaAPI;

    // Constructors
    public BackTestingSvcImpl() {
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void defaultBackTest(Request request, Response response) {
        String stockName = (String) request.getParams().get("stockName");
        if ("".equals(stockName)) {
            response.addParam("error", "Stock name is empty");
            return;
        }

        List<StockBar> stockBars = Functions.functionalStockBars(alpacaAPI, stockName, 60 * 24 * (365 + 50), 0);
        BigDecimal totalValue = BigDecimal.ZERO;
        BigDecimal moneySpent = BigDecimal.ZERO;
        BigDecimal stockPrice = BigDecimal.ZERO;
        List<Order> orders = new ArrayList<Order>(0);

        for (int i = 50; i < stockBars.size(); i++) {
            stockPrice = BigDecimal.valueOf(stockBars.get(i).getClose());
            if (BuySignals.signalLineCross(stockBars, i) & !BuySignals.signalLineCross(stockBars, i-1)) {
                orders.add(new Order(BigDecimal.TEN, null, null, BigDecimal.valueOf(0.8),
                        BigDecimal.valueOf(1.1).multiply(stockPrice), stockPrice));
                        moneySpent= moneySpent.add(BigDecimal.TEN);
            }
            for (int f = orders.size()-1; f >= 0; f--) {
                if (orders.get(f).getHighPrice().compareTo(stockPrice) < 0)
                orders.get(f).setHighPrice(stockPrice);

                if ((stockPrice.compareTo(orders.get(f).getTotalProfit())) >= 0) {
                    totalValue = totalValue.add(orders.get(f).convertToDollars(stockPrice));
                    orders.remove(f);
                }
                else if ((stockPrice.divide(orders.get(f).getHighPrice(), MathContext.DECIMAL32))
                        .compareTo(orders.get(f).getTrailingStopPercent()) < 0) {
                    totalValue = totalValue.add(orders.get(f).convertToDollars(stockPrice));
                    orders.remove(f);
                }
            }
        }
        for (int i = 0; i < orders.size(); i++)
        totalValue = totalValue.add(orders.get(i).convertToDollars(stockPrice));

        response.addParam("moneySpent", moneySpent);
        response.addParam("stockValue", totalValue);
    }
}
