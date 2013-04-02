/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui;

import ajuda.Ajuda;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
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
        cabecalho.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "information.png"));
        adicionarAbaListener(this);
        initComponents();
        this.setLayout(new BorderLayout());
        Ajuda ajuda = new Ajuda();
        this.add(ajuda,BorderLayout.CENTER);        
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
