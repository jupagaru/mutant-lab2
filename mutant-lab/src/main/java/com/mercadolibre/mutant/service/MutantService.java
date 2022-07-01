package com.mercadolibre.mutant.service;

import java.util.List;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.dto.MutantDTO;

public interface MutantService {
	
	List<Mutant> findAll();

	/**
	 * @author <a href="mailto:jupagaru@gmail.com">Juan Pablo García</a>
	 * 
	 * Metodo encargado de validar el ADN de una persona, corroborando 
	 * si es mutante o no. Una vez validad se guarda en base de datos
	 * 
	 * @param mutantDTO
	 * @return Boolean
	 * @throws Exception
	 */
	Boolean save(MutantDTO mutantDTO) throws Exception;

	
	/**
	 * @author <a href="mailto:jupagaru@gmail.com">Juan Pablo García</a>
	 * 
	 * Metodo encargada de validar que la información enviada sea la correcta.
	 * 
	 * @param mutant
	 * @throws Exception
	 */
	void validate(Mutant mutant) throws Exception;

}
