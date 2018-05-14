package mro.app.commonview.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.bo.ListClassspecBO;
import mro.app.commonview.vo.ListClassspecVO;
import mro.base.entity.Assetattribute;
import mro.base.entity.Classspec;
import mro.base.entity.Classstructure;

import org.springframework.beans.BeanUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;


@ManagedBean(name="ListClassspecBean")
@ViewScoped
public class ListClassspecBean implements Serializable{
	private static final long serialVersionUID = 3952746628310049178L;

	private List<ListClassspecVO> listClassspecVO;
    
    private Classstructure classstructure;
    
    private Assetattribute addAssetattribute;
    private ListClassspecVO deleteListClassspecVO;
    
    private boolean editButton=false;
    private Map dooleanCheckbox;
    
	public ListClassspecBean(){
		
	}
	
	@PostConstruct
	public void init() {
		 classstructure = new Classstructure();
		 deleteListClassspecVO=new ListClassspecVO();
		 listClassspecVO=new ArrayList<>();
	}
	public void onSearch(){
		ListClassspecBO listClassspecBO = SpringContextUtil.getBean(ListClassspecBO.class);
		listClassspecVO=listClassspecBO.getListClassspec(classstructure.getClassstructureid(),null,false);
		dooleanCheckbox = new LinkedHashMap();
		for(ListClassspecVO l:listClassspecVO)
		{
			dooleanCheckbox.put(l.getAssetattrid(), l.getItemrequirevalue().intValue()==1? Boolean.TRUE:Boolean.FALSE);
		}
		editButton=true;//可以編輯
	}
	public void onAddList(){  //新增屬性
		
		if(dooleanCheckbox.get(addAssetattribute.getAssetattrid())==null){
			ListClassspecVO l=new ListClassspecVO();
			BeanUtils.copyProperties(addAssetattribute, l);
			l.setClassstructureid(classstructure.getClassstructureid());
			l.setDomainid(l.getClassstructureid()+l.getAssetattrid());
			l.setItemsequence(new BigDecimal( listClassspecVO.size()+1));
			listClassspecVO.add(l);
			dooleanCheckbox.put(l.getAssetattrid(), Boolean.TRUE);
		}
	}
	public void onDelete(){  //刪除屬性
		listClassspecVO.remove(deleteListClassspecVO);
		dooleanCheckbox.remove(deleteListClassspecVO.getAssetattrid());
	}
	public void onUpdate(){  //更新屬性
		ListClassspecBO listClassspecBO = SpringContextUtil.getBean(ListClassspecBO.class);
		GlobalGrowl message = new GlobalGrowl();
		for(ListClassspecVO l:listClassspecVO){
			l.setItemrequirevalue(new BigDecimal((boolean) dooleanCheckbox.get(l.getAssetattrid())? "1":"0"));
			Classspec c = new Classspec();
			BeanUtils.copyProperties(l, c);
			listClassspecBO.updateListClassspecVO(c);
		}
		this.onSearch();
		message.addInfoMessage("Update", "Update successful.");
	}
//===================================================================================================

	public Classstructure getClassstructure() {
		return classstructure;
	}

	public void setClassstructure(Classstructure classstructure) {
		this.classstructure = classstructure;
	}

	public List<ListClassspecVO> getListClassspecVO() {
		return listClassspecVO;
	}

	public void setListClassspecVO(List<ListClassspecVO> listClassspecVO) {
		this.listClassspecVO = listClassspecVO;
	}

	public Assetattribute getAddAssetattribute() {
		return addAssetattribute;
	}

	public void setAddAssetattribute(Assetattribute addAssetattribute) {
		this.addAssetattribute = addAssetattribute;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public ListClassspecVO getDeleteListClassspecVO() {
		return deleteListClassspecVO;
	}

	public void setDeleteListClassspecVO(ListClassspecVO deleteListClassspecVO) {
		this.deleteListClassspecVO = deleteListClassspecVO;
	}

	public Map<String, Boolean> getDooleanCheckbox() {
		return dooleanCheckbox;
	}

	public void setDooleanCheckbox(Map<String, Boolean> dooleanCheckbox) {
		this.dooleanCheckbox = dooleanCheckbox;
	}	
	
}
