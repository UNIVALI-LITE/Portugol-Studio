package br.univali.ps.ui.rstautil;

import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoNao;
import br.univali.portugol.nucleo.asa.NoOperacao;
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
import br.univali.portugol.nucleo.asa.NoReferencia;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.ps.ui.rstautil.lista.VisitanteNulo;
import java.util.List;

//classe usada para procurar por um determinado símbolo dentro da ASA
public class ProcuradorDeDeclaracao extends VisitanteNulo {

    private NoDeclaracao noDeclaracao;
    private final String nomeDoSimbolo;
    private final int colunaDoSimbolo;
    private final int linhaDoSimbolo;
    private final int tamanhoDoTexto;
    private boolean declaracaoEncontrada;

    public ProcuradorDeDeclaracao(String nomeDoSimbolo, int linhaDoSimbolo, int colunaDoSimbolo, int tamanhoDoTexto) {
        this.nomeDoSimbolo = nomeDoSimbolo;
        this.linhaDoSimbolo = linhaDoSimbolo;
        this.colunaDoSimbolo = colunaDoSimbolo;
        this.tamanhoDoTexto = tamanhoDoTexto;
        this.declaracaoEncontrada = false;
    }

    public boolean encontrou() {
        return declaracaoEncontrada;
    }

    public NoDeclaracao getNoDeclaracao() {
        return noDeclaracao;
    }

    private void verificarNoDeclaracao(NoDeclaracao no) throws ExcecaoVisitaASA {
        boolean mesmoNome = no.getNome().equals(nomeDoSimbolo);
        boolean mesmaLinha = no.getTrechoCodigoFonteNome().getLinha() == linhaDoSimbolo;
        boolean mesmaColuna = no.getTrechoCodigoFonteNome().getColuna() == colunaDoSimbolo;
        boolean mesmoTamanho = no.getTrechoCodigoFonteNome().getTamanhoTexto() == tamanhoDoTexto;
        boolean encontrouSimbolo = mesmoNome && mesmaLinha && mesmaColuna && mesmoTamanho;
        //caso a coluna e a linha não estejam corretas armazena o nó da declaração e tenta encontrar
        //uma referência para este nó, talvez a string procurada seja de uma referência e não da
        //declaração do nó
        if (encontrouSimbolo || (mesmoNome && mesmoTamanho)) {
            this.noDeclaracao = no;
        }
        if (encontrouSimbolo) {
            declaracaoEncontrada = true;
        }
        else{
            no.getInicializacao().aceitar(this); //tentar encontrar a referência na inicialização
        }
    }

    @Override
    public Object visitar(ArvoreSintaticaAbstrataPrograma asap) throws ExcecaoVisitaASA {
         for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais()) {
            if(!declaracaoEncontrada){
                declaracao.aceitar(this);
            }
        }
        return null;
    }
    
    //+++++++++++++++  DECLARAÇÕES +++++++++++++++++++++++++++++++
    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
        verificarNoDeclaracao(noDeclaracaoVariavel);
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
        verificarNoDeclaracao(noDeclaracaoVetor);
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
        verificarNoDeclaracao(noDeclaracaoMatriz);
        return null;
    }
    //+++++++++++++++  REFERÊNCIAS +++++++++++++++++++++++++++++++
    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA {
        verificaNoReferencia(noReferenciaMatriz);
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA {
        verificaNoReferencia(noReferenciaVariavel);
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA {
        verificaNoReferencia(noReferenciaVetor);
        return null;
    }

    private void verificaNoReferencia(NoReferencia noReferencia) {
        if (!declaracaoEncontrada) {
            if (noDeclaracao != null && noDeclaracao.getNome().equals(noReferencia.getNome())) {
                //encontrou a referência para o nó declaracão encontrado anteriormente na árvore
                declaracaoEncontrada = true;
            }
        }
    }
    //+++++++++++++++  Nós Top level +++++++++++++++++++++++++++++++

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA {
        noSe.getCondicao().aceitar(this);
        if(!declaracaoEncontrada){
            return super.visitar(noSe);
        }
        return null;
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA {
        noCaso.getExpressao().aceitar(this);
        if(!declaracaoEncontrada){
            return super.visitar(noCaso); 
        }
        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA {
        noEscolha.getExpressao().aceitar(this);
        if(!declaracaoEncontrada){
            return super.visitar(noEscolha); 
        }
        return null;
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA {
        noEnquanto.getCondicao().aceitar(this);
        if(!declaracaoEncontrada){
            return super.visitar(noEnquanto); 
        }
        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA {
        noFacaEnquanto.getCondicao().aceitar(this);
        if(!declaracaoEncontrada){
            return super.visitar(noFacaEnquanto); 
        }
        return null;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA {
        noPara.getInicializacao().aceitar(this);
        noPara.getCondicao().aceitar(this);
        noPara.getIncremento().aceitar(this);
        if(!declaracaoEncontrada){
            return super.visitar(noPara); 
        }
        return null;
    }
    
    //++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA {
        noMenosUnario.getExpressao().aceitar(this);
        return null;
    }
    
    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA {
        noOperacaoBitwiseNao.getExpressao().aceitar(this);
        return null;
    }
    
    private void visitaOperandos(NoOperacao noOperacao) throws ExcecaoVisitaASA{
        noOperacao.getOperandoEsquerdo().aceitar(this);
        noOperacao.getOperandoDireito().aceitar(this);
    }
    
    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoAtribuicao);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoDivisao);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoLogicaDiferenca);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoLogicaE);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoLogicaIgualdade);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoLogicaMaior);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoLogicaMaiorIgual);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoLogicaMenor);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoLogicaMenorIgual);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoLogicaOU);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoModulo);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoMultiplicacao);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoSoma);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoSubtracao);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoBitwiseE);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoBitwiseLeftShift);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoBitwiseOu);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoBitwiseRightShift);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA {
        visitaOperandos(noOperacaoBitwiseXOR);
        return null;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA {
        noNao.getExpressao().aceitar(this);
        return null;
    }

}
