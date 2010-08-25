package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.simbolos.Funcao;

/**
 *
 * @author Luiz Fernando Noschang
 */

public class ErroNumeroParametrosPassadosFuncao extends Erro
{
    private int numeroParametrosPassados;
    private int numeroParametrosEsperados;

    private Funcao funcao;
    private NoChamadaFuncao chamadaFuncao;

    public ErroNumeroParametrosPassadosFuncao(int numeroParametrosPassados, int numeroParametrosEsperados, Funcao funcao, NoChamadaFuncao chamadaFuncao)
    {
        this.numeroParametrosPassados = numeroParametrosPassados;
        this.numeroParametrosEsperados = numeroParametrosEsperados;

        this.funcao = funcao;
        this.chamadaFuncao = chamadaFuncao;

        setLinha(chamadaFuncao.getTokenNome().getLinha());
        setColuna(chamadaFuncao.getTokenNome().getColuna());
    }

    public int getNumeroParametrosEsperados()
    {
        return numeroParametrosEsperados;
    }

    public int getNumeroParametrosPassados()
    {
        return numeroParametrosPassados;
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("A função \"");
        construtorString.append(funcao.getNome());
        construtorString.append("\" esperarava ");

        if (numeroParametrosEsperados < numeroParametrosPassados)
            construtorString.append("apenas ");

        construtorString.append(numeroParametrosEsperados);

        if (numeroParametrosEsperados == 1) construtorString.append(" parâmetro");
        else construtorString.append(" parâmetros");

        if (numeroParametrosPassados == 1) construtorString.append(" mas foi passado ");
        else construtorString.append(" mas foram passados ");

        if (numeroParametrosEsperados > numeroParametrosPassados)
            construtorString.append("apenas ");

        construtorString.append(numeroParametrosPassados);

        if (numeroParametrosPassados == 1) construtorString.append(" parâmetro.");
        else construtorString.append(" parâmetros.");

        return construtorString.toString();
    }
}
