package br.univali.portugol.nucleo.simbolos;

import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.asa.TipoDado;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um vetor alocado em memória durante a execução de um programa.
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class Vetor extends Simbolo
{
    private List<Object> valores;
    private int ultimoIndiceModificado;
    public final static Integer TAMANHO_MAXIMO = 16777216;

    public int getUltimoIndiceModificado()
    {
        return ultimoIndiceModificado;
    }

    /**
     * Aloca um vetor em memória sem definir seu tamanho nem seus valores.
     *
     * @param nome o nome deste vetor.
     *
     * @param tipoDado o tipo de dado armazenado por este vetor.
     *
     * @param declaracaoOrigem a declaração que originou este vetor
     *
     * @since 1.0
     */
    public Vetor(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem)
    {
        super(nome, tipoDado, declaracaoOrigem);
        setInicializado(true);
    }

    /**
     * Aloca um vetor em memória definindo seu tamanho, mas sem definir seus
     * valores.
     *
     * @param nome o nome deste vetor.
     *
     * @param tipoDado o tipo de dado armazenado por este vetor.
     *
     * @param declaracaoOrigem a declaração que originou este vetor
     *
     * @param tamanho o tamamho que este vetor terá.
     *
     * @since 1.0
     */
    public Vetor(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem, int tamanho)
    {
        this(nome, tipoDado, declaracaoOrigem);
        valores = new ArrayList<>(tamanho);
        for (int i = 0; i < tamanho; i++)
        {
            valores.add(null);
        }
    }

    /**
     * Aloca um vetor em memória definindo seu tamanho e seus valores.
     *
     * @param nome o nome deste vetor.
     *
     * @param tipoDado o tipo de dado armazenado por este vetor.
     *
     * @param declaracaoOrigem a declaração que originou este vetor
     *
     * @param tamanho o tamamho que este vetor terá.
     *
     * @param valores os valores que serão armazenados neste vetor.
     *
     * @since 1.0
     */
    public Vetor(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem, int tamanho, List<Object> valores)
    {
        this(nome, tipoDado, declaracaoOrigem, tamanho);
        for (int i = 0; i < this.valores.size(); i++)
        {
            this.valores.set(i, valores.get(i));
        }
        setInicializado(true);
    }

    /**
     * Aloca um vetor em memória definindo seus valores, mas sem definir seu
     * tamanho. O tamanho do vetor será detectado automaticamente a partir da
     * lista de valores.
     *
     * @param nome o nome deste vetor.
     *
     * @param tipoDado o tipo de dado armazenado por este vetor.
     *
     * @param declaracaoOrigem a declaração que originou este vetor
     *
     * @param valores os valores que serão armazenados neste vetor.
     *
     * @since 1.0
     */
    public Vetor(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem, List<Object> valores)
    {
        this(nome, tipoDado, declaracaoOrigem);
        this.valores = new ArrayList<>(valores);
        setInicializado(true);
    }

    /**
     * Obtém o tamanho deste vetor.
     *
     * @return o tamanho deste vetor.
     *
     * @since 1.0
     */
    public int getTamanho()
    {
        return valores.size();
    }

    /**
     * Recupera um valor armazenado neste vetor no índice esecificado.
     *
     * @param indice o índice deste vetor no qual o valor se encontra.
     *
     * @return o valor armazenado neste vetor no índice especificado.
     *
     * @since 1.0
     */
    public Object getValor(int indice)
    {
        setUtilizado(true);
        return valores.get(indice);
    }

    /**
     * Armazena um valor neste vetor no índice esecificado.
     *
     * @param indice o índice deste vetor no qual o valor será armazenado.
     *
     * @param valor o valor que será armazenado neste vetor no índice
     * especificado.
     *
     * @since 1.0
     */
    public void setValor(int indice, Object valor)
    {
        ultimoIndiceModificado = indice;
        this.valores.set(indice, valor);
    }

    public void inicializarComValores(List<Object> valores)
    {
        this.valores = new ArrayList<>(valores);
    }

    /**
     * {@inheritDoc }
     *
     * @return uma cópia deste vetor
     */
    @Override
    public Vetor copiar(String novoNome)
    {
        Vetor vetor = new Vetor(novoNome, getTipoDado(), getOrigemDoSimbolo());
        vetor.valores = new ArrayList<>(this.valores.size());
        Collections.copy(vetor.valores, this.valores);

        return vetor;
    }

    public List<Object> obterValores()
    {
        if (valores != null)
        {
            return new ArrayList<>(valores);
        }

        return new ArrayList<>();
    }
}
