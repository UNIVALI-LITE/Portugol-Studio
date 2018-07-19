/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.paineis;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.plugins.base.MetaDadosPlugins;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.abas.IndicadorDeProgresso;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.swing.weblaf.jOptionPane.QuestionDialog;
import br.univali.ps.ui.utils.FabricaDeFileChooser;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Adson Esteves
 */
public class PainelPluginsInstalados extends javax.swing.JPanel implements Themeable {

    /**
     * Creates new form PainelPluginsInstalados
     */
    private static final FiltroArquivo filtroPlugins = new FiltroArquivo("Plugin do Portugol", "zip");
    private List<PainelPluginItem> listaPlugins = new ArrayList<>();
    private static JDialog indicadorProgresso;

    public PainelPluginsInstalados() {
        initComponents();
        configurarCores();
        configurarBotoes();
        configuraLoader();
        adicionarPlugins();
    }

    private void configuraLoader() {
        boolean usandoTemaDark = Configuracoes.getInstancia().isTemaDark();
        String caminhoIcone = String.format("/br/univali/ps/ui/icones/%s/grande/load.gif", usandoTemaDark ? "Dark" : "Portugol");
        Icon icone = new ImageIcon(getClass().getResource(caminhoIcone));
        indicadorProgresso = new JDialog();
        indicadorProgresso.setUndecorated(true);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(ColorController.FUNDO_CLARO);
        painel.add(new JLabel(icone, JLabel.CENTER));
        painel.add(new JLabel("Instalando...", JLabel.CENTER));

        indicadorProgresso.add(painel);
        indicadorProgresso.setLocationRelativeTo(Lancador.getJFrame());
        indicadorProgresso.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        indicadorProgresso.pack();
    }

    private void configurarBotoes() {
        botaoSelecionarTodos.setAction(new AbstractAction("selecionar todos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PainelPluginItem component : listaPlugins) {
                    component.getSeletorPlugin().setSelected(true);
                }
            }
        });
        botaoInstalarPlugins.setAction(new AbstractAction("instalar plugins") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dialogoSelecaoArquivo = criarSeletorPlugin();
                if (dialogoSelecaoArquivo.showOpenDialog(PortugolStudio.getInstancia().getTelaPrincipal()) == JFileChooser.APPROVE_OPTION) {
                    final File[] arquivos = dialogoSelecaoArquivo.getSelectedFiles();
                    final List<File> listaArquivos = new ArrayList<>(Arrays.asList(arquivos));
                    GerenciadorPlugins.getInstance().instalarPlugins(listaArquivos, indicadorProgresso);
//                    if (confirmouReinicializacao()) {
//                        Configuracoes.getInstancia().restartApplication();
//                    }
                }
                Configuracoes.getInstancia().setCaminhoUltimoDiretorio(dialogoSelecaoArquivo.getCurrentDirectory());
            }
        });
        botaoDesinstalarPlugins.setAction(new AbstractAction("desinstalar plugins") {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> lista = new ArrayList<>();
                for (PainelPluginItem component : listaPlugins) {
                    lista.add(component.getLabelPluginInstalado().getText());
                }
                GerenciadorPlugins.getInstance().desinstalarPlugins(lista);
            }
        });
    }

    private boolean confirmouReinicializacao() {
        int resp = QuestionDialog.getInstance().showConfirmMessage("Para finalizar a instalação do plugin, o Portugol Studio precisa reinicializar! Confirma?");
        if (resp == JOptionPane.YES_OPTION) {
            return true;
        } else if (resp == JOptionPane.CANCEL_OPTION || resp == JOptionPane.CLOSED_OPTION) {
            return false;
        } else {
            return false;
        }
    }

    private JFileChooser criarSeletorPlugin() {
        JFileChooser dialogoSelecaoArquivo = FabricaDeFileChooser.getFileChooserAbertura();
        dialogoSelecaoArquivo.setCurrentDirectory(new File(Configuracoes.getInstancia().getCaminhoUltimoDiretorio()));
        dialogoSelecaoArquivo.setMultiSelectionEnabled(true);
        dialogoSelecaoArquivo.setAcceptAllFileFilterUsed(false);

        dialogoSelecaoArquivo.addChoosableFileFilter(filtroPlugins);

        dialogoSelecaoArquivo.setFileFilter(filtroPlugins);
        return dialogoSelecaoArquivo;
    }

    private void adicionarPlugins() {
        List<Class<? extends Plugin>> pluginsCarregados = GerenciadorPlugins.getInstance().getPluginsCarregados();
        MetaDadosPlugins metaPlugins = GerenciadorPlugins.getInstance().obterMetaDadosPlugins();

        if (pluginsCarregados.size() < 1) {
            botaoSelecionarTodos.setVisible(false);
        }

        for (Class<? extends Plugin> pluginCarregado : pluginsCarregados) {
            try {
                PainelPluginItem item = new PainelPluginItem();
                item.getLabelPluginInstalado().setText(pluginCarregado.getSimpleName());
                item.getLabelPluginInstalado().setIcon(new ImageIcon(metaPlugins.obter(pluginCarregado).getIcone16x16()));
                painelPluginsInstalados.add(item);
                listaPlugins.add(item);
            } catch (Exception e) {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(String.format("Erro ao instalar o plugin '%s'", pluginCarregado.getName()), e, ExcecaoAplicacao.Tipo.ERRO_PROGRAMA));
            }
        }
    }

    @Override
    public void configurarCores() {
        painelSelecionadorPlugins.setBackground(ColorController.FUNDO_MEDIO);
        painelPluginsInstalados.setBackground(ColorController.FUNDO_CLARO);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configuraWebLaf(scrollPlugins);
            WeblafUtils.configurarBotao(botaoInstalarPlugins, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configurarBotao(botaoDesinstalarPlugins, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configurarBotao(botaoSelecionarTodos, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2, true);
        }
    }

    public JDialog getIndicadorProgresso() {
        return indicadorProgresso;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelSelecionadorPlugins = new javax.swing.JPanel();
        botaoSelecionarTodos = new com.alee.laf.button.WebButton();
        botaoInstalarPlugins = new com.alee.laf.button.WebButton();
        botaoDesinstalarPlugins = new com.alee.laf.button.WebButton();
        scrollPlugins = new javax.swing.JScrollPane();
        painelPluginsInstalados = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        painelSelecionadorPlugins.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        botaoSelecionarTodos.setText("selecionar todos");
        painelSelecionadorPlugins.add(botaoSelecionarTodos);

        botaoInstalarPlugins.setText("instalar plugins");
        painelSelecionadorPlugins.add(botaoInstalarPlugins);

        botaoDesinstalarPlugins.setText("desinstalar plugins");
        painelSelecionadorPlugins.add(botaoDesinstalarPlugins);

        add(painelSelecionadorPlugins, java.awt.BorderLayout.NORTH);

        painelPluginsInstalados.setLayout(new javax.swing.BoxLayout(painelPluginsInstalados, javax.swing.BoxLayout.Y_AXIS));
        scrollPlugins.setViewportView(painelPluginsInstalados);

        add(scrollPlugins, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoDesinstalarPlugins;
    private com.alee.laf.button.WebButton botaoInstalarPlugins;
    private com.alee.laf.button.WebButton botaoSelecionarTodos;
    private javax.swing.JPanel painelPluginsInstalados;
    private javax.swing.JPanel painelSelecionadorPlugins;
    private javax.swing.JScrollPane scrollPlugins;
    // End of variables declaration//GEN-END:variables
}
