package br.univali.ps.ui;

import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.ui.acoes.AcaoNovoArquivo;
import javax.swing.Icon;

public class BotoesControleAba extends CabecalhoAba {

    
    AcaoAbrirArquivo acaoAbrirArquivo;
    AcaoNovoArquivo acaoNovoArquivo;
    
    /** Creates new form BotoesControleAba */
    public BotoesControleAba(Aba aba) {
        super(aba);
        removeAll();
        initComponents();     
    }

    @Override
    public String getTitulo() {
        return "Pagina Inicial";
    }

    @Override
    public void setIcone(Icon icone) {
    }

    @Override
    public void setBotaoFecharVisivel(boolean removivel) {
    }
    
    @Override
    public void setTitulo(String titulo) {
        
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

        jToolBar1 = new javax.swing.JToolBar();
        jBAbrir = new javax.swing.JButton();
        jBNovaAba = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setOpaque(false);

        jBAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_closed.png"))); // NOI18N
        jBAbrir.setBorderPainted(false);
        jBAbrir.setContentAreaFilled(false);
        jBAbrir.setFocusPainted(false);
        jBAbrir.setHideActionText(true);
        jBAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBAbrir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_open.png"))); // NOI18N
        jBAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAbrirActionPerformed(evt);
            }
        });
        jToolBar1.add(jBAbrir);

        jBNovaAba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/plus.png"))); // NOI18N
        jBNovaAba.setBorderPainted(false);
        jBNovaAba.setContentAreaFilled(false);
        jBNovaAba.setFocusable(false);
        jBNovaAba.setHideActionText(true);
        jBNovaAba.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBNovaAba.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBNovaAba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNovaAbaActionPerformed(evt);
            }
        });
        jToolBar1.add(jBNovaAba);

        add(jToolBar1, java.awt.BorderLayout.CENTER);
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
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
