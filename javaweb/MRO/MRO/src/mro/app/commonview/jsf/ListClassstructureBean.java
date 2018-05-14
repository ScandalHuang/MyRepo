package mro.app.commonview.jsf;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.bo.ListClassstructureBO;
import mro.base.entity.Classstructure;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.ReflectUtils;
import com.inx.commons.util.SpringContextUtil;


@ManagedBean(name="ListClassstructureBean")
@ViewScoped
public class ListClassstructureBean implements Serializable{
	private static final long serialVersionUID = -7575447799038282509L;
	private List<Classstructure> listClassstructure;
    private List<Classstructure> filterlistClassstructure;
    
    private Classstructure classstructure;
    private Object object;
    private String updateView;
    
    private Classstructure addClassstructure;
    
    private boolean addHasChildren;
    private boolean editButton=false;
    private boolean addButton=false;
    
	public ListClassstructureBean(){
		
	}
	
	@PostConstruct
	public void init() {
		 classstructure = new Classstructure();
		 addClassstructure=new Classstructure();
	}
	public void onSearch(){  //分類新增異動
		ListClassstructureBO listClassstructureBO = SpringContextUtil.getBean(ListClassstructureBO.class);
		String s="ROOT";  //預設大分類
		if(classstructure!=null)
			s=classstructure.getClassstructureid();
		
		listClassstructure=listClassstructureBO.getchildList(s);
		filterlistClassstructure=listClassstructure;
		editButton=true;//可以編輯
	}
	public void onSearchView(boolean activeFlag){ //料號新增異動
		this.onSearchView(activeFlag,null);
	}
	public void onSearchView(boolean activeFlag,String nonMaterial){ //料號新增異動
		ListClassstructureBO listClassstructureBO = SpringContextUtil.getBean(ListClassstructureBO.class);
		listClassstructure=listClassstructureBO.getRecursiveClassstructureList(0,activeFlag,nonMaterial); //找最小分類
		filterlistClassstructure=listClassstructure;
		editButton=false;//不可以編輯
	}
	
	public void onAdd(){
		ListClassstructureBO listClassstructureBO = SpringContextUtil.getBean(ListClassstructureBO.class);
		GlobalGrowl message = new GlobalGrowl();
		boolean vaildate=this.onvaildate(message); //空值驗證
		
		if(vaildate){
			String s="ROOT";  //預設大分類
			if(classstructure!=null )
				s=classstructure.getClassstructureid();
			
			String parentId=classstructure==null?"":classstructure.getClassstructureid();
			addClassstructure.setClassstructureid(parentId+addClassstructure.getClassstructureid());
			addClassstructure.setClassificationid(addClassstructure.getClassstructureid());
			addClassstructure.setParent(s);
			addClassstructure.setHaschildren(new Long (addHasChildren ? 1:0));
			addClassstructure.setEnterdate(new Timestamp(System.currentTimeMillis()));
			
			listClassstructureBO.addClassstructure(addClassstructure);
			message.addInfoMessage("Insert", "Insert successful.");
			
			
			addClassstructure=new Classstructure();
			addHasChildren=false;
			listClassstructure=listClassstructureBO.getchildList(s);
			filterlistClassstructure=listClassstructure;
		}
	}
	public boolean onvaildate(GlobalGrowl message){
		
		boolean vaildate=true;
		
		if(StringUtils.isEmpty(addClassstructure.getClassstructureid()))
		{message.addWarnMessage("Warn", "類別不能為空值!"); vaildate=false;}
		if(StringUtils.isEmpty(addClassstructure.getDescription()))
		{message.addWarnMessage("Warn", "類別名稱不能為空值!"); vaildate=false;}
		return vaildate;
	}
	
	public void setClassstructureuid(Classstructure classstructure) throws Exception {
		ReflectUtils.onReflectMethod(object, updateView, "setClassstructureuid", 
				classstructure.getClassificationid());
	}
	
	//===================================CommonMethod============================================
	
	//==================子分類===================================================
	public Classstructure getChildClassstructure(String classstructureid){
		ListClassstructureBO listClassstructureBO = SpringContextUtil.getBean(ListClassstructureBO.class);
		return classstructure=listClassstructureBO.getClassstructure(
				new String[]{"parent"},classstructureid);
	}
	//===========================================================================================
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

	public boolean isAddHasChildren() {
		return addHasChildren;
	}

	public void setAddHasChildren(boolean addHasChildren) {
		this.addHasChildren = addHasChildren;
	}

	public Classstructure getAddClassstructure() {
		return addClassstructure;
	}

	public void setAddClassstructure(Classstructure addClassstructure) {
		this.addClassstructure = addClassstructure;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public boolean isAddButton() {
		return addButton;
	}

	public void setAddButton(boolean addButton) {
		this.addButton = addButton;
	}

	public List<Classstructure> getFilterlistClassstructure() {
		return filterlistClassstructure;
	}

	public void setFilterlistClassstructure(
			List<Classstructure> filterlistClassstructure) {
		this.filterlistClassstructure = filterlistClassstructure;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getUpdateView() {
		return updateView;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}
	
}
