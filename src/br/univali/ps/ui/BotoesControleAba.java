package br.univali.ps.ui;

import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.ui.acoes.AcaoNovoArquivo;

public class BotoesControleAba extends CabecalhoAba {

    AcaoAbrirArquivo acaoAbrirArquivo;
    AcaoNovoArquivo acaoNovoArquivo;
    
    /** Creates new form BotoesControleAba */
    public BotoesControleAba(Aba aba) {
        super(aba);
        initComponents();
    }

    public void setAcaoAbrirAction(AcaoAbrirArquivo acao) {
        acaoAbrirArquivo = acao;
    }
    
    public void setAcaoNovoArquivo(AcaoNovoArquivo acao) {
        acaoNovoArquivo = acao;
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
        jBAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrirActionPerformed(evt);
            }
        });
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
        acaoNovoArquivo.actionPerformed(evt);
    }//GEN-LAST:event_jBNovaAbaActionPerformed

    private void jBAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAbrirActionPerformed
        acaoAbrirArquivo.actionPerformed(evt);
    }//GEN-LAST:event_jBAbrirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAbrir;
    private javax.swing.JButton jBNovaAba;
    // End of variables declaration//GEN-END:variables
}
