package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.ProspectStatus;
import com.sip.syshumres_repositories.ProspectStatusRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class ProspectStatusServiceImpl extends CommonServiceImpl<ProspectStatus, ProspectStatusRepository> 
  implements ProspectStatusService {
	
	@Override
	@Transactional(readOnly = true)
	public List<ProspectStatus> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
