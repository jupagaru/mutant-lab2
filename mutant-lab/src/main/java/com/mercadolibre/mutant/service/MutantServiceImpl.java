package com.mercadolibre.mutant.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.repository.MutantRepository;

@Service
public class MutantServiceImpl implements MutantService{

	
	@Autowired
	MutantRepository mutantRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public List<Mutant> findAll() {
		return mutantRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Mutant> findById(Integer id) {
		return mutantRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Mutant save(Mutant entity) throws Exception {
		if(entity==null) {
			throw new Exception("El mutant es nulo");
		}
		
		validate(entity);
		
		if(mutantRepository.existsById(entity.getMutantId())) {
			throw new Exception("El mutant ya existe");
		}
		
		return mutantRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Mutant update(Mutant entity) throws Exception {
		if(entity==null) {
			throw new Exception("El mutant es nulo");
		}
		
		validate(entity);
		
		if(mutantRepository.existsById(entity.getMutantId())==false) {
			throw new Exception("El mutant no existe");
		}
		
		return mutantRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(Mutant entity) throws Exception {
		if(entity==null) {
			throw new Exception("El mutant es nulo");
		}
		
		if(entity.getMutantId()==null) {
			throw new Exception("El mutant id es nulo");
		}
		
		if(mutantRepository.existsById(entity.getMutantId())==false) {
			throw new Exception("El mutant no existe");
		}
		
		
		mutantRepository.deleteById(entity.getMutantId());
		
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		if(id==null)
			throw new Exception("El id es nulo");
		
		if(mutantRepository.existsById(id)==false) {
			throw new Exception("El customer no existe");
		}
		
		delete(mutantRepository.findById(id).get());	
		
	}

	@Override
	public void validate(Mutant entity) throws Exception {
		Set<ConstraintViolation<Mutant>> constraintViolations=validator.validate(entity);
		if(constraintViolations.isEmpty()==false) {
			throw new ConstraintViolationException(constraintViolations);
		}		
		
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return mutantRepository.count();
	}

}
