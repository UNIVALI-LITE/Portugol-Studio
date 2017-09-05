package br.univali.portugol.nucleo.analise;

import br.univali.portugol.nucleo.analise.semantica.ObservadorAnaliseSemantica;
import br.univali.portugol.nucleo.analise.sintatica.ObservadorAnaliseSintatica;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Observa a análise de um código fonte e adiciona todos os erros encontrados durante esta análise a um objeto
 * {@link ResultadoAnalise} para serem utilizados mais tarde.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorAlgoritmo
 */
public final class ObservadorAnaliseAlgoritmo implements ObservadorAnaliseSintatica, ObservadorAnaliseSemantica
{
    private ResultadoAnalise resultadoAnalise;

    public ObservadorAnaliseAlgoritmo()
    {
        resultadoAnalise = new ResultadoAnalise();
    }    

    /**
     * {@inheritDoc }
     */
    @Override
    public void tratarErroSintatico(ErroSintatico erroSintatico)
    {
        resultadoAnalise.adicionarErro(erroSintatico);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void tratarErroSemantico(ErroSemantico erroSemantico)
    {
        resultadoAnalise.adicionarErro(erroSemantico);
    }

    /**
     * Obtém o resultado da análise realizada no código fonte.
     * 
     * @return     o resultado da análise realizada no código fonte.
     * @since 1.0
     */    
    public ResultadoAnalise getResultadoAnalise()
    {
        return resultadoAnalise;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void tratarAviso(AvisoAnalise aviso)
    {
        resultadoAnalise.adicionarAviso(aviso);
    }
}
