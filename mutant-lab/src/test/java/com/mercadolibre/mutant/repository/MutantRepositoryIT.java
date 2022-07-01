package com.mercadolibre.mutant.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

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
		
		Mutant mutant = new Mutant();
		mutant.setAdn("ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG");
		mutant.setIsMutant("S");
		
		//Act
		mutantRepository.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro es nulo, no se pudo grabar");
	}
	
	
	@Test
	@Order(3)
	void debeConsultarTodosLosRegistros() {
		//Arrange
		List<Mutant> mutants = null;
		
		
		//Act
		mutants = mutantRepository.findAll();
		
		mutants.forEach(mutant -> log.info(mutant.getAdn().toString()));
		
		//Assert
		assertFalse(mutants.isEmpty(), "No encontró registros");
		
	}
	
	@Test
	@Order(4)
	void debeCrearRegistroMutantWithoutArg() {
		//Arrange
		String adn = "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG";
		String isMutant = "S";
		Mutant mutant = new Mutant();
		mutant.setAdn(adn);
		mutant.setIsMutant(isMutant);
		//Act
		mutantRepository.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro es nulo, no se pudo grabar");
	}
	
	@Test
	@Order(5)
	void debeCrearRegistroMutantAllArg() {
		//Arrange
		String adn = "ATGCGA,CAGTGC,TTATGT,AGAAGG,CCCCTA,TCACTG";
		String isMutant = "S";
		Mutant mutant = new Mutant(adn, isMutant);
		
		//Act
		mutantRepository.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro es nulo, no se pudo grabar");
	}

}
