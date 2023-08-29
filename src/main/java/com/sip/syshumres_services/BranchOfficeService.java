package com.sip.syshumres_services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sip.syshumres_entities.BranchOffice;


public interface BranchOfficeService {
	
    Iterable<BranchOffice> findAll();
	
	Iterable<BranchOffice> findAll(Sort sort);
	
	Page<BranchOffice> findAll(Pageable pageable);
	
	Optional<BranchOffice> findById(Long id);
	
	BranchOffice create(BranchOffice entity);
	
	void deleteById(Long id);
	
	List<BranchOffice> findByEnabledTrueOrderByDescription();
	
	Page<BranchOffice> findByDescriptionLikeOrBranchOfficeTypeLikeOrCostCenterLikeOr(String text, Pageable pageable);
	
	Page<BranchOffice> findByFilterSession(String filter, Pageable pageable);

}
