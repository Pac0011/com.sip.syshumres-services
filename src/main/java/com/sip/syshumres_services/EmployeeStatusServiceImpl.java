package com.sip.syshumres_services;

import org.springframework.stereotype.Service;

import com.sip.syshumres_entities.EmployeeStatus;
import com.sip.syshumres_repositories.EmployeeStatusRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class EmployeeStatusServiceImpl extends CommonServiceImpl<EmployeeStatus, EmployeeStatusRepository> 
	implements EmployeeStatusService {

}
