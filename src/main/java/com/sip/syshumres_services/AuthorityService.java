package com.sip.syshumres_services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.Module;
import com.sip.syshumres_entities.Authority;
import com.sip.syshumres_entities.dtos.ModuleListDTO;
import com.sip.syshumres_services.common.CommonService;


public interface AuthorityService extends CommonService<Authority> {

	public List<Authority> findByEnabledTrueOrderByDescription();
	
	public long countByAuthorityIdAndModuleId(Long authorityId, Long moduleId);
	
    public Page<Authority> findByDescriptionLikeOrDetailLike(String text, Pageable pageable);
    
    public List<ModuleListDTO> getModulesChilds(Authority entity);
    
    public Page<Authority> findByFilterSession(String filter, Pageable pageable);
    
    public Authority assignModules(Authority entity, List<Module> modules);
    
    public Authority removeModule(Authority entity, Module module);

}
