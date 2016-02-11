package br.univali.ps.ui.abas;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.es.Armazenador;
import br.univali.portugol.nucleo.execucao.es.Entrada;
import br.univali.portugol.nucleo.execucao.es.Saida;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.BarraDeBotoesExpansivel;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.PopOverDirection;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

public final class AbaConsole extends Aba implements PropertyChangeListener
{

    private static final float VALOR_INCREMENTO_FONTE = 2.0f;
    private static final float TAMANHO_MAXIMO_FONTE = 50.0f;
    private static final float TAMANHO_MINIMO_FONTE = 10.0f;

    private static final Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "application_xp_terminal.png");

    private boolean executandoPrograma = false;

    private WebPopOver popupLeia = null;
    private final ActionListener listener;
    private Timer timerPopupLeia;
    private boolean removendoPopup = true;

    private AbstractAction acaoLimpar;
    private AbstractAction acaoCopiar;

    private final HandlerDaSaida handlerDaSaida;
    private AbaCodigoFonte abaCodigoFonte;

    public AbaConsole()
    {
        super("Console", icone, false);

        initComponents();

        WeblafUtils.configuraWebLaf(painelRolagem);

        console.setDocument(new DocumentoConsole());

        listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (removendoPopup)
                {
                    if (popupLeia != null)
                    {
                        popupLeia.dispose();
                        popupLeia = null;
                    }
                }
                else
                {
                    console.requestFocusInWindow();
                    console.setCaretPosition(console.getText().length());
                }
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

        JPopupMenu popupMenu = criarBarraDeBotoes();
        console.setComponentPopupMenu(popupMenu);

        instalarObservadores();
        console.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));
        carregarConfiguracoes();

        handlerDaSaida = new HandlerDaSaida();
    }

    private void exibirPopupLeia()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                popupLeia = new WebPopOver(console);

                popupLeia.setModal(false);
                popupLeia.setMargin(8);
                popupLeia.setMovable(false);
                popupLeia.setCloseOnFocusLoss(false);
                popupLeia.setLayout(new VerticalFlowLayout());

                WebLabel label = new WebLabel("O programa está aguardando a entrada de dados", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "warning.png"));
                label.setFont(label.getFont().deriveFont(12.0f));

                popupLeia.add(label);
                popupLeia.show(console, PopOverDirection.up);

//                        popupLeia.requestFocus();
                AbaConsole.this.selecionar();
                console.requestFocusInWindow();
            }
        });
    }

    private JPopupMenu criarBarraDeBotoes()
    {

        Icon iconeMais = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "plus2.png");
        Icon iconeMenos = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "minus.png");

        //++++ Cria as ações ++++++++
        Action acaoAumentarFonte = new AbstractAction("", iconeMais)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setTamanhoFonteConsole(console.getFont().getSize() + VALOR_INCREMENTO_FONTE);
            }
        };
        //+++++++++++++++++
        Action acaoDiminuirFonte = new AbstractAction("", iconeMenos)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setTamanhoFonteConsole(console.getFont().getSize() - VALOR_INCREMENTO_FONTE);
            }
        };
        //+++++++++++++++++++
        acaoLimpar = new AbstractAction("Limpar", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "edit_clear.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                limparConsole();
            }
        };

        acaoLimpar.setEnabled(false);
        //+++++++++++++++++++++++++++++++
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
        //++++++++++++++++++++++++++++++++++++++++++++++++
        BarraDeBotoesExpansivel barra = new BarraDeBotoesExpansivel();
        Icon iconeFonte = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font.png");
        //+++++++++++++++++++++++++
        barra.adicionaGrupoDeItems("Tamanho da fonte", iconeFonte, new Action[]
        {
            acaoAumentarFonte, acaoDiminuirFonte
        });
        barra.adicionaAcao(acaoLimpar);
        barra.adicionaAcao(acaoCopiar);
        FabricaDicasInterface.criarDicaInterface(barra.getCompomemtParaAdicionarDica(), "Personalizar a console");
        GridBagConstraints constrainsts = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 2), 0, 0);

        this.add(barra, constrainsts);
        this.setComponentZOrder(barra, 0);

        return barra.getPopupMenu();
    }

    public void setAbaCodigoFonte(AbaCodigoFonte abaCodigoFonte)
    {
        this.abaCodigoFonte = abaCodigoFonte;
    }

    public synchronized boolean isLendo()
    {
        DocumentoConsole documento = (DocumentoConsole) console.getDocument();

        return documento.lendo;
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

//    private void criarDicasInterface() {
//        FabricaDicasInterface.criarDicaInterface(botaoLimpar, "Limpa o texto existente no console");
//        FabricaDicasInterface.criarDicaInterface(botaoCopiar, "Copia o texto existente no console");
//        FabricaDicasInterface.criarDicaInterface(botaoAumentarFonte, "Aumenta o tamanho da fonte do console");
//        FabricaDicasInterface.criarDicaInterface(botaoDiminuirFonte, "Diminui o tamanho da fonte do console");
//    }
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

                    if (console.getText().length() > 0)
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
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        painelRolagem = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();

        setFocusable(false);
        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        painelRolagem.setBackground(new java.awt.Color(255, 255, 0));
        painelRolagem.setBorder(null);
        painelRolagem.setOpaque(false);

        console.setEditable(false);
        console.setBackground(new java.awt.Color(230, 230, 230));
        console.setColumns(20);
        console.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        console.setOpaque(false);
        painelRolagem.setViewportView(console);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        add(painelRolagem, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea console;
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
                exibirPopupLeia();
            }
        });

        timerPopupLeia.setRepeats(false);
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
            else
            {
                if (tipoDado == TipoDado.REAL)
                {
                    return Double.parseDouble(entrada);
                }
                else
                {
                    if (tipoDado == TipoDado.CARACTER)
                    {
                        return entrada.charAt(0);
                    }
                    else
                    {
                        if (tipoDado == TipoDado.LOGICO)
                        {
                            switch (entrada)
                            {
                                case "falso":
                                    return false;
                                case "verdadeiro":
                                    return true;
                            }
                        }
                    }
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

        if (popupLeia != null)
        {
            popupLeia.dispose();
            popupLeia = null;
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

        public synchronized void setLendo(boolean lendo)
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

                if (valor != null)
                {
                    armazenador.setValor(valor);
                }
                else
                {
                    armazenador.cancelarLeitura();
                }
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

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                WebLookAndFeel.install();
                JFrame frame = new JFrame("Teste Aba Console");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                AbaConsole aba = new AbaConsole();
                frame.getContentPane().add(aba, BorderLayout.CENTER);

                frame.setVisible(true);
            }
        });

    }
}
