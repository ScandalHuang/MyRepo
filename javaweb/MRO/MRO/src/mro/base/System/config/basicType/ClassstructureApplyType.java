package mro.base.System.config.basicType;

public enum ClassstructureApplyType {
	CLASSSTRUCTURE_ALNDOMAIN_ADD,  //清單新增
	CLASSSTRUCTURE_ALNDOMAIN_INACTIVE,  //清單失效
	CLASSSTRUCTURE_ALNDOMAIN_ACTIVE;  //清單生效
	
	//critera enum.toString 先判斷
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
}
