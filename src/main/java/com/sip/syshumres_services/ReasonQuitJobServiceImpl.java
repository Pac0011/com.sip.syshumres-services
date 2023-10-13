package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.ReasonQuitJob;
import com.sip.syshumres_repositories.ReasonQuitJobRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class ReasonQuitJobServiceImpl extends CommonServiceImpl<ReasonQuitJob, ReasonQuitJobRepository> 
	implements ReasonQuitJobService {

	@Override
	@Transactional(readOnly = true)
	public List<ReasonQuitJob> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
