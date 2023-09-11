package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.CostCenter;
import com.sip.syshumres_services.common.CommonService;


public interface CostCenterService extends CommonService<CostCenter> {
	
	List<CostCenter> findByEnabledTrueOrderByDescription();
	
	Page<CostCenter> findByDescriptionLikeOrCodeLike(String text, Pageable pageable);
	
	Page<CostCenter> findByFilterSession(String filter, Pageable pageable);

}
