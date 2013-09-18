package br.univali.ps.ui;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.acoes.AcaoAbrirArquivo;
import br.univali.ps.ui.acoes.FabricaAcao;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.swing.filtros.FiltroComposto;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import net.java.balloontip.BalloonTip;

public final class BotoesControleAba extends CabecalhoAba implements PainelTabuladoListener
{
    private static final Icon iconeAtivo = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png");
    private static final Icon iconeInativo = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code_off.png");
    
    private static final Color corAtivo = new Color(60, 60, 60);
    private static final Color corInativo = new Color(120, 120, 120);
    public static final String  ACAO_NOVO_ARQUIVO = "Novo arquivo";
    
    private Action acaoNovoArquivo;
    private AcaoAbrirArquivo acaoAbrirArquivo;
    private Action acaoExibirTelaInicial;
    
    private Aba abaAtual;
    private PainelTabulado painelTabulado;
    
    private FiltroArquivo filtroExercicio;
    private FiltroArquivo filtroPrograma;
    private FiltroArquivo filtroTodosSuportados;
    private JFileChooser dialogoSelecaoArquivo;
    
    
    public BotoesControleAba(AbaInicial abaInicial, PainelTabulado painelTabulado)
    {
        super(abaInicial);
        removeAll();
        initComponents();
        
        this.painelTabulado = painelTabulado;
        
        configurarSeletorArquivo();
        configurarAcoes();
        configurarBotoes();
        criarDicasInterface();
        instalarObservadores();        
    }
    
    private void configurarBotoes()
    {
        botaoAbrir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAbrir.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_closed.png"));
        botaoAbrir.setRolloverIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_open.png"));
        botaoAbrir.setPressedIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_open.png"));
        
        botaoNovoArquivo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoNovoArquivo.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_add.png"));
        botaoNovoArquivo.setRolloverIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_add.png"));
        botaoNovoArquivo.setPressedIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_add.png"));
        
        
        titulo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    private void configurarSeletorArquivo()
    {
        filtroExercicio = new FiltroArquivo("Exercício do Portugol", "pex");
        filtroPrograma = new FiltroArquivo("Programa do Portugol", "por");        
        filtroTodosSuportados = new FiltroComposto("Todos os tipos suportados", filtroPrograma, filtroExercicio);
        
        dialogoSelecaoArquivo = new JFileChooser();
        dialogoSelecaoArquivo.setCurrentDirectory(new File("."));
        dialogoSelecaoArquivo.setMultiSelectionEnabled(true);
        dialogoSelecaoArquivo.setAcceptAllFileFilterUsed(false);
    }    
    

    private void configurarAcoes()
    {
        configurarAcaoNovoArquivo();
        configurarAcaoAbrirArquivo();
        configurarAcaoExibirTelaInicial();
    }
    
    private void configurarAcaoNovoArquivo()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        
        acaoNovoArquivo = new AbstractAction(ACAO_NOVO_ARQUIVO, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_add.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PortugolStudio.getInstancia().criarNovoCodigoFonte();
            }
        };
        acaoNovoArquivo.putValue(Action.ACCELERATOR_KEY, atalho);
        
        botaoNovoArquivo.setAction(acaoNovoArquivo);
        
        painelTabulado.getActionMap().put(ACAO_NOVO_ARQUIVO, acaoNovoArquivo);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, ACAO_NOVO_ARQUIVO);
    }
    
    private void configurarAcaoAbrirArquivo()
    {
        //TelaPrincipalDesktop telaPrincipal = PortugolStudio.getInstancia().getTelaPrincipal();
        
        acaoAbrirArquivo = (AcaoAbrirArquivo) FabricaAcao.getInstancia().criarAcao(AcaoAbrirArquivo.class);
        acaoAbrirArquivo.configurar(painelTabulado, getParent(), dialogoSelecaoArquivo, filtroTodosSuportados, filtroExercicio, filtroPrograma, filtroTodosSuportados);
        
        String nome = (String) acaoAbrirArquivo.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoAbrirArquivo.getValue(AbstractAction.ACCELERATOR_KEY);
        
        painelTabulado.getActionMap().put(nome, acaoAbrirArquivo);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
        
        botaoAbrir.setAction(acaoAbrirArquivo);
    }    
    
    private void configurarAcaoExibirTelaInicial()
    {
        String nome = "Exibir tela inicial";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.ALT_DOWN_MASK);
        
        acaoExibirTelaInicial = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getAba().selecionar();
            }
        };
        
        acaoExibirTelaInicial.putValue(Action.ACCELERATOR_KEY, atalho);
        
        painelTabulado.getActionMap().put(nome, acaoExibirTelaInicial);
        painelTabulado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void criarDicasInterface()
    {
        FabricaDicasInterface.criarDicaInterface(botaoAbrir, "Abre um programa ou exercício existente no computador", acaoAbrirArquivo, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(botaoNovoArquivo, "Cria uma nova aba contendo um modelo básico de programa", acaoNovoArquivo, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(titulo, "Exibe a tela inicial do Portugol Studio", acaoExibirTelaInicial, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
    }
    
    private void instalarObservadores()
    {
        painelTabulado.adicionaPainelTabuladoListener(this);
        
        titulo.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                if (!(abaAtual instanceof AbaInicial))
                {
                    ativar();
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if (!(abaAtual instanceof AbaInicial))
                {
                    desativar();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                getAba().selecionar();
            }            
        });
    }

    @Override
    protected void calculaTamanhoCabecalho()
    {
        
    }

    @Override
    public void abaSelecionada(Aba aba)
    {
        abaAtual = aba;
        
        if (abaAtual == this.getAba())
        {
            ativar();
        } 
        else 
        {
            desativar();
        }
    }    
    
    private void desativar()
    {
        titulo.setIcon(iconeInativo);
        titulo.setForeground(corInativo);
    }
    
    private void ativar()
    {
        titulo.setIcon(iconeAtivo);
        titulo.setForeground(corAtivo);
    }
             

    @Override
    public String getTitulo()
    {
        return "Pagina Inicial";
    }

    @Override
    public void setIcone(Icon icone)
    {
    }

    @Override
    public void setBotaoFecharVisivel(boolean removivel)
    {
    }

    @Override
    public void setTitulo(String titulo)
    {
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelTitulo = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        painelBotoes = new javax.swing.JPanel();
        botaoAbrir = new javax.swing.JButton();
        botaoNovoArquivo = new javax.swing.JButton();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(180, 25));
        setMinimumSize(new java.awt.Dimension(180, 25));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(180, 25));
        setLayout(new java.awt.BorderLayout());

        painelTitulo.setOpaque(false);
        painelTitulo.setPreferredSize(new java.awt.Dimension(120, 16));
        painelTitulo.setLayout(new java.awt.BorderLayout());

        titulo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        titulo.setForeground(new java.awt.Color(62, 62, 62));
        titulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light-bulb-code_off.png"))); // NOI18N
        titulo.setText("Portugol Studio");
        titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4));
        titulo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        titulo.setMinimumSize(new java.awt.Dimension(100, 16));
        titulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelTitulo.add(titulo, java.awt.BorderLayout.CENTER);

        add(painelTitulo, java.awt.BorderLayout.CENTER);

        painelBotoes.setOpaque(false);
        painelBotoes.setLayout(new java.awt.GridLayout(1, 2));

        botaoAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_closed.png"))); // NOI18N
        botaoAbrir.setBorderPainted(false);
        botaoAbrir.setContentAreaFilled(false);
        botaoAbrir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        botaoAbrir.setFocusable(false);
        botaoAbrir.setHideActionText(true);
        botaoAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoAbrir.setMinimumSize(new java.awt.Dimension(32, 25));
        botaoAbrir.setPreferredSize(new java.awt.Dimension(32, 25));
        botaoAbrir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_open.png"))); // NOI18N
        botaoAbrir.setRequestFocusEnabled(false);
        botaoAbrir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/folder_open.png"))); // NOI18N
        botaoAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelBotoes.add(botaoAbrir);

        botaoNovoArquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/page_white_add.png"))); // NOI18N
        botaoNovoArquivo.setBorderPainted(false);
        botaoNovoArquivo.setContentAreaFilled(false);
        botaoNovoArquivo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        botaoNovoArquivo.setFocusable(false);
        botaoNovoArquivo.setHideActionText(true);
        botaoNovoArquivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoNovoArquivo.setPreferredSize(new java.awt.Dimension(32, 25));
        botaoNovoArquivo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/page_add.png"))); // NOI18N
        botaoNovoArquivo.setRequestFocusEnabled(false);
        botaoNovoArquivo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/page_add.png"))); // NOI18N
        botaoNovoArquivo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelBotoes.add(botaoNovoArquivo);

        add(painelBotoes, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAbrir;
    private javax.swing.JButton botaoNovoArquivo;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
