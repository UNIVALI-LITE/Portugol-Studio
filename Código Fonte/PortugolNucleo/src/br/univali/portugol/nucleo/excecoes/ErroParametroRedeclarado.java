package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoParametro;
import br.univali.portugol.nucleo.simbolos.Funcao;

public class ErroParametroRedeclarado extends Erro
{
    private Funcao funcao;
    private NoParametro parametro;

    private static final long serialVersionUID = 8170600234316233741L;

    public ErroParametroRedeclarado(NoParametro parametro, Funcao funcao)
    {
        this.funcao = funcao;
        this.parametro = parametro;

        setLinha(parametro.getTokenNome().getLinha());
        setColuna(parametro.getTokenNome().getColuna());
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("O parâmetro \"");
        builder.append(parametro.getNome());
        builder.append("\" já foi declarado na função \"");
        builder.append(funcao.getNome());
        builder.append("\".");

        return builder.toString();
    }
}
