package org.toasthub.stockraider.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toasthub.stockraider.common.CurrentBuySignals;
import org.toasthub.stockraider.model.Trade;

import net.jacobpeterson.alpaca.AlpacaAPI;

@Service("CurrentTestingSvc")
public class CurrentTestingSvcImpl{

    @Autowired
    protected AlpacaAPI alpacaAPI;

    @Autowired
    protected CurrentBuySignals currentBuySignals;

    // Constructors
    public CurrentTestingSvcImpl() {
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void currentTest(Trade trade) {
        String alg = trade.getAlgorithum();
        String stock = trade.getStock();
            if (currentBuySignals.process(alg, stock)){
                
            }
    }
}
