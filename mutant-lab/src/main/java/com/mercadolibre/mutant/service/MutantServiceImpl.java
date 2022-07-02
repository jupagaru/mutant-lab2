package com.mercadolibre.mutant.service;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mercadolibre.mutant.domain.Mutant;
import com.mercadolibre.mutant.dto.MutantDTO;
import com.mercadolibre.mutant.dto.StatisticsDTO;
import com.mercadolibre.mutant.repository.MutantRepository;

@Service
public class MutantServiceImpl implements MutantService {

	static final String nombreConstante = "AAAA";

	@Value("${adn.sequence.adenina}")
	private String sequenceAdenina;

	@Value("${adn.sequence.timina}")
	private String sequenceTimina;

	@Value("${adn.sequence.citosina}")
	private String sequenceCitosina;

	@Value("${adn.sequence.guanina}")
	private String sequenceGuanina;

	@Autowired
	MutantRepository mutantRepository;

	@Autowired
	Validator validator;

	@Override
	@Transactional(readOnly = true)
	public StatisticsDTO findAll() throws Exception {
		List<Mutant> lstMutant = mutantRepository.findAll();

		double countMutantDna = 0;
		double countHumanDna = 0;
		double resultadoRatio = 0;
		for (Mutant mutant : lstMutant) {
			if (mutant.getIsMutant().equals("S")) {
				countMutantDna++;
			} else {
				countHumanDna++;
			}
		}
		StatisticsDTO satisticsDTO = new StatisticsDTO();
		satisticsDTO.setCountHumanDna(countHumanDna);
		satisticsDTO.setCountMutantDna(countMutantDna);

		DecimalFormat df = new DecimalFormat("#.00");
		resultadoRatio = (countMutantDna / countHumanDna);
		
		String ratio = String.valueOf(df.format(resultadoRatio));
		satisticsDTO.setRatio(ratio);
		return satisticsDTO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Boolean save(MutantDTO mutantDTO) throws Exception {
		if (mutantDTO == null) {
			throw new Exception("Por favor ingrese una secuencia de ADN");
		}

		String[] adnSequences = { sequenceAdenina, sequenceTimina, sequenceCitosina, sequenceGuanina };

		/*
		 * Concatenamos la secuencia para luego proceder a guardarla en la base de datos
		 * separada por el caracter coma (,)
		 */
		String adnMutant = mutantDTO.getAdn().stream().map(Object::toString).collect(Collectors.joining(","));

		validarInformacionSuministrada(adnMutant, mutantDTO);

		/*
		 * Esto se hace con el fin de graficar la matriz y poder visualizarla en la
		 * consola, para así poder validar el correcto funcionamiento.
		 */
		char[][] matriz = new char[mutantDTO.getAdn().size()][mutantDTO.getAdn().size()];

		matriz = crearMatrizMutant(mutantDTO);
		for (char mat[] : matriz) {
			System.out.println(Arrays.toString(mat));
		}

		Mutant mutant = new Mutant();
		mutant.setAdn(adnMutant);
		int times = 0;

		for (int i = 0; i < adnSequences.length; i++) {
			times += validarIsMutant(mutantDTO, adnSequences[i]);
		}

		Boolean isMutant = times >= 1;
		mutant.setIsMutant(isMutant ? "S" : "N");

		validate(mutant);

		mutantRepository.save(mutant);

		return isMutant;
	}

	private void validarInformacionSuministrada(String adnMutant, MutantDTO mutantDTO) throws Exception {
		// Valida que la secuencia no tenga números
		if (adnMutant.matches(".*[0-9].*")) {
			throw new Exception("La secuencia no puede contener números, solo debe tener las letras A,T,C,G");
		}

		// Validamos que cada cadena esté conformada por 6 letras y las que corresponden
		for (String mutant : mutantDTO.getAdn()) {
			Pattern pat = Pattern.compile("[a-zA-Z]{6,6}");
			Matcher mat = pat.matcher(mutant);
			if (!mat.matches()) {
				throw new Exception("Cada base nitrogenada debe estar conformada por 6 letras");
			}

			pat = Pattern.compile("(A|T|C|G)+");
			mat = pat.matcher(mutant);
			if (!mat.matches()) {
				throw new Exception("La secuencia no puede contener valores diferentes a las letras A,T,C,G");
			}
		}

	}

	/**
	 * @author <a href="mailto:jupagaru@gmail.com">Juan Pablo García</a>
	 * 
	 *         Metodo encargado de validar el ADN suministado, en caso de que
	 *         coincida incrementará el valor de times.
	 * 
	 *         Nota: La funcionalidad descrita a continuación fue obtenida del
	 *         repositorio https://github.com/andresrvilla/DNA-Analyzer
	 * 
	 * @param mutantDTO
	 * @param mutantSequence
	 * @return int
	 */
	private int validarIsMutant(MutantDTO mutantDTO, String mutantSequence) {

		int times = 0;

		for (int i = 0; i < mutantDTO.getAdn().size(); i++) {
			// Busco las ocurrencias horizontales
			if (mutantDTO.getAdn().get(i).contains(mutantSequence))
				times++;

			// Compongo la diagonal vertical
			String sequenceToAnalyze = "";
			for (int j = 0; j < mutantDTO.getAdn().size(); j++) {
				sequenceToAnalyze += mutantDTO.getAdn().get(j).charAt(i);
			}

			// Busco las ocurrencias verticales
			if (sequenceToAnalyze.contains(mutantSequence))
				times++;
		}

		// La diferencia entre la dimension de la matriz y el largo de la secuencia
		// me sirve para el rango en x e y que tengo que recorrer de las diagonales
		// Utilizo este rango calculado para evitar algunos bucles del for que serian
		// innecesarios
		int lengthDifference = mutantDTO.getAdn().size() - mutantSequence.length();

		// Busco las ocurrencias en la diagonal inferior y diagonal central, de arriba
		// para abajo
		for (int i = lengthDifference; i >= 0; i--) {
			String sequenceToAnalyze = "";
			for (int j = 0; j < mutantDTO.getAdn().size() - i; j++) {
				sequenceToAnalyze += mutantDTO.getAdn().get(i + j).charAt(j);
			}
			if (sequenceToAnalyze.contains(mutantSequence))
				times++;
		}
		// Busco las ocurrencias en la diagonal superior, de arriba para abajo
		for (int i = 1; i <= lengthDifference; i++) {
			String sequenceToAnalyze = "";
			for (int j = 0; j < mutantDTO.getAdn().size() - i; j++) {
				sequenceToAnalyze += mutantDTO.getAdn().get(j).charAt(i + j);
			}
			if (sequenceToAnalyze.contains(mutantSequence))
				times++;
		}

		// Busco las ocurrencias en la diagonal inferior y diagonal central, de abajo
		// para arriba
		for (int i = lengthDifference + 1; i < mutantDTO.getAdn().size(); i++) {
			String sequenceToAnalyze = "";
			for (int j = 0; j <= i; j++) {
				sequenceToAnalyze += mutantDTO.getAdn().get(i - j).charAt(j);
			}
			if (sequenceToAnalyze.contains(mutantSequence))
				times++;
		}

		// Busco las ocurrencias en la diagonal superior, de abajo para arriba
		for (int i = 0; i < lengthDifference; i++) {
			String sequenceToAnalyze = "";

			for (int j = i + 1; j < mutantDTO.getAdn().size(); j++) {
				sequenceToAnalyze += mutantDTO.getAdn().get(mutantDTO.getAdn().size() - j + i).charAt(j);
			}

			if (sequenceToAnalyze.contains(mutantSequence))
				times++;
		}
		return times;
	}

	/**
	 * @author <a href="mailto:jupagaru@gmail.com">Juan Pablo García</a>
	 * 
	 *         Metodo encargada de crear la matriz del mutanta para luego imprimirla
	 *         en consola y proceder a validar su correcto funcionamiento.
	 * 
	 * @param mutantDTO
	 * @return char[][]
	 */
	private char[][] crearMatrizMutant(MutantDTO mutantDTO) {

		char[][] matriz = new char[mutantDTO.getAdn().size()][mutantDTO.getAdn().size()];

		char[] characterArray = null;
		for (int i = 0; i < mutantDTO.getAdn().size(); i++) {
			characterArray = mutantDTO.getAdn().get(i).toUpperCase().toCharArray();
			int j = 0;
			for (char c : characterArray) {
				matriz[i][j] = c;
				j++;
			}
		}

		return matriz;
	}

	@Override
	public void validate(Mutant entity) throws Exception {
		Set<ConstraintViolation<Mutant>> constraintViolations = validator.validate(entity);
		if (constraintViolations.isEmpty() == false) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}

}