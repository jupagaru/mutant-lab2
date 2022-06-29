package com.mercadolibre.mutant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.dto.MutantDTO;
import com.mercadolibre.mutant.mapper.MutantMapper;
import com.mercadolibre.mutant.service.MutantService;

@RestController
@RequestMapping("api/v1/mutants")
public class MutantController {
	
	@Autowired
	MutantService mutantService;
	
	@Autowired
	MutantMapper mutantMapper;
	
	@PutMapping
	public MutantDTO update(@Valid @RequestBody MutantDTO mutantDTO) throws Exception {
		Mutant mutant = mutantMapper.mutantDTOToMutant(mutantDTO);
		mutant = mutantService.update(mutant);
		mutantDTO = mutantMapper.mutantToMutantDTO(mutant);

		return mutantDTO;
	}

	
	@PostMapping
	public MutantDTO save(@Valid @RequestBody MutantDTO mutantDTO) throws Exception{
		Mutant mutant = mutantMapper.mutantDTOToMutant(mutantDTO);
		mutant = mutantService.save(mutant);
		mutantDTO = mutantMapper.mutantToMutantDTO(mutant);
		return mutantDTO;
	}
	
	@GetMapping("/{mutantId}")
	public MutantDTO findById(@PathVariable("mutantId") Integer  mutantId) throws Exception{
		Mutant mutant = (mutantService.findById(mutantId).isPresent()==true) ? mutantService.findById(mutantId).get():null;
		return mutantMapper.mutantToMutantDTO(mutant);
	}
	@GetMapping()
	public List<MutantDTO> findAll() throws Exception{
		
		List<Mutant> mutants = mutantService.findAll();
		List<MutantDTO> mutantDTOs = mutantMapper.mutantListToMutantDTOList(mutants);
		
		return mutantDTOs;
	}

}
