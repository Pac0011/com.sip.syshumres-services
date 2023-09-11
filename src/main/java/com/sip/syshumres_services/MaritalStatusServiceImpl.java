package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.MaritalStatus;
import com.sip.syshumres_repositories.MaritalStatusRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class MaritalStatusServiceImpl extends CommonServiceImpl<MaritalStatus, MaritalStatusRepository> implements MaritalStatusService {

	@Transactional(readOnly = true)
	public List<MaritalStatus> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
