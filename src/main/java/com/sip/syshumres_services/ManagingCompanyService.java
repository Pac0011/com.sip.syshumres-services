package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.BranchOffice;
import com.sip.syshumres_entities.ManagingCompany;
import com.sip.syshumres_services.common.CommonService;


public interface ManagingCompanyService extends CommonService<ManagingCompany> {
	
	Page<ManagingCompany> findByDescriptionLikeOrCompanyNameLikeOrRfcLikeOr(String text, Pageable pageable);
	
	Page<ManagingCompany> findByFilterSession(String filter, Pageable pageable);
	
	ManagingCompany assignBranchOffices(ManagingCompany entity, List<BranchOffice> branchOffices);
    
	ManagingCompany removeBranchOffice(ManagingCompany entity, BranchOffice branchOffice);
}
