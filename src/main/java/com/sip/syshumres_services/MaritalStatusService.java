package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.MaritalStatus;
import com.sip.syshumres_services.common.CommonService;


public interface MaritalStatusService extends CommonService<MaritalStatus> {
	
	List<MaritalStatus> findByEnabledTrueOrderByDescription();

}
