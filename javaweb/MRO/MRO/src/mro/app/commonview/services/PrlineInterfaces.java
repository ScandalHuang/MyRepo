package mro.app.commonview.services;

import java.math.BigDecimal;

import mro.base.entity.Invbalances;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.Pr;
import mro.base.entity.Prline;

public interface PrlineInterfaces {
	
	/** 料號更新到PRLINE */
	public void setPrline(Pr pr, Prline prline, Item item);
	
	/** 重訂購量計算(R2) */
	public void r2Control(Prline prline);
	
	/** 新建議月用量(R1) */
	public void r1Control(Prline prline);
	
	/** 更新總價 & 控管量資訊整理 */
	public void newminlevel(Pr pr, Prline prline);
	
	/** 新控管量與近三個月平均耗用/領用倍數 : */
	public void setMultipleAvghdqtyThree(Prline prline,BigDecimal minlevel);
	
	/** 設定預設倉別 */
	public void setStoreCategory(Prline prline);
	
	/** 設定價格 R2:標準成本,R1:模糊價格 ELSE:報價單價格 */
	public void setPrice(Pr pr,Prline prline,Item item,ItemAttribute itemAttribute);
	
	/** 設定控管資訊(領耗用資訊整合) */
	public void setInvbalances(Pr pr,Prline prline);
	
	/** 設定控管資訊 */
	public void setInventory(Prline prline);
	
	/** 設定控管模式(空值才更新) */
	public void setPrControlConfig(Prline prline);
	
	/** 設定領用資訊 */
	public void setMatusetransHalf(Pr pr,Prline prline,Invbalances invbalances,boolean controlFlag);
	
	/** 設定耗用資訊 */
	public void setbsstrkhost(Pr pr,Prline prline,Invbalances invbalances,boolean controlFlag);
}
