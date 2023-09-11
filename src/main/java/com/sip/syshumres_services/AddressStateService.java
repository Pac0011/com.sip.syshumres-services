package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.AddressState;
import com.sip.syshumres_services.common.CommonService;


public interface AddressStateService extends CommonService<AddressState> {
	
	List<AddressState> findByEnabledTrueOrderByDescription();

}
