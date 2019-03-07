package br.univali.ps.nucleo;

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
	String user_name;
	String local_machine_hostname;
	JSONArray help_examples;
}
