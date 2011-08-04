package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoReferencia;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class ErroSimboloNaoDeclarado extends Erro
{
    private static final long serialVersionUID = -7709264185740100774L;

    private NoReferencia referencia;

    public ErroSimboloNaoDeclarado(File arquivo, NoReferencia referencia)
    {
        super
        (
            arquivo,
            referencia.getTokenNome().getLinha(),
            referencia.getTokenNome().getColuna()
        );

        this.referencia = referencia;
    }

    public NoReferencia getReferencia()
    {
        return referencia;
    }

    @Override
    protected String construirMensagem()
    {
        if (referencia instanceof NoReferenciaVetor) return construirMensagemVetor();
        if (referencia instanceof NoReferenciaMatriz) return construirMensagemMatriz();
        if (referencia instanceof NoReferenciaVariavel) return construirMensagemVariavel();
        if (referencia instanceof NoChamadaFuncao) return construirMensagemFuncao();

        return null;
    }

    private String construirMensagemVetor()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("O vetor \"");
        construtorString.append(referencia.getNome());
        construtorString.append("\" não foi declarado neste escopo.");

        return construtorString.toString();
    }

    private String construirMensagemMatriz()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("A matriz \"");
        construtorString.append(referencia.getNome());
        construtorString.append("\" não foi declarada neste escopo.");

        return construtorString.toString();
    }

    private String construirMensagemVariavel()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("A variável \"");
        construtorString.append(referencia.getNome());
        construtorString.append("\" não foi declarada neste escopo.");

        return construtorString.toString();
    }

    private String construirMensagemFuncao()
    {
        StringBuilder construtorString = new StringBuilder();

        construtorString.append("A função \"");
        construtorString.append(referencia.getNome());
        construtorString.append("\" não foi declarada neste escopo.");

        return construtorString.toString();
    }
}
