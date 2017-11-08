package br.univali.portugol.nucleo.simbolos;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.Quantificador;
import java.util.List;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;

/**
 * Representa uma função alocada em memória durante a execução de um programa.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 */
public final class Funcao extends Simbolo
{
    private List<NoBloco> blocos;
    private Quantificador quantificador;
    private List<NoDeclaracaoParametro> parametros;

    /**
     * 
     * @param nome              o nome desta função.
     * @param tipoDado          o tipo de dado do valor de retorno desta função.
     * @param quantificador     o quantificador do valor de retorno desta função.
     * @param parametros        a lista de parâmetros esperados por esta função.
     * @param blocos            a lista de blocos a serem executados por esta função.
     * @since 1.0
     */
    public Funcao(String nome, TipoDado tipoDado, Quantificador quantificador, List<NoDeclaracaoParametro> parametros, NoDeclaracaoFuncao declaracaoOrigem)
    {
        super(nome, tipoDado, declaracaoOrigem);
        
        if (declaracaoOrigem != null)
        {
            this.blocos = ((NoDeclaracaoFuncao) declaracaoOrigem).getBlocos();
        }
        
        this.parametros = parametros;
        this.quantificador = quantificador;
    }

    @Override
    public boolean constante()
    {
        return true; 
    }
    

    /**
     * Obtém a lista de blocas desta função. Estes blocos serão executados toda vez que uma 
     * chamada a esta função for feita.
     * 
     * @return     a lista de blocos a serem executados por esta função.
     * @since 1.0
     */
    public List<NoBloco> getBlocos()
    {
        return blocos;
    }

    /**
     * Obtém o quantificador do valor de retorno desta função.
     * 
     * @return     o quantificador do valor de retorno desta função.
     * @since 1.0
     */
    public Quantificador getQuantificador()
    {
        return quantificador;
    }

    /**
     * Obtém a lista de parâmetros esperados por esta função.
     * 
     * @return     a lista de parâmetros esperados por esta função.
     * @since 1.0
     */
    public List<NoDeclaracaoParametro> getParametros()
    {
        return parametros;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Funcao copiar(String novoNome)
    {
        return null;
    }
}
