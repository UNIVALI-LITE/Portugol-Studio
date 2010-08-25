package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoParametro;
import br.univali.portugol.nucleo.simbolos.Funcao;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class ErroParametroRedeclarado extends Erro
{
    private Funcao funcao;
    private NoParametro parametro;

    private static final long serialVersionUID = 8170600234316233741L;

    public ErroParametroRedeclarado(File arquivo, NoParametro parametro, Funcao funcao)
    {
        super
        (
            arquivo,
            parametro.getTokenNome().getLinha(),
            parametro.getTokenNome().getColuna()
        );

        this.funcao = funcao;
        this.parametro = parametro;
    }

    public Funcao getFuncao()
    {
        return funcao;
    }

    public NoParametro getParametro()
    {
        return parametro;
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
