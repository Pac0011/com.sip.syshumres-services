package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.EmployeeTypeHealth;
import com.sip.syshumres_services.common.CommonService;


public interface EmployeeTypeHealthService extends CommonService<EmployeeTypeHealth> {
	
	List<EmployeeTypeHealth> findByEnabledTrueOrderByDescription();

}