package br.univali.portugol.nucleo.simbolos;

import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;

/**
 * Representa um ponteiro para outro símbolo durante a execução de um programa.
 *
 * @author Luiz Fernando Noschang
 * @since 1.0
 */
public final class Ponteiro extends Simbolo
{
    protected Simbolo simbolo;

    /**
     *
     * @param nome o nome deste ponteiro.
     *
     * @param parametroOrigem o parâmetro que originou este ponteiro
     *
     * @param simbolo o símbolo que está sendo apontado por este ponteiro.
     */
    public Ponteiro(String nome, NoDeclaracaoParametro parametroOrigem, Simbolo simbolo)
    {
        super(nome, null, null);
        setOrigemDoSimbolo(parametroOrigem);
        setSimbolo(simbolo);
    }

    public void setSimbolo(Simbolo simbolo)
    {
        if (simbolo != null)
        {
            this.simbolo = simbolo;
            this.tipoDado = simbolo.getTipoDado();
        }
        else
        {
            this.simbolo = null;
            this.tipoDado = null;
        }
    }

    /**
     * Obtém o símbolo apontado por este ponteiro.
     *
     * @return o símbolo apontado por este ponteiro.
     *
     * @since 1.0
     */
    public Simbolo getSimboloApontado()
    {
        if (simbolo == null)
        {
            throw new IllegalStateException("É necessário adicionar um simbolo a ser apontado");
        }
        setUtilizado(true);
        return simbolo;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Ponteiro copiar(String novoNome)
    {
        return new Ponteiro(novoNome, (NoDeclaracaoParametro) getOrigemDoSimbolo(), simbolo);
    }
}
