package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.Region;
import com.sip.syshumres_repositories.RegionRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class RegionServiceImpl extends CommonServiceImpl<Region, RegionRepository> implements RegionService {

	@Transactional(readOnly = true)
	public List<Region> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}

}
