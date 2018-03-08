package br.univali.portugol.nucleo.analise.semantica.avisos;

import br.univali.portugol.nucleo.asa.*;

class ConstrutorMensagem extends VisitanteASABasico {
    private AvisoValorExpressaoSeraConvertido aviso;

    public ConstrutorMensagem(AvisoValorExpressaoSeraConvertido aviso) {
        this.aviso = aviso;

    }

    public String getMensagem() {
        try {
            if (aviso.getNoRetorne() == null) {
                return (String) aviso.getExpressao().aceitar(this);
            }

            return (String) aviso.getNoRetorne().aceitar(this);
        } catch (ExcecaoVisitaASA e) {
            return e.getMessage();
        }
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA {
        String nomeFuncao = (String) aviso.getDetalhe(0);

        if (isTipoExpressaoRealAndTipoConversaoInteiro()) {
            return String.format("O valor da expressão retornada na função \"%s\" será truncado", nomeFuncao);
        }

        return String.format(
                "O valor da expressão retornada na função \"%s\" será automaticamente convertido de \"%s\" para \"%s\"",
                nomeFuncao, aviso.getTipoExpressao(),
                aviso.getTipoConversao());
    }

    private boolean isTipoExpressaoRealAndTipoConversaoInteiro() {
        return
                aviso.getTipoExpressao() == TipoDado.REAL &&
                aviso.getTipoConversao() == TipoDado.INTEIRO;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA {
        if (noOperacaoAtribuicao.getOperandoDireito() instanceof NoVetor) {
            if (isTipoExpressaoRealAndTipoConversaoInteiro()) {
                return "O valores do vetor à direita da atribuição serão truncados";
            }

            return String.format(
                    "O valores do vetor à direita da atribuição serão automaticamente convertidos de \"%s\" para \"%s\"",
                    aviso.getTipoExpressao(),
                    aviso.getTipoConversao());
        } else if (noOperacaoAtribuicao.getOperandoDireito() instanceof NoMatriz) {
            if (isTipoExpressaoRealAndTipoConversaoInteiro()) {
                return "O valores da matriz à direita da atribuição serão truncados";
            }

            return String.format(
                    "O valores da matriz à direita da atribuição serão automaticamente convertidos de \"%s\" para \"%s\"",
                    aviso.getTipoExpressao(),
                    aviso.getTipoConversao());
        } else {
            if (isTipoExpressaoRealAndTipoConversaoInteiro()) {
                return "O valor da expressão á direita da atribuição será truncado";
            }

            return String.format(
                    "O valor da expressão á direita da atribuição será automaticamente convertido de \"%s\" para \"%s\"",
                    aviso.getTipoExpressao(),
                    aviso.getTipoConversao());
        }
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA {
        String nomeFuncao = chamadaFuncao.getNome();
        String nomeParametro = (String) aviso.getDetalhe(0);

        if (isTipoExpressaoRealAndTipoConversaoInteiro()) {
            return String.format(
                    "O valor da expressão passada para o parâmetro \"%s\" da função \"%s\" será truncado",
                    nomeParametro, nomeFuncao);
        }

        return String.format(
                "O valor da expressão passada para o parâmetro \"%s\" da função \"%s\" será automaticamente convertido de \"%s\" para \"%s\"",
                nomeParametro, nomeFuncao,
                aviso.getTipoExpressao(),
                aviso.getTipoConversao());
    }
}
