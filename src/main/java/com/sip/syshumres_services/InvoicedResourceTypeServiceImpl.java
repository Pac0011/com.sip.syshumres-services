package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.InvoicedResourceType;
import com.sip.syshumres_repositories.InvoicedResourceTypeRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class InvoicedResourceTypeServiceImpl extends CommonServiceImpl<InvoicedResourceType, 
	InvoicedResourceTypeRepository> implements InvoicedResourceTypeService {

	@Override
	@Transactional(readOnly = true)
	public List<InvoicedResourceType> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
