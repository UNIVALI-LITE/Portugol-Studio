package br.univali.ps.ui.paineis;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.MetaDadosPlugin;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.telas.TelaInformacoesPlugin;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Luiz Fernando Noschang
 */
public final class PainelPlugins extends JPanel
{
    private Plugin plugin;
    private MetaDadosPlugin metaDadosPlugin;
    private AbaCodigoFonte abaCodigoFonte;

    public PainelPlugins()
    {
        initComponents();
        criarDicasInterface();
    }

    private void criarDicasInterface()
    {
        FabricaDicasInterface.criarTooltip(botaoFechar, "Fecha o painel de plugins");
        FabricaDicasInterface.criarTooltip(botaoInformacoes, "Exibe informações sobre este plugin");
    }

    public void setPlugin(Plugin plugin)
    {
        if (plugin != null)
        {
            removerPlugin();

            this.plugin = plugin;
            this.metaDadosPlugin = plugin.getMetaDados();

            instalarNovoPlugin();
        }
    }

    public Plugin getPlugin()
    {
        return plugin;
    }

    public void setAbaCodigoFonte(AbaCodigoFonte abaCodigoFonte)
    {
        this.abaCodigoFonte = abaCodigoFonte;
    }

    public void removerPlugin()
    {
        if (this.plugin != null)
        {
            painelConteudo.remove(this.plugin.getVisao());
            this.plugin = null;
        }
    }

    private void instalarNovoPlugin()
    {
        rotuloNome.setIcon(new ImageIcon(metaDadosPlugin.getIcone16x16()));
        rotuloNome.setText(metaDadosPlugin.getNome());

        painelConteudo.add(plugin.getVisao(), BorderLayout.CENTER);
        
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                validate();
                repaint();
            }
        });
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
        botaoInformacoes = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();
        separador = new javax.swing.JSeparator();
        painelConteudo = new javax.swing.JPanel();

        setBackground(new java.awt.Color(250, 250, 250));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setPreferredSize(new java.awt.Dimension(200, 200));
        setLayout(new java.awt.BorderLayout());

        painelBarraFerramentas.setOpaque(false);
        painelBarraFerramentas.setPreferredSize(new java.awt.Dimension(304, 34));
        painelBarraFerramentas.setLayout(new java.awt.BorderLayout());

        painelAlinhamentoFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        painelAlinhamentoFerramentas.setOpaque(false);
        painelAlinhamentoFerramentas.setPreferredSize(new java.awt.Dimension(302, 30));
        painelAlinhamentoFerramentas.setLayout(new java.awt.BorderLayout());

        painelAlinhamentoNome.setOpaque(false);
        painelAlinhamentoNome.setLayout(new java.awt.BorderLayout());

        rotuloNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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

        botaoInformacoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/information.png"))); // NOI18N
        botaoInformacoes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoInformacoes.setFocusable(false);
        botaoInformacoes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoInformacoes.setMaximumSize(new java.awt.Dimension(22, 22));
        botaoInformacoes.setMinimumSize(new java.awt.Dimension(22, 22));
        botaoInformacoes.setOpaque(false);
        botaoInformacoes.setPreferredSize(new java.awt.Dimension(22, 22));
        botaoInformacoes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botaoInformacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoInformacoesActionPerformed(evt);
            }
        });
        barraFerramentas.add(botaoInformacoes);

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close.png"))); // NOI18N
        botaoFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFechar.setFocusable(false);
        botaoFechar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoFechar.setMaximumSize(new java.awt.Dimension(22, 22));
        botaoFechar.setMinimumSize(new java.awt.Dimension(22, 22));
        botaoFechar.setOpaque(false);
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

        separador.setForeground(new java.awt.Color(210, 210, 210));
        painelBarraFerramentas.add(separador, java.awt.BorderLayout.SOUTH);

        add(painelBarraFerramentas, java.awt.BorderLayout.NORTH);

        painelConteudo.setOpaque(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());
        add(painelConteudo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoFecharActionPerformed
    {//GEN-HEADEREND:event_botaoFecharActionPerformed
        if (abaCodigoFonte != null)
        {
            //abaCodigoFonte.ocultarPainelPlugins();
        }
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoInformacoesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoInformacoesActionPerformed
    {//GEN-HEADEREND:event_botaoInformacoesActionPerformed
        TelaInformacoesPlugin telaInformacoesPlugin = PortugolStudio.getInstancia().getTelaInformacoesPlugin();
        telaInformacoesPlugin.setPlugin(plugin);
        telaInformacoesPlugin.setVisible(true);
    }//GEN-LAST:event_botaoInformacoesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler alinhador;
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoInformacoes;
    private javax.swing.JPanel painelAlinhamentoFerramentas;
    private javax.swing.JPanel painelAlinhamentoNome;
    private javax.swing.JPanel painelBarraFerramentas;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JLabel rotuloNome;
    private javax.swing.JSeparator separador;
    // End of variables declaration//GEN-END:variables
}
