package br.univali.ps.ui.abas;

import br.univali.ps.dominio.pack.PackDownloader;
import br.univali.ps.dominio.pack.PackDownloaderException;
import br.univali.ps.dominio.pack.PackDownloaderListener;
import br.univali.ps.dominio.pack.PackDownloaderObserver;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.Configuracoes;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.PainelTabuladoPrincipal;
import br.univali.ps.ui.TelaAtalhosTeclado;
import br.univali.ps.ui.TelaPrincipalDesktop;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import net.java.balloontip.BalloonTip;

public final class AbaInicial extends Aba implements PackDownloaderObserver
{
    private JPopupMenu menuExemplos;
    //private PainelTabulado painelTabulado;
    private final TelaAtalhosTeclado telaAtalhosTeclado;
    private Action acaoExplorarExemplos;
    private Action acaoExibirAtalhosTeclado;

    public AbaInicial(TelaPrincipalDesktop telaPrincipalDesktop)
    {
        super();
        setPainelTabulado(telaPrincipalDesktop.getPainelTabulado());
        this.telaAtalhosTeclado = new TelaAtalhosTeclado();
        setCabecalho(new BotoesControleAba(this, telaPrincipalDesktop));

        initComponents();
        
        configurarAcaoExplorarExemplos();
        configurarCursorLogos();
        criarDicasInterface();
        configurarAcoes();
        configurarLinks();
        configurarExibicaoAvisoVideoAulas();
        
    }
    
    private void configurarExibicaoAvisoVideoAulas()
    {
        addComponentListener(new ComponentAdapter() 
        {
            @Override
            public void componentShown(ComponentEvent e) 
            {
                Configuracoes configuracoes = Configuracoes.getInstancia();
                
                if (configuracoes.isExibirAvisoVideoAulas())
                {                        
                    configuracoes.setExibirAvisoVideoAulas(false);
                    
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        @Override
                        public void run() 
                        {
                            JOptionPane.showMessageDialog(AbaInicial.this, "Seja bem vindo!!\n\nPara tornar o Portugol Studio ainda melhor, preparamos uma série de vídeoaulas que irão auxiliá-lo no seu aprendizado.\nPara assistí-las, acesse o link \"Assistir Vídeoaulas\" localizado no menu \"Aprender\".\n\nObrigado por utilizar o Portugol Studio e bons estudos!", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                }
            }            
        });
    }
    
    private void criarMenuExemplos()
    {
        try
        {
            Configuracoes configuracoes = Configuracoes.getInstancia();
            File diretorioExemplos = new File(configuracoes.getDiretorioExemplos());

            if (diretorioExemplos.exists())
            {
                Icon iconeDiretorio = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_open.png");
                Icon iconeArquivo = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png");

                menuExemplos = new JPopupMenu();
                
                List<String[]> entradasIndice = lerIndice(new File(diretorioExemplos, "indice.txt"));

                for (String[] entradaIndice : entradasIndice)
                {
                    JMenuItem item = obterSubniveis(diretorioExemplos, entradaIndice, iconeDiretorio, iconeArquivo);

                    if (item != null)
                    {
                        menuExemplos.add(item);
                    }
                }
            }
            else
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new Exception("Diretórios de exemplos " + diretorioExemplos.getPath() + " não existe! Não foi possível criar os exemplos!"));
            }
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
    }

    private JMenuItem obterSubniveis(File diretorioAtual, String[] entradaIndice, Icon iconeDiretorio, Icon iconeArquivo) throws Exception
    {
        File caminho = new File(diretorioAtual, entradaIndice[1]);
        
        if (caminho.isDirectory())
        {
            JMenu submenu = new JMenu(entradaIndice[0]);

            submenu.setIcon(iconeDiretorio);
            submenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            List<String[]> entradasIndiceSubDiretorio = lerIndice(new File(caminho, "indice.txt"));

            for (String[] entradaIndiceSubDiretorio : entradasIndiceSubDiretorio)
            {
                JMenuItem item = obterSubniveis(caminho, entradaIndiceSubDiretorio, iconeDiretorio, iconeArquivo);

                if (item != null)
                {
                    submenu.add(item);
                }
            }

            if (submenu.getSubElements().length > 0)
            {
                return submenu;
            }
        }
        else
        {
            JMenuItem item = new JMenuItem(new AbstractAction(entradaIndice[0], iconeArquivo)
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    try
                    {
                        File exemplo = new File(((JMenuItem) e.getSource()).getName());
                        String codigoFonte = FileHandle.open(exemplo);
                        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.criaNovaAba();
                        abaCodigoFonte.setCodigoFonte(codigoFonte, exemplo, false);
                        getPainelTabulado().add(abaCodigoFonte);
                        //abaCodigoFonte.adicionar(getPainelTabulado());
                    }
                    catch (Exception excecao)
                    {
                        PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
                    }
                }
            });

            item.setName(caminho.getAbsolutePath());
            item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            return item;
        }

        return null;
    }
    
    private List<String[]> lerIndice(File arquivoIndice) throws Exception
    {
        if (arquivoIndice.exists())
        {
            int cont = 0;
            String linha;
            List<String[]> indice = new ArrayList<>();
            
            try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream(arquivoIndice), "UTF-8")))
            {
                while ((linha = leitor.readLine()) != null)
                {
                    cont += 1;
                    
                    if (linha.trim().length() >= 3 && linha.contains("="))
                    {
                        String[] entrada = linha.split("=");
                        
                        if (entrada.length == 2)
                        {
                            indice.add(entrada);
                        }
                        else
                        {
                            throw new Exception(String.format("A entrada %d do arquivo de índice é inválida: ", cont, entrada));
                        }
                    }
                }
                
                leitor.close();
            }
            
            return indice;
        }
        else
        {
            throw new Exception(String.format("O arquivo de índice não foi encontrado no diretório: %s", arquivoIndice.getCanonicalPath()));
        }        
    }

    private void abrirGitHub()
    {
        try
        {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/Univali-l2s/Portugol"));
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(AbaInicial.this, "Não foi possível abrir o seu navegador de Internet!\nPara auxiliar no desenvolvimento do projeto, por favor acesse o seguinte endereço:\n\nhttps://github.com/Univali-l2s/Portugol", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void registrarListener(PackDownloader packDownloader)
    {
        packDownloader.addListener(new PackDownloaderListener()
        {
            @Override
            public void downloadStarted()
            {
                rotuloExplorarExemplos.setEnabled(false);
            }

            @Override
            public void downloadFinished()
            {
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        criarMenuExemplos();
                        rotuloExplorarExemplos.setEnabled(true);
                    }
                });
            }

            @Override
            public void downloadProgress(final int bytesDownloaded, final int totalBytes)
            {
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run()
                    {
                        //int progresso = (int)((bytesDownloaded * 100.0f )/totalBytes );
                    }
                });
            }

            @Override
            public void downloadFail(PackDownloaderException ex)
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
        });
    }

    private void configurarCursorLogos()
    {
        logoUnivali.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void configurarAcoes()
    {
        configurarAcaoSairProgramando();
        configurarAcaoConhecerLinguagem();
        configurarAcaoAssistirVideoAulas();
        configurarAcaoConhecerBibliotecas();
        configurarAcaoExibirTelaSobre();
        configurarAcaoAjudarDesenvolvimento();
        configurarAcaoRelatarBug();
        
        configurarAcaoExibirAtalhosTeclado();
    }

    private void configurarAcaoAssistirVideoAulas()
    {
         Action acao = new AbstractAction(rotuloAssistirVideoAulas.getName())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try 
                {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.youtube.com/user/portugolstudio"));
                }
                catch (IOException ex) 
                {
                    JOptionPane.showMessageDialog(AbaInicial.this, "Não foi possível abrir o seu navegador de Internet!\nPara assistir às vídeo aulas, acesse o seguinte endereço:\n\nhttps://www.youtube.com/user/portugolstudio", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

        getActionMap().put(rotuloAssistirVideoAulas.getName(), acao);
    }
    
    private void configurarAcaoSairProgramando()
    {
        Action acao = new AbstractAction(rotuloSairProgramando.getName())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getPainelTabulado().getActionMap().get(BotoesControleAba.ACAO_NOVO_ARQUIVO).actionPerformed(e);
                Action action = getPainelTabulado().getActionMap().get(BotoesControleAba.ACAO_NOVO_ARQUIVO);
                System.out.println("nome da classe da Action: " + action.getClass().getName());
            }
        };

        getActionMap().put(rotuloSairProgramando.getName(), acao);
    }

    private void configurarAcaoConhecerLinguagem()
    {
        Action acao = new AbstractAction(rotuloConhecerLinguagem.getName())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getPainelTabulado().getActionMap().get(PainelTabuladoPrincipal.ACAO_EXIBIR_AJUDA).actionPerformed(e);
            }
        };

        getActionMap().put(rotuloConhecerLinguagem.getName(), acao);
    }

    private void configurarAcaoConhecerBibliotecas()
    {
        Action acao = new AbstractAction(rotuloConhecerBibliotecas.getName())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getPainelTabulado().getActionMap().get(PainelTabuladoPrincipal.ACAO_EXIBIR_DOCUMENTACAO_BIBLIOTECA).actionPerformed(e);
            }
        };

        getActionMap().put(rotuloConhecerBibliotecas.getName(), acao);
    }

    private void configurarAcaoExibirTelaSobre()
    {
        Action acao = new AbstractAction(rotuloInformacoesSoftware.getName())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PortugolStudio.getInstancia().getTelaSobre().setVisible(true);
            }
        };

        getActionMap().put(rotuloInformacoesSoftware.getName(), acao);
    }

    private void configurarAcaoAjudarDesenvolvimento()
    {
        Action acao = new AbstractAction(rotuloAjudarDesenvolvimento.getName())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                abrirGitHub();
            }
        };

        getActionMap().put(rotuloAjudarDesenvolvimento.getName(), acao);
    }

    private void configurarAcaoRelatarBug()
    {
        Action acao = new AbstractAction(rotuloRelatarBug.getName())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://docs.google.com/forms/d/1PfTW-mDrkv1PVYYB8UedH9x9hNJgMz8TnxqYgsjIwLE/viewform"));
                }
                catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(AbaInicial.this, "Não foi possível abrir o seu navegador de Internet!\nPara relatar um bug, por favor acesse o seguinte endereço:\n\nhttps://docs.google.com/forms/d/1PfTW-mDrkv1PVYYB8UedH9x9hNJgMz8TnxqYgsjIwLE/viewform", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

        getActionMap().put(rotuloRelatarBug.getName(), acao);
    }

    private void configurarLinks()
    {
        MouseListener listener = new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JLabel rotulo = (JLabel) e.getSource();
                String nomeAcao = rotulo.getName();

                Action acao = getActionMap().get(nomeAcao);

                if (acao != null)
                {
                    acao.actionPerformed(null);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                JLabel rotulo = (JLabel) e.getSource();
                rotulo.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                JLabel rotulo = (JLabel) e.getSource();
                rotulo.setForeground(Color.WHITE);
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

        rotuloInformacoesSoftware.addMouseListener(listener);
        rotuloInformacoesSoftware.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rotuloExplorarExemplos.addMouseListener(listener);
        rotuloExplorarExemplos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void configurarAcaoExplorarExemplos()
    {
        String nome = "explorarExemplos";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.ALT_DOWN_MASK);

        acaoExplorarExemplos = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (menuExemplos != null)
                {
                    selecionar();
                    menuExemplos.show(rotuloExplorarExemplos, 0, rotuloExplorarExemplos.getHeight());
                }
                else
                {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao("Não foi possível carregar os exemplos", ExcecaoAplicacao.Tipo.ERRO));
                }
            }
        };

        acaoExplorarExemplos.putValue(Action.ACCELERATOR_KEY, atalho);

        getActionMap().put(nome, acaoExplorarExemplos);

        getPainelTabulado().getActionMap().put(nome, acaoExplorarExemplos);
        getPainelTabulado().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoExibirAtalhosTeclado()
    {
        String nome = "atalhosTeclado";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0);

        acaoExibirAtalhosTeclado = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                telaAtalhosTeclado.setLocationRelativeTo(null);
                telaAtalhosTeclado.setVisible(true);
            }
        };

        acaoExibirAtalhosTeclado.putValue(Action.ACCELERATOR_KEY, atalho);

        getActionMap().put(nome, acaoExibirAtalhosTeclado);

        getPainelTabulado().getActionMap().put(nome, acaoExibirAtalhosTeclado);
        getPainelTabulado().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void criarDicasInterface()
    {
        FabricaDicasInterface.criarDicaInterface(logoUnivali, "Conhecer o curso de Ciência da Computação da UNIVALI", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.NORTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelGradiente = new br.univali.ps.ui.imagens.Gradiente();
        painelCabecalho = new javax.swing.JPanel();
        painelCentralizacaoLogo = new javax.swing.JPanel();
        logoPS = new br.univali.ps.ui.imagens.Logo();
        rotuloSlogan = new javax.swing.JLabel();
        painelConteudo = new javax.swing.JPanel();
        painelAprender = new javax.swing.JPanel();
        painelTituloAprender = new javax.swing.JPanel();
        rotuloAprender = new javax.swing.JLabel();
        separador1 = new javax.swing.JSeparator();
        conteudoAprender = new javax.swing.JPanel();
        painelAlinhamento5 = new javax.swing.JPanel();
        rotuloFormaAprender = new javax.swing.JLabel();
        rotuloSairProgramando = new javax.swing.JLabel();
        rotuloAssistirVideoAulas = new javax.swing.JLabel();
        rotuloConhecerLinguagem = new javax.swing.JLabel();
        rotuloConhecerBibliotecas = new javax.swing.JLabel();
        rotuloAtalhosTeclado = new javax.swing.JLabel();
        rotuloExplorarExemplos = new javax.swing.JLabel();
        painelColaborarCreditos = new javax.swing.JPanel();
        painelColaborar = new javax.swing.JPanel();
        painelTituloColaborar = new javax.swing.JPanel();
        rotuloColaborar = new javax.swing.JLabel();
        separador3 = new javax.swing.JSeparator();
        conteudoColaborar = new javax.swing.JPanel();
        painelAlinhamento1 = new javax.swing.JPanel();
        rotuloSoftwareLivre = new javax.swing.JLabel();
        rotuloColabore = new javax.swing.JLabel();
        rotuloRelatarBug = new javax.swing.JLabel();
        rotuloAjudarDesenvolvimento = new javax.swing.JLabel();
        painelCreditos = new javax.swing.JPanel();
        painelTituloCreditos = new javax.swing.JPanel();
        rotuloCreditos = new javax.swing.JLabel();
        separador4 = new javax.swing.JSeparator();
        conteudoCreditos = new javax.swing.JPanel();
        painelAlinhamento3 = new javax.swing.JPanel();
        rotuloFormaAprender8 = new javax.swing.JLabel();
        rotuloInformacoesSoftware = new javax.swing.JLabel();
        painelNovidades = new javax.swing.JPanel();
        painelTituloNovidades = new javax.swing.JPanel();
        rotuloNovidades = new javax.swing.JLabel();
        separador2 = new javax.swing.JSeparator();
        conteudoAprender1 = new javax.swing.JPanel();
        rotuloFormaAprender1 = new javax.swing.JLabel();
        rotuloFormaAprender2 = new javax.swing.JLabel();
        rotuloFormaAprender3 = new javax.swing.JLabel();
        rotuloFormaAprender4 = new javax.swing.JLabel();
        rotuloFormaAprender5 = new javax.swing.JLabel();
        rotuloFormaAprender6 = new javax.swing.JLabel();
        rotuloFormaAprender7 = new javax.swing.JLabel();
        rotuloFormaAprender9 = new javax.swing.JLabel();
        rotuloFormaAprender10 = new javax.swing.JLabel();
        painelRodape = new javax.swing.JPanel();
        painelAlinhamento7 = new javax.swing.JPanel();
        logoUnivali = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        painelGradiente.setLayout(new java.awt.BorderLayout());

        painelCabecalho.setOpaque(false);
        painelCabecalho.setLayout(new java.awt.BorderLayout());

        painelCentralizacaoLogo.setOpaque(false);

        logoPS.setOpaque(false);
        painelCentralizacaoLogo.add(logoPS);

        painelCabecalho.add(painelCentralizacaoLogo, java.awt.BorderLayout.CENTER);

        rotuloSlogan.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        rotuloSlogan.setForeground(new java.awt.Color(255, 255, 255));
        rotuloSlogan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloSlogan.setText("Ambiente para Aprender a Programar");
        rotuloSlogan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        painelCabecalho.add(rotuloSlogan, java.awt.BorderLayout.SOUTH);

        painelGradiente.add(painelCabecalho, java.awt.BorderLayout.NORTH);

        painelConteudo.setBackground(new Color(0.9f,0.9f,0.9f,0.45f));
        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(35, 50, 15, 50));
        painelConteudo.setOpaque(false);
        painelConteudo.setPreferredSize(new java.awt.Dimension(700, 80));
        painelConteudo.setRequestFocusEnabled(false);
        painelConteudo.setLayout(new java.awt.GridLayout(1, 3, 70, 0));

        painelAprender.setOpaque(false);
        painelAprender.setLayout(new java.awt.BorderLayout());

        painelTituloAprender.setOpaque(false);
        painelTituloAprender.setLayout(new java.awt.BorderLayout());

        rotuloAprender.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        rotuloAprender.setForeground(new java.awt.Color(255, 255, 255));
        rotuloAprender.setText("Aprender");
        rotuloAprender.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));
        painelTituloAprender.add(rotuloAprender, java.awt.BorderLayout.PAGE_START);
        painelTituloAprender.add(separador1, java.awt.BorderLayout.CENTER);

        painelAprender.add(painelTituloAprender, java.awt.BorderLayout.NORTH);

        conteudoAprender.setOpaque(false);
        conteudoAprender.setLayout(new java.awt.BorderLayout());

        painelAlinhamento5.setOpaque(false);
        painelAlinhamento5.setLayout(new javax.swing.BoxLayout(painelAlinhamento5, javax.swing.BoxLayout.Y_AXIS));

        rotuloFormaAprender.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender.setText("<html><body><div>Escolha sua forma de aprender:</div></body></html>");
        rotuloFormaAprender.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        painelAlinhamento5.add(rotuloFormaAprender);

        rotuloSairProgramando.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloSairProgramando.setForeground(new java.awt.Color(255, 255, 255));
        rotuloSairProgramando.setText("<html><body><div>:: <u>Sair Programando</u> (Ctrl + N)</div></body></html>");
        rotuloSairProgramando.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloSairProgramando.setName("sairProgramando"); // NOI18N
        painelAlinhamento5.add(rotuloSairProgramando);

        rotuloAssistirVideoAulas.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloAssistirVideoAulas.setForeground(new java.awt.Color(255, 255, 255));
        rotuloAssistirVideoAulas.setText("<html><body><div>:: <u>Assistir Vídeoaulas</u></div></body></html>");
        rotuloAssistirVideoAulas.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloAssistirVideoAulas.setName("assistirVideoAulas"); // NOI18N
        painelAlinhamento5.add(rotuloAssistirVideoAulas);

        rotuloConhecerLinguagem.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloConhecerLinguagem.setForeground(new java.awt.Color(255, 255, 255));
        rotuloConhecerLinguagem.setText("<html><body><div>:: <u>Conhecer a Linguagem</u> (F1)</div></body></html>");
        rotuloConhecerLinguagem.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloConhecerLinguagem.setName("conhecerLinguagem"); // NOI18N
        painelAlinhamento5.add(rotuloConhecerLinguagem);

        rotuloConhecerBibliotecas.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloConhecerBibliotecas.setForeground(new java.awt.Color(255, 255, 255));
        rotuloConhecerBibliotecas.setText("<html><body><div>:: <u>Conhecer as Bibliotecas</u> (Shift + F1)</div></body></html>");
        rotuloConhecerBibliotecas.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloConhecerBibliotecas.setName("conhecerBibliotecas"); // NOI18N
        painelAlinhamento5.add(rotuloConhecerBibliotecas);

        rotuloAtalhosTeclado.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloAtalhosTeclado.setForeground(new java.awt.Color(255, 255, 255));
        rotuloAtalhosTeclado.setText("<html><body><div>:: <u>Atalhos do teclado</u> (F11)</div></body></html>");
        rotuloAtalhosTeclado.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloAtalhosTeclado.setName("atalhosTeclado"); // NOI18N
        painelAlinhamento5.add(rotuloAtalhosTeclado);

        rotuloExplorarExemplos.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloExplorarExemplos.setForeground(new java.awt.Color(255, 255, 255));
        rotuloExplorarExemplos.setText("<html><body><div>:: <u>Explorar os Exemplos</u> (Alt+E)</div></body></html>");
        rotuloExplorarExemplos.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloExplorarExemplos.setName("explorarExemplos"); // NOI18N
        painelAlinhamento5.add(rotuloExplorarExemplos);

        conteudoAprender.add(painelAlinhamento5, java.awt.BorderLayout.NORTH);

        painelAprender.add(conteudoAprender, java.awt.BorderLayout.CENTER);

        painelConteudo.add(painelAprender);

        painelColaborarCreditos.setOpaque(false);
        painelColaborarCreditos.setLayout(new javax.swing.BoxLayout(painelColaborarCreditos, javax.swing.BoxLayout.Y_AXIS));

        painelColaborar.setOpaque(false);
        painelColaborar.setPreferredSize(new java.awt.Dimension(270, 170));
        painelColaborar.setLayout(new java.awt.BorderLayout());

        painelTituloColaborar.setOpaque(false);
        painelTituloColaborar.setLayout(new java.awt.BorderLayout());

        rotuloColaborar.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        rotuloColaborar.setForeground(new java.awt.Color(255, 255, 255));
        rotuloColaborar.setText("Colaborar");
        rotuloColaborar.setToolTipText("");
        rotuloColaborar.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));
        painelTituloColaborar.add(rotuloColaborar, java.awt.BorderLayout.PAGE_START);
        painelTituloColaborar.add(separador3, java.awt.BorderLayout.CENTER);

        painelColaborar.add(painelTituloColaborar, java.awt.BorderLayout.NORTH);

        conteudoColaborar.setOpaque(false);
        conteudoColaborar.setPreferredSize(new java.awt.Dimension(296, 50));
        conteudoColaborar.setLayout(new java.awt.BorderLayout());

        painelAlinhamento1.setOpaque(false);
        painelAlinhamento1.setPreferredSize(new java.awt.Dimension(296, 10));
        painelAlinhamento1.setLayout(new javax.swing.BoxLayout(painelAlinhamento1, javax.swing.BoxLayout.Y_AXIS));

        rotuloSoftwareLivre.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloSoftwareLivre.setForeground(new java.awt.Color(255, 255, 255));
        rotuloSoftwareLivre.setText("<html><body><div>O Portugol Studio é software livre!</div></body></html>");
        rotuloSoftwareLivre.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        painelAlinhamento1.add(rotuloSoftwareLivre);

        rotuloColabore.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloColabore.setForeground(new java.awt.Color(255, 255, 255));
        rotuloColabore.setText("<html><body><div>Colabore:</div></body></html>");
        rotuloColabore.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloColabore.setName("sairProgramando"); // NOI18N
        painelAlinhamento1.add(rotuloColabore);

        rotuloRelatarBug.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloRelatarBug.setForeground(new java.awt.Color(255, 255, 255));
        rotuloRelatarBug.setText("<html><body><div>:: <u>Relatar um Bug</u></div></body></html>");
        rotuloRelatarBug.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloRelatarBug.setName("relatarBug"); // NOI18N
        painelAlinhamento1.add(rotuloRelatarBug);

        rotuloAjudarDesenvolvimento.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloAjudarDesenvolvimento.setForeground(new java.awt.Color(255, 255, 255));
        rotuloAjudarDesenvolvimento.setText("<html><body><div>:: <u>Ajudar no desenvolvimento</u></div></body></html>");
        rotuloAjudarDesenvolvimento.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloAjudarDesenvolvimento.setName("ajudarDesenvolvimento"); // NOI18N
        painelAlinhamento1.add(rotuloAjudarDesenvolvimento);

        conteudoColaborar.add(painelAlinhamento1, java.awt.BorderLayout.CENTER);

        painelColaborar.add(conteudoColaborar, java.awt.BorderLayout.CENTER);

        painelColaborarCreditos.add(painelColaborar);

        painelCreditos.setOpaque(false);
        painelCreditos.setLayout(new java.awt.BorderLayout());

        painelTituloCreditos.setOpaque(false);
        painelTituloCreditos.setLayout(new java.awt.BorderLayout());

        rotuloCreditos.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        rotuloCreditos.setForeground(new java.awt.Color(255, 255, 255));
        rotuloCreditos.setText("Créditos");
        rotuloCreditos.setToolTipText("");
        rotuloCreditos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));
        painelTituloCreditos.add(rotuloCreditos, java.awt.BorderLayout.PAGE_START);
        painelTituloCreditos.add(separador4, java.awt.BorderLayout.CENTER);

        painelCreditos.add(painelTituloCreditos, java.awt.BorderLayout.NORTH);

        conteudoCreditos.setOpaque(false);
        conteudoCreditos.setLayout(new java.awt.BorderLayout());

        painelAlinhamento3.setOpaque(false);
        painelAlinhamento3.setLayout(new javax.swing.BoxLayout(painelAlinhamento3, javax.swing.BoxLayout.Y_AXIS));

        rotuloFormaAprender8.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender8.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender8.setText("<html><body><div>O PortugolStudio é desenvolvido na UNIVALI pelo Grupo de Informática na Educação</div></body></html>");
        rotuloFormaAprender8.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        painelAlinhamento3.add(rotuloFormaAprender8);

        rotuloInformacoesSoftware.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloInformacoesSoftware.setForeground(new java.awt.Color(255, 255, 255));
        rotuloInformacoesSoftware.setText("<html><body><div>:: <u>Informações do Software</u> (F12)</div></body></html>");
        rotuloInformacoesSoftware.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        rotuloInformacoesSoftware.setName("informacoesSoftware"); // NOI18N
        painelAlinhamento3.add(rotuloInformacoesSoftware);

        conteudoCreditos.add(painelAlinhamento3, java.awt.BorderLayout.CENTER);

        painelCreditos.add(conteudoCreditos, java.awt.BorderLayout.CENTER);

        painelColaborarCreditos.add(painelCreditos);

        painelConteudo.add(painelColaborarCreditos);

        painelNovidades.setOpaque(false);
        painelNovidades.setLayout(new java.awt.BorderLayout());

        painelTituloNovidades.setOpaque(false);
        painelTituloNovidades.setLayout(new java.awt.BorderLayout());

        rotuloNovidades.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        rotuloNovidades.setForeground(new java.awt.Color(255, 255, 255));
        rotuloNovidades.setText("Novidades");
        rotuloNovidades.setToolTipText("");
        rotuloNovidades.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));
        painelTituloNovidades.add(rotuloNovidades, java.awt.BorderLayout.PAGE_START);
        painelTituloNovidades.add(separador2, java.awt.BorderLayout.CENTER);

        painelNovidades.add(painelTituloNovidades, java.awt.BorderLayout.NORTH);

        conteudoAprender1.setOpaque(false);
        conteudoAprender1.setLayout(new javax.swing.BoxLayout(conteudoAprender1, javax.swing.BoxLayout.Y_AXIS));

        rotuloFormaAprender1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender1.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender1.setText("<html><body><div>Novidades da versão 2.0 (19/08/2013)</div></body></html>");
        rotuloFormaAprender1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender1);

        rotuloFormaAprender2.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender2.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender2.setText("<html><body><div>:: Depurador de Código</div></body></html>");
        rotuloFormaAprender2.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender2);

        rotuloFormaAprender3.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender3.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender3.setText("<html><body><div>:: Árvore de Símbolos</div></body></html>");
        rotuloFormaAprender3.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender3);

        rotuloFormaAprender4.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender4.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender4.setText("<html><body><div>:: Suporte a Bibliotecas</div></body></html>");
        rotuloFormaAprender4.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender4);

        rotuloFormaAprender5.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender5.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender5.setText("<html><body><div>:: Novo Sistema de Ajuda</div></body></html>");
        rotuloFormaAprender5.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender5);

        rotuloFormaAprender6.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender6.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender6.setText("<html><body><div>:: Novos Exemplos</div></body></html>");
        rotuloFormaAprender6.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender6);

        rotuloFormaAprender7.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender7.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender7.setText("<html><body><div>:: Melhorias na análise semântica</div></body></html>");
        rotuloFormaAprender7.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender7);

        rotuloFormaAprender9.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender9.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender9.setText("<html><body><div>:: Inúmeros Bugs corrigidos</div></body></html>");
        rotuloFormaAprender9.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender9);

        rotuloFormaAprender10.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        rotuloFormaAprender10.setForeground(new java.awt.Color(255, 255, 255));
        rotuloFormaAprender10.setText("<html><body><div>:: Em Breve: Corretor automático</div></body></html>");
        rotuloFormaAprender10.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
        conteudoAprender1.add(rotuloFormaAprender10);

        painelNovidades.add(conteudoAprender1, java.awt.BorderLayout.CENTER);

        painelConteudo.add(painelNovidades);

        painelGradiente.add(painelConteudo, java.awt.BorderLayout.CENTER);

        painelRodape.setOpaque(false);
        painelRodape.setPreferredSize(new java.awt.Dimension(10, 80));
        painelRodape.setLayout(new java.awt.BorderLayout());

        painelAlinhamento7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 50, 0, 10));
        painelAlinhamento7.setOpaque(false);
        painelAlinhamento7.setPreferredSize(new java.awt.Dimension(200, 64));
        painelAlinhamento7.setLayout(new java.awt.BorderLayout());

        logoUnivali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoUnivali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/univali.png"))); // NOI18N
        logoUnivali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoUnivaliMouseClicked(evt);
            }
        });
        painelAlinhamento7.add(logoUnivali, java.awt.BorderLayout.WEST);

        painelRodape.add(painelAlinhamento7, java.awt.BorderLayout.WEST);

        painelGradiente.add(painelRodape, java.awt.BorderLayout.SOUTH);

        add(painelGradiente, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void logoUnivaliMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_logoUnivaliMouseClicked
    {//GEN-HEADEREND:event_logoUnivaliMouseClicked
        try
        {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://www.univali.br/computacao"));
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(AbaInicial.this, "Não foi possível abrir o seu navegador de Internet!\nPara conhecer o curso de computação da UNIVALI, por favor acesse o seguinte endereço:\n\nhttp://www.univali.br/computacao", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_logoUnivaliMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel conteudoAprender;
    private javax.swing.JPanel conteudoAprender1;
    private javax.swing.JPanel conteudoColaborar;
    private javax.swing.JPanel conteudoCreditos;
    private br.univali.ps.ui.imagens.Logo logoPS;
    private javax.swing.JLabel logoUnivali;
    private javax.swing.JPanel painelAlinhamento1;
    private javax.swing.JPanel painelAlinhamento3;
    private javax.swing.JPanel painelAlinhamento5;
    private javax.swing.JPanel painelAlinhamento7;
    private javax.swing.JPanel painelAprender;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JPanel painelCentralizacaoLogo;
    private javax.swing.JPanel painelColaborar;
    private javax.swing.JPanel painelColaborarCreditos;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelCreditos;
    private br.univali.ps.ui.imagens.Gradiente painelGradiente;
    private javax.swing.JPanel painelNovidades;
    private javax.swing.JPanel painelRodape;
    private javax.swing.JPanel painelTituloAprender;
    private javax.swing.JPanel painelTituloColaborar;
    private javax.swing.JPanel painelTituloCreditos;
    private javax.swing.JPanel painelTituloNovidades;
    private javax.swing.JLabel rotuloAjudarDesenvolvimento;
    private javax.swing.JLabel rotuloAprender;
    private javax.swing.JLabel rotuloAssistirVideoAulas;
    private javax.swing.JLabel rotuloAtalhosTeclado;
    private javax.swing.JLabel rotuloColaborar;
    private javax.swing.JLabel rotuloColabore;
    private javax.swing.JLabel rotuloConhecerBibliotecas;
    private javax.swing.JLabel rotuloConhecerLinguagem;
    private javax.swing.JLabel rotuloCreditos;
    private javax.swing.JLabel rotuloExplorarExemplos;
    private javax.swing.JLabel rotuloFormaAprender;
    private javax.swing.JLabel rotuloFormaAprender1;
    private javax.swing.JLabel rotuloFormaAprender10;
    private javax.swing.JLabel rotuloFormaAprender2;
    private javax.swing.JLabel rotuloFormaAprender3;
    private javax.swing.JLabel rotuloFormaAprender4;
    private javax.swing.JLabel rotuloFormaAprender5;
    private javax.swing.JLabel rotuloFormaAprender6;
    private javax.swing.JLabel rotuloFormaAprender7;
    private javax.swing.JLabel rotuloFormaAprender8;
    private javax.swing.JLabel rotuloFormaAprender9;
    private javax.swing.JLabel rotuloInformacoesSoftware;
    private javax.swing.JLabel rotuloNovidades;
    private javax.swing.JLabel rotuloRelatarBug;
    private javax.swing.JLabel rotuloSairProgramando;
    private javax.swing.JLabel rotuloSlogan;
    private javax.swing.JLabel rotuloSoftwareLivre;
    private javax.swing.JSeparator separador1;
    private javax.swing.JSeparator separador2;
    private javax.swing.JSeparator separador3;
    private javax.swing.JSeparator separador4;
    // End of variables declaration//GEN-END:variables
}
