package com.mercadolibre.mutant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercadolibre.mutant.domain.Mutant;

public interface MutantRepository extends JpaRepository<Mutant, Integer>{

}
