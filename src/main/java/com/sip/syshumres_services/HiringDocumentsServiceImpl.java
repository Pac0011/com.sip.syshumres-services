package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.HiringDocuments;
import com.sip.syshumres_repositories.HiringDocumentsRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class HiringDocumentsServiceImpl extends CommonServiceImpl<HiringDocuments, HiringDocumentsRepository> 
    implements HiringDocumentsService {
	
	@Transactional(readOnly = true)
	@Override
	public List<HiringDocuments> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<HiringDocuments> findByDescriptionLikeOrHiringDocumentsTypeLike(String text, Pageable pageable) {
		return repository.findByDescriptionLikeOrHiringDocumentsTypeLike(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<HiringDocuments> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && !filter.equals("")) {
			return repository.findByDescriptionLikeOrHiringDocumentsTypeLike(filter, pageable);
		} 
		
		return repository.findAll(pageable);
	}

}
