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
import com.mercadolibre.mutant.mapper.MutantMapper;
import com.mercadolibre.mutant.service.MutantService;

@RestController
@RequestMapping("/mutant")
public class MutantController {
	
	@Autowired
	MutantService mutantService;
	
	@Autowired
	MutantMapper mutantMapper;
	
	@PostMapping
	public ResponseEntity<Boolean> save(@Valid @RequestBody MutantDTO mutantDTO) throws Exception{
		Boolean isMutant = false;
		
		isMutant = mutantService.save(mutantDTO);
		return new ResponseEntity<>(isMutant, HttpStatus.OK);
		// ResponseEntity.ok().body(mutantMapper.mutantToMutantDTO(mutant));

	}
	
	@GetMapping()
	public ResponseEntity<List<MutantDTO>> findAll() throws Exception{
		
		List<Mutant> mutants = mutantService.findAll();
		//List<MutantDTO> mutantDTOs = mutantMapper.mutantListToMutantDTOList(mutants);
		
		return null; //ResponseEntity.ok().body(mutantMapper.mutantListToMutantDTOList(mutants));
	}

}
