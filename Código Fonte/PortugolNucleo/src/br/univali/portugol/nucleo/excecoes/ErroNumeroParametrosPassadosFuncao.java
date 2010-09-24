package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.simbolos.Funcao;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class ErroNumeroParametrosPassadosFuncao extends Erro
{
    private int numeroParametrosPassados;
    private int numeroParametrosEsperados;

    private Funcao funcao;
    private NoChamadaFuncao chamadaFuncao;

    public ErroNumeroParametrosPassadosFuncao(File arquivo, int numeroParametrosPassados, int numeroParametrosEsperados, Funcao funcao, NoChamadaFuncao chamadaFuncao)
    {
        super
        (
            arquivo,
            chamadaFuncao.getTokenNome().getLinha(),
            chamadaFuncao.getTokenNome().getColuna()
        );

        this.numeroParametrosPassados = numeroParametrosPassados;
        this.numeroParametrosEsperados = numeroParametrosEsperados;

        this.funcao = funcao;
        this.chamadaFuncao = chamadaFuncao;
    }

    public int getNumeroParametrosEsperados()
    {
        return numeroParametrosEsperados;
    }

    public int getNumeroParametrosPassados()
    {
        return numeroParametrosPassados;
    }

    public NoChamadaFuncao getChamadaFuncao()
    {
        return chamadaFuncao;
    }

    public Funcao getFuncao()
    {
        return funcao;
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
