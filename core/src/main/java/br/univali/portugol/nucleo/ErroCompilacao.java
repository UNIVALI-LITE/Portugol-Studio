package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.mensagens.Erro;

/**
 * Erro gerado pela fachada (Facade) Portugol para permitir a captura dos erros gerados durante a análise
 * do código fonte.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class ErroCompilacao extends Erro
{
    private final ResultadoAnalise resultadoAnalise;

    /**
     * 
     * @param resultadoAnalise     o resultado da análise do código fonte.
     * @since 1.0
     */
    public ErroCompilacao(ResultadoAnalise resultadoAnalise) 
    {
        this.resultadoAnalise = resultadoAnalise;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem() 
    {
        return "Não foi possível compilar o programa, o código contém erros.";
    }

    /**
     * Obtém o resultado da análise do código fonte.
     * 
     * @return     o resultado da análise do código fonte.
     * @since 1.0
     */
    public ResultadoAnalise getResultadoAnalise() 
    {
        return resultadoAnalise;
    }
}
