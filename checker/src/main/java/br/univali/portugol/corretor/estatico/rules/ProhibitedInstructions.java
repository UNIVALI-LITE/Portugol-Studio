package br.univali.portugol.corretor.estatico.rules;

import br.univali.portugol.nucleo.asa.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProhibitedInstructions implements VisitanteASA{

    final Map<Class<? extends No>, Boolean> instructions; 

    private void checkInstruction(No no){
        if (instructions.containsKey(no.getClass())){
            instructions.put(no.getClass(), Boolean.FALSE);
        }
    }
    
    public void verificar(){
    
        for (Class c : instructions.keySet()){
            if (instructions.get(c)){
                System.out.println("Tente não utilizar uma instrução "+c.getSimpleName());
            }
        }
        
    }
    
    private void visitarBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA{
         if (blocos != null) {
             for (NoBloco noBloco : blocos) {
                 noBloco.aceitar(this);
             }
         }     
    }
    
    public ProhibitedInstructions(List<Class<? extends No>> instructions) {
        this.instructions = new HashMap<Class<? extends No>, Boolean>();
        for (Class<? extends No> class1 : instructions) {
            this.instructions.put(class1, Boolean.FALSE);
        }
    }
    
    public boolean thereIsAbsentInstruction() {
        for (Boolean bool : instructions.values()) {
            if (!bool)
                return true; 
        }
        return false;
    }
    
    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA {
        List<NoDeclaracao> listaDeclaracoesGlobais = asap.getListaDeclaracoesGlobais();
        
        for (NoDeclaracao noDeclaracao : listaDeclaracoesGlobais) {
            noDeclaracao.aceitar(this);
        }
        
        return null;
    }

    @Override
    public Object visitar(NoCadeia no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoCaracter no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoCaso no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        visitarBlocos(no.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        visitarBlocos(no.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoEnquanto no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        visitarBlocos(no.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoEscolha no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        final List<NoCaso> casos = no.getCasos();
        for (NoCaso noCaso : casos) {
            noCaso.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        visitarBlocos(no.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoInteiro no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoLogico no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoMatriz no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoMenosUnario no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoNao no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoBitwiseNao no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoPara no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        visitarBlocos(no.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoPare no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoReal no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoReferenciaMatriz no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVariavel no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVetor no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoRetorne no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoSe no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        visitarBlocos(no.getBlocosVerdadeiros());
        visitarBlocos(no.getBlocosFalsos());
        return null;
    }

    @Override
    public Object visitar(NoVetor no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoParametro no) throws ExcecaoVisitaASA {
        checkInstruction(no);
        return null;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca no) throws ExcecaoVisitaASA {
        checkInstruction(no);
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
