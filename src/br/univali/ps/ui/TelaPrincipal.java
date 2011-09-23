package br.univali.ps.ui;

import br.univali.ps.controller.PortugolControlador;
import br.univali.ps.ui.acoes.Acao;
import br.univali.ps.ui.acoes.FabricaAcao;
import br.univali.ps.ui.acoes.AcaoCopiar;
import br.univali.ps.ui.acoes.AcaoRecortar;
import br.univali.ps.ui.acoes.AcaoColar;
import br.univali.ps.ui.acoes.AcaoNovoArquivo;
import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.ui.acoes.AcaoListener;
import br.univali.ps.ui.acoes.AcaoRefazer;
import br.univali.ps.ui.acoes.AcaoSalvarComo;
import br.univali.ps.ui.acoes.AcaoSalvarArquivo;
import br.univali.ps.ui.acoes.AcaoDesfazer;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.ajuda.NavegadorAjuda;
import br.univali.ps.ui.swing.ResultadoAnaliseTableModel;
import br.univali.ps.ui.swing.filtro.FiltroArquivoPortugol;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaPrincipal extends JFrame implements WindowListener, AcaoListener{

    ResultadoAnaliseTableModel model;
    private JFileChooser fileChooser = new JFileChooser();
    private AcaoNovoArquivo acaoNovoArquivo = null;
    private AcaoAbrirArquivo openFileAction = null;
    private AcaoSalvarArquivo saveFileAction = null;
    private AcaoSalvarComo saveAsAction = null;
    private AcaoCopiar editCopyAction = null;
    private AcaoRecortar editCutAction = null;
    private AcaoColar editPasteAction = null;
    private AcaoRefazer redoAction = null;
    private AcaoDesfazer undoAction = null;
    private PortugolControlador controle = null;
 
    
    
    private void acoesprontas() {
        acaoNovoArquivo = (AcaoNovoArquivo) FabricaAcao.getInstancia().criarAcao(AcaoNovoArquivo.class);
        acaoNovoArquivo.adicionarListener(this);
        acaoNovoArquivo.setup(controle);

        openFileAction = (AcaoAbrirArquivo) FabricaAcao.getInstancia().criarAcao(AcaoAbrirArquivo.class);
        openFileAction.adicionarListener(this);
        openFileAction.configurar(controle,this, fileChooser);

        saveFileAction = (AcaoSalvarArquivo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarArquivo.class);
       /// saveFileAction.configurar(controle);
        saveFileAction.adicionarListener(this);
        saveFileAction.setEnabled(false);

        saveAsAction = (AcaoSalvarComo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarComo.class);
        saveAsAction.adicionarListener(this);
        saveAsAction.setEnabled(false);
        saveAsAction.setup(controle,this, fileChooser);

        //btnNew.setAction(acaoNovoArquivo);
        mniNew.setAction(acaoNovoArquivo);
        //btnNew.setText("");


        //btnOpen.setAction(openFileAction);
        mniOpen.setAction(openFileAction);
        //btnOpen.setText("");

        //btnSave.setAction(saveFileAction);
        mniSave.setAction(saveFileAction);
 //       btnSave.setText("");

        mniSaveAs.setAction(saveAsAction);
    }

    private void acoesAindaParaFazer() {
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
  //      btnCut.setAction(editCutAction);
  //      btnCut.setText("");
        mniCopy.setAction(editCopyAction);
  //      btnCopy.setAction(editCopyAction);
  //      btnCopy.setText("");
        mniPaste.setAction(editPasteAction);
  //      btnPaste.setAction(editPasteAction);
  //      btnPaste.setText("");
        mniUndo.setAction(undoAction);
  //      btnUndo.setAction(undoAction);
  //      btnUndo.setText("");
        mniRedo.setAction(redoAction);
  //      btnRedo.setAction(redoAction);
  //      btnRedo.setText("");
    }

    public TelaPrincipal(PortugolControlador controle) {
        this.controle = controle;
        this.setIconImage(new ImageIcon(getClass().getResource("icones/pequeno/lightbulb.png")).getImage());
        model = new ResultadoAnaliseTableModel();
        initComponents();
        this.setLocationRelativeTo(null);
        this.addComponentListener(new AdaptadorComponente());
        configurarSeletorArquivo();
        this.addWindowListener(this);
        Action action = new AbstractAction() {

            public void actionPerformed(ActionEvent ae) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        //this.painelTabulado1.init(action, action);
        
        // Configurar o jfilechooser para iniciar na pasta de exemplos
        fileChooser.setCurrentDirectory(new File("./exemplos"));
        
        acoesprontas();

        acoesAindaParaFazer();

        bottomPane.setLayout(new BorderLayout());
       
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        if (!PortugolStudio.getInstancia().isDepurando())
        {
  //          btnAlgoritmoTeste.setVisible(false);            
        }
    }
    

    private void configurarSeletorArquivo() {
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.addChoosableFileFilter(new FiltroArquivoPortugol());
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
        controle.fecharAplicativo();
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }
    

    public void setEditor(JPanel editor) {
    //    jSplitPane1.setLeftComponent(editor);
    }

    public void setPainelSaida(JPanel saida) {
     //   jSplitPane1.setRightComponent(saida);
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

        bottomPane = new javax.swing.JPanel();
        painelTabulado1 = new br.univali.ps.ui.PainelTabulado();
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
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Portugol Studio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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
        mniClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCloseActionPerformed(evt);
            }
        });
        mnuFile.add(mniClose);

        mniCloseAll.setText("Fechar todos.");
        mniCloseAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCloseAllActionPerformed(evt);
            }
        });
        mnuFile.add(mniCloseAll);
        mnuFile.add(mnuFileSeparator2);

        mniExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        mniExit.setText("Sair");
        mniExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniExitActionPerformed(evt);
            }
        });
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

        jMenuItem1.setText("Tópicos de Ajuda");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mnuHelp.add(jMenuItem1);

        mnuBar.add(mnuHelp);

        setJMenuBar(mnuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bottomPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelTabulado1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(painelTabulado1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    NavegadorAjuda hb = new NavegadorAjuda();
    hb.setSize(800,600);
    hb.setVisible(true);
    hb.setLocationRelativeTo(this);
}//GEN-LAST:event_jMenuItem1ActionPerformed

private void mniExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniExitActionPerformed
    controle.fecharAplicativo();
}//GEN-LAST:event_mniExitActionPerformed

private void mniCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCloseActionPerformed
    controle.fecharAbaAtual();
}//GEN-LAST:event_mniCloseActionPerformed

private void mniCloseAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCloseAllActionPerformed
    controle.fecharTodasAbas();
}//GEN-LAST:event_mniCloseAllActionPerformed
    //Converter em action.    // <editor-fold defaultstate="collapsed" desc="IDE Declaration Code">
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPane;
    private javax.swing.JMenuItem jMenuItem1;
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
    private br.univali.ps.ui.PainelTabulado painelTabulado1;
    // End of variables declaration//GEN-END:variables

    public void acaoExecutadaSucesso(Acao acao, String mensagem) {
  //      throw new UnsupportedOperationException("Not supported yet.");
    }

    public void acaoFalhou(Acao acao, Exception motivoE) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void habilitaSalvar(boolean b) {
        saveFileAction.setEnabled(b);
    }

    public void dialogoSalvar() {
        saveAsAction.actionPerformed(null);
    }

    public void habilitaCompilar(boolean b) {
//        btnCompile.setEnabled(b);
    }

    public void habilitarDebug(boolean b) {
 //       btnDebug.setEnabled(b);
    }


    public Acao getAcaoColar(){
        return editPasteAction;
    }

    public void configurarBotoesEditar() {
        undoAction.iniciar();
        redoAction.iniciar();
        editCopyAction.iniciar();
     //   editPasteAction.configurar();
        editCutAction.iniciar();
    }

    public void habilitaSalvarComo(boolean b) {
        saveAsAction.setEnabled(b);
    }

    public void desabilitarBotoesEditar() {
        undoAction.setEnabled(false);
        redoAction.setEnabled(false);
        editCopyAction.setEnabled(false);
        editPasteAction.setEnabled(false);
        editCutAction.setEnabled(false);
    }
    // End of variables declaration
// </editor-fold>

    private class AdaptadorComponente extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
          //  jSplitPane1.setDividerLocation(TelaPrincipal.this.getHeight() - 300);
        }
    }
    
}
