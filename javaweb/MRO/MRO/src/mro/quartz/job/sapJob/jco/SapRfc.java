package mro.quartz.job.sapJob.jco;

import java.util.Properties;
import java.util.ResourceBundle;

import mro.quartz.job.sapJob.ProductionInterface;

import com.sap.mw.jco.JCO;

public class SapRfc implements ProductionInterface {

	private final static ResourceBundle myResources = ResourceBundle.getBundle("mro.quartz.job.sapJob.properties.sap");
	private final static String pName = production ? "p." : "s.";
	private final static String repository = myResources.getString(pName + "jco.client.repository");
	private static Properties jcoProperties = null;
	private static JCO.Client jcoClient = null;
	
	private SapRfc(){}
	static {
		// initial jcoProperties
		if (jcoProperties == null) {
			jcoProperties = new Properties();
			jcoProperties.put("jco.client.client",myResources.getString(pName + "jco.client.client"));
			jcoProperties.put("jco.client.user",myResources.getString(pName + "jco.client.user"));
			jcoProperties.put("jco.client.passwd",myResources.getString(pName + "jco.client.passwd"));
			jcoProperties.put("jco.client.sysnr",myResources.getString(pName + "jco.client.sysnr"));
			if (repository.equals("PRD_GROUP")) { // SAP定義是會走Logon Group 方式 , 來達到Loading Balance 的
				jcoProperties.put("jco.client.mshost",myResources.getString(pName + "jco.client.mshost"));
				jcoProperties.put("jco.client.r3name",myResources.getString(pName + "jco.client.r3name"));
				jcoProperties.put("jco.client.group",myResources.getString(pName + "jco.client.group"));
			} else {
				jcoProperties.put("jco.client.ashost",myResources.getString(pName + "jco.client.ashost"));
			}
		}
	}
	public static JCO.Client getJcoClient() {
		if (jcoClient == null){
            synchronized(SapRfc.class){
                if(jcoClient == null) {
        			jcoClient = JCO.createClient(jcoProperties);
                }
            }
        }
		return jcoClient;
	}

	public static void disconnect() {
		if (jcoClient != null) {
			jcoClient.disconnect();
			jcoClient = null;
		}
	}

	public static String getRepository() {
		return repository;
	}
	
}
