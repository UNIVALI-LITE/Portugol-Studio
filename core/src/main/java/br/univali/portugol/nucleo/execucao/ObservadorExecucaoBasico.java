package br.univali.portugol.nucleo.execucao;

import br.univali.portugol.nucleo.programa.Programa;

/**
 *
 * @author Luiz Fernando Noschang
 */
public abstract class ObservadorExecucaoBasico implements ObservadorExecucao
{
    @Override
    public void execucaoIniciada(Programa programa)
    {
    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
    {
    }

    @Override
    public void execucaoPausada()
    {
    }

    @Override
    public void execucaoResumida()
    {
    }
    
    @Override
    public void highlightLinha(int linha)
    {
    }

    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho)
    {
    }

}
