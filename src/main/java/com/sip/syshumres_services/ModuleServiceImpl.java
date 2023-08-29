package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.Module;
import com.sip.syshumres_repositories.ModuleRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;

@Service
public class ModuleServiceImpl extends CommonServiceImpl<Module, ModuleRepository> implements ModuleService {

	@Transactional(readOnly = true)
	public List<Module> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Module> findByDescriptionLikeOrUrlLikeOrDetailLike(String text, Pageable pageable) {
		return repository.findByDescriptionLikeOrUrlLikeOrDetail(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Module> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLikeOrUrlLikeOrDetail(filter, pageable);
		} 
		
		return repository.findAll(pageable);
	}

}