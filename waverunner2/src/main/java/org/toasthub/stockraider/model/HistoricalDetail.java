package org.toasthub.stockraider.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "tb_historical_detail")
public class HistoricalDetail extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Backtest backtest;
    private long boughtAtTime;
    private String stringedBoughtAtTime;
    private BigDecimal boughtAt;
    private long soldAtTime;
    private String stringedSoldAtTime;
    private BigDecimal soldAt;
    private BigDecimal highPrice;

    

    public HistoricalDetail() {
		super();
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
	}
    public HistoricalDetail(String code, Boolean defaultLang, String dir) {
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());

	}
    @JsonIgnore
    @ManyToOne(targetEntity = Backtest.class , fetch = FetchType.LAZY)
    @JoinColumn(name = "backtest_id")
    public Backtest getBacktest() {
        return backtest;
    }
    public void setBacktest(Backtest backtest) {
        this.backtest = backtest;
    }
    @Column(name = "highPrice")
    public BigDecimal getHighPrice() {
        return highPrice;
    }
    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }
    @Column(name = "soldAt")
    public BigDecimal getSoldAt() {
        return soldAt;
    }
    public void setSoldAt(BigDecimal soldAt) {
        this.soldAt = soldAt;
    }
    @Column(name = "boughtAt")
    public BigDecimal getBoughtAt() {
        return boughtAt;
    }
    public void setBoughtAt(BigDecimal boughtAt) {
        this.boughtAt = boughtAt;
    }
    @Column(name = "soldAtTime")
    public long getSoldAtTime() {
        return soldAtTime;
    }
    public void setSoldAtTime(long soldAtTime) {
        this.soldAtTime = soldAtTime;
        if(soldAtTime != 0)
        setStringedSoldAtTime(soldAtTime);
    }
    @Column(name = "boughtAtTime")
    public long getBoughtAtTime() {
        return boughtAtTime;
    }
    public void setBoughtAtTime(long boughtAtTime) {
        this.boughtAtTime = boughtAtTime;
        setStringedBoughtAtTime(boughtAtTime);
    }
    @Transient
    public String getStringedSoldAtTime() {
        return stringedSoldAtTime;
    }
    public void setStringedSoldAtTime(long soldAtTime) {
        Date date = new Date(soldAtTime * 1000);
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        this.stringedSoldAtTime = df.format(date);
    }
    public void setStringedSoldAtTime(String stringedSoldAtTime){
        this.stringedSoldAtTime = stringedSoldAtTime;
    }
    @Transient
    public String getStringedBoughtAtTime() {
        return stringedBoughtAtTime;
    }
    public void setStringedBoughtAtTime(long boughAtTime) {
        Date date = new Date(boughtAtTime * 1000);
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        this.stringedBoughtAtTime = df.format(date);
    }
}
