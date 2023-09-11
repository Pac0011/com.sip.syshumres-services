package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.PayrollType;
import com.sip.syshumres_services.common.CommonService;


public interface PayrollTypeService extends CommonService<PayrollType> {
	
	List<PayrollType> findByEnabledTrueOrderByDescription();
	
	List<PayrollType> findNormalByEnabledTrueOrderByDescription();

}
