package org.toasthub.api;


import org.toasthub.stockraider.BackTestingSvc;
import org.toasthub.stockraider.CryptoMarketSvc;
import org.toasthub.stockraider.DashboardSvc;
import org.toasthub.stockraider.PlaceOrderSvc;
import org.toasthub.stockraider.StockMarketSvc;
import org.toasthub.stockraider.TradeBlasterSvc;
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
		case "STOCK_BUY":
			placeOrderSvc.placeDefaultOrder(request, response);
			break;
		case "TRAILING_STOP_ORDER":
			placeOrderSvc.placeTrailingStopOrder(request, response);
			break;
		case "DEFAULT_BACK_TEST":
			backTestingSvc.defaultBackTest(request, response);
			break;
		case "DASHBOARD":
			dashboardSvc.process(request, response);
			break;
		case "TRADEBLASTER":
			tradeBlasterSvc.process(request, response);
			break;
		
		default:
			break;
		}
		
		
		return response;
	}
	
	
}
