package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.TypeHiring;
import com.sip.syshumres_services.common.CommonService;


public interface TypeHiringService extends CommonService<TypeHiring> {
	
	List<TypeHiring> findByEnabledTrueOrderByDescription();

}
