/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui;

import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;

/**
 *
 * @author Carlos
 */
public class AbaAjuda extends Aba implements AbaListener
{
    public AbaAjuda(JTabbedPane painelTabulado)
    {
        super(painelTabulado);
        cabecalho.setTitulo("Ajuda");
        cabecalho.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "help.png"));
        adicionarAbaListener(this);
        initComponents();
        this.setLayout(new BorderLayout());
        List<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean fechandoAba(Aba aba)
    {
        return true;
    }
}
