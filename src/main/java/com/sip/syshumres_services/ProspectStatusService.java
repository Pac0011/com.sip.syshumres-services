package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.ProspectStatus;
import com.sip.syshumres_services.common.CommonService;


public interface ProspectStatusService extends CommonService<ProspectStatus> {
	
	List<ProspectStatus> findByEnabledTrueOrderByDescription();

}
