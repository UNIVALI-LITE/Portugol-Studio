package br.univali.ps.ui;

import br.univali.ps.ui.abas.AbaConsole;
import br.univali.ps.ui.abas.AbaMensagemCompilador;
import br.univali.ps.ui.abas.AbaEnunciado;

public final class PainelSaida extends PainelTabulado
{
    private final AbaConsole abaConsole;
    private final AbaMensagemCompilador abaMensagensCompilador;
    private AbaEnunciado abaEnunciado;

    public PainelSaida()
    {
        initComponents();

        abaConsole = new AbaConsole();
        add(abaConsole);
        
        abaMensagensCompilador = new AbaMensagemCompilador();
        //abaMensagensCompilador.adicionar(this);
        this.add(abaMensagensCompilador);
    }

    public AbaConsole getConsole()
    {
        return abaConsole;
    }

    public AbaMensagemCompilador getAbaMensagensCompilador()
    {
        return abaMensagensCompilador;
    }

    public AbaEnunciado getEnunciadoAba()
    {
        return abaEnunciado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 8, 8, 5));
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
