package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.AddressState;
import com.sip.syshumres_repositories.AddressStateRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class AddressStateServiceImpl extends CommonServiceImpl<AddressState, AddressStateRepository> 
    implements AddressStateService {

	@Transactional(readOnly = true)
	public List<AddressState> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
