package br.univali.portugol.corretor.estatico;

import br.univali.portugol.nucleo.asa.*;
import java.util.List;

public class Serializador implements VisitanteASA{

    private StringBuilder astSerializada = new StringBuilder();

    public String getAstSerializada() {
        return astSerializada.toString();
    }
    
    
    private void visitarBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA{
        astSerializada.append("{");
        if (blocos != null){
            for (NoBloco noBloco : blocos) {
                noBloco.aceitar(this);
            }
        }
        astSerializada.append("}");
    }
    
    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA {
        
        astSerializada.append(1);
        List<NoDeclaracao> declaracoesGlobais = asap.getListaDeclaracoesGlobais();
        
        for (NoDeclaracao noDeclaracao : declaracoesGlobais) {
            noDeclaracao.aceitar(this);
        }
        
        return null;
        
    }

    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA {
        //astSerializada.append("cadeia " + noCadeia.getValor());
        return null; 
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA {
        //astSerializada.append("caracter " + noCaracter.getValor());
        return null; 
    }

    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA {
        astSerializada.append("h");
        visitarBlocos(noCaso.getBlocos());
        return null; 
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA {
        final List<NoExpressao> parametros = chamadaFuncao.getParametros();
        
        if ("escreva".equals(chamadaFuncao.getNome())){
            astSerializada.append(3);
        } else if ("leia".equals(chamadaFuncao.getNome())) {
            astSerializada.append(4);
        } 
        
        if (parametros != null && !parametros.isEmpty())
            for (NoExpressao noExpressao : parametros) {
                noExpressao.aceitar(this);
            }
        
        return null; 
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
        astSerializada.append(2);
        List<NoBloco> blocos = declaracaoFuncao.getBlocos();
        visitarBlocos(blocos);
        return null; 
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
        astSerializada.append(7);
        noDeclaracaoMatriz.getNumeroColunas().aceitar(this);
        noDeclaracaoMatriz.getNumeroLinhas().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
        astSerializada.append(5);
        if (noDeclaracaoVariavel.getInicializacao() != null)
            noDeclaracaoVariavel.getInicializacao().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
        astSerializada.append(6);
        if (noDeclaracaoVetor.getTamanho() != null)
            noDeclaracaoVetor.getTamanho().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA {
        astSerializada.append("a");
        noEnquanto.getCondicao().aceitar(this);
        visitarBlocos(noEnquanto.getBlocos());
        return null; 
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA {
        astSerializada.append("g");
        noEscolha.getExpressao().aceitar(this);
        List<NoCaso> casos = noEscolha.getCasos();
        for (NoCaso noCaso : casos) {
            noCaso.aceitar(this);
        }
        return null; 
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA {
        astSerializada.append("a");
        noFacaEnquanto.getCondicao().aceitar(this);
        visitarBlocos(noFacaEnquanto.getBlocos());
        return null; 
    }

    @Override
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA {
       // astSerializada.append("inteiro "+noInteiro.getValor());
        return null; 
    }

    @Override
    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA {
       // astSerializada.append("logico "+ noLogico.getValor());
        return null; 
    }

    @Override
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA {
     //   astSerializada.append("matriz");
        return null; 
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA {
     //   astSerializada.append("menos unario");
        noMenosUnario.getExpressao().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA {
     //   astSerializada.append("não");
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA {
        noOperacaoLogicaIgualdade.getOperandoEsquerdo().aceitar(this);
       // astSerializada.append("==");
        noOperacaoLogicaIgualdade.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA {
        noOperacaoLogicaDiferenca.getOperandoEsquerdo().aceitar(this);
      //  astSerializada.append("!=");
        noOperacaoLogicaDiferenca.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA {
        noOperacaoAtribuicao.getOperandoEsquerdo().aceitar(this);
        astSerializada.append("=");
        noOperacaoAtribuicao.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA {
        noOperacaoLogicaE.getOperandoEsquerdo().aceitar(this);
      //  astSerializada.append("e");
        noOperacaoLogicaE.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA {
        noOperacaoLogicaOU.getOperandoEsquerdo().aceitar(this);
       // astSerializada.append("ou");
        noOperacaoLogicaOU.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA {
        noOperacaoLogicaMaior.getOperandoEsquerdo().aceitar(this);
       // astSerializada.append(">");
        noOperacaoLogicaMaior.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA {
        noOperacaoLogicaMaiorIgual.getOperandoEsquerdo().aceitar(this);
       // astSerializada.append(">=");
        noOperacaoLogicaMaiorIgual.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA {
        noOperacaoLogicaMenor.getOperandoEsquerdo().aceitar(this);
      //  astSerializada.append("<");
        noOperacaoLogicaMenor.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA {
        noOperacaoLogicaMenorIgual.getOperandoEsquerdo().aceitar(this);
      //  astSerializada.append("<=");
        noOperacaoLogicaMenorIgual.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA {
        noOperacaoSoma.getOperandoEsquerdo().aceitar(this);
       // astSerializada.append("+");
        noOperacaoSoma.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA {
        noOperacaoSubtracao.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append("-");
        noOperacaoSubtracao.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA {
        noOperacaoDivisao.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append("/");
        noOperacaoDivisao.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA {
        noOperacaoMultiplicacao.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append("*");
        noOperacaoMultiplicacao.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA {
        noOperacaoModulo.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append("%");
        noOperacaoModulo.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA {
        noOperacaoBitwiseLeftShift.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append("<<");
        noOperacaoBitwiseLeftShift.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA {
        noOperacaoBitwiseRightShift.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append(">>");
        noOperacaoBitwiseRightShift.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA {
        noOperacaoBitwiseE.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append("&");
        noOperacaoBitwiseE.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA {
        noOperacaoBitwiseOu.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append("|");
        noOperacaoBitwiseOu.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA {
        noOperacaoBitwiseXOR.getOperandoEsquerdo().aceitar(this);
        //astSerializada.append("^");
        noOperacaoBitwiseXOR.getOperandoDireito().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA {        
        //astSerializada.append("~");
        noOperacaoBitwiseNao.getExpressao().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA {
        astSerializada.append("a");
        noPara.getInicializacao().aceitar(this);
        noPara.getCondicao().aceitar(this);
        noPara.getIncremento().aceitar(this);
        visitarBlocos(noPara.getBlocos());
        return null; 
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA {
        astSerializada.append("b");
        return null; 
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA {
        //astSerializada.append("real");
        return null; 
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA {
        astSerializada.append("e");
        noReferenciaMatriz.getLinha().aceitar(this);
        noReferenciaMatriz.getColuna().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA {
        astSerializada.append("c");
        return null; 
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA {
        astSerializada.append("d");
        noReferenciaVetor.getIndice().aceitar(this);
        return null; 
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA {
        astSerializada.append("f");
        return null; 
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA {
        astSerializada.append(8);
        noSe.getCondicao().aceitar(this);
        visitarBlocos(noSe.getBlocosVerdadeiros());
        if (noSe.getBlocosFalsos() != null && !noSe.getBlocosFalsos().isEmpty()) {
            astSerializada.append(9);
            visitarBlocos(noSe.getBlocosFalsos());
        }
        return null; 
    }

    @Override
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA {
        //astSerializada.append("vetor");
        return null; 
    }

    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA {
       // astSerializada.append("declarando o parametro"+noDeclaracaoParametro.getNome());
        //astSerializada.append("tipo parametro"+noDeclaracaoParametro.getTipoDado());
        return null; 
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA {
        
        return null; 
    }

    @Override
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
