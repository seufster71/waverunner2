package org.toasthub.stockraider;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.toasthub.stockraider.model.Trade;
import org.toasthub.utils.GlobalConstant;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

import net.jacobpeterson.alpaca.AlpacaAPI;


@Service("TradeBlasterSvc")
public class TradeBlasterSvcImpl implements TradeBlasterSvc {

	@Autowired
	protected AlpacaAPI alpacaAPI;
	
	@Autowired
	protected TradeBlasterDao tradeBlasterDao;
	
	final AtomicBoolean tradeAnalysisJobRunning = new AtomicBoolean(false);
	
	// Constructors
	public TradeBlasterSvcImpl() {
	}
	
		
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
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
				
				if (m.containsKey("id") && !"".equals(m.get("id")) ) {
					request.addParam(GlobalConstant.ITEMID, m.get("id"));
					tradeBlasterDao.item(request, response);
					trade = (Trade) response.getParam("item");
					response.getParams().remove("item");
				} else {
					trade = new Trade();
				}
				if (m.containsKey("name")) {
					trade.setName((String) m.get("name"));
				} else {
					trade.setName("Test");
				}
				trade.setStock((String) m.get("stock"));
				if (m.containsKey("status")) {
					trade.setRunStatus((String) m.get("status"));
				} else {
					trade.setRunStatus("No");
				}
				if (m.get("buyAmount") instanceof Integer) {
					trade.setBuyAmount(new BigDecimal((Integer)m.get("buyAmount")));
				} else if (m.get("buyAmount") instanceof String) {
					trade.setBuyAmount(new BigDecimal((String)m.get("buyAmount")));
				}
				if (m.get("sellAmount") instanceof Integer) {
					trade.setSellAmount(new BigDecimal((Integer)m.get("sellAmount")));
				} else if (m.get("sellAmount") instanceof String) {
					trade.setSellAmount(new BigDecimal((String)m.get("sellAmount")));
				}
				if (m.containsKey("algorithum")) {
					trade.setAlgorithum((String)m.get("algorithum"));
				} else {
					trade.setAlgorithum("Algorithum1");
				}
			}
			request.addParam("item", trade);
			
			tradeBlasterDao.save(request, response);
			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}
		
	}


	@Override
	public void delete(Request request, Response response) {
		try {
			tradeBlasterDao.delete(request, response);
			tradeBlasterDao.itemCount(request, response);
			if ((Long) response.getParam(GlobalConstant.ITEMCOUNT) > 0) {
				tradeBlasterDao.items(request, response);
			}
			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}
		
	}


	@Override
	public void item(Request request, Response response) {
		try {
			tradeBlasterDao.item(request, response);
			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}
		
	}


	@Override
	public void items(Request request, Response response) {
		try {
			tradeBlasterDao.itemCount(request, response);
			if ((Long) response.getParam(GlobalConstant.ITEMCOUNT) > 0) {
				tradeBlasterDao.items(request, response);
			}
			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}
		
	}
	
	@Scheduled(cron="0 * * * * ?")
	public void tradeAnalysisTask() {
		
		if (tradeAnalysisJobRunning.get()) {
			// Prevent job from running over the top of an existing job.
			System.out.println("Trade analysis is currently running skipping this time");
			
		} else {
			tradeAnalysisJobRunning.set(true);
			checkTrades();
			tradeAnalysisJobRunning.set(false);
		}
	}
	
	private void checkTrades() {
		try {
			System.out.println("Running trade analysis job");
			LocalDateTime now = LocalDateTime.now();
			
			List<Trade> trades = tradeBlasterDao.getAutomatedTrades("Yes");
			
			if (trades != null && trades.size() > 0) {
				for(Trade trade : trades) {
					System.out.println("Checking trade name:" + trade.getName());
				}
			} else {
				System.out.println("No trades to run");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
}
