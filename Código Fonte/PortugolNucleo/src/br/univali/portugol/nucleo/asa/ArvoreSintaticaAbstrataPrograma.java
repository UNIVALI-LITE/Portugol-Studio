package br.univali.portugol.nucleo.asa;

public final class ArvoreSintaticaAbstrataPrograma extends ArvoreSintaticaAbstrata
{
	private String nomeTokenPrograma;
	
	public ArvoreSintaticaAbstrataPrograma(String nomeTokenPrograma)
	{
		super();
		this.nomeTokenPrograma = nomeTokenPrograma;
	}
	
	public String getNomeTokenPrograma()
	{
		return nomeTokenPrograma;
	}
}
