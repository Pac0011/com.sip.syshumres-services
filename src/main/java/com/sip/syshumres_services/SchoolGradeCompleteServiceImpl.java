package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.SchoolGradeComplete;
import com.sip.syshumres_repositories.SchoolGradeCompleteRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class SchoolGradeCompleteServiceImpl extends CommonServiceImpl<SchoolGradeComplete, SchoolGradeCompleteRepository> 
	implements SchoolGradeCompleteService {

	@Override
	@Transactional(readOnly = true)
	public List<SchoolGradeComplete> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
