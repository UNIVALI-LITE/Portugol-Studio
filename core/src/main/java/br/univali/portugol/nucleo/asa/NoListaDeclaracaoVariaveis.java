package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Elieser
 */
public class NoListaDeclaracaoVariaveis extends NoListaDeclaracoes<NoDeclaracaoVariavel> {

    public NoListaDeclaracaoVariaveis(TipoDado tipo, boolean constante) {
        super(tipo, constante);
    }

    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA {
        for (NoDeclaracaoVariavel declaracao : declaracoes) {
            visitante.visitar(declaracao);
        }

        return null;
    }

}
