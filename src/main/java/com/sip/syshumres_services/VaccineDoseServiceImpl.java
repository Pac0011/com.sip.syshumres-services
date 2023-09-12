package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.VaccineDose;
import com.sip.syshumres_repositories.VaccineDoseRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class VaccineDoseServiceImpl extends CommonServiceImpl<VaccineDose, VaccineDoseRepository> 
    implements VaccineDoseService {

	@Override
	@Transactional(readOnly = true)
	public List<VaccineDose> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
