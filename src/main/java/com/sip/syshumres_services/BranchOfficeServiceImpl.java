package com.sip.syshumres_services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.BranchOffice;
import com.sip.syshumres_repositories.BranchOfficeRepository;


@Service
public class BranchOfficeServiceImpl implements BranchOfficeService {
	
	private final BranchOfficeRepository repository;

	@Autowired
	public BranchOfficeServiceImpl(BranchOfficeRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<BranchOffice> findAll() {
		return repository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<BranchOffice> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BranchOffice> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public BranchOffice save(BranchOffice entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BranchOffice> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public List<BranchOffice> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<BranchOffice> findByDescriptionLikeOrBranchOfficeTypeLikeOrCostCenterLikeOr(String text,
			Pageable pageable) {
		return repository.findByDescriptionLikeOrBranchOfficeTypeLikeOrCostCenterLikeOr(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<BranchOffice> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLikeOrBranchOfficeTypeLikeOrCostCenterLikeOr(filter, pageable);
		}
		
		return repository.findAll(pageable);
	}
	
}
