package br.univali.ps.plugins.base;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.Erro;

/**
 *
 * @author Ailton Cardoso Jr
 */
public class ErroExecucaoPlugin extends Erro {

    private final String mensagem;
    private TrechoCodigoFonte trechoCodigoFonte;

    public ErroExecucaoPlugin(String mensagem, TrechoCodigoFonte trechoCodigoFonte) {
        this.mensagem = mensagem;
        this.trechoCodigoFonte = trechoCodigoFonte;
    }

    @Override
    protected String construirMensagem() {
        return mensagem;
    }

    public TrechoCodigoFonte getTrechoCodigoFonte() {
        return trechoCodigoFonte;
    }
}
