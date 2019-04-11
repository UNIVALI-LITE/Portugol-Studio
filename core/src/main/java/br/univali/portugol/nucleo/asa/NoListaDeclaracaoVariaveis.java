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

    public NoListaDeclaracaoVariaveis(TipoDado tipo) {
        this.tipo = tipo;
    }
    
    public void adicionaDeclaracao(NoDeclaracaoVariavel declaracao) {
        declaracoes.add(declaracao);
    }
    
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA {
        for (NoDeclaracaoVariavel declaracao : declaracoes) {
            declaracao.aceitar(visitante);
        }

        return null;
    }

    public List<NoDeclaracaoVariavel> getDeclaracoes() {
        return declaracoes;
    }
    
    @Override
    public String getNome() {
        return "";
    }
}
