package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.TypeStaff;
import com.sip.syshumres_repositories.TypeStaffRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class TypeStaffServiceImpl extends CommonServiceImpl<TypeStaff, TypeStaffRepository> 
	implements TypeStaffService {

	@Transactional(readOnly = true)
	public List<TypeStaff> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
