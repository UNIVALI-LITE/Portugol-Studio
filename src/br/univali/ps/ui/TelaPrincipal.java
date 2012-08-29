package br.univali.ps.ui;

import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.ui.acoes.FabricaAcao;
import br.univali.ps.ui.acoes.AcaoNovoArquivo;
import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.ui.acoes.AcaoSalvarComo;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.swing.filtro.FiltroArquivoExercicio;
import br.univali.ps.ui.swing.filtro.FiltroArquivoPortugol;
import br.univali.ps.ui.telas.TelaSobre;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class TelaPrincipal extends JFrame implements PainelTabuladoListener, Thread.UncaughtExceptionHandler
{   
    private JFileChooser dialogoEscolhaArquivo = new JFileChooser();
    private AcaoNovoArquivo acaoNovoArquivo = null;
    private AcaoAbrirArquivo abrirArquivo = null;
    private AcaoSalvarComo acaoSalvarComo = null;
 
    private Action acaoSelecionarAbaDireita = null;
    private Action acaoSelecionarAbaEsquerda = null;
 
    
    
    private void acoesprontas() {
        acaoNovoArquivo = (AcaoNovoArquivo) FabricaAcao.getInstancia().criarAcao(AcaoNovoArquivo.class);
        acaoNovoArquivo.configurar(painelTabulado);

        abrirArquivo = (AcaoAbrirArquivo) FabricaAcao.getInstancia().criarAcao(AcaoAbrirArquivo.class);
        abrirArquivo.configurar(painelTabulado, this, dialogoEscolhaArquivo);
        
        acaoSalvarComo = (AcaoSalvarComo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarComo.class);
        acaoSalvarComo.setEnabled(false);
       
        mniNovo.setAction(acaoNovoArquivo);
        mniAbrir.setAction(abrirArquivo);
        mniSalvarComo.setAction(acaoSalvarComo);
    }

    public TelaPrincipal()  
    {
        Thread.setDefaultUncaughtExceptionHandler(this);
        
        try {
        this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS +"/light-bulb-code.png")));
        } catch (IOException ioe) {} 
        initComponents();
        mniFechar.setEnabled(false);
        mniFecharTodos.setEnabled(false);
        mnuEdit.setEnabled(false);
        painelTabulado.adicionaPainelTabuladoListener(this);
        this.setLocationRelativeTo(null);
        configurarSeletorArquivo();
        this.addWindowListener(new TelaPrincipalListener());
        mnuPrograma.setVisible(false);
        mnuPrograma.setEnabled(false);
        dialogoEscolhaArquivo.setCurrentDirectory(new File("./exemplos"));
        
        acoesprontas();

        this.painelTabulado.init(abrirArquivo, acaoNovoArquivo);
       
        bottomPane.setLayout(new BorderLayout());
       
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        if (!PortugolStudio.getInstancia().isDepurando())
        {
            //TODO ACHAR UM LUGAR PARA ESSE BOTÃO
    //        btnAlgoritmoTeste.setVisible(false);            
        }
        
        acaoSelecionarAbaEsquerda = new AcaoSelecionarAbaEsquerda();
        getRootPane().getActionMap().put("SelecionarEsquerda", acaoSelecionarAbaEsquerda);
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("shift alt LEFT"), "SelecionarEsquerda");
        
        acaoSelecionarAbaDireita = new AcaoSelecionarAbaDireita();
        getRootPane().getActionMap().put("SelecionarDireita", acaoSelecionarAbaDireita);
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("shift alt RIGHT"), "SelecionarDireita");
    }
    
    private void configurarSeletorArquivo() {
        dialogoEscolhaArquivo.setMultiSelectionEnabled(true);
        dialogoEscolhaArquivo.addChoosableFileFilter(new FiltroArquivoExercicio());
        dialogoEscolhaArquivo.addChoosableFileFilter(new FiltroArquivoPortugol());
        dialogoEscolhaArquivo.setAcceptAllFileFilterUsed(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bottomPane = new javax.swing.JPanel();
        painelTabulado = new br.univali.ps.ui.PainelTabulado();
        mnuBar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mniNovo = new javax.swing.JMenuItem();
        mniAbrir = new javax.swing.JMenuItem();
        mniSalvar = new javax.swing.JMenuItem();
        mniSalvarComo = new javax.swing.JMenuItem();
        mnuFileSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniFechar = new javax.swing.JMenuItem();
        mniFecharTodos = new javax.swing.JMenuItem();
        mnuFileSeparator2 = new javax.swing.JSeparator();
        mniExit = new javax.swing.JMenuItem();
        mnuPrograma = new javax.swing.JMenu();
        mniExecutar = new javax.swing.JMenuItem();
        mniInterromper = new javax.swing.JMenuItem();
        mnuEdit = new javax.swing.JMenu();
        mniDesfazer = new javax.swing.JMenuItem();
        mniRefazer = new javax.swing.JMenuItem();
        mnuEditSeparator1 = new javax.swing.JSeparator();
        mniRecortar = new javax.swing.JMenuItem();
        mniCopiar = new javax.swing.JMenuItem();
        mniColar = new javax.swing.JMenuItem();
        mnuEditSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniSubstituir = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenu();
        mniAbout = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        mniNovo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnuFile.add(mniNovo);

        mniAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        mnuFile.add(mniAbrir);

        mniSalvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mniSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/disk.png"))); // NOI18N
        mniSalvar.setText("Salvar");
        mniSalvar.setEnabled(false);
        mnuFile.add(mniSalvar);

        mniSalvarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mnuFile.add(mniSalvarComo);
        mnuFile.add(mnuFileSeparator1);

        mniFechar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mniFechar.setText("Fechar esta aba");
        mniFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFecharActionPerformed(evt);
            }
        });
        mnuFile.add(mniFechar);

        mniFecharTodos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        mniFecharTodos.setText("Fechar todas as abas");
        mniFecharTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniFecharTodosActionPerformed(evt);
            }
        });
        mnuFile.add(mniFecharTodos);
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

        mnuPrograma.setText("Programa");

        mniExecutar.setText("Executar");
        mnuPrograma.add(mniExecutar);

        mniInterromper.setText("Interromper");
        mnuPrograma.add(mniInterromper);

        mnuBar.add(mnuPrograma);

        mnuEdit.setText("Editar");

        mniDesfazer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        mniDesfazer.setText("Desfazer");
        mnuEdit.add(mniDesfazer);

        mniRefazer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        mniRefazer.setText("Refazer");
        mnuEdit.add(mniRefazer);
        mnuEdit.add(mnuEditSeparator1);

        mniRecortar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mniRecortar.setText("Recortar");
        mnuEdit.add(mniRecortar);

        mniCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mniCopiar.setText("Copiar");
        mnuEdit.add(mniCopiar);

        mniColar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        mniColar.setText("Colar");
        mnuEdit.add(mniColar);
        mnuEdit.add(mnuEditSeparator2);

        mniSubstituir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        mniSubstituir.setText("Localizar e Substituir...");
        mniSubstituir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSubstituirActionPerformed(evt);
            }
        });
        mnuEdit.add(mniSubstituir);

        mnuBar.add(mnuEdit);

        mnuHelp.setText("Ajuda");

        mniAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        mniAbout.setText("Sobre");
        mniAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAboutActionPerformed(evt);
            }
        });
        mnuHelp.add(mniAbout);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
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
                .addComponent(painelTabulado, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(painelTabulado, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    new AbaAjuda(painelTabulado);
}//GEN-LAST:event_jMenuItem1ActionPerformed

private void mniExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniExitActionPerformed
    fecharAplicativo();
}//GEN-LAST:event_mniExitActionPerformed

private void mniFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFecharActionPerformed
     Aba aba = painelTabulado.getAbaSelecionada();
     if (aba != null && !(aba.getClass() == AbaInicial.class)) {
        aba.fechar();
     }
}//GEN-LAST:event_mniFecharActionPerformed

private void mniFecharTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFecharTodosActionPerformed
    for (Class<? extends Aba> classeAba: Aba.classesFilhas())
    {
        if (classeAba != AbaInicial.class)
            painelTabulado.fecharTodasAbas(classeAba);
    }  
}//GEN-LAST:event_mniFecharTodosActionPerformed

private TelaSobre telaSobre = new TelaSobre(this);
private void mniAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAboutActionPerformed
    telaSobre.setModal(true);
    telaSobre.setVisible(true);    
}//GEN-LAST:event_mniAboutActionPerformed

    private void mniSubstituirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSubstituirActionPerformed
        ((AbaCodigoFonte) painelTabulado.getAbaSelecionada()).exibirPainelPesquisa();
    }//GEN-LAST:event_mniSubstituirActionPerformed
    //Converter em action.    // <editor-fold defaultstate="collapsed" desc="IDE Declaration Code">
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPane;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem mniAbout;
    private javax.swing.JMenuItem mniAbrir;
    private javax.swing.JMenuItem mniColar;
    private javax.swing.JMenuItem mniCopiar;
    private javax.swing.JMenuItem mniDesfazer;
    private javax.swing.JMenuItem mniExecutar;
    private javax.swing.JMenuItem mniExit;
    private javax.swing.JMenuItem mniFechar;
    private javax.swing.JMenuItem mniFecharTodos;
    private javax.swing.JMenuItem mniInterromper;
    private javax.swing.JMenuItem mniNovo;
    private javax.swing.JMenuItem mniRecortar;
    private javax.swing.JMenuItem mniRefazer;
    private javax.swing.JMenuItem mniSalvar;
    private javax.swing.JMenuItem mniSalvarComo;
    private javax.swing.JMenuItem mniSubstituir;
    private javax.swing.JMenuBar mnuBar;
    private javax.swing.JMenu mnuEdit;
    private javax.swing.JSeparator mnuEditSeparator1;
    private javax.swing.JPopupMenu.Separator mnuEditSeparator2;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JPopupMenu.Separator mnuFileSeparator1;
    private javax.swing.JSeparator mnuFileSeparator2;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenu mnuPrograma;
    private br.univali.ps.ui.PainelTabulado painelTabulado;
    // End of variables declaration//GEN-END:variables

    public void dialogoSalvar() {
        acaoSalvarComo.actionPerformed(null);
    }

    public void configurarBotoesEditar() {
    }
    
    private void fecharAplicativo() 
    {
        painelTabulado.fecharTodasAbas(AbaCodigoFonte.class);

        if (!painelTabulado.temAbaAberta(AbaCodigoFonte.class))
            System.exit(0); 
    }

    @Override
    public void abaSelecionada(Aba aba) 
    {
        if (aba.getClass() == AbaCodigoFonte.class) {
            AbaCodigoFonte abaCodigoFonte = (AbaCodigoFonte) aba;
            mniSalvar.setAction(abaCodigoFonte.getAcaoSalvarArquivo());
            acaoSalvarComo.configurar(abaCodigoFonte.getAcaoSalvarArquivo(), this, dialogoEscolhaArquivo);
            mnuEdit.setEnabled(true);
            
            mniDesfazer.setAction(abaCodigoFonte.getAcaoDesfazer());
            mniRefazer.setAction(abaCodigoFonte.getAcaoRefazer());
            mniCopiar.setAction(abaCodigoFonte.getAcaoCopiar());
            mniColar.setAction(abaCodigoFonte.getAcaoColar());
            mniRecortar.setAction(abaCodigoFonte.getAcaoRecortar());
            acaoSalvarComo.setEnabled(true);
            
            mniExecutar.setAction(abaCodigoFonte.getAcaoExecutar());
            mniInterromper.setAction(abaCodigoFonte.getAcaoInterromper());
            mnuPrograma.setVisible(true);
            mnuPrograma.setEnabled(true);
            
        } else {
            mniSalvar.setEnabled(false);
            acaoSalvarComo.setEnabled(false);
            mnuEdit.setEnabled(false);
            mnuPrograma.setVisible(false);
            mnuPrograma.setEnabled(false);
        }
        
        if (painelTabulado.temAbaAberta(AbaCodigoFonte.class) || 
                painelTabulado.temAbaAberta(AbaAjuda.class) ){
            mniFechar.setEnabled(true);
            mniFecharTodos.setEnabled(true);
        } else {
            mniFechar.setEnabled(false);
            mniFecharTodos.setEnabled(false);
        }
    }
        
    
    private class TelaPrincipalListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent we) {
            fecharAplicativo();
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) 
    {
        if ((e instanceof ClassNotFoundException) || (e instanceof NoClassDefFoundError))
        {
            String mensagem = "Uma das bibliotecas ou classes necessárias para o funcionamento do PortugolStudio não foi encontrada.\nO PortugolStudio será enecerrado.";
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(mensagem, e, ExcecaoAplicacao.Tipo.ERRO));
            System.exit(1);
        }
        else if (e instanceof IllegalArgumentException){e.printStackTrace();}
        else 
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(e, ExcecaoAplicacao.Tipo.ERRO));
            e.printStackTrace();
        }
    }
    
    private class AcaoSelecionarAbaEsquerda extends AbstractAction
    {
        public AcaoSelecionarAbaEsquerda() 
        {
            super("Selecionar esquerda");
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            painelTabulado.selecionarAbaAnterior();
        }        
    }
    
    private class AcaoSelecionarAbaDireita extends AbstractAction
    {
        public AcaoSelecionarAbaDireita() 
        {
            super("Selecionar direita");
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            painelTabulado.selecionarProximaAba();
        }        
    }
}
