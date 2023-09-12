package com.sip.syshumres_services;

import org.springframework.stereotype.Service;

import com.sip.syshumres_entities.EmployeeType;
import com.sip.syshumres_repositories.EmployeeTypeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class EmployeeTypeServiceImpl extends CommonServiceImpl<EmployeeType, EmployeeTypeRepository> 
	implements EmployeeTypeService {

}
