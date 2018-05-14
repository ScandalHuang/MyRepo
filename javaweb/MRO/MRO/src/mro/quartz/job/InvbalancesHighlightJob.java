package mro.quartz.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mro.app.mcMgmtInterface.bo.ListItemHighlightBO;
import mro.app.mcMgmtInterface.form.HighlightForm;
import mro.base.System.config.basicType.ItemType;
import mro.base.bo.PersonBO;
import mro.base.entity.MroInvbalancesHighlightV;
import mro.base.entity.Person;
import mro.utility.DateUtils;

import com.inx.commons.util.SpringContextUtil;

/*
 * 	控管量Monitor下修
 */
public class InvbalancesHighlightJob {
	private List<String> prs = new ArrayList<String>();
	private ListItemHighlightBO listItemHighlightBO;
	private PersonBO personBO;
	private final String action = "initalMinlevel";

	public InvbalancesHighlightJob(){
	}
	public void start(){
		listItemHighlightBO = SpringContextUtil.getBean(ListItemHighlightBO.class);
		personBO = SpringContextUtil.getBean(PersonBO.class);
		
		Person person = personBO.getPerson("-1");
		// ================先將未屬於highlight範圍內的值清空======================
		listItemHighlightBO.updateNonHighLight();

		// ================進行自動開單判斷========================

		HighlightForm highlightForm = new HighlightForm();
		highlightForm.setSearchHistory(false); // 所有highlight單據都找出來
		List<MroInvbalancesHighlightV> list = listItemHighlightBO
				.getHighlightList(highlightForm);
		for (MroInvbalancesHighlightV v : list) { // highlight清單
			if (v.getHighlightInitialDate() == null) { // 第一次highlight,將最後回覆內容清空並上highligh日期
				v.setHighlightInitialDate(new Date(System.currentTimeMillis()));
				listItemHighlightBO.updateInvbalances(v.getInvbalancesid(),
						person, null);
			} else { // 以下用階層式表示比較容易理解
				if (v.getLastHighlightResponseDate() == null) { // 7天內未回覆,直接下修為建議量
					if (DateUtils.subtractDay(v.getHighlightInitialDate(),
							new Date()) > 7) {
						this.updateHighLightLog(v, person);
					}
				} else {// 有回覆
					if (v.getCommoditygroup().equals(ItemType.R1.toString())) {// 月份相減>0代表為第二次highligh
						if (DateUtils.subtractMonth(
								v.getHighlightInitialDate(), new Date()) > 0) {
							this.updateHighLightLog(v, person);
						}
					} else if (v.getCommoditygroup().equals(
							ItemType.R2.toString())) { // >30天代表為第二次highligh
						if (DateUtils.subtractDay(v.getHighlightInitialDate(),
								new Date()) > 30) {
							this.updateHighLightLog(v, person);
						}
					}
				}
			}
		}
	}

	public void updateHighLightLog(MroInvbalancesHighlightV v, Person person) {
		HighlightForm highlightForm = new HighlightForm();
		highlightForm.setMroInvbalancesHighlightV(v);
		highlightForm.setEditButton(true); // 設定為管理員
		highlightForm.setChangeQty(); // 設定下修量=建議量
		if (listItemHighlightBO.updateHighLightLog(highlightForm, person,
				action, null)) {
			prs.add(highlightForm.getInvbalancesHighlightLog().getPr()
					.getPrnum());
		}
	}

}
