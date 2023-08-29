package com.sip.syshumres_services.common;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Interface generica para Clases Service
 * 
 * @author Prong
 * @version 2.0
 */
public interface CommonService<E> {
	
	public Iterable<E> findAll();
	
	public Iterable<E> findAll(Sort sort);
	
	public Page<E> findAll(Pageable pageable);
	
	public Optional<E> findById(Long id);
	
	public E save(E entity);
	
	public void deleteById(Long id);

}
