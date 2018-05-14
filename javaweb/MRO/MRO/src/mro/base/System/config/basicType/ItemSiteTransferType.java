package mro.base.System.config.basicType;

public enum ItemSiteTransferType {
	reTransfer("0"), //系統重拋
	create("1"),  //料號新增
	change("2"),  //規格異動
	bySite("3"),  //BY_SITE申請
	insert("I"),  //I:新增
	update("U"),  //U:更新
	stop("S");    //S:停止
	

	private String value;
	

	private ItemSiteTransferType(String value) {
		this.value = value;
	}
	
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
	
	public String getValue() {
        return value;
    }
	
	public static ItemSiteTransferType findValue(String key){
		return ItemSiteTransferType.valueOf(key);
	}
}
