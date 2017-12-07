package br.univali.portugol.nucleo.programa;

import br.univali.portugol.nucleo.execucao.es.Armazenador;
import br.univali.portugol.nucleo.execucao.es.InputMediator;

class InputHandler implements InputMediator, Armazenador {
    private Programa programa;
    private Object valor;
    private boolean cancelado = false;

    public InputHandler(Programa programa) {
        this.programa = programa;
    }

    @Override
    public Object getValor() {
        synchronized (programa.getLOCK()) {
            return valor;
        }
    }

    @Override
    public void setValor(Object valor) {
        synchronized (programa.getLOCK()) {
            this.valor = valor;
            programa.getLOCK().notifyAll();
        }
    }

    @Override
    public void cancelarLeitura() {
        synchronized (programa.getLOCK()) {
            this.cancelado = true;
            programa.getLOCK().notifyAll();
        }
    }

    public boolean isCancelado() {
        synchronized (programa.getLOCK()) {
            return cancelado;
        }
    }
}
