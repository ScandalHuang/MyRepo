package mro.app.mcMgmtInterface.service;

import mro.app.mcMgmtInterface.form.MroOrgFacilityEqForm;

public interface ListMroOrgFacilityEqInterface {

	public MroOrgFacilityEqForm onSearch(MroOrgFacilityEqForm mroOrgFacilityEqForm);
	
	public MroOrgFacilityEqForm onDelete(MroOrgFacilityEqForm mroOrgFacilityEqForm);
	
	public MroOrgFacilityEqForm setParameter(MroOrgFacilityEqForm mroOrgFacilityEqForm);
}
