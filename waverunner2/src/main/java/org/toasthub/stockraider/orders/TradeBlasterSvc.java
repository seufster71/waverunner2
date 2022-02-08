package org.toasthub.stockraider.orders;

import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

public interface TradeBlasterSvc {
	public void process(Request request, Response response);
	public void save(Request request, Response response);
	public void delete(Request request, Response response);
	public void item(Request request, Response response);
	public void items(Request request, Response response);
	public void deleteBacktest(Request request, Response response);
}
