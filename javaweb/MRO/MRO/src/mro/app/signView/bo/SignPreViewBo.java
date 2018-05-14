package mro.app.signView.bo;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.app.signTask.service.ActorInterface;
import mro.app.signTask.service.ValidateInterface;
import mro.base.bo.HrEmpBO;
import mro.base.bo.HrOrgBO;
import mro.base.bo.SignProcessListBO;
import mro.base.entity.HrEmp;
import mro.base.entity.HrOrg;
import mro.base.entity.SignProcessList;
import mro.base.entity.view.MroSignTaskListV;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;

 

@Component
@Scope("prototype")
public class SignPreViewBo {

    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void setinit(SessionFactory sessionFactory) {
	}
    
    // ==============送簽者==================================
    @Transactional(readOnly = true)
	public MroSignTaskListV setSignHistory(String empno,String signerComment, String signDescription) {
    	HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
    	if(StringUtils.isNotBlank(empno)){
			HrEmp hrEmp = hrEmpBO.getHrEmp(empno);
			if(hrEmp==null){return null;}
			MroSignTaskListV signHistoryVO = new MroSignTaskListV();
			signHistoryVO.setSignDescription(signDescription);
			signHistoryVO.setActorId(empno);
			signHistoryVO.setActorDept(hrEmp.getDeptNo());
			signHistoryVO.setSignerComment(signerComment);
			
			return signHistoryVO;
    	}
    	return null;
	}
    
    // ==============增加簽核者==================================
    @Transactional(readOnly = true)
	public MroSignTaskListV setTaskList(String empno, String signDescription) {
    	HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
    	if(StringUtils.isNotBlank(empno)){
			HrEmp hrEmp = hrEmpBO.getHrEmp(empno.trim());
			if(hrEmp==null){return null;}
			MroSignTaskListV signHistoryVO = new MroSignTaskListV();
			signHistoryVO.setSignDescription(signDescription);
			signHistoryVO.setActorId(empno);
			signHistoryVO.setActorDept(hrEmp.getDeptNo());
			
			return signHistoryVO;
    	}
    	return null;
	}
    @Transactional(readOnly = true)
	public List<MroSignTaskListV> onSignPreView(int processId,String empno,String comment,
			int price,String[] counterEmp,Map map) {
    	HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
    	HrOrgBO hrOrgBO=SpringContextUtil.getBean(HrOrgBO.class);
		List<MroSignTaskListV> temp_signHistoryVOList=new LinkedList<>();
		List<MroSignTaskListV> signHistoryVOList=new LinkedList<>();
		String fDeptNO; // 下一層簽核部門
		String fEmpno = empno;// 簽核來源
		BigDecimal validatePhase=null;
		// =============================SignProcessList================================================
		// ==============送簽者==================================
		temp_signHistoryVOList.add(this.setSignHistory(empno,comment,"送審者"));
		fDeptNO=temp_signHistoryVOList.get(0).getActorDept();
		// ==============signProcessList簽核清單======================================
		SignProcessListBO SignProcessListBO=SpringContextUtil.getBean(SignProcessListBO.class);
		List<SignProcessList> signProcessList = SignProcessListBO.getList(new BigDecimal(processId));
		for (SignProcessList s : signProcessList) {
			//===========================驗證節點判斷=====================================
			if(validatePhase!=null && validatePhase.compareTo(s.getSignSequence())!=0){
				continue;
			}else{
				validatePhase=null;
			}
			//=========================================================================
			if (price >s.getSignPrice().intValue() || s.getSignPrice().intValue()==0) {
				// =======================================================
				if (s.getSignCategory() != null
						&& s.getSignCategory().equals("APPOINT")) { // 指定人員
					MroSignTaskListV signHistoryVO=this.setTaskList(s.getSignEmpNo(),"");
					temp_signHistoryVOList.add(signHistoryVO);
					fDeptNO = signHistoryVO.getActorDept();
					fEmpno = signHistoryVO.getActorId();
				} else if ((s.getSignCategory() != null && s.getSignCategory().equals("CONTINUE"))
						|| (s.getSignCategory() == null && s.getSignSequence().intValue() == 1)) { // 接續人員 or送審者
					HrOrg hrOrg = hrOrgBO.getHrOrg(fDeptNO);
					if(hrOrg==null || hrOrg.getDeptLevel()==null) return null;
					while (hrOrg.getManagerId().equals(fEmpno)) {
						hrOrg = hrOrgBO.getHrOrg(hrOrg.getTopDept());
					}

					if (s.getSignLevel().intValue() == 99) { // 部門主管
						MroSignTaskListV signHistoryVO=this.setTaskList(hrOrg.getManagerId(),"");
						temp_signHistoryVOList.add(signHistoryVO);
						fDeptNO = signHistoryVO.getActorDept();
						fEmpno = signHistoryVO.getActorId();
					} else {
						while (s.getSignLevel().intValue() <= hrOrg
								.getDeptLevel().intValue()) { 
							if (s.getSignLevel().intValue() == hrOrg
									.getDeptLevel().intValue()) {
								MroSignTaskListV signHistoryVO=this.setTaskList(hrOrg.getManagerId(),"");
								temp_signHistoryVOList.add(signHistoryVO);
								fDeptNO = hrOrg.getTopDept();
								fEmpno = signHistoryVO.getActorId();
								break;
							} else {
								hrOrg = hrOrgBO.getHrOrg(hrOrg.getTopDept());
							}
						}
					}
				} else if (s.getSignCategory() != null && s.getSignCategory().equals("FROM_SYSTEM")) { // 系統來源
					List<String> list=getActor(map, s.getSignValidateFunction());
					if(list!=null && list.size()>0){
						for(String actor:list){
							if(StringUtils.isNotBlank(actor)){
								MroSignTaskListV signHistoryVO=this.setTaskList(actor,"");
								temp_signHistoryVOList.add(signHistoryVO);
								fDeptNO = signHistoryVO.getActorDept();
								fEmpno = signHistoryVO.getActorId();
								
							}
						}
					}else{
						temp_signHistoryVOList.add(null);  //簽核清單check使用
					}
				} else if (s.getSignCategory() != null && s.getSignCategory().equals("VALIDATE")) { // 驗證節點
					boolean validate=onSignVaildate(map, s.getSignValidateFunction());
					if(validate){
						if(s.getSignValidateTrue()==null){
							break;
						}else{
							validatePhase=s.getSignValidateTrue();
						}
					}else{
						if(s.getSignValidateFalse()==null){
							break;
						}else{
							validatePhase=s.getSignValidateFalse();
						}
					}
				}

				//====================判斷是否有副層級(ex:總處、副總處)=======================
					HrOrg hrOrg = hrOrgBO.getHrOrg(fDeptNO); 
					//HrOrg hrOrgTop = hrOrgBO.getHrOrg(hrOrg.getTopDept()); 
					while (s.getSignLevel()!=null &&
							s.getSignLevel().intValue() == hrOrg
							.getDeptLevel().intValue()){
						MroSignTaskListV signHistoryVOMannger = this.setTaskList(hrOrg.getManagerId(),"");
						temp_signHistoryVOList.add(signHistoryVOMannger);
						fDeptNO = hrOrg.getTopDept();
						fEmpno = signHistoryVOMannger.getActorId();
						 hrOrg = hrOrgBO.getHrOrg(fDeptNO); 
					}

			}
		}
		// ======================會簽(目前簽核者往後調整)========================================
		if(counterEmp!=null && counterEmp.length>0){
			for(int c=counterEmp.length-1;c>=0;c--){
				MroSignTaskListV signHistoryVO =this.setSignHistory(counterEmp[c].trim(),"","會簽");
				if(signHistoryVO!=null){
					temp_signHistoryVOList.add(1,signHistoryVO);}
			}
		}
		int seq=1;
		Map signMap=new HashedMap();
		for(MroSignTaskListV s:temp_signHistoryVOList){
			if(s!=null && StringUtils.isNotBlank(s.getActorId())){  
				if(signMap.get(s.getActorId())==null || s.getSignDescription().equals("會簽")){ //會簽不考慮重複
					HrEmp hrEmp = hrEmpBO.getHrEmp(s.getActorId().trim());
					s.setActor(hrEmp.getEmpNo()+"/"+hrEmp.getName()+"/"+hrEmp.getEMail());
					s.setActorDeptName(hrEmp.getDeptNo()+" "+hrEmp.getRealDeptName());
					s.setSignSeqId(new BigDecimal(seq));
					signHistoryVOList.add(s);
					seq++;
					
					signMap.put(s.getActorId(), s.getActorId());
				}
			}else{
				signHistoryVOList.add(null);
			}
		}
		//======================================BY PLANT=========================================================	
//		if(StringUtils.isNotBlank(status) && status.equals("other") && fsEmpNo.length>0){
//			for(int x=0;x<fsEmpNo.length;x++){
//				HrEmp hrEmp = signPreViewDao.getHrEmp(fsEmpNo[x]);
//				if(hrEmp!=null){
//					SignHistoryVO s = new SignHistoryVO();
//					s.setActorId(fsEmpNo[i].trim());
//					s.setActorDept(hrEmp.getDeptNo());
//					s.setActor(empno+"/"+hrEmp.getName()+"/"+hrEmp.getEMail());
//					s.setActorDeptName(hrEmp.getDeptNo()+" "+hrEmp.getRealDeptName());
//					s.setSignSeqId(new BigDecimal(seq));
//					signHistoryVOList.add(s);
//					seq++;
//				}
//			}
//		}
		
		return signHistoryVOList;
	}
    
    @Transactional(readOnly = true)
    public List<MroSignTaskListV> addExtraSigner(List<MroSignTaskListV> signHistoryVOList,String[] extraEmp,
    		String[] extraDescription){
    	HrEmpBO hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		// ======================會簽(目前簽核者往後調整)========================================
		if(extraEmp!=null && extraEmp.length>0){
			for(int i=0;i<extraEmp.length;i++){
				
				//==========================會簽節點==============================================
				String description="會簽";
				if(extraDescription!=null && extraDescription.length>i && 
						StringUtils.isNotBlank(extraDescription[i])){
					description=extraDescription[i];
				}
				HrEmp hrEmp = hrEmpBO.getHrEmp(extraEmp[i]);
				if(hrEmp!=null){
					MroSignTaskListV extra_signHistoryVO =new MroSignTaskListV();
				extra_signHistoryVO.setSignDescription(description);
				extra_signHistoryVO.setActorId(hrEmp.getEmpNo());
				extra_signHistoryVO.setActorDept(hrEmp.getDeptNo());
				extra_signHistoryVO.setActor(hrEmp.getEmpNo()+"/"+hrEmp.getName()+"/"+hrEmp.getEMail());
				extra_signHistoryVO.setActorDeptName(hrEmp.getDeptNo()+" "+hrEmp.getRealDeptName());
				extra_signHistoryVO.setSignSeqId(new BigDecimal(signHistoryVOList.size()+1));
					signHistoryVOList.add(extra_signHistoryVO);
				}
			}
		}
		return signHistoryVOList;
    }
    
    public  List<String> getActor(Map map,String signValidateFunction){
		List list =new LinkedList<>();
		try {
			Class clazz = Class.forName(signValidateFunction);
			ActorInterface actor=(ActorInterface) clazz.newInstance();
			list=actor.getActor(map);
		} catch (Exception e) {
			throw new RuntimeException("Runtime exception when execute actor handler. Actor Handler(ClassName:"+signValidateFunction+")");
		}

	return list;
    }
    public  boolean onSignVaildate(Map map,String signValidateFunction){
		boolean validate=false;
		try {
			Class clazz = Class.forName(signValidateFunction);
			ValidateInterface validateInterface=(ValidateInterface) clazz.newInstance();
			validate=validateInterface.onSignVaildate(map);
		} catch (Exception e) {
			throw new RuntimeException("Runtime exception when execute validate handler. Validate Handler(ClassName:"+signValidateFunction+")");
		}

	return validate;
    }
}
