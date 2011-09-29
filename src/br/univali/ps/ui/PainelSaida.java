package br.univali.ps.ui;

import javax.swing.JTabbedPane;

public class PainelSaida extends JTabbedPane {

    private AbaConsole console;
    private AbaMensagemCompilador mensagemCompilador;
    
    public PainelSaida() {
        initComponents();
        console = new AbaConsole(this);
        mensagemCompilador = new AbaMensagemCompilador(this);
    }

    public AbaConsole getConsole() {
        return console;
    }

    public AbaMensagemCompilador getMensagemCompilador() {
        return mensagemCompilador;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
