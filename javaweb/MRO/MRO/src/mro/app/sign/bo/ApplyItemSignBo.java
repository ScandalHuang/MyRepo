package mro.app.sign.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import mro.app.applyItem.Utils.ApplyUtils;
import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.applyItem.vo.ListAItemspecVO;
import mro.app.commonview.bo.FileUploadBO;
import mro.app.commonview.dao.FileUploadDAO;
import mro.app.sign.dao.ApplyItemSignDao;
import mro.base.System.config.basicType.ItemSiteTransferType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.FunctionBO;
import mro.base.bo.ItemSiteTransferLogBO;
import mro.base.bo.LocationMapBO;
import mro.base.bo.PersonBO;
import mro.base.bo.SapPlantAttributeBO;
import mro.base.entity.AItem;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.AItemspec;
import mro.base.entity.Attachment;
import mro.base.entity.Invvendor;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemSecondItemnum;
import mro.base.entity.ItemSite;
import mro.base.entity.Itemspec;
import mro.base.entity.LocationMap;
import mro.base.entity.Person;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.form.ItemForm;
import mro.utility.ExceptionUtils;
import mro.utility.ValidationUtils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;

@Component
@Scope("prototype")
public class ApplyItemSignBo {

	private ApplyItemSignDao applyItemSignDao;
	private SapPlantAttributeBO sapPlantAttributeBO;
	private FileUploadDAO fileUploadDAO;
	private ItemSiteTransferLogBO itemSiteTransferLogBO;
	private LocationMapBO locationMapBO;
	private PersonBO personBO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		applyItemSignDao = new ApplyItemSignDao(sessionFactory);
		fileUploadDAO = new FileUploadDAO(sessionFactory);
		sapPlantAttributeBO=SpringContextUtil.getBean(SapPlantAttributeBO.class);
		itemSiteTransferLogBO=SpringContextUtil.getBean(ItemSiteTransferLogBO.class);
		locationMapBO = SpringContextUtil.getBean(LocationMapBO.class);
		personBO = SpringContextUtil.getBean(PersonBO.class);
	}

	@Transactional(readOnly = false)
	public void onSign(ItemForm itemForm, SignStatus action, String loginEmpNo) {
		boolean signStaus = false;
		String apprStatus = "";
		String oriitemnum = "";
		AItem aItem=itemForm.getaItem();
		if (action.compareTo(SignStatus.REJECT)==0) {
			signStaus = WorkflowActionUtils.onReject(aItem.getTaskId(), loginEmpNo,
					itemForm.getSignComment());
		} else if (action.compareTo(SignStatus.TRANSFER)==0) {
			signStaus = WorkflowActionUtils.onTransfer(aItem.getTaskId(), loginEmpNo,
					itemForm.getSignComment(), itemForm.getPersonForward().getPersonId());
		} else if (action.compareTo(SignStatus.APPR)==0) {
			String mailContent=null;
			List<String> notifyList=new ArrayList<String>();
			if (!WorkflowActionUtils.getNextSignTask(aItem.getTaskId().intValue())) { // 沒有下一個簽核者(結案)
				oriitemnum=getOriItemnum(itemForm);
				LocationMap locationMap = locationMapBO.getLocationMapByOcode(aItem.getOrganizationCode());
				// =============================mailContent============================================
				mailContent = "料號：" + oriitemnum + "<br>" + "申請廠區："
						+ locationMap.getOrganizationName() + "<br>" + "品名："
						+ aItem.getDescription() + "<br>";

				// =============================結案另行通知==========================================
				String[] itemNotify = this.getItemNotify(aItem.getItemnum()).split(",");
				Stream.of(itemNotify).forEach(n->{
					Person person = personBO.getActivePerson(n);
					if (person != null && StringUtils.isNotBlank(person.getEmailAddress()))
						notifyList.add(person.getEmailAddress());});
			}
			apprStatus = WorkflowActionUtils.onAppr(aItem.getTaskId(), 
					loginEmpNo,itemForm.getSignComment(), mailContent,notifyList);
			if (StringUtils.isNotBlank(apprStatus)) {
				signStaus = true;
			}
		}

		if (!signStaus) {
			ExceptionUtils.showFalilException(itemForm.getaItem().getItemnum(),
					itemForm.getaItem().getTaskId(),
					"執行程序：" + action);
		} else {
			if (action.compareTo(SignStatus.REJECT)==0) {
				setAItem(itemForm.getaItem(), action);
			} else if (action.compareTo(SignStatus.APPR)==0) {
				if (apprStatus.equals(SignStatus.APPR.toString())) { // Finally Approve
					onFinalAccept(itemForm,oriitemnum);
				}
			}

		}
	}

	@Transactional(readOnly = false)
	// 確定同意(最後簽核者)
	public void onFinalAccept(ItemForm itemForm,String oriitemnum) {
		ApplyItemChangeSignBo applyItemChangeSignBo = SpringContextUtil
				.getBean(ApplyItemChangeSignBo.class);
		FileUploadBO fileUploadBO = SpringContextUtil
				.getBean(FileUploadBO.class);
		FunctionBO functionBO=SpringContextUtil.getBean(FunctionBO.class);
		ValidationUtils.validateStatus(itemForm.getaItem().getItemnum(),
				itemForm.getaItem().getTaskId(),ItemStatusType.TYPE_PROCESS_AS);
		itemForm.setListAttachment(fileUploadBO.getAttachmentList(itemForm
				.getaItem().getItemnum(), null, ""));
		if (itemForm.getaItem().getEaudittype().equals("I")) {
			BigDecimal itemId = functionBO.getSquence("ITEMSEQ"); // 取的新的itemID
			setFinalAccept(itemForm, itemId,oriitemnum);
		} else if (itemForm.getaItem().getEaudittype().equals("U")) {
			applyItemChangeSignBo.setFinalAccept(itemForm);
		}
	}
	@Transactional(readOnly = true)
	public AItemspec getAItemspec(String CLASSSTRUCTUREID, String ASSETATTRID,
			BigDecimal Eaudittransid) {

		StringBuffer condition = new StringBuffer();
		condition.append("and CLASSSTRUCTUREID ='" + CLASSSTRUCTUREID + "' ");
		condition.append("and ASSETATTRID ='" + ASSETATTRID + "' ");
		condition.append("and Eaudittransid ='" + Eaudittransid + "' ");

		return applyItemSignDao.getAItemspec(condition.toString());
	}

	@Transactional(readOnly = false)
	public void setAItem(AItem aItem, SignStatus status) {
		aItem.setStatus(status.toString());
		aItem.setEaudittimestamp(new Date(System.currentTimeMillis()));
		applyItemSignDao.insertUpdate(aItem);
		applyItemSignDao.updateItem(aItem.getOriitemnum(), SignStatus.ACTIVE);
	}

	@Transactional(readOnly = false)
	public void setFinalAccept(ItemForm itemForm, BigDecimal itemId,String oriitemnum) {
		Date date = new Date(System.currentTimeMillis());
		// ====================Item=================================
		Item item = new Item();
		BeanUtils.copyProperties(itemForm.getaItem(), item);
		item.setItemid(itemId);
		item.setItemnum(oriitemnum);
		//====================================================
		item.setStatus(SignStatus.SYNC.toString());
		item.setCreateDate(date);
		item.setChangeLastUpdate(date);
		item.setCreateBy(itemForm.getaItem().getChangeby());
		item.setCreateByDeptNo(itemForm.getaItem().getDeptNo());
		item.setCreateOrganizationCode(itemForm.getaItem().getOrganizationCode());
		applyItemSignDao.insertUpdate(item);
		// ====================AItem=================================
		itemForm.getaItem().setItem(item);
		// aItem.setItemnum(itemNum);
		itemForm.getaItem().setEaudittimestamp(date);
		itemForm.getaItem().setStatus(SignStatus.SYNC.toString());
		applyItemSignDao.insertUpdate(itemForm.getaItem());

		// ====================Item Spec====================================
		for (ListAItemspecVO l : itemForm.getListAItemspecVO()) {
			// l.setItemnum(itemNum);
			// l.setStatus(aItem.getStatus());
			AItemspec aItemspec = new AItemspec();
			BeanUtils.copyProperties(l, aItemspec);
			applyItemSignDao.insertUpdate(aItemspec);

			Itemspec itemspec = new Itemspec();
			BeanUtils.copyProperties(l, itemspec);
			itemspec.setItem(item);
			itemspec.setItemnum(item.getItemnum());
			// itemspec.setStatus(item.getStatus());
			itemspec.setChangedate(date);
			applyItemSignDao.insertUpdate(itemspec);
		}
		// ====================Invvendor====================================
		for (ListAInvvendorVO l : itemForm.getListListAInvvendorVO()) {
			Invvendor invvendor = new Invvendor();
			BeanUtils.copyProperties(l, invvendor);
			invvendor.setItem(item);
			invvendor.setItemnum(item.getItemnum());
			applyItemSignDao.insertUpdate(invvendor);
		}
		// ====================Second ====================================
		for (AItemSecondItemnum l : itemForm.getListAItemSecondItemnum()) {
			ItemSecondItemnum itemSecondItemnum = new ItemSecondItemnum();
			BeanUtils.copyProperties(l, itemSecondItemnum);
			itemSecondItemnum.setEaudittransid(l.getAItem().getEaudittransid());
			itemSecondItemnum.setItem(item);
			applyItemSignDao.insertUpdate(itemSecondItemnum);
		}
		// ===========================申請單其他屬性===================================
		ItemAttribute itemAttribute = new ItemAttribute();
		BeanUtils.copyProperties(itemForm.getaItemAttribute(), itemAttribute);
		itemAttribute.setEaudittransid(itemForm.getaItemAttribute().getAItem()
				.getEaudittransid());
		itemAttribute.setItem(item);
		applyItemSignDao.insertUpdate(itemAttribute);
		if (!itemForm.getaItem().getCommoditygroup().equals(ItemType.R94.toString())) {
			// ===========================ITEM_SITE========================================
			ItemSite itemSite = new ItemSite();
			itemSite.setItemid(item.getItemid());
			itemSite.setItemnum(item.getItemnum());
			itemSite.setLocationSite(applyItemSignDao.getLocationSiteMap(
					itemForm.getaItem().getOrganizationCode())
					.getLocationSite());
			itemSite.setLastUpdateDate(new Date(System.currentTimeMillis()));
			itemSite.setLastUpdatedBy(item.getCreateBy());
			applyItemSignDao.insertUpdate(itemSite);
			// ===========================Item_site_transfer_log==========================
			itemSiteTransferLogBO.update(sapPlantAttributeBO.getListBySite(
					itemSite.getLocationSite(), item.getClassstructureid(),true),
					item, itemForm.getaItem().getItemnum(), itemForm.getaItem().getChangeby(),
					ItemSiteTransferType.create, ItemSiteTransferType.insert);
		}
		// ===========================檔案=======================================
		for (Attachment attachmet : itemForm.getListAttachment()) {
			this.updateFile(attachmet, item.getItemnum(), date);
		}
	}

	@Transactional(readOnly = false)
	public void updateFile(Attachment attachment, String itemNum, Date date) {
		// ==========================新檔案==============================================
		Attachment NewAttachment = attachment;
		// applyItemSignDao.insertUpdate(photoAttachmentDownLoad);
		// ==========================新檔案==============================================
		NewAttachment.setFileId(fileUploadDAO.getFileId());
		NewAttachment.setKeyId(itemNum);
		NewAttachment.setCreateDate(date);
		applyItemSignDao.insertUpdate(NewAttachment);
	}

	@Transactional(readOnly = true)
	public String getItemNotify(String itemnum) {
		return applyItemSignDao.getItemNotify(itemnum);
	}	
	@Transactional(readOnly = true)
	public String getOriItemnum(ItemForm itemForm) {
		AItem aItem=itemForm.getaItem();
		if(StringUtils.isNotBlank(aItem.getOriitemnum())){
			return aItem.getOriitemnum();
		}else{
			if (itemForm.getaItem().getCommoditygroup().equals(ItemType.R94.toString())) { // 94特殊取號
				return ApplyUtils.getItemNum94(itemForm);
			} else {
				return ApplyUtils.getItemNum(itemForm); // 料號
			}
		}
	}
}
