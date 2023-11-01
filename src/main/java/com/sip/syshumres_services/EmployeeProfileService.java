package com.sip.syshumres_services;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.sip.syshumres_entities.EmployeePosition;
import com.sip.syshumres_entities.EmployeeProfile;
import com.sip.syshumres_entities.User;
import com.sip.syshumres_exceptions.CreateRegisterException;
import com.sip.syshumres_exceptions.EntityIdNotFoundException;
import com.sip.syshumres_exceptions.UnknownOptionException;
import com.sip.syshumres_exceptions.UploadFileException;
import com.sip.syshumres_exceptions.UploadFormatsAllowException;
import com.sip.syshumres_services.common.CommonService;


public interface EmployeeProfileService extends CommonService<EmployeeProfile> {
	
	public void configBasePaths(String uploadBaseDocuments, String uploadDocuments
			, String urlDocuments, String uploadFormatsAllow, int sizeEmployeeNumber);
	
	public String generateEmployeeNumber(EmployeePosition employeePosition);
	
	public Page<EmployeeProfile> listEmployeeType(Long idBranchOffice, Long idEmployeeType, Pageable pageable);
	
	public Page<EmployeeProfile> listEmployeeType(Long idEmployeeType, Pageable pageable);
	
	public long countByEmailWithAnotherEmployee(String email, Long id);
	
	public long countByCurpWithAnotherEmployee(String curp, Long id);
	
	public long countByNssWithAnotherEmployee(String nss, Long id);
	
	public long countByRfcWithAnotherEmployee(String rfc, Long id);
	
	public long countByBankAccountWithAnotherEmployee(String bankAccount, Long id);
	
	public long countByClabeWithAnotherEmployee(String clabe, Long id);
	
	public long countByNameAnotherEmployee(String lastName, String lastNameSecond);
	
	public Optional<EmployeeProfile> findByIdEmployeeClinicalData(Long id);
	
	public Optional<EmployeeProfile> findByIdEmployeePayroll(Long id);
	
	public Page<EmployeeProfile> findByFullNameLikeOrRfcLikeOrCurpLikeOr(Long idBranchOffice, Long idEmployeeType, String text, Pageable pageable);
	
	public Page<EmployeeProfile> findByFullNameLikeOrRfcLikeOrCurpLikeOr(Long idEmployeeType, String text, Pageable pageable);
	
	public Page<EmployeeProfile> findByFilterSession(String filter, User user, Long idEmployeeType, Pageable pageable);
	
	public String uploadFile(Long id, String nameInput, MultipartFile fileUpload) 
			throws EntityIdNotFoundException, UploadFormatsAllowException, UploadFileException, 
			UnknownOptionException, CreateRegisterException, IOException;
	
	public Resource getFileEmployee(EmployeeProfile entity, String nameInput) throws IOException;
	
	public Map<String, String> validEntity(EmployeeProfile entity, Long id);
		
}
