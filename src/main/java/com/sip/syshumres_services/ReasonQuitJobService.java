package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.ReasonQuitJob;
import com.sip.syshumres_services.common.CommonService;


public interface ReasonQuitJobService extends CommonService<ReasonQuitJob> {
	
	List<ReasonQuitJob> findByEnabledTrueOrderByDescription();

}
