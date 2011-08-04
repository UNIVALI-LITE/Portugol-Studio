package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoDecremento;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoIncremento;
import br.univali.portugol.nucleo.asa.NoOperacao;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 29/07/2010
 * @version 1.0.0
 */

public class ErroOperacaoComExpressaoConstante extends Erro
{
    private NoBloco operacao;
    private NoExpressao expressao;

    public ErroOperacaoComExpressaoConstante(File arquivo, NoBloco operacao, NoExpressao expressao)
    {
        super
        (
            arquivo,
            expressao.getToken().getLinha(),
            expressao.getToken().getColuna()
        );

        this.operacao = operacao;
        this.expressao = expressao;
    }

    public NoBloco getOperacao()
    {
        return operacao;
    }

    public NoExpressao getExpressao()
    {
        return expressao;
    }

    @Override
    protected String construirMensagem()
    {
        if (operacao instanceof NoIncremento) return construirMensagemIncremento();
        if (operacao instanceof NoDecremento) return construirMensagemDecremento();
        if (operacao instanceof NoOperacao) return construirMensagemAtribuicao();

        return null;
    }

    private String construirMensagemIncremento()
    {
        return "Não é possível incrementar a expressão pois ela é constante!";
    }

    private String construirMensagemDecremento()
    {
        return "Não é possível decrementar a expressão pois ela é constante!";
    }

    private String construirMensagemAtribuicao()
    {
        return "Não é possível atribuir um valor à expressão pois ela é constante!";
    }
}
