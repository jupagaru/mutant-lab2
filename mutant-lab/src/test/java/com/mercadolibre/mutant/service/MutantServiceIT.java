package com.mercadolibre.mutant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.dto.MutantDTO;

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
		
		MutantDTO mutant = new MutantDTO();
		List<String> listAdn = new ArrayList<>();
		listAdn.add("ATGCGA");
		listAdn.add("CAGTGC");
		listAdn.add("TTATGT");
		listAdn.add("AGAAGG");
		listAdn.add("CCCCTA");
		listAdn.add("TCACTG");
		
		mutant.setAdn(listAdn);
		
		//Act
		mutantService.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro es nulo, no se pudo grabar");
	}
	
	
	@Test
	@Order(5)
	void debeConsultarTodosLosRegistros() {
		//Arrange
		List<Mutant> mutants = null;
		
		
		//Act
		mutants = mutantService.findAll();
		
		mutants.forEach(mutant -> log.info(mutant.getAdn().toString()));
		
		//Assert
		assertFalse(mutants.isEmpty(), "No encontró registros");
		
	}
	
	@Test
	@Order(6)
	void debeCrearPasandoArgumentosDTO() throws Exception {
		//Arrange
		
		List<String> listAdn = new ArrayList<>();
		listAdn.add("ATGCGA");
		listAdn.add("CAGTGC");
		listAdn.add("TTATGT");
		listAdn.add("AGAAGG");
		listAdn.add("CCCCTA");
		listAdn.add("TCACTG");
		
		MutantDTO mutant = new MutantDTO(listAdn);
		
		
		//Act
		mutantService.save(mutant);
		
		//Assert
		assertNotNull(mutant, "El registro no se pudo grabar");
	}
	

}
