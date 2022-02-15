package org.toasthub.stockraider.orders;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.toasthub.stockraider.algorithum.AlgorithumCruncherSvc;
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

	@Autowired
	protected AlgorithumCruncherSvc algorithumCruncherSvc;
	
	final AtomicBoolean tradeAnalysisJobRunning = new AtomicBoolean(false);
	
	// Constructors
	public TradeBlasterSvcImpl() {
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
		case "DELETE_BACKTEST":
			deleteBacktest(request, response);
			break;
		default:
			break;
		}
		
	}


	@Override
	@SuppressWarnings("unchecked")
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
				if (m.get("maxProfit") instanceof Integer) {
					trade.setProfitLimit(new BigDecimal((Integer)m.get("maxProfit")));
				} else if (m.get("sellAmount") instanceof String) {
					trade.setProfitLimit((new BigDecimal((String)m.get("maxProfit"))));
				}
				if (m.get("trailingStopPercent") instanceof Integer) {
					trade.setTrailingStopPercent(new BigDecimal((Integer)m.get("trailingStopPercent")));
				} else if (m.get("trailingStopPercent") instanceof String) {
					trade.setTrailingStopPercent(new BigDecimal((String)m.get("trailingStopPercent")));
				}
				if (m.containsKey("algorithum")) {
					trade.setAlgorithum((String)m.get("algorithum"));
				}else
				trade.setAlgorithum("touchesLBB");
				String algorithum2 ="";
				if(m.containsKey("algorithum2"))
				algorithum2 = " "+(String)m.get("algorithum2");
				else
				algorithum2 = " touchesLBB";
				if(m.containsKey("operand")){
					trade.setAlgorithum(trade.getAlgorithum() + " "+ m.get("operand") + algorithum2);
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
	public void deleteBacktest(Request request, Response response) {
		try {
			tradeBlasterDao.deleteBacktest(request, response);
			tradeBlasterDao.backtestCount(request, response);
			if ((Long) response.getParam("backtestCount") > 0) {
				tradeBlasterDao.backtests(request, response);
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
			tradeBlasterDao.backtestCount(request, response);
			if ((Long) response.getParam("backtestCount") > 0) {
				tradeBlasterDao.backtests(request, response);
			}
			response.setStatus(Response.SUCCESS);
		} catch (Exception e) {
			response.setStatus(Response.ACTIONFAILED);
			e.printStackTrace();
		}
		
	}
	
	@Scheduled(cron="0 * * * * ?")
	public void tradeAnalysisTask() {
		
		// if (tradeAnalysisJobRunning.get()) {
		// 	System.out.println("Trade analysis is currently running skipping this time");
		// 	return;

		// } else {
		// 	new Thread(()->{
		// 		tradeAnalysisJobRunning.set(true);
		// 		algorithumCruncherSvc.load();
		// 		checkTrades();
		// 		tradeAnalysisJobRunning.set(false);
		// 	}).start();
		// }
	}
	
	private void checkTrades() {
		try {
			System.out.println("Running trade analysis job");
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
