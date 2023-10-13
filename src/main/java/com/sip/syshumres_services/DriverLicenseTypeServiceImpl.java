package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.DriverLicenseType;
import com.sip.syshumres_repositories.DriverLicenseTypeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class DriverLicenseTypeServiceImpl extends CommonServiceImpl<DriverLicenseType, DriverLicenseTypeRepository> implements DriverLicenseTypeService {

	@Override
	@Transactional(readOnly = true)
	public List<DriverLicenseType> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
