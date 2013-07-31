package br.univali.ps.ui;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.Entrada;
import br.univali.portugol.nucleo.execucao.Saida;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
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
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
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

public final class AbaConsole extends Aba implements Saida, Entrada, PropertyChangeListener
{
    private static final float VALOR_INCREMENTO_FONTE = 2.0f;
    private static final float TAMANHO_MAXIMO_FONTE = 50.0f;
    private static final float TAMANHO_MINIMO_FONTE = 10.0f;
    
    private TipoDado tipoDado;
    private boolean executandoPrograma = false;
    private JLabel rotuloPopupLeia;
    private BalloonTip popupLeia;
    private BalloonTipStyle estiloPopupLeia;
    private ActionListener foo;
    private boolean popupFinalizado;
    
    private Action acaoAumentarFonte;
    private Action acaoDiminuirFonte;
    private Action acaoLimpar;
    private Action acaoCopiar;

    public AbaConsole(JTabbedPane painelTabulado) 
    {
        super(painelTabulado);
        cabecalho.setBotaoFecharVisivel(false);
        cabecalho.setTitulo("Console");
        cabecalho.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "application_xp_terminal.png"));
        initComponents();
        console.setComponentPopupMenu(menuConsole);
        this.menuConsoleLimpar.setText("Limpar");
        this.menuConsoleCopiar.setText("Copiar");
        this.menuAumentarFonte.setText("Aumentar fonte");
        this.menuDiminuirFonte.setText("Diminuir fonte");
        console.setDocument(new DocumentoConsole());

        rotuloPopupLeia = new JLabel("<html><body><p>O programa está aguardando a entrada de dados</p></body></html>", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "information.png"), JLabel.LEFT);
        rotuloPopupLeia.setVerticalTextPosition(JLabel.CENTER);
        rotuloPopupLeia.setIconTextGap(10);
        rotuloPopupLeia.setPreferredSize(new Dimension(250, 50));
        rotuloPopupLeia.setFont(rotuloPopupLeia.getFont().deriveFont(Font.BOLD, 14f));

        estiloPopupLeia = new EdgedBalloonStyle(new Color(255, 255, 210), Color.BLACK);
        popupLeia = new BalloonTip(painelRolagem.getViewport(), rotuloPopupLeia, estiloPopupLeia, BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.NORTH, 50, 25, false);
        popupLeia.setPadding(8);
        popupLeia.setVisible(false);

        foo = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                popupFinalizado = true;
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
        
        criarAcoes();
        criarDicasInterface();
        instalarObservadores();
        carregarConfiguracoes();
    }
    
    private void criarAcoes()
    {
        criarAcaoLimpar();
        criarAcaoCopiar();
        criarAcaoAumentarFonte();
        criarAcaoDiminuirFonte();
        
    }
    
    private void criarAcaoLimpar()
    {
        acaoLimpar = new AbstractAction("Limpar", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "edit_clear.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try 
                {
                    limpar(); 
                }
                catch (Exception ex) 
                {
                    
                }
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
        FabricaDicasInterface.criarDicaInterface(botaoLimpar, "Limpa o texto existente no console", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(botaoCopiar, "Copia o texto existente no console", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(botaoAumentarFonte, "Aumenta o tamanho da fonte do console", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(botaoDiminuirFonte, "Diminui o tamanho da fonte do console", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
    }    
    
    private void instalarObservadores()
    {
        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        
        configuracoes.adicionarObservadorConfiguracao(this, Configuracoes.TAMANHO_FONTE_CONSOLE);
    }    
    
    private void carregarConfiguracoes()
    {
        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        
        setTamanhoFonteConsole(configuracoes.getTamanhoFonteConsole());
    }
    
    private void setTamanhoFonteConsole(float tamanho)
    {
        if ((tamanho != console.getFont().getSize()) && (tamanho >= TAMANHO_MINIMO_FONTE) && (tamanho <= TAMANHO_MAXIMO_FONTE))
        {
            console.setFont(console.getFont().deriveFont(tamanho));
            PortugolStudio.getInstancia().getConfiguracoes().setTamanhoFonteConsole(tamanho);
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
        console.setRows(5);
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
        botaoAumentarFonte.setOpaque(false);
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
        botaoDiminuirFonte.setOpaque(false);
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
        botaoLimpar.setOpaque(false);
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
        botaoCopiar.setOpaque(false);
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

    @Override
    public void limpar() throws Exception
    {
        ManipuladorSaida saida = new ManipuladorSaida(null);
        saida.execute();
        saida.get();
    }

    public void escreveConsole(String texto) throws Exception
    {
        this.selecionar();
        console.requestFocusInWindow();
        
        ManipuladorSaida manipuladorSaida = new ManipuladorSaida(texto);
        manipuladorSaida.execute();
        manipuladorSaida.get();
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

    @Override
    public Object ler(TipoDado tipoDado) throws Exception
    {
        console.setEditable(true);
        console.setFocusable(true);
        
        this.tipoDado = tipoDado;

        popupFinalizado = false;
        FadingUtils.fadeInBalloon(popupLeia, foo, 200, 24);
        aguardarPopup();
        
        this.selecionar();
        console.requestFocusInWindow();
        console.setCaretPosition(console.getText().length());

        ManipuladorEntrada manipuladorEntrada = new ManipuladorEntrada();
        manipuladorEntrada.execute();

        String entrada = (String) manipuladorEntrada.get();

        popupFinalizado = false;
        FadingUtils.fadeOutBalloon(popupLeia, foo, 50, 24);
        aguardarPopup();
        popupLeia.setVisible(false);

        console.setEditable(false);
        console.setFocusable(false);

        return obterValorEntrada(entrada);
    }

    private void aguardarPopup()
    {
        while (!popupFinalizado)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception ex)
            {
            }
        }
    }

    private Object obterValorEntrada(String entrada)
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
                if (entrada.equals("falso"))
                {
                    return false;
                }
                else if (entrada.equals("verdadeiro"))
                {
                    return true;
                }
            }

            return entrada;
        }
        catch (Exception e)
        {
            //TODO interroper;
            return null;
        }
    }

    void removerPopupLeia()
    {
        if (popupLeia.isVisible())
        {
            FadingUtils.fadeOutBalloon(popupLeia, foo, 200, 24);
            popupFinalizado = true;
            popupLeia.setVisible(false);
        }
    }

    private class ManipuladorEntrada extends SwingWorker
    {
        public ManipuladorEntrada()
        {
            ((DocumentoConsole) console.getDocument()).setLendo(true);
        }

        @Override
        protected Object doInBackground() throws Exception
        {

            while (((DocumentoConsole) console.getDocument()).isLendo())
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException ex)
                {
                    ((DocumentoConsole) console.getDocument()).setLendo(false);
                    throw ex;
                }
            }

            return ((DocumentoConsole) console.getDocument()).getValorLido();
        }
    }

    private class ManipuladorSaida extends SwingWorker
    {
        String valorSaida;

        public ManipuladorSaida(String valorSaida)
        {
            this.valorSaida = valorSaida;
        }

        @Override
        protected Object doInBackground() throws Exception
        {
            if (valorSaida != null)
            {
                console.append(valorSaida);
            }
            else
            {
                console.setText(null);
            }
            return true;
        }
    }

    private class DocumentoConsole extends PlainDocument
    {
        private int limitOffset = 0;
        private String valor;
        private boolean lendo = false;

        public boolean isLendo()
        {
            return lendo;
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
                valor = getText(limitOffset, getLength() - limitOffset);
                lendo = false;
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

        private String getValorLido()
        {
            return valor;
        }
    }

    public final class CursorConsole extends DefaultCaret
    {
        private static final long serialVersionUID = 1L;

        public CursorConsole()
        {
            setBlinkRate(250);
        }

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

        /* (non-Javadoc)
         * @see javax.swing.text.DefaultCaret#paint(java.awt.Graphics)
         */
        public void paint(Graphics g)
        {
            JTextComponent comp = getComponent();

            if (comp == null)
            {
                return;
            }

            int dot = getDot();
            Rectangle r = null;
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
