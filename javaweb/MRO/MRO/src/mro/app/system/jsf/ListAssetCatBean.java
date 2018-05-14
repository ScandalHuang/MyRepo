package mro.app.system.jsf;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.system.bo.ListAssetCatBO;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.ClassstructureBO;
import mro.base.entity.Classstructure;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.RowEditEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;


@ManagedBean(name="ListAssetCatBean")
@ViewScoped
public class ListAssetCatBean implements Serializable{ 
	private static final long serialVersionUID = 7318406252790662732L;
	private Map structs1;
    private Map structs2;
    private Map structs3;
    private Map structs4;
    private Map storeCategory; // 庫存分類
    
    private String sStructs1;
    private String sStructs2;
    private String sStructs3;
    private String sStructs4;
    
    private String description;  //類別名稱
    private Map dooleanCheckbox;
    
    private List<Classstructure> listClassstructure;
    
    private Classstructure selectClassstructure;
    private Classstructure classstructure;
    
    private transient ClassstructureBO classstructureBO;
    private transient ListAssetCatBO listAssetCatBO;
    
	public ListAssetCatBean(){
		
	}
	
	@PostConstruct
	public void init() {
		listAssetCatBO=SpringContextUtil.getBean(ListAssetCatBO.class);
		classstructureBO=SpringContextUtil.getBean(ClassstructureBO.class);
		structs1=SelectStruct("ROOT");  //大分類
		structs2=SelectStruct(structs1.values().toArray()[0].toString());  //中分類
		structs3=new LinkedHashMap();  //小分類
		structs4=new LinkedHashMap();  //小小分類
		
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		storeCategory=bean.getParameterOption().get(ParameterType.SAP_STORE_CATEGORY); // 庫存分類選項
	}
	
	public void structChange(long id){ //分類更新
		if(id==1){
			structs2=SelectStruct(sStructs1);  //中分類
			sStructs2="";
			structs3.clear();
			structs4.clear();
		}else if(id==2){
			structs3=SelectStruct(sStructs2);  //小分類
		}else if(id==3){
			structs4=SelectStruct(sStructs3);  //小小分類
		}
	}
	
	
	public Map SelectStruct(String classstructureid){ 
		ClassstructureBO classstructureBO = SpringContextUtil.getBean(ClassstructureBO.class);
		return classstructureBO.getChildMap(classstructureid);
		
	}
	public void onSearch(){
		getClssstructure(); //更新LIST
	}
	
	public void onEdit(RowEditEvent event) {
		Classstructure c=(Classstructure)event.getObject();
		c.setHaschildren(new Long((boolean) dooleanCheckbox.get(c.getClassstructureid())? 1:0)); 
		if(c.getHaschildren()==1){c.setStoreCategory(null);}//只有小分類才能有SOTRE_CATEGORY
		listAssetCatBO.update(c);
		GlobalGrowl message = new GlobalGrowl();
		message.addInfoMessage("Update",  "Update a record successful.");
	}
	public void onForm(){
		
	}

	public void ondialogClose(CloseEvent event) {  //分類視窗關閉後更新
		getClssstructure(); //更新LIST
		structs1=SelectStruct("ROOT");  //大分類
		structs2=SelectStruct(sStructs1);  //中分類
		structs3=SelectStruct(sStructs2);  //小分類
		structs4=SelectStruct(sStructs3);  //小小分類
	}
	public void getClssstructure(){
		listClassstructure=classstructureBO.getListById(sStructs1,sStructs2,sStructs3,sStructs4, description); //更新LIST
		dooleanCheckbox = new LinkedHashMap();
		
		for(Classstructure c:listClassstructure)
		{
			dooleanCheckbox.put(c.getClassstructureid(), c.getHaschildren()==1? Boolean.TRUE:Boolean.FALSE);
		}
	}
	
//===================================================================================
	public Map getStructs1() {
		return structs1;
	}

	public void setStructs1(Map structs1) {
		this.structs1 = structs1;
	}

	public Map getStructs2() {
		return structs2;
	}

	public void setStructs2(Map structs2) {
		this.structs2 = structs2;
	}

	public Map getStructs3() {
		return structs3;
	}

	public void setStructs3(Map structs3) {
		this.structs3 = structs3;
	}

	public String getsStructs1() {
		return sStructs1;
	}

	public void setsStructs1(String sStructs1) {
		this.sStructs1 = sStructs1;
	}

	public String getsStructs2() {
		return sStructs2;
	}

	public void setsStructs2(String sStructs2) {
		this.sStructs2 = sStructs2;
	}

	public String getsStructs3() {
		return sStructs3;
	}

	public void setsStructs3(String sStructs3) {
		this.sStructs3 = sStructs3;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Classstructure> getListClassstructure() {
		return listClassstructure;
	}

	public void setListClassstructure(List<Classstructure> listClassstructure) {
		this.listClassstructure = listClassstructure;
	}

	public Classstructure getClassstructure() {
		return classstructure;
	}

	public void setClassstructure(Classstructure classstructure) {
		this.classstructure = classstructure;
	}

	public Classstructure getSelectClassstructure() {
		return selectClassstructure;
	}

	public void setSelectClassstructure(Classstructure selectClassstructure) {
		this.selectClassstructure = selectClassstructure;
	}

	public Map getDooleanCheckbox() {
		return dooleanCheckbox;
	}

	public void setDooleanCheckbox(Map dooleanCheckbox) {
		this.dooleanCheckbox = dooleanCheckbox;
	}

	public Map getStoreCategory() {
		return storeCategory;
	}

	public void setStoreCategory(Map storeCategory) {
		this.storeCategory = storeCategory;
	}

	public Map getStructs4() {
		return structs4;
	}

	public void setStructs4(Map structs4) {
		this.structs4 = structs4;
	}

	public String getsStructs4() {
		return sStructs4;
	}

	public void setsStructs4(String sStructs4) {
		this.sStructs4 = sStructs4;
	}
	
}
