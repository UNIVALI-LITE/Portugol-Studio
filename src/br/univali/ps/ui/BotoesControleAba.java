/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BotoesControleAba.java
 *
 * Created on 22/09/2011, 17:39:15
 */
package br.univali.ps.ui;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author fillipipelz
 */
public class BotoesControleAba extends JPanel {

    FabricaAba fabricaAba;
    
    /** Creates new form BotoesControleAba */
    public BotoesControleAba() {
        initComponents();
    }

    public void setFabricaAba(JTabbedPane painel){
        fabricaAba = new FabricaAba(painel);
    }
    
    public void setBotaoAbrirAction(Action action) {
        jBAbrir.setAction(action);
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBAbrir = new javax.swing.JButton();
        jBNovaAba = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jBAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_closed.png"))); // NOI18N
        jBAbrir.setBorderPainted(false);
        jBAbrir.setContentAreaFilled(false);
        jBAbrir.setHideActionText(true);
        jBAbrir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_open.png"))); // NOI18N
        add(jBAbrir);

        jBNovaAba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/plus.png"))); // NOI18N
        jBNovaAba.setBorderPainted(false);
        jBNovaAba.setContentAreaFilled(false);
        jBNovaAba.setHideActionText(true);
        jBNovaAba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNovaAbaActionPerformed(evt);
            }
        });
        add(jBNovaAba);
    }// </editor-fold>//GEN-END:initComponents

    private void jBNovaAbaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNovaAbaActionPerformed
        fabricaAba.criarAbaCodidigoFonte();
    }//GEN-LAST:event_jBNovaAbaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbrir;
    private javax.swing.JButton jBNovaAba;
    // End of variables declaration//GEN-END:variables
}
