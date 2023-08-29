package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.BloodType;
import com.sip.syshumres_services.common.CommonService;


public interface BloodTypeService extends CommonService<BloodType> {
	
	List<BloodType> findByEnabledTrueOrderByDescription();

}
