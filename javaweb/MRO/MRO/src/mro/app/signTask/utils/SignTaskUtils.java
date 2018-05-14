package mro.app.signTask.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import mro.base.bo.MroSignTaskListVBO;
import mro.base.bo.SignProcessBO;
import mro.base.entity.view.MroSignTaskListV;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.inx.commons.util.SpringContextUtil;

public class SignTaskUtils {

	public static String vaildateSubmit(String empno,int processId) {
		SignProcessBO signProcessBo = SpringContextUtil
				.getBean(SignProcessBO.class);
		
		if(processId==0 || StringUtils.isBlank(empno)){return  "未指定簽核流程!";}
		
		if(signProcessBo.getSignProcess(new BigDecimal(processId),true)==null){return  "指定簽核流程錯誤!";}
		
		return "";
	}
	
	public static String vaildateSignList(List<MroSignTaskListV> signHistoryVOList) {
		if(signHistoryVOList==null) return  "系統來源簽核人員有誤，請洽該申請模式與廠區的負責人員(首頁->聯絡窗口)!";
		for(MroSignTaskListV s:signHistoryVOList){
			if(s==null){
				return  "系統來源簽核人員有誤，請洽該申請模式與廠區的負責人員(首頁->聯絡窗口)!";
			}
		}
		
		return "";
	}

	public static boolean vaildateApprove(BigDecimal taskId, String empNO) {
		MroSignTaskListVBO mroSignTaskListVBO=SpringContextUtil.getBean(MroSignTaskListVBO.class);
		if (taskId == null|| StringUtils.isBlank(empNO) ||
			mroSignTaskListVBO.getNowListCount(taskId, empNO)!=1) {
//			SignTask s=SpringContextUtil.getBean(SignTaskBO.class).getSignTask(taskId);
//			List list=mroSignTaskListVBO.getListByTaskId(taskId);
			return false;
		}
		return true;
	}

	public static Map getSignParameter(String param){
		ObjectMapper mapper = new ObjectMapper();
		Map map=new HashedMap();
		try {
			if(StringUtils.isNotBlank(param)){
				map=mapper.readValue(param, Map.class);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
