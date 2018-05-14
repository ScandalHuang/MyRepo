package mro.app.sign.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.applyItem.vo.ListAItemspecVO;
import mro.app.commonview.dao.FileUploadDAO;
import mro.app.sign.dao.ApplyItemChangeSignDao;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ItemSiteTransferType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.ItemSiteTransferLogBO;
import mro.base.bo.SapPlantAttributeBO;
import mro.base.entity.AInvvendor;
import mro.base.entity.AItem;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.AItemspec;
import mro.base.entity.Attachment;
import mro.base.entity.Invvendor;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemSecondItemnum;
import mro.base.entity.Itemspec;
import mro.form.ItemForm;
import mro.utility.ValidationUtils;

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
public class ApplyItemChangeSignBo {

    private ApplyItemChangeSignDao applyItemChangeSignDao;
	private SapPlantAttributeBO sapPlantAttributeBO;
	private FileUploadDAO fileUploadDAO;
	private ItemSiteTransferLogBO itemSiteTransferLogBO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	applyItemChangeSignDao=new ApplyItemChangeSignDao(sessionFactory);
    	fileUploadDAO=new FileUploadDAO(sessionFactory);
		sapPlantAttributeBO=SpringContextUtil.getBean(SapPlantAttributeBO.class);
		itemSiteTransferLogBO=SpringContextUtil.getBean(ItemSiteTransferLogBO.class);
    }

	
	@Transactional(readOnly=true)
    public List<AItem> getAItemSignList(List<BigDecimal> wString) {
		StringBuffer condition=new StringBuffer(); 
			condition.append("and STATUS ='"+SignStatus.INPRG+"' ");
			condition.append("and ORIITEMNUM is not null ");
			condition.append("and ( TASK_ID=-1 ");
			for(BigDecimal s:wString){
				condition.append(" or TASK_ID="+s);
			}
			condition.append(")");
			
			
	   return applyItemChangeSignDao.getAItemList(condition.toString());
	}
	
	@Transactional(readOnly=false)
	public void setFinalAccept(ItemForm itemForm){
		Date date = new Date(System.currentTimeMillis());
		String itemNum=itemForm.getaItem().getOriitemnum();
		ValidationUtils.validateStatus(itemForm.getaItem().getItemnum(),
				itemForm.getaItem().getTaskId(),ItemStatusType.TYPE_PROCESS_AS);
		//====================AItem=================================
		itemForm.getaItem().setEaudittimestamp(date);
		itemForm.getaItem().setStatus(SignStatus.SYNC.toString());
		applyItemChangeSignDao.insertUpdate(itemForm.getaItem());
		//====================Item=================================
		Item item=applyItemChangeSignDao.getItem(itemForm.getaItem().getItem().getItemid().intValue());
		item.setStatus(SignStatus.SYNC.toString());
        item.setDescription(itemForm.getaItem().getDescription());
        item.setIssueunit(itemForm.getaItem().getIssueunit());
        item.setChangeby(itemForm.getaItem().getChangeby());
        item.setValidateDescription(itemForm.getaItem().getValidateDescription());
        item.setRemark(itemForm.getaItem().getRemark());
        item.setStorageCondition(itemForm.getaItem().getStorageCondition());
        item.setTotalShelfLife(itemForm.getaItem().getTotalShelfLife());
        item.setMinShelfLife(itemForm.getaItem().getMinShelfLife());
        item.setChangeLastUpdate(date);
        applyItemChangeSignDao.insertUpdate(item);
			//====================Item Spec====================================
        	applyItemChangeSignDao.deleteItemSpec(item.getItemid()); //先刪除spec
			for(ListAItemspecVO l:itemForm.getListAItemspecVO()){
				AItemspec aItemspec=new AItemspec();
				BeanUtils.copyProperties(l, aItemspec);
				applyItemChangeSignDao.insertUpdate(aItemspec);
				
				Itemspec itemspec=new Itemspec();
				BeanUtils.copyProperties(l, itemspec);
				itemspec.setItem(item);
				itemspec.setItemnum(itemNum);
				applyItemChangeSignDao.insertUpdate(itemspec);
			}
			//====================Invvendor====================================
			applyItemChangeSignDao.updateInvvendor(item.getItemid().intValue());//先將所有的供應商disable=1
			for(ListAInvvendorVO l:itemForm.getListListAInvvendorVO()){
				//l.setItemnum(itemNum);
				AInvvendor aInvvendor=new AInvvendor();
				BeanUtils.copyProperties(l, aInvvendor);
				applyItemChangeSignDao.insertUpdate(aInvvendor);
				
				Invvendor invvendor=applyItemChangeSignDao.getInvvendor(item.getItemid().intValue(), l.getVendor());
				if(invvendor==null){
					invvendor=new Invvendor();
					BeanUtils.copyProperties(l, invvendor);
					invvendor.setItem(item);
					invvendor.setItemnum(itemNum);
				}else{
					invvendor.setDisabled(new BigDecimal(0));
				}
				applyItemChangeSignDao.insertUpdate(invvendor);
			}
			// ===============替代料號新增================================
			applyItemChangeSignDao.deleteAItemSecondItemnum(item.getItemid()); // 替代料號清空
			for (AItemSecondItemnum l : itemForm.getListAItemSecondItemnum()) {
				ItemSecondItemnum itemSecondItemnum=new ItemSecondItemnum();
				BeanUtils.copyProperties(l, itemSecondItemnum);
				itemSecondItemnum.setEaudittransid(l.getAItem().getEaudittransid());
				itemSecondItemnum.setItem(item);
				applyItemChangeSignDao.insertUpdate(itemSecondItemnum);
			}
			// ===========================申請單其他屬性===================================
			ItemAttribute itemAttribute=applyItemChangeSignDao.getItemAttribute(item.getItemid().intValue());
			BeanUtils.copyProperties(itemForm.getaItemAttribute(), itemAttribute);
			itemAttribute.setEaudittransid(itemForm.getaItemAttribute().getAItem().getEaudittransid());
			applyItemChangeSignDao.insertUpdate(itemAttribute);
			if(!itemForm.getaItem().getCommoditygroup().equals(ItemType.R94.toString())){
				// ===========================Item_site_transfer_log==========================
				itemSiteTransferLogBO.update(sapPlantAttributeBO.getListByItem(
						item.getItemnum(), item.getClassstructureid()),
						item, itemForm.getaItem().getItemnum(), itemForm.getaItem().getChangeby(),
						ItemSiteTransferType.change, ItemSiteTransferType.change);
			}
			//===========================檔案=======================================
			fileUploadDAO.deleteAttachmentList(" and key_id='"+itemNum+"' ");
			for(Attachment attachmet:itemForm.getListAttachment()){
				this.updateFile(attachmet, itemNum, date);
			}
	}
	@Transactional(readOnly=false)
	public void updateFile(Attachment attachment,String itemNum,Date date){
		//==========================新檔案==============================================
		Attachment NewAttachment=attachment;
		//applyItemSignDao.insertUpdate(photoAttachmentDownLoad);
		//==========================新檔案==============================================
		NewAttachment.setFileId(fileUploadDAO.getFileId());
		NewAttachment.setKeyId(itemNum);
		NewAttachment.setCreateDate(date);
		applyItemChangeSignDao.insertUpdate(NewAttachment);
	}
}
