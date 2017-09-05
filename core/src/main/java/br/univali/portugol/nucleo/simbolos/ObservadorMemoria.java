package br.univali.portugol.nucleo.simbolos;

/**
 *
 * @author fillipi
 */
public interface ObservadorMemoria
{
    public void simboloAdicionado(Simbolo simbolo);
    
    public void simboloRemovido(Simbolo simbolo);
}
