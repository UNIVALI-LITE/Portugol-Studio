package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.TipoDado;

/**
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSemantico
 */
public final class ExcecaoValorSeraConvertido extends Exception
{
    private TipoDado tipoEntrada;
    private TipoDado tipoSaida;

    public ExcecaoValorSeraConvertido(TipoDado tipoEntrada, TipoDado tipoSaida)
    {
        this.tipoEntrada = tipoEntrada;
        this.tipoSaida = tipoSaida;
    }

    public TipoDado getTipoEntrada()
    {
        return tipoEntrada;
    }

    public TipoDado getTipoSaida()
    {
        return tipoSaida;
    }
}
