package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.CostCenter;
import com.sip.syshumres_repositories.CostCenterRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class CostCenterServiceImpl extends CommonServiceImpl<CostCenter, CostCenterRepository> 
    implements CostCenterService {

	@Transactional(readOnly = true)
	@Override
	public List<CostCenter> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<CostCenter> findByDescriptionLikeOrCodeLike(String text, Pageable pageable) {
		return repository.findByDescriptionLikeOrCodeLike(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<CostCenter> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLikeOrCodeLike(filter, pageable);
		} 
		
		return repository.findAll(pageable);
	}
	
}
