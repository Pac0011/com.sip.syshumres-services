package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.EmployeePosition;
import com.sip.syshumres_repositories.EmployeePositionRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class EmployeePositionServiceImpl extends CommonServiceImpl<EmployeePosition, EmployeePositionRepository> 
    implements EmployeePositionService {

	@Transactional(readOnly = true)
	@Override
	public List<EmployeePosition> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

	@Transactional(readOnly = true)
	@Override
	public List<EmployeePosition> findByEnabledTrueOrderByDescription(long idEmployeeType) {
		return repository.findByEnabledTrueOrderByDescription(idEmployeeType);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<EmployeePosition> findByDescriptionLikeOrEmployeeTypeLike(String text, Pageable pageable) {
		return repository.findByDescriptionLikeOrEmployeeTypeLike(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<EmployeePosition> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLikeOrEmployeeTypeLike(filter, pageable);
		}
		
		return repository.findAll(pageable);
	}
	
}
