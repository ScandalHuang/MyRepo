package mro.viewForm;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import mro.base.System.config.basicType.GpType;
import mro.base.System.config.basicType.SecondItemType;

import org.apache.commons.lang3.StringUtils;

@ManagedBean(name = "ItemView")
@ApplicationScoped
public class ItemView implements Serializable {

	private static final long serialVersionUID = 7804211309472285039L;

	public ItemView() {

	}

	@PostConstruct
	public void init() {

	}

	public boolean vGpType2(String gpProductCategory){
		if(StringUtils.isBlank(gpProductCategory)) return false;
		return GpType.valueOf(gpProductCategory).vGpType2();
	}
	
	public boolean secondSource(String secondItemType){
		if(StringUtils.isBlank(secondItemType)) return false;
		return SecondItemType.valueOf(secondItemType).secondSource();
	}
}
