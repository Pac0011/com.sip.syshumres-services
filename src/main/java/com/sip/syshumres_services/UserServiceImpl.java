package com.sip.syshumres_services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.User;
import com.sip.syshumres_entities.Authority;
import com.sip.syshumres_repositories.UserRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;
import com.sip.syshumres_utils.StringTrim;


@Service
public class UserServiceImpl extends CommonServiceImpl<User, UserRepository> implements UserService {
	
	private static final String MSG_ERROR_EMPTY = "La contraseña no debe estar vacia";
	
	@Override
	@Transactional(readOnly = true)
	public Optional<User> findOneByUsername(String username) {
		return repository.findOneByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findOneByEmail(String email) {
		return repository.findOneByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public long countByEmail(String email) {
		return repository.countByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public long countByUsername(String username) {
		return repository.countByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public long countByEmailWithAnotherUser(String email, Long id) {
		return repository.countByEmailWithAnotherUser(email, id);
	}

	@Override
	@Transactional(readOnly = true)
	public long countByUsernameWithAnotherUser(String username, Long id) {
		return repository.countByUsernameWithAnotherUser(username, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> findByUsernameLikeOrFirstNameLikeOrEmailLike(String text, Pageable pageable) {
		return repository.findByUsernameLikeOrFirstNameLikeOrEmailLike(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<User> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && !filter.equals("")) {
			return repository.findByUsernameLikeOrFirstNameLikeOrEmailLike(filter, pageable);
		} 
		
		return repository.findAll(pageable);
	}
	
	@Override
	@Transactional
	public User saveNewPassword(User entity, String newPass) {
		entity.setPassword(this.encrypPassword(newPass));
		
		return repository.save(entity);
	}
	
	@Override
	public boolean validUserPassword(String passOld, String passSession) {
		return new BCryptPasswordEncoder().matches(passOld, passSession);
	}
	
	@Override
	public HttpSession logout(HttpSession session, String sessionUserName) {
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.setInvalidateHttpSession(true);        
        SecurityContextHolder.clearContext();
        
        session.setAttribute(sessionUserName, "");
        session.invalidate();
        
        return session;
	}
	
	@Override
	@Transactional
    public User assignAuthorities(User entity, List<Authority> authorities) {
		authorities.forEach(m -> 
			entity.addAuthority(m)
		);
		
		return repository.save(entity);
	}
    
	@Override
	@Transactional
	public User removeAuthority(User entity, Authority authority) {
		
		entity.removeAuthority(authority);
		
		return repository.save(entity);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> validEntity(User entity, Long id) {
        //valid email
        if (repository.countByEmailWithAnotherUser(entity.getEmail(), id) > 0) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("email", "El Email esta asociado a otro usuario ingrese uno nuevo");
            return errors;
        }
        
        //valid username
        if (repository.countByUsernameWithAnotherUser(entity.getUsername(), id) > 0) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("username", "El nombre de Usuario esta asociado a otro registro ingrese uno nuevo");
            return errors;
        }
        
        return Collections.emptyMap();
    }
	
	@Override
	public Map<String, Object> validChangePassword(String passCurrent, String passOld, String passNew, String passConfirm) {
		if (passOld.equals("")) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("password_old", MSG_ERROR_EMPTY);
			return errors;
		}
		
	    if (!this.validUserPassword(passOld, passCurrent)) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("password_old", "La contraseña actual, no coincide con la que está guardada");
			return errors;
		}
		
		String passN = StringTrim.trimAndRemoveDiacriticalMarks(passNew);
		if (passN.equals("")) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("password_new", MSG_ERROR_EMPTY);
			return errors;
		}
		
		String passC = StringTrim.trimAndRemoveDiacriticalMarks(passConfirm);
		if (!passN.equals(passC)) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("password_confirm", "La confirmación de la contraseña no es la misma");
			return errors;
		}
		 
		return Collections.emptyMap();
	}
	
	@Override
	public Map<String, Object> validNewPassword(String passNew, String passConfirm) {
		if (passNew.equals("")) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("password_new", MSG_ERROR_EMPTY);
			return errors;
		}
		
		if (!passNew.equals(passConfirm)) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("password_confirm", "La confirmación de la contraseña no es la misma");
			return errors;
		}
		 
		return Collections.emptyMap();
	}
	
	@Override
	public String encodePassword(String pass) {
		return this.encrypPassword(pass);
	}
	
	private String encrypPassword(String pass) {
		return new BCryptPasswordEncoder().encode(pass);
	}

}
