package mro.base.System.config.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import mro.base.System.config.basicType.PrType;


public class PrMcParameter implements Serializable{
	
	private static final long serialVersionUID = 8781468438352244009L;
	private BigDecimal mcBuyerWorkTime;//物管及採購作業時間
	private BigDecimal mFrequenceLimit; //月耗用次數下限
	private BigDecimal maxRemarkRange; //說明欄位限制上限(倍數)
	private BigDecimal minRemarkRange; //說明欄位限制下限(倍數)
	
	public PrMcParameter(String prtype){
		if(PrType.findValue(prtype).compareTo(PrType.R1CONTROL)==0){
			this.PrMcParameter(new BigDecimal("0.2"), new BigDecimal("3"),//20171122 lead time 修改 0.1-->0.2
					new BigDecimal("1.3"),new BigDecimal("0.7"));
		}else if(PrType.findValue(prtype).compareTo(PrType.R2CONTROL)==0){
			this.PrMcParameter(new BigDecimal("0.8"), new BigDecimal("3"), //20171122 lead time 修改 0.6-->0.8
					new BigDecimal("1.15"),new BigDecimal("0.85"));
		}
	}
	
	private void PrMcParameter(BigDecimal mcBuyerWorkTime,BigDecimal mFrequenceLimit,
			BigDecimal maxRemarkRange,BigDecimal minRemarkRange){
		this.mcBuyerWorkTime = mcBuyerWorkTime;
		this.mFrequenceLimit = mFrequenceLimit;
		this.maxRemarkRange = maxRemarkRange;
		this.minRemarkRange = minRemarkRange;
	}

	public BigDecimal getMcBuyerWorkTime() {
		return mcBuyerWorkTime;
	}

	public BigDecimal getmFrequenceLimit() {
		return mFrequenceLimit;
	}

	public BigDecimal getMaxRemarkRange() {
		return maxRemarkRange;
	}

	public BigDecimal getMinRemarkRange() {
		return minRemarkRange;
	}

	
}
