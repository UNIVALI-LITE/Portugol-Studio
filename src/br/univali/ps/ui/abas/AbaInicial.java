package br.univali.ps.ui.abas;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.WebConnectionUtils;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.PainelTabuladoPrincipal;
import br.univali.ps.ui.telas.TelaEditarUriAtualizacao;
import br.univali.ps.ui.telas.TelaPrincipal;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public final class AbaInicial extends Aba {



    private Action acaoExibirAtalhosTeclado;
    private Action acaoExibirDicasInterface;
    private boolean redimensionouParaBaixaResolucao = false;
    private TelaEditarUriAtualizacao telaEditarUriAtualizacao;
    
    
    
    public AbaInicial(TelaPrincipal telaPrincipal) {
        super();
        setPainelTabulado(telaPrincipal.getPainelTabulado());
        setCabecalho(new BotoesControleAba(this, telaPrincipal));
        initComponents(); 
        configurarResolucao();
        configurarCursorLogos();
        criarDicasInterface();
        configurarAcoes();
        configurarLinks();
        instalarObservadorCombinacoesSecretas();
        instalarAcoesSecretas();
    }

    private void instalarObservadorCombinacoesSecretas() {
        final StringBuilder sb = new StringBuilder();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (AbaInicial.this.getPainelTabulado().getAbaSelecionada() == AbaInicial.this && e.getID() == KeyEvent.KEY_PRESSED) {
                    if (Character.isLetterOrDigit(e.getKeyCode())) {
                        sb.append(e.getKeyChar());
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        synchronized (AbaInicial.this) {
                            String nome = sb.toString().toUpperCase();
                            Action acao = getActionMap().get(nome);

                            sb.delete(0, sb.length());

                            if (acao != null) {
                                acao.actionPerformed(null);
                            }
                        }
                    }
                }

                return false;
            }
        });
    }

    private void instalarAcoesSecretas() {
        instalarAcaoModificarURLAtualizacao();
        instalarAcaoEaster1();
    }

    private void instalarAcaoEaster1() {
        getActionMap().put("RICK", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {                    
                try {
                    ImageIcon gif = new ImageIcon(new URL("http://lite.acad.univali.br/~alice/portugol/resources/rick/image.gif"));
                    if(gif.getIconWidth()>1){
                        rotuloSairProgramando.setIcon(gif);
                        rotuloAssistirVideoAulas.setIcon(gif);
                        rotuloConhecerBibliotecas.setIcon(gif);
                        rotuloConhecerLinguagem.setIcon(gif);
                        JOptionPane.showMessageDialog(null, gif, "Never gonna", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(AbaInicial.class.getName()).log(Level.SEVERE, null, ex);
                }                    
            }
        });
    }
    private void instalarAcaoModificarURLAtualizacao() {
        getActionMap().put("BETA", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (telaEditarUriAtualizacao == null) {
                    telaEditarUriAtualizacao = new TelaEditarUriAtualizacao();
                }

                telaEditarUriAtualizacao.setLocationRelativeTo(null);
                telaEditarUriAtualizacao.setVisible(true);
            }
        });
    }
    private void configurarResolucao(){
        addComponentListener(new ComponentAdapter(){

            @Override
            public void componentResized(ComponentEvent e)
            {
                SwingUtilities.invokeLater(() -> {
                    if(getSize().width <= 800 || getSize().height<=600){
                        if(!redimensionouParaBaixaResolucao){
                            redimensionouParaBaixaResolucao = true;
                            rotuloSairProgramando.setIcon(null);
                            rotuloAssistirVideoAulas.setIcon(null);
                            rotuloConhecerBibliotecas.setIcon(null);
                            rotuloConhecerLinguagem.setIcon(null);
                            FabricaDicasInterface.mostrarNotificacao("Utilize uma resolução maior para melhor uso do Portugol Studio", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "stop.png"));
                        }
                    }else{
                        redimensionouParaBaixaResolucao = false;
                        rotuloSairProgramando.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/programar.png"));
                        rotuloAssistirVideoAulas.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/videoaulas.png"));
                        rotuloConhecerBibliotecas.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/bibliotecas.png"));
                        rotuloConhecerLinguagem.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/ajuda.png"));
                    }
                });
            }
            
        });
    }

    private void abrirGitHub() {
        WebConnectionUtils.abrirSite("https://github.com/UNIVALI-LITE/Portugol-Studio");
    }

    private void configurarCursorLogos() {
//        logoUnivali.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        logoLite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void configurarAcoes() {
        configurarAcaoSairProgramando();
        configurarAcaoConhecerLinguagem();
        configurarAcaoAssistirVideoAulas();
        configurarAcaoAtualizacoes();
        configurarAcaoConhecerBibliotecas();
        configurarAcaoExibirTelaSobre();
        configurarAcaoAjudarDesenvolvimento();
        configurarAcaoRelatarBug();
        configurarAcaoExibirAtalhosTeclado();
        configurarAcaoDicasInterface();
    }

    private void configurarAcaoAssistirVideoAulas() {
        Action acao = new AbstractAction(rotuloAssistirVideoAulas.getName()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                WebConnectionUtils.abrirSite("https://www.youtube.com/user/portugolstudio");
            }
        };

        getActionMap().put(rotuloAssistirVideoAulas.getName(), acao);
    }
    private void configurarAcaoAtualizacoes() {
        Action acao = new AbstractAction(rotuloAssistirVideoAulas.getName()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                WebConnectionUtils.abrirSite("https://github.com/UNIVALI-LITE/Portugol-Studio/commits/master");
            }
        };

        getActionMap().put(rotuloUpdate.getName(), acao);
    }

    private void configurarAcaoSairProgramando() {
        Action acao = new AbstractAction(rotuloSairProgramando.getName()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPainelTabulado().getActionMap().get(BotoesControleAba.ACAO_NOVO_ARQUIVO).actionPerformed(e);
                Action action = getPainelTabulado().getActionMap().get(BotoesControleAba.ACAO_NOVO_ARQUIVO);
            }
        };

        getActionMap().put(rotuloSairProgramando.getName(), acao);
    }

    private void configurarAcaoConhecerLinguagem() {
        Action acao = new AbstractAction(rotuloConhecerLinguagem.getName()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPainelTabulado().getActionMap().get(PainelTabuladoPrincipal.ACAO_EXIBIR_AJUDA).actionPerformed(e);
            }
        };

        getActionMap().put(rotuloConhecerLinguagem.getName(), acao);
    }

    private void configurarAcaoConhecerBibliotecas() {
        Action acao = new AbstractAction(rotuloConhecerBibliotecas.getName()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPainelTabulado().getActionMap().get(PainelTabuladoPrincipal.ACAO_EXIBIR_DOCUMENTACAO_BIBLIOTECA).actionPerformed(e);
            }
        };

        getActionMap().put(rotuloConhecerBibliotecas.getName(), acao);
    }

    private void configurarAcaoExibirTelaSobre() {
        Action acao = new AbstractAction(rotuloInformacoesSoftware.getName()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                PortugolStudio.getInstancia().getTelaSobre().setVisible(true);
            }
        };

        getActionMap().put(rotuloInformacoesSoftware.getName(), acao);
    }

    private void configurarAcaoAjudarDesenvolvimento() {
        Action acao = new AbstractAction(rotuloAjudarDesenvolvimento.getName()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGitHub();
            }
        };

        getActionMap().put(rotuloAjudarDesenvolvimento.getName(), acao);
    }

    private void configurarAcaoRelatarBug() {
        Action acao = new AbstractAction(rotuloRelatarBug.getName()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                WebConnectionUtils.abrirSite("https://docs.google.com/forms/d/1PfTW-mDrkv1PVYYB8UedH9x9hNJgMz8TnxqYgsjIwLE/viewform");
            }
        };

        getActionMap().put(rotuloRelatarBug.getName(), acao);
    }

    private void configurarLinks() {
        MouseListener listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JLabel rotulo = (JLabel) e.getSource();
                String nomeAcao = rotulo.getName();

                Action acao = getActionMap().get(nomeAcao);

                if (acao != null) {
                    acao.actionPerformed(null);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel rotulo = (JLabel) e.getSource();
                rotulo.setForeground(new Color(49,104,146));
                rotulo.setOpaque(true);
                //rotulo.setFont(rotulo.getFont().deriveFont(Font.BOLD));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel rotulo = (JLabel) e.getSource();
                rotulo.setForeground(new Color(51, 51, 51));
                rotulo.setOpaque(false);
                //rotulo.setFont(rotulo.getFont().deriveFont(Font.PLAIN));
            }
        };

        rotuloSairProgramando.addMouseListener(listener);
        rotuloSairProgramando.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rotuloAssistirVideoAulas.addMouseListener(listener);
        rotuloAssistirVideoAulas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rotuloConhecerBibliotecas.addMouseListener(listener);
        rotuloConhecerBibliotecas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rotuloConhecerLinguagem.addMouseListener(listener);
        rotuloConhecerLinguagem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rotuloAjudarDesenvolvimento.addMouseListener(listener);
        rotuloAjudarDesenvolvimento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rotuloRelatarBug.addMouseListener(listener);
        rotuloRelatarBug.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rotuloAtalhosTeclado.addMouseListener(listener);
        rotuloAtalhosTeclado.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        rotuloDicasInterface.addMouseListener(listener);
        rotuloDicasInterface.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rotuloInformacoesSoftware.addMouseListener(listener);
        rotuloInformacoesSoftware.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        rotuloUpdate.addMouseListener(listener);
        rotuloUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void configurarAcaoExibirAtalhosTeclado() {
        String nome = "atalhosTeclado";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0);

        acaoExibirAtalhosTeclado = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                PortugolStudio.getInstancia().getTelaAtalhosTeclado().setVisible(true);
            }
        };

        acaoExibirAtalhosTeclado.putValue(Action.ACCELERATOR_KEY, atalho);

        getActionMap().put(nome, acaoExibirAtalhosTeclado);

        getPainelTabulado().getActionMap().put(nome, acaoExibirAtalhosTeclado);
        getPainelTabulado().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void configurarAcaoDicasInterface() {
        String nome = "dicasInterface";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0);

        acaoExibirDicasInterface = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                PortugolStudio.getInstancia().getTelaDicas().setVisible(true);
            }
        };

        acaoExibirDicasInterface.putValue(Action.ACCELERATOR_KEY, atalho);

        getActionMap().put(nome, acaoExibirDicasInterface);

        getPainelTabulado().getActionMap().put(nome, acaoExibirDicasInterface);
        getPainelTabulado().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void criarDicasInterface() {
//        FabricaDicasInterface.criarDicaInterface(logoUnivali, "Conhecer o curso de Ciência da Computação da UNIVALI");
//        FabricaDicasInterface.criarDicaInterface(logoLite, "Conhecer o Laboratório de Inovação Tecnológica na Educação");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelFundo = new javax.swing.JPanel();
        painelCabecalho = new javax.swing.JPanel();
        logoPS = new br.univali.ps.ui.imagens.Logo();
        painelAmbiente = new javax.swing.JPanel();
        rotuloSlogan = new javax.swing.JLabel();
        conteudoColaborar = new javax.swing.JPanel();
        painelAlinhamento1 = new javax.swing.JPanel();
        rotuloDicasInterface = new javax.swing.JLabel();
        rotuloAtalhosTeclado = new javax.swing.JLabel();
        rotuloRelatarBug = new javax.swing.JLabel();
        rotuloAjudarDesenvolvimento = new javax.swing.JLabel();
        rotuloInformacoesSoftware = new javax.swing.JLabel();
        rotuloUpdate = new javax.swing.JLabel();
        painelCentral = new javax.swing.JPanel();
        conteudoIniciando = new javax.swing.JPanel();
        painelAlinhamento5 = new javax.swing.JPanel();
        rotuloSairProgramando = new javax.swing.JLabel();
        rotuloConhecerLinguagem = new javax.swing.JLabel();
        rotuloAssistirVideoAulas = new javax.swing.JLabel();
        rotuloConhecerBibliotecas = new javax.swing.JLabel();
        painelConteudo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        painelExemplos1 = new br.univali.ps.ui.swing.components.PainelExemplos();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        painelFundo.setBackground(new java.awt.Color(49, 104, 146));
        painelFundo.setLayout(new java.awt.BorderLayout());

        painelCabecalho.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
        painelCabecalho.setOpaque(false);
        painelCabecalho.setLayout(new java.awt.BorderLayout());

        logoPS.setOpaque(false);
        logoPS.setPreferredSize(new java.awt.Dimension(0, 100));
        painelCabecalho.add(logoPS, java.awt.BorderLayout.PAGE_START);

        painelAmbiente.setOpaque(false);
        painelAmbiente.setLayout(new java.awt.BorderLayout());

        rotuloSlogan.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloSlogan.setForeground(new java.awt.Color(232, 232, 232));
        rotuloSlogan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloSlogan.setText("Ambiente para Aprender a Programar");
        rotuloSlogan.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        rotuloSlogan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        painelAmbiente.add(rotuloSlogan, java.awt.BorderLayout.CENTER);

        painelCabecalho.add(painelAmbiente, java.awt.BorderLayout.SOUTH);

        painelFundo.add(painelCabecalho, java.awt.BorderLayout.NORTH);

        conteudoColaborar.setBackground(new java.awt.Color(228, 241, 254));
        conteudoColaborar.setLayout(new java.awt.BorderLayout());

        painelAlinhamento1.setOpaque(false);
        painelAlinhamento1.setLayout(new javax.swing.BoxLayout(painelAlinhamento1, javax.swing.BoxLayout.X_AXIS));

        rotuloDicasInterface.setBackground(new java.awt.Color(210, 231, 252));
        rotuloDicasInterface.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloDicasInterface.setForeground(new java.awt.Color(51, 51, 51));
        rotuloDicasInterface.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloDicasInterface.setText("<html><body><div>Dicas Interface (F3)</div></body></html>");
        rotuloDicasInterface.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloDicasInterface.setName("dicasInterface"); // NOI18N
        painelAlinhamento1.add(rotuloDicasInterface);

        rotuloAtalhosTeclado.setBackground(new java.awt.Color(210, 231, 252));
        rotuloAtalhosTeclado.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloAtalhosTeclado.setForeground(new java.awt.Color(51, 51, 51));
        rotuloAtalhosTeclado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloAtalhosTeclado.setText("<html><body><div>Atalhos do teclado (F11)</div></body></html>");
        rotuloAtalhosTeclado.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloAtalhosTeclado.setName("atalhosTeclado"); // NOI18N
        painelAlinhamento1.add(rotuloAtalhosTeclado);

        rotuloRelatarBug.setBackground(new java.awt.Color(210, 231, 252));
        rotuloRelatarBug.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloRelatarBug.setForeground(new java.awt.Color(51, 51, 51));
        rotuloRelatarBug.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloRelatarBug.setText("<html><body><div>Relatar um Bug</div></body></html>");
        rotuloRelatarBug.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloRelatarBug.setName("relatarBug"); // NOI18N
        painelAlinhamento1.add(rotuloRelatarBug);

        rotuloAjudarDesenvolvimento.setBackground(new java.awt.Color(210, 231, 252));
        rotuloAjudarDesenvolvimento.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloAjudarDesenvolvimento.setForeground(new java.awt.Color(51, 51, 51));
        rotuloAjudarDesenvolvimento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloAjudarDesenvolvimento.setText("<html><body><div>Ajudar no desenvolvimento</div></body></html>");
        rotuloAjudarDesenvolvimento.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloAjudarDesenvolvimento.setName("ajudarDesenvolvimento"); // NOI18N
        painelAlinhamento1.add(rotuloAjudarDesenvolvimento);

        rotuloInformacoesSoftware.setBackground(new java.awt.Color(210, 231, 252));
        rotuloInformacoesSoftware.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloInformacoesSoftware.setForeground(new java.awt.Color(51, 51, 51));
        rotuloInformacoesSoftware.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloInformacoesSoftware.setText("<html><body><div>Sobre (F12)</div></body></html>");
        rotuloInformacoesSoftware.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloInformacoesSoftware.setName("informacoesSoftware"); // NOI18N
        painelAlinhamento1.add(rotuloInformacoesSoftware);

        rotuloUpdate.setBackground(new java.awt.Color(210, 231, 252));
        rotuloUpdate.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloUpdate.setForeground(new java.awt.Color(51, 51, 51));
        rotuloUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloUpdate.setText("<html><body><div>Atualizações</div></body></html>");
        rotuloUpdate.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloUpdate.setName("updateLog"); // NOI18N
        painelAlinhamento1.add(rotuloUpdate);

        conteudoColaborar.add(painelAlinhamento1, java.awt.BorderLayout.CENTER);

        painelFundo.add(conteudoColaborar, java.awt.BorderLayout.SOUTH);

        painelCentral.setBackground(new java.awt.Color(228, 241, 254));
        painelCentral.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelCentral.setLayout(new java.awt.BorderLayout());

        conteudoIniciando.setBackground(new java.awt.Color(4, 52, 88));
        conteudoIniciando.setOpaque(false);
        conteudoIniciando.setLayout(new java.awt.BorderLayout());

        painelAlinhamento5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelAlinhamento5.setMaximumSize(new java.awt.Dimension(2147483647, 500));
        painelAlinhamento5.setOpaque(false);
        painelAlinhamento5.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        rotuloSairProgramando.setBackground(new java.awt.Color(210, 231, 252));
        rotuloSairProgramando.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloSairProgramando.setForeground(new java.awt.Color(51, 51, 51));
        rotuloSairProgramando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloSairProgramando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/lite/programar.png"))); // NOI18N
        rotuloSairProgramando.setText("<html><body><div>Programar (Ctrl + N)</div></body></html>");
        rotuloSairProgramando.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloSairProgramando.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloSairProgramando.setMinimumSize(new java.awt.Dimension(150, 25));
        rotuloSairProgramando.setName("sairProgramando"); // NOI18N
        rotuloSairProgramando.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelAlinhamento5.add(rotuloSairProgramando);

        rotuloConhecerLinguagem.setBackground(new java.awt.Color(210, 231, 252));
        rotuloConhecerLinguagem.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloConhecerLinguagem.setForeground(new java.awt.Color(51, 51, 51));
        rotuloConhecerLinguagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloConhecerLinguagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/lite/ajuda.png"))); // NOI18N
        rotuloConhecerLinguagem.setText("<html><body><div>Ajuda (F1)</div></body></html>");
        rotuloConhecerLinguagem.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloConhecerLinguagem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloConhecerLinguagem.setMinimumSize(new java.awt.Dimension(110, 25));
        rotuloConhecerLinguagem.setName("conhecerLinguagem"); // NOI18N
        rotuloConhecerLinguagem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelAlinhamento5.add(rotuloConhecerLinguagem);

        rotuloAssistirVideoAulas.setBackground(new java.awt.Color(210, 231, 252));
        rotuloAssistirVideoAulas.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloAssistirVideoAulas.setForeground(new java.awt.Color(51, 51, 51));
        rotuloAssistirVideoAulas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloAssistirVideoAulas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/lite/videoaulas.png"))); // NOI18N
        rotuloAssistirVideoAulas.setText("<html><body><div>Videoaulas</div></body></html>");
        rotuloAssistirVideoAulas.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloAssistirVideoAulas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloAssistirVideoAulas.setMinimumSize(new java.awt.Dimension(150, 25));
        rotuloAssistirVideoAulas.setName("assistirVideoAulas"); // NOI18N
        rotuloAssistirVideoAulas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelAlinhamento5.add(rotuloAssistirVideoAulas);

        rotuloConhecerBibliotecas.setBackground(new java.awt.Color(210, 231, 252));
        rotuloConhecerBibliotecas.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        rotuloConhecerBibliotecas.setForeground(new java.awt.Color(51, 51, 51));
        rotuloConhecerBibliotecas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloConhecerBibliotecas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/lite/bibliotecas.png"))); // NOI18N
        rotuloConhecerBibliotecas.setText("<html><body><div>Bibliotecas (Shift + F1)</div></body></html>");
        rotuloConhecerBibliotecas.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloConhecerBibliotecas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloConhecerBibliotecas.setMinimumSize(new java.awt.Dimension(88, 25));
        rotuloConhecerBibliotecas.setName("conhecerBibliotecas"); // NOI18N
        rotuloConhecerBibliotecas.setVerifyInputWhenFocusTarget(false);
        rotuloConhecerBibliotecas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        painelAlinhamento5.add(rotuloConhecerBibliotecas);

        conteudoIniciando.add(painelAlinhamento5, java.awt.BorderLayout.CENTER);

        painelCentral.add(conteudoIniciando, java.awt.BorderLayout.NORTH);

        painelConteudo.setBackground(new java.awt.Color(228, 241, 254));
        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelConteudo.setPreferredSize(new java.awt.Dimension(700, 80));
        painelConteudo.setRequestFocusEnabled(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(75, 119, 190));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        painelExemplos1.setBorder(null);
        jPanel1.add(painelExemplos1, java.awt.BorderLayout.CENTER);

        painelConteudo.add(jPanel1, java.awt.BorderLayout.CENTER);

        painelCentral.add(painelConteudo, java.awt.BorderLayout.CENTER);

        painelFundo.add(painelCentral, java.awt.BorderLayout.CENTER);

        add(painelFundo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel conteudoColaborar;
    private javax.swing.JPanel conteudoIniciando;
    private javax.swing.JPanel jPanel1;
    private br.univali.ps.ui.imagens.Logo logoPS;
    private javax.swing.JPanel painelAlinhamento1;
    private javax.swing.JPanel painelAlinhamento5;
    private javax.swing.JPanel painelAmbiente;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JPanel painelCentral;
    private javax.swing.JPanel painelConteudo;
    private br.univali.ps.ui.swing.components.PainelExemplos painelExemplos1;
    private javax.swing.JPanel painelFundo;
    private javax.swing.JLabel rotuloAjudarDesenvolvimento;
    private javax.swing.JLabel rotuloAssistirVideoAulas;
    private javax.swing.JLabel rotuloAtalhosTeclado;
    private javax.swing.JLabel rotuloConhecerBibliotecas;
    private javax.swing.JLabel rotuloConhecerLinguagem;
    private javax.swing.JLabel rotuloDicasInterface;
    private javax.swing.JLabel rotuloInformacoesSoftware;
    private javax.swing.JLabel rotuloRelatarBug;
    private javax.swing.JLabel rotuloSairProgramando;
    private javax.swing.JLabel rotuloSlogan;
    private javax.swing.JLabel rotuloUpdate;
    // End of variables declaration//GEN-END:variables
}
