package com.sip.syshumres_services;


import java.io.File;
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
	
	@Override
	public String generateEmployeeNumber(EmployeePosition employeePosition) {
		//OFR-00001  
		//AFR-00001
		String prefix = "";
		if (employeePosition != null) {
			if (employeePosition.getEmployeeType() != null) {
			    prefix = prefix + employeePosition.getEmployeeType().getPrefix();
			}
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
		
		if (filter != null && filter != "") {
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
		Optional<EmployeeProfile> o = null;
		if (nameInput.equals("covidCertificate")) {
			o = repository.findByIdEmployeeClinicalData(id);
		} else if (nameInput.equals("bankAccountFile")) { 
			o = repository.findByIdEmployeePayroll(id);
		} else {
			o = repository.findById(id);
		}
		if (o == null || !o.isPresent()) {
			throw new EntityIdNotFoundException("No se pudo subir archivo, Id Empleado " + id + " no encontrado");
		}
		EmployeeProfile entity = o.get();
		StringBuilder urlFile = new StringBuilder(this.urlDocuments);
		if (!fileUpload.isEmpty()) {
			String contentType = fileUpload.getContentType();
			if (contentType == null || this.uploadFormatsAllow.indexOf(contentType) < 0) {
				throw new UploadFormatsAllowException("y es (" + contentType + ")");
			}
			String newNameFile = null;
			switch (nameInput) {
				case "fileCurp":
				case "frontPhoto":
				case "leftPhoto":
				case "rightPhoto":
				case "covidCertificate":
				case "bankAccountFile":
					newNameFile = UtilFile.saveFile(fileUpload, this.uploadDocuments + entity.getEcript() + File.separator);
					if (newNameFile != null) {
						// Procesamos la variable nombreImagen
						urlFile.append(entity.getEcript())
						  .append(File.separator)
						  .append(newNameFile);
						if (nameInput.equals("fileCurp")) {
							entity.setFileCurp(urlFile.toString());
						} else if (nameInput.equals("frontPhoto")) {
							entity.setFrontPhoto(urlFile.toString());
						} else if (nameInput.equals("leftPhoto")) {
							entity.setLeftPhoto(urlFile.toString());
						} else if (nameInput.equals("rightPhoto")) {
							entity.setRightPhoto(urlFile.toString());
						} else if (nameInput.equals("covidCertificate")) {
							entity.getEmployeeClinicalData().setCovidCertificate(urlFile.toString());
						} else if (nameInput.equals("bankAccountFile")) {
							entity.getEmployeePayroll().setBankAccountFile(urlFile.toString());
						}
					} else {
						throw new UploadFileException();
					}
					//Una vez que es obtenido el MultipartFile del /tmp es borrado y marca error si otro proceso lo quieres acceder
					//entity.setFileCurp(fileUpload.getBytes());
					break;
				default:
					throw new UnknownOptionException();
			}
			
		} else {
			throw new UploadFileException("No se pudo subir archivo, porque esta vació");
		}
		
		if (repository.save(entity) == null) {
			throw new CreateRegisterException();
		}
		Map<String, Object> response = new HashMap<>();
		response.put("message", urlFile.toString());
		
		return response;
	}
	
	@Override
	public Resource getFileEmployee(EmployeeProfile entity, String nameInput) {
		Resource resource = null;
		
		switch (nameInput) {
			case "fileCurp":
				if (entity.getFileCurp() != null && entity.getFileCurp() != "") {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getFileCurp());
				}
				break;
			case "leftPhoto":
				if (entity.getLeftPhoto() != null && entity.getLeftPhoto() != "") {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getLeftPhoto());
				}
				break;
			case "frontPhoto":
				if (entity.getFrontPhoto() != null && entity.getFrontPhoto() != "") {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getFrontPhoto());
				}
				break;
			case "rightPhoto":
				if (entity.getRightPhoto() != null && entity.getRightPhoto() != "") {
					resource = UtilFile.getFileStream(this.uploadBaseDocuments + entity.getRightPhoto());
				}
				break;
			case "covidCertificate":
				if (entity.getEmployeeClinicalData() != null) {
					if (entity.getEmployeeClinicalData().getCovidCertificate() != null 
					&& entity.getEmployeeClinicalData().getCovidCertificate() != "") {
						resource = UtilFile.getFileStream(this.uploadBaseDocuments 
								+ entity.getEmployeeClinicalData().getCovidCertificate());
					}
				}
				break;
			case "bankAccountFile":
				if (entity.getEmployeePayroll() != null) {
					if (entity.getEmployeePayroll().getBankAccountFile() != null 
					&& entity.getEmployeePayroll().getBankAccountFile() != "") {
						resource = UtilFile.getFileStream(this.uploadBaseDocuments 
								+ entity.getEmployeePayroll().getBankAccountFile());
					}
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
			    if (entity.getEmployeePayroll().getClabe() != "") {
					if (repository.countByClabeWithAnotherEmployee(entity.getEmployeePayroll().getClabe(), id) > 0) {
						Map<String, Object> errors = new HashMap<>();
						errors.put("employeePayroll_clabe", "El campo Clabe esta asociado a otro perfil ingrese uno nuevo");
						return errors;
					}
			    }
			}
		}
		
		return null;
	}

}
