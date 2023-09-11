package com.sip.syshumres_services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.OtherReasonQuitJob;
import com.sip.syshumres_services.common.CommonService;


public interface OtherReasonQuitJobService extends CommonService<OtherReasonQuitJob> {
	
	Page<OtherReasonQuitJob> findByDescriptionLike(String text, Pageable pageable);
	
	Page<OtherReasonQuitJob> findByFilterSession(String filter, Pageable pageable);

}
