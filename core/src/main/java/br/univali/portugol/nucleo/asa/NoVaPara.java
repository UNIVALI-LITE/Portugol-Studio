/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Nereu
 */
public class NoVaPara extends NoBloco
{
    private NoTitulo titulo;
    private TrechoCodigoFonte trechoCodigoFonte;
    
    public NoVaPara(NoTitulo titulo)
    {
        this.titulo = titulo;
    }

    /**
     * @return the titulo
     */
    public NoTitulo getTitulo()
    {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(NoTitulo titulo)
    {
        this.titulo = titulo;
    }

    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
}
