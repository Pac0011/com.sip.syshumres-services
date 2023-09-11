package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.BranchOfficeType;
import com.sip.syshumres_services.common.CommonService;


public interface BranchOfficeTypeService extends CommonService<BranchOfficeType> {
	
	public List<BranchOfficeType> findByDescription(String term);
	
	List<BranchOfficeType> findByEnabledTrueOrderByDescription();

}
