package com.mercadolibre.mutant.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mutant", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mutant implements Serializable{
	
	private static final long serialVersionUID = 2447601727166923309L;
	
	@Id
	@Column(name = "adn", nullable = false)
	@NotNull
	private String adn;
	
	@Column(name = "is_mutant", nullable = false)
	@NotNull
	private String isMutant;
	

}