package br.univali.ps.ui;

import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.ui.acoes.FabricaAcao;
import br.univali.ps.ui.acoes.AcaoNovoArquivo;
import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.swing.filtros.FiltroComposto;
import br.univali.ps.ui.util.IconFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.SearchDialogSearchContext;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

public final class TelaPrincipal extends JFrame
{
    private FiltroArquivo filtroExercicio;
    private FiltroArquivo filtroPrograma;
    private FiltroArquivo filtroTodosSuportados;
    private JFileChooser dialogoSelecaoArquivo;
    
    private AcaoNovoArquivo acaoNovoArquivo;
    private AcaoAbrirArquivo acaoAbrirArquivo;
    private Action acaoSelecionarAbaDireita;
    private Action acaoSelecionarAbaEsquerda;
    private Action acaoPesquisarSubstituir;
    private Action acaoFecharAbaAtual;
    private Action acaoFecharTodasAbas;
    private Action acaoExibirAjuda;
    private Action acaoExibirTelaSobre;
    private Action acaoExibirDocumentacaoBiblioteca;
    
    private FindDialog dialogoPesquisar;
    private ReplaceDialog dialogoSubstituir;
    private FindReplaceActionListener observadorAcaoPesquisaSubstituir;
        
    private AbaAjuda abaAjuda;
    private AbaCodigoFonte abaCodigoFonte;    
    private AbaDocumentacaoBiblioteca abaDocumentacao;    
    
    private boolean telaPrincipal = false;
    
    public TelaPrincipal()
    {
        initComponents();        
        configurarJanela();
        configurarSeletorArquivo();        
        configurarDialogosPesquisarSubstituir();        
        configurarAcoes();
        instalarObservadores();
        List<Aba> abas =  painelTabulado.getAbas(AbaInicial.class);
        final AbaInicial abaInicial = (AbaInicial)abas.get(0);
        
        abaInicial.configurarAcaoSobre(acaoExibirTelaSobre);
        abaInicial.configurarAcaoAjuda(acaoExibirAjuda);
        abaInicial.configurarAcaoBibliotecas(acaoExibirDocumentacaoBiblioteca);
        //criarMenuExemplos();        
    }    
    
     private void configurarJanela()
    {
        configurarIconeAplicacao();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
     
    private void configurarSeletorArquivo()
    {
        filtroExercicio = new FiltroArquivo("Exercício do Portugol", "pex");
        filtroPrograma = new FiltroArquivo("Programa do Portugol", "por");        
        filtroTodosSuportados = new FiltroComposto("Todos os tipos suportados", filtroPrograma, filtroExercicio);
        
        dialogoSelecaoArquivo = new JFileChooser();
        
        dialogoSelecaoArquivo.setCurrentDirectory(new File("./exemplos"));
        dialogoSelecaoArquivo.setMultiSelectionEnabled(true);
        dialogoSelecaoArquivo.setAcceptAllFileFilterUsed(false);
    }
    
    private void configurarIconeAplicacao()
    {
        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light-bulb-code.png")));
        }
        catch (IOException ioe)
        {
        }
    }
    
    public void configurarDialogosPesquisarSubstituir()
    {
        observadorAcaoPesquisaSubstituir = new FindReplaceActionListener();
        
        dialogoPesquisar = new FindDialog(this, observadorAcaoPesquisaSubstituir);
        dialogoSubstituir = new ReplaceDialog(this, observadorAcaoPesquisaSubstituir);
        dialogoSubstituir.setSearchContext(dialogoPesquisar.getSearchContext());
    }
    
    private void configurarAcoes()
    {
        configurarAcaoNovoArquivo();
        configurarAcaoAbrirArquivo();
        
        configurarAcaoSelecionarAbaEsquerda();
        configurarAcaoSelecionarAbaDireita();
        configurarAcaoPesquisarSubstituir();
        
        configurarAcaoFecharAbaAtual();
        configurarAcaoFecharTodasAbas();
        
        configurarAcaoExibirAjuda();
        configurarAcaoExibirDocumentacaoBiblioteca();
        configurarAcaoExibirTelaSobre();
        
        painelTabulado.init(acaoAbrirArquivo, acaoNovoArquivo);
    }
    
    private void configurarAcaoNovoArquivo()
    {
        acaoNovoArquivo = (AcaoNovoArquivo) FabricaAcao.getInstancia().criarAcao(AcaoNovoArquivo.class);
        acaoNovoArquivo.configurar(painelTabulado);
        
        String nome = (String) acaoNovoArquivo.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoNovoArquivo.getValue(AbstractAction.ACCELERATOR_KEY);
        
        getRootPane().getActionMap().put(nome, acaoNovoArquivo);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void configurarAcaoAbrirArquivo()
    {
        acaoAbrirArquivo = (AcaoAbrirArquivo) FabricaAcao.getInstancia().criarAcao(AcaoAbrirArquivo.class);
        acaoAbrirArquivo.configurar(painelTabulado, this, dialogoSelecaoArquivo, filtroTodosSuportados, filtroExercicio, filtroPrograma, filtroTodosSuportados);
        
        String nome = (String) acaoAbrirArquivo.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoAbrirArquivo.getValue(AbstractAction.ACCELERATOR_KEY);
        
        getRootPane().getActionMap().put(nome, acaoAbrirArquivo);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoSelecionarAbaEsquerda()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK);
        String nome = "Selecionar aba à esquerda";
                
        acaoSelecionarAbaEsquerda = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                painelTabulado.selecionarAbaAnterior();
            }
        };
        
        getRootPane().getActionMap().put(nome, acaoSelecionarAbaEsquerda);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void configurarAcaoSelecionarAbaDireita()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK);
        String nome = "Selecionar aba á direita";
        
        acaoSelecionarAbaDireita = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                painelTabulado.selecionarProximaAba();
            }
        };
        
        getRootPane().getActionMap().put(nome, acaoSelecionarAbaDireita);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void configurarAcaoPesquisarSubstituir()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK);
        String nome = "Pesquisar e substituir";
        
        acaoPesquisarSubstituir = new AbstractAction(nome,IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "find.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!telaPrincipal)
                {
                    if (dialogoPesquisar.isVisible())
                    {
                        dialogoPesquisar.setVisible(false);
                    }
                    
                    dialogoSubstituir.setVisible(true);
                }
            }
        };
        
        painelTabulado.getActionMap().put(nome, acaoPesquisarSubstituir);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void configurarAcaoFecharAbaAtual()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke("control Q");
        String nome = "Fechar aba atual";
        
        acaoFecharAbaAtual = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Aba aba = painelTabulado.getAbaSelecionada();
                
                if (aba != null && aba.getClass() != AbaInicial.class)
                {
                    aba.fechar();
                }
            }
        };
        
        painelTabulado.getActionMap().put(nome, acaoFecharAbaAtual);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void  configurarAcaoFecharTodasAbas()
    {
       KeyStroke atalho = KeyStroke.getKeyStroke("shift control Q");
       String nome = "Fechar todas as abas";
        
        acaoFecharTodasAbas = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for (Class<? extends Aba> classeAba : Aba.classesFilhas())
                {
                    if (classeAba != AbaInicial.class)
                    {
                        painelTabulado.fecharTodasAbas(classeAba);
                    }
                }
            }
        };
        
        painelTabulado.getActionMap().put(nome, acaoFecharTodasAbas);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void  configurarAcaoExibirDocumentacaoBiblioteca()
    {
       KeyStroke atalho = KeyStroke.getKeyStroke("shift F1");
       String nome = "Documentação das bibliotecas";
        
        acaoExibirDocumentacaoBiblioteca = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                exibirAbaDocumentacao();
            }
        };
        
        painelTabulado.getActionMap().put(nome, acaoExibirDocumentacaoBiblioteca);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    } 

    private void  configurarAcaoExibirAjuda()
    {
       KeyStroke atalho = KeyStroke.getKeyStroke("F1");
       String nome = "Exibir ajuda";
        
        acaoExibirAjuda = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                exibirAbaAjuda();
            }
        };
        
        painelTabulado.getActionMap().put(nome, acaoExibirAjuda);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void  configurarAcaoExibirTelaSobre()
    {
       KeyStroke atalho = KeyStroke.getKeyStroke("F12");
       String nome = "Exibir tela sobre";
        
        acaoExibirTelaSobre = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PortugolStudio.getInstancia().getTelaSobre().setVisible(true);
            }
        };
        
        painelTabulado.getActionMap().put(nome, acaoExibirTelaSobre);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }    
    
    private void instalarObservadores()
    {
        instalarObservadorExcecoesNaoTratadas();
        instalarObservadorPainelTabulado();
        instalarObservadorJanela();
    }
    
    private void instalarObservadorExcecoesNaoTratadas()
    {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread thread, Throwable excecao)
            {
                if ((excecao instanceof ClassNotFoundException) || (excecao instanceof NoClassDefFoundError))
                {
                    String mensagem = "Uma das bibliotecas ou classes necessárias para o funcionamento do Portugol Studio não foi encontrada.\nO Portugol Studio será enecerrado.";
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO));
                    System.exit(1);
                }
                else if (excecao instanceof IllegalArgumentException)
                {
                    excecao.printStackTrace(System.err);
                }
                else
                {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO));
                    excecao.printStackTrace(System.err);
                }
            }
        });
    }
    
    private void instalarObservadorPainelTabulado()
    {
        painelTabulado.adicionaPainelTabuladoListener(new PainelTabuladoListener()
        {
            @Override
            public void abaSelecionada(Aba aba)
            {
                if (aba.getClass() == AbaCodigoFonte.class)
                {
                    abaCodigoFonte = (AbaCodigoFonte) aba;
                    telaPrincipal = false;
                }
                else
                {
                    abaCodigoFonte = null;
                    telaPrincipal = true;
                }               
            }
        });
    }
    
    private void instalarObservadorJanela()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                fecharAplicativo();
            }            
        });
    }
    
    private void fecharAplicativo()
    {
        painelTabulado.fecharTodasAbas(AbaCodigoFonte.class);

        if (!painelTabulado.temAbaAberta(AbaCodigoFonte.class))
        {
            try
            {
                PortugolStudio.getInstancia().getConfiguracoes().salvar();
            }
            catch (ExcecaoAplicacao excecaoAplicacao)
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
            }
            
            System.exit(0);
        }
    }    

    private class FindReplaceActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

            if (painelTabulado.getAbaSelecionada().getClass() == AbaCodigoFonte.class)
            {
                String command = e.getActionCommand();
                SearchDialogSearchContext context = dialogoPesquisar.getSearchContext();
                AbaCodigoFonte abaCodigoFonte = (AbaCodigoFonte) painelTabulado.getAbaSelecionada();
                RSyntaxTextArea textArea = abaCodigoFonte.getEditor().getTextArea();

                //int pos = textArea.getCaretPosition();
                
                
                if (FindDialog.ACTION_FIND.equals(command))
                {
                    if (!SearchEngine.find(textArea, context))
                    {
                        reiniciar(context, textArea, e);
                    }
                }
                else if (ReplaceDialog.ACTION_REPLACE.equals(command))
                {
                    if (!SearchEngine.replace(textArea, context))
                    {
                        reiniciar(context, textArea, e);
                    }
                }
                else if (ReplaceDialog.ACTION_REPLACE_ALL.equals(command))
                {
                    int count = SearchEngine.replaceAll(textArea, context);
                    JOptionPane.showMessageDialog(null, count
                            + " ocorrências foram substituídas.");
                }
                
                //textArea.setCaretPosition(pos);

            }
        }
        
        private void reiniciar(SearchContext context, RSyntaxTextArea textArea, ActionEvent e)
        {
            UIManager.getLookAndFeel().provideErrorFeedback(textArea);
                        
            String s = "A pesquisa chegou no início do arquivo, deseja recomeçar do final?";

            if (context.getSearchForward())
            {
                s = "A pesquisa chegou no final do arquivo, deseja recomeçar do início?";
            }

            if (JOptionPane.showConfirmDialog(TelaPrincipal.this, s, "Pesquisar", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)
            {
                if (context.getSearchForward())
                {
                    textArea.setCaretPosition(0);
                }
                else
                {
                    textArea.setCaretPosition(textArea.getText().length() - 1);
                }

                actionPerformed(e);
            }
        }
    }

    public PainelTabuladoPrincipal getPainelTabulado()
    {
        return painelTabulado;
    }
    
    private void exibirAbaAjuda()
    {
        if (abaAjuda == null)
        {
            abaAjuda = new AbaAjuda(painelTabulado);
        }
        else if (!painelTabulado.temAbaAberta(AbaAjuda.class))
        {
            abaAjuda.adicionar(painelTabulado);
        }
        
        abaAjuda.selecionar();
    }
    
    private void exibirAbaDocumentacao()
    {
        if (abaDocumentacao == null)
        {
            abaDocumentacao = new AbaDocumentacaoBiblioteca(painelTabulado);
        }
        else if (!painelTabulado.temAbaAberta(AbaDocumentacaoBiblioteca.class))
        {
            abaDocumentacao.adicionar(painelTabulado);
        }
        
        abaDocumentacao.selecionar();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelTabulado = new br.univali.ps.ui.PainelTabuladoPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Portugol Studio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(700, 520));

        painelTabulado.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 8, 8, 8));
        getContentPane().add(painelTabulado, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.univali.ps.ui.PainelTabuladoPrincipal painelTabulado;
    // End of variables declaration//GEN-END:variables
}
