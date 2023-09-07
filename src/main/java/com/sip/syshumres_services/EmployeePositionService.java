package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.EmployeePosition;
import com.sip.syshumres_services.common.CommonService;


public interface EmployeePositionService extends CommonService<EmployeePosition> {

	List<EmployeePosition> findByEnabledTrueOrderByDescription();
	
    List<EmployeePosition> findByEnabledTrueOrderByDescription(long idEmployeeType);
	
	Page<EmployeePosition> findByDescriptionLikeOrEmployeeTypeLike(String text, Pageable pageable);
	
	Page<EmployeePosition> findByFilterSession(String filter, Pageable pageable);
}
