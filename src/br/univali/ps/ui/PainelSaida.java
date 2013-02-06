package br.univali.ps.ui;

import br.univali.ps.depurador.AbaDepurador;
import javax.swing.JTabbedPane;

public class PainelSaida extends JTabbedPane {

    private AbaConsole console;
    private AbaMensagemCompilador mensagemCompilador;
    private AbaDepurador depurador; 
    
    public PainelSaida() {
        initComponents();
        console = new AbaConsole(this);
        mensagemCompilador = new AbaMensagemCompilador(this);
        depurador = new AbaDepurador(this);
    }

    public AbaConsole getConsole() {
        return console;
    }

    public AbaDepurador getDepurador (){
        return depurador;
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
