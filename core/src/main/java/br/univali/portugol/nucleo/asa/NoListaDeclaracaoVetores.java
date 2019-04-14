package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Elieser
 */
public class NoListaDeclaracaoVetores extends NoListaDeclaracoes<NoDeclaracaoVetor> {

    public NoListaDeclaracaoVetores(TipoDado tipo, boolean constante) {
        super(tipo, constante);
    }

    @Override
    public void adicionaDeclaracao(NoDeclaracaoVetor declaracao) {
        declaracoes.add(declaracao);
    }

    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA {
        for (NoDeclaracaoVetor declaracao : declaracoes) {
            visitante.visitar(declaracao);
        }
        
        return null;
    }

}
