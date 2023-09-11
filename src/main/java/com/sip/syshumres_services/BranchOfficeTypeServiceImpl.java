package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.BranchOfficeType;
import com.sip.syshumres_repositories.BranchOfficeTypeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class BranchOfficeTypeServiceImpl extends CommonServiceImpl<BranchOfficeType, BranchOfficeTypeRepository> 
    implements BranchOfficeTypeService {

	@Override
	public List<BranchOfficeType> findByDescription(String term) {
		return repository.findByDescription(term);
	}

	@Transactional(readOnly = true)
	public List<BranchOfficeType> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
