package mro.quartz.job.sapJob.jco;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

public class SapRfcFunction {

	private String functionName = null;
	private JCO.Repository jcoRepository = null;
	private JCO.Function jcoFunction = null;

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public JCO.Function getJcoFunction(JCO.Client jcoClient) {
		if(jcoRepository==null){
			jcoClient.connect();
			jcoRepository = new JCO.Repository(SapRfc.getRepository(), jcoClient);
		}
		IFunctionTemplate ft = jcoRepository.getFunctionTemplate(functionName);
		jcoFunction = ft.getFunction();

		return jcoFunction;
	}
	
	public JCO.Client connect(){
		return SapRfc.getJcoClient();
	}
}
