package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.SchoolGrade;
import com.sip.syshumres_repositories.SchoolGradeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class SchoolGradeServiceImpl extends CommonServiceImpl<SchoolGrade, SchoolGradeRepository> 
    implements SchoolGradeService {

	@Transactional(readOnly = true)
	public List<SchoolGrade> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
