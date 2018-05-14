package mro.app.applyItem.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.app.applyItem.vo.ListAItemspecVO;
import mro.base.System.config.basicType.SecondItemType;
import mro.base.entity.AItemAttribute;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.Itemspec;
import mro.form.ItemForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.hibernate.ActiveFlag;

public class ApplyValidationUtils {
	 /*
	  * 驗證替代料號
	  */
	public static StringBuffer validateSecondItemList(ItemForm itemForm, StringBuffer warnMessage) {
		AItemAttribute attribute=itemForm.getaItemAttribute();
		if (StringUtils.isBlank(attribute.getSecondItemFlag())) {
			warnMessage.append("請先選擇是否有替代料號!<br />");
		}else if (attribute.getSecondItemFlag().equals(ActiveFlag.Y.name())){
			if(StringUtils.isBlank(attribute.getSecondItemType())) {
				warnMessage.append("請先選擇替代料號類型!<br />");
			}else{
				if(SecondItemType.valueOf(attribute.getSecondItemType()).secondSource()){
					if(StringUtils.isBlank(attribute.getSecondSourceItemnum())){
						warnMessage.append("當有替代料號時，請選擇替代料號!<br />");
					}else if(!attribute.getSecondSourceItemnum().substring(
							attribute.getSecondSourceItemnum().length()-1).equals("0")){
						warnMessage.append("當有替代料號類型為同規格，不同MAKER時，替代料號的最後碼必須為0!<br />");
					}else{
						//===========================類別結構驗證======================================
						warnMessage = ApplyItemTransferSiteUtils.validateClassstructureid(
								Arrays.asList(attribute.getSecondSourceItemnum()), 
								itemForm.getaItem().getClassstructureid(), warnMessage);
						//===========只驗證新增料號===========================
						if(StringUtils.isBlank(itemForm.getaItem().getOriitemnum()) && 
								warnMessage.indexOf("類別結構與此申請單不同")==-1){
							Map<String, Itemspec> spec=itemForm.getItemSpecMap();
							if(CollectionUtils.isEmpty(spec.values())){
								warnMessage.append("料號("+attribute.getSecondSourceItemnum()+
									")不可用於同規格，不同MAKER的替代類型!<br />");
							}else{
								if(!validate2nd(spec, itemForm.getListAItemspecVO())){
									warnMessage.append("2nd替代料號屬性有誤，請重新選取!<br />");
								}
							}
						}
					}
				}else{
					if(CollectionUtils.isEmpty(itemForm.getListAItemSecondItemnum())){
						warnMessage.append("當有替代料號時，請選擇替代料號!<br />");
					}else{
						//驗證料號是否有重複
						List<String> itemList=new ArrayList<String>();
						for(AItemSecondItemnum i:itemForm.getListAItemSecondItemnum()){
							itemList.add(i.getSecondItemnum());
						}
						if (StringUtils.isNotBlank(itemForm.getaItem().getOriitemnum())){  //規格異動料號要加入驗證
							itemList.add(itemForm.getaItem().getOriitemnum());
						}
						//===========================驗證料號是否有重複======================================
						warnMessage = ApplyPrValidationUtils.validateItemList(itemList, warnMessage);
						//===========================類別結構驗證======================================
						warnMessage = ApplyItemTransferSiteUtils.validateClassstructureid(itemList, 
								itemForm.getaItem().getClassstructureid(), warnMessage);
					}
				}
			}
		}
		return warnMessage;
	}
	public static boolean validate2nd(Map<String, Itemspec> spec,List<ListAItemspecVO> specVo){
		//========替代料料號屬性spec
		List itemspecs=new LinkedList();
		spec.values().stream().forEach((l)->{
			try {
				ListAItemspecVO vo=new ListAItemspecVO();
				BeanUtils.copyProperties(vo, l);
				itemspecs.add(vo);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		String sDescription=ApplyUtils.getDescription(itemspecs);
		//========申請單替代料號屬性spec
		List aitemSpecs=specVo.stream().filter(
				s->spec.get(s.getAssetattrid())!=null).collect(Collectors.toList());
		String description=ApplyUtils.getDescription(aitemSpecs);
		return sDescription.equals(description);
	}
}
