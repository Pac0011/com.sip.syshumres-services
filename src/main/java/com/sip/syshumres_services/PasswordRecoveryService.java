package com.sip.syshumres_services;

import java.util.Optional;

import com.sip.syshumres_entities.PasswordRecovery;
import com.sip.syshumres_services.common.CommonService;


public interface PasswordRecoveryService extends CommonService<PasswordRecovery> {
	
	public Optional<PasswordRecovery> findOneByUuidAndEnabledTrue(String uuid);
	
	public PasswordRecovery savePasswordRecovery(String email);
	
	public boolean expirationLinkRecovery(PasswordRecovery entity);
	
}
