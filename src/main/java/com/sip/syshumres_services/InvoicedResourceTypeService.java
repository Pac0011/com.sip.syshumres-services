package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.InvoicedResourceType;
import com.sip.syshumres_services.common.CommonService;


public interface InvoicedResourceTypeService extends CommonService<InvoicedResourceType> {
	
	List<InvoicedResourceType> findByEnabledTrueOrderByDescription();

}
