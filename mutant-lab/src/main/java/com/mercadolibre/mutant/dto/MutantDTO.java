package com.mercadolibre.mutant.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutantDTO {
	
	@NotNull
	private Integer mutantId;
	
	@NotNull
	private String adn;
	
	@NotNull
	private String isMutant;

}
