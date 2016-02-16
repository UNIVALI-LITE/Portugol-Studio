package br.univali.ps.ui.abas;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.FabricaDeFileChooser;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.PainelTabuladoListener;
import br.univali.ps.ui.TelaPrincipal;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.swing.filtros.FiltroComposto;
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.button.WebButtonUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public final class BotoesControleAba extends CabecalhoAba implements PainelTabuladoListener {

    private static final Icon iconeAtivo = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png");
    private static final Icon iconeInativo = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix_off.png");

    private static final Color corAtivo = new Color(60, 60, 60);
    private static final Color corInativo = new Color(120, 120, 120);

    public static final String ACAO_NOVO_ARQUIVO = "Novo arquivo";

    private Action acaoNovoArquivo;
    private Action acaoAbrirArquivo;
    private Action acaoExibirTelaInicial;

    private Aba abaAtual;

    private static final FiltroArquivo filtroExercicio = new FiltroArquivo("Exercício do Portugol", "pex");
    private static final FiltroArquivo filtroPrograma = new FiltroArquivo("Programa do Portugol", "por");
    private static final FiltroArquivo filtroTodosSuportados = new FiltroComposto("Todos os tipos suportados", filtroPrograma, filtroExercicio);
    //private JFileChooser dialogoSelecaoArquivo;

    public BotoesControleAba(AbaInicial abaInicial, TelaPrincipal telaPrincipal) {
        super(abaInicial);

        removeAll();
        initComponents();

        //criarSeletorArquivo();
        configurarAcoes(telaPrincipal);
        configurarBotoes();
        criarDicasInterface();
        instalarObservadores(telaPrincipal);
        if (WeblafUtils.weblafEstaInstalado()) {
            ((WebButtonUI) botaoAbrir.getUI()).setRolloverDecoratedOnly(true);
            ((WebButtonUI) botaoNovoArquivo.getUI()).setRolloverDecoratedOnly(true);
        }

    }

    private void configurarBotoes() {
        botaoAbrir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAbrir.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_closed.png"));
//        botaoAbrir.setRolloverIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_open.png"));
//        botaoAbrir.setPressedIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_open.png"));

        botaoNovoArquivo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoNovoArquivo.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_add.png"));
//        botaoNovoArquivo.setRolloverIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_add.png"));
//        botaoNovoArquivo.setPressedIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_add.png"));

        titulo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private JFileChooser criarSeletorArquivo() {
        JFileChooser dialogoSelecaoArquivo = FabricaDeFileChooser.getFileChooserAbertura();
        dialogoSelecaoArquivo.setCurrentDirectory(Configuracoes.getInstancia().getDiretorioUsuario());
        dialogoSelecaoArquivo.setMultiSelectionEnabled(true);
        dialogoSelecaoArquivo.setAcceptAllFileFilterUsed(false);

        dialogoSelecaoArquivo.addChoosableFileFilter(filtroExercicio);
        dialogoSelecaoArquivo.addChoosableFileFilter(filtroPrograma);
        dialogoSelecaoArquivo.addChoosableFileFilter(filtroTodosSuportados);

        dialogoSelecaoArquivo.setFileFilter(filtroPrograma);
        return dialogoSelecaoArquivo;

    }

    private void configurarAcoes(final TelaPrincipal telaPrincipalDesktop) {
        configurarAcaoNovoArquivo(telaPrincipalDesktop);
        configurarAcaoAbrirArquivo(telaPrincipalDesktop);
        configurarAcaoExibirTelaInicial(telaPrincipalDesktop);
    }

    private void configurarAcaoNovoArquivo(final TelaPrincipal telaPrincipal) {
        /*
         * Esta ação não deveria estar exposta.
         * Futuramente vamos movê-la para o local correto ou encapsulá-la
         */
        final String nome = ACAO_NOVO_ARQUIVO;
        final KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);

        acaoNovoArquivo = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaPrincipal.criarNovoCodigoFonte();
            }
        };

        botaoNovoArquivo.setAction(acaoNovoArquivo);

        telaPrincipal.getPainelTabulado().getActionMap().put(nome, acaoNovoArquivo);
        telaPrincipal.getPainelTabulado().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoAbrirArquivo(final TelaPrincipal telaPrincipal) {
        final String nome = "Abrir arquivo";
        final KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);

        acaoAbrirArquivo = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dialogoSelecaoArquivo = criarSeletorArquivo();
                if (dialogoSelecaoArquivo.showOpenDialog(telaPrincipal) == JFileChooser.APPROVE_OPTION) {
                    final File[] arquivos = dialogoSelecaoArquivo.getSelectedFiles();
                    final List<File> listaArquivos = new ArrayList<>(Arrays.asList(arquivos));

                    telaPrincipal.abrirArquivosCodigoFonte(listaArquivos);
                }
            }
        };

        botaoAbrir.setAction(acaoAbrirArquivo);

        telaPrincipal.getPainelTabulado().getActionMap().put(nome, acaoAbrirArquivo);
        telaPrincipal.getPainelTabulado().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoExibirTelaInicial(final TelaPrincipal telaPrincipal) {
        final String nome = "Exibir tela inicial";
        final KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.ALT_DOWN_MASK);

        acaoExibirTelaInicial = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAba().selecionar();
            }
        };

        telaPrincipal.getPainelTabulado().getActionMap().put(nome, acaoExibirTelaInicial);
        telaPrincipal.getPainelTabulado().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void criarDicasInterface() {
        FabricaDicasInterface.criarDicaInterface(botaoAbrir, "Abre um programa ou exercício existente no computador", acaoAbrirArquivo);
        FabricaDicasInterface.criarDicaInterface(botaoNovoArquivo, "Cria uma nova aba contendo um modelo básico de programa", acaoNovoArquivo);
        FabricaDicasInterface.criarDicaInterface(titulo, "Exibe a tela inicial do Portugol Studio", acaoExibirTelaInicial);
    }

    private void instalarObservadores(final TelaPrincipal telaPrincipal) {
        telaPrincipal.getPainelTabulado().adicionaPainelTabuladoListener(this);

        titulo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!(abaAtual instanceof AbaInicial)) {
                    ativar();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!(abaAtual instanceof AbaInicial)) {
                    desativar();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                getAba().selecionar();
            }
        });
    }

    @Override
    protected void calculaTamanhoCabecalho() {

    }

    @Override
    public void abaSelecionada(Aba aba) {
        abaAtual = aba;

        if (abaAtual == this.getAba()) {
            ativar();
        } else {
            desativar();
        }
    }

    private void desativar() {
        titulo.setIcon(iconeInativo);
        titulo.setForeground(corInativo);
    }

    private void ativar() {
        titulo.setIcon(iconeAtivo);
        titulo.setForeground(corAtivo);
    }

    @Override
    public String getTitulo() {
        return "Pagina Inicial";
    }

    @Override
    public void setIcone(Icon icone) {
    }

    @Override
    public void setBotaoFecharVisivel(boolean removivel) {
    }

    @Override
    public void setTitulo(String titulo) {

    }

    public void exibirDica(final String dica) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FabricaDicasInterface.criarDicaInterfaceEstatica(titulo, dica);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        painelTitulo = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        painelBotoes = new javax.swing.JPanel();
        botaoAbrir = new javax.swing.JButton();
        botaoNovoArquivo = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

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
        titulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix_off.png"))); // NOI18N
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
        botaoAbrir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        botaoAbrir.setFocusable(false);
        botaoAbrir.setHideActionText(true);
        botaoAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoAbrir.setMinimumSize(new java.awt.Dimension(32, 25));
        botaoAbrir.setPreferredSize(new java.awt.Dimension(32, 25));
        botaoAbrir.setRequestFocusEnabled(false);
        botaoAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelBotoes.add(botaoAbrir);

        botaoNovoArquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/page_white_add.png"))); // NOI18N
        botaoNovoArquivo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        botaoNovoArquivo.setFocusable(false);
        botaoNovoArquivo.setHideActionText(true);
        botaoNovoArquivo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoNovoArquivo.setPreferredSize(new java.awt.Dimension(32, 25));
        botaoNovoArquivo.setRequestFocusEnabled(false);
        botaoNovoArquivo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelBotoes.add(botaoNovoArquivo);

        add(painelBotoes, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAbrir;
    private javax.swing.JButton botaoNovoArquivo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
