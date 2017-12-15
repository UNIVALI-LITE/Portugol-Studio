package br.univali.portugol.nucleo.bibliotecas.base;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoConstante;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import java.util.List;

/**
 * Classe base para a construção de bibliotecas do Portugol. Todas as
 * bibliotecas escritas para o Portugol deverão estender esta classe e seguir as
 * seguintes regras de implementação:
 *
 * </p>
 * <ul>
 * <li><p>
 * A biblioteca deve estender a classe {@link Biblioteca}</p><br/></li>
 * <li>
 * <p>
 * A classe da biblioteca deve ser <strong>pública</strong>,
 * <strong>final</strong>
 * e não pode ser <strong>estática</strong>, <strong>anônima</strong>,
 * <strong>sintética</strong>,
 * <strong>membro</strong> ou <strong>local</strong>
 * </p>
 * <br/>
 * </li>
 * <li>
 * <p>
 * A biblioteca deve estar anotada com as anotações
 * {@link DocumentacaoBiblioteca} e {@link PropriedadesBiblioteca}
 * </p>
 * <br/>
 * </li>
 * <li><p>
 * A biblioteca deve exportar pelo menos uma função ou constante</p><br/></li>
 * <li>
 * <p>
 * Para que um método da classe seja exportado como uma função da biblioteca, o
 * método deve ser <strong>público</strong>, <strong> não estático</strong>,
 * estar anotado com a anotação {@link DocumentacaoFuncao} e jogar um
 * {@link ErroExecucao}
 * </p>
 * <br/>
 * </li>
 * <li>
 * <p>
 * A biblioteca não pode ter sobrecarga dos métodos públicos exportados como
 * funções. Já os métodos privados poderão ser sobrecarregados a qualquer
 * momento
 * </p>
 * <br/>
 * </li>
 * <li>
 * <p>
 * Para que um atributo da classe seja exportado como uma constante da
 * biblioteca, o atributo deve ser <strong>público</strong>,
 * <strong>final</strong>, <strong>estático</strong>, ter o nome todo em letras
 * maiúsculas e estar anotado com a anotação {@link DocumentacaoConstante}
 * </p>
 * <br/>
 * </li>
 * <li>
 * <p>
 * Os atributos da classe exportados como constantes da biblioteca, não
 * podederão ser vetor nem matriz, somente valores
 * </p>
 * <br/>
 * </li>
 *
 * <li>
 * <p>
 * O tipo de retorno e parâmetros dos métodos, e o tipo dos atributos
 * exportados, deverá ser compatível com os tipos de dados do Portugol, a saber:
 *
 * <br/>
 * <ul>
 * <li>{@link Integer} --&gt; {@link TipoDado#INTEIRO}<br/></li>
 * <li>{@link Double} --&gt; {@link TipoDado#REAL} <br/></li>
 * <li>{@link Boolean} --&gt; {@link TipoDado#LOGICO}<br/></li>
 * <li>{@link String} --&gt; {@link TipoDado#CADEIA}<br/></li>
 * <li>{@link Character} --&gt; {@link TipoDado#CARACTER}<br/></li>
 * <li><strong>void</strong> --&gt; {@link TipoDado#VAZIO}<br/></li>
 * </ul>
 * <br/>
 *
 * Com exceção do tipo <strong>void</strong>, não poderão ser utilizados os
 * tipos primitivos correspondentes, a saber: <strong>int</strong>,
 * <strong>double</strong>, <strong>boolean</strong> e <strong>char</strong>.
 * Além disso, o tipo <strong>void</strong> não pode ser utilizado nos
 * parâmetros dos métodos</strong>
 * </p>
 * <br/>
 * </li>
 * </ul>
 *
 * @author Luiz Fernando Noschang
 */
public abstract class Biblioteca
{
    private TipoBiblioteca tipo = getClass().getAnnotation(PropriedadesBiblioteca.class).tipo();
    private final String nome;

    public Biblioteca()
    {
        nome = getClass().getSimpleName();
    }

    public final String getNome()
    {
        return nome;
    }

    public final TipoBiblioteca getTipo()
    {
        return tipo;
    }

    void setTipo(TipoBiblioteca tipo)
    {
        this.tipo = tipo;
    }

    /**
     * Este método será chamado automaticamente para inicializar as bibliotecas
     * do tipo {@link TipoBiblioteca#RESERVADA}. O método será chamado no início
     * da execução de cada {@link Programa}
     *
     * @param programa o programa no qual a biblioteca está registrada e
     * executando
     *
     * @param bibliotecasReservadas a lista das bibliotecas reservadas que foram
     * incluídas no programa antes da inlusão desta biblioteca. Para obter as
     * demais bibliotecas, o método {@link Biblioteca#bibliotecaRegistrada(Biblioteca)
     * }
     * deve ser sobrescrito.
     *
     * @throws ErroExecucaoBiblioteca
     * @throws java.lang.InterruptedException
     *
     * @see TipoBiblioteca
     */
    public void inicializar(Programa programa, List<Biblioteca> bibliotecasReservadas) throws ErroExecucaoBiblioteca, InterruptedException
    {

    }

    /**
     * Este método será chamado automaticamente para finalizar a biblioteca no
     * término da execução de cada {@link Programa}
     *
     * <p>
     * Para as bibliotecas do tipo {@link TipoBiblioteca#COMPARTILHADA}, quando
     * houver um código de finalização, a biblioteca fica responsável por
     * garantir que o código de finalização seja executado apenas uma vez
     * </p>
     *
     * @throws ErroExecucaoBiblioteca
     * @throws java.lang.InterruptedException
     *
     * @see GerenciadorBibliotecas
     *
     */
    public void finalizar() throws ErroExecucaoBiblioteca, InterruptedException
    {

    }

    /**
     * Método chamado automaticamente pelo {@link GerenciadorBibliotecas} toda
     * vez que uma nova biblioteca do tipo {@link TipoBiblioteca#RESERVADA} é
     * declarada no programa
     *
     * @param biblioteca
     *
     * @throws ErroExecucaoBiblioteca
     * @throws java.lang.InterruptedException
     */
    protected void bibliotecaRegistrada(Biblioteca biblioteca) throws ErroExecucaoBiblioteca, InterruptedException
    {

    }
}
