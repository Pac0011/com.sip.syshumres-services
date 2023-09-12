package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.SchoolGradeComplete;
import com.sip.syshumres_services.common.CommonService;


public interface SchoolGradeCompleteService extends CommonService<SchoolGradeComplete> {
	
	List<SchoolGradeComplete> findByEnabledTrueOrderByDescription();

}
