package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.DriverLicenseType;
import com.sip.syshumres_services.common.CommonService;


public interface DriverLicenseTypeService extends CommonService<DriverLicenseType> {
	
	List<DriverLicenseType> findByEnabledTrueOrderByDescription();

}
