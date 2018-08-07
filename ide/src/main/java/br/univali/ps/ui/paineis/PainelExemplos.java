package br.univali.ps.ui.paineis;

import br.univali.ps.ui.paineis.utils.ExampleTreeRender;
import br.univali.ps.ui.paineis.utils.ExampleMutableTreeNode;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.editor.Editor;
import br.univali.ps.ui.utils.FileHandle;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.swing.weblaf.PSTreeUI;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.telas.TelaPrincipal;
import br.univali.ps.ui.utils.FabricaDeFileChooser;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import com.alee.laf.button.WebButton;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Alisson
 */
public class PainelExemplos extends javax.swing.JPanel implements Themeable{

    private static final Logger LOGGER = Logger.getLogger(PainelExemplos.class.getName());

    private final Icon imagemPadrao;
    private final Icon imagemPadraolowres;
    private final Icon imagemOpen;
    private final Icon imagemOpenlowres;
    private final Icon imagemHelp;
    private final Icon imagemHelplowres;
    private final Icon imagemPlugins;
    private final Icon imagemPluginslowres;
    private final Icon imagemNew;
    private final Icon imagemNewlowres;
    private final Icon imagemPastaPadrao;

    private final Editor editor;
    
    private boolean redimensionouParaBaixaResolucao = false;
    private boolean primeiraExibicao = true;

    
    private final ImagePanel imagePanel; // usando para desenhar uma imagem que 'estica' e centraliza conforme o tamanho do componente
    private final ImagePanel imagePortugol; // usando para desenhar uma imagem que 'estica' e centraliza conforme o tamanho do componente

    public PainelExemplos() {
        
        initComponents();
        
        labelVersao.setText("v"+PortugolStudio.getInstancia().getVersao());
        imagePanel = new ImagePanel();
        imagePane.add(imagePanel);
        imagePortugol = new ImagePanel();
        areaLogo.add(imagePortugol);
        configurarCores();
        configurarResolucao();
        
        editor = new Editor(true);
        labelTitulo.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png"));
        imagemPadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "file.png");
        imagemPadraolowres = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "file64.png");
        
        imagemNew = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "newfile.png");
        imagemNewlowres = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "newfile64.png");
        
        imagemHelp = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "help.png");
        imagemHelplowres = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "helplow.png");
        
        imagemPlugins = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "plugin.png");
        imagemPluginslowres = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "plugin64.png");
        
        staticPanel.setBackground(ColorController.FUNDO_ESCURO);
        
        imagemOpen = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "openfile.png");
        imagemOpenlowres = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "openfile64.png");
        if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)>=20 && Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<=30 && Calendar.getInstance().get(Calendar.MONTH)==Calendar.DECEMBER){
            imagemPastaPadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/lite_n.png");
        }else{
            imagemPastaPadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/lite.png");
        }
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                jSplitPane1.setDividerLocation(jSplitPane1.getWidth()-300);
            }
            
            
        });
        inicializarJTree();

        painelDireita.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizarPainelDireita();
            }
        });
        buscaExemplo.setSearchAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarExemplos();
            }
        });
        
        configurarAcaoAbrirExemploBuscado();
        configurarAcaoMoverParaBaixoEmExemplosBuscados();
        configurarAcaoMoverParaCimaEmExemplosBuscados();
        
        buscaExemplo.setPlaceholder("Buscar Exemplos");
        buscaExemplo.setSearchDelay(250);
        //areaRecentes.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
        
        atualizarRecentes();
        
    }
    
    private void configurarAcaoAbrirExemploBuscado()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        String nome = "Abrir Exemplo";
        Action acaoAbrirExemploBuscado = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                botaoAbrirExemplo.doClick();
            }
        };
        buscaExemplo.getCampoBusca().getInputMap().put(atalho, nome);
        buscaExemplo.getCampoBusca().getActionMap().put(nome, acaoAbrirExemploBuscado);
    }
    
    private void configurarAcaoMoverParaBaixoEmExemplosBuscados()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        String nome = "Mover para baixo";
        Action acaoAbrirExemploBuscado = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = arvoreExemplos.getSelectionRows()[0];
                arvoreExemplos.setSelectionRow(Math.min(row+1, arvoreExemplos.getRowCount()-1));
            }
        };
        buscaExemplo.getCampoBusca().getInputMap().put(atalho, nome);
        buscaExemplo.getCampoBusca().getActionMap().put(nome, acaoAbrirExemploBuscado);
    }
    
    private void configurarAcaoMoverParaCimaEmExemplosBuscados()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        String nome = "Mover para cima";
        Action acaoAbrirExemploBuscado = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = arvoreExemplos.getSelectionRows()[0];
                arvoreExemplos.setSelectionRow(Math.max(0,row-1));
            }
        };
        buscaExemplo.getCampoBusca().getInputMap().put(atalho, nome);
        buscaExemplo.getCampoBusca().getActionMap().put(nome, acaoAbrirExemploBuscado);
    }
    
    public void criarBotaoAbrirRecentes(){
        staticPanel.removeAll();
        WebButton button = new WebButton();
        button.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().getActionMap().get("Abrir arquivo").actionPerformed(e);
            }
        });
        button.setText("Abrir Arquivo");
        if(redimensionouParaBaixaResolucao)
        {
            textRecentes.setFont(textRecentes.getFont().deriveFont(16f));
            button.setIcon(imagemOpenlowres);
        }else{
            textRecentes.setFont(textRecentes.getFont().deriveFont(24f));
            button.setIcon(imagemOpen);
        }
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        WeblafUtils.configurarBotao(button,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
        FabricaDicasInterface.criarTooltip(button, "Abrir arquivo");
        
        
        WebButton buttonNovo = new WebButton();
        buttonNovo.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PortugolStudio.getInstancia().getTelaPrincipal().criarNovoCodigoFonte();
            }
        });
        buttonNovo.setText("Novo Arquivo");
        if(redimensionouParaBaixaResolucao)
        {
            jSplitPane1.setDividerLocation(jSplitPane1.getWidth()-200);
            textRecentes.setFont(textRecentes.getFont().deriveFont(16f));
            buttonNovo.setIcon(imagemNewlowres);
        }else{
            jSplitPane1.setDividerLocation(jSplitPane1.getWidth()-300);
            textRecentes.setFont(textRecentes.getFont().deriveFont(24f));
            buttonNovo.setIcon(imagemNew);
        }
        buttonNovo.setHorizontalAlignment(SwingConstants.CENTER);
        buttonNovo.setVerticalAlignment(SwingConstants.CENTER);
        buttonNovo.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonNovo.setVerticalTextPosition(SwingConstants.BOTTOM);
        WeblafUtils.configurarBotao(buttonNovo,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
        FabricaDicasInterface.criarTooltip(buttonNovo, "Novo arquivo");
        
        WebButton buttonHelp = new WebButton();
        buttonHelp.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().getActionMap().get(PainelTabuladoPrincipal.ACAO_EXIBIR_AJUDA).actionPerformed(e);
            }
        });
        buttonHelp.setText("Ajuda");
        if(redimensionouParaBaixaResolucao)
        {
            textRecentes.setFont(textRecentes.getFont().deriveFont(16f));
            buttonHelp.setIcon(imagemHelplowres);
        }else{
            textRecentes.setFont(textRecentes.getFont().deriveFont(24f));
            buttonHelp.setIcon(imagemHelp);
        }
        buttonHelp.setHorizontalAlignment(SwingConstants.CENTER);
        buttonHelp.setVerticalAlignment(SwingConstants.CENTER);
        buttonHelp.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonHelp.setVerticalTextPosition(SwingConstants.BOTTOM);
        WeblafUtils.configurarBotao(buttonHelp,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
        FabricaDicasInterface.criarTooltip(buttonHelp, "Ajuda");
        
        WebButton buttonPlugins = new WebButton();
        buttonPlugins.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PortugolStudio.getInstancia().getTelaPluginsInstalados().setVisible(true);
            }
        });
        buttonPlugins.setText("Plugins");
        if(redimensionouParaBaixaResolucao)
        {
            textRecentes.setFont(textRecentes.getFont().deriveFont(16f));
            buttonPlugins.setIcon(imagemPluginslowres);
        }else{
            textRecentes.setFont(textRecentes.getFont().deriveFont(24f));
            buttonPlugins.setIcon(imagemPlugins);
        }
        buttonPlugins.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPlugins.setVerticalAlignment(SwingConstants.CENTER);
        buttonPlugins.setHorizontalTextPosition(SwingConstants.CENTER);
        buttonPlugins.setVerticalTextPosition(SwingConstants.BOTTOM);
        WeblafUtils.configurarBotao(buttonPlugins,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
        FabricaDicasInterface.criarTooltip(buttonPlugins, "Plugins");
        
        staticPanel.add(buttonNovo);
        staticPanel.add(button);
        staticPanel.add(buttonHelp);
        //Reativar quando um plugin for adicionado
        staticPanel.add(buttonPlugins);
    }
    @Override
    public void configurarCores() {
        labelVersao.setForeground(ColorController.COR_LETRA_TITULO);
        arvoreExemplos.setBackground(ColorController.FUNDO_CLARO);
        imagePane.setBackground(ColorController.FUNDO_ESCURO);
        description.setForeground(ColorController.COR_LETRA);
        scrollArvoreExemplos.setBackground(ColorController.FUNDO_CLARO);
        scrollArvoreExemplos.setCorner(JScrollPane.LOWER_RIGHT_CORNER, null);
        labelTitulo.setForeground(ColorController.COR_LETRA_TITULO);
        labelTitulo.setBackground(ColorController.FUNDO_ESCURO);
        textRecentes.setForeground(ColorController.COR_LETRA_TITULO);
        textRecentes.setBackground(ColorController.FUNDO_ESCURO);
        painelRecentesPrincipal.setBackground(ColorController.FUNDO_ESCURO);
        painelRecentesMaster.setBackground(ColorController.FUNDO_ESCURO);
        descriptionPanel.setBackground(ColorController.COR_DESTAQUE);
        areaLogo.setBackground(ColorController.FUNDO_ESCURO);
        painelDireita.setBackground(ColorController.COR_DESTAQUE);
        
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configuraWebLaf(jScrollPane1);
            WeblafUtils.configuraWebLaf(scrollArvoreExemplos);
            WeblafUtils.configuraWebLaf(buscaExemplo.getCampoBusca());
            WeblafUtils.configurarBotao(botaoAbrirExemplo, ColorController.AMARELO, ColorController.FUNDO_ESCURO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 10);
        }
    }
    
    public void atualizarRecentes(){
        boolean arquivoRemovido = false;
        List<File> files = new ArrayList<>(PortugolStudio.getInstancia().getRecentFilesQueue());
        Collections.reverse(files);
        Icon icone = imagemPastaPadrao;
        areaRecentes.removeAll();
        areaLogo.removeAll();
        
        
        WebImage imagemPortugol = new WebImage(icone);
        imagemPortugol.setDisplayType(DisplayType.fitComponent);
        areaLogo.add(imagemPortugol);
        if(files.isEmpty())
        {
            textRecentes.setVisible(false);
        }
        else
        {
            textRecentes.setVisible(true);
        }
        criarBotaoAbrirRecentes();
        for (File recente : files) {
            if(!recente.exists())
            {
                arquivoRemovido = true;
                continue;
            }
            String codigoFonte;
            try {
                codigoFonte = FileHandle.open(recente);
                WebButton button = new WebButton();
                button.setAction(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        TelaPrincipal t = PortugolStudio.getInstancia().getTelaPrincipal();
                        AbaCodigoFonte abaCodigoFonte  = AbaCodigoFonte.novaAba();
                        abaCodigoFonte.setCodigoFonte(codigoFonte, recente, true);
                        t.getPainelTabulado().adicionaAba(abaCodigoFonte);
//                        List<File> arquivo = new ArrayList<>();
//                        arquivo.add(recente);
//                        t.abrirArquivosCodigoFonte(arquivo);
                        PortugolStudio.getInstancia().salvarComoRecente(recente);
                    }
                });
                if(recente.getName().length()>15){
                    button.setText(recente.getName().substring(0, 11)+"...");
                }else{
                    button.setText(recente.getName());
                }
                
                if(redimensionouParaBaixaResolucao)
                {
                    
                    textRecentes.setFont(textRecentes.getFont().deriveFont(16f));
                    button.setIcon(imagemPadraolowres);
                }else{
                    
                    textRecentes.setFont(textRecentes.getFont().deriveFont(24f));
                    button.setIcon(imagemPadrao);
                }
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                WeblafUtils.configurarBotao(button,ColorController.TRANSPARENTE, ColorController.COR_LETRA_TITULO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 5);
                FabricaDicasInterface.criarTooltip(button, recente.getPath());
                areaRecentes.add(button);
            } catch (Exception ex) {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
        }        
        if(arquivoRemovido)
        {                
            PortugolStudio.getInstancia().readRecents();
        }
        areaRecentes.revalidate();
        areaLogo.revalidate();
    }
    
    
    
    private String carregarHTML(String caminho)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(caminho), Charset.forName("UTF-8")));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        String base = contentBuilder.toString();
        
        return base;
    }
    
    private void configurarResolucao()
    {
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                SwingUtilities.invokeLater(() -> 
                        {
                            if (Lancador.getActualSize().width <= 1024 || Lancador.getActualSize().height <= 768)
                            {
                                if (!redimensionouParaBaixaResolucao)
                                {
                                    redimensionouParaBaixaResolucao = true;
                                }
                            }
                            else
                            {
                                redimensionouParaBaixaResolucao = false;
                            }
                            atualizarRecentes();
//                            carregarRecuperados();
                });
            }
        });
    }
    
    private void inicializarJTree() {
        arvoreExemplos.setCellRenderer(new ExampleTreeRender());
        arvoreExemplos.setUI(new PSTreeUI());
        mostrarExemplos();
        initTreeListner();
    }
    
    private void mostrarExemplos()
    {
        File diretorioExemplos = Configuracoes.getInstancia().getDiretorioExemplos();

        if (diretorioExemplos.exists()) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
            List<DefaultMutableTreeNode> nodes = readIndex(diretorioExemplos);
            for (DefaultMutableTreeNode node : nodes) {
                root.add(node);
            }
            DefaultTreeModel model = new DefaultTreeModel(root);
            arvoreExemplos.setModel(model);
            arvoreExemplos.setRootVisible(false);
            arvoreExemplos.setShowsRootHandles(true);
//          expandJTree();
            jTreedoClick();            
        }
    }

    private void jTreedoClick() {
        SwingUtilities.invokeLater(()
                -> {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) arvoreExemplos.getModel().getRoot();
            DefaultMutableTreeNode leaf;
            if(arvoreExemplos.getModel().getChildCount(root)>0)
            {
                leaf = (DefaultMutableTreeNode) root.getFirstChild();
                if(leaf!=null)
                {
                    arvoreExemplos.setSelectionPath(new TreePath(leaf.getPath()));
                }
            }            
            
        });

    }

    private void expandJTree() {
        for (int i = 0; i < arvoreExemplos.getRowCount(); i++) {
            arvoreExemplos.expandRow(i);
        }
    }

    private List<DefaultMutableTreeNode> readIndex(File dir) {
        Properties prop = new Properties();
        List<DefaultMutableTreeNode> nodes = new ArrayList<>();
        try {
            File file = new File(dir, "index.properties");
            if (file.exists()) {
                prop.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                for (int i = 0; i < Integer.parseInt(prop.getProperty("items")); i++) {
                    String item = "item" + i + ".";                                        
                                                                
                    if (prop.getProperty(item + "type").equals("dir")) {
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(prop.getProperty(item + "name"));
                        List<DefaultMutableTreeNode> subNodes = readIndex(new File(dir, prop.getProperty(item + "dir")));
                        subNodes.stream().forEach((subNode) -> {
                            if(buscaExemplo.getCampoBusca().getText().isEmpty())
                            {
                                node.add(subNode);
                            }
                            else
                            {
                                nodes.add(subNode);
                            }
                        });
                        if(buscaExemplo.getCampoBusca().getText().isEmpty())
                        nodes.add(node);
                    } else {
                        if(!prop.getProperty(item + "name").toLowerCase().contains(buscaExemplo.getCampoBusca().getText().toLowerCase()))
                        {                            
                            continue;
                        }
                        DefaultMutableTreeNode leaf;
                        if (Boolean.parseBoolean(prop.getProperty(item + "hasImage"))) {
                            leaf = new ExampleMutableTreeNode(new File(dir, prop.getProperty(item + "file")), prop.getProperty(item + "description"), new File(dir, prop.getProperty(item + "image")), prop.getProperty(item + "name"));
                        } else {
                            leaf = new ExampleMutableTreeNode(new File(dir, prop.getProperty(item + "file")), prop.getProperty(item + "description"), prop.getProperty(item + "name"));
                        }
                        nodes.add(leaf);
                    }

                }
            }
        } catch (IOException | NumberFormatException exception) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(exception);
        }
        return nodes;
    }

    private void initTreeListner() {
        arvoreExemplos.addTreeSelectionListener((TreeSelectionEvent e) -> {
            atualizarPainelDireita();
        });
        arvoreExemplos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreExemplos.getLastSelectedPathComponent();
                if (e.getKeyCode() == KeyEvent.VK_ENTER && node.isLeaf()) {
                    botaoAbrirExemplo.doClick();
                }
            }
        });
        arvoreExemplos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreExemplos.getLastSelectedPathComponent();
                if (e.getClickCount() == 2 && node.isLeaf()) {
                    botaoAbrirExemplo.doClick();
                }
            }
        });
    }
    
    private void atualizarPainelDireita() {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreExemplos.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        
        Icon icone = imagemPastaPadrao;
        if (node.isLeaf()) {
            try {

                ExampleMutableTreeNode item = (ExampleMutableTreeNode) node;
                File exemplo = item.getFile();
                String codigoFonte = FileHandle.open(exemplo);

                description.setVisible(true);
                description.setText("<html><head></head><body>" + item.getDescription() + "</body></html>");
                
                if (item.hasImage()) {
                    icone = new ImageIcon(item.getImage().getAbsolutePath());
                } 

                editor.setCodigoFonte(codigoFonte);
                editor.rolarAtePosicao(0);
                botaoAbrirExemplo.setAction(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
                        abaCodigoFonte.setCodigoFonte(codigoFonte, exemplo, false);
                        TelaPrincipal t = PortugolStudio.getInstancia().getTelaPrincipal();
                        t.getPainelTabulado().adicionaAba(abaCodigoFonte);
                        buscaExemplo.getCampoBusca().setText("");
                    }
                });
                botaoAbrirExemplo.setText("Explorar Exemplo");
                botaoAbrirExemplo.setVisible(true);
                imagePane.removeAll();
                imagePane.add(imagePanel);
                imagePanel.setImagem(((ImageIcon)icone).getImage());
//                imagePane.setBackground(ColorController.FUNDO_ESCURO);
            } catch (Exception ex) {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
        } 
        else {

            //dataPane.setPreferredSize(new Dimension(painelDireita.getSize().width, 0));
            description.setVisible(false);
            botaoAbrirExemplo.setVisible(false);
            imagePane.removeAll();
//            imagePane.setBackground(ColorController.FUNDO_CLARO);
            imagePane.add(painelRecentesPrincipal);
        }        
//        imagePanel.setImagem(((ImageIcon)icone).getImage());
        
        painelDireita.revalidate();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        painelRecentesPrincipal = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        areaLogo = new javax.swing.JPanel();
        painelRecentesMaster = new javax.swing.JPanel();
        painelRecentes = new javax.swing.JPanel();
        textRecentes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaRecentes = new javax.swing.JPanel();
        painelVersao = new javax.swing.JPanel();
        labelVersao = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        painelDireita = new javax.swing.JPanel();
        staticPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        imagePane = new javax.swing.JPanel();
        descriptionPanel = new javax.swing.JPanel();
        botaoAbrirExemplo = new com.alee.laf.button.WebButton();
        description = new javax.swing.JLabel();
        painelEsquerda = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        scrollArvoreExemplos = new javax.swing.JScrollPane();
        arvoreExemplos = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        labelTitulo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        buscaExemplo = new br.univali.ps.ui.rstautil.tree.SearchTextPanel();

        painelRecentesPrincipal.setLayout(new java.awt.BorderLayout());

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.BorderLayout());

        areaLogo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        areaLogo.setName("areaLogo"); // NOI18N
        areaLogo.setLayout(new java.awt.BorderLayout());
        jPanel5.add(areaLogo, java.awt.BorderLayout.CENTER);

        painelRecentesMaster.setLayout(new java.awt.BorderLayout());

        painelRecentes.setName("painelRecentes"); // NOI18N
        painelRecentes.setOpaque(false);
        painelRecentes.setLayout(new java.awt.BorderLayout());

        textRecentes.setForeground(new java.awt.Color(255, 255, 255));
        textRecentes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textRecentes.setText("Recentes");
        textRecentes.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textRecentes.setName("labelTitulo"); // NOI18N
        textRecentes.setOpaque(true);
        painelRecentes.add(textRecentes, java.awt.BorderLayout.NORTH);

        areaRecentes.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 10, 0));
        areaRecentes.setName("areaRecentes"); // NOI18N
        areaRecentes.setOpaque(false);
        jScrollPane1.setViewportView(areaRecentes);

        painelRecentes.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        painelRecentesMaster.add(painelRecentes, java.awt.BorderLayout.CENTER);

        painelVersao.setName("painelVersao"); // NOI18N
        painelVersao.setOpaque(false);
        painelVersao.setLayout(new java.awt.BorderLayout());

        labelVersao.setName("labelVersao"); // NOI18N
        painelVersao.add(labelVersao, java.awt.BorderLayout.EAST);

        painelRecentesMaster.add(painelVersao, java.awt.BorderLayout.PAGE_END);

        jPanel5.add(painelRecentesMaster, java.awt.BorderLayout.SOUTH);

        painelRecentesPrincipal.add(jPanel5, java.awt.BorderLayout.CENTER);

        setBackground(new java.awt.Color(51, 51, 51));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(1920);
        jSplitPane1.setName("splitPainelExemplos"); // NOI18N

        painelDireita.setBackground(new java.awt.Color(255, 255, 255));
        painelDireita.setName(""); // NOI18N
        painelDireita.setOpaque(false);
        painelDireita.setLayout(new java.awt.BorderLayout());

        staticPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        painelDireita.add(staticPanel, java.awt.BorderLayout.PAGE_START);

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.BorderLayout());

        imagePane.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        imagePane.setLayout(new java.awt.BorderLayout());
        jPanel6.add(imagePane, java.awt.BorderLayout.CENTER);

        descriptionPanel.setLayout(new java.awt.GridBagLayout());

        botaoAbrirExemplo.setVisible(false);
        botaoAbrirExemplo.setText("Explorar Exemplo");
        botaoAbrirExemplo.setName("botaoExplorarExemplo"); // NOI18N
        botaoAbrirExemplo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAbrirExemploActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        descriptionPanel.add(botaoAbrirExemplo, gridBagConstraints);

        description.setVisible(false);
        description.setBackground(new java.awt.Color(51, 51, 51));
        description.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        description.setForeground(new java.awt.Color(255, 255, 255));
        description.setText("Descrição do Exemplo");
        description.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        description.setName("labelDescricao"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        descriptionPanel.add(description, gridBagConstraints);

        jPanel6.add(descriptionPanel, java.awt.BorderLayout.SOUTH);

        painelDireita.add(jPanel6, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(painelDireita);

        painelEsquerda.setOpaque(false);
        painelEsquerda.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        scrollArvoreExemplos.setBorder(null);

        arvoreExemplos.setBackground(new java.awt.Color(240, 240, 240));
        scrollArvoreExemplos.setViewportView(arvoreExemplos);

        jPanel2.add(scrollArvoreExemplos, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.BorderLayout());

        labelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/light_pix.png"))); // NOI18N
        labelTitulo.setText("Exemplos");
        labelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelTitulo.setName("labelTitulo"); // NOI18N
        labelTitulo.setOpaque(true);
        jPanel3.add(labelTitulo, java.awt.BorderLayout.NORTH);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 2, 0));
        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.add(buscaExemplo, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        painelEsquerda.add(jPanel1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(painelEsquerda);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAbrirExemploActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAbrirExemploActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoAbrirExemploActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel areaLogo;
    private javax.swing.JPanel areaRecentes;
    private javax.swing.JTree arvoreExemplos;
    private com.alee.laf.button.WebButton botaoAbrirExemplo;
    private br.univali.ps.ui.rstautil.tree.SearchTextPanel buscaExemplo;
    private javax.swing.JLabel description;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JPanel imagePane;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel labelVersao;
    private javax.swing.JPanel painelDireita;
    private javax.swing.JPanel painelEsquerda;
    private javax.swing.JPanel painelRecentes;
    private javax.swing.JPanel painelRecentesMaster;
    private javax.swing.JPanel painelRecentesPrincipal;
    private javax.swing.JPanel painelVersao;
    private javax.swing.JScrollPane scrollArvoreExemplos;
    private javax.swing.JPanel staticPanel;
    private javax.swing.JLabel textRecentes;
    // End of variables declaration//GEN-END:variables
}
