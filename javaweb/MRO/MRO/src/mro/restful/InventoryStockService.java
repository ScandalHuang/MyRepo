package mro.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import mro.app.reportView.form.InventoryStockReportForm;
import mro.app.reportView.jsf.InventoryStockReportBean;
import mro.app.reportView.viewType.InventoryStockReportType;

@Path("/InventoryStockService")
public class InventoryStockService {

	private InventoryStockReportBean bean = new InventoryStockReportBean();

	@POST
	@Path("/serchType")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response serchType(JSONObject input) throws JSONException {
		InventoryStockReportType type = InventoryStockReportType.valueOf(input
				.get("methodType").toString());
		JSONObject param = (JSONObject) input.get("param");
		return Response.status(201).entity(this.search(type, param)).build();
	}
	
	@POST
	@Path("/serchTypeForm")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response serchType(@FormParam(value = "methodType") String methodType,
			@FormParam(value="param") JSONObject param){
		InventoryStockReportType type = InventoryStockReportType.valueOf(methodType);
		return Response.status(201).entity(this.search(type, param)).build();
	}

	private InventoryStockReportForm search(
			InventoryStockReportType methodType, JSONObject param) {
		bean.init();
		bean.searchType(methodType, param);

		return bean.getForm();
	}

}