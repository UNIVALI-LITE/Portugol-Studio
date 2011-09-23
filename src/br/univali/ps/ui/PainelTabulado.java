package br.univali.ps.ui;

import javax.swing.Action;
import javax.swing.JComponent;



public class PainelTabulado extends javax.swing.JTabbedPane {

    BotoesControleAba botoesControleAba = new BotoesControleAba();
    
    public PainelTabulado() {
        initComponents();
        add(new JComponent() {});
        setTabComponentAt(0, botoesControleAba);
        botoesControleAba.setFabricaAba(this);
    }
    
    public void init(Action acaoBotaoAbrir){
        botoesControleAba.setBotaoAbrirAction(acaoBotaoAbrir);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
