package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.BranchOffice;
import com.sip.syshumres_entities.ManagingCompany;
import com.sip.syshumres_repositories.ManagingCompanyRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class ManagingCompanyServiceImpl extends CommonServiceImpl<ManagingCompany, ManagingCompanyRepository> implements ManagingCompanyService {

	@Transactional(readOnly = true)
	@Override
	public Page<ManagingCompany> findByDescriptionLikeOrCompanyNameLikeOrRfcLikeOr(String text, Pageable pageable) {
		return repository.findByDescriptionLikeOrCompanyNameLikeOrRfcLikeOr(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<ManagingCompany> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLikeOrCompanyNameLikeOrRfcLikeOr(filter, pageable);
		} 
		
		return repository.findAll(pageable);
	}
	
	@Override
	public ManagingCompany assignBranchOffices(ManagingCompany entity, List<BranchOffice> branchOffices) {
		
		branchOffices.forEach(m -> {
			entity.addBranchOffice(m);
		});
		
		return repository.save(entity);
	}
	
	@Override
	public ManagingCompany removeBranchOffice(ManagingCompany entity, BranchOffice branchOffice) {
		
		entity.removeBranchOffice(branchOffice);
		
		return repository.save(entity);
	}
	
}