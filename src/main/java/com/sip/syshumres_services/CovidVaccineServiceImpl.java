package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.CovidVaccine;
import com.sip.syshumres_repositories.CovidVaccineRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class CovidVaccineServiceImpl extends CommonServiceImpl<CovidVaccine, CovidVaccineRepository> implements CovidVaccineService {
	
	@Transactional(readOnly = true)
	public List<CovidVaccine> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}

