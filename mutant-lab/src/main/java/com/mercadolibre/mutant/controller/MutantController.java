package com.mercadolibre.mutant.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.mutant.dto.MutantDTO;
import com.mercadolibre.mutant.dto.StatisticsDTO;
import com.mercadolibre.mutant.service.MutantService;

@RestController
@RequestMapping()
public class MutantController {
	
	@Autowired
	MutantService mutantService;
	
	
	/**
	 * @author <a href="mailto:jupagaru@gmail.com">Juan Pablo García</a>
	 * 
	 * Metodo encargado de validar el ADN de una persona, corroborando 
	 * si es mutante o no
	 * 
	 * @param mutantDTO
	 * @return ResponseEntity<Boolean> 
	 * @throws Exception
	 */
	@PostMapping("/mutant")
	public ResponseEntity<Boolean> isMutant(@Valid @RequestBody MutantDTO mutantDTO) throws Exception{
		Boolean isMutant = mutantService.save(mutantDTO);
		if (isMutant) {
			return new ResponseEntity<>(isMutant, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(isMutant, HttpStatus.FORBIDDEN);
		}

	}
	
	/**
	 * @author <a href="mailto:jupagaru@gmail.com">Juan Pablo García</a>
	 * 
	 * Metodo encargado de obtener las estádisticas de la información alojada
	 * en la base de datos.
	 * 
	 * @return StatisticsDTO
	 * @throws Exception
	 */
	@GetMapping("/stats")
	public ResponseEntity<StatisticsDTO> findAll() throws Exception{
		StatisticsDTO statisticsDTO = mutantService.findAll();
		return ResponseEntity.ok().body(statisticsDTO);
	}

}
