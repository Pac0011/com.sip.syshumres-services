package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.ExpertType;
import com.sip.syshumres_services.common.CommonService;


public interface ExpertTypeService extends CommonService<ExpertType> {
	
	List<ExpertType> findByEnabledTrueOrderByDescription();

}
