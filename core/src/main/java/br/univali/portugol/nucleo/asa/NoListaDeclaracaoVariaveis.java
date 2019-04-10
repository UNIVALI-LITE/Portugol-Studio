package br.univali.portugol.nucleo.asa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elieser
 */
public class NoListaDeclaracaoVariaveis extends NoBloco{

    private final List<NoDeclaracaoVariavel> declaracoes = new ArrayList<>();
    
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA {
        for (NoDeclaracaoVariavel declaracao : declaracoes) {
            declaracao.aceitar(visitante);
        }
        
        return null;
    }
    
}
