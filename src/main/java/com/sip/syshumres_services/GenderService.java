package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.Gender;
import com.sip.syshumres_services.common.CommonService;


public interface GenderService extends CommonService<Gender> {
	
	List<Gender> findByEnabledTrueOrderByDescription();

}
