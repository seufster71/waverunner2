package org.toasthub.stockraider;

import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

public interface StockMarketSvc {
	public void process(Request request, Response response);
	public void getMarketData(Request request, Response response);
	public void buy(Request request, Response response);
	public void sell(Request request, Response response);
}
