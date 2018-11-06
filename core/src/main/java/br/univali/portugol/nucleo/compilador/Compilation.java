package br.univali.portugol.nucleo.compilador;

import org.json.JSONArray;

public class Compilation {
	Boolean sent = false;
	Boolean successfulCompilation;
	String compileDate;
	int number_characters;
	int numberOfFunctions;
	int number_lines;
	JSONArray compilation_errors;
	JSONArray warnings;
}
