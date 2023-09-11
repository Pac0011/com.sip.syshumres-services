package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.HiringDocuments;
import com.sip.syshumres_services.common.CommonService;


public interface HiringDocumentsService extends CommonService<HiringDocuments> {
	
	List<HiringDocuments> findByEnabledTrueOrderByDescription();
	
	Page<HiringDocuments> findByDescriptionLikeOrHiringDocumentsTypeLike(String text, Pageable pageable);
	
	Page<HiringDocuments> findByFilterSession(String filter, Pageable pageable);
	
}
