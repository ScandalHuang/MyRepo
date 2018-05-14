package mro.base.System.config.basicType;

public enum ActionType {
	submit,save,noSign,add,update;
	
	//critera enum.toString 先判斷
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
}
