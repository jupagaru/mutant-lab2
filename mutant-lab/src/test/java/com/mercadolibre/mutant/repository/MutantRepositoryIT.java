package com.mercadolibre.mutant.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.repository.MutantRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
class MutantRepositoryIT {
	
	@Autowired
	MutantRepository mutantRepository;

	@Test
	@Order(1)
	void debeValidarLasDependencias() {
		assertNotNull(mutantRepository);
	}
	
	@Test
	@Order(2)
	void debeCrearRegistroMutant() {
		//Arrange
		Integer mutantId = 1234;
		
		Mutant mutant = new Mutant();
		mutant.setMutantId(mutantId);
		mutant.setAdn("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG");
		mutant.setIsMutant("S");
		
		//Act
		mutantRepository.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro es nulo, no se pudo grabar");
	}
	
	@Test
	@Order(3)
	void debeModificarRegistroMutant() {
		//Arrange
		Integer mutantId = 1234;
		Mutant mutant = null;
		
		mutant = mutantRepository.findById(mutantId).get();
		mutant.setIsMutant("N");
		
		//Act
		mutantRepository.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro nos se pudo modificar");
	}
	
	@Test
	@Order(4)
	void debeBorrarRegistroMutant() {
		//Arrange
		Integer mutantId = 1234;
		Mutant mutant = null;
		Optional<Mutant> mutantOptional = null;
		
		assertTrue(mutantRepository.findById(mutantId).isPresent(), "No encontró el registro");
		mutant = mutantRepository.findById(mutantId).get();
		
		//Act
		mutantRepository.delete(mutant);
		mutantOptional = mutantRepository.findById(mutantId);
		
		//Assert
		assertFalse(mutantOptional.isPresent(), "No se pudo borrar el registro");
	}
	
	@Test
	@Order(5)
	void debeConsultarTodosLosRegistros() {
		//Arrange
		List<Mutant> mutants = null;
		
		
		//Act
		mutants = mutantRepository.findAll();
		
		mutants.forEach(mutant -> log.info(mutant.getMutantId().toString()));
		
		//Assert
		assertFalse(mutants.isEmpty(), "No encontró registros");
		
	}
	
	@Test
	@Order(6)
	void debeCrearRegistroMutantAllArg() {
		//Arrange
		Integer mutantId = 1237;
		String adn = "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG";
		String isMutant = "S";
		Mutant mutant = new Mutant(mutantId, adn, isMutant);
		
		//Act
		mutantRepository.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro es nulo, no se pudo grabar");
	}

}
