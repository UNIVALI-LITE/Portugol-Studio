package br.univali.ps.ui;


import br.univali.portugol.nucleo.AnalizadorSemantico;
import br.univali.portugol.nucleo.Interpretador;
import br.univali.portugol.nucleo.excecoes.ListaMensagens;
import br.univali.portugol.nucleo.excecoes.Mensagem;
import br.univali.portugol.nucleo.iu.Entrada;
import br.univali.portugol.nucleo.iu.Saida;
import br.univali.ps.acoes.FabricaAcao;
import br.univali.ps.acoes.AcaoCopiar;
import br.univali.ps.acoes.AcaoRecortar;
import br.univali.ps.acoes.AcaoColar;
import br.univali.ps.acoes.AcaoNovoArquivo;
import br.univali.ps.acoes.AcaoAbrirArquivo;
import br.univali.ps.acoes.AcaoListener;
import br.univali.ps.acoes.AcaoRefazer;
import br.univali.ps.acoes.AcaoSalvarComo;
import br.univali.ps.acoes.AcaoSalvarArquivo;
import br.univali.ps.acoes.AcaoDesfazer;
import br.univali.ps.dominio.PortugolDocument;
import br.univali.ps.exception.NullFileOnSaveExcpetion;
import br.univali.ps.ui.exemplojtable.exemplo1.ModeloExemplo1;
import br.univali.ps.ui.exemplojtable.exemplo2.ModeloExemplo2;
import br.univali.ps.ui.exemplojtable.exemplo2.RenderizadorMensagem;
import br.univali.ps.ui.swing.filtros.FiltroArquivoPortugol;
import br.univali.ps.ui.swing.tabs.Tab;
import br.univali.ps.ui.swing.tabs.TabClosingEvent;
import br.univali.ps.ui.swing.tabs.TabListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Fillipi Pelz
 */
public class MainFrame extends JFrame implements TabListener, AcaoListener, Saida, Entrada
{
    ListMessagesModel model ;
                
    private JFileChooser fileChooser = new JFileChooser();
    private JTabbedPane editorTabs = new JTabbedPane();
    
    private AcaoNovoArquivo acaoNovoArquivo = null;
    private AcaoAbrirArquivo openFileAction = null;
    private AcaoSalvarArquivo saveFileAction = null;
    private AcaoSalvarComo saveAsAction = null;
    private AcaoCopiar editCopyAction = null;
    private AcaoRecortar editCutAction = null;
    private AcaoColar editPasteAction = null;
    private AcaoRefazer redoAction = null;
    private AcaoDesfazer undoAction = null;
    

    private void acoesprontas()
    {
        List<Exception> exceptions = new ArrayList<Exception>();
        exceptions.add(new NullFileOnSaveExcpetion());
        exceptions.add(new Exception("Buteco"));

        acaoNovoArquivo = (AcaoNovoArquivo) FabricaAcao.getInstancia().criarAcao(AcaoNovoArquivo.class);
        acaoNovoArquivo.adicionarListener(this);
        acaoNovoArquivo.setup(editorTabs, this);

        openFileAction = (AcaoAbrirArquivo) FabricaAcao.getInstancia().criarAcao(AcaoAbrirArquivo.class);
        openFileAction.adicionarListener(this);
        openFileAction.configurar(this, fileChooser);

        saveFileAction = (AcaoSalvarArquivo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarArquivo.class);
        saveFileAction.adicionarListener(this);
        saveFileAction.setEnabled(false);

        saveAsAction = (AcaoSalvarComo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarComo.class);
        saveAsAction.adicionarListener(this);
        saveAsAction.setEnabled(false);
        saveAsAction.setup(this, fileChooser);

        btnNew.setAction(acaoNovoArquivo);
        mniNew.setAction(acaoNovoArquivo);
        btnNew.setText("");


        btnOpen.setAction(openFileAction);
        mniOpen.setAction(openFileAction);
        btnOpen.setText("");

        btnSave.setAction(saveFileAction);
        mniSave.setAction(saveFileAction);
        btnSave.setText("");

        mniSaveAs.setAction(saveAsAction);
    }

    private void acoesAindaParaFazer()
    {
        editCopyAction = (AcaoCopiar) FabricaAcao.getInstancia().criarAcao(AcaoCopiar.class);
        editCopyAction.setEnabled(false);
        editCutAction = (AcaoRecortar) FabricaAcao.getInstancia().criarAcao(AcaoRecortar.class);
        editCutAction.setEnabled(false);
        editPasteAction = (AcaoColar) FabricaAcao.getInstancia().criarAcao(AcaoColar.class);
        editPasteAction.setEnabled(false);
        redoAction = (AcaoRefazer) FabricaAcao.getInstancia().criarAcao(AcaoRefazer.class);
        redoAction.setEnabled(false);
        undoAction = (AcaoDesfazer) FabricaAcao.getInstancia().criarAcao(AcaoDesfazer.class);
        undoAction.setEnabled(false);

        mniCut.setAction(editCutAction);
        btnCut.setAction(editCutAction);
        btnCut.setText("");
        mniCopy.setAction(editCopyAction);
        btnCopy.setAction(editCopyAction);
        btnCopy.setText("");
        mniPaste.setAction(editPasteAction);
        btnPaste.setAction(editPasteAction);
        btnPaste.setText("");
        mniUndo.setAction(undoAction);
        btnUndo.setAction(undoAction);
        btnUndo.setText("");
        mniRedo.setAction(redoAction);
        btnRedo.setAction(redoAction);
        btnRedo.setText("");
    }

    public MainFrame()
    {
        this.setIconImage(new ImageIcon(getClass().getResource("icons/small/lightbulb.png")).getImage());
        model = new ListMessagesModel();
        initComponents();
        centralizar();
        this.addComponentListener(new AdaptadorComponente());
        configurarSeletorArquivo();

        acoesprontas();

        acoesAindaParaFazer();

        editorTabs.addChangeListener(new ChangeTabListener());
        editorTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        jSplitPane1.setLeftComponent(editorTabs);
        bottomPane.setLayout(new BorderLayout());

        console.setComponentPopupMenu(menuConsole);
        console.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                atualizarItensMenuConsole();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                atualizarItensMenuConsole();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                
            }
        });
        
    }

    private void atualizarItensMenuConsole()
    {
        if (console.getText().length() > 0)
        {
            menuConsoleLimpar.setEnabled(true);

            int selecao = console.getSelectionEnd() - console.getSelectionStart();

            if (selecao > 0)
                menuConsoleCopiar.setEnabled(true);

            else menuConsoleCopiar.setEnabled(false);
        }
        else
        {
            menuConsoleLimpar.setEnabled(false);
            menuConsoleCopiar.setEnabled(false);
        }
    }

    public String getTextOfSelecteTab()
    {
        String strReturn = null;
        if (editorTabs.getTabCount() > 0)
        {
            PortugolDocument p = ((Tab) editorTabs.getSelectedComponent()).getPortugolDocument();
            try
            {
                strReturn = p.getText(0, p.getLength());
            } catch (BadLocationException ex)
            {
                MainFrame.this.showError(ex.getMessage(), "Não foi possivel recuperar texto do editor");
            }
        }
        return strReturn;
    }

    public void showError(String msg, String title)
    {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    //ActionListener
    @Override
    public void acaoExecutadaSucesso(br.univali.ps.acoes.Acao action, String message)
    {
        if (action == openFileAction)
        {
            Tab tab = new Tab(editorTabs, ((AcaoAbrirArquivo) action).getTituloArquivo());
            tab.addTabListener(this);
            tab.getTextArea().setText(((AcaoAbrirArquivo) action).getTextoArquivo());
            editorTabs.add(tab);
            saveFileAction.setup(openFileAction.getFile(), openFileAction.getTextoArquivo());
            editorTabs.setSelectedIndex(editorTabs.indexOfComponent(tab));
            btnCompile.setEnabled(true);
        } else if (action == saveAsAction)
        {            
            saveFileAction.setup(((AcaoSalvarComo) action).getFile(), getTextOfSelecteTab());
            saveFileAction.actionPerformed(null);
        } else if (action == saveFileAction)
        {
            ((Tab) editorTabs.getSelectedComponent()).setTitle(saveFileAction.getFile().getName());
            ((Tab) editorTabs.getSelectedComponent()).getPortugolDocument().setChanged(false);
            ((Tab) editorTabs.getSelectedComponent()).getPortugolDocument().setFile(saveFileAction.getFile());
            saveFileAction.setEnabled(false);
        }

        else if (action  ==  acaoNovoArquivo)
        {
            btnCompile.setEnabled(true);
        }
    }

    @Override
    public void acaoFalhou(br.univali.ps.acoes.Acao action, Exception reason)
    {
        if (action instanceof AcaoSalvarArquivo)
        {
            if (reason instanceof NullFileOnSaveExcpetion)
            {
                saveAsAction.actionPerformed(null);
                return;
            }
        }

        else

        if ((action == saveAsAction) || (action == openFileAction))
        {

        }

        else
        showError(reason.getMessage(), action.toString());
    }

    //TAB Listener:
    @Override
    public void tabClosing(TabClosingEvent evt)
    {
        if (((Tab)editorTabs.getSelectedComponent()).getPortugolDocument().isChanged()){
            int resp = JOptionPane.showConfirmDialog(MainFrame.this, "O documento possui modificações, deseja Salva-las?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);
            if ( resp == JOptionPane.YES_OPTION)
            {
                saveFileAction.actionPerformed(null);
                evt.setCanClose(true);
            }
            else if(resp == JOptionPane.NO_OPTION)
            {
                evt.setCanClose(true);
            }
            else
            {
                evt.setCanClose(false);
            }
        } else {
            evt.setCanClose(true);
        }
        
    }

    @Override
    public void limpar()
    {
        console.setText(null);
    }

    @Override
    public void imprimir(String valor)
    {
        console.append(valor);
    }

    @Override
    public String ler()
    {
        return JOptionPane.showInputDialog(this, "Digite um valor:", null);
    }

    private void configurarSeletorArquivo()
    {
        //TODO: Permitir abrir mais de um arquivo por vez?
        //fileChooser.setMultiSelectionEnabled(false);

        fileChooser.addChoosableFileFilter(new FiltroArquivoPortugol());
        fileChooser.setAcceptAllFileFilterUsed(false); // Desativar filtro curinga
    }

    private class ChangeTabListener implements ChangeListener
    {

        @Override
        public void stateChanged(ChangeEvent e)
        {
            if (editorTabs.getTabCount() > 0)
            {
                saveAsAction.setEnabled(true);
                Tab tab = (Tab) editorTabs.getSelectedComponent();
                File f = (File) tab.getPortugolDocument().getFile();
                saveFileAction.setup(f, getTextOfSelecteTab());
                saveFileAction.setEnabled(tab.getPortugolDocument().isChanged());
                undoAction.setup();
                redoAction.setup();
                editCopyAction.configurar();
                editPasteAction.configurar();
                tab.getTextArea().addFocusListener(editPasteAction);
                editCutAction.setup();
            } else
            {
                saveAsAction.setEnabled(false);
                editPasteAction.setEnabled(false);
                redoAction.setEnabled(false);
                undoAction.setEnabled(false);
            }

        }
    }
    /*--------------------------------------------------------------------------*/

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuConsole = new javax.swing.JPopupMenu();
        menuConsoleLimpar = new javax.swing.JMenuItem();
        menuConsoleCopiar = new javax.swing.JMenuItem();
        topPane = new javax.swing.JPanel();
        fileBar = new javax.swing.JToolBar();
        btnNew = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        undoRedoBar = new javax.swing.JToolBar();
        btnUndo = new javax.swing.JButton();
        btnRedo = new javax.swing.JButton();
        editBar = new javax.swing.JToolBar();
        btnCut = new javax.swing.JButton();
        btnCopy = new javax.swing.JButton();
        btnPaste = new javax.swing.JButton();
        compileBar = new javax.swing.JToolBar();
        btnCompile = new javax.swing.JButton();
        btnDebug = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        painelSaida = new javax.swing.JTabbedPane();
        jScrollPaneConsole = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        jScrollPaneTabelaMensagens = new javax.swing.JScrollPane();
        tabelaMensagens = new javax.swing.JTable();
        bottomPane = new javax.swing.JPanel();
        mnuBar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mniNew = new javax.swing.JMenuItem();
        mniOpen = new javax.swing.JMenuItem();
        mniSave = new javax.swing.JMenuItem();
        mniSaveAs = new javax.swing.JMenuItem();
        mnuFileSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniClose = new javax.swing.JMenuItem();
        mniCloseAll = new javax.swing.JMenuItem();
        mnuFileSeparator2 = new javax.swing.JSeparator();
        mniExit = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();
        mniUndo = new javax.swing.JMenuItem();
        mniRedo = new javax.swing.JMenuItem();
        mnuEditSeparator1 = new javax.swing.JSeparator();
        mniCut = new javax.swing.JMenuItem();
        mniCopy = new javax.swing.JMenuItem();
        mniPaste = new javax.swing.JMenuItem();
        mnuEditSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniFindReplace = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mniAbout = new javax.swing.JMenuItem();

        menuConsoleLimpar.setText("Limpar");
        menuConsoleLimpar.setEnabled(false);
        menuConsoleLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsoleLimparActionPerformed(evt);
            }
        });
        menuConsole.add(menuConsoleLimpar);

        menuConsoleCopiar.setText("Copiar");
        menuConsoleCopiar.setEnabled(false);
        menuConsoleCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsoleCopiarActionPerformed(evt);
            }
        });
        menuConsole.add(menuConsoleCopiar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Portugol Studio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        topPane.setLayout(new javax.swing.BoxLayout(topPane, javax.swing.BoxLayout.LINE_AXIS));

        fileBar.setToolTipText("Barra ferramentas arquivo");

        btnNew.setText("");
        btnNew.setDoubleBuffered(true);
        btnNew.setFocusable(false);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fileBar.add(btnNew);

        btnOpen.setText("");
        btnOpen.setActionCommand("Abrir");
        btnOpen.setFocusable(false);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fileBar.add(btnOpen);

        btnSave.setText("");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fileBar.add(btnSave);

        topPane.add(fileBar);

        btnUndo.setText("");
        btnUndo.setFocusable(false);
        btnUndo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUndo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoRedoBar.add(btnUndo);

        btnRedo.setText("");
        btnRedo.setFocusable(false);
        btnRedo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRedo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoRedoBar.add(btnRedo);

        topPane.add(undoRedoBar);

        btnCut.setFocusable(false);
        btnCut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editBar.add(btnCut);

        btnCopy.setFocusable(false);
        btnCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editBar.add(btnCopy);

        btnPaste.setFocusable(false);
        btnPaste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPaste.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editBar.add(btnPaste);

        topPane.add(editBar);

        compileBar.setRollover(true);

        btnCompile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icons/large/control_play_blue.png"))); // NOI18N
        btnCompile.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icons/large/control_play.png"))); // NOI18N
        btnCompile.setEnabled(false);
        btnCompile.setFocusable(false);
        btnCompile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompileActionPerformed(evt);
            }
        });
        compileBar.add(btnCompile);

        btnDebug.setFocusable(false);
        btnDebug.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDebug.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        compileBar.add(btnDebug);

        topPane.add(compileBar);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setRequestFocusEnabled(false);

        console.setColumns(20);
        console.setEditable(false);
        console.setRows(5);
        jScrollPaneConsole.setViewportView(console);

        painelSaida.addTab("Console", jScrollPaneConsole);

        tabelaMensagens.setModel(model);
        jScrollPaneTabelaMensagens.setViewportView(tabelaMensagens);

        painelSaida.addTab("Mensagens", jScrollPaneTabelaMensagens);

        jSplitPane1.setRightComponent(painelSaida);

        javax.swing.GroupLayout bottomPaneLayout = new javax.swing.GroupLayout(bottomPane);
        bottomPane.setLayout(bottomPaneLayout);
        bottomPaneLayout.setHorizontalGroup(
            bottomPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 632, Short.MAX_VALUE)
        );
        bottomPaneLayout.setVerticalGroup(
            bottomPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        mnuFile.setText("Arquivo");
        mnuFile.add(mniNew);
        mnuFile.add(mniOpen);
        mnuFile.add(mniSave);
        mnuFile.add(mniSaveAs);
        mnuFile.add(mnuFileSeparator1);

        mniClose.setText("Fechar");
        mnuFile.add(mniClose);

        mniCloseAll.setText("Fechar todos.");
        mnuFile.add(mniCloseAll);
        mnuFile.add(mnuFileSeparator2);

        mniExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mniExit.setText("Sair");
        mnuFile.add(mniExit);

        mnuBar.add(mnuFile);

        mnuEdit.setText("Editar");

        mniUndo.setText("desfazer");
        mnuEdit.add(mniUndo);

        mniRedo.setText("refazer");
        mnuEdit.add(mniRedo);
        mnuEdit.add(mnuEditSeparator1);

        mniCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mniCut.setText("Recortar");
        mnuEdit.add(mniCut);

        mniCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mniCopy.setText("Copiar");
        mnuEdit.add(mniCopy);

        mniPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        mniPaste.setText("Colar");
        mnuEdit.add(mniPaste);
        mnuEdit.add(mnuEditSeparator2);

        mniFindReplace.setText("Procurar e substituir");
        mnuEdit.add(mniFindReplace);

        mnuBar.add(mnuEdit);

        mnuHelp.setText("Ajuda");

        mniAbout.setText("Sobre");
        mnuHelp.add(mniAbout);

        mnuBar.add(mnuHelp);

        setJMenuBar(mnuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topPane, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
            .addComponent(bottomPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(topPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompileActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCompileActionPerformed
    {//GEN-HEADEREND:event_btnCompileActionPerformed
        try
        {
            if (saveFileAction.getFile() != null)
            {
                btnCompile.setEnabled(false);
                saveFileAction.actionPerformed(null);
                console.setText(null);
                painelSaida.setSelectedIndex(0);

                AnalizadorSemantico analizadorSemantico = new AnalizadorSemantico(saveFileAction.getFile());
                ListaMensagens listaMensagens = analizadorSemantico.analizar();

                
                 
                 ModeloExemplo1 modelo = new ModeloExemplo1();                
                 /*
                ModeloExemplo2 modelo = new ModeloExemplo2();
                tabelaMensagens.setDefaultRenderer(Mensagem.class, new RenderizadorMensagem());
                */

                tabelaMensagens.setModel(modelo);
                modelo.adicionar(listaMensagens);


                if (listaMensagens.getNumeroErros() == 0)
                {                    
                    long horaInicial = System.currentTimeMillis();

                    Interpretador interpretador = new Interpretador();
                    interpretador.setSaida(this);
                    interpretador.setEntrada(this);
                    interpretador.interpretar(saveFileAction.getFile(), new String[]{"teste"});

                    long tempo = (System.currentTimeMillis() - horaInicial) / 1000;
                    console.append("\n\nPrograma executado com sucesso! Tempo de execução: " + tempo + " segundos");
                }
                else painelSaida.setSelectedIndex(1);

                btnCompile.setEnabled(true);
            }
            else saveFileAction.actionPerformed(null);
        }
        catch(Exception ex)
        {
            showError(ex.getMessage(), "Portugol Studio");
            btnCompile.setEnabled(true);
        }
    }//GEN-LAST:event_btnCompileActionPerformed

    private void menuConsoleLimparActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuConsoleLimparActionPerformed
    {//GEN-HEADEREND:event_menuConsoleLimparActionPerformed
        console.setText(null);
    }//GEN-LAST:event_menuConsoleLimparActionPerformed

    private void menuConsoleCopiarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuConsoleCopiarActionPerformed
    {//GEN-HEADEREND:event_menuConsoleCopiarActionPerformed
        console.copy();
    }//GEN-LAST:event_menuConsoleCopiarActionPerformed
    //Converter em action.    // <editor-fold defaultstate="collapsed" desc="IDE Declaration Code">
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPane;
    private javax.swing.JButton btnCompile;
    private javax.swing.JButton btnCopy;
    private javax.swing.JButton btnCut;
    private javax.swing.JButton btnDebug;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnPaste;
    private javax.swing.JButton btnRedo;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUndo;
    private javax.swing.JToolBar compileBar;
    private javax.swing.JTextArea console;
    private javax.swing.JToolBar editBar;
    private javax.swing.JToolBar fileBar;
    private javax.swing.JScrollPane jScrollPaneConsole;
    private javax.swing.JScrollPane jScrollPaneTabelaMensagens;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPopupMenu menuConsole;
    private javax.swing.JMenuItem menuConsoleCopiar;
    private javax.swing.JMenuItem menuConsoleLimpar;
    private javax.swing.JMenuItem mniAbout;
    private javax.swing.JMenuItem mniClose;
    private javax.swing.JMenuItem mniCloseAll;
    private javax.swing.JMenuItem mniCopy;
    private javax.swing.JMenuItem mniCut;
    private javax.swing.JMenuItem mniExit;
    private javax.swing.JMenuItem mniFindReplace;
    private javax.swing.JMenuItem mniNew;
    private javax.swing.JMenuItem mniOpen;
    private javax.swing.JMenuItem mniPaste;
    private javax.swing.JMenuItem mniRedo;
    private javax.swing.JMenuItem mniSave;
    private javax.swing.JMenuItem mniSaveAs;
    private javax.swing.JMenuItem mniUndo;
    private javax.swing.JMenuBar mnuBar;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JSeparator mnuEditSeparator1;
    private javax.swing.JPopupMenu.Separator mnuEditSeparator2;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JPopupMenu.Separator mnuFileSeparator1;
    private javax.swing.JSeparator mnuFileSeparator2;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JTabbedPane painelSaida;
    private javax.swing.JTable tabelaMensagens;
    private javax.swing.JPanel topPane;
    private javax.swing.JToolBar undoRedoBar;
    // End of variables declaration//GEN-END:variables
// </editor-fold>


    private void centralizar()
    {
        Dimension dimensoesTela = Toolkit.getDefaultToolkit().getScreenSize();

        int centroX = (dimensoesTela.width / 2) - (getWidth() / 2);
        int centroY = (dimensoesTela.height / 2) - (getHeight() / 2);

        setLocation(centroX, centroY);
    }

    private class AdaptadorComponente extends ComponentAdapter
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            jSplitPane1.setDividerLocation(MainFrame.this.getHeight() - 300);
        }
    }
}
