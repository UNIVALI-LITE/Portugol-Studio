package br.univali.ps.ui.paineis;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.MetaDadosPlugin;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.telas.TelaCustomBorder;
import br.univali.ps.ui.telas.TelaInformacoesPlugin;
import br.univali.ps.ui.utils.IconFactory;

/**
 * @author Luiz Fernando Noschang
 */
public final class PainelPlugins extends javax.swing.JPanel implements Themeable {

    private Plugin plugin;
    private AbaCodigoFonte abaCodigoFonte;

    public PainelPlugins() {
        initComponents();
        criarDicasInterface();
        configurarCores();
        
        botaoFechar.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_close.png"));
        botaoInformacoes.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "information.png"));
    }

    @Override
    public void configurarCores() {
        setBackground(ColorController.COR_DESTAQUE);
        painelBarraFerramentas.setBackground(ColorController.COR_DESTAQUE);
        painelConteudo.setBackground(ColorController.COR_DESTAQUE);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configuraWebLaf(separator);
            WeblafUtils.configuraWeblaf(barraFerramentas);//tira a borda dos botões principais
            WeblafUtils.configurarBotao(botaoInformacoes, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
            WeblafUtils.configurarBotao(botaoFechar, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
        }
    }

    private void criarDicasInterface() {
        FabricaDicasInterface.criarTooltip(botaoFechar, "Fecha o painel de plugins");
        FabricaDicasInterface.criarTooltip(botaoInformacoes, "Exibe informações sobre este plugin");
    }

    public void setPlugin(Plugin plugin) {
        if (plugin != null) {
            removerPlugin();

            this.plugin = plugin;
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setAbaCodigoFonte(AbaCodigoFonte abaCodigoFonte) {
        this.abaCodigoFonte = abaCodigoFonte;
    }

    public void removerPlugin() {
        if (this.plugin != null) {
            painelConteudo.remove(this.plugin.getVisao());
            this.plugin = null;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelBarraFerramentas = new javax.swing.JPanel();
        painelAlinhamentoFerramentas = new javax.swing.JPanel();
        painelAlinhamentoNome = new javax.swing.JPanel();
        rotuloNome = new javax.swing.JLabel();
        barraFerramentas = new javax.swing.JToolBar();
        alinhador = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        botaoInformacoes = new com.alee.laf.button.WebButton();
        botaoFechar = new com.alee.laf.button.WebButton();
        separator = new com.alee.laf.separator.WebSeparator();
        painelConteudo = new javax.swing.JPanel();

        setBackground(new java.awt.Color(250, 250, 250));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setPreferredSize(new java.awt.Dimension(200, 200));
        setLayout(new java.awt.BorderLayout());

        painelBarraFerramentas.setBackground(new java.awt.Color(255, 0, 51));
        painelBarraFerramentas.setOpaque(false);
        painelBarraFerramentas.setPreferredSize(new java.awt.Dimension(304, 34));
        painelBarraFerramentas.setLayout(new java.awt.BorderLayout());

        painelAlinhamentoFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        painelAlinhamentoFerramentas.setOpaque(false);
        painelAlinhamentoFerramentas.setPreferredSize(new java.awt.Dimension(302, 30));
        painelAlinhamentoFerramentas.setLayout(new java.awt.BorderLayout());

        painelAlinhamentoNome.setOpaque(false);
        painelAlinhamentoNome.setLayout(new java.awt.BorderLayout());

        rotuloNome.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        rotuloNome.setForeground(new java.awt.Color(255, 255, 255));
        rotuloNome.setText("Plugin");
        rotuloNome.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 0));
        rotuloNome.setIconTextGap(6);
        rotuloNome.setPreferredSize(new java.awt.Dimension(59, 80));
        painelAlinhamentoNome.add(rotuloNome, java.awt.BorderLayout.CENTER);

        barraFerramentas.setFloatable(false);
        barraFerramentas.setRollover(true);
        barraFerramentas.setOpaque(false);
        barraFerramentas.setPreferredSize(new java.awt.Dimension(50, 25));
        barraFerramentas.add(alinhador);

        botaoInformacoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/information.png"))); // NOI18N
        botaoInformacoes.setFocusable(false);
        botaoInformacoes.setHideActionText(true);
        botaoInformacoes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoInformacoes.setMaximumSize(new java.awt.Dimension(22, 22));
        botaoInformacoes.setMinimumSize(new java.awt.Dimension(22, 22));
        botaoInformacoes.setPreferredSize(new java.awt.Dimension(22, 22));
        botaoInformacoes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoInformacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoInformacoesActionPerformed(evt);
            }
        });
        barraFerramentas.add(botaoInformacoes);

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/window_close.png"))); // NOI18N
        botaoFechar.setFocusable(false);
        botaoFechar.setHideActionText(true);
        botaoFechar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoFechar.setMaximumSize(new java.awt.Dimension(22, 22));
        botaoFechar.setMinimumSize(new java.awt.Dimension(22, 22));
        botaoFechar.setPreferredSize(new java.awt.Dimension(22, 22));
        botaoFechar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        barraFerramentas.add(botaoFechar);

        painelAlinhamentoNome.add(barraFerramentas, java.awt.BorderLayout.EAST);

        painelAlinhamentoFerramentas.add(painelAlinhamentoNome, java.awt.BorderLayout.CENTER);

        painelBarraFerramentas.add(painelAlinhamentoFerramentas, java.awt.BorderLayout.CENTER);
        painelBarraFerramentas.add(separator, java.awt.BorderLayout.SOUTH);

        add(painelBarraFerramentas, java.awt.BorderLayout.NORTH);

        painelConteudo.setOpaque(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());
        add(painelConteudo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoInformacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoInformacoesActionPerformed
        TelaCustomBorder telaInformacoesPlugin = PortugolStudio.getInstancia().getTelaInformacoesPlugin();
        ((TelaInformacoesPlugin)telaInformacoesPlugin.getPanel()).setPlugin(plugin);
        telaInformacoesPlugin.pack();
        telaInformacoesPlugin.setVisible(true);
        
    }//GEN-LAST:event_botaoInformacoesActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        if (abaCodigoFonte != null) {
            abaCodigoFonte.ocultarPainelPlugins();
        }
    }//GEN-LAST:event_botaoFecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler alinhador;
    private javax.swing.JToolBar barraFerramentas;
    private com.alee.laf.button.WebButton botaoFechar;
    private com.alee.laf.button.WebButton botaoInformacoes;
    private javax.swing.JPanel painelAlinhamentoFerramentas;
    private javax.swing.JPanel painelAlinhamentoNome;
    private javax.swing.JPanel painelBarraFerramentas;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JLabel rotuloNome;
    private com.alee.laf.separator.WebSeparator separator;
    // End of variables declaration//GEN-END:variables
}
