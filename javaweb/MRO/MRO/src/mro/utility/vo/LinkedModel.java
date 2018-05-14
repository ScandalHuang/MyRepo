package mro.utility.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;




@Entity
public class LinkedModel implements Serializable {
	private static final long serialVersionUID = 2677930143332332865L;
	private List list;
	private String header;  //顯示節點名稱
	private Object type;  //enum type

	public LinkedModel(List list,Object type) {
		this.list = list;
		this.type = type;
	}
	
	public LinkedModel(List list,Object type,String header) {
		this.list = list;
		this.header = header;
		this.type = type;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Object getType() {
		return type;
	}

	public void setType(Object type) {
		this.type = type;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
}