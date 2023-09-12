package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.SchoolGrade;
import com.sip.syshumres_services.common.CommonService;


public interface SchoolGradeService extends CommonService<SchoolGrade> {
	
	List<SchoolGrade> findByEnabledTrueOrderByDescription();

}
