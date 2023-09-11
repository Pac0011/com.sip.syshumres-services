package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.FactorDiscountInfonavit;
import com.sip.syshumres_services.common.CommonService;


public interface FactorDiscountInfonavitService extends CommonService<FactorDiscountInfonavit> {
	
	List<FactorDiscountInfonavit> findByEnabledTrueOrderByDescription();

}
