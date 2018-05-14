package mro.base.System.config.basicType;

import mro.base.bo.GpTypeMappingBO;
import mro.base.entity.AItemAttribute;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.hibernate.ActiveFlag;

public enum GpType {
	GP_TYPE1(0), //航太產品專用 / 航太製程專
	GP_TYPE2(0), //其他(3C,車用,醫療…ext)
	DELIVERY_TYPE1(0.5), DELIVERY_TYPE2(0.3), DELIVERY_TYPE3(0), REMAIN_TYPE1(
			0.4), REMAIN_TYPE2(0.2), REMAIN_TYPE3(0);

	private double value;

	private GpType(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return name() != null ? name() : null;
	}

	public boolean vGpType2(){
		if(name().equals(GpType.GP_TYPE2.toString())){
			return true;
		}
		return false;
	}
	public static void setGpValue(AItemAttribute aItemAttribute) {
		double score = GpType.getScore(aItemAttribute.getGpDeliveryType(),
				aItemAttribute.getGpRemainType());
		aItemAttribute.setGpControlFlag(GpType.getGpFlag(score));
		aItemAttribute.setGpRiskLv(GpType.getGpRisk(score));
		aItemAttribute.setGpEvLv(GpType.getEvLv(
				aItemAttribute.getGpDeliveryType(),
				aItemAttribute.getGpRemainType()));
	}
	public static String getGpFlag(AItemAttribute aItemAttribute) {
		double score = GpType.getScore(aItemAttribute.getGpDeliveryType(),
				aItemAttribute.getGpRemainType());
		return GpType.getGpFlag(score);
	}
	
	public static double getScore(String... types) {
		double score = 0;
		for (String type : types) {
			if (StringUtils.isNotBlank(type)) {
				score += GpType.valueOf(type).value;
			}
		}
		return score;
	}

	public static String getGpRisk(double score) {
		if (score >= 0.5)
			return "高風險";
		else if (score >= 0.3)
			return "中風險";
		else if (score >= 0.1)
			return "低風險";
		else
			return "無風險";
	}

	public static String getGpFlag(double score) {
		if (score >= 0.3)
			return ActiveFlag.Y.toString();
		else
			return ActiveFlag.N.toString();
	}

	public static String getEvLv(String deliveryType, String remainType) {
		GpTypeMappingBO bo = SpringContextUtil.getBean(GpTypeMappingBO.class);
		return bo.get(deliveryType, remainType).getEvLv();
	}
}
