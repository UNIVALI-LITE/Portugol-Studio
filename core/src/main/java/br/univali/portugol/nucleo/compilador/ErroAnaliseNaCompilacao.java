package br.univali.portugol.nucleo.compilador;

import br.univali.portugol.nucleo.mensagens.ErroAnalise;

class ErroAnaliseNaCompilacao extends ErroAnalise {
    private final String mensagem;

    public ErroAnaliseNaCompilacao(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    protected String construirMensagem() {
        return mensagem;
    }

}
