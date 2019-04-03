package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.asa.ASA;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import java.util.ArrayList;
import java.util.Collection;

public class ParserWrapper {

    private final PortugolParser parser;

    private final Collection<ObservadorParsing> observadores;

    public ParserWrapper(PortugolParser parser) {
        this.parser = parser;

        this.observadores = new ArrayList<>();
    }

    public void adicionarObservadorParsing(ObservadorParsing observador) {
        if (observadores.contains(observador)) {
            throw new IllegalStateException("Observador já foi adicionado!");
        }

        observadores.add(observador);
    }

    public ASA parse() {
        return new ASA() {
            @Override
            public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA {
               return null;
            }
        };
    }

}
