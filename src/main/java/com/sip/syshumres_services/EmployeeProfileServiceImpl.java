package com.sip.syshumres_services;


import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sip.syshumres_entities.EmployeePosition;
import com.sip.syshumres_entities.EmployeeProfile;
import com.sip.syshumres_entities.dtos.UserTokenExtraDTO;
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
public class EmployeeProfileServiceImpl extends CommonServiceImpl<EmployeeProfile, EmployeeProfileRepository> 
	implements EmployeeProfileService {

	private String uploadBaseDocuments;
	
	private String uploadDocuments;
	
	private String urlDocuments;
	
	private String uploadFormatsAllow;
	
	private int sizeEmployeeNumber;
	
	private static final String NM_FILECURP = "fileCurp";
	private static final String NM_LEFTPHOTO = "leftPhoto";
	private static final String NM_FRONTPHOTO = "frontPhoto";
	private static final String NM_RIGHTPHOTO = "rightPhoto";
	private static final String NM_COVIDCERT = "covidCertificate";
	private static final String NM_BANKACCOUNT = "bankAccountFile";
	
	@Override
	public void configBasePaths(String uploadBaseDocuments, String uploadDocuments
			, String urlDocuments, String uploadFormatsAllow, int sizeEmployeeNumber) {
		this.uploadBaseDocuments = uploadBaseDocuments;
		this.uploadDocuments = uploadDocuments;
		this.urlDocuments = urlDocuments;
		this.uploadFormatsAllow = uploadFormatsAllow;
		this.sizeEmployeeNumber = sizeEmployeeNumber;
	}
	
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
	public Page<EmployeeProfile> listEmployeeType(Long id, Pageable pageable, UserTokenExtraDTO userToken) {
		if (userToken.isSeeAllBranchs()) {
			return repository.listEmployeeType(id, pageable);
		}
		return repository.listEmployeeType(userToken.getIdBrachOffice(), id, pageable);
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
	public Page<EmployeeProfile> findByFilterSession(String filter, UserTokenExtraDTO userToken, Long idEmployeeType, Pageable pageable) {
		Page<EmployeeProfile> entities = null;
		
		if (filter != null && !filter.equals("")) {
			if (userToken.isSeeAllBranchs()) {
				entities = repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(idEmployeeType, 
						filter, pageable);
			} else {
			    entities = repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(userToken.getIdBrachOffice(), 
			    		idEmployeeType, filter, pageable);
			}
		} else {
			if (userToken.isSeeAllBranchs()) {
			    entities = repository.listEmployeeType(idEmployeeType, pageable);
			} else {
				entities = repository.listEmployeeType(userToken.getIdBrachOffice(), 
						idEmployeeType, pageable);
			}
		} 
		
		return entities;
	}
	
	@Override
	@Transactional
	public String uploadFile(Long id, String nameInput, MultipartFile fileUpload) 
			throws EntityIdNotFoundException, UploadFormatsAllowException, UploadFileException
			, UnknownOptionException, CreateRegisterException, IOException {		
		EmployeeProfile entity = this.getEmployeeProfile(id, nameInput);
		StringBuilder urlFile = new StringBuilder(this.urlDocuments);
		//Valid file
		this.validFileFormat(fileUpload);
		String newNameFile = null;
		if (validOption(nameInput)) {
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
		
		return urlFile.toString();
	}
	
	private EmployeeProfile getEmployeeProfile(Long id, String nameInput) throws EntityIdNotFoundException {
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
		
		return o.get();
	}
	
	private boolean validOption(String nameInput) {
		return (nameInput.equals(NM_FILECURP) || nameInput.equals(NM_FRONTPHOTO)
				|| nameInput.equals(NM_LEFTPHOTO) || nameInput.equals(NM_RIGHTPHOTO)
				|| nameInput.equals(NM_COVIDCERT) || nameInput.equals(NM_BANKACCOUNT));
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
	public Resource getFileEmployee(EmployeeProfile entity, String nameInput) throws IOException {
		Resource resource = null;
		
		switch (nameInput) {
			case NM_FILECURP:
				if (validStringEmpty(entity.getFileCurp())) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getFileCurp());
				}
				break;
			case NM_LEFTPHOTO:
				if (validStringEmpty(entity.getLeftPhoto())) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getLeftPhoto());
				}
				break;
			case NM_FRONTPHOTO:
				if (validStringEmpty(entity.getFrontPhoto())) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getFrontPhoto());
				}
				break;
			case NM_RIGHTPHOTO:
				if (validStringEmpty(entity.getRightPhoto())) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getRightPhoto());
				}
				break;
			case NM_COVIDCERT:
				if (entity.getEmployeeClinicalData() != null 
					&& validStringEmpty(entity.getEmployeeClinicalData().getCovidCertificate())) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments 
						+ entity.getEmployeeClinicalData().getCovidCertificate());
				}
				break;
			case NM_BANKACCOUNT:
				if (entity.getEmployeePayroll() != null 
					&& validStringEmpty(entity.getEmployeePayroll().getBankAccountFile())) {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments 
						+ entity.getEmployeePayroll().getBankAccountFile());
				}
				break;
			default:
				return null;
		}
		
		return resource;
	}
	
	private boolean validStringEmpty(String val) {
		return (val != null && !val.equals(""));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, String> validEntity(EmployeeProfile entity, Long id) {
		//valid email repeat
		if (repository.countByEmailWithAnotherEmployee(entity.getEmail(), id) > 0) {
			return setMessage("email", "El campo Email esta asociado a otro perfil ingrese uno nuevo");
		}
		//valid Curp repeat
		if (repository.countByCurpWithAnotherEmployee(entity.getCurp(), id) > 0) {
			return setMessage("curp", "El campo Curp esta asociado a otro perfil ingrese uno nuevo");
		}
		
		if (entity.getEmployeePayroll() != null) {
			//valid Nss repeat
			if (entity.getEmployeePayroll().getNss() != null) {
				entity.getEmployeePayroll().setNss(StringTrim.trimAndRemoveDiacriticalMarks(entity.getEmployeePayroll().getNss()));
				if (repository.countByNssWithAnotherEmployee(entity.getEmployeePayroll().getNss(), id) > 0) {
					return setMessage("employeePayroll_nss", "El campo Nss esta asociado a otro perfil ingrese uno nuevo");
				}
			}
			//valid Rfc repeat
			if (entity.getEmployeePayroll().getRfc() != null) {
				entity.getEmployeePayroll().setRfc(StringTrim.trimAndRemoveDiacriticalMarks(entity.getEmployeePayroll().getRfc()));
				if (repository.countByRfcWithAnotherEmployee(entity.getEmployeePayroll().getRfc(), id) > 0) {					
					return setMessage("employeePayroll_rfc", "El campo Rfc esta asociado a otro perfil ingrese uno nuevo");
				}
			}
			//valid bankAccount repeat
			if (entity.getEmployeePayroll().getBankAccount() != null) {
				entity.getEmployeePayroll().setBankAccount(StringTrim.trimAndRemoveDiacriticalMarks(entity.getEmployeePayroll().getBankAccount()));
				if (repository.countByBankAccountWithAnotherEmployee(entity.getEmployeePayroll().getBankAccount(), id) > 0) {					
					return setMessage("employeePayroll_bankAccount", "El campo Cuenta esta asociado a otro perfil ingrese uno nuevo");
				}
			}
			
			//valid clabe repeat
			if (entity.getEmployeePayroll().getClabe() != null) {
				entity.getEmployeePayroll().setClabe(
						StringTrim.trimAndRemoveDiacriticalMarks(entity.getEmployeePayroll().getClabe()));
			    if (!entity.getEmployeePayroll().getClabe().equals("") 
			    	&& repository.countByClabeWithAnotherEmployee(entity.getEmployeePayroll().getClabe(), id) > 0) {
			    	return setMessage("employeePayroll_clabe", "El campo Clabe esta asociado a otro perfil ingrese uno nuevo");
			    }
			}
		}
		
		return Collections.emptyMap();
	}
	
	private Map<String, String> setMessage(String key, String message) {
		Map<String, String> errors = new HashMap<>();
		errors.put(key, message);
		return errors;
	}

}
