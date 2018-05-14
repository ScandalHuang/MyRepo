package mro.base.System.config.basicType;

public enum Eaudittype {
	I,  //料號申請
	U,  //料號規格異動
	A,  //料號生效
	S;  //料號失效
	
	//critera enum.toString 先判斷
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
}
