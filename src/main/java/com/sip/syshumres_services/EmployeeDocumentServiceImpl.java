package com.sip.syshumres_services;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sip.syshumres_entities.EmployeeDocument;
import com.sip.syshumres_entities.EmployeeProfile;
import com.sip.syshumres_entities.HiringDocuments;
import com.sip.syshumres_exceptions.CreateRegisterException;
import com.sip.syshumres_exceptions.EntityIdNotFoundException;
import com.sip.syshumres_exceptions.TypeHiringDocumentNotExistException;
import com.sip.syshumres_exceptions.UploadFileException;
import com.sip.syshumres_exceptions.UploadFormatsAllowException;
import com.sip.syshumres_repositories.EmployeeDocumentRepository;
import com.sip.syshumres_repositories.EmployeeProfileRepository;
import com.sip.syshumres_repositories.HiringDocumentsRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;
import com.sip.syshumres_utils.UtilFile;


@Service
public class EmployeeDocumentServiceImpl extends CommonServiceImpl<EmployeeDocument, EmployeeDocumentRepository> 
	implements EmployeeDocumentService {

	private final EmployeeProfileRepository repositoryE;
	
	private final HiringDocumentsRepository repositoryH;
	
	@Value("${UPLOAD.BASE.DOCUMENTS.EMPLOYEES}")
	private String uploadBaseDocuments;
	
	//Tiene que haber un espacio en el el signo $ y el {, para uqe funcione (SINO MARCA ERROR QUE NO ENCUENTRA LA PROPIEDAD)
	@Value("${UPLOAD.PATH.DOCUMENTS.EMPLOYEES}")
	private String uploadDocuments;
	
	@Value("${URL.DOCUMENTS.EMPLOYEES}")
	private String urlDocuments;
	
	//@Value("#{'${UPLOAD.LIST.FORMATS.ALLOW}'.split(',')}") 
	@Value("${UPLOAD.LIST.FORMATS.ALLOW}")
	private String uploadFormatsAllow;
	
	@Autowired
	public EmployeeDocumentServiceImpl(EmployeeProfileRepository repositoryE, 
			HiringDocumentsRepository repositoryH) {
		super();
		this.repositoryE = repositoryE;
		this.repositoryH = repositoryH;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<EmployeeDocument> findByEmployeeProfileAndHiringDocument(Long idEmployeeProfile, Long idHiringDocuments) {
		return repository.findByEmployeeProfileAndHiringDocument(idEmployeeProfile, idHiringDocuments);
	}	
	
	@Override
	@Transactional
	public String uploadFile(Long idEmployeeProfile, Long idHiringDocument, MultipartFile fileUpload) 
			throws UploadFormatsAllowException, EntityIdNotFoundException
			, TypeHiringDocumentNotExistException, CreateRegisterException
			, IOException, UploadFileException {
		EmployeeDocument doc = null;
		StringBuilder urlFile = new StringBuilder(this.urlDocuments);
		if (!fileUpload.isEmpty()) {
			String contentType = fileUpload.getContentType();
			if (contentType == null || this.uploadFormatsAllow.indexOf(contentType) < 0) {
				throw new UploadFormatsAllowException("y es (" + contentType + ")");
			}
			EmployeeProfile employee = this.getEmployeeProfile(idEmployeeProfile);
			String newNameFile = UtilFile.saveFile(fileUpload, this.uploadDocuments 
					+ employee.getEcript() + File.separator);
			if (newNameFile == null) {
				throw new UploadFileException();
			}
			urlFile.append(employee.getEcript()).append(File.separator).append(newNameFile);
			doc = this.getDocument(employee, idEmployeeProfile, idHiringDocument, urlFile.toString());
		}
		try {
			if (doc != null) {
			    repository.save(doc);
			} else {
				throw new CreateRegisterException("Error al guardar registro de Documento de empleado, EmployeeDocument nulo");
			}
		} catch (Exception ex) {
			throw new CreateRegisterException("Error al guardar registro de Documento de empleado");
		}
		
		return urlFile.toString();
	}
	
	private EmployeeProfile getEmployeeProfile(Long idEmployeeProfile) throws EntityIdNotFoundException {
		
		Optional<EmployeeProfile> o = this.repositoryE.findById(idEmployeeProfile);
		if (!o.isPresent()) {
			throw new EntityIdNotFoundException("Error al guardar documento, no existe id empleado " + idEmployeeProfile);
		}
		return o.get();
	}
	
	private EmployeeDocument getDocument(EmployeeProfile employee, Long idEmployeeProfile, Long idHiringDocument, String urlFile) 
			throws TypeHiringDocumentNotExistException {
		EmployeeDocument doc = null;
		Optional<EmployeeDocument> o2 = repository.findByEmployeeProfileAndHiringDocument(idEmployeeProfile, idHiringDocument);
		if (!o2.isPresent()) {
			Optional<HiringDocuments> o3 = this.repositoryH.findById(idHiringDocument);
			if (!o3.isPresent()) {
				throw new TypeHiringDocumentNotExistException("Error al guardar documento, no existe el tipo de documento de contratacion " + idHiringDocument);
			}
			doc = new EmployeeDocument();
			doc.setDocument(urlFile);
			doc.setEmployeeProfile(employee);
			doc.setHiringDocuments(o3.get());
		} else {
			doc = o2.get();
			doc.setDocument(urlFile);
		}
	
		return doc;
	}
	
}
