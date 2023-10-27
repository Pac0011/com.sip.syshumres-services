package com.sip.syshumres_services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.Authority;
import com.sip.syshumres_entities.User;
import com.sip.syshumres_services.common.CommonService;


public interface UserService extends CommonService<User> {
	
	Optional<User> findOneByUsername(String username);
	
	Optional<User> findOneByEmail(String email);
	
    long countByEmail(String email);
	
	long countByUsername(String username);
	
	long countByEmailWithAnotherUser(String email, Long id);
	
	long countByUsernameWithAnotherUser(String username, Long id);
	
    Page<User> findByUsernameLikeOrFirstNameLikeOrEmailLike(String text, Pageable pageable);
    
    Page<User> findByFilterSession(String filter, Pageable pageable);
    
    User saveNewPassword(User entity, String newPass);
    
    boolean validUserPassword(String passOld, String passSession);
    
    void logout(HttpSession session, String sessionUserName);
    
    User assignAuthorities(User entity, List<Authority> authorities);
    
    User removeAuthority(User entity, Authority authority);
    
    Map<String, Object> validEntity(User entity, Long id);
    
    Map<String, Object> validChangePassword(String passCurrent, String passOld, String passNew, String passConfirm);
    
    Map<String, Object> validNewPassword(String passNew, String passConfirm);
    
    String encodePassword(String pass);

}
