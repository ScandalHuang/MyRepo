package mro.app.mcMgmtInterface.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



import org.apache.commons.beanutils.BeanUtils;



import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.UploadInterfaces;
import mro.app.mcMgmtInterface.form.MroInventoryIdleListForm;
import mro.app.mcMgmtInterface.service.ListUpLoadInterface;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.MroInventoryIdleListBO;
import mro.base.bo.SubinventoryConfigBO;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroInventoryIdleList;
import mro.base.entity.SubinventoryConfig;



import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ListMroInventoryIdleImpl implements ListUpLoadInterface,UploadInterfaces {

	private MroInventoryIdleListBO bo;
	private MroInventoryIdleListForm form;
	public ListMroInventoryIdleImpl(){
		
	}
	public ListMroInventoryIdleImpl(Object form){
		bo=SpringContextUtil.getBean(MroInventoryIdleListBO.class);
		this.form =(MroInventoryIdleListForm) form;
	}
	
	/** 取得SubinventoryConfig mapping更新後的廠區 */
	public List<MroInventoryIdleList> getVMroInventoryIdleList(List<MroInventoryIdleList> list){
		SubinventoryConfigBO subinventoryConfigBO=SpringContextUtil.getBean(SubinventoryConfigBO.class);
		Map<String,SubinventoryConfig> mapping=subinventoryConfigBO.getMap();//取得SubinventoryConfig mapping
		List<MroInventoryIdleList> vList=new ArrayList<MroInventoryIdleList>();
		list.forEach(l->{
			MroInventoryIdleList temp=new MroInventoryIdleList();
			try {
				BeanUtils.copyProperties(temp, l);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e.getMessage());
			}
			SubinventoryConfig s=mapping.get(temp.getOrganizationCode()+","+temp.getLocation());
			if(s!=null) temp.setOrganizationCode(s.getAssignOrganizationCode());
			vList.add(temp);
		});
		return vList;
	}
	
	@Override
	public void onSearch() {
		form.intial();
		if(Utility.isValueNotEmpty(form.getsLocationSiteMap(),form.getSelectPlantCode())){
			form.setList(bo.getListByOrg(form.getsLocationSiteMap(), form.getSelectPlantCode()));
			form.copyList();
		}
	}
	@Override
	public void onDelete(GlobalGrowl message) {
		bo.delete(Arrays.asList(form.getDeleteList()));
		if(message!=null) message.addInfoMessage("Delete", "Delete successful!");
	}

	@Override
	public void onSelectAll() {
		form.setDeleteList((MroInventoryIdleList[]) 
				ApplyPrUtils.onSelectAll(form.getDeleteList(), form.getList()));
	}
	@Override
	public void setParameter() {
		
	}

	@Override
	public void preFunction(List objects) {
		SubinventoryConfigBO subinventoryConfigBO=SpringContextUtil.getBean(SubinventoryConfigBO.class);
		Map<String,SubinventoryConfig> mapping=subinventoryConfigBO.getMap();//取得SubinventoryConfig mapping
		
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		((List<MroInventoryIdleList>)objects).forEach(l->{
			String key=l.getOrganizationCode()+","+l.getLocation(); //SubinventoryConfig key(B003+380A)
			LocationMap locationMap=bean.getLocationMap().get(l.getOrganizationCode());
			if(locationMap!=null&&locationMap.getLocationSiteMap()!=null) {
				l.setLocationSiteMap(locationMap.getLocationSiteMap());
			}
			else {
				l.setLocationSiteMap(new LocationSiteMap(mapping.get(key).getLocationSite()));
			}
		});
	}
	@Override
	public boolean assignFunctionFlag() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void assignFunction(List objects) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean validate(List objects) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void excute(List objects) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroyed(List objects) {
		// TODO Auto-generated method stub
		
	}

}
