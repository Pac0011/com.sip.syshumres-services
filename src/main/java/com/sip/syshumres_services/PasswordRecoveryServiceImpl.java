package com.sip.syshumres_services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.PasswordRecovery;
import com.sip.syshumres_repositories.PasswordRecoveryRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;
import com.sip.syshumres_utils.RandomString;


@Service
public class PasswordRecoveryServiceImpl extends CommonServiceImpl<PasswordRecovery, PasswordRecoveryRepository> 
   implements PasswordRecoveryService {

	@Transactional(readOnly = true)
	@Override
	public Optional<PasswordRecovery> findOneByUuidAndEnabledTrue(String uuid) {
		return repository.findOneByUuidAndEnabledTrue(uuid);
	}
	
	@Override
	@Transactional
	public PasswordRecovery savePasswordRecovery(String email) {
		PasswordRecovery r = new PasswordRecovery();
		r.setEmail(email);
		r.setUuid(RandomString.getRandomStringStream(16));
		r.setEnabled(true);
		
		return repository.save(r);
	}
	
	@Override
	public boolean expirationLinkRecovery(PasswordRecovery entity) {
		Date today = new Date();
		return entity.getExpirationDate().before(today);
	}

}
