package br.univali.portugol.nucleo.asa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elieser
 */
public class NoListaDeclaracoes extends NoBloco implements NoDeclaracao {
    
    protected final List<NoDeclaracao> declaracoes = new ArrayList<>();
    protected final TipoDado tipo;
    protected final boolean constante;

    public NoListaDeclaracoes(TipoDado tipo, boolean constante) {
        this.tipo = tipo;
        this.constante = constante;
    }

    public void adicionaDeclaracao(NoDeclaracao declaracao) {
        declaracoes.add(declaracao);
    }

    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA {
        for (NoDeclaracao declaracao : declaracoes) {
            declaracao.aceitar(visitante);
        }
        return null;
    }
    
    public TipoDado getTipo() {
        return tipo;
    }

    public boolean ehConstante() {
        return constante;
    }

    public List<NoDeclaracao> getDeclaracoes() {
        return declaracoes;
    }
    
    @Override
    public String getNome() { // TODO remover este método da Interface NoDeclaracao
        return "";
    }
    
}
