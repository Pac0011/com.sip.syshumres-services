package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.EmployeeTypeHealth;
import com.sip.syshumres_repositories.EmployeeTypeHealthRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class EmployeeTypeHealthServiceImpl extends CommonServiceImpl<EmployeeTypeHealth, EmployeeTypeHealthRepository> 
    implements EmployeeTypeHealthService {

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeTypeHealth> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
