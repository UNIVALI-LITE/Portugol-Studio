package br.univali.portugol.nucleo.asa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elieser
 * @param <T>
 */
public abstract class NoListaDeclaracoes <T extends NoDeclaracao> extends NoBloco implements NoDeclaracao {
    
    protected final List<T> declaracoes = new ArrayList<>();
    protected final TipoDado tipo;
    protected final boolean constante;

    public NoListaDeclaracoes(TipoDado tipo, boolean constante) {
        this.tipo = tipo;
        this.constante = constante;
    }

    public void adicionaDeclaracao(T declaracao) {
        declaracoes.add(declaracao);
    }

    public TipoDado getTipo() {
        return tipo;
    }

    public boolean ehConstante() {
        return constante;
    }

    public List<T> getDeclaracoes() {
        return declaracoes;
    }
    
    @Override
    public String getNome() { // TODO remover este método da Interface No Declaracao
        return "";
    }
    
}
