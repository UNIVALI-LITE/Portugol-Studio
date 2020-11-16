package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.programa.Programa;

import java.util.List;

final class BuscadorDeSimbolo implements VisitanteASA {
    private ASAPrograma asa;

    private int linha;
    private int coluna;
    private NoDeclaracaoBase declaracaoSimbolo = null;

    public void buscarSimbolo(Programa programa, int linha, int coluna) throws ErroAoRenomearSimbolo {
        try {
            this.linha = linha;
            this.coluna = coluna - 1;
            this.asa = programa.getArvoreSintaticaAbstrata();

            programa.getArvoreSintaticaAbstrata().aceitar(this);
        } catch (ExcecaoVisitaASA ex) {
            if (ex.getCause() instanceof ErroAoRenomearSimbolo) {
                throw (ErroAoRenomearSimbolo) ex.getCause();
            }

            throw new ErroAoRenomearSimbolo(ex.getMessage(), ErroAoRenomearSimbolo.Tipo.ERRO_USUARIO);
        }
    }

    public boolean simboloEncontrado() {
        return declaracaoSimbolo != null;
    }

    public NoDeclaracaoBase getDeclaracaoSimbolo() {
        return declaracaoSimbolo;
    }

    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA {
        for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais()) {
            if (simboloEncontrado()) {
                break;
            }

            declaracao.aceitar(this);
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = declaracaoFuncao.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            declaracaoSimbolo = declaracaoFuncao;

            return null;
        }

        if (declaracaoFuncao.getParametros() != null) {
            for (NoDeclaracaoParametro parametro : declaracaoFuncao.getParametros()) {
                parametro.aceitar(this);

                if (simboloEncontrado()) {
                    return null;
                }
            }
        }

        visitarBlocos(declaracaoFuncao.getBlocos());

        return null;
    }

    private void visitarBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return;
        }

        if (blocos != null) {
            for (NoBloco bloco : blocos) {
                if (simboloEncontrado()) {
                    break;
                }

                bloco.aceitar(this);
            }
        }
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = noDeclaracaoVariavel.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            declaracaoSimbolo = noDeclaracaoVariavel;

            return null;
        }

        if (noDeclaracaoVariavel.getInicializacao() != null) {
            noDeclaracaoVariavel.getInicializacao().aceitar(this);
        }

        return null;
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        if (noCaso.getExpressao() != null) {
            noCaso.getExpressao().aceitar(this);
        }

        visitarBlocos(noCaso.getBlocos());

        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = chamadaFuncao.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            if (chamadaFuncao.getEscopoBiblioteca() == null) {
                declaracaoSimbolo = chamadaFuncao.getOrigemDaReferencia();

                return null;
            } else {
                throw new ExcecaoVisitaASA(new ErroAoRenomearSimbolo(String.format("A função \"%s\" não pode ser renomeada porque pertence a uma biblioteca", chamadaFuncao.getNome()), ErroAoRenomearSimbolo.Tipo.ERRO_USUARIO), asa, chamadaFuncao);
            }
        }

        if (chamadaFuncao.getParametros() != null) {
            for (NoExpressao expressao : chamadaFuncao.getParametros()) {
                if (simboloEncontrado()) {
                    return null;
                }

                expressao.aceitar(this);
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        noEnquanto.getCondicao().aceitar(this);

        if (simboloEncontrado()) {
            return null;
        }

        visitarBlocos(noEnquanto.getBlocos());

        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        noEscolha.getExpressao().aceitar(this);

        for (NoCaso caso : noEscolha.getCasos()) {
            if (simboloEncontrado()) {
                return null;
            }

            caso.aceitar(this);
        }

        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        noFacaEnquanto.getCondicao().aceitar(this);

        if (simboloEncontrado()) {
            return null;
        }

        visitarBlocos(noFacaEnquanto.getBlocos());

        return null;
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        noMenosUnario.getExpressao().aceitar(this);

        return null;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        noNao.getExpressao().aceitar(this);

        return null;
    }

    private Object visitarOperacao(NoOperacao operacao) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        operacao.getOperandoEsquerdo().aceitar(this);

        if (simboloEncontrado()) {
            return null;
        }

        operacao.getOperandoDireito().aceitar(this);

        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoLogicaIgualdade);
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoLogicaDiferenca);
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoAtribuicao);
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoLogicaE);
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoLogicaOU);
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoLogicaMaior);
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoLogicaMaiorIgual);
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoLogicaMenor);
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoLogicaMenorIgual);
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoSoma);
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoSubtracao);
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoDivisao);
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoMultiplicacao);
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoModulo);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoBitwiseLeftShift);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoBitwiseRightShift);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoBitwiseE);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoBitwiseOu);
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA {
        return visitarOperacao(noOperacaoBitwiseXOR);
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA {
        noOperacaoBitwiseNao.getExpressao().aceitar(this);

        return null;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        if (noPara.getInicializacoes() != null) {
            for (NoBloco inicializacao : noPara.getInicializacoes()) {
                inicializacao.aceitar(this);
            }
        }

        if (simboloEncontrado()) {
            return null;
        }

        if (noPara.getCondicao() != null) {
            noPara.getCondicao().aceitar(this);
        }

        if (simboloEncontrado()) {
            return null;
        }

        if (noPara.getIncremento() != null) {
            noPara.getIncremento().aceitar(this);
        }

        visitarBlocos(noPara.getBlocos());

        return null;
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = noReferenciaMatriz.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            if (noReferenciaMatriz.getEscopoBiblioteca() == null) {
                declaracaoSimbolo = noReferenciaMatriz.getOrigemDaReferencia();

                return null;
            } else {
                throw new ExcecaoVisitaASA(new ErroAoRenomearSimbolo(String.format("A matriz \"%s\" não pode ser renomeada porque pertence a uma biblioteca", noReferenciaMatriz.getNome()), ErroAoRenomearSimbolo.Tipo.ERRO_USUARIO), asa, noReferenciaMatriz);
            }
        }

        if (noReferenciaMatriz.getLinha() != null) {
            noReferenciaMatriz.getLinha().aceitar(this);
        }

        if (simboloEncontrado()) {
            return null;
        }

        if (noReferenciaMatriz.getColuna() != null) {
            noReferenciaMatriz.getColuna().aceitar(this);
        }

        return null;
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = noReferenciaVetor.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            if (noReferenciaVetor.getEscopoBiblioteca() == null) {
                declaracaoSimbolo = noReferenciaVetor.getOrigemDaReferencia();

                return null;
            } else {
                throw new ExcecaoVisitaASA(new ErroAoRenomearSimbolo(String.format("O vetor \"%s\" não pode ser renomeado porque pertence a uma biblioteca", noReferenciaVetor.getNome()), ErroAoRenomearSimbolo.Tipo.ERRO_USUARIO), asa, noReferenciaVetor);
            }
        }

        if (noReferenciaVetor.getIndice() != null) {
            noReferenciaVetor.getIndice().aceitar(this);
        }

        return null;
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        if (noRetorne.getExpressao() != null) {
            noRetorne.getExpressao().aceitar(this);
        }

        return null;
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        noSe.getCondicao().aceitar(this);

        if (simboloEncontrado()) {
            return null;
        }

        visitarBlocos(noSe.getBlocosVerdadeiros());

        if (simboloEncontrado()) {
            return null;
        }

        visitarBlocos(noSe.getBlocosFalsos());

        return null;
    }

    @Override
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        if (noMatriz.getValores() != null) {
            for (List<Object> linhaMatriz : noMatriz.getValores()) {
                for (Object objeto : linhaMatriz) {
                    if (objeto instanceof No) {
                        ((No) objeto).aceitar(this);

                        if (simboloEncontrado()) {
                            return null;
                        }

                    }
                }
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        if (noVetor.getValores() != null) {
            for (Object object : noVetor.getValores()) {
                if (object instanceof No) {
                    ((No) object).aceitar(this);

                    if (simboloEncontrado()) {
                        return null;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = noDeclaracaoMatriz.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            declaracaoSimbolo = noDeclaracaoMatriz;

            return null;
        }

        if (noDeclaracaoMatriz.getNumeroLinhas() != null) {
            noDeclaracaoMatriz.getNumeroLinhas().aceitar(this);
        }

        if (simboloEncontrado()) {
            return null;
        }

        if (noDeclaracaoMatriz.getNumeroColunas() != null) {
            noDeclaracaoMatriz.getNumeroColunas().aceitar(this);
        }

        if (simboloEncontrado()) {
            return null;
        }

        if (noDeclaracaoMatriz.getInicializacao() != null) {
            noDeclaracaoMatriz.getInicializacao().aceitar(this);
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = noDeclaracaoVetor.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            declaracaoSimbolo = noDeclaracaoVetor;

            return null;
        }

        if (noDeclaracaoVetor.getTamanho() != null) {
            noDeclaracaoVetor.getTamanho().aceitar(this);
        }

        if (simboloEncontrado()) {
            return null;
        }

        if (noDeclaracaoVetor.getInicializacao() != null) {
            noDeclaracaoVetor.getInicializacao().aceitar(this);
        }

        return null;
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = noReferenciaVariavel.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            if (noReferenciaVariavel.getEscopoBiblioteca() == null) {
                declaracaoSimbolo = noReferenciaVariavel.getOrigemDaReferencia();

                return null;
            } else {
                throw new ExcecaoVisitaASA(new ErroAoRenomearSimbolo(String.format("A constante \"%s\" não pode ser renomeada porque pertence a uma biblioteca", noReferenciaVariavel.getNome()), ErroAoRenomearSimbolo.Tipo.ERRO_USUARIO), asa, noReferenciaVariavel);
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA {
        if (simboloEncontrado()) {
            return null;
        }

        TrechoCodigoFonte trecho = noDeclaracaoParametro.getTrechoCodigoFonteNome();

        if (trecho.getLinha() == linha && trecho.getColuna() == coluna) {
            declaracaoSimbolo = noDeclaracaoParametro;
        }

        return null;
    }

    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoSenao noSenao) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoParametroFuncao noParametroFuncao) throws ExcecaoVisitaASA {
        return null;
    }
}
