package mro.app.alitem.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemListALItemVO implements Serializable {
	private static final long serialVersionUID = 7410390148119817605L;
	private BigDecimal itemSecondItemnumId;
	private String itemnum;
	private String itemnumDescription;
	private String altitemnum;
	private String altItemnumDescription;
	
	public ItemListALItemVO(){
		
	}
	
	@Id
	@Column(name = "ITEM_SECOND_ITEMNUM_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getItemSecondItemnumId() {
		return this.itemSecondItemnumId;
	}

	public void setItemSecondItemnumId(BigDecimal itemSecondItemnumId) {
		this.itemSecondItemnumId = itemSecondItemnumId;
	}
	@Column(name = "ITEMNUM")
	public String getItemnum() {
		return itemnum;
	}
	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}
	
	@Column(name = "ITEMNUM_DESCRIPTION")
	public String getItemnumDescription() {
		return itemnumDescription;
	}
	public void setItemnumDescription(String itemnumDescription) {
		this.itemnumDescription = itemnumDescription;
	}
	
	@Column(name = "ALTITEMNUM")
	public String getAltitemnum() {
		return altitemnum;
	}
	public void setAltitemnum(String altitemnum) {
		this.altitemnum = altitemnum;
	}
	
	@Column(name = "ALT_ITEMNUM_DESCRIPTION")
	public String getAltItemnumDescription() {
		return altItemnumDescription;
	}
	public void setAltItemnumDescription(String altItemnumDescription) {
		this.altItemnumDescription = altItemnumDescription;
	}
	
	
	
}