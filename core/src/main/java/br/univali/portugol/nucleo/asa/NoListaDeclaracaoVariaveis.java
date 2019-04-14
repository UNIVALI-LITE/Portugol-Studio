package br.univali.portugol.nucleo.asa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elieser
 */
public class NoListaDeclaracaoVariaveis extends NoBloco implements NoDeclaracao {

    private final List<NoDeclaracaoVariavel> declaracoes = new ArrayList<>();
    private final TipoDado tipo;
    private final boolean constante;

    public NoListaDeclaracaoVariaveis(TipoDado tipo, boolean constante) {
        this.tipo = tipo;
        this.constante = constante;
    }
    
    public void adicionaDeclaracao(NoDeclaracaoVariavel declaracao) {
        declaracoes.add(declaracao);
    }

    public TipoDado getTipo() {
        return tipo;
    }
    
    public boolean ehConstante() {
        return constante;
    }
    
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA {
        for (NoDeclaracaoVariavel declaracao : declaracoes) {
            visitante.visitar(declaracao);
        }
        
        return null;
    }

    public List<NoDeclaracaoVariavel> getDeclaracoes() {
        return declaracoes;
    }
    
    @Override
    public String getNome() { // TODO remover este método da Interface No Declaracao
        return "";
    }
}
