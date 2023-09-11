package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.FactorDiscountInfonavit;
import com.sip.syshumres_repositories.FactorDiscountInfonavitRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class FactorDiscountInfonavitServiceImpl extends CommonServiceImpl<FactorDiscountInfonavit, 
	FactorDiscountInfonavitRepository> implements FactorDiscountInfonavitService {

    @Transactional(readOnly = true)
    public List<FactorDiscountInfonavit> findByEnabledTrueOrderByDescription() {
	    return repository.findByEnabledTrueOrderByDescription();
    }

}
