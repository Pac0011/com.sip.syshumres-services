package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.HiringDocumentsType;
import com.sip.syshumres_services.common.CommonService;


public interface HiringDocumentsTypeService extends CommonService<HiringDocumentsType> {
	
	List<HiringDocumentsType> findByEnabledTrueOrderByDescription();

}
