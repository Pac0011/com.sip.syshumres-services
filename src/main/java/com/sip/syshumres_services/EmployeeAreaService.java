package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.EmployeeArea;
import com.sip.syshumres_services.common.CommonService;


public interface EmployeeAreaService extends CommonService<EmployeeArea> {
	
	List<EmployeeArea> findByEnabledTrueOrderByDescription();
	
	Page<EmployeeArea> findByDescriptionLikeOrCostCenterLikeOrFatherLike(String text, Pageable pageable);
	
	Page<EmployeeArea> findByFilterSession(String filter, Pageable pageable);

}
