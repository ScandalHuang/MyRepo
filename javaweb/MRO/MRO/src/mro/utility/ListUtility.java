package mro.utility;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import mro.utility.vo.ColumnModel;

public class ListUtility {

	/*
	 * List去除重複資料
	 */
	public static List removeDuplicate(List list){
		return (new ArrayList(new  LinkedHashSet(list)));
	}
	
	/*
	 * Llist to columnModel
	 */
	public static List<ColumnModel> getColumnModels(List<String> list){
		List<ColumnModel> columnModels=new ArrayList<ColumnModel>();
		for(String s:list){
			columnModels.add(new ColumnModel(s, s));
		}
		return columnModels;
	}

	/*
	 * Llist去除指定node之後的資料
	 */
	public static void removeNodeLast(LinkedList list,int node){
		while((list.size()-(node+1))>0){ 
			list.removeLast();
		}
	}
}
