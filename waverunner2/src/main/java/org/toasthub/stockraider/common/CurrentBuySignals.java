package org.toasthub.stockraider.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toasthub.stockraider.orders.TradeBlasterDao;

@Service("CurrentBuySignals")
public class CurrentBuySignals{
    
    @Autowired
    TradeBlasterDao tradeBlasterDao;

    public Boolean process(String alg, String stock) {
        Boolean result = false;
        switch (alg) {

            case "goldenCross":
                result = currentGoldenCross(stock);
                break;

            case "touchesLBB":
                result = currentTouchesLBB(stock);
                break;

            case "signalLineCross":
                result = currentSignalLineCross(stock);
                break;

            default:
                break;
        }
        return result;
    }

    public Boolean currentSignalLineCross(String stock) {
        if (tradeBlasterDao.queryLatestAlgValue("MACD", stock, "MACD")
        .compareTo(tradeBlasterDao.queryLatestAlgValue("SL", stock, "SL")) > 0)
            return true;
        return false;
    }
    public Boolean currentTouchesLBB(String stock) {
       if (tradeBlasterDao.queryLatestAlgValue("SMA", stock, "20-day")
       .compareTo(tradeBlasterDao.queryLatestAlgValue("LBB", stock, "20-day")) <= 0)
           return true;
       return false;
   }
   public Boolean currentGoldenCross(String stock) {
       if (tradeBlasterDao.queryLatestAlgValue("SMA", stock, "15-day")
       .compareTo(tradeBlasterDao.queryLatestAlgValue("SMA", stock, "50-day")) > 0)
           return true;
       return false;
   }
}
