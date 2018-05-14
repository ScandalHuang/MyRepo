package mro.restful.test;

import java.io.Serializable;

public class Track implements Serializable{
	
	private static final long serialVersionUID = -1563260059078967349L;
	
	String title;
	String singer;
	
	public Track(){
		
	}
	public Track(String title,String singer){
		this.title=title;
		this.singer=singer;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	@Override
	public String toString() {
		return "Track [title=" + title + ", singer=" + singer + "]";
	}

}

