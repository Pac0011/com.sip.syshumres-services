package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.EmployeeBank;
import com.sip.syshumres_services.common.CommonService;


public interface EmployeeBankService extends CommonService<EmployeeBank> {

	List<EmployeeBank> findByEnabledTrueOrderByDescription();
}
