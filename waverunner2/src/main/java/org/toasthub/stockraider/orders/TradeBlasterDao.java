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
import java.util.Map;

import javax.persistence.Query;

import org.toasthub.stockraider.common.BaseDao;
import org.toasthub.stockraider.model.Backtest;
import org.toasthub.stockraider.model.Trade;
import org.toasthub.stockraider.model.algorithms.EMA;
import org.toasthub.stockraider.model.algorithms.LBB;
import org.toasthub.stockraider.model.algorithms.MACD;
import org.toasthub.stockraider.model.algorithms.SL;
import org.toasthub.utils.Request;
import org.toasthub.utils.Response;

public interface TradeBlasterDao extends BaseDao {
	public List<Trade> getAutomatedTrades(String runStatus);
	public BigDecimal queryMACDValue(MACD MACD);
	public BigDecimal queryLBBValue(LBB lbb);
	public BigDecimal querySLValue(SL sl);
	public BigDecimal queryEMAValue(EMA ema);
	public BigDecimal queryAlgValue(String alg, String stock, String type, long epochSeconds);
	public Query queryBuilder(String alg, long epochSeconds, String type, String stock);
	public void saveAll(Map< String , List<?> > map);
	public Boolean queryChecker(String alg, long epochSeconds, String type, String stock);
	public void saveBacktest(Backtest backtest);
	public void backtests(Request request, Response response);
	public void backtestCount(Request request, Response response);
	public void deleteBacktest(Request request, Response response);
}
