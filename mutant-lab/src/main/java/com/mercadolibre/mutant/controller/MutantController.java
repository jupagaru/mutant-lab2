package com.mercadolibre.mutant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.dto.MutantDTO;
import com.mercadolibre.mutant.service.MutantService;

@RestController
@RequestMapping("/mutant")
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
	@PostMapping
	public ResponseEntity<Boolean> isMutant(@Valid @RequestBody MutantDTO mutantDTO) throws Exception{
		Boolean isMutant = mutantService.save(mutantDTO);
		return new ResponseEntity<>(isMutant, HttpStatus.OK);

	}
	
	@GetMapping()
	public ResponseEntity<List<Mutant>> findAll() throws Exception{
		
		List<Mutant> mutants = mutantService.findAll();
		//List<MutantDTO> mutantDTOs = mutantMapper.mutantListToMutantDTOList(mutants);
		
		return ResponseEntity.ok().body(mutants);
	}

}
