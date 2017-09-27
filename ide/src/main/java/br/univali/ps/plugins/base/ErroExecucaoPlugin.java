package br.univali.ps.plugins.base;

import br.univali.portugol.nucleo.mensagens.Erro;

/**
 *
 * @author Ailton Cardoso Jr
 */
public class ErroExecucaoPlugin extends Erro {

    private final String mensagem;

    public ErroExecucaoPlugin(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    protected String construirMensagem() {
        return mensagem;
    }
}
