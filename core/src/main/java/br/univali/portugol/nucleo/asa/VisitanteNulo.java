package br.univali.portugol.nucleo.asa;

import java.util.List;

/**
 *
 * @author elieser
 */
public class VisitanteNulo extends VisitanteASABasico {

    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA {
        
        for (NoInclusaoBiblioteca listaInclusoesBiblioteca : asap.getListaInclusoesBibliotecas())
        {
            listaInclusoesBiblioteca.aceitar(this);
        }
        
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
        if (noCaso.getBlocos() != null) {
            for (NoBloco filho : noCaso.getBlocos()) {
                filho.aceitar(this);
            }
        }
        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
        List<NoDeclaracaoParametro> parametros = declaracaoFuncao.getParametros();
        for (NoDeclaracaoParametro parametro : parametros)
        {
            parametro.aceitar(this);
        }
        
        for (NoBloco filho : declaracaoFuncao.getBlocos()) 
        {
            filho.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz no) throws ExcecaoVisitaASA {
        if (no.temInicializacao())
        {
            no.getInicializacao().aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel no) throws ExcecaoVisitaASA {
        if (no.temInicializacao())
        {
            no.getInicializacao().aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor no) throws ExcecaoVisitaASA {
        if (no.temInicializacao())
        {
            no.getInicializacao().aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA {
        noEnquanto.getCondicao().aceitar(this);
        for (NoBloco bloco : noEnquanto.getBlocos()) {
            bloco.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA {
        noEscolha.getExpressao().aceitar(this);
        for (NoCaso caso : noEscolha.getCasos()) {
            caso.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA {
        noFacaEnquanto.getCondicao().aceitar(this);
        for (NoBloco no : noFacaEnquanto.getBlocos()) {
            no.aceitar(this);
        }
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
    public Object visitar(NoOperacaoLogicaIgualdade no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR no) throws ExcecaoVisitaASA {
        no.getOperandoEsquerdo().aceitar(this);
        no.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoBitwiseNao no) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA {
        List<NoBloco> inicializacoes = noPara.getInicializacoes();
        
        if (inicializacoes != null)
        {
            for (NoBloco inicializacao : inicializacoes)
            {
                inicializacao.aceitar(this);
            }
        }
        
        NoExpressao condicao = noPara.getCondicao();
        if (condicao != null)
        {
            condicao.aceitar(this);
        }
        
        for (NoBloco no : noPara.getBlocos()) {
            no.aceitar(this);
        }
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
        if(noRetorne.getExpressao() != null){ 
            return noRetorne.getExpressao().aceitar(this); 
        } 
        return null; 
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA {
        noSe.getCondicao().aceitar(this);
        
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
