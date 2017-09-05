package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;

/**
 * Esta enumeração define todos os tipos de dado existentes no Portugol.
 * <p>
 * Esta enumeração é utilizada em diversos nós da ASA para definir o tipo de dado com
 * o qual se está trabalhando. Na declaração de variável, indica o tipo de dado da variável,
 * na declaração de função, indica o tipo de dado retornado pela função, em uma declaração de
 * parâmetro, indica o tipo de dado esperado pelo parâmetro, e assim por diante. 
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public enum TipoDado
{
    /**
     * Representa os valores inteiros no Portugol. Os valores inteiros são todos os números naturais positivos
     * ou negativos que não possuem vírgula, ou seja, não fracionários.
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao exemplorInteiro()
     *      {
     *           inteiro numA = 56
     *           inteiro numB = -12
     *           inteiro numC = 0
     *      }
     * 
     * </pre></code>
     *
     * @since 1.0
     */
    INTEIRO("inteiro", "inteiro", 0, Integer.TYPE),
    
    /**
     * Representa os valores reais no Portugol. Os valores reais são todos os números fracionários positivos
     * ou negativos.
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao exemploReal()
     *      { 
     *           real numA = 9.76
     *           real numB = -34.67
     *           real numC = 0.0
     *           real PI = 3.14159265
     *      }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    REAL("real", "real", 0.0, Double.TYPE),
    
    /**
     * Representa os valores lógicos (verdadeiro ou falso) no Portugol. 
     * <p>
     * Exemplo:
     * <code><pre>
     *      
     *      funcao exemploLogico()
     *      {
     *           logico marcianosSaoVerdes = verdadeiro
     *           logico marcianosSaoAmigos = falso
     *      }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    LOGICO("logico", "lógico", false, Boolean.TYPE),
    
    /**
     * Representa as cadeias de carcateres, ou seja, os valores textuais, no Portugol.
     * Os valores textuais são declarados entre aspas duplas "".
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao exemploCadeia()
     *      {
     *           cadeia texto1 = "Isto é uma cadeia"
     *           cadeia texto2 = "Isto é outra cadeia"
     *           cadeia alcatraz = "Também é uma cadeia! :-D"
     *      }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    CADEIA("cadeia", "cadeia", null, String.class),
    
    /**
     * Representa um único caracter no Portugol. Os caracteres são declarados entre aspas simples ''.
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao exemploCaracter()
     *      {
     *            
     *           caracter primeiraLetraDoAlfabeto = 'A'
     *           caracter ultimaLetraDoAlfabeto = 'Z'
     * 
     *           caracter vogais[] = {'A', 'E', 'I', 'O', 'U'}
     *           caracter palavra[] = {'p', 'a', 'l', 'a', 'v', 'r', 'a'}
     *      }
     * 
     * </pre></code>
     * 
     * @since 1.0
     */
    CARACTER("caracter", "caracter", '\0', Character.TYPE),
    
    /**
     * Este é um tipo de dados especial utilizado apenas nas declarações de função para indicar
     * que a função não irá retornar valores. Se o tipo de dado da função for omitido, assume-se
     * automaticamente um retorno vazio.
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao vazio naoRetornaNada()
     *      {
     *           escreva("Esta função não retorna nada!")
     *      }
     * 
     *      funcao tambemNaoRetornaNada()
     *      {
     *           escreva("Esta função também não retorna nada!")
     *      }
     * 
     * </pre></code>
     * 
     * @since 1.0
     * 
     */
    VAZIO("vazio", "vazio", null, Void.TYPE),
    
    /**
     * Este é um tipo de dados especial utilizado apenas com os parâmetros 
     * das funções de bibliotecas. Este tipo de dado indica ao {@link AnalisadorSemantico}
     * que o parâmetro da função aceita qualquer tipo de dado.
     */ 
    TODOS("todos", "todos", null, Object.class);
    
    private final String nome;
    private final String descricao;
    private final Object valorPadrao;
    private final Class tipoJava;

    /**
     * 
     * @param nome         
     * @param descricao   
     * @param valorPadrao
     */    
    private TipoDado(String nome, String descricao, Object valorPadrao, Class tipoJava)
    {
        this.nome = nome;
        this.descricao = descricao;
        this.valorPadrao = valorPadrao;
        this.tipoJava = tipoJava;
    }

    /**
     * Obtém o valor padrão para este tipo de dado. Este valor define o valor inicial das
     * variáveis que não foram inicializadas pelo usuário.
     * <p>
     * Exemplo:
     * <code><pre>
     * 
     *      funcao exemploValorPadrao()
     *      {
     *           inteiro a = 6     // O valor inicial desta variável é 6
     * 
     *           inteiro b         /* 
     *                              * O valor inicial desta variável, é o valor definido como
     *                              *  padrão para o tipo inteiro
     *                              *&#47;
     *      }
     * 
     * </pre></code>
     * 
     * @return     o valor padrão para este tipo de dado.
     * 
     * @since 1.0
     */
    public Object getValorPadrao()
    {
        return valorPadrao;
    }

    public String getNome()
    {
        return nome;
    }

    
    
    public Class getTipoJava()
    {
        return tipoJava;
    }
    
    @Override
    public String toString()
    {
        return descricao;
    }

    /**
     * Obtém um tipo de dado a partir de seu nome.
     * 
     * @param nome     o nome do tipo de dado desejado.
     * @return         o tipo de dado correspondente ao nome informado. Retorna <code>null</code>
     *                 se não for encontrado um tipo correspondente ao nome informado.
     * 
     * @since 1.0
     */
    public static TipoDado obterTipoDadoPeloNome(String nome)
    {
        TipoDado[] tiposDado = values();
 
        for (TipoDado tipoDado : tiposDado)
        {
            if (nome.equals(tipoDado.nome))
            {
                return tipoDado;
            }
        }

        return null;
    }
    
    public static TipoDado obterTipoDadoPeloTipoJava(Class tipoJava)
    {
        TipoDado[] tiposDado = values();
 
        for (TipoDado tipoDado : tiposDado)
        {
            if (tipoDado.getTipoJava().equals(tipoJava))
            {
                return tipoDado;
            }
        }

        return null;
    }
}