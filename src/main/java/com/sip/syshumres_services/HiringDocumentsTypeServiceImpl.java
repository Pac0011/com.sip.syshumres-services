package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.HiringDocumentsType;
import com.sip.syshumres_repositories.HiringDocumentsTypeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class HiringDocumentsTypeServiceImpl extends CommonServiceImpl<HiringDocumentsType, HiringDocumentsTypeRepository> 
    implements HiringDocumentsTypeService {

	@Transactional(readOnly = true)
	public List<HiringDocumentsType> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
