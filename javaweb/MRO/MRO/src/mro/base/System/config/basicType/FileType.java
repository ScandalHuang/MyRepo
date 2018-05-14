package mro.base.System.config.basicType;

public enum FileType {
	PhotoSubName("/(\\.|\\/)(gif|GIF|JPE?G|jpe?g|png|PNG|zip|ZIP|7Z|7z|RAR|rar)$/"),
	PDFSubName("/(\\.|\\/)(pdf|PDF|XLS|xls|XLSX|xlsx)$/"),
	PDFWordZipSubName("/(\\.|\\/)(pdf|PDF|PPT|ppt|XLS|xls|XLSX|xlsx|doc|DOC|zip|ZIP|7Z|7z|RAR|rar)$/"),
	ExcelSubName("/(\\.|\\/)(XLS|xls)$/"),
	AllSubName("/(\\.|\\/)(gif|GIF|JPE?G|jpe?g|png|PNG|zip|ZIP|"
			+ "pdf|PDF|PPT|ppt|XLS|xls|XLSX|xlsx|doc|DOC|zip|ZIP|7Z|7z|RAR|rar)$/"),
	
	FileTypesPDFWordZip("PDF , XLS, DOC, ZIP, 7Z, RAR, PPT"),
	FileTypesPDF("PDF , XLS"),
	FileTypesPhoto("GIF , JPEG , PNG, ZIP, 7Z, RAR"),
	FileTypesExcel("XLS"),
	FileTypesALL("PDF , XLS, DOC, ZIP, 7Z, RAR, PPT, GIF , JPEG , PNG"),;
	
	private String value;

	private FileType(String value) {
		this.value = value;
	}
	
	@Override
    public String toString() {
        return name()!=null?name():null;
    }
	
	public String getValue() {
        return value;
    }
	
	public static FileType findValue(String key){
		return FileType.valueOf(key);
	}
	
	public FileType getSubName(){
		if(name().equals(FileTypesPDFWordZip.name())){
			return PDFWordZipSubName;
		}else if(name().equals(FileTypesPDF.name())){
			return PDFSubName;
		}else if(name().equals(FileTypesPhoto.name())){
			return PhotoSubName;
		}else if(name().equals(FileTypesExcel.name())){
			return ExcelSubName;
		}else if(name().equals(FileTypesALL.name())){
			return AllSubName;
		}
		return null;
	}
}
