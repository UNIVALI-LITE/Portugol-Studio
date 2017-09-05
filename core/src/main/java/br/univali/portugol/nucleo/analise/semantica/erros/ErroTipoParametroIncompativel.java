package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.analise.semantica.AnalisadorSemantico;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 * Erro gerado pelo analisador semântico quando é realizada uma chamada de
 * função passando um parâmetro com um tipo de dado diferento do esperado pela
 * função.
 * <p>
 * Exemplo:  <code><pre>
 *
 *      programa
 *      {
 *           funcao exemploTipoParametroIncompativel()
 *           {
 *                 /*
 *                  * Esta chamada de função gera erro, pois o segundo parâmetro da função espera um valor do tipo
 *                  * inteiro, no entanto, está sendo passado uma expressão do tipo cadeia.
 *                  *&#47;
 *
 *                 inteiro valor = soma(5, "Oooooppppsss!")
 *
 *                 escreva(valor)
 *           }
 *
 *           funcao inteiro soma(nteiro a, inteiro b)
 *           {
 *                retorne a + b
 *           }
 *      }
 *
 * </pre></code>
 *
 * @author Luiz Fernando Noschang
 * @since 1.0
 *
 * @see AnalisadorSemantico
 */
public class ErroTipoParametroIncompativel extends ErroSemantico
{
    private TipoDado tipoEsperado;
    private TipoDado tipoPassado;

    private NoExpressao parametroPassado;
    private final String nomeFuncao;
    private final String nomeParametro;

    /**
     * @param nomeFuncao o nome da função que foi chamada.
     * @param nomeParametro o nome do parâmetro da função.
     * @param tipoEsperado o tipo de dado esperado pelo parâmetro da função.
     * @param tipoPassado o tipo de dado da expressão que foi passada por
     * parâmetro.
     * @param parametroPassado a expressão que foi passada por parâmetro.
     * @param quantificadorEsperado o {@link Quantificador} do parâmetro
     * esperado.
     * @param quantificadorPassado o {@link Quantificador} do parâmetro passado.
     */
    public ErroTipoParametroIncompativel(String nomeFuncao, String nomeParametro, NoExpressao parametroPassado, TipoDado tipoEsperado, TipoDado tipoPassado)
    {
        super(parametroPassado.getTrechoCodigoFonte(), "ErroSemantico.ErroTipoParametroIncompativel");
        this.tipoEsperado = tipoEsperado;
        this.tipoPassado = tipoPassado;
        this.nomeFuncao = nomeFuncao;
        this.nomeParametro = nomeParametro;
        this.parametroPassado = parametroPassado;
    }

    /**
     * Obtém a expressão que foi passada por parâmetro.
     *
     * @return a expressão que foi passada por parâmetro.
     * @since 1.0
     */
    public NoExpressao getParametroPassado()
    {
        return parametroPassado;
    }

    /**
     * Obtém o tipo de dado esperado pelo parâmetro da função.
     *
     * @return o tipo de dado esperado pelo parâmetro da função.
     * @since 1.0
     */
    public TipoDado getTipoEsperado()
    {
        return tipoEsperado;
    }

    /**
     * Obtém o tipo de dado da expressão que foi passada por parâmetro.
     *
     * @return o tipo de dado da expressão que foi passada por parâmetro.
     * @since 1.0
     */
    public TipoDado getTipoPassado()
    {
        return tipoPassado;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorString = new StringBuilder();

        if (AnalisadorSemantico.FUNCAO_ESCREVA.equals(nomeFuncao))
        {
            construtorString.append("Você não pode passar uma função com retorno vazio para a função \"escreva\"");
        }
        else
        {
            construtorString.append("Tipos incompatíveis! O parâmetro \"");
            construtorString.append(nomeParametro);
            construtorString.append("\" da função \"");
            construtorString.append(nomeFuncao);
            construtorString.append("\" espera uma expressão do tipo \"");
            construtorString.append(tipoEsperado);
            construtorString.append("\", mas foi passada uma expressão do tipo \"");
            construtorString.append(tipoPassado);
            construtorString.append("\"");
        }

        return construtorString.toString();
    }
}
