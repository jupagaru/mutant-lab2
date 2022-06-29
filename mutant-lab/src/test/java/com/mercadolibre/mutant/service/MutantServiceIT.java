package com.mercadolibre.mutant.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.mutant.domain.Mutant;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class MutantServiceIT {
	
		
	@Autowired
	MutantService mutantService;
	
	
	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(mutantService);
	}
	
	@Test
	@Order(2)
	void debeCrearRegistroMutant() throws Exception {
		//Arrange
		Integer mutantId = 1234;
		
		Mutant mutant = new Mutant();
		mutant.setMutantId(mutantId);
		mutant.setAdn("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG");
		mutant.setIsMutant("S");
		
		//Act
		mutantService.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro es nulo, no se pudo grabar");
	}
	
	@Test
	@Order(3)
	void debeModificarRegistroMutant() throws Exception {
		//Arrange
		Integer mutantId = 1234;
		Mutant mutant = null;
		
		mutant = mutantService.findById(mutantId).get();
		mutant.setIsMutant("N");
		
		//Act
		mutantService.update(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro nos se pudo modificar");
	}
	
	@Test
	@Order(4)
	void debeBorrarRegistroMutant() throws Exception {
		//Arrange
		Integer mutantId = 1234;
		Mutant mutant = null;
		Optional<Mutant> mutantOptional = null;
		
		assertTrue(mutantService.findById(mutantId).isPresent(), "No encontró el registro");
		mutant = mutantService.findById(mutantId).get();
		
		//Act
		mutantService.delete(mutant);
		mutantOptional = mutantService.findById(mutantId);
		
		//Assert
		assertFalse(mutantOptional.isPresent(), "No se pudo borrar el registro");
	}
	
	@Test
	@Order(5)
	void debeConsultarTodosLosRegistros() {
		//Arrange
		List<Mutant> mutants = null;
		
		
		//Act
		mutants = mutantService.findAll();
		
		mutants.forEach(mutant -> log.info(mutant.getMutantId().toString()));
		
		//Assert
		assertFalse(mutants.isEmpty(), "No encontró registros");
		
	}

}
