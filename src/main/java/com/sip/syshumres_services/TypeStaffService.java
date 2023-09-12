package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.TypeStaff;
import com.sip.syshumres_services.common.CommonService;


public interface TypeStaffService extends CommonService<TypeStaff> {
	
	List<TypeStaff> findByEnabledTrueOrderByDescription();

}
