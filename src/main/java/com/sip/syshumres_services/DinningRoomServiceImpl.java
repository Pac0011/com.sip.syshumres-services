package com.sip.syshumres_services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.DinningRoom;
import com.sip.syshumres_repositories.DinningRoomRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class DinningRoomServiceImpl extends CommonServiceImpl<DinningRoom, DinningRoomRepository> implements DinningRoomService {

	@Override
	@Transactional(readOnly = true)
	public List<DinningRoom> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
}
