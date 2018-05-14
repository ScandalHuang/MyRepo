package mro.base.System.config.basicType;

public enum ItemType {
	R1,R2,R3,R94;
	
	@Override
    public String toString() {
		String s=name()!=null?name():null;
		if(s.equals("R94")){
			return "94";
		}else{
			return s;
		}
    }
	
	public static ItemType getItemType(String prtype){
		if(prtype.substring(0, 2).equals(R1.name())) return R1;
		if(prtype.substring(0, 2).equals(R2.name())) return R2;
		return null;
	}
}
