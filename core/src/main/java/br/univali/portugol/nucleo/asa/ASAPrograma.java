package br.univali.portugol.nucleo.asa;

import java.util.List;

/**
 * Representa a ASA de um programa.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @see ASA
 */
public final class ASAPrograma extends ASA
{
    private List<NoInclusaoBiblioteca> listaInclusoesBibliotecas;
    
    private int totalVariaveisDeclaradas = 0; 
    private int totalVetoresDeclarados = 0;
    private int totalMatrizesDeclaradas = 0;
    
    public ASAPrograma()
    {
        
    }

    public void setTotalMatrizesDeclaradas(int totalMatrizesDeclaradas)
    {
        this.totalMatrizesDeclaradas = totalMatrizesDeclaradas;
    }

    public void setTotalVariaveisDeclaradas(int totalVariaveisDeclaradas)
    {
        this.totalVariaveisDeclaradas = totalVariaveisDeclaradas;
    }

    public void setTotalVetoresDeclarados(int totalVetoresDeclarados)
    {
        this.totalVetoresDeclarados = totalVetoresDeclarados;
    }
    
    public int getTotalVariaveisDeclaradas()
    {
        return totalVariaveisDeclaradas;
    }
    
    public int getTotalVetoresDeclarados()
    {
        return totalVetoresDeclarados;
    }
    
    public int getTotalMatrizesDeclaradas()
    {
        return totalMatrizesDeclaradas;
    }
    
    public List<NoInclusaoBiblioteca> getListaInclusoesBibliotecas()
    {
        return listaInclusoesBibliotecas;
    }

    public void setListaInclusoesBibliotecas(List<NoInclusaoBiblioteca> listaInclusoesBibliotecas)
    {
        this.listaInclusoesBibliotecas = listaInclusoesBibliotecas;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
}