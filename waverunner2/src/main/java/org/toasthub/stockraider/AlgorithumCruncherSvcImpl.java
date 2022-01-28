package org.toasthub.stockraider;


import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toasthub.stockraider.model.Trade;
import org.toasthub.utils.GlobalConstant;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

import net.jacobpeterson.alpaca.AlpacaAPI;


@Service("AlgorithumCruncherSvc")
public class AlgorithumCruncherSvcImpl implements AlgorithumCruncherSvc {

	@Autowired
	protected AlpacaAPI alpacaAPI;
	
	
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
		
		default:
			break;
		}
		
	}


	@Override
	public void save(Request request, Response response) {
		try {
			Trade trade =  null;
			if (request.containsParam(GlobalConstant.ITEM)) {
				Map<String,Object> m = (Map<String,Object>) request.getParam(GlobalConstant.ITEM);
				
			}
			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}
		
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

	private static double calcBollingerBands(double numArray[]) {
		double[] numArrayTest = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		// calculate the simple moving average
		double sma = calcSMA(numArrayTest);
		// calculate standard deviation
		double sd = calcSD(numArrayTest);
		// calculate upper band
		double upper = sma + sd * 2 + sma;
		// calculate lower band
		double lower = sma - sd * 2;
		// band width
		double bandwidth = upper - lower;
		
		return 0;
		
	}
	
	private static double calcSMA(double numArray[]) {
		double sum = 0.0;
		int length = numArray.length;
        for(double num : numArray) {
            sum += num;
        }
        double mean = sum/length;
        return mean;
	}
	
	private static double calcSD(double numArray[]) {
		double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;
        for(double num : numArray) {
            sum += num;
        }
        double mean = sum/length;
        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation/length);
	}
}
