package com.sip.syshumres_services;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.sip.syshumres_entities.EmployeeDocument;
import com.sip.syshumres_exceptions.CreateRegisterException;
import com.sip.syshumres_exceptions.EntityIdNotFoundException;
import com.sip.syshumres_exceptions.TypeHiringDocumentNotExistException;
import com.sip.syshumres_exceptions.UploadFileException;
import com.sip.syshumres_exceptions.UploadFormatsAllowException;
import com.sip.syshumres_services.common.CommonService;


public interface EmployeeDocumentService extends CommonService<EmployeeDocument> {
	
	public Optional<EmployeeDocument> findByEmployeeProfileAndHiringDocument(Long idEmployeeProfile, Long idHiringDocuments);
	
	public Map<String, Object> uploadFile(Long idEmployeeProfile, Long idHiringDocument, MultipartFile fileUpload) 
			throws UploadFormatsAllowException, EntityIdNotFoundException, TypeHiringDocumentNotExistException, 
			CreateRegisterException, IOException, UploadFileException;

}
