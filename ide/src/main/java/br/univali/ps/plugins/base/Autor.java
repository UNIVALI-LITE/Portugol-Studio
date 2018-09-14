package br.univali.ps.plugins.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
*
* @author Rafael Ferreira Costa
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nome", "email" })
public class Autor {

	@JsonProperty("nome")
	private String nome;
	
	@JsonProperty("email")
	private String email;

	public Autor() 
	{
	
	}
	
	@JsonProperty("nome")
	public String getNome() {
		return nome;
	}

	@JsonProperty("nome")
	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}