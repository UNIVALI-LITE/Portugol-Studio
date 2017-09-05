package br.univali.portugol.corretor.dinamico;

import br.univali.portugol.corretor.dinamico.model.Caso;
import br.univali.portugol.corretor.dinamico.model.Saida;
import java.util.List;

public class CasoFalho {
    
    private Caso casoTestado;
    private List<Saida> saidaEncontrada;

    public CasoFalho(Caso casoTestado, List<Saida> saidaEncontrada) {
        this.casoTestado = casoTestado;
        this.saidaEncontrada = saidaEncontrada;
    }

    public Caso getCasoTestado() {
        return casoTestado;
    }

    public List<Saida> getSaidaEncontrada() {
        if (saidaEncontrada == null) {
            throw new IllegalStateException("Nenhum valor encontrado");
        }
        return saidaEncontrada;
    }
    
}
