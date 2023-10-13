package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.EmployeeBank;
import com.sip.syshumres_repositories.EmployeeBankRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class EmployeeBankServiceImpl extends CommonServiceImpl<EmployeeBank, EmployeeBankRepository> implements EmployeeBankService {

	@Override
	@Transactional(readOnly = true)
	public List<EmployeeBank> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
