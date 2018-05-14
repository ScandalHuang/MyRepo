package com.inx.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;

import oracle.jdbc.pool.OracleDataSource;

import com.oreilly.servlet.MailMessage;

public class MiddleCall 
{
  
  private String   INIfilename = ".\\ini\\MiddleCall.properties";
  private String   MailServer     = "";
  private String   MailDomain     = "";
  private String   MailFrom       = "";
  private String   ServerName="";
  private String   DatabaseName="";
  private String   DriverType="";
  private String   NetworkProtocol="";
  private int      PortNumber=0;
  private String   User="";
  private String   Password="";
  private int      StoredProcedureNumber=0;
  private String[] StoredProcedureName=null;
  private String[] StoredProcedureRun=null;
  private String[] NotifyUsers=null;
  
  Connection con=null;
  Statement  stm=null; 
  CallableStatement callableStm=null;
  
  /**
   * ��o�t�γ]�w��
   */
  private void getPropertyFile(String iniPath) throws Exception{
        try{
            System.out.println("================================================================================");
            System.out.println(this.getSystemTime("yyyy/MM/dd  HH:mm:ss")+" Loading INI...");
            Properties properties = new Properties();
            if (iniPath!=null){
              INIfilename=iniPath;
            }
            File file = new File(INIfilename);
            if(file.exists())
            {
                properties.load(new FileInputStream(file));
            }
            else
            {
                throw new Exception(INIfilename+" does not exist. please check it.");
            }
            
            MailServer     =properties.getProperty("Mail.Server").trim();
            MailDomain     =properties.getProperty("Mail.Domain").trim();
            MailFrom       =properties.getProperty("Mail.From").trim();
            ServerName     =properties.getProperty("DBSetting.ServerName").trim();
            DatabaseName   =properties.getProperty("DBSetting.DatabaseName").trim();   
            DriverType     =properties.getProperty("DBSetting.DriverType").trim();       
            NetworkProtocol=properties.getProperty("DBSetting.NetworkProtocol").trim();  
            PortNumber     =Integer.parseInt(properties.getProperty("DBSetting.PortNumber").trim());       
            User           =properties.getProperty("DBSetting.User").trim();            
            Password       =properties.getProperty("DBSetting.Password").trim();
            StoredProcedureNumber=Integer.parseInt(properties.getProperty("StoredProcedure.Number").trim());
            StoredProcedureName =new String[StoredProcedureNumber];
            StoredProcedureRun  =new String[StoredProcedureNumber];
            NotifyUsers         =new String[StoredProcedureNumber];
            for (int i=0;i<StoredProcedureNumber ; i++ ) 
            {
              StoredProcedureName[i]=properties.getProperty("StoredProcedure.Name"+i).trim();
              StoredProcedureRun[i] =properties.getProperty("StoredProcedure.Run"+i).trim();
              NotifyUsers[i]        =properties.getProperty("Notify.User"+i).trim();
            }
            
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Mail.Server              :"+MailServer);
            System.out.println("Mail.Domain              :"+MailDomain);
            System.out.println("Mail.From                :"+MailFrom);
            System.out.println("DBSetting.ServerName     :"+ServerName);
            System.out.println("DBSetting.DatabaseName   :"+DatabaseName);   
            System.out.println("DBSetting.DriverType     :"+DriverType);       
            System.out.println("DBSetting.NetworkProtocol:"+NetworkProtocol);  
            System.out.println("DBSetting.PortNumber     :"+PortNumber);       
            System.out.println("DBSetting.User           :"+User);            
            System.out.println("DBSetting.Password       :"+Password);    
            System.out.println("StoredProcedure.Number   :"+StoredProcedureNumber);
            for (int i=0;i<StoredProcedureNumber ; i++ ) 
            {
              System.out.println("StoredProcedureName["+i+"]="+StoredProcedureName[i]+ " .Runnable : "+StoredProcedureRun[i]);
              System.out.println("NotifyUsers["+i+"]="+NotifyUsers[i]);

            }
        }
        catch(Exception e)
        {
            System.err.println("Failed to load or save the INI file.");
            System.err.println("Exception : " + e);
            return;
        }
   
  
  }
  
  /**
   * �I�sStored Procedure
   * */
  public void callStoredProcedure() throws Exception{
    try{
      OracleDataSource myDS=new OracleDataSource();
      myDS.setServerName(ServerName);
      myDS.setDatabaseName(DatabaseName);
      myDS.setDriverType(DriverType);
      myDS.setNetworkProtocol(NetworkProtocol);
      myDS.setPortNumber(PortNumber);
      myDS.setUser(User);
      myDS.setPassword(Password);
      con=myDS.getConnection();
      
      for (int i=0 ;i< this.StoredProcedureNumber ; i++) 
      { 

        if(StoredProcedureRun[i].toLowerCase().equals("true")){
            try{
              System.out.println("--"+this.getSystemTime("HH:mm:ss")+" .Start Call--------------------------------------");
              callableStm=con.prepareCall("{call "+StoredProcedureName[i]+"}");
              callableStm.execute();
              System.out.println("--          Sucessful call : "+StoredProcedureName[i]);             
              System.out.println("--"+this.getSystemTime("HH:mm:ss")+" .End   Call--------------------------------------");
            }catch(Exception ex){
              //�Y�I�sStored Procedure���ѡA�h�o�H�q�������H��
              String mailTitle="--"+this.getSystemTime("yyyy/MM/dd  HH:mm:ss")+" .Failed in Calling Stored Procedure : "+StoredProcedureName[i] +" .";
              String mailBody ="Error Message -> "+ex+"\n"+
                               "NotifyUsers   -> "+NotifyUsers[i]+"\n"+
                               "System Time   -> "+this.getSystemTime("yyyy/MM/dd  HH:mm:ss")+"\n"+
                               "DB Server     -> "+this.ServerName+":"+this.PortNumber+":"+this.DatabaseName;
                         
              System.out.println(mailTitle+"\n"+mailBody);
              //mailSomeone(NotifyUsers[i].split(","),null,null,this.MailFrom,mailTitle,mailBody);
              mailSomeone(split(NotifyUsers[i],","),null,null,this.MailFrom,mailTitle,mailBody);
            }
        }  
      }
      
      //callableStm=con.prepareCall("{call pf_p_Parse_CycleTime_Excel(?,?)}");
      //callableStm.setString(1,p_facility);
      //callableStm.setString(2,p_tgtMonth);
      
      
    
    }catch(Exception e){
      System.out.println("can not getDB");
      String users="";
      for (int i=0 ;i< NotifyUsers.length;i++ ) 
         users=users+this.NotifyUsers[i]+",";
      
      //�Y��o��Ʈw�s�u���ѡA�h�o�H�q�������H��
      //mailSomeone(users.split(","),null,null,this.MailFrom,"Can not get Database Connection on "+ServerName,"Error:"+e);
      mailSomeone(split(users,","),null,null,this.MailFrom,"Can not get Database Connection on "+ServerName,"Error:"+e);
    }finally{
      try 
      { 
        if(stm!=null){stm.close();}
        if(con!=null){con.close();} 
      } catch (Exception ex) 
      {
        ex.printStackTrace();
      } 
      
    }
  }
  
  /**
   * ��o�t�ήɶ�
   * */
  private String getSystemTime(String format){
    //"yyyy/MM/dd  HH:mm:ss"
    Calendar calendar=Calendar.getInstance();
    java.util.Date date=new java.util.Date();
    date.setTime(calendar.getTime().getTime());
    SimpleDateFormat f=new SimpleDateFormat(format);
    return f.format(date);
  }
  

  /**
   * �H�H
   * */
  private void mailSomeone(String[] to,String[] cc,String[] bcc,String from,String subject,String body) throws Exception{
    MailMessage mail=null;
    if (to!=null){
        try 
        {    
            mail = new MailMessage(this.MailServer);

            mail.from(from);

            for (int i = 0; i < to.length; i++) 
            {
              mail.to(to[i]+MailDomain); 
            }

            if( cc!=null){
              for (int i = 0; i < cc.length; i++) 
                mail.cc(cc[i]+MailDomain); 
            }

            if( bcc!=null){
              for (int i = 0; i < bcc.length; i++) 
                mail.bcc(bcc[i]+MailDomain); 
            }
    
            mail.setSubject(subject);
            PrintStream out = mail.getPrintStream();
  
            out.println(body);
            mail.sendAndClose();
      
        } catch (Exception ex) 
        {
          ex.printStackTrace();
          System.out.println("Mail error:"+ex.toString());
        } 

      }else{
           System.out.println("����w���H�H�C��!mailList is null.");
      }
  
  }
  
  	
	/**
	 *��strSplitter��Ѧr��
	 *@param strInput ��r��
	 *@param strSplitter ���j�r��
	 *@return ��ѫ᪺�r��}�C
	 */
	private String[] split(String strInput,String strSplitter){
		
		if(strSplitter == null || strSplitter.length() == 0){
			return new String[] {strInput};
		}
		
		Vector vecReturnPool = new Vector();
		
		String NewString = strInput;

		int index = -1;

		boolean go = true;

		while(go){

			index = NewString.indexOf(strSplitter);

			if(index!=-1){

				int endindex = index + strSplitter.length();
				
				String strElement = NewString.substring(0,index);
				
				vecReturnPool.add(strElement);
				
				NewString = NewString.substring(endindex);

			}else{
			
				if(NewString.length() > 0){
					vecReturnPool.add(NewString);
				}
				go = false;
			}
		}
		
		int iSize = vecReturnPool.size();
		String[] strReturn = new String[iSize];
		
		for(int i=0;i<iSize;i++){
			strReturn[i] = (String)vecReturnPool.elementAt(i);
		}
		
		return strReturn;
	}

	/**
	 *�ˬd��J�r��O�_���ƭȫ��A
	 */
	public static boolean checkNum(String String_Num){
		try{
            Double.parseDouble(String_Num);
        }catch(Exception e){
			return false;
        }
		return true;

	}//end of checkNum
  
  public MiddleCall()
  {
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) throws Exception
  {
    if (args.length==0){
      System.out.println("�п�Jini�ɤ����|�Ѽ�. ex: java.exe -classpath .;.\\lib\\classes12.jar;.\\lib\\cos.jar cmo.pdm.util.MiddleCall .\\ini\\MiddleCall.properties ");
    }else{
      System.out.println("ini file path :"+args[0]);
      MiddleCall middleCall = new MiddleCall();
      middleCall.getPropertyFile(args[0]);
      middleCall.callStoredProcedure();
    }
    
  }
}