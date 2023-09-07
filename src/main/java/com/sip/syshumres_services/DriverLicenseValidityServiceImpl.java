package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.DriverLicenseValidity;
import com.sip.syshumres_repositories.DriverLicenseValidityRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;

@Service
public class DriverLicenseValidityServiceImpl extends CommonServiceImpl<DriverLicenseValidity, DriverLicenseValidityRepository> 
implements DriverLicenseValidityService {

	@Transactional(readOnly = true)
	public List<DriverLicenseValidity> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
