package br.univali.ps.ui;

public final class PainelTabuladoPrincipal extends PainelTabulado
{
    public PainelTabuladoPrincipal()
    {
        initComponents();
        
        AbaInicial abaInicial = new AbaInicial(PainelTabuladoPrincipal.this);
        abaInicial.adicionar(PainelTabuladoPrincipal.this);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setFocusable(false);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
