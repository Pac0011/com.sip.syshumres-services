package com.sip.syshumres_services;

import java.util.List;

import com.sip.syshumres_entities.DinningRoom;
import com.sip.syshumres_services.common.CommonService;

public interface DinningRoomService extends CommonService<DinningRoom> {
	
	List<DinningRoom> findByEnabledTrueOrderByDescription();

}
