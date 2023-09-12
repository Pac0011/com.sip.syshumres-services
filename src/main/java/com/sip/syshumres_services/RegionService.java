package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.Region;
import com.sip.syshumres_services.common.CommonService;


public interface RegionService extends CommonService<Region> {
	
	List<Region> findByEnabledTrueOrderByDescription();

}
