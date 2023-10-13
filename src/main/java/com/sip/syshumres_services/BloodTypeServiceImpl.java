package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.BloodType;
import com.sip.syshumres_repositories.BloodTypeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;

@Service
public class BloodTypeServiceImpl extends CommonServiceImpl<BloodType, BloodTypeRepository> implements BloodTypeService {

	@Override
	@Transactional(readOnly = true)
	public List<BloodType> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
