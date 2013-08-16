package br.univali.ps.ui;

public final class PainelSaida extends PainelTabulado
{
    private AbaConsole abaConsole;
    private AbaMensagemCompilador abaMensagensCompilador;
    private AbaEnunciado abaEnunciado;

    public PainelSaida()
    {
        initComponents();
        
        abaConsole = new AbaConsole();
        abaConsole.adicionar(PainelSaida.this);
        
        abaMensagensCompilador = new AbaMensagemCompilador();
        abaMensagensCompilador.adicionar(PainelSaida.this);
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
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 8, 8, 4));
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
