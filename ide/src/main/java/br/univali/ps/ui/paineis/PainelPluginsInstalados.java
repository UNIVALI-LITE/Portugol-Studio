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
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.telas.TelaCustomBorder;
import br.univali.ps.ui.telas.TelaPluginsDisponiveis;
import br.univali.ps.ui.utils.FabricaDeFileChooser;
import br.univali.ps.ui.utils.WebConnectionUtils;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

/**
 *
 * @author Adson Esteves
 */
public class PainelPluginsInstalados extends javax.swing.JPanel implements Themeable {

    /**
     * Creates new form PainelPluginsInstalados
     */
    private static final FiltroArquivo filtroPlugins = new FiltroArquivo("Plugin do Portugol", "psa");
    private List<PainelPluginItem> listaPlugins = new ArrayList<>();    
    private TelaCustomBorder dialogoPai;

    public PainelPluginsInstalados(TelaCustomBorder pai) {
        initComponents();
        
        dialogoPai = pai;
        
        configurarCores();
        configurarBotoes();
        adicionarPlugins();
    }

    private void configurarBotoes() {
        botaoSelecionarTodos.setAction(new AbstractAction("Selecionar todos") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PainelPluginItem component : listaPlugins) {
                    component.getSeletorPlugin().setSelected(true);
                }
            }
        });
        botaoInstalarPlugins.setAction(new AbstractAction("Instalar plugins") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dialogoSelecaoArquivo = criarSeletorPlugin();
                if (dialogoSelecaoArquivo.showOpenDialog(PortugolStudio.getInstancia().getTelaPrincipal()) == JFileChooser.APPROVE_OPTION) {
                    final File[] arquivos = dialogoSelecaoArquivo.getSelectedFiles();
                    final List<File> listaArquivos = new ArrayList<>(Arrays.asList(arquivos));
                    dialogoPai.setVisible(false);
                    GerenciadorPlugins.getInstance().instalarPlugins(listaArquivos);
                }
                Configuracoes.getInstancia().setCaminhoUltimoDiretorio(dialogoSelecaoArquivo.getCurrentDirectory());
            }
        });
        botaoDesinstalarPlugins.setAction(new AbstractAction("Desinstalar plugins") {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<File> lista = new ArrayList<>();
                for (PainelPluginItem component : listaPlugins) {
                	if(component.getSeletorPlugin().isSelected()) {
                		lista.add(component.getPastaDeInstalacao());
                	}
                }
                if(!lista.isEmpty()) {
	                dialogoPai.setVisible(false);
	                GerenciadorPlugins.getInstance().desinstalarPlugins(lista);
                }
            }
        });
        botaoBaixarPlugins.setAction(new AbstractAction("Baixar plugins") {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirTelaPluginsDisponiveis();               
            }
        });
        botaoCriarPlugin.setAction(new AbstractAction("Crie seu Plugin") {
            @Override
            public void actionPerformed(ActionEvent e) {
                WebConnectionUtils.abrirSite("https://github.com/UNIVALI-LITE/Plugin-Exemplo");              
            }
        });
    }
    
    private void exibirTelaPluginsDisponiveis()
    {
        SwingUtilities.invokeLater(() -> {
            TelaCustomBorder main = new TelaCustomBorder("Plugins Disponíveis");
            TelaPluginsDisponiveis ta = new TelaPluginsDisponiveis();
            main.setPanel(ta, true);
            main.setLocationRelativeTo(Lancador.getInstance().getJFrame());
            main.setVisible(true);
            //main.pack();
        });        
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
                item.setPastaDeInstalacao(metaPlugins.obter(pluginCarregado).getArquivoJar().getParentFile());
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
            WeblafUtils.configurarBotao(botaoBaixarPlugins, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configurarBotao(botaoCriarPlugin, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 2, true);
        }
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
        botaoBaixarPlugins = new com.alee.laf.button.WebButton();
        botaoCriarPlugin = new com.alee.laf.button.WebButton();
        scrollPlugins = new javax.swing.JScrollPane();
        painelPluginsInstalados = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        painelSelecionadorPlugins.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        botaoSelecionarTodos.setText("Selecionar todos");
        painelSelecionadorPlugins.add(botaoSelecionarTodos);

        botaoInstalarPlugins.setText("Instalar plugins");
        painelSelecionadorPlugins.add(botaoInstalarPlugins);

        botaoDesinstalarPlugins.setText("Desinstalar plugins");
        painelSelecionadorPlugins.add(botaoDesinstalarPlugins);

        botaoBaixarPlugins.setText("Baixar plugins");
        painelSelecionadorPlugins.add(botaoBaixarPlugins);

        botaoCriarPlugin.setText("Crie seu plugin");
        painelSelecionadorPlugins.add(botaoCriarPlugin);

        add(painelSelecionadorPlugins, java.awt.BorderLayout.NORTH);

        painelPluginsInstalados.setLayout(new javax.swing.BoxLayout(painelPluginsInstalados, javax.swing.BoxLayout.Y_AXIS));
        scrollPlugins.setViewportView(painelPluginsInstalados);

        add(scrollPlugins, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoBaixarPlugins;
    private com.alee.laf.button.WebButton botaoCriarPlugin;
    private com.alee.laf.button.WebButton botaoDesinstalarPlugins;
    private com.alee.laf.button.WebButton botaoInstalarPlugins;
    private com.alee.laf.button.WebButton botaoSelecionarTodos;
    private javax.swing.JPanel painelPluginsInstalados;
    private javax.swing.JPanel painelSelecionadorPlugins;
    private javax.swing.JScrollPane scrollPlugins;
    // End of variables declaration//GEN-END:variables
}
