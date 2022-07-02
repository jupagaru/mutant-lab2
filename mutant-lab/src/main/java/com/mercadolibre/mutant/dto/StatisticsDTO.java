package com.mercadolibre.mutant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticsDTO {
	
	private Double countMutantDna;
	private Double countHumanDna;
	private String ratio;

}
