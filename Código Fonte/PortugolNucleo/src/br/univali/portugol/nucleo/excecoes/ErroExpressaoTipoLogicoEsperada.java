package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoSe;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 27/08/2010
 * @version 1.0.0
 *
 */

public class ErroExpressaoTipoLogicoEsperada extends Erro
{
    private NoBloco bloco;
    private NoExpressao expressao;


    public ErroExpressaoTipoLogicoEsperada(File arquivo, NoBloco bloco, NoExpressao expressao)
    {
        super
        (
            arquivo,
            expressao.getToken().getLinha(),
            expressao.getToken().getColuna()
        );

        this.bloco = bloco;
        this.expressao = expressao;
    }

    public NoExpressao getExpressao()
    {
        return expressao;
    }

    public NoBloco getBloco()
    {
        return bloco;
    }

    @Override
    protected String construirMensagem()
    {
        if (bloco instanceof NoSe) return construirMensagemBlocoSe();
        if (bloco instanceof NoEnquanto) return construirMensagemBlocoEnquanto();
        if (bloco instanceof NoFacaEnquanto) return construirMensagemFacaEnquanto();

        return null;
    }

    private String construirMensagemBlocoSe()
    {
        return "Tipos incompatíveis! A expressão utilizada com o desvio condicional \"se\" deve ser do tipo lógico!";
    }

    private String construirMensagemBlocoEnquanto()
    {
        return "Tipos incompatíveis! A expressão utilizada com o laço \"enquanto\" dever ser do tipo lógico!";
    }

    private String construirMensagemFacaEnquanto()
    {
        return "Tipos incompatíveis! A expressão utilizada com o laço \"faca-enquanto\" deve ser do tipo lógico!";
    }


}
