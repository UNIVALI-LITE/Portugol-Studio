package br.univali.ps.ui;


public class PainelSaida extends PainelTabulado {

    private AbaConsole console;
    private AbaMensagemCompilador mensagemCompilador;
    private AbaEnunciado enunciadoAba;
    
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

    public AbaEnunciado getEnunciadoAba() {
        return enunciadoAba;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
