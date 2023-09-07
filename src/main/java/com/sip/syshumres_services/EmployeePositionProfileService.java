package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.EmployeePositionProfile;
import com.sip.syshumres_services.common.CommonService;


public interface EmployeePositionProfileService extends CommonService<EmployeePositionProfile> {
	
	List<EmployeePositionProfile> findByEnabledTrueOrderByDescription();
	
	Page<EmployeePositionProfile> findByDescriptionLikeOrTypeStaffLike(String text, Pageable pageable);
	
	Page<EmployeePositionProfile> findByFilterSession(String filter, Pageable pageable);

}
