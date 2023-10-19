package com.sip.syshumres_services;


import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sip.syshumres_entities.EmployeePosition;
import com.sip.syshumres_entities.EmployeeProfile;
import com.sip.syshumres_entities.User;
import com.sip.syshumres_exceptions.CreateRegisterException;
import com.sip.syshumres_exceptions.EntityIdNotFoundException;
import com.sip.syshumres_exceptions.UnknownOptionException;
import com.sip.syshumres_exceptions.UploadFileException;
import com.sip.syshumres_exceptions.UploadFormatsAllowException;
import com.sip.syshumres_repositories.EmployeeProfileRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;
import com.sip.syshumres_utils.RandomString;
import com.sip.syshumres_utils.StringTrim;
import com.sip.syshumres_utils.UtilFile;


@Service
public class EmployeeProfileServiceImpl extends CommonServiceImpl<EmployeeProfile, EmployeeProfileRepository> implements EmployeeProfileService {

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
	
	@Value("${SIZE.EMPLOYEE.NUMBER}")
	private int sizeEmployeeNumber;
	
	private static final String NM_FILECURP = "fileCurp";
	private static final String NM_LEFTPHOTO = "leftPhoto";
	private static final String NM_FRONTPHOTO = "frontPhoto";
	private static final String NM_RIGHTPHOTO = "rightPhoto";
	private static final String NM_COVIDCERT = "covidCertificate";
	private static final String NM_BANKACCOUNT = "bankAccountFile";
	
	@Override
	public String generateEmployeeNumber(EmployeePosition employeePosition) {
		//OFR-00001  
		//AFR-00001
		String prefix = "";
		if (employeePosition != null && employeePosition.getEmployeeType() != null) {
		    prefix = prefix + employeePosition.getEmployeeType().getPrefix();
		}
		String num = RandomString.getRandomNumber(this.sizeEmployeeNumber);
		return (prefix + num);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeProfile> listEmployeeType(Long idBranchOffice, Long id, Pageable pageable) {
		return repository.listEmployeeType(idBranchOffice, id, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeProfile> listEmployeeType(Long id, Pageable pageable) {
		return repository.listEmployeeType(id, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countByEmailWithAnotherEmployee(String email, Long id) {
		return repository.countByEmailWithAnotherEmployee(email, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countByCurpWithAnotherEmployee(String curp, Long id) {
		return repository.countByCurpWithAnotherEmployee(curp, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countByNssWithAnotherEmployee(String nss, Long id) {
		return repository.countByNssWithAnotherEmployee(nss, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countByRfcWithAnotherEmployee(String rfc, Long id) {
		return repository.countByRfcWithAnotherEmployee(rfc, id);
	}
	
	@Override
	@Transactional(readOnly = true)
    public long countByBankAccountWithAnotherEmployee(String bankAccount, Long id) {
    	return repository.countByBankAccountWithAnotherEmployee(bankAccount, id);
    }
	
	@Override
	@Transactional(readOnly = true)
	public long countByClabeWithAnotherEmployee(String clabe, Long id) {
		return repository.countByClabeWithAnotherEmployee(clabe, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public long countByNameAnotherEmployee(String lastName, String lastNameSecond) {
		return repository.countByNameAnotherEmployee(lastName, lastNameSecond);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<EmployeeProfile> findByIdEmployeeClinicalData(Long id) {
		return repository.findByIdEmployeeClinicalData(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<EmployeeProfile> findByIdEmployeePayroll(Long id) {
		return repository.findByIdEmployeePayroll(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeProfile> findByFullNameLikeOrRfcLikeOrCurpLikeOr(Long idBranchOffice, Long idEmployeeType, String text,
			Pageable pageable) {
		return repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(idBranchOffice, idEmployeeType, text, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeProfile> findByFullNameLikeOrRfcLikeOrCurpLikeOr(Long idEmployeeType, String text,
			Pageable pageable) {
		return repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(idEmployeeType, text, pageable);
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public Page<EmployeeProfile> findByFilterSession(String filter, User user, Long idEmployeeType, Pageable pageable) {
		Page<EmployeeProfile> entities = null;
		
		if (filter != null && !filter.equals("")) {
			if (user.isSeeAllBranchs()) {
				entities = repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(idEmployeeType, 
						filter, pageable);
			} else {
			    entities = repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(user.getBranchOffice().getId(), 
			    		idEmployeeType, filter, pageable);
			}
		} else {
			if (user.isSeeAllBranchs()) {
			    entities = repository.listEmployeeType(idEmployeeType, pageable);
			} else {
				entities = repository.listEmployeeType(user.getBranchOffice().getId(), 
						idEmployeeType, pageable);
			}
		} 
		
		return entities;
	}
	
	@Override
	@Transactional
	public Map<String, Object> uploadFile(Long id, String nameInput, MultipartFile fileUpload) 
			throws EntityIdNotFoundException, UploadFormatsAllowException, UploadFileException, 
			UnknownOptionException, CreateRegisterException {		
		Optional<EmployeeProfile> o = Optional.empty();
		if (nameInput.equals(NM_COVIDCERT)) {
			o = repository.findByIdEmployeeClinicalData(id);
		} else if (nameInput.equals(NM_BANKACCOUNT)) { 
			o = repository.findByIdEmployeePayroll(id);
		} else {
			o = repository.findById(id);
		}
		if (!o.isPresent()) {
			throw new EntityIdNotFoundException("No se pudo subir archivo, Id Empleado " 
					+ id + " no encontrado");
		}
		EmployeeProfile entity = o.get();
		StringBuilder urlFile = new StringBuilder(this.urlDocuments);
		//Valid file
		this.validFileFormat(fileUpload);
			
		String newNameFile = null;
		if (nameInput.equals(NM_FILECURP) || nameInput.equals(NM_FRONTPHOTO)
				|| nameInput.equals(NM_LEFTPHOTO) || nameInput.equals(NM_RIGHTPHOTO)
				|| nameInput.equals(NM_COVIDCERT) || nameInput.equals(NM_BANKACCOUNT)) {
			
			newNameFile = UtilFile.saveFile(fileUpload, this.uploadDocuments + entity.getEcript() + File.separator);
			if (newNameFile != null) {
				urlFile.append(entity.getEcript())
				  .append(File.separator)
				  .append(newNameFile);
				if (nameInput.equals(NM_FILECURP)) {
					entity.setFileCurp(urlFile.toString());
				} else if (nameInput.equals(NM_FRONTPHOTO)) {
					entity.setFrontPhoto(urlFile.toString());
				} else if (nameInput.equals(NM_LEFTPHOTO)) {
					entity.setLeftPhoto(urlFile.toString());
				} else if (nameInput.equals(NM_RIGHTPHOTO)) {
					entity.setRightPhoto(urlFile.toString());
				} else if (nameInput.equals(NM_COVIDCERT)) {
					entity.getEmployeeClinicalData().setCovidCertificate(urlFile.toString());
				} else {
					entity.getEmployeePayroll().setBankAccountFile(urlFile.toString());
				}
			} else {
				throw new UploadFileException();
			}
			
		} else {
			throw new UnknownOptionException();
		}

		try { 
			repository.save(entity);
		} catch (Exception ex) {
			throw new CreateRegisterException();
		}
		Map<String, Object> response = new HashMap<>();
		response.put("message", urlFile.toString());
		
		return response;
	}
	
	private void validFileFormat(MultipartFile fileUpload) throws UploadFileException, 
			UploadFormatsAllowException {
		if (fileUpload.isEmpty()) {
			throw new UploadFileException("No se pudo subir archivo, porque esta vació");
		}
		String contentType = fileUpload.getContentType();
		if (contentType == null || this.uploadFormatsAllow.indexOf(contentType) < 0) {
			throw new UploadFormatsAllowException("y es (" + contentType + ")");
		}
	}
	
	@Override
	public Resource getFileEmployee(EmployeeProfile entity, String nameInput) {
		Resource resource = null;
		
		switch (nameInput) {
			case NM_FILECURP:
				if (entity.getFileCurp() != null && !entity.getFileCurp().equals("")) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getFileCurp());
				}
				break;
			case NM_LEFTPHOTO:
				if (entity.getLeftPhoto() != null && !entity.getLeftPhoto().equals("")) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getLeftPhoto());
				}
				break;
			case NM_FRONTPHOTO:
				if (entity.getFrontPhoto() != null && !entity.getFrontPhoto().equals("")) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getFrontPhoto());
				}
				break;
			case NM_RIGHTPHOTO:
				if (entity.getRightPhoto() != null && !entity.getRightPhoto().equals("")) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getRightPhoto());
				}
				break;
			case NM_COVIDCERT:
				if (entity.getEmployeeClinicalData() != null 
					&& entity.getEmployeeClinicalData().getCovidCertificate() != null 
					&& !entity.getEmployeeClinicalData().getCovidCertificate().equals("")) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments 
						+ entity.getEmployeeClinicalData().getCovidCertificate());
				}
				break;
			case NM_BANKACCOUNT:
				if (entity.getEmployeePayroll() != null 
					&& entity.getEmployeePayroll().getBankAccountFile() != null 
					&& !entity.getEmployeePayroll().getBankAccountFile().equals("")) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments 
						+ entity.getEmployeePayroll().getBankAccountFile());
				}
				break;
			default:
				return null;
		}
		
		return resource;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> validEntity(EmployeeProfile entity, Long id) {
		//valid email repeat
		if (repository.countByEmailWithAnotherEmployee(entity.getEmail(), id) > 0) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("email", "El campo Email esta asociado a otro perfil ingrese uno nuevo");
			return errors;
		}
		//valid Curp repeat
		if (repository.countByCurpWithAnotherEmployee(entity.getCurp(), id) > 0) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("curp", "El campo Curp esta asociado a otro perfil ingrese uno nuevo");
			return errors;
		}
		
		if (entity.getEmployeePayroll() != null) {
			//valid Nss repeat
			if (entity.getEmployeePayroll().getNss() != null) {
				entity.getEmployeePayroll().setNss(StringTrim.trimAndRemoveDiacriticalMarks(entity.getEmployeePayroll().getNss()));
				if (repository.countByNssWithAnotherEmployee(entity.getEmployeePayroll().getNss(), id) > 0) {
					Map<String, Object> errors = new HashMap<>();
					errors.put("employeePayroll_nss", "El campo Nss esta asociado a otro perfil ingrese uno nuevo");
					return errors;
				}
			}
			//valid Rfc repeat
			if (entity.getEmployeePayroll().getRfc() != null) {
				entity.getEmployeePayroll().setRfc(StringTrim.trimAndRemoveDiacriticalMarks(entity.getEmployeePayroll().getRfc()));
				if (repository.countByRfcWithAnotherEmployee(entity.getEmployeePayroll().getRfc(), id) > 0) {
					Map<String, Object> errors = new HashMap<>();
					errors.put("employeePayroll_rfc", "El campo Rfc esta asociado a otro perfil ingrese uno nuevo");
					return errors;
				}
			}
			//valid bankAccount repeat
			if (entity.getEmployeePayroll().getBankAccount() != null) {
				entity.getEmployeePayroll().setBankAccount(StringTrim.trimAndRemoveDiacriticalMarks(entity.getEmployeePayroll().getBankAccount()));
				if (repository.countByBankAccountWithAnotherEmployee(entity.getEmployeePayroll().getBankAccount(), id) > 0) {
					Map<String, Object> errors = new HashMap<>();
					errors.put("employeePayroll_bankAccount", "El campo Cuenta esta asociado a otro perfil ingrese uno nuevo");
					return errors;
				}
			}
			
			//valid clabe repeat
			if (entity.getEmployeePayroll().getClabe() != null) {
				entity.getEmployeePayroll().setClabe(StringTrim.trimAndRemoveDiacriticalMarks(entity.getEmployeePayroll().getClabe()));
			    if (!entity.getEmployeePayroll().getClabe().equals("") 
			    	&& repository.countByClabeWithAnotherEmployee(entity.getEmployeePayroll().getClabe(), id) > 0) {
					Map<String, Object> errors = new HashMap<>();
					errors.put("employeePayroll_clabe", "El campo Clabe esta asociado a otro perfil ingrese uno nuevo");
					return errors;
			    }
			}
		}
		
		return Collections.emptyMap();
	}

}
