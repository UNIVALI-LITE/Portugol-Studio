package br.univali.portugol.nucleo.simbolos;

import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.asa.TipoDado;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma matriz alocada em memória durante a execução de um programa.
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class Matriz extends Simbolo
{
    private int numeroLinhas;
    private int numeroColunas;
    private Object[][] valores;
    public static final Integer TAMANHO_MAXIMO = 16777216;
    private int ultimaLinhaModificada;
    private int ultimaColunaModificada;

    public int getUltimaLinhaModificada()
    {
        return ultimaLinhaModificada;
    }

    public int getUltimaColunaModificada()
    {
        return ultimaColunaModificada;
    }

    /**
     * Aloca uma matriz em memória sem definir seu tamanho nem seus valores.
     *
     * @param nome o nome desta matriz.
     *
     * @param tipoDado o tipo de dado armazenado por esta matriz.
     *
     * @param declaracaoOrigem a declaração que originou esta matriz.
     *
     * @since 1.0
     */
    public Matriz(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem)
    {
        super(nome, tipoDado, declaracaoOrigem);
        setInicializado(true);
    }

    /**
     * Aloca uma matriz em memória definindo suas dimensões, mas sem definir
     * seus valores.
     *
     * @param nome o nome desta matriz.
     *
     * @param tipoDado o tipo de dado armazenado por esta matriz.
     *
     * @param declaracaoOrigem a declaração que originou esta matriz.
     *
     * @param numeroLinhas o número de linhas que esta matriz terá.
     *
     * @param numeroColunas o número de colunas que esta matriz terá.
     *
     * @since 1.0
     */
    public Matriz(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem, int numeroLinhas, int numeroColunas)
    {
        super(nome, tipoDado, declaracaoOrigem);
        inicializarComDimensoes(numeroLinhas, numeroColunas);
        setInicializado(true);
    }

    /**
     * Aloca uma matriz em memória definindo suas dimensões e seus valores.
     *
     * @param nome o nome desta matriz.
     *
     * @param tipoDado o tipo de dado armazenado por esta matriz.
     *
     * @param declaracaoOrigem a declaração que originou esta matriz.
     *
     * @param numeroLinhas o número de linhas que esta matriz terá.
     *
     * @param numeroColunas o número de colunas que esta matriz terá.
     *
     * @param valores os valores que serão armazenados nesta matriz.
     *
     * @since 1.0
     */
    public Matriz(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem, int numeroLinhas, int numeroColunas, List<List<Object>> valores)
    {
        super(nome, tipoDado, declaracaoOrigem);
        inicializarComDimensoesValores(numeroLinhas, numeroColunas, valores);
        setInicializado(true);
    }

    /**
     * Aloca uma matriz em memória definindo apenas seus valores. As dimensões
     * da matriz serão detectadas automaticamente a partir da lista de valores.
     *
     * @param nome o nome desta matriz.
     *
     * @param tipoDado o tipo de dado armazenado por esta matriz.
     *
     * @param declaracaoOrigem a declaração que originou esta matriz.
     *
     * @param valores os valores que serão armazenados nesta matriz.
     *
     * @since 1.0
     */
    public Matriz(String nome, TipoDado tipoDado, NoDeclaracaoBase declaracaoOrigem, List<List<Object>> valores)
    {
        super(nome, tipoDado, declaracaoOrigem);
        if (valores != null)
        {
            inicializarComValores(valores);
            setInicializado(true);
        }
    }

    /**
     * Obtém o número de linhas desta matriz.
     *
     * @return o número de linhas desta matriz.
     *
     * @since 1.0
     */
    public int getNumeroLinhas()
    {
        return numeroLinhas;
    }

    /**
     * Obtém o número de colunas desta matriz.
     *
     * @return o número de colunas desta matriz.
     *
     * @since 1.0
     */
    public int getNumeroColunas()
    {
        return numeroColunas;
    }

    /**
     * Recupera um valor armazenado nesta matriz na linha e na coluna
     * especificadas.
     *
     * @param linha linha da matriz onde o valor se encontra.
     *
     * @param coluna coluna da matriz onde o valor se encontra.
     *
     * @return o valor armazenado nesta matriz na posição especificada.
     *
     * @since 1.0
     */
    public Object getValor(int linha, int coluna)
    {
        setUtilizado(true);
        return valores[linha][coluna];
    }

    /**
     * Armazena um valor nesta matriz na linha e na coluna especificadas.
     *
     * @param linha linha da matriz onde o valor se encontra.
     *
     * @param coluna coluna da matriz onde o valor se encontra.
     *
     * @param valor o valor a ser armazenado nesta matriz.
     *
     * @since 1.0
     */
    public void setValor(int linha, int coluna, Object valor)
    {
        ultimaLinhaModificada = linha;
        ultimaColunaModificada = coluna;
        this.valores[linha][coluna] = valor;
    }

    private void inicializarComDimensoes(int numeroLinhas, int numeroColunas)
    {
        Object valorPadrao = getTipoDado().getValorPadrao();

        this.numeroLinhas = numeroLinhas;
        this.numeroColunas = numeroColunas;
        valores = new Object[numeroLinhas][numeroColunas];

        for (int i = 0; i < numeroLinhas; i++)
        {
            for (int j = 0; j < numeroColunas; j++)
            {
                valores[i][j] = null;
            }
        }
    }

    private void inicializarComDimensoesValores(int numeroLinhas, int numeroColunas, List<List<Object>> valores)
    {
        this.numeroLinhas = numeroLinhas;
        this.numeroColunas = numeroColunas;
        this.valores = new Object[numeroLinhas][numeroColunas];

        for (int i = 0; i < this.numeroLinhas; i++)
        {
            for (int j = 0; j < this.numeroColunas; j++)
            {
                try
                {
                    this.valores[i][j] = obterValor(valores.get(i).get(j));
                }
                catch (Exception e)
                {
                    this.valores[i][j] = null;
                }
            }
        }
    }

    public void inicializarComValores(List<List<Object>> valores)
    {
        inicializarComDimensoesValores(obterNumeroLinhas(valores), obterNumeroColunas(valores), valores);
    }

    private int obterNumeroLinhas(List<List<Object>> valores)
    {
        return valores.size();
    }

    private int obterNumeroColunas(List<List<Object>> valores)
    {
        int maiorNumeroColunas = -1;

        for (int i = 0; i < valores.size(); i++)
        {
            if (valores.get(i) != null)
            {
                if (valores.get(i).size() > maiorNumeroColunas)
                {
                    maiorNumeroColunas = valores.get(i).size();
                }
            }
        }

        return maiorNumeroColunas;
    }

    private Object obterValor(Object value)
    {
        return (value == null) ? getTipoDado().getValorPadrao() : value;
    }

    @Override
    public Matriz copiar(String novoNome)
    {
        Matriz matriz = new Matriz(novoNome, getTipoDado(), getOrigemDoSimbolo());
        matriz.numeroLinhas = numeroLinhas;
        matriz.numeroColunas = numeroColunas;
        matriz.valores = copiarValores();

        return matriz;
    }

    public List<List<Object>> obterValores()
    {
        List<List<Object>> retorno = new ArrayList<>();

        for (int i = 0; i < numeroLinhas; i++)
        {
            List<Object> linha = new ArrayList<>();
            for (int j = 0; j < numeroColunas; j++)
            {
                linha.add(this.valores[i][j]);
            }
            retorno.add(linha);
        }
        return retorno;
    }

    private Object[][] copiarValores()
    {
        Object[][] copia = new Object[numeroLinhas][numeroColunas];

        for (int i = 0; i < numeroLinhas; i++)
        {
            copia[i] = valores[i].clone();
        }

        return copia;
    }
}
