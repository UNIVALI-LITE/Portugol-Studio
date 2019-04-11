package br.univali.portugol.nucleo.simbolos;

import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.asa.TipoDado;

/**
 * Representa uma variável alocada em memória durante a execução de um programa.
 *
 * @author Luiz Fernando Noschang
 * @since 1.0
 */
public final class Variavel extends Simbolo
{
    private Object valor;

    /**
     * Aloca uma variável em memória sem definir seu valor.
     *
     * @param nome o nome desta variável.
     *
     * @param tipoDado o tipo de dado armazenado por esta variável.
     *
     * @param declaracaoOrigem o nó de declaração que originou a variável
     *
     * @since 1.0
     */
    public Variavel(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem)
    {
        super(nome, tipoDado, declaracaoOrigem);
    }

    /**
     * Obtém o valor armazenado nesta variável.
     *
     * @return o valor armazenado nesta variável.
     *
     * @since 1.0
     */
    public Object getValor()
    {
        setUtilizado(true);
        return valor;
    }

    /**
     * Armazena um valor nesta variável.
     *
     * @param valor o valor que será armazenado nesta variável
     *
     * @since 1.0
     */
    public void setValor(Object valor)
    {
        if ((valor instanceof Double) && (tipoDado == TipoDado.INTEIRO))
        {
            double val = (Double) valor;
            valor = (int) val;
        }

        else if ((valor instanceof Integer) && (tipoDado == TipoDado.REAL))
        {
            valor = ((Integer) valor).doubleValue();
        }

        setInicializado(true);
        this.valor = valor;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Variavel copiar(String novoNome)
    {
        Variavel variavel = new Variavel(novoNome, getTipoDado(), getOrigemDoSimbolo());
        variavel.setInicializado(true);
        variavel.valor = valor;

        return variavel;
    }
    
}
