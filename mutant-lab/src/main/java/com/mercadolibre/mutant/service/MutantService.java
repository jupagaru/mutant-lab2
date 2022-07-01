package com.mercadolibre.mutant.service;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.dto.MutantDTO;
import com.mercadolibre.mutant.dto.StatisticsDTO;

public interface MutantService {
	
	/*+
	 * @author <a href="mailto:jupagaru@gmail.com">Juan Pablo García</a>
	 * 
	 * Metodo encargado de obtener la estadísticas de los valores
	 * alojados en la base de datos.
	 * 
	 * @param 
	 * @return StatisticsDTO
	 * @throws Exception
	 */
	StatisticsDTO findAll()throws Exception;

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
