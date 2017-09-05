package br.univali.portugol.corretor.estatico.rules;

import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import br.univali.portugol.nucleo.simbolos.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UsingAux implements VisitanteASA, Dica{
    
    private HashMap<String, Simbolo> memoria = new HashMap<String, Simbolo>();
    private ASAPrograma asa = null;

    List<Mensagem> dicas = new ArrayList<Mensagem>();
        
    
    private String funcaoPrincipal = "inicio";
    
    List<Simbolo> simbolosLidos = new ArrayList<Simbolo>();
    private int atrNumber = 0;
    
    
    private Simbolo aux;
    private Simbolo primeira;
    private Simbolo segunda;
    
    
    public void setFuncaoPrincipal(String funcaoPrincipal){
        this.funcaoPrincipal = funcaoPrincipal;
    }
    
    @Override
    public List<Mensagem> recuperar() {
        
        return dicas;
    }
    
    private void visitarBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA {
        if (blocos != null) {
            for (NoBloco noBloco : blocos) {
                noBloco.aceitar(this);
            }
        }
    }

    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA {
        asa = asap;
        List<NoDeclaracao> listaDeclaracoesGlobais = asap.getListaDeclaracoesGlobais();
        for (NoDeclaracao noDeclaracao : listaDeclaracoesGlobais) {
            noDeclaracao.aceitar(this);
        }
        Funcao f = (Funcao) memoria.get(funcaoPrincipal);
        visitarBlocos(f.getBlocos());
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
        noCaso.getExpressao().aceitar(this);
        visitarBlocos(noCaso.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA {
        
        if (!"escreva".equals(chamadaFuncao.getNome())
                && !"leia".equals(chamadaFuncao.getNome())) {
            Funcao funcao = (Funcao) memoria.get(chamadaFuncao.getNome());

            visitarBlocos(funcao.getBlocos());

        } else {
            if ("leia".equals(chamadaFuncao.getNome())){
                List<NoExpressao> parametros = chamadaFuncao.getParametros();

                for (NoExpressao noExpressao : parametros) {
                    if (noExpressao instanceof NoReferenciaVariavel) {
                        NoReferenciaVariavel ref = (NoReferenciaVariavel) noExpressao;
                        simbolosLidos.add(memoria.get(ref.getNome()));
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
        Funcao f = new Funcao(declaracaoFuncao.getNome(), declaracaoFuncao.getTipoDado(), declaracaoFuncao.getQuantificador(), declaracaoFuncao.getParametros(), declaracaoFuncao);
        memoria.put(declaracaoFuncao.getNome(), f);
        
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
        Matriz m = new Matriz(noDeclaracaoMatriz.getNome(), noDeclaracaoMatriz.getTipoDado(), noDeclaracaoMatriz);
        memoria.put(noDeclaracaoMatriz.getNome(), m);

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {

        Variavel v = new Variavel(noDeclaracaoVariavel.getNome(), noDeclaracaoVariavel.getTipoDado(), noDeclaracaoVariavel);

        memoria.put(noDeclaracaoVariavel.getNome(), v);

        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
        Vetor v = new Vetor(noDeclaracaoVetor.getNome(), noDeclaracaoVetor.getTipoDado(), noDeclaracaoVetor);
        memoria.put(noDeclaracaoVetor.getNome(), v);
        return null;
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA {
        noEnquanto.getCondicao().aceitar(this);
        visitarBlocos(noEnquanto.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA {
        noEscolha.getExpressao().aceitar(this);
        List<NoCaso> casos = noEscolha.getCasos();
        for (NoCaso noCaso : casos) {
            noCaso.aceitar(this);
        }
        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA {
        noFacaEnquanto.getCondicao().aceitar(this);
        visitarBlocos(noFacaEnquanto.getBlocos());
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
        noMenosUnario.getExpressao().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA {
        noNao.getExpressao().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA {
        noOperacaoLogicaIgualdade.getOperandoEsquerdo().aceitar(this);
        noOperacaoLogicaIgualdade.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA {
        noOperacaoLogicaDiferenca.getOperandoEsquerdo().aceitar(this);
        noOperacaoLogicaDiferenca.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA {
        
        if (atrNumber == 0) {
            
            List<String> vars = new ArrayList<String>(memoria.keySet());
            vars.remove(funcaoPrincipal);
            List<Integer> indicesRemocao = new ArrayList<Integer>();
            for (Simbolo s : simbolosLidos){
                for (int i = 0; i < vars.size(); i ++){
                    if (s.getNome().equals(vars.get(i))){
                        indicesRemocao.add(i);
                    }
                }
            }
            
            for (Integer i : indicesRemocao){
                vars.remove(i.intValue());
            }
            
            if(vars.size() == 1){
                
                if (noOperacaoAtribuicao.getOperandoEsquerdo() instanceof NoReferenciaVariavel) {
                    NoReferenciaVariavel ref = (NoReferenciaVariavel) noOperacaoAtribuicao.getOperandoEsquerdo();
                    if (ref.getNome().equals(vars.get(0))){
                        aux = memoria.get(ref.getNome());
                    
                    
                        if (noOperacaoAtribuicao.getOperandoDireito() instanceof NoReferenciaVariavel){
                            NoReferenciaVariavel ref2 = (NoReferenciaVariavel) noOperacaoAtribuicao.getOperandoDireito();
                            if (!ref2.getNome().equals(aux.getNome())){
                                primeira = memoria.get(ref2.getNome());
                            } else {
                                dicas.add(new Mensagem() {

                                    @Override
                                    protected String construirMensagem() {
                                        return "Tente armazenar o valor de outra variável em "+aux.getNome();
                                    }
                                });
                            }
                        } else {
                            dicas.add(new Mensagem() {

                                    @Override
                                    protected String construirMensagem() {
                                        return "Tente armazenar o valor de uma variável em "+aux.getNome();
                                    }
                                });
                        }
                    
                    } else {
                        dicas.add(new Mensagem() {
                            @Override
                            protected String construirMensagem() {
                                return "Lembre-se de utilizar a variável auxiliar.";
                            }
                        });
                    
                    }
                    
                    
                }
                
            } else {
                dicas.add(new Mensagem() {
                            @Override
                            protected String construirMensagem() {
                                return "Tente utilizar uma variável auxiliar.";
                            }
                        });
            }
            atrNumber++;
        } else if (atrNumber == 1  && primeira != null ){
            
            if (noOperacaoAtribuicao.getOperandoEsquerdo() instanceof NoReferenciaVariavel){
                NoReferenciaVariavel ref = (NoReferenciaVariavel) noOperacaoAtribuicao.getOperandoEsquerdo();
                    
                if (ref != null && !ref.getNome().equals(primeira.getNome())){
                    dicas.add(new Mensagem() {
                            @Override
                            protected String construirMensagem() {
                                return "Tente trocar o valor de "+primeira.getNome();
                            }
                        });
                }
                
                if (noOperacaoAtribuicao.getOperandoDireito() instanceof NoReferenciaVariavel){
                            NoReferenciaVariavel ref2 = (NoReferenciaVariavel) noOperacaoAtribuicao.getOperandoDireito();
                            
                            if (ref2.getNome().equals(aux.getNome()) || ref2.getNome().equals(primeira.getNome())){
                                
                                for (String s : memoria.keySet()){
                                    if (aux != null && !s.equals(primeira.getNome()) && !s.equals(aux.getNome())){
                                        segunda = memoria.get(ref2.getNome());
                                    }
                                }
                                
                                dicas.add(new Mensagem() {

                                    @Override
                                    protected String construirMensagem() {
                                        return "Tente armazenar o valor de outra variável em "+primeira.getNome();
                                    }
                                });  
                            } else {
                                segunda = memoria.get(ref2.getNome());
                            }
                }
                
            }
            atrNumber++;
        } else if (segunda != null){
        
            NoReferenciaVariavel ref = (NoReferenciaVariavel) noOperacaoAtribuicao.getOperandoEsquerdo();
                    
                if (!ref.getNome().equals(segunda.getNome())){
                        dicas.add(new Mensagem() {
                            @Override
                            protected String construirMensagem() {
                                return "Tente trocar o valor de "+segunda.getNome();
                            }
                        });
                }
                
                if (noOperacaoAtribuicao.getOperandoDireito() instanceof NoReferenciaVariavel){
                            NoReferenciaVariavel ref2 = (NoReferenciaVariavel) noOperacaoAtribuicao.getOperandoDireito();
                            
                            if (ref2.getNome().equals(segunda.getNome()) || ref2.getNome().equals(primeira.getNome())){
                                dicas.add(new Mensagem() {

                                    @Override
                                    protected String construirMensagem() {
                                        return "Tente armazenar o valor de outra variável em "+segunda.getNome();
                                    }
                                });  
                            }
                }
        }
        
        noOperacaoAtribuicao.getOperandoEsquerdo().aceitar(this);
        
        
        
        noOperacaoAtribuicao.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA {
        noOperacaoLogicaE.getOperandoEsquerdo().aceitar(this);
        noOperacaoLogicaE.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA {
        noOperacaoLogicaOU.getOperandoEsquerdo().aceitar(this);
        noOperacaoLogicaOU.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA {
        noOperacaoLogicaMaior.getOperandoEsquerdo().aceitar(this);
        noOperacaoLogicaMaior.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA {
        noOperacaoLogicaMaiorIgual.getOperandoEsquerdo().aceitar(this);
        noOperacaoLogicaMaiorIgual.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA {
        noOperacaoLogicaMenor.getOperandoEsquerdo().aceitar(this);
        noOperacaoLogicaMenor.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA {
        noOperacaoLogicaMenorIgual.getOperandoEsquerdo().aceitar(this);
        noOperacaoLogicaMenorIgual.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA {
        noOperacaoSoma.getOperandoEsquerdo().aceitar(this);
        noOperacaoSoma.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA {
        noOperacaoSubtracao.getOperandoEsquerdo().aceitar(this);
        noOperacaoSubtracao.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA {
        noOperacaoDivisao.getOperandoEsquerdo().aceitar(this);
        noOperacaoDivisao.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA {
        noOperacaoMultiplicacao.getOperandoEsquerdo().aceitar(this);
        noOperacaoMultiplicacao.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA {
        noOperacaoModulo.getOperandoEsquerdo().aceitar(this);
        noOperacaoModulo.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA {
        noOperacaoBitwiseLeftShift.getOperandoEsquerdo().aceitar(this);
        noOperacaoBitwiseLeftShift.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA {
        noOperacaoBitwiseRightShift.getOperandoEsquerdo().aceitar(this);
        noOperacaoBitwiseRightShift.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA {
        noOperacaoBitwiseE.getOperandoEsquerdo().aceitar(this);
        noOperacaoBitwiseE.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA {
        noOperacaoBitwiseOu.getOperandoEsquerdo().aceitar(this);
        noOperacaoBitwiseOu.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA {
        noOperacaoBitwiseXOR.getOperandoEsquerdo().aceitar(this);
        noOperacaoBitwiseXOR.getOperandoDireito().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA {
        noOperacaoBitwiseNao.getExpressao().aceitar(this);
        return null;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA {
        final NoBloco inicializacao = noPara.getInicializacao();
        if (inicializacao != null) {
            inicializacao.aceitar(this);
        }
        final NoExpressao incremento = noPara.getIncremento();
        if (incremento != null) {
            incremento.aceitar(this);
        }

        noPara.getCondicao().aceitar(this);

        visitarBlocos(noPara.getBlocos());

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
        final String nome = noReferenciaMatriz.getNome();
        Simbolo simbolo = memoria.get(nome);
        simbolo.setUtilizado(true);
        return null;

    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA {
        final String nome = noReferenciaVariavel.getNome();
        Simbolo simbolo = memoria.get(nome);
        simbolo.setUtilizado(true);
        return null;

    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA {
        final String nome = noReferenciaVetor.getNome();
        Simbolo simbolo = memoria.get(nome);
        simbolo.setUtilizado(true);
        return null;

    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA {
        return null;
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA {
        noSe.getCondicao().aceitar(this);
        visitarBlocos(noSe.getBlocosVerdadeiros());
        visitarBlocos(noSe.getBlocosFalsos());
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
