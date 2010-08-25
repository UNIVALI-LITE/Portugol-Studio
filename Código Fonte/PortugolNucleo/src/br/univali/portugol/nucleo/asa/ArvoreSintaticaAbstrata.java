package br.univali.portugol.nucleo.asa;

import java.util.List;

public abstract class ArvoreSintaticaAbstrata
{	
	private List<NoDeclaracao> listaDeclaracoesGlobais;
	private List<NoInclusaoBiblioteca> listaInclusaoBibliotecas;
		
	public ArvoreSintaticaAbstrata()
	{		
	}	
	
	public List<NoDeclaracao> getListaDeclaracoesGlobais()
	{
		return listaDeclaracoesGlobais;
	}
	
	public List<NoInclusaoBiblioteca> getListaInclusaoBibliotecas()
	{
		return listaInclusaoBibliotecas;
	}

	public void setListaDeclaracoesGlobais(List<NoDeclaracao> listaDeclaracoesGlobais)
	{
		this.listaDeclaracoesGlobais = listaDeclaracoesGlobais;
	}
	
	public void setListaInclusaoBibliotecas(List<NoInclusaoBiblioteca> listaInclusaoBibliotecas)
	{
		this.listaInclusaoBibliotecas = listaInclusaoBibliotecas;
	}
}
