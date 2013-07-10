package br.univali.ps.ui;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.acoes.Acao;
import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.ui.acoes.AcaoListener;
import br.univali.ps.ui.acoes.AcaoNovoArquivo;
import javax.swing.Icon;
import net.java.balloontip.BalloonTip;

public class BotoesControleAba extends CabecalhoAba implements AcaoListener
{
    private AcaoAbrirArquivo acaoAbrirArquivo;
    private AcaoNovoArquivo acaoNovoArquivo;

    public BotoesControleAba(Aba aba)
    {
        super(aba);
        removeAll();
        initComponents();
        criarDicasInterface();
    }

    @Override
    public String getTitulo()
    {
        return "Pagina Inicial";
    }

    @Override
    public void setIcone(Icon icone)
    {
    }

    @Override
    public void setBotaoFecharVisivel(boolean removivel)
    {
    }

    @Override
    public void setTitulo(String titulo)
    {
    }

    public void setAcaoAbrirAction(AcaoAbrirArquivo acao)
    {
        acaoAbrirArquivo = acao;
        acaoAbrirArquivo.adicionarListener(BotoesControleAba.this);
    }

    public void setAcaoNovoArquivo(AcaoNovoArquivo acao)
    {
        acaoNovoArquivo = acao;
        acaoNovoArquivo.adicionarListener(BotoesControleAba.this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jBAbrir = new javax.swing.JButton();
        jBNovaAba = new javax.swing.JButton();

        setFocusable(false);
        setOpaque(false);
        setLayout(new java.awt.GridLayout(1, 2));

        jBAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_closed.png"))); // NOI18N
        jBAbrir.setBorderPainted(false);
        jBAbrir.setContentAreaFilled(false);
        jBAbrir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAbrir.setFocusable(false);
        jBAbrir.setHideActionText(true);
        jBAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBAbrir.setMinimumSize(new java.awt.Dimension(32, 25));
        jBAbrir.setPreferredSize(new java.awt.Dimension(32, 25));
        jBAbrir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_open.png"))); // NOI18N
        jBAbrir.setRequestFocusEnabled(false);
        jBAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBAbrir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBAbrirActionPerformed(evt);
            }
        });
        add(jBAbrir);

        jBNovaAba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/plus.png"))); // NOI18N
        jBNovaAba.setBorderPainted(false);
        jBNovaAba.setContentAreaFilled(false);
        jBNovaAba.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBNovaAba.setFocusable(false);
        jBNovaAba.setHideActionText(true);
        jBNovaAba.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBNovaAba.setPreferredSize(new java.awt.Dimension(32, 25));
        jBNovaAba.setRequestFocusEnabled(false);
        jBNovaAba.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBNovaAba.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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

    @Override
    public void acaoExecutadaSucesso(Acao acao, String mensagem)
    {
    }

    @Override
    public void acaoFalhou(Acao acao, Exception motivoE)
    {
        if (acao instanceof AcaoNovoArquivo)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(motivoE);
        }
    }

    private void criarDicasInterface()
    {
        FabricaDicasInterface.criarDicaInterfacePara(jBAbrir, "Abre um programa ou exercício existente no computador", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(jBNovaAba, "Cria uma nova aba contendo um modelo básico de programa", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
    }
}
