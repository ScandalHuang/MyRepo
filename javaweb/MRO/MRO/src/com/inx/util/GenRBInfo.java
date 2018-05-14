package com.inx.util;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;

import oracle.jdbc.pool.OracleDataSource;

public class GenRBInfo 
{ 
  private String   INIfilename = ".\\ini\\RBInfo.properties";
  private String   ServerName="";
  private String   DatabaseName="";
  private String   DriverType="";
  private String   NetworkProtocol="";
  private int      PortNumber=0;
  private String   User="";
  private String   Password="";
  
  OracleDataSource myDS=null;
  Connection con=null;
  Statement  stm=null; 
  ResultSet  rst= null;  
  public GenRBInfo()
  {
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) throws Exception
  {
    GenRBInfo genRBInfo = new GenRBInfo();
    genRBInfo.setDBSetting(args[0]);
    //genRBInfo.setDBSetting(".\\ini\\RBInfo.properties");
    if(args[1].equalsIgnoreCase("RoleListRB"))
       genRBInfo.generateRoleListRB();
    
    if(args[1].equalsIgnoreCase("DepartmentListRB"))
       genRBInfo.generateDepartmentListRB();
    
    if(args[1].equalsIgnoreCase("TeamSetting"))
       genRBInfo.generateTeamSetting();
  }
  
  private void generateRoleListRB(){
     try{
      con=myDS.getConnection();
      System.out.println( "ResourceInfo.class=wt.tools.resource.EnumResourceInfo                \n"+ 
                          "ResourceInfo.customizable=true                                       \n"+
                          "ResourceInfo.deprecated=false                                        \n"+
                          "                                                                     \n"+
                          "# Entry Format (values equal to default value are not included)      \n"+
                          "# <key>.value=                                                       \n"+ 
                          "# <key>.category=                                                    \n"+
                          "# <key>.comment=                                                     \n"+
                          "# <key>.argComment<n>=                                               \n"+
                          "# <key>.constant=                                                    \n"+
                          "# <key>.customizable=                                                \n"+
                          "# <key>.deprecated=                                                  \n"+
                          "# <key>.abbreviatedDisplay=                                          \n"+
                          "# <key>.fullDisplay=                                                 \n"+ 
                          "# <key>.shortDescription=                                            \n"+
                          "# <key>.longDescription=                                             \n"+
                          "# <key>.order=                                                       \n"+
                          "# <key>.defaultValue=                                                \n"+
                          "# <key>.selectable=                                                  \n");
       stm=con.createStatement();
       //--------------------------------------------------------------------------------------------
       String sql= " select t.role_rbinfo_key||'.value='||t.pdm_show_name name "+
                   " from pdm_int_admin.pty_role_list t                        "+
                   " where t.selectable='Y'                               "+
                   " order by t.role_rbinfo_key ";
       
       rst=stm.executeQuery(sql);
       while (rst.next()) 
          {
            System.out.println(rst.getString("name"));
          }
       //--------------------------------------------------------------------------------------------
       System.out.println("\n####################################################################");
       System.out.println("####The following are selectable=false #############################");
       System.out.println("####################################################################");
       //--------------------------------------------------------------------------------------------
       sql= " select t.role_rbinfo_key||'.value='||t.pdm_show_name name   "+
                   " from pdm_int_admin.pty_role_list t                   "+
                   " where t.selectable='N'                               "+
                   " order by t.role_rbinfo_key ";
       
       rst=stm.executeQuery(sql);
       while (rst.next()) 
          {
            System.out.println(rst.getString("name"));
          }
       //--------------------------------------------------------------------------------------------
       System.out.println( "\n\n");
       sql= " select t.role_rbinfo_key||'.selectable=false'  name"+
                   " from pdm_int_admin.pty_role_list t          "+
                   " where t.selectable='N'                               "+
                   " order by t.role_rbinfo_key ";
       
       rst=stm.executeQuery(sql);
       while (rst.next()) 
          {
            System.out.println(rst.getString("name"));
          }   
       
    }catch(Exception e){
      System.out.println(e);
    }finally{
      try 
      { 
        if(rst!=null){rst.close();}
        if(stm!=null){stm.close();}
        if(con!=null){con.close();} 
      } catch (Exception ex) 
      {
        ex.printStackTrace();
      } 
      
    }
  }
  
  private void generateDepartmentListRB(){
     try{
      con=myDS.getConnection();
      
      System.out.println( "ResourceInfo.class=wt.tools.resource.EnumResourceInfo                \n"+ 
                          "ResourceInfo.customizable=true                                       \n"+
                          "ResourceInfo.deprecated=false                                        \n"+
                          "                                                                     \n"+
                          "# Entry Format (values equal to default value are not included)      \n"+
                          "# <key>.value=                                                       \n"+ 
                          "# <key>.category=                                                    \n"+
                          "# <key>.comment=                                                     \n"+
                          "# <key>.argComment<n>=                                               \n"+
                          "# <key>.constant=                                                    \n"+
                          "# <key>.customizable=                                                \n"+
                          "# <key>.deprecated=                                                  \n"+
                          "# <key>.abbreviatedDisplay=                                          \n"+
                          "# <key>.fullDisplay=                                                 \n"+ 
                          "# <key>.shortDescription=                                            \n"+
                          "# <key>.longDescription=                                             \n"+
                          "# <key>.order=                                                       \n"+
                          "# <key>.defaultValue=                                                \n"+
                          "# <key>.selectable=                                                  \n");
       stm=con.createStatement();
       //--------------------------------------------------------------------------------------------
       String sql= " select t.dept_rbinfo_key||'.value='||t.pdm_show_name name "+
                   " from pdm_int_admin.pty_dept_list t                        "+
                   " where (t.is_ec='Y' or t.is_doc='Y' or t.is_part='Y') and t.selectable='Y'     "+
                   " order by t.dept_rbinfo_key ";
       
       rst=stm.executeQuery(sql);
       while (rst.next()) 
          {
            System.out.println(rst.getString("name"));
          }
       //--------------------------------------------------------------------------------------------
       System.out.println("\n####################################################################");
       System.out.println("####The following are selectable=false #############################");
       System.out.println("####################################################################");
       //--------------------------------------------------------------------------------------------
       sql= " select t.dept_rbinfo_key||'.value='||t.pdm_show_name name                     "+
            " from pdm_int_admin.pty_dept_list t                                            "+
            " where (t.is_ec='Y' or t.is_doc='Y' or t.is_part='Y') and t.selectable='N'     "+
            " order by t.dept_rbinfo_key                                                    ";
       
       rst=stm.executeQuery(sql);
       while (rst.next()) 
          {
            System.out.println(rst.getString("name"));
          }
       //--------------------------------------------------------------------------------------------
       System.out.println( "\n\n");
       sql= " select t.dept_rbinfo_key||'.value='||t.pdm_show_name name                     "+
            " from pdm_int_admin.pty_dept_list t                                            "+
            " where (t.is_ec='Y' or t.is_doc='Y' or t.is_part='Y') and t.selectable='N'     "+
            " order by t.dept_rbinfo_key                                                    ";
       
       rst=stm.executeQuery(sql);
       while (rst.next()) 
          {
            System.out.println(rst.getString("name"));
          }   
       
    }catch(Exception e){
      System.out.println(e);
    }finally{
      try 
      { 
        if(rst!=null){rst.close();}
        if(stm!=null){stm.close();}
        if(con!=null){con.close();} 
      } catch (Exception ex) 
      {
        ex.printStackTrace();
      } 
      
    }
  }  
  private void generateTeamSetting(){
     try{
      con=myDS.getConnection();

       stm=con.createStatement();
       //--------------------------------------------------------------------------------------------
       String tmp_str="";
       String sql_1=  " select distinct t.dept_rbinfo_key "+
                      " from pdm_int_admin.pty_dept_team_mapping t "+
                      " where t.selectable='Y'            "+
                      " order by t.dept_rbinfo_key        ";   
       
       String sql_2=""; 
       
       rst=stm.executeQuery(sql_1);
       Vector vec=new Vector();
       String value_str="";
       while (rst.next()) 
          {
            vec.add(rst.getString("dept_rbinfo_key"));
          }
        
       for(int i=0;i<vec.size();i++){
          tmp_str=(String)vec.elementAt(i);

          sql_2= " select t.dept_rbinfo_key,t.teamtemplate_name "+
                 " from pdm_int_admin.pty_dept_team_mapping t   "+
                 " where t.selectable='Y'                         "+
                 "       and t.dept_rbinfo_key='"+tmp_str      +"'"+   
                 " order by t.dept_rbinfo_key,t.teamtemplate_name ";
           
          //System.out.println("sql_2="+sql_2);
          rst=stm.executeQuery(sql_2);
          value_str="";
          while(rst.next()){
            value_str=value_str+rst.getString("teamtemplate_name").trim()+",";
          }
          System.out.println(tmp_str+"="+value_str.substring(0,value_str.length()-1));
       }

    }catch(Exception e){
      System.out.println(e);
    }finally{
      try 
      { 
        if(rst!=null){rst.close();}
        if(stm!=null){stm.close();}
        if(con!=null){con.close();} 
      } catch (Exception ex) 
      {
        ex.printStackTrace();
      } 
      
    }
  }    
  private void setDBSetting(String iniPath) throws Exception{
      //System.out.println("##"+this.getSystemTime("yyyy/MM/dd  HH:mm:ss")+" Loading INI...");
      Properties properties = new Properties();
      properties.clear();
      
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
      System.out.println("####################################################################");
      System.out.println("## Generated by program on "+this.getSystemTime("yyyy/MM/DD HH:mm:ss")); 
      System.out.println("####################################################################");
      System.out.println("## Loading DB Initial file -> INIfilename:"+INIfilename);
      ServerName     =   properties.getProperty("DBSetting.ServerName").trim();
      DatabaseName   =   properties.getProperty("DBSetting.DatabaseName").trim();   
      DriverType     =   properties.getProperty("DBSetting.DriverType").trim();       
      NetworkProtocol=   properties.getProperty("DBSetting.NetworkProtocol").trim();  
      PortNumber     =   Integer.parseInt(properties.getProperty("DBSetting.PortNumber").trim());       
      User           =   properties.getProperty("DBSetting.User").trim();            
      Password       =   properties.getProperty("DBSetting.Password").trim();
      System.out.println("####################################################################");
      System.out.println("## DBSetting.ServerName     :"+ServerName);
      System.out.println("## DBSetting.DatabaseName   :"+DatabaseName);   
      System.out.println("## DBSetting.DriverType     :"+DriverType);       
      System.out.println("## DBSetting.NetworkProtocol:"+NetworkProtocol);  
      System.out.println("## DBSetting.PortNumber     :"+PortNumber);       
      System.out.println("## DBSetting.User           :"+User);            
      System.out.println("## DBSetting.Password       :"+Password);
      System.out.println("####################################################################");
      System.out.println("\n\n");
      
      myDS=new OracleDataSource();
      myDS.setServerName(ServerName);
      myDS.setDatabaseName(DatabaseName);
      myDS.setDriverType(DriverType);
      myDS.setNetworkProtocol(NetworkProtocol);
      myDS.setPortNumber(PortNumber);
      myDS.setUser(User);
      myDS.setPassword(Password);
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
  
}