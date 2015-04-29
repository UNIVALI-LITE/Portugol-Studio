package br.univali.ps.ui.abas;

import br.univali.portugol.nucleo.SetadorPontosParada;

/**
 *
 * @author elieser
 */

import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoContinue;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoNao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseE;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseLeftShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseOu;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseRightShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseXOR;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaDiferenca;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaE;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaIgualdade;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaior;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaiorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaOU;
import br.univali.portugol.nucleo.asa.NoOperacaoModulo;
import br.univali.portugol.nucleo.asa.NoOperacaoMultiplicacao;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoOperacaoSubtracao;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoPare;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoTitulo;
import br.univali.portugol.nucleo.asa.NoVaPara;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Esta classe recebe uma coleção de inteiros representado os números das linhas
 * onde o usuário colocou pontos de parada. A árvore é precorrida e em cada nó é
 * feita uma comparação com a linha de código que corresponde ao nó. Se existe
 * um ponto de parada para a linha então um ponto de parada é adicionado no nó
 * da AST. Esta classe também desativa os pontos de parada de todos os outros
 * nós que não estão marcados com 'breakpoints', evitando que se tenha que
 * executar algum método que reseta o estado de parada dos nós antes de cada
 * execução do depurador.
 *
 * @author Luiz Fernando
 * @author Elieser
 */


public final class BuscadorDeLinhasParaveis implements VisitanteASA {

    private static final Logger LOGGER = Logger.getLogger(SetadorPontosParada.class.getName());
    private Set<Integer> linhasDosCandidatosParaPontoDeParada;

    private Set<Integer> linhasComPontoDeParada;//guarda somente os pontos de parada que realmente puderam ser adicionados aos nós, já que nem todo nó pode ser parado

//    private boolean podeParar(int linha)
//    {
//        if (linhasDosCandidatosParaPontoDeParada.contains(linha))
//        {
//            linhasComPontoDeParada.add(linha);
//            return true;
//        }
//        return false;
//    }
//
//    private boolean podeParar(TrechoCodigoFonte trechoCodigoFonte)
//    {
//        int linha = trechoCodigoFonte.getLinha();
//        if (linhasDosCandidatosParaPontoDeParada.contains(linha))
//        {
//            linhasComPontoDeParada.add(linha);
//            return true;
//        }
//        return false;
//    }
    private boolean podeParar(NoBloco noBloco) {
        int linha = noBloco.getTrechoCodigoFonte().getLinha();
        if (linhasDosCandidatosParaPontoDeParada.contains(linha)) {
            linhasComPontoDeParada.add(linha);
            return true;
        }
        return false;
    }

    /**
     *
     * @param linhasDosPontosDeParada As linhas onde serão aplicados os pontos
     * de parada
     * @param asa
     * @return Uma coleção contendo apenas as linhas onde foram adicionados os
     * pontos de parada, pois nem todos os nós podem ser parados.
     */
    public Set<Integer> setaPontosDeParada(Collection<Integer> linhasDosPontosDeParada, ArvoreSintaticaAbstrataPrograma asa) {

        this.linhasDosCandidatosParaPontoDeParada = new HashSet();
        for (Integer linha : linhasDosPontosDeParada) {
            this.linhasDosCandidatosParaPontoDeParada.add(linha);
        }

        this.linhasComPontoDeParada = new HashSet<>();

        try {
            asa.aceitar(this);
        } catch (ExcecaoVisitaASA ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return linhasComPontoDeParada;
    }

    @Override
    public Object visitar(ArvoreSintaticaAbstrataPrograma asap) throws ExcecaoVisitaASA {
        for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais()) {
            declaracao.aceitar(this);
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
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA {
        NoExpressao expressao = noCaso.getExpressao();
        if (expressao != null) {
            expressao.definirPontoParada(podeParar(expressao));
        } else {
            noCaso.definirPontoParada(podeParar(noCaso));
        }

        if (noCaso.getBlocos() != null) {
            for (NoBloco filho : noCaso.getBlocos()) {
                filho.aceitar(this);
            }
        }
        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA {
        chamadaFuncao.definirPontoParada(podeParar(chamadaFuncao));
        return null;
    }

    @Override
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA {
        noContinue.definirPontoParada(podeParar(noContinue));
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
        declaracaoFuncao.definirPontoParada(podeParar(declaracaoFuncao));

        for (NoBloco filho : declaracaoFuncao.getBlocos()) {
            filho.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
        noDeclaracaoMatriz.definirPontoParada(podeParar(noDeclaracaoMatriz));
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
        noDeclaracaoVariavel.definirPontoParada(podeParar(noDeclaracaoVariavel));
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
        noDeclaracaoVetor.definirPontoParada(podeParar(noDeclaracaoVetor));
        return null;
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA {
        noEnquanto.getCondicao().definirPontoParada(podeParar(noEnquanto.getCondicao()));
        for (NoBloco bloco : noEnquanto.getBlocos()) {
            bloco.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA {
        noEscolha.definirPontoParada(podeParar(noEscolha));
        for (NoCaso caso : noEscolha.getCasos()) {
            caso.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA {
        noFacaEnquanto.definirPontoParada(podeParar(noFacaEnquanto));
        for (NoBloco no : noFacaEnquanto.getBlocos()) {
            no.aceitar(this);
        }

        noFacaEnquanto.getCondicao().definirPontoParada(podeParar(noFacaEnquanto.getCondicao()));
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
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA {
        noOperacaoAtribuicao.definirPontoParada(podeParar(noOperacaoAtribuicao));
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA {
        NoExpressao condicao = noPara.getCondicao();
        condicao.definirPontoParada(podeParar(condicao));
        for (NoBloco no : noPara.getBlocos()) {
            no.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA {
        noPare.definirPontoParada(podeParar(noPare));
        return null;
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA {
        noRetorne.getExpressao().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA {
        noSe.getCondicao().definirPontoParada(podeParar(noSe.getCondicao()));
        for (NoBloco no : noSe.getBlocosVerdadeiros()) {
            no.aceitar(this);
        }

        if (noSe.getBlocosFalsos() != null) {
            for (NoBloco no : noSe.getBlocosFalsos()) {
                no.aceitar(this);
            }
        }

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
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA {
        return null;
    }
}
