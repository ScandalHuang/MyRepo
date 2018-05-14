package mro.base.System.config.basicType;

import java.util.HashMap;
import java.util.Map;

public enum FileCategory {
	//file category(新增file必須更新downloadFileMap)=
	ITEM_PHOTO(FileType.FileTypesPhoto), //圖片 
	ITEM_ATTACHMENT(FileType.FileTypesPDFWordZip),  //料號新增報價單
	ITEM_GP_REPORT(FileType.FileTypesPDFWordZip),
	ITEM_GP_MSDS(FileType.FileTypesPDFWordZip),
	ITEM_SPEC(FileType.FileTypesPDFWordZip),//規格書
	ITEM_VENDOR_REMARK(FileType.FileTypesPDFWordZip),//指廠說明
	PR_HEADER_ATTACHMENT(FileType.FileTypesPDFWordZip),
	PR_LINE_ATTACHMENT(FileType.FileTypesPDFWordZip),
	BBS_ATTACHMENT(FileType.FileTypesPDFWordZip),
	CLASSSTRUCTURE_HEADER_ATTACHMENT(FileType.FileTypesPDFWordZip),
	SITE_HEADER_ATTACHMENT(FileType.FileTypesALL), //料號區域申請HEADER
	SITE_LINE_ATTACHMENT(FileType.FileTypesALL); //料號區域申請line
	

	private FileType value;
	
	private FileCategory(FileType value) {
		this.value = value;
	}
	//critera enum.toString 先判斷
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
	
	public static Map<String,FileCategory> getFileMap(){
		Map<String,FileCategory> map=new HashMap<String,FileCategory>();
		for(FileCategory f:FileCategory.values()){
			map.put(f.name(), f);
		}
		return map;
	}
	public static Map getDownloadFileMap(){
		Map map=new HashMap<>();
		for(FileCategory f:FileCategory.values()){
			map.put(f, 0);
		}
		return map;
	}
	public FileType getValue() {
        return value;
    }
}
