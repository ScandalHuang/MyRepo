package mro.base.System.config.basicType;

import java.util.Arrays;
import java.util.List;


public enum ItemStatusType {
	
	/*
	 * hongjie.wu
	 * 2015.03.18
	 * 定義可item process status
	 */
	TYPE_A(Arrays.asList(SignStatus.ACTIVE.toString())),
	TYPE_D(Arrays.asList(SignStatus.DRAFT.toString())),
	TYPE_N(Arrays.asList(SignStatus.NEW.toString())),
	TYPE_AC(Arrays.asList(SignStatus.ACTIVE.toString(),
			SignStatus.CHANGED.toString())),
	TYPE_ACS(Arrays.asList(SignStatus.ACTIVE.toString(),
			SignStatus.CHANGED.toString(),SignStatus.SYNC.toString())),
	TYPE_ACSS(Arrays.asList(SignStatus.ACTIVE.toString(),
			SignStatus.CHANGED.toString(),SignStatus.SYNC.toString(),
			SignStatus.STOPUSE.toString())),
	TYPE_IS(Arrays.asList(SignStatus.INPRG.toString(),
					SignStatus.SYNC.toString())),
	TYPE_PROCESS_AI(Arrays.asList(SignStatus.APPR.toString(),
			SignStatus.INPRG.toString())),
	TYPE_PROCESS_AS(Arrays.asList(SignStatus.APPR.toString(),
			SignStatus.SYNC.toString())),
	TYPE_PROCESS_AIS(Arrays.asList(SignStatus.APPR.toString(),
			SignStatus.INPRG.toString(),SignStatus.SYNC.toString())),
	TYPE_PROCESS_AISR(Arrays.asList(SignStatus.APPR.toString(),
			SignStatus.INPRG.toString(),SignStatus.SYNC.toString(),
			SignStatus.REJECT.toString())),
	TYPE_PROCESS_AC(Arrays.asList(SignStatus.APPR.toString(),
			SignStatus.CAN.toString())),
	TYPE_PROCESS_AIRC(Arrays.asList(SignStatus.APPR.toString(),
			SignStatus.INPRG.toString(),SignStatus.REJECT.toString(),
			SignStatus.CAN.toString()));

	private List<String> value;
	
	private ItemStatusType(List<String> value) {
		this.value = value;
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}
	
}
