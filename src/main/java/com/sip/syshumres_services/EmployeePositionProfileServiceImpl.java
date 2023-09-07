package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.EmployeePositionProfile;
import com.sip.syshumres_repositories.EmployeePositionProfileRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class EmployeePositionProfileServiceImpl extends CommonServiceImpl<EmployeePositionProfile, EmployeePositionProfileRepository> implements EmployeePositionProfileService {

	@Transactional(readOnly = true)
	@Override
	public List<EmployeePositionProfile> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<EmployeePositionProfile> findByDescriptionLikeOrTypeStaffLike(String text, Pageable pageable) {
		return repository.findByDescriptionLikeOrTypeStaffLike(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<EmployeePositionProfile> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLikeOrTypeStaffLike(filter, pageable);
		} 
		
		return repository.findAll(pageable);
	}
}
