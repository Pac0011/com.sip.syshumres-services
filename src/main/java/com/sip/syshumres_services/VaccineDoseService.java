package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.VaccineDose;
import com.sip.syshumres_services.common.CommonService;


public interface VaccineDoseService extends CommonService<VaccineDose> {
	
	List<VaccineDose> findByEnabledTrueOrderByDescription();

}
