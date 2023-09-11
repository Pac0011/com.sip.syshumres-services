package com.sip.syshumres_services;

import org.springframework.stereotype.Service;

import com.sip.syshumres_entities.Nationality;
import com.sip.syshumres_repositories.NationalityRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class NationalityServiceImpl extends CommonServiceImpl<Nationality, NationalityRepository> 
	implements NationalityService {

}
