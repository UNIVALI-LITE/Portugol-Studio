package br.univali.portugol.nucleo.execucao;

import br.univali.portugol.nucleo.execucao.erros.ErroExecucaoNaoTratado;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TradutorErrosExecucao
{
    //private final Map<Class, 
    public ErroExecucao traduzir(Exception excecao)
    {
        return new ErroExecucaoNaoTratado(excecao);
    }
}
