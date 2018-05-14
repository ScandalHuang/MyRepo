package mro.base.System.config.basicType;

public enum SignTaskStatus {
	APPR,
	INPRG,
	TRANSFER,
	APPROVE,
	SUBMIT,
	REJECT,
	CANCEL;

	//critera enum.toString 先判斷
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
	
}
