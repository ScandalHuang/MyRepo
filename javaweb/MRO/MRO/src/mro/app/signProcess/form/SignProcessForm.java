package mro.app.signProcess.form;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mro.base.entity.SignProcess;
import mro.base.entity.SignProcessList;

public class SignProcessForm implements Serializable {
	private static final long serialVersionUID = -13206912122208814L;
	private List<SignProcess> signProcessQuery;
	private List<SignProcessList> signProcessList;
	private List<SignProcessList> deleteSignProcessList;
	private SignProcessList[] sDeleteSignProcessList;
	
	private SignProcess signProcess;
	
	private Map<String,String> signLevel;  //簽核層級 
	private Map<String,String> signCategory; //簽核類別
	
	private Map<String,String> signSourseSystem; //簽核來源系統
	private Map<String,String> signSourseCategory; //簽核來源類別
	private Map signSequenceMap; //簽核順序選項
	
	private Map<String,Map> methodMap;
	
	private String changeName; //異動人
	private String createName; //申請人
	private int activeIndex;//  page
	private int signSequence;
	
	public SignProcessForm(){
		
	}
	
	public void inital(){
		signProcessList=new LinkedList<>();
		signProcessList=new LinkedList<>();
		deleteSignProcessList=new LinkedList<>();
		sDeleteSignProcessList=null;
		signProcess=null;
		createName="";
		changeName="";
		activeIndex=0;
		this.initalSignSequenceMap();
	}
	public void initalSignSequenceMap(){
		signSequenceMap=new LinkedHashMap<>();
	}
	public void onNew(String createName){
		this.inital();
		signProcess=new SignProcess();
		this.createName=createName;
		changeName=createName;
		activeIndex=1;
	}

	public List<SignProcess> getSignProcessQuery() {
		return signProcessQuery;
	}

	public void setSignProcessQuery(List<SignProcess> signProcessQuery) {
		this.signProcessQuery = signProcessQuery;
	}

	public List<SignProcessList> getSignProcessList() {
		return signProcessList;
	}

	public void setSignProcessList(List<SignProcessList> signProcessList) {
		this.signProcessList = signProcessList;
	}

	public List<SignProcessList> getDeleteSignProcessList() {
		return deleteSignProcessList;
	}

	public void setDeleteSignProcessList(List<SignProcessList> deleteSignProcessList) {
		this.deleteSignProcessList = deleteSignProcessList;
	}

	public SignProcessList[] getsDeleteSignProcessList() {
		return sDeleteSignProcessList;
	}

	public void setsDeleteSignProcessList(SignProcessList[] sDeleteSignProcessList) {
		this.sDeleteSignProcessList = sDeleteSignProcessList;
	}

	public SignProcess getSignProcess() {
		return signProcess;
	}

	public void setSignProcess(SignProcess signProcess) {
		this.signProcess = signProcess;
	}

	public Map<String, String> getSignLevel() {
		return signLevel;
	}

	public void setSignLevel(Map<String, String> signLevel) {
		this.signLevel = signLevel;
	}

	public Map<String, String> getSignCategory() {
		return signCategory;
	}

	public void setSignCategory(Map<String, String> signCategory) {
		this.signCategory = signCategory;
	}

	public Map<String, String> getSignSourseSystem() {
		return signSourseSystem;
	}

	public void setSignSourseSystem(Map<String, String> signSourseSystem) {
		this.signSourseSystem = signSourseSystem;
	}

	public Map<String, String> getSignSourseCategory() {
		return signSourseCategory;
	}

	public void setSignSourseCategory(Map<String, String> signSourseCategory) {
		this.signSourseCategory = signSourseCategory;
	}

	public String getChangeName() {
		return changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public int getSignSequence() {
		return signSequence;
	}

	public void setSignSequence(int signSequence) {
		this.signSequence = signSequence;
	}

	public Map<String, Map> getMethodMap() {
		return methodMap;
	}

	public void setMethodMap(Map<String, Map> methodMap) {
		this.methodMap = methodMap;
	}

	public Map getSignSequenceMap() {
		return signSequenceMap;
	}

	public void setSignSequenceMap(Map signSequenceMap) {
		this.signSequenceMap = signSequenceMap;
	}
	
	
}
