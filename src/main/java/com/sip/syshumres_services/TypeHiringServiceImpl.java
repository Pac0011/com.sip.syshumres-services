package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.TypeHiring;
import com.sip.syshumres_repositories.TypeHiringRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class TypeHiringServiceImpl extends CommonServiceImpl<TypeHiring, TypeHiringRepository> 
	implements TypeHiringService {

	@Override
	@Transactional(readOnly = true)
	public List<TypeHiring> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
