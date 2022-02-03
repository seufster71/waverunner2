package org.toasthub.api;


import org.toasthub.stockraider.algorithum.AlgorithumCruncherSvc;
import org.toasthub.stockraider.analysis.BackTestingSvc;
import org.toasthub.stockraider.crypto.CryptoMarketSvc;
import org.toasthub.stockraider.dashboard.DashboardSvc;
import org.toasthub.stockraider.orders.PlaceOrderSvc;
import org.toasthub.stockraider.orders.TradeBlasterSvc;
import org.toasthub.stockraider.stock.StockMarketSvc;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/public")
public class PublicWS {

	@Autowired
	StockMarketSvc stockMarketSvc;
	
	@Autowired
	CryptoMarketSvc cryptoMarketSvc;

	@Autowired
	PlaceOrderSvc placeOrderSvc;
	
	@Autowired
	DashboardSvc dashboardSvc;

	@Autowired
	BackTestingSvc backTestingSvc;
	
	@Autowired
	TradeBlasterSvc tradeBlasterSvc;

	@Autowired
	AlgorithumCruncherSvc algorithumCruncherSvc;
	
	
	@RequestMapping(value = "callService", method = RequestMethod.POST)
	public Response service(@RequestBody Request request) {
		String service = (String) request.getParams().get("service");
		
		
		Response response = new Response();
		
		switch (service) {
		case "STOCK":
			stockMarketSvc.process(request, response);
			break;
		case "CRYPTO":
			cryptoMarketSvc.process(request, response);
			break;
		case "PLACE_ORDER":
			placeOrderSvc.process(request, response);
			break;
		case "BACKTEST":
			backTestingSvc.process(request, response);
			break;
		case "DASHBOARD":
			dashboardSvc.process(request, response);
			break;
		case "TRADEBLASTER":
			tradeBlasterSvc.process(request, response);
			break;
		case "ALGORITHMCRUNCHER":
			algorithumCruncherSvc.process(request, response);
			break;
		
		default:
			break;
		}
		
		return response;
	}
	
	
}
