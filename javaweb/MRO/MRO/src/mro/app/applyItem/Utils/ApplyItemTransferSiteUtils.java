package mro.app.applyItem.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.AItemBO;
import mro.base.bo.InvbalancesBO;
import mro.base.bo.ItemBO;
import mro.base.bo.ItemTransferLineApplyBO;
import mro.base.bo.PrlineBO;
import mro.base.bo.ZErpUnprocessPrBO;
import mro.base.entity.AItem;
import mro.base.entity.Invbalances;
import mro.base.entity.Item;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.base.entity.Prline;
import mro.base.entity.ZErpUnprocessPr;
import mro.form.ItemTransferSiteForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ApplyItemTransferSiteUtils {

	public static String vaildate(ItemTransferSiteForm itemTransferSiteForm) {
		StringBuffer warnMessage = new StringBuffer();
		warnMessage = warnMessage.append(vaildateHeader(itemTransferSiteForm
				.getItemTransferHeaderApply()));

		if (itemTransferSiteForm.getListItemTransferLineApply().size() == 0) {
			warnMessage.append("請選擇生效料號!<br />");
		} else {
			List<String> itemList = new ArrayList<String>();
			for (ItemTransferLineApply i : itemTransferSiteForm
					.getListItemTransferLineApply()) {
				itemList.add(i.getItemnum());

				if (StringUtils.isBlank(i.getLineRemark())) {
					warnMessage
							.append("項次" + i.getLineNum() + " ：說明為必填!<br />");
				}
			}
			// ===========================類別結構驗證======================================
			warnMessage = validateClassstructureid(itemList,
					itemTransferSiteForm.getItemTransferHeaderApply()
							.getClassstructureid(), warnMessage);
			// =============================料號狀態判斷==========================
			warnMessage = ApplyPrValidationUtils.validateNeStautsItem(itemList,
					ItemStatusType.TYPE_AC.getValue(), warnMessage);
			// ===========================驗證料號是否有重複======================================
			warnMessage = ApplyPrValidationUtils.validateItemList(itemList,
					warnMessage);

			// =============================送審或同步中申請單==========================
			warnMessage = validateProcessHeaderApply(itemList,
					itemTransferSiteForm.getItemTransferHeaderApply()
							.getLocationSite(), null, warnMessage);

			// ========================================================================
			if (itemTransferSiteForm.getItemTransferHeaderApply().getAction()
					.equals(LocationSiteActionType.S.name())) { // 凍結
				// =============================區域判斷(未生效)==========================
				warnMessage = ApplyPrValidationUtils.validateItemSiteInactvie(
						itemList,
						itemTransferSiteForm.getItemTransferHeaderApply()
								.getLocationSite(), warnMessage);

				// =============================送審或同步中規格異動申請單=============
				warnMessage = validateProcessAItem(itemList,
						itemTransferSiteForm.getItemTransferHeaderApply()
								.getLocationSite(), warnMessage);
				//============2016.05.06 取消驗證 改由寫入extraRemark===================
				// =============================送審中mro pr==========================
//				warnMessage.append(vaildateInprgPrline(itemList,
//						itemTransferSiteForm.getItemTransferHeaderApply()
//								.getLocationSite()));
//				// =============================送審中oracle pr=======================
//				warnMessage.append(vaildateInprgOraclePR(itemList,
//						itemTransferSiteForm.getItemTransferHeaderApply()
//								.getLocationSite()));
//				// =============================有控管量==========================
//				warnMessage.append(vaildateInvbalances(itemList,
//						itemTransferSiteForm.getItemTransferHeaderApply()
//								.getLocationSite()));
			} else if (itemTransferSiteForm.getItemTransferHeaderApply()
					.getAction().equals(LocationSiteActionType.I.name())) { // 生效
				// =============================區域判斷(已生效)==========================
				warnMessage = ApplyPrValidationUtils.validateItemSiteActive(
						itemList,
						itemTransferSiteForm.getItemTransferHeaderApply()
								.getLocationSite(), warnMessage);
			} else {
				warnMessage.append("ACTION ERROR");
			}
		}
		return warnMessage.toString();

	}

	public static String vaildateHeader(
			ItemTransferHeaderApply itemTransferHeaderApply) {
		StringBuffer warnMessage = new StringBuffer();
		if (StringUtils.isBlank(itemTransferHeaderApply.getClassstructureid())) // 必須先選擇類別結構
		{
			warnMessage.append("請先選擇類別結構!<br />");
		}
		if (StringUtils.isBlank(itemTransferHeaderApply.getLocationSite())) // 必須先選擇Site
		{
			warnMessage.append("[Site]必須選取!<br />");
		}
		return warnMessage.toString();

	}

	public static String vaildateInprgPrline(List<String> itemnum,
			String locationSite) {
		StringBuffer warnMessage = new StringBuffer();
		PrlineBO prlineBO = SpringContextUtil.getBean(PrlineBO.class);
		List<Prline> list = prlineBO.getPrlineBySite(itemnum, locationSite,
				SignStatus.INPRG);
		for (Prline prline : list) {
			warnMessage.append("料號(" + prline.getItemnum() + ") 在申請區域( "
					+ locationSite + " ) " + "有申請單( " + prline.getPrnum()
					+ " )簽核中!<br />");
		}
		return warnMessage.toString();

	}

	public static String vaildateInprgOraclePR(List<String> itemnum,
			String locationSite) {
		StringBuffer warnMessage = new StringBuffer();
		ZErpUnprocessPrBO zErpUnprocessPrBO = SpringContextUtil
				.getBean(ZErpUnprocessPrBO.class);
		List<ZErpUnprocessPr> list = zErpUnprocessPrBO
				.getZErpUnprocessPrListBySite(itemnum, locationSite);
		for (ZErpUnprocessPr zErpUnprocessPr : list) {
			warnMessage.append("料號(" + zErpUnprocessPr.getPartNo()
					+ ") 在Oracle " + "有待處理請購申請單( " + zErpUnprocessPr.getPrNo()
					+ " )!<br />");
		}
		return warnMessage.toString();

	}

	public static String vaildateInvbalances(List<String> itemnum,
			String locationSite) {
		StringBuffer warnMessage = new StringBuffer();
		InvbalancesBO invbalancesBO = SpringContextUtil
				.getBean(InvbalancesBO.class);
		List<Invbalances> list = invbalancesBO.getInvbalancesBySite(itemnum,
				locationSite);
		for (Invbalances invbalances : list) {
			warnMessage.append("料號(" + invbalances.getItemnum() + ") " + "在( "
					+ invbalances.getLocation() + " )有控管量  "
					+ invbalances.getMinlevel() + " !<br />");
		}
		return warnMessage.toString();

	}

	public static String validateInprgTransfer(List<String> itemList,String siteid, String action) {
		StringBuffer warnMessage = new StringBuffer();
		ItemTransferLineApplyBO itemTransferLineApplyBO = SpringContextUtil
				.getBean(ItemTransferLineApplyBO.class);
		if (Utility.isNotEmpty(itemList)) {
			List<String> signStausList = new ArrayList<String>();
			signStausList.add(SignStatus.INPRG.toString());
			signStausList.add(SignStatus.SYNC.toString());
			List<String> list = itemTransferLineApplyBO.getInprgItem(itemList,
					siteid, signStausList, action);
			for (String s : list) {
				warnMessage.append("料號(" + s + ") 正進行區域凍結申請，不可送審!<br />");
			}
		}
		return warnMessage.toString();
	}

	public static StringBuffer validateClassstructureid(List<String> itemList,
			String classstructureid, StringBuffer warnMessage) {
		ItemBO itemBO = SpringContextUtil.getBean(ItemBO.class);
		if (Utility.isNotEmpty(itemList)) {
			List<Item> items = itemBO.validateClassstructureid(itemList,
					classstructureid);
			if (Utility.isNotEmpty(items)) {
				for (Item i : items) {
					warnMessage.append("料號(" + i.getItemnum()
							+ ")類別結構與此申請單不同!<br />");
				}
			}
		}

		return warnMessage;
	}

	public static StringBuffer validateProcessHeaderApply(
			List<String> itemList, String locationSite, String action,
			StringBuffer warnMessage) {
		ItemTransferLineApplyBO itemTransferLineApplyBO = SpringContextUtil
				.getBean(ItemTransferLineApplyBO.class);
		if (Utility.isNotEmpty(itemList)) {
			List<ItemTransferLineApply> list = itemTransferLineApplyBO
					.getItemTransferLineApplyList(itemList, locationSite,
							action, SignStatus.INPRG.toString(),
							SignStatus.SYNC.toString());
			if (Utility.isNotEmpty(list)) {
				Map map = LocationSiteActionType.getMap();
				for (ItemTransferLineApply i : list) {
					warnMessage.append("料號"
							+ i.getItemnum()
							+ "在區域"
							+ i.getItemTransferHeaderApply().getLocationSite()
							+ "有申請單("
							+ i.getItemTransferHeaderApply()
									.getApplyHeaderNum()
							+ ")正進行"
							+ map.get(i.getItemTransferHeaderApply()
									.getAction()) + "申請!<br/>");
				}
			}
		}

		return warnMessage;
	}

	public static StringBuffer validateProcessAItem(List<String> itemList,
			String locationSite, StringBuffer warnMessage) {
		AItemBO aItemBO = SpringContextUtil.getBean(AItemBO.class);
		if (Utility.isNotEmpty(itemList)) {
			List<AItem> list = aItemBO.getAItemList(itemList, locationSite,ItemStatusType.TYPE_IS);
			if (Utility.isNotEmpty(list)) {
				for (AItem i : list) {
					warnMessage.append("料號" + i.getOriitemnum() + "有申請單("
							+ i.getItemnum() + ")在區域" + locationSite
							+ "進行規格異動申請!<br/>");
				}
			}
		}

		return warnMessage;
	}
}
