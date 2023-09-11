package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.Gender;
import com.sip.syshumres_repositories.GenderRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class GenderServiceImpl extends CommonServiceImpl<Gender, GenderRepository> implements GenderService {

	@Transactional(readOnly = true)
	public List<Gender> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
