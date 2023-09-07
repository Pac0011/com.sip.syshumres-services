package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.DriverLicenseValidity;
import com.sip.syshumres_services.common.CommonService;

public interface DriverLicenseValidityService extends CommonService<DriverLicenseValidity> {
	
	List<DriverLicenseValidity> findByEnabledTrueOrderByDescription();

}
