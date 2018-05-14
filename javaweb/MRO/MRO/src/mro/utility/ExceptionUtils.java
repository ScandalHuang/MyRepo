package mro.utility;

import java.math.BigDecimal;

import org.hibernate.TransactionException;

public class ExceptionUtils {

	public static void showFalilException(String num,BigDecimal taskid,String msg){
		throw new TransactionException("簽核失敗!,"
				+ "單號："+ num+ " , 簽核單號："+ taskid + ","+msg);
	}
	public static void showFalilException(String num,String msg){
		throw new TransactionException("送審失敗!,"
				+ "單號："+ num+ ","+msg);
	}
}
