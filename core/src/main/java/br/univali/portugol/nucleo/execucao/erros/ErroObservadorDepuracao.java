package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroObservadorDepuracao extends ErroExecucao
{
     @Override
    public int getLinha()
    {
        return 1;
    }
                
    @Override
    protected String construirMensagem()
    {
        return "A depuração depende de um observador";
    }
}
