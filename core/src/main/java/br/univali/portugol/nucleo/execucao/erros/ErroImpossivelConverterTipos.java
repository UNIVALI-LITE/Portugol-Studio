package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class ErroImpossivelConverterTipos extends ErroExecucao
{
    private TipoDado tipoEntrada;
    private TipoDado tipoSaida;

    public ErroImpossivelConverterTipos(TipoDado tipoEntrada, TipoDado tipoSaida)
    {
        this.tipoEntrada = tipoEntrada;
        this.tipoSaida = tipoSaida;
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("Não foi possível converter o valor de \"%s\" para \"%s\"", tipoEntrada, tipoSaida);
    }    
}
