package com.sip.syshumres_services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.BranchOffice;
import com.sip.syshumres_entities.EmployeeClinicalData;
import com.sip.syshumres_entities.EmployeeGeneralData;
import com.sip.syshumres_entities.EmployeeLaborData;
import com.sip.syshumres_entities.EmployeeOperation;
import com.sip.syshumres_entities.EmployeePayroll;
import com.sip.syshumres_entities.EmployeeProfile;
import com.sip.syshumres_entities.EmployeeStatus;
import com.sip.syshumres_entities.ProspectProfile;
import com.sip.syshumres_entities.ProspectStatus;
import com.sip.syshumres_entities.User;
import com.sip.syshumres_exceptions.EmployeeFieldsAlreadyExistException;
import com.sip.syshumres_exceptions.ProspectFieldsAlreadyExistException;
import com.sip.syshumres_repositories.EmployeeProfileRepository;
import com.sip.syshumres_repositories.ProspectProfileRepository;
import com.sip.syshumres_services.common.CommonServiceImpl;
import com.sip.syshumres_utils.RandomString;


@Service
public class ProspectProfileServiceImpl extends CommonServiceImpl<ProspectProfile, ProspectProfileRepository> 
implements ProspectProfileService {
	
	private final EmployeeProfileRepository repositoryE;
	
	@Value("${SIZE.HASH.DIR.UPLOAD.EMPLOYEE}")
	private int sizeHashDirUploadEmployee;
	
	@Autowired
	public ProspectProfileServiceImpl(EmployeeProfileRepository repositoryE) {
		super();
		this.repositoryE = repositoryE;
	}

	@Override
	@Transactional
	public ProspectProfile save(ProspectProfile entity, BranchOffice branchOffice, ProspectStatus status) {
		entity.setBranchOffice(branchOffice);
		entity.setProspectStatus(status);
		//Create hash for a dir employee
		entity.setEcript(RandomString.getRandomStringStream(this.sizeHashDirUploadEmployee));
		
		return repository.save(entity);
	}
	
	@Override
	@Transactional
	public EmployeeProfile saveNewHire(ProspectProfile entity, String employeeNumber, ProspectStatus status, 
			EmployeeStatus statusE) {		
		EmployeeProfile e = new EmployeeProfile();
		e.setFirstName(entity.getFirstName());
		e.setLastName(entity.getLastName());
		e.setLastNameSecond(entity.getLastNameSecond());
		e.setBranchOffice(entity.getBranchOffice());
		e.setCurp(entity.getCurp());
		e.setCellNumber(entity.getCellNumber());
		e.setDateBirth(entity.getDateBirth());
		e.setEcript(entity.getEcript());
		e.setEmail(entity.getEmail());
		e.setGender(entity.getGender());
		e.setEmployeeStatus(statusE);
		e.setEmployeePosition(entity.getEmployeePosition());
		e.setEmployeeNumber(employeeNumber);
		
		//Create Payroll
		EmployeePayroll ep = new EmployeePayroll();
		if (entity.getRfc() != null && entity.getRfc() != "") {
			ep.setRfc(entity.getRfc());
		}
		e.setEmployeePayroll(ep);
		
		EmployeeClinicalData ec = new EmployeeClinicalData();
		e.setEmployeeClinicalData(ec);
		
		EmployeeGeneralData eg = new EmployeeGeneralData();
		e.setEmployeeGeneralData(eg);
		
		EmployeeLaborData el = new EmployeeLaborData();
		e.setEmployeeLaborData(el);
		
		EmployeeOperation eo = new EmployeeOperation();
		e.setEmployeeOperation(eo);
		
		entity.setProspectStatus(status);
		if (repository.save(entity) == null) {
			return null;
		}
		
		return repositoryE.save(e);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProspectProfile> listBranchOffice(Long idBranchOffice, Pageable pageable) {
		return repository.listBranchOffice(idBranchOffice, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProspectProfile> findByFullNameLikeOrRfcLikeOrCurpLikeOr(Long idBranchOffice, String text,
			Pageable pageable) {
		return repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(idBranchOffice, text, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProspectProfile> findByFullNameLikeOrRfcLikeOrCurpLikeOr(String text, Pageable pageable) {
		return repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(text, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<ProspectProfile> findByFilterSession(String filter, User user, Pageable pageable) {
		Page<ProspectProfile> entities = null;
		
		if (filter != null && filter != "") {
			if (user.isSeeAllBranchs()) {
				entities = repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(filter, pageable);
			} else {
			    entities = repository.findByFullNameLikeOrRfcLikeOrCurpLikeOr(user.getBranchOffice().getId(), 
			    		filter, pageable);
			}
		} else {
			if (user.isSeeAllBranchs()) {
				entities = repository.findAll(pageable);
			} else {
				entities = repository.listBranchOffice(user.getBranchOffice().getId(), pageable);
			}
		}
		
		return entities;
	}
	
	@Override
	@Transactional(readOnly = true)
	public void validEntity(ProspectProfile entity, Long id) 
			throws ProspectFieldsAlreadyExistException, EmployeeFieldsAlreadyExistException {
		//valid email repeat
		if (repository.countByEmailWithAnotherProspect(entity.getEmail(), id) > 0) {
			throw new ProspectFieldsAlreadyExistException("El Email ( " + entity.getEmail() + " ) esta asociado a otro Prospecto, valide la información");
		}
		//valid Curp repeat
		if (repository.countByCurpWithAnotherProspect(entity.getCurp(), id) > 0) {
			throw new ProspectFieldsAlreadyExistException("La Curp ( " + entity.getCurp() + " ) esta asociado a otro Prospecto, valide la información");
		}
		//valid Rfc repeat
		if (repository.countByRfcWithAnotherProspect(entity.getRfc(), id) > 0) {
			throw new ProspectFieldsAlreadyExistException("El Rfc ( " + entity.getRfc() + " ) esta asociado a otro Prospecto, valide la información");
		}
						
		//valid email repeat
		if (repositoryE.countByEmailWithAnotherEmployee(entity.getEmail(), 0L) > 0) {
			throw new EmployeeFieldsAlreadyExistException("El Email ( " + entity.getEmail() + " ) esta asociado a otro perfil contratado, valide la información del Prospecto");
		}
		//valid Curp repeat
		if (repositoryE.countByCurpWithAnotherEmployee(entity.getCurp(), 0L) > 0) {
			throw new EmployeeFieldsAlreadyExistException("La Curp ( " + entity.getCurp() + " ) esta asociado a otro perfil contratado, valide la información del Prospecto");
		}
		//valid Rfc repeat
		if (repositoryE.countByRfcWithAnotherEmployee(entity.getRfc(), 0L) > 0) {
			throw new EmployeeFieldsAlreadyExistException("El Rfc ( " + entity.getRfc() + " ) esta asociado a otro perfil contratado, valide la información del Prospecto");
		}
		
	}

}
