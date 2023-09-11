package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.PayrollType;
import com.sip.syshumres_repositories.PayrollTypeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class PayrollTypeServiceImpl extends CommonServiceImpl<PayrollType, PayrollTypeRepository> 
	implements PayrollTypeService {

	@Transactional(readOnly = true)
	public List<PayrollType> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
	
	@Transactional(readOnly = true)
	public List<PayrollType> findNormalByEnabledTrueOrderByDescription() {
		return repository.findNormalByEnabledTrueOrderByDescription();
	}

}
