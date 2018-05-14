package mro.app.tempCreateItem.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyItemTransferSiteUtils;
import mro.app.applyItem.Utils.ApplyUtils;
import mro.app.applyItem.bo.ApplyItemBo;
import mro.app.applyItem.bo.ApplyItemChangeBo;
import mro.app.applyItem.bo.ApplyItemTransferSiteBo;
import mro.app.applyItem.jsf.ApplyItemBean;
import mro.app.applyItem.jsf.ApplyItemChangeBean;
import mro.app.applyItem.jsf.ApplyItemTransferSiteBean;
import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.commonview.bo.ListItemCommonBO;
import mro.app.commonview.services.FileUploadInterfaces;
import mro.app.commonview.services.Impl.FileUploadImpl;
import mro.app.sign.bo.ApplyItemSignBo;
import mro.app.sign.bo.ApplyItemTransferSiteSignBo;
import mro.app.sign.jsf.ApplyItemTransferSiteSignBean;
import mro.app.tempCreateItem.bo.TempCreateItemBO;
import mro.base.System.config.basicType.ActionType;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.AItemBO;
import mro.base.bo.FunctionBO;
import mro.base.bo.ItemTransferHeaderApplyBO;
import mro.base.bo.LocationMapBO;
import mro.base.bo.PersonBO;
import mro.base.entity.AItem;
import mro.base.entity.Item;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.Person;
import mro.form.ItemForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "TempCreateItem")
@ViewScoped
public class TempCreateItem implements Serializable {
	private static final long serialVersionUID = 6229289311944860076L;

	@ManagedProperty(value = "#{ApplyItemBean}")
	private ApplyItemBean applyItemBean;
	
	@ManagedProperty(value = "#{ApplyItemChangeBean}")
	private ApplyItemChangeBean applyItemChangeBean;
	
	@ManagedProperty(value = "#{ApplyItemTransferSiteBean}")
	private ApplyItemTransferSiteBean applyItemTransferSiteBean;
	
	@ManagedProperty(value = "#{ApplyItemTransferSiteSignBean}")
	private ApplyItemTransferSiteSignBean applyItemTransferSiteSignBean;
	
	private String status;
	private int fail;
	private int sucess;
	private String changeBySpecMaterialGroup = "'R1102','R1103','R1104','R1107','R1108'";

	@PostConstruct
	public void init() {
		fail = 0;
		sucess = 0;
		status = "";
	}

	public void submitItemTransferHeader() {
		ItemTransferHeaderApplyBO bo = SpringContextUtil.getBean(ItemTransferHeaderApplyBO.class);
		ApplyItemTransferSiteBo applyItemTransferSiteBo = SpringContextUtil.getBean(ApplyItemTransferSiteBo.class);
		List<ItemTransferHeaderApply> list=bo.getList(new BigDecimal("1986"), new BigDecimal("2362"),"NEW");
		for (ItemTransferHeaderApply header : list) {
			applyItemTransferSiteBean.SelectItemTransferHeaderApply(header);
			String warnMessage=ApplyItemTransferSiteUtils.vaildate(
					applyItemTransferSiteBean.getItemTransferSiteForm());
			if(warnMessage.length()>0){
				status=status+header.getApplyHeaderId()+warnMessage+"</br>";
				fail++;
			}else{
				sucess++;
				applyItemTransferSiteBo.onApplySave(
						applyItemTransferSiteBean.getItemTransferSiteForm(),ActionType.submit);
			}
		}
		status = status+ "submitItemTransferHeader 成功數：" + sucess + ",失敗數：" + fail + ",共處理："
				+ (sucess + fail) + " 個料號";
	}
	
	public void syncItemTransferHeader() {
		ApplyItemTransferSiteSignBo applyItemTransferSiteSignBo =SpringContextUtil.getBean(ApplyItemTransferSiteSignBo.class);
		ItemTransferHeaderApplyBO bo = SpringContextUtil.getBean(ItemTransferHeaderApplyBO.class);
		List<ItemTransferHeaderApply> list=bo.getList(new BigDecimal("1986"), new BigDecimal("2362"),"INPRG");
		for (ItemTransferHeaderApply header : list) {
			applyItemTransferSiteSignBean.selectApply(header);
			applyItemTransferSiteSignBo.setFinalAccept(
					applyItemTransferSiteSignBean.getItemTransferSiteForm());
				sucess++;
		}
		status = "syncItemTransferHeader 成功數：" + sucess + ",失敗數：" + fail + ",共處理："
				+ (sucess + fail) + " 個料號";
	}
	
	public void onFinalApprNoSignByChange() throws Exception {
		AItemBO aItemBO = SpringContextUtil.getBean(AItemBO.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		this.init();
		List<AItem> listAItem = aItemBO.getAItemListBySimple(sdf.parse("2015/07/13"));
		
		for (AItem aItem : listAItem) {
			applyItemChangeBean.selectApply(aItem);
			ItemInterface itemInterface=new ItemImpl();
			applyItemChangeBean.setItemForm(
					itemInterface.setApplySubmit(applyItemChangeBean.getItemForm()));
			boolean v=ApplyUtils.validate(applyItemChangeBean.getItemForm(),new GlobalGrowl());
			if(v){
//				applyItemChangeBean.onItemSelectSubmit();  //免送簽時，ITEMIMPL.onApplySave只保留免簽邏輯
				sucess++;
			}else{
				fail++;
			}
		}
		status = "onFinalApprNoSignByChange 成功數：" + sucess + ",失敗數：" + fail + ",共處理："
				+ (sucess + fail) + " 個料號";
	}
//	public void NewToInprg() throws UnsupportedEncodingException {
//		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
//		List<AItem> listAItem = applyItemBo.getAItemListTemp("",
//				SystemConfig.NEW);
//		this.init();
//		for (AItem a : listAItem) {
//			AItemAttribute aItemAttribute = applyItemBo.getAItemAttribute(a
//					.getEaudittransid().intValue());
//
//			if (aItemAttribute == null) {
//				aItemAttribute = new AItemAttribute();
//			}
//			if (aItemAttribute.getGpProductCategory() == null) {
//				aItemAttribute.setGpProductCategory("");
//			}
//			if (aItemAttribute.getMcContinuityUse() == null) {
//				aItemAttribute.setMcContinuityUse("");
//			}
//			if (aItemAttribute.getSecondItemFlag() == null) {
//				aItemAttribute.setSecondItemFlag("");
//			}
//
//			List<ListAItemspecVO> listAItemspecVO = applyItemBo
//					.getListAItemspecVOList(a.getEaudittransid().intValue());
//			List<ListAInvvendorVO> listListAInvvendorVO = new ArrayList<ListAInvvendorVO>();
//			List<AItemSecondItemnum> listAItemSecondItemnum = new ArrayList<AItemSecondItemnum>();
//			this.onApplySave(a, aItemAttribute, listAItemspecVO,
//					listListAInvvendorVO, listAItemSecondItemnum);
//		}
//		status = "NewToInprg 成功數：" + sucess + ",失敗數：" + fail + ",共處理："
//				+ (sucess + fail) + " 個料號";
//	}
//
//	public void onApplySave(ItemForm itemForm)
//			throws UnsupportedEncodingException { // 儲存草稿
//		GlobalGrowl message = new GlobalGrowl();
//		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
//
//		Object[] sItem = ApplyUtils.getIRItemNum(itemForm.getaItem());
//		BigDecimal Eaudittransid = (BigDecimal) sItem[0];
//		String ItemNum = (String) sItem[1];
//		// =============================產品/製程類別驗證==========================================================
//		itemForm = ApplyUtils.gpProductCategory(itemForm.getaItemAttribute(), ItemNum);
//		// =============================連續性用料驗證==========================================================
//		aItemAttribute = ApplyUtils.mcContinuityUse(itemForm.getaItemAttribute());
//		// =============================轉換數量==========================================================
//		if (StringUtils.isBlank(itemForm.getaItemAttribute().getPackageUnit())) {
//			itemForm.getaItemAttribute().setTransferQuantity(null);
//		}
//		// ==============================================================================
//		aItem.setDescription(ApplyUtils.getItemDescription(listAItemspecVO));// 短品名
//		aItem.setLongDescription(ApplyUtils
//				.getLongItemDescription(listAItemspecVO));// 品名
//		aItem.setEaudittimestamp(new Date(System.currentTimeMillis()));
//		if (ApplyUtils.validate(aItem, listListAInvvendorVO, listAItemspecVO,
//				aItemAttribute, listAItemSecondItemnum, message)) {
//			applyItemBo.onApplySave(aItem, Eaudittransid, ItemNum,
//					listAItemspecVO, listListAInvvendorVO, aItemAttribute,
//					listAItemSecondItemnum, "submit");
//			sucess++;
//		} else {
//			applyItemBo.onApplySave(aItem, Eaudittransid, ItemNum,
//					listAItemspecVO, listListAInvvendorVO, aItemAttribute,
//					listAItemSecondItemnum, "save");
//			fail++;
//		}
//	}

	/*
	 * 產生料號規格異動清單 hongjie.wu 2013/11/4
	 */
	public void AItemChangeBySpec() throws Exception {
		ListItemCommonBO listItemCommonBO = SpringContextUtil
				.getBean(ListItemCommonBO.class);
		List<Item> listItem = listItemCommonBO
				.getItemChangeListTemp(changeBySpecMaterialGroup);
		for (Item item : listItem) {
			applyItemChangeBean.setSelectItem(item);
			applyItemChangeBean.onItemSelectEdit("simpleSave");
		}
		status = status + "共" + listItem.size() + "顆料號<br />";
	}

	/*
	 * 刪除料號規格異動清單 hongjie.wu 2013/11/4
	 */
	public void AItemChangeBySpecDelete() throws IOException {
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		List<AItem> listAItem = applyItemBo.getAItemListBySpecTemp(
				changeBySpecMaterialGroup, SignStatus.NEW);
		this.init();
		int i = 0;
		for (AItem aItem : listAItem) {
			if (aItem.getEaudittransid() != null) { // 有暫存資料在刪除
				ApplyItemChangeBo applyItemChangeBo = SpringContextUtil
						.getBean(ApplyItemChangeBo.class);
				FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
				applyItemChangeBo.onApplyDelete(aItem);
				fileUploadInterfaces.fileDelete(aItem.getItemnum(),
						FileCategory.ITEM_ATTACHMENT, "");
				fileUploadInterfaces.fileDelete(aItem.getItemnum(),
						FileCategory.ITEM_PHOTO, "");
				i++;
				status = status + "第" + i + "個料號：" + aItem.getOriitemnum()
						+ ",申請單號：" + aItem.getItemnum() + "<br />";
			}
		}
		status = status + "共" + i + "顆料號<br />";
	}


//	/*
//	 * 產生料號驗證品名 hongjie.wu 2013/11/18
//	 */
//	public void createValidateDescription() throws IOException {
//		ListClassspecBO listClassspecBO = SpringContextUtil
//				.getBean(ListClassspecBO.class);
//		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
//		TempCreateItemBO tempCreateItemBO = SpringContextUtil
//				.getBean(TempCreateItemBO.class);
//		List<AItem> listAItem = tempCreateItemBO.getAItemList();
//		List<Item> listItem = tempCreateItemBO.getItemList();
//		int i = 0;
//		for (Item item : listItem) {
//			// ===========================================================================
//			List<ListAItemspecVO> listAItemspecVO = listClassspecBO
//					.getListClassspecToListAItemspecVO(item
//							.getClassstructureid(), null, item.getItemid()
//							.toString());
//			item.setValidateDescription(ApplyUtils
//					.getValidateDescription(listAItemspecVO));
//			tempCreateItemBO.updateItem(item);
//			i++;
//		}
//		status = status + "料號共" + i + "顆料號<br />";
//		i = 0;
//		for (AItem aItem : listAItem) {
//			List<ListAItemspecVO> listAItemspecVO = applyItemBo
//					.getListAItemspecVOList(aItem.getEaudittransid().intValue());
//			aItem.setValidateDescription(ApplyUtils
//					.getValidateDescription(listAItemspecVO));
//			tempCreateItemBO.updateAItem(aItem);
//			i++;
//		}
//		status = status + "異動料號共" + i + "顆料號<br />";
//	}

	/*
	 * CREATE 料號新增申請單 hongjie.wu 2014/1/20
	 */
	public void createApplyItem() throws IOException {
		TempCreateItemBO tempCreateItemBO = SpringContextUtil
				.getBean(TempCreateItemBO.class);
		FunctionBO functionBO = SpringContextUtil.getBean(FunctionBO.class);
		ApplyItemBo applyItemBo = SpringContextUtil.getBean(ApplyItemBo.class);
		PersonBO personBO = SpringContextUtil.getBean(PersonBO.class);
		LocationMapBO locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
		List<AItem> listAItem = tempCreateItemBO.getAItemList(
				new String[] { "status" }, "null");
		for (AItem aItem : listAItem) {
			String ItemNum = "IR-" + functionBO.getSquence("A_ITEM_SEQ"); // 取的新的ItemNum
			BigDecimal Eaudittransid = aItem.getEaudittransid();
			applyItemBean.newApply();
			applyItemBean.setClassstructureuid(aItem.getClassstructureid());
			applyItemBean.getItemForm().getaItem().setEaudittimestamp(
					new Date(System.currentTimeMillis()));
			applyItemBean.getItemForm().getaItem().setEauditusername(
					aItem.getEauditusername());
			applyItemBean.getItemForm().getaItem().setChangeby(aItem.getChangeby());

			Person person = personBO.getPerson(aItem.getChangeby());
			applyItemBean.getItemForm().getaItem().setOrganizationCode(
					person.getOrganizationCode());
			applyItemBean.getItemForm().getaItem().setDeptNo(person.getDeptCode());
			applyItemBo.onApplySave(applyItemBean.getItemForm(), Eaudittransid,
					ItemNum, "save");
		}
		status = status + "共" + listAItem.size() + "顆料號";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public ApplyItemBean getApplyItemBean() {
		return applyItemBean;
	}

	public void setApplyItemBean(ApplyItemBean applyItemBean) {
		this.applyItemBean = applyItemBean;
	}

	public ApplyItemChangeBean getApplyItemChangeBean() {
		return applyItemChangeBean;
	}

	public void setApplyItemChangeBean(ApplyItemChangeBean applyItemChangeBean) {
		this.applyItemChangeBean = applyItemChangeBean;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getSucess() {
		return sucess;
	}

	public void setSucess(int sucess) {
		this.sucess = sucess;
	}

	public String getChangeBySpecMaterialGroup() {
		return changeBySpecMaterialGroup;
	}

	public void setChangeBySpecMaterialGroup(String changeBySpecMaterialGroup) {
		this.changeBySpecMaterialGroup = changeBySpecMaterialGroup;
	}

	public ApplyItemTransferSiteBean getApplyItemTransferSiteBean() {
		return applyItemTransferSiteBean;
	}

	public void setApplyItemTransferSiteBean(
			ApplyItemTransferSiteBean applyItemTransferSiteBean) {
		this.applyItemTransferSiteBean = applyItemTransferSiteBean;
	}

	public ApplyItemTransferSiteSignBean getApplyItemTransferSiteSignBean() {
		return applyItemTransferSiteSignBean;
	}

	public void setApplyItemTransferSiteSignBean(
			ApplyItemTransferSiteSignBean applyItemTransferSiteSignBean) {
		this.applyItemTransferSiteSignBean = applyItemTransferSiteSignBean;
	}

}
