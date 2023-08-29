package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.Module;
import com.sip.syshumres_services.common.CommonService;

public interface ModuleService extends CommonService<Module> {

	List<Module> findByEnabledTrueOrderByDescription();
	
	//Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
    Page<Module> findByDescriptionLikeOrUrlLikeOrDetailLike(String text, Pageable pageable);
    
    Page<Module> findByFilterSession(String filter, Pageable pageable);
	
}
