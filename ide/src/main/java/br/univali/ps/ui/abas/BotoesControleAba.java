package br.univali.ps.ui.abas;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.utils.FabricaDeFileChooser;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.paineis.utils.PainelTabuladoListener;
import br.univali.ps.ui.telas.TelaPrincipal;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.swing.filtros.FiltroComposto;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
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
import javax.swing.text.StyleConstants;

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

        //botaoConfigPlguin.setVisible(false);
        //criarSeletorArquivo();
        configurarAcoes(telaPrincipal);
        configurarBotoes();
        criarDicasInterface();
        instalarObservadores(telaPrincipal);
        titulo.setForeground(ColorController.COR_LETRA);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configurarBotao(botaoAbrir,ColorController.TRANSPARENTE,ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 1);
            WeblafUtils.configurarBotao(botaoNovoArquivo,ColorController.TRANSPARENTE,ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 1);
            WeblafUtils.configurarBotao(botaoConfigPlguin,ColorController.TRANSPARENTE,ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 1);
        }

    }

    private void configurarBotoes() {
        botaoAbrir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoAbrir.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_closed.png"));
        botaoNovoArquivo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoNovoArquivo.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_add.png"));
        titulo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        titulo.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix_off.png"));
    }

    private JFileChooser criarSeletorArquivo() {
        JFileChooser dialogoSelecaoArquivo = FabricaDeFileChooser.getFileChooserAbertura();
        dialogoSelecaoArquivo.setCurrentDirectory(Configuracoes.getInstancia().getCaminhoUltimoDiretorio());
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
                Configuracoes.getInstancia().setCaminhoUltimoDiretorio(dialogoSelecaoArquivo.getCurrentDirectory());
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
        FabricaDicasInterface.criarTooltip(botaoAbrir, "Abre um programa ou exercício existente no computador", acaoAbrirArquivo);
        FabricaDicasInterface.criarTooltip(botaoNovoArquivo, "Cria uma nova aba contendo um modelo básico de programa", acaoNovoArquivo);
        FabricaDicasInterface.criarTooltip(titulo, "Exibe a tela inicial do Portugol Studio", acaoExibirTelaInicial);
    }

    private void instalarObservadores(final TelaPrincipal telaPrincipal) {
        telaPrincipal.getPainelTabulado().adicionaPainelTabuladoListener(this);

        titulo.addMouseListener(new MouseAdapter() {
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
        titulo.setForeground(ColorController.COR_LETRA);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configurarBotao(botaoAbrir,ColorController.TRANSPARENTE, ColorController.COR_LETRA, ColorController.COR_PRINCIPAL,ColorController.COR_LETRA, 1);
            WeblafUtils.configurarBotao(botaoNovoArquivo, ColorController.TRANSPARENTE, ColorController.COR_LETRA, ColorController.COR_PRINCIPAL,ColorController.COR_LETRA, 1);
        }
        
    }

    private void ativar() {
        titulo.setIcon(iconeAtivo);
        titulo.setForeground(ColorController.COR_LETRA);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configurarBotao(botaoAbrir,ColorController.TRANSPARENTE,ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 1);
            WeblafUtils.configurarBotao(botaoNovoArquivo,ColorController.TRANSPARENTE,ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 1);
        }
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
                FabricaDicasInterface.criarTooltipEstatica(titulo, dica);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        titulo = new javax.swing.JLabel();
        botaoAbrir = new com.alee.laf.button.WebButton();
        botaoNovoArquivo = new com.alee.laf.button.WebButton();
        botaoConfigPlguin = new com.alee.laf.button.WebButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(180, 25));
        setMinimumSize(new java.awt.Dimension(180, 25));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(180, 25));
        setLayout(new java.awt.GridBagLayout());

        titulo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        titulo.setForeground(new java.awt.Color(62, 62, 62));
        titulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Portugol/pequeno/light_pix_off.png"))); // NOI18N
        titulo.setText("Portugol Studio");
        titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4));
        titulo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        titulo.setMinimumSize(new java.awt.Dimension(100, 16));
        titulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(titulo, gridBagConstraints);

        botaoAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/folder_closed.png"))); // NOI18N
        botaoAbrir.setHideActionText(true);
        add(botaoAbrir, new java.awt.GridBagConstraints());

        botaoNovoArquivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/page_white_add.png"))); // NOI18N
        botaoNovoArquivo.setHideActionText(true);
        add(botaoNovoArquivo, new java.awt.GridBagConstraints());

        botaoConfigPlguin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/plugin.png"))); // NOI18N
        botaoConfigPlguin.setHideActionText(true);
        add(botaoConfigPlguin, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoAbrir;
    private com.alee.laf.button.WebButton botaoConfigPlguin;
    private com.alee.laf.button.WebButton botaoNovoArquivo;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
