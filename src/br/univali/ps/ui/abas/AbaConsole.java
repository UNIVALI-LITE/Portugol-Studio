package br.univali.ps.ui.abas;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.es.Armazenador;
import br.univali.portugol.nucleo.execucao.es.Entrada;
import br.univali.portugol.nucleo.execucao.es.Saida;
import br.univali.ps.ui.Configuracoes;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.EdgedBalloonStyle;
import net.java.balloontip.utils.FadingUtils;

public final class AbaConsole extends Aba implements PropertyChangeListener
{

    private static final float VALOR_INCREMENTO_FONTE = 2.0f;
    private static final float TAMANHO_MAXIMO_FONTE = 50.0f;
    private static final float TAMANHO_MINIMO_FONTE = 10.0f;

    private static final Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "application_xp_terminal.png");

    private boolean executandoPrograma = false;

    private BalloonTip popupLeia;
    private final JLabel rotuloPopupLeia;
    private final BalloonTipStyle estiloPopupLeia;
    private final ActionListener foo;
    private Timer timerPopupLeia;
    private boolean removendoPopup = true;

    private Action acaoAumentarFonte;
    private Action acaoDiminuirFonte;
    private Action acaoLimpar;
    private Action acaoCopiar;

    private final HandlerDaSaida handlerDaSaida;
    private AbaCodigoFonte abaCodigoFonte;

    public AbaConsole()
    {
        super("Console", icone, false);

        initComponents();
        console.setComponentPopupMenu(menuConsole);
        this.menuConsoleLimpar.setText("Limpar");
        this.menuConsoleCopiar.setText("Copiar");
        this.menuAumentarFonte.setText("Aumentar fonte");
        this.menuDiminuirFonte.setText("Diminuir fonte");
        console.setDocument(new DocumentoConsole());

        rotuloPopupLeia = new JLabel("<html><body><p>O programa está aguardando a entrada de dados</p></body></html>", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png"), JLabel.LEFT);
        rotuloPopupLeia.setVerticalTextPosition(JLabel.CENTER);
        rotuloPopupLeia.setIconTextGap(10);
        rotuloPopupLeia.setPreferredSize(new Dimension(230, 70));
        rotuloPopupLeia.setFont(rotuloPopupLeia.getFont().deriveFont(12f));

        estiloPopupLeia = new EdgedBalloonStyle(new Color(255, 255, 210), Color.BLACK);
        popupLeia = new BalloonTip(painelRolagem.getViewport(), rotuloPopupLeia, estiloPopupLeia, BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.NORTH, 50, 25, false);
        popupLeia.setPadding(8);
        popupLeia.setVisible(false);

        foo = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (removendoPopup)
                {
                    popupLeia.setVisible(false);
                }/*
                 * else
                 * {
                 * console.requestFocusInWindow();
                 * console.setCaretPosition(console.getText().length());
                 * }
                 */

            }
        };

        console.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent ce)
            {
                painelRolagem.getVerticalScrollBar().setValue(ce.getComponent().getHeight());
            }
        });
        console.getDocument().addDocumentListener(new DocumentListener()
        {
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

        console.setCaret(new CursorConsole());
        console.getCaret().setVisible(false);

        configurarBarraFerramentas();
        criarAcoes();
        criarDicasInterface();
        instalarObservadores();
        console.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));
        carregarConfiguracoes();

        handlerDaSaida = new HandlerDaSaida();
    }

    private void configurarBarraFerramentas()
    {
        barraFerramentas.setOpaque(false);
        botaoAumentarFonte.setOpaque(false);
        botaoCopiar.setOpaque(false);
        botaoDiminuirFonte.setOpaque(false);
        botaoLimpar.setOpaque(false);
    }

    private void criarAcoes()
    {
        criarAcaoLimpar();
        criarAcaoCopiar();
        criarAcaoAumentarFonte();
        criarAcaoDiminuirFonte();
    }

    public void setAbaCodigoFonte(AbaCodigoFonte abaCodigoFonte)
    {
        this.abaCodigoFonte = abaCodigoFonte;
    }

    public void limparConsole()
    {
        console.setText(null);
    }

    public void escreverNoConsole(final String msg)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                console.append(msg);
            }
        });

    }

    private void criarAcaoLimpar()
    {
        acaoLimpar = new AbstractAction("Limpar", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "edit_clear.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                limparConsole();
            }
        };

        acaoLimpar.setEnabled(false);

        menuConsoleLimpar.setAction(acaoLimpar);
        botaoLimpar.setAction(acaoLimpar);
    }

    private void criarAcaoCopiar()
    {
        acaoCopiar = new AbstractAction("Copiar", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_copy.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                console.selectAll();
                console.copy();
                console.select(0, 0);
            }
        };

        acaoCopiar.setEnabled(false);

        menuConsoleCopiar.setAction(acaoCopiar);
        botaoCopiar.setAction(acaoCopiar);
    }

    private void criarAcaoAumentarFonte()
    {
        acaoAumentarFonte = new AbstractAction("Aumentar Fonte", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font_add.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Font fonteAtual = console.getFont();
                float novoTamanho = fonteAtual.getSize() + VALOR_INCREMENTO_FONTE;

                setTamanhoFonteConsole(novoTamanho);
            }
        };

        menuAumentarFonte.setAction(acaoAumentarFonte);
        botaoAumentarFonte.setAction(acaoAumentarFonte);
    }

    private void criarAcaoDiminuirFonte()
    {
        acaoDiminuirFonte = new AbstractAction("Diminuir Fonte", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font_delete.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Font fonteAtual = console.getFont();
                float novoTamanho = fonteAtual.getSize() - VALOR_INCREMENTO_FONTE;

                setTamanhoFonteConsole(novoTamanho);
            }
        };

        menuDiminuirFonte.setAction(acaoDiminuirFonte);
        botaoDiminuirFonte.setAction(acaoDiminuirFonte);
    }

    private void criarDicasInterface()
    {
        FabricaDicasInterface.criarDicaInterface(botaoLimpar, "Limpa o texto existente no console", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(botaoCopiar, "Copia o texto existente no console", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(botaoAumentarFonte, "Aumenta o tamanho da fonte do console", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(botaoDiminuirFonte, "Diminui o tamanho da fonte do console", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
    }

    private void instalarObservadores()
    {
        Configuracoes configuracoes = Configuracoes.getInstancia();

        configuracoes.adicionarObservadorConfiguracao(this, Configuracoes.TAMANHO_FONTE_CONSOLE);
    }

    private void carregarConfiguracoes()
    {
        Configuracoes configuracoes = Configuracoes.getInstancia();

        setTamanhoFonteConsole(configuracoes.getTamanhoFonteConsole());
    }

    private void setTamanhoFonteConsole(float tamanho)
    {
        if ((tamanho != console.getFont().getSize()) && (tamanho >= TAMANHO_MINIMO_FONTE) && (tamanho <= TAMANHO_MAXIMO_FONTE))
        {
            console.setFont(console.getFont().deriveFont(tamanho));
            Configuracoes.getInstancia().setTamanhoFonteConsole(tamanho);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals(Configuracoes.TAMANHO_FONTE_CONSOLE))
        {
            setTamanhoFonteConsole((Float) evt.getNewValue());
        }
    }

    /*
     * Por algum motivo o método atualizarItensMenuConsole()
     * buga o limpar() se for chamado durante a execução do programa.
     *
     * Para prevenir o bug, desativamos a execução deste método
     * enquanto o programa está rodando.
     *
     * Para que o menu seja atualizado corretamente ao terminar a
     * execução do programa, o método setExecutandoPrograma()
     * chama manualmente o método atualizarItensMenuConsole()
     *
     * Testei atualizar usando um SwingWorker. Funciona perfeitamente,
     * porém deixa o escreva muito mais lento. Por isso, optei por deixar
     * como está, atualizando somente no final da execução.
     *
     * Talvez possamos remover o método setExecutando() e tornar o
     * método atualizarItensMenuConsole() público para acessá-lo nos
     * métodos execucaoIniciada() e execucaoEncerrada()
     *
     */
    public void setExecutandoPrograma(boolean executandoPrograma)
    {
        this.executandoPrograma = executandoPrograma;
        if (!executandoPrograma)
        {
            ((DocumentoConsole) console.getDocument()).setLendo(false);
            console.setEditable(false);
            console.setFocusable(false);
        }
        atualizarItensMenuConsole();
    }

    private void atualizarItensMenuConsole()
    {
        if (!executandoPrograma)
        {
            if (console.getText() != null)
            {
                if (console.getText().length() > 0)
                {
                    acaoLimpar.setEnabled(true);

                    int selecao = console.getSelectionEnd() - console.getSelectionStart();

                    if (selecao > 0)
                    {
                        acaoCopiar.setEnabled(true);
                    }
                    else
                    {
                        acaoCopiar.setEnabled(false);
                    }
                }
                else
                {
                    acaoLimpar.setEnabled(false);
                    acaoCopiar.setEnabled(false);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        menuConsole = new javax.swing.JPopupMenu();
        menuConsoleLimpar = new javax.swing.JMenuItem();
        menuConsoleCopiar = new javax.swing.JMenuItem();
        menuAumentarFonte = new javax.swing.JMenuItem();
        menuDiminuirFonte = new javax.swing.JMenuItem();
        painelConteudo = new javax.swing.JPanel();
        painelRolagem = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        painelFerramentas = new javax.swing.JPanel();
        barraFerramentas = new javax.swing.JToolBar();
        botaoAumentarFonte = new javax.swing.JButton();
        botaoDiminuirFonte = new javax.swing.JButton();
        botaoLimpar = new javax.swing.JButton();
        botaoCopiar = new javax.swing.JButton();

        menuConsoleLimpar.setText("jMenuItem1");
        menuConsole.add(menuConsoleLimpar);

        menuConsoleCopiar.setText("jMenuItem2");
        menuConsole.add(menuConsoleCopiar);

        menuAumentarFonte.setText("jMenuItem1");
        menuConsole.add(menuAumentarFonte);

        menuDiminuirFonte.setText("jMenuItem1");
        menuConsole.add(menuDiminuirFonte);

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setFocusable(false);
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        painelConteudo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        painelRolagem.setBackground(new java.awt.Color(250, 250, 250));
        painelRolagem.setBorder(null);
        painelRolagem.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));

        console.setEditable(false);
        console.setBackground(new java.awt.Color(250, 250, 250));
        console.setColumns(20);
        console.setBorder(null);
        painelRolagem.setViewportView(console);

        painelConteudo.add(painelRolagem, java.awt.BorderLayout.CENTER);

        painelFerramentas.setBackground(new java.awt.Color(250, 250, 250));
        painelFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        painelFerramentas.setPreferredSize(new java.awt.Dimension(34, 100));
        painelFerramentas.setLayout(new java.awt.BorderLayout());

        barraFerramentas.setFloatable(false);
        barraFerramentas.setOrientation(javax.swing.SwingConstants.VERTICAL);
        barraFerramentas.setRollover(true);
        barraFerramentas.setOpaque(false);

        botaoAumentarFonte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        botaoAumentarFonte.setBorderPainted(false);
        botaoAumentarFonte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoAumentarFonte.setFocusable(false);
        botaoAumentarFonte.setHideActionText(true);
        botaoAumentarFonte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoAumentarFonte.setMaximumSize(new java.awt.Dimension(24, 24));
        botaoAumentarFonte.setMinimumSize(new java.awt.Dimension(24, 24));
        botaoAumentarFonte.setPreferredSize(new java.awt.Dimension(24, 24));
        botaoAumentarFonte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(botaoAumentarFonte);

        botaoDiminuirFonte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        botaoDiminuirFonte.setBorderPainted(false);
        botaoDiminuirFonte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoDiminuirFonte.setFocusable(false);
        botaoDiminuirFonte.setHideActionText(true);
        botaoDiminuirFonte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoDiminuirFonte.setMaximumSize(new java.awt.Dimension(24, 24));
        botaoDiminuirFonte.setMinimumSize(new java.awt.Dimension(24, 24));
        botaoDiminuirFonte.setPreferredSize(new java.awt.Dimension(24, 24));
        botaoDiminuirFonte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(botaoDiminuirFonte);

        botaoLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        botaoLimpar.setBorderPainted(false);
        botaoLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoLimpar.setFocusable(false);
        botaoLimpar.setHideActionText(true);
        botaoLimpar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoLimpar.setMaximumSize(new java.awt.Dimension(24, 24));
        botaoLimpar.setMinimumSize(new java.awt.Dimension(24, 24));
        botaoLimpar.setPreferredSize(new java.awt.Dimension(24, 24));
        botaoLimpar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(botaoLimpar);

        botaoCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        botaoCopiar.setBorderPainted(false);
        botaoCopiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoCopiar.setFocusable(false);
        botaoCopiar.setHideActionText(true);
        botaoCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoCopiar.setMaximumSize(new java.awt.Dimension(24, 24));
        botaoCopiar.setMinimumSize(new java.awt.Dimension(24, 24));
        botaoCopiar.setPreferredSize(new java.awt.Dimension(24, 24));
        botaoCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(botaoCopiar);

        painelFerramentas.add(barraFerramentas, java.awt.BorderLayout.CENTER);

        painelConteudo.add(painelFerramentas, java.awt.BorderLayout.EAST);

        add(painelConteudo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JButton botaoAumentarFonte;
    private javax.swing.JButton botaoCopiar;
    private javax.swing.JButton botaoDiminuirFonte;
    private javax.swing.JButton botaoLimpar;
    private javax.swing.JTextArea console;
    private javax.swing.JMenuItem menuAumentarFonte;
    private javax.swing.JPopupMenu menuConsole;
    private javax.swing.JMenuItem menuConsoleCopiar;
    private javax.swing.JMenuItem menuConsoleLimpar;
    private javax.swing.JMenuItem menuDiminuirFonte;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelFerramentas;
    private javax.swing.JScrollPane painelRolagem;
    // End of variables declaration//GEN-END:variables

    private final class HandlerDaSaida implements Saida
    {
        @Override
        public void limpar() throws Exception
        {
            escreveConsole(null);
        }

        @Override
        public void escrever(String valor) throws Exception
        {
            escreveConsole(valor);
        }

        @Override
        public void escrever(boolean valor) throws Exception
        {
            escreveConsole((valor) ? "verdadeiro" : "falso");
        }

        @Override
        public void escrever(int valor) throws Exception
        {
            escreveConsole(String.valueOf(valor));
        }

        @Override
        public void escrever(double valor) throws Exception
        {
            escreveConsole(String.valueOf(valor));
        }

        @Override
        public void escrever(char valor) throws Exception
        {
            escreveConsole(String.valueOf(valor));
        }

        private void escreveConsole(final String texto) throws Exception
        {
            SwingUtilities.invokeAndWait(new Runnable()
            {
                @Override
                public void run()
                {
                    AbaConsole.this.selecionar();

                    console.requestFocusInWindow();
                    abaCodigoFonte.exibirPainelSaida();

                    if (texto != null)
                    {
                        console.append(texto);
                    }
                    else
                    {
                        console.setText(null);
                    }
                }
            });
        }
    }

    private void agendarPopupLeia()
    {
        timerPopupLeia = new Timer(4000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                removendoPopup = false;
                FadingUtils.fadeInBalloon(popupLeia, foo, 500, 24);
                cancelarPopupLeia();
            }
        });

        timerPopupLeia.start();
    }

    private void cancelarPopupLeia()
    {
        if (timerPopupLeia != null)
        {
            timerPopupLeia.stop();
        }
    }

    private Object obterValorEntrada(TipoDado tipoDado, String entrada)
    {
        try
        {
            if (tipoDado == TipoDado.INTEIRO)
            {
                return Integer.parseInt(entrada);
            }
            else if (tipoDado == TipoDado.REAL)
            {
                return Double.parseDouble(entrada);
            }
            else if (tipoDado == TipoDado.CARACTER)
            {
                return entrada.charAt(0);
            }
            else if (tipoDado == TipoDado.LOGICO)
            {
                switch (entrada)
                {
                    case "falso":
                        return false;
                    case "verdadeiro":
                        return true;
                }
            }

            return entrada;
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    void removerPopupLeia()
    {
        cancelarPopupLeia();

        removendoPopup = true;

        if (popupLeia.isVisible())
        {
            FadingUtils.fadeOutBalloon(popupLeia, foo, 500, 24);
        }
    }

    public void registrarComoSaida(Programa p)
    {
        if (handlerDaSaida == null)
        {
            throw new IllegalStateException("Handler está nulo!");
        }
        p.setSaida(handlerDaSaida);
    }

    public void registrarComoEntrada(Programa programa)
    {
        programa.setEntrada((Entrada) console.getDocument());
    }

    private class DocumentoConsole extends PlainDocument implements Entrada
    {

        private int limitOffset = 0;
        private boolean lendo = false;

        private Armazenador armazenador;
        private TipoDado tipoDado;

        @Override
        public void solicitaEntrada(final TipoDado tipoDado, final Armazenador armazenador) throws Exception
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    AbaConsole.this.selecionar();
                    
                    setLendo(true);

                    abaCodigoFonte.exibirPainelSaida();
                    DocumentoConsole.this.armazenador = armazenador;
                    DocumentoConsole.this.tipoDado = tipoDado;

                    console.setEditable(true);
                    console.setFocusable(true);

                    console.getCaret().setVisible(true);                    
                    console.setCaretPosition(console.getText().length());
                    console.requestFocusInWindow();
                                        
                    agendarPopupLeia();
                }
            });
        }

        public void setLendo(boolean lendo)
        {
            this.lendo = lendo;

            if (lendo)
            {
                limitOffset = getLength();
            }
            else
            {
                limitOffset = 0;
            }
        }

        @Override
        public void replace(int offset, int length, String string, AttributeSet as) throws BadLocationException
        {
            if (string == null)
            {
                remove(0, getLength());
                return;
            }
            if (lendo && string.equals("\n"))
            {
                removerPopupLeia();

                console.setEditable(false);
                console.setFocusable(false);

                Object valor = obterValorEntrada(tipoDado, getText(limitOffset, getLength() - limitOffset));
                setLendo(false);
                console.getCaret().setVisible(false);
                armazenador.setValor(valor);
            }

            if (offset >= limitOffset)
            {
                super.replace(offset, length, string, as);
            }

            if (!lendo)
            {
                limitOffset = getLength();
            }
        }

        @Override
        public void remove(int i, int i1) throws BadLocationException
        {
            if (!lendo || limitOffset <= i)
            {
                super.remove(i, i1);
            }
        }
    }

    private final class CursorConsole extends DefaultCaret
    {

        private static final long serialVersionUID = 1L;

        public CursorConsole()
        {
            setBlinkRate(250);
        }

        @Override
        protected synchronized void damage(Rectangle r)
        {
            if (r == null)
            {
                return;
            }

            // give values to x,y,width,height (inherited from java.awt.Rectangle)
            x = r.x;
            y = r.y;
            height = r.height;
            // A value for width was probably set by paint(), which we leave alone.
            // But the first call to damage() precedes the first call to paint(), so
            // in this case we must be prepared to set a valid width, or else
            // paint()
            // will receive a bogus clip area and caret will not get drawn properly.
            if (width <= 0)
            {
                width = getComponent().getWidth();
            }

            repaint();  //Calls getComponent().repaint(x, y, width, height) to erase 
            repaint();  // previous location of caret. Sometimes one call isn't enough.
        }

        /*
         * (non-Javadoc)
         * @see javax.swing.text.DefaultCaret#paint(java.awt.Graphics)
         */
        @Override
        public void paint(Graphics g)
        {
            JTextComponent comp = getComponent();

            if (comp == null)
            {
                return;
            }

            int dot = getDot();
            Rectangle r;
            char dotChar;
            try
            {
                r = comp.modelToView(dot);
                if (r == null)
                {
                    return;
                }
                dotChar = comp.getText(dot, 1).charAt(0);
            }
            catch (BadLocationException e)
            {
                return;
            }

            if (Character.isWhitespace(dotChar))
            {
                dotChar = '_';
            }

            if ((x != r.x) || (y != r.y))
            {
                // paint() has been called directly, without a previous call to
                // damage(), so do some cleanup. (This happens, for example, when
                // the text component is resized.)
                damage(r);
                return;
            }

            g.setColor(comp.getCaretColor());
            g.setXORMode(comp.getBackground()); // do this to draw in XOR mode

            width = g.getFontMetrics().charWidth(dotChar);
            if (isVisible())
            {
                r.height = 2;
                r.y = r.y + g.getFontMetrics().getHeight() - r.height - 1;

                g.fillRect(r.x, r.y, width, r.height);
            }
        }
    }
}
