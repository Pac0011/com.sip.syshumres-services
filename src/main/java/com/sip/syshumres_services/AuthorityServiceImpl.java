package com.sip.syshumres_services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.Module;
import com.sip.syshumres_entities.Authority;
import com.sip.syshumres_entities.dtos.ModuleListDTO;
import com.sip.syshumres_repositories.AuthorityRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;


@Service
public class AuthorityServiceImpl extends CommonServiceImpl<Authority, AuthorityRepository> implements AuthorityService {

	@Transactional(readOnly = true)
	@Override
	public List<Authority> findByEnabledTrueOrderByDescription() {
		return repository.findByEnabledTrueOrderByDescription();
	}
	
	@Transactional(readOnly = true)
	@Override
	public long countByAuthorityIdAndModuleId(Long authorityId, Long moduleId) {
		return repository.countByAuthorityIdAndModuleId(authorityId, moduleId);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Authority> findByDescriptionLikeOrDetailLike(String text, Pageable pageable) {
		return repository.findByDescriptionLikeOrDetailLike(text, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Authority> findByFilterSession(String filter, Pageable pageable) {
		
		if (filter != null && filter != "") {
			return repository.findByDescriptionLikeOrDetailLike(filter, pageable);
		} 
		
		return repository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ModuleListDTO> getModulesChilds(Authority entity) {
		List<ModuleListDTO> modulesDTO = new ArrayList<ModuleListDTO>();
		
		if (entity.getModules() != null) {
			entity.getModules().forEach(module -> {
				List<ModuleListDTO> modulesChildsDTO = new ArrayList<ModuleListDTO>();
				if (module.getChilds() != null && module.getChilds().size() > 0) {
					module.getChilds().forEach(c -> {
						//Filter only child modules assign to module father
						if (repository.countByAuthorityIdAndModuleId(entity.getId(), c.getId()) > 0) {
						    modulesChildsDTO.add(new ModuleListDTO(c.getId(), c.getDescription(), null));
						}
					});
					modulesChildsDTO.sort(Comparator.comparing(ModuleListDTO::getDescription));
				    modulesDTO.add(new ModuleListDTO(module.getId(), module.getDescription(), modulesChildsDTO));
				} else if (module.getFather() == null) {
					modulesDTO.add(new ModuleListDTO(module.getId(), module.getDescription(), null));
				}
			});
		}
		modulesDTO.sort(Comparator.comparing(ModuleListDTO::getDescription));
		
		return modulesDTO;
	}
	
	@Override
	public Authority assignModules(Authority entity, List<Module> modules) {
		
		modules.forEach(m -> {
			entity.addModule(m);
		});
		
		return repository.save(entity);
	}
	
	@Override
	public Authority removeModule(Authority entity, Module module) {
		
		entity.removeModule(module);
		
		return repository.save(entity);
	}

}
