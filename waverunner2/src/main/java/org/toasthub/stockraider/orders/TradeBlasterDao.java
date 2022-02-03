/*
 * Copyright (C) 2020 The ToastHub Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.toasthub.stockraider.orders;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import org.toasthub.stockraider.common.BaseDao;
import org.toasthub.stockraider.model.Trade;
import org.toasthub.stockraider.model.algorithms.EMA;
import org.toasthub.stockraider.model.algorithms.LBB;
import org.toasthub.stockraider.model.algorithms.MACD;
import org.toasthub.stockraider.model.algorithms.SL;
import org.toasthub.stockraider.model.algorithms.SMA;

public interface TradeBlasterDao extends BaseDao {
	public List<Trade> getAutomatedTrades(String runStatus);
	public void saveSL(SL sl);
	public void saveMACD(MACD macd) ;
	public void saveEMA(EMA ema) ;
	public void saveLBB(LBB lbb) ;
	public void saveSMA(SMA sma); 
	public BigDecimal queryEMAValue(EMA ema);
	public BigDecimal queryMACDValue(MACD MACD);
	public BigDecimal queryLBBValue(LBB lbb);
	public BigDecimal querySLValue(SL sl);
	public BigDecimal queryLatestAlgValue(String alg, String stock, String type);
	public Query queryBuilder(String alg);
}
