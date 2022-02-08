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

/**
 * @author Edward H. Seufert
 */

package org.toasthub.stockraider.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_backtest")
public class Backtest extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String stock;
	protected BigDecimal buyAmount;
	protected BigDecimal sellAmount;
	protected String algorithum;
	protected BigDecimal trailingStopPercent;
	protected BigDecimal profitLimit;
    private BigDecimal moneySpent;
    private BigDecimal totalValue;
    private String date;

	//Constructor
	public Backtest() {
		super();
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
	}
	    public Backtest(String code, Boolean defaultLang, String dir){
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
		
	}

	// Methods
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "stock")
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	
	@Column(name = "buy_amount")
	public BigDecimal getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}

	@Column(name = "sell_amount")
	public BigDecimal getSellAmount() {
		return sellAmount;
	}
	public void setSellAmount(BigDecimal sellAmount) {
		this.sellAmount = sellAmount;
	}

	@Column(name = "algorithum")
	public String getAlgorithum() {
		return algorithum;
	}
	public void setAlgorithum(String algorithum) {
		this.algorithum = algorithum;
	}
	@Column(name = "trailing_stop_percent")
	public BigDecimal getTrailingStopPercent(){
		return trailingStopPercent;
	}
	public void setTrailingStopPercent(BigDecimal trailingStopPercent){
		this.trailingStopPercent = trailingStopPercent;
	}
	@Column(name = "profit_limit")
	public BigDecimal getProfitLimit(){
		return profitLimit;
	}
	public void setProfitLimit(BigDecimal profitLimit){
		this.profitLimit = profitLimit;
	}
    @Column(name= "total_value")
    public BigDecimal getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
    @Column(name = "money_spent")
    public BigDecimal getMoneySpent() {
        return moneySpent;
    }
    public void setMoneySpent(BigDecimal moneySpent) {
        this.moneySpent = moneySpent;
    }
    @Column(name="date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
