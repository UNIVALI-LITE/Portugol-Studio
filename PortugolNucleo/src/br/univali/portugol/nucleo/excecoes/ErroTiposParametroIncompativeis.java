package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.TipoDado;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoParametro;
import br.univali.portugol.nucleo.simbolos.Funcao;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class ErroTiposParametroIncompativeis extends Erro
{
    private TipoDado tipoDadoParametroEsperado;
    private TipoDado tipoDadoParametroPassado;

    private NoParametro parametroEsperado;
    private NoExpressao parametroPassado;
    private Funcao funcao;

    public ErroTiposParametroIncompativeis(TipoDado tipoDadoParametroEsperado, TipoDado tipoDadoParametroPassado, NoParametro parametroEsperado, NoExpressao parametroPassado, Funcao funcao)
    {
        this.tipoDadoParametroEsperado = tipoDadoParametroEsperado;
        this.tipoDadoParametroPassado = tipoDadoParametroPassado;
        this.parametroEsperado = parametroEsperado;
        this.parametroPassado= parametroPassado;
        this.funcao = funcao;

        setLinha(parametroPassado.getToken().getLinha());
        setColuna(parametroPassado.getToken().getColuna());
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("Tipos incompatíveis! O parâmetro \"");
        construtorString.append(parametroEsperado.getNome());
        construtorString.append("\" da função \"");
        construtorString.append(funcao.getNome());
        construtorString.append("\" esperava uma expressão do tipo \"");
        construtorString.append(tipoDadoParametroEsperado);
        construtorString.append("\" mas foi passada uma expressão do tipo \"");
        construtorString.append(tipoDadoParametroPassado);
        construtorString.append("\".");

        return construtorString.toString();
    }

}
