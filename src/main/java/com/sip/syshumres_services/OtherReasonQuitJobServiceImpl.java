package com.sip.syshumres_services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.OtherReasonQuitJob;
import com.sip.syshumres_repositories.OtherReasonQuitJobRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class OtherReasonQuitJobServiceImpl extends CommonServiceImpl<OtherReasonQuitJob, 
	OtherReasonQuitJobRepository> implements OtherReasonQuitJobService {

	@Override
	@Transactional(readOnly = true)
	public Page<OtherReasonQuitJob> findByDescriptionLike(String text, Pageable pageable) {
		return repository.findByDescriptionLike(text, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<OtherReasonQuitJob> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLike(filter, pageable);
		} 
		
		return repository.findAll(pageable);
	}
	
}
