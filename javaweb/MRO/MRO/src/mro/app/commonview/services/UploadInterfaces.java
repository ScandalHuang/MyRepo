package mro.app.commonview.services;

import java.util.List;

public interface UploadInterfaces {
	/**================== 執行儲存前的預先作業 function======================*/
	public void preFunction(List objects);
	
	/**================== insert function==========================*/
	public boolean assignFunctionFlag();   //是否指定function
	
	public void assignFunction(List objects);  //指定的function做法
	
	/**================== insert後的額外作業=============================*/
	public boolean validate(List objects);  //額外作業 驗證
	
	public void excute(List objects);  //額外作業驗證通過後執行function
	
	/**================== 全部執行完畢後 finally function======================*/
	public void destroyed(List objects);  //全部執行完畢後 finally function

}
