package br.univali.ps.ui;


public class PainelSaida extends PainelTabulado {

    private AbaConsole console;
    private AbaMensagemCompilador mensagemCompilador;
    private EnunciadoAba enunciadoAba;
    
    public PainelSaida() {
        initComponents();
        console = new AbaConsole(this);
        mensagemCompilador = new AbaMensagemCompilador(this);
        enunciadoAba = new EnunciadoAba(this);
    }

    public AbaConsole getConsole() {
        return console;
    }

    public AbaMensagemCompilador getMensagemCompilador() {
        return mensagemCompilador;
    }

    public EnunciadoAba getEnunciadoAba() {
        return enunciadoAba;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
