package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.EmployeeArea;
import com.sip.syshumres_repositories.EmployeeAreaRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class EmployeeAreaServiceImpl extends CommonServiceImpl<EmployeeArea, EmployeeAreaRepository> 
  implements EmployeeAreaService {

	@Transactional(readOnly = true)
	@Override
	public List<EmployeeArea> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<EmployeeArea> findByDescriptionLikeOrCostCenterLikeOrFatherLike(String text, Pageable pageable) {
		return repository.findByDescriptionLikeOrCostCenterLikeOrFatherLike(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<EmployeeArea> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLikeOrCostCenterLikeOrFatherLike(filter, pageable);
		}
		
		return repository.findAll(pageable);
	}

}
