package br.univali.ps.ui;

import javax.swing.JTabbedPane;

public class FabricaAba {
    
    JTabbedPane tabbedPane;

    public FabricaAba(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }
    
    public void criarAbaCodidigoFonte(){
        new AbaCodigoFonte(tabbedPane);
    }
    
}
