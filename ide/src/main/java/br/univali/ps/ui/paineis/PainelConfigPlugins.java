package br.univali.ps.ui.paineis;

import br.univali.ps.plugins.base.MetaDadosPlugin;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Component;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

/**
 * @author Luiz Fernando Noschang
 */
public final class PainelConfigPlugins extends javax.swing.JPanel implements Themeable {

    private MeuModel modelListaPlugin = new MeuModel();
    private List<Plugin> plugins = new ArrayList<>();
    private AbaCodigoFonte abaCodigoFonte;
    private PainelPlugins painelPlugins;

    public PainelConfigPlugins() {
        initComponents();
        criarDicasInterface();
        configurarCores();
        botaoFechar.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_close.png"));
        listaPlugins.setModel(modelListaPlugin);
        listaPlugins.setCellRenderer(new Render());
    }

    private class MeuModel implements ListModel<String> {

        private List<MetaDadosPlugin> dados = new ArrayList<>();
        private List<ListDataListener> listeners = new ArrayList<>();

        @Override
        public int getSize() {
            return dados.size();
        }

        @Override
        public String getElementAt(int index) {
            return dados.get(index).getNome();
        }

        @Override
        public void addListDataListener(ListDataListener l) {
            listeners.add(l);
        }

        public MetaDadosPlugin obterMetadado(int indice) {
            return dados.get(indice);
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
            listeners.remove(l);
        }

        public void add(MetaDadosPlugin dado) {
            dados.add(dado);
            fireDataChanged();
        }

        public void clear() {
            dados.clear();
            fireDataChanged();
        }

        public void fireDataChanged() {
            for (ListDataListener listener : listeners) {
                listener.contentsChanged(null);
            }
        }
    }

    public void addModeloLista(Plugin plugin) {
        plugins.add(plugin);
        modelListaPlugin.add(plugin.getMetaDados());
    }

    public void removeModeloLista(Plugin plugin) {
        plugins.remove(plugin);
    }

    public class Render extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            final JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            final MetaDadosPlugin metadado = modelListaPlugin.obterMetadado(index);
            final Image icone = metadado.getIcone16x16();

            renderer.setIcon(new ImageIcon(icone));
            renderer.setBorder(new EmptyBorder(5, 10, 5, 0));

            return renderer;
        }
    }

    @Override
    public void configurarCores() {
        painelBarraFerramentas.setBackground(ColorController.FUNDO_ESCURO);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configuraWeblaf(barraFerramentas);//tira a borda dos botões principais
            WeblafUtils.configurarBotao(botaoFechar, ColorController.COR_PRINCIPAL, ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
            listaPlugins.setBackground(ColorController.COR_DESTAQUE);
            listaPlugins.setForeground(ColorController.COR_LETRA);
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
        scrollPanelPlugins = new javax.swing.JScrollPane();
        listaPlugins = new javax.swing.JList<>();

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

        scrollPanelPlugins.setBorder(null);

        listaPlugins.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listaPlugins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaPluginsMouseClicked(evt);
            }
        });
        listaPlugins.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listaPluginsValueChanged(evt);
            }
        });
        scrollPanelPlugins.setViewportView(listaPlugins);

        painelConteudo.add(scrollPanelPlugins, java.awt.BorderLayout.CENTER);

        add(painelConteudo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        if (abaCodigoFonte != null) {
            abaCodigoFonte.ocultarPainelPlugins();
        }
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void listaPluginsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaPluginsMouseClicked
        final int index = ((JList) evt.getSource()).getSelectedIndex();
        final Plugin plugin = plugins.get(index);
        painelPlugins = new PainelPlugins();
        /*JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setLayout(new GridLayout(1, 1));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(plugin.getVisao());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);*/

        painelPlugins.setAbaCodigoFonte(abaCodigoFonte);
        if (plugin.getVisao() != null) {
            painelPlugins.setPlugin(plugin);
            painelPlugins.add(plugin.getVisao());
        }
        abaCodigoFonte.getScrollInspetor().remove(this);
        abaCodigoFonte.getScrollInspetor().setViewportView(painelPlugins);
        //divisorArvoreInspetor.setDividerLocation(0.7);
        //painelInspetorArvore.validate();*/
    }//GEN-LAST:event_listaPluginsMouseClicked

    private void listaPluginsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listaPluginsValueChanged

    }//GEN-LAST:event_listaPluginsValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler alinhador;
    private javax.swing.JToolBar barraFerramentas;
    private com.alee.laf.button.WebButton botaoFechar;
    private javax.swing.JList<String> listaPlugins;
    private javax.swing.JPanel painelAlinhamentoFerramentas;
    private javax.swing.JPanel painelAlinhamentoNome;
    private javax.swing.JPanel painelBarraFerramentas;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JLabel rotuloNome;
    private javax.swing.JScrollPane scrollPanelPlugins;
    private javax.swing.JSeparator separador;
    // End of variables declaration//GEN-END:variables
}
