package com.sip.syshumres_services;

import org.springframework.stereotype.Service;

import com.sip.syshumres_entities.EmployeeSpecialCourses;
import com.sip.syshumres_repositories.EmployeeSpecialCoursesRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class EmployeeSpecialCoursesServiceImpl 
	extends CommonServiceImpl<EmployeeSpecialCourses, EmployeeSpecialCoursesRepository> 
	implements EmployeeSpecialCoursesService {

}
