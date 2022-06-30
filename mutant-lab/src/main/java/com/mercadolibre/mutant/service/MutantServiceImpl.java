package com.mercadolibre.mutant.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.dto.MutantDTO;
import com.mercadolibre.mutant.repository.MutantRepository;

@Service
public class MutantServiceImpl implements MutantService{

	
	@Autowired
	MutantRepository mutantRepository;
	
	@Autowired
	Validator validator;
	
	String[] mutantSequences = {"AAAA", "TTTT", "CCCC", "GGGG"};
	
	@Override
	@Transactional(readOnly = true)
	public List<Mutant> findAll() {
		return mutantRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean save(MutantDTO mutantDTO) throws Exception {
		if(mutantDTO==null) {
			throw new Exception("El mutant es nulo");
		}
		
		char[][] matriz = new char[6][6];
		Mutant mutant = new Mutant();
		boolean isMutant = false;
		
		
		String adnMutant = mutantDTO.getAdn().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
		
		mutant.setAdn(adnMutant);
		
		matriz = crearMatrizMutant(mutantDTO);
		for (char mat[] : matriz) {
        	System.out.println(Arrays.toString(mat));
		}
		int times = 0;

        for (int i = 0; i < mutantSequences.length; i++) {
            times += validarIsMutant(mutantDTO, mutantSequences[i]);
        }
		//isMutant = validarIsMutant(matriz);
		
		Boolean isMutantOk = times >= 1;
		mutant.setIsMutant(isMutantOk ? "S":"N");
		
		validate(mutant);
		
		
		mutantRepository.save(mutant);
		
		return isMutantOk;
	}


	private int validarIsMutant(MutantDTO mutantDTO, String mutantSequence) {
		
		 int times = 0;

	     for (int i = 0; i < mutantDTO.getAdn().size(); i++) {
	            //Busco las ocurrencias horizontales
	            if (mutantDTO.getAdn().get(i).contains(mutantSequence))
	                times++;

	            //Compongo la diagonal vertical
	            String sequenceToAnalyze = "";
	            for (int j = 0; j < mutantDTO.getAdn().size(); j++) {
	                sequenceToAnalyze += mutantDTO.getAdn().get(j).charAt(i);
	            }

	            //Busco las ocurrencias verticales
	            if (sequenceToAnalyze.contains(mutantSequence))
	                times++;
	        }

	        //La diferencia entre la dimension de la matriz y el largo de la secuencia
	        //me sirve para el rango en x  e y que tengo que recorrer de las diagonales
	        //Utilizo este rango calculado para evitar algunos bucles del for que serian innecesarios
	        int lengthDifference = mutantDTO.getAdn().size() - mutantSequence.length();

	        //Busco las ocurrencias en la diagonal inferior y diagonal central, de arriba para abajo
	        for (int i = lengthDifference; i >= 0; i--) {
	            String sequenceToAnalyze = "";
	            for (int j = 0; j < mutantDTO.getAdn().size() - i; j++) {
	                sequenceToAnalyze += mutantDTO.getAdn().get(i + j).charAt(j);
	            }
	            if (sequenceToAnalyze.contains(mutantSequence))
	                times++;
	        }
	        //Busco las ocurrencias en la diagonal superior, de arriba para abajo
	        for (int i = 1; i <= lengthDifference; i++) {
	            String sequenceToAnalyze = "";
	            for (int j = 0; j < mutantDTO.getAdn().size() - i; j++) {
	                sequenceToAnalyze += mutantDTO.getAdn().get(j).charAt(i + j);
	            }
	            if (sequenceToAnalyze.contains(mutantSequence))
	                times++;
	        }


	        //Busco las ocurrencias en la diagonal inferior y diagonal central, de abajo para arriba
	        for (int i = lengthDifference + 1; i < mutantDTO.getAdn().size(); i++) {
	            String sequenceToAnalyze = "";
	            for (int j = 0; j <= i; j++) {
	                sequenceToAnalyze += mutantDTO.getAdn().get(i - j).charAt(j);
	            }
	            if (sequenceToAnalyze.contains(mutantSequence))
	                times++;
	        }

	        //Busco las ocurrencias en la diagonal superior, de abajo para arriba
	        for (int i = 0; i < lengthDifference; i++) {
	            String sequenceToAnalyze = "";

	            for (int j = i + 1; j < mutantDTO.getAdn().size(); j++) {
	                sequenceToAnalyze += mutantDTO.getAdn().get(mutantDTO.getAdn().size() - j + i).charAt(j);
	            }

	            if (sequenceToAnalyze.contains(mutantSequence))
	                times++;
	        }
		
		/*for (char mat[] : matriz) {
        	System.out.println(Arrays.toString(mat));
        }
		
		for(int i = 0; i < matriz.length; i++) {
			for(int j = 0; j< matriz[0].length-3; j++) {
				if((matriz[i][j] == matriz[i][j+1]
					&& matriz[i][j] == matriz[i][j+2]
					&& matriz[i][j] == matriz[i][j+3])
					||(matriz[i][j] == matriz[j][i+1]
						&& matriz[i][j] == matriz[j][i+2]
						&& matriz[i][j] == matriz[j][i+3])
					/*||(matriz[i][j] == matriz[i+1][j+1]
							&& matriz[i][j] == matriz[i+2][j+2]
									&& matriz[i][j] == matriz[i+2][j+3])) {
					return true;
				}
			}
		}*/
		
		return times;
	}
	

	private char[][] crearMatrizMutant(MutantDTO mutantDTO) {
        
        char[][] matriz = new char[6][6];
        
        char[] characterArray = null;
        for(int i = 0; i <mutantDTO.getAdn().size(); i++) {
        	characterArray = mutantDTO.getAdn().get(i).toUpperCase().toCharArray();
        	int j = 0;
        	for(char c : characterArray) {
        		matriz[i][j] = c;
        		j++;
        	}
        }
            
		return matriz;
	}

	@Override
	public void validate(Mutant entity) throws Exception {
		Set<ConstraintViolation<Mutant>> constraintViolations=validator.validate(entity);
		if(constraintViolations.isEmpty()==false) {
			throw new ConstraintViolationException(constraintViolations);
		}		
		
	}

}
