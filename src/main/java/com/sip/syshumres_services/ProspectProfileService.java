package com.sip.syshumres_services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sip.syshumres_entities.BranchOffice;
import com.sip.syshumres_entities.EmployeeProfile;
import com.sip.syshumres_entities.EmployeeStatus;
import com.sip.syshumres_entities.ProspectProfile;
import com.sip.syshumres_entities.ProspectStatus;
import com.sip.syshumres_entities.User;
import com.sip.syshumres_exceptions.EmployeeFieldsAlreadyExistException;
import com.sip.syshumres_exceptions.ProspectFieldsAlreadyExistException;
import com.sip.syshumres_services.common.CommonService;


public interface ProspectProfileService extends CommonService<ProspectProfile> {
	
	public void configProspectProfileService(int sizeHashDirUploadEmployee);
	
	public ProspectProfile save(ProspectProfile entity, BranchOffice branchOffice, ProspectStatus status);
	
	public EmployeeProfile saveNewHire(ProspectProfile entity, String employeeNumber, ProspectStatus status, EmployeeStatus statusE);
	
	public Page<ProspectProfile> listBranchOffice(Long idBranchOffice, Pageable pageable);
	
    public Page<ProspectProfile> findByFullNameLikeOrRfcLikeOrCurpLikeOr(Long idBranchOffice, String text, Pageable pageable);
	
    public Page<ProspectProfile> findByFullNameLikeOrRfcLikeOrCurpLikeOr(String text, Pageable pageable);
    
    public Page<ProspectProfile> findByFilterSession(String filter, User user, Pageable pageable);
    
    public void validEntity(ProspectProfile entity, Long id) 
    		throws ProspectFieldsAlreadyExistException, EmployeeFieldsAlreadyExistException;

}
