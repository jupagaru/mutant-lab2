package com.mercadolibre.mutant.service;

import java.util.List;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.dto.MutantDTO;

public interface MutantService {
	
	List<Mutant> findAll();

	Boolean save(MutantDTO mutantDTO) throws Exception;

	void validate(Mutant mutant) throws Exception;

}
