package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.ExpertType;
import com.sip.syshumres_repositories.ExpertTypeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class ExpertTypeServiceImpl extends CommonServiceImpl<ExpertType, ExpertTypeRepository> 
	implements ExpertTypeService {

    @Transactional(readOnly = true)
    public List<ExpertType> findByEnabledTrueOrderByDescription() {
	    return repository.findByEnabledTrueOrderByDescription();
    }

}
