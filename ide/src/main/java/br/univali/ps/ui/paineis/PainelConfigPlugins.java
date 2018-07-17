package br.univali.ps.ui.paineis;

import br.univali.ps.plugins.base.MetaDadosPlugin;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import com.alee.laf.button.WebButton;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

/**
 * @author Luiz Fernando Noschang
 */
public final class PainelConfigPlugins extends javax.swing.JPanel implements Themeable {

    private List<Plugin> plugins = new ArrayList<>();
    private AbaCodigoFonte abaCodigoFonte;
    private PainelPlugins painelPlugins;
    
    public PainelConfigPlugins() {
        initComponents();
        criarDicasInterface();
        configurarCores();
        botaoFechar.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_close.png"));
    }
    
    public void addModeloLista(Plugin plugin) {
        plugins.add(plugin);
        painelPluginsList.add(new PainelPluginItem(plugin.getMetaDados()));
        painelPluginsList.revalidate();
        painelPluginsList.repaint();
    }

    public void removeModeloLista(Plugin plugin) {
        plugins.remove(plugin);
    }

    @Override
    public void configurarCores() {
        painelBarraFerramentas.setBackground(ColorController.FUNDO_ESCURO);
        painelConteudo.setBackground(ColorController.FUNDO_ESCURO);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configuraWeblaf(barraFerramentas);//tira a borda dos botões principais
            WeblafUtils.configurarBotao(botaoFechar, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
            WeblafUtils.configuraWebLaf(painelScrollPlugins);
            rotuloNome.setForeground(ColorController.COR_LETRA);
        }
    }

    private void criarDicasInterface() {
        FabricaDicasInterface.criarTooltip(botaoFechar, "Fecha o painel de plugins");
    }

    public void setAbaCodigoFonte(AbaCodigoFonte abaCodigoFonte) {
        this.abaCodigoFonte = abaCodigoFonte;
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
        botaoFechar = new com.alee.laf.button.WebButton();
        separador = new javax.swing.JSeparator();
        painelConteudo = new javax.swing.JPanel();
        painelScrollPlugins = new javax.swing.JScrollPane();
        painelPluginsList = new javax.swing.JPanel();

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

        rotuloNome.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        rotuloNome.setForeground(new java.awt.Color(255, 255, 255));
        rotuloNome.setText("Configuração de Plugins");
        rotuloNome.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 0));
        rotuloNome.setIconTextGap(6);
        rotuloNome.setPreferredSize(new java.awt.Dimension(59, 80));
        painelAlinhamentoNome.add(rotuloNome, java.awt.BorderLayout.CENTER);

        barraFerramentas.setFloatable(false);
        barraFerramentas.setRollover(true);
        barraFerramentas.setOpaque(false);
        barraFerramentas.setPreferredSize(new java.awt.Dimension(25, 25));
        barraFerramentas.add(alinhador);

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

        separador.setForeground(new java.awt.Color(210, 210, 210));
        painelBarraFerramentas.add(separador, java.awt.BorderLayout.SOUTH);

        add(painelBarraFerramentas, java.awt.BorderLayout.NORTH);

        painelConteudo.setOpaque(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());

        painelScrollPlugins.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        painelScrollPlugins.setOpaque(false);

        painelPluginsList.setOpaque(false);
        painelPluginsList.setLayout(new javax.swing.BoxLayout(painelPluginsList, javax.swing.BoxLayout.Y_AXIS));
        painelScrollPlugins.setViewportView(painelPluginsList);

        painelConteudo.add(painelScrollPlugins, java.awt.BorderLayout.CENTER);

        add(painelConteudo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        if (abaCodigoFonte != null) {
            abaCodigoFonte.ocultarPainelPlugins();
        }
    }//GEN-LAST:event_botaoFecharActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler alinhador;
    private javax.swing.JToolBar barraFerramentas;
    private com.alee.laf.button.WebButton botaoFechar;
    private javax.swing.JPanel painelAlinhamentoFerramentas;
    private javax.swing.JPanel painelAlinhamentoNome;
    private javax.swing.JPanel painelBarraFerramentas;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelPluginsList;
    private javax.swing.JScrollPane painelScrollPlugins;
    private javax.swing.JLabel rotuloNome;
    private javax.swing.JSeparator separador;
    // End of variables declaration//GEN-END:variables
}
