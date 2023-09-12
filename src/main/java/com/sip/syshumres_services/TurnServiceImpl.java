package com.sip.syshumres_services;

import org.springframework.stereotype.Service;

import com.sip.syshumres_entities.Turn;
import com.sip.syshumres_repositories.TurnRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class TurnServiceImpl extends CommonServiceImpl<Turn, TurnRepository> implements TurnService {

}
