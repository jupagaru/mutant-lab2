package com.mercadolibre.mutant.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mutant", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mutant implements Serializable{
	
	private static final long serialVersionUID = 2447601727166923309L;
	
	@Id
	@Column(name="mutant_id", unique = true, nullable = false)
	@NotNull
	private Integer mutantId;
	
	@Column(name = "adn", nullable = false)
	@NotNull
	private String adn;
	
	@Column(name = "is_mutant", nullable = false)
	@NotNull
	private String isMutant;
	

}
