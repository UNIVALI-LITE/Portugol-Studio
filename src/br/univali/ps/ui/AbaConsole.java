package br.univali.ps.ui;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.Entrada;
import br.univali.portugol.nucleo.execucao.Saida;
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
import java.util.logging.Level;
import java.util.logging.Logger;
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

public final class AbaConsole extends Aba implements Saida, Entrada
{
    private TipoDado tipoDado;
    private boolean executandoPrograma = false;
    private JLabel rotuloPopupLeia;
    private BalloonTip popupLeia;
    private BalloonTipStyle estiloPopupLeia;
    private ActionListener foo;
    private boolean popupFinalizado;

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
        popupLeia = new BalloonTip(jScrollPane1.getViewport(), rotuloPopupLeia, estiloPopupLeia, BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.NORTH, 50, 25, false);
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
                jScrollPane1.getVerticalScrollBar().setValue(ce.getComponent().getHeight());
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
                    menuConsoleLimpar.setEnabled(true);

                    int selecao = console.getSelectionEnd() - console.getSelectionStart();

                    if (selecao > 0)
                    {
                        menuConsoleCopiar.setEnabled(true);
                    }
                    else
                    {
                        menuConsoleCopiar.setEnabled(false);
                    }
                }
                else
                {
                    menuConsoleLimpar.setEnabled(false);
                    menuConsoleCopiar.setEnabled(false);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();

        menuConsoleLimpar.setText("jMenuItem1");
        menuConsoleLimpar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuConsoleLimparActionPerformed(evt);
            }
        });
        menuConsole.add(menuConsoleLimpar);

        menuConsoleCopiar.setText("jMenuItem2");
        menuConsoleCopiar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuConsoleCopiarActionPerformed(evt);
            }
        });
        menuConsole.add(menuConsoleCopiar);

        menuAumentarFonte.setText("jMenuItem1");
        menuAumentarFonte.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuAumentarFonteActionPerformed(evt);
            }
        });
        menuConsole.add(menuAumentarFonte);

        menuDiminuirFonte.setText("jMenuItem1");
        menuDiminuirFonte.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuDiminuirFonteActionPerformed(evt);
            }
        });
        menuConsole.add(menuDiminuirFonte);

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setFocusable(false);
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(250, 250, 250));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));

        console.setEditable(false);
        console.setBackground(new java.awt.Color(250, 250, 250));
        console.setColumns(20);
        console.setRows(5);
        console.setBorder(null);
        jScrollPane1.setViewportView(console);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void menuConsoleLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsoleLimparActionPerformed
        try
        {
            limpar();
        }
        catch (Exception ex)
        {
            Logger.getLogger(AbaConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuConsoleLimparActionPerformed

    private void menuConsoleCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsoleCopiarActionPerformed
        console.copy();
    }//GEN-LAST:event_menuConsoleCopiarActionPerformed

    private void menuAumentarFonteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuAumentarFonteActionPerformed
    {//GEN-HEADEREND:event_menuAumentarFonteActionPerformed
        final Font fonteAtual = console.getFont();
        float novoTamanho = fonteAtual.getSize() + 4;
        if (novoTamanho < 70)
        {
            console.setFont(fonteAtual.deriveFont(novoTamanho));
        }
    }//GEN-LAST:event_menuAumentarFonteActionPerformed

    private void menuDiminuirFonteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuDiminuirFonteActionPerformed
    {//GEN-HEADEREND:event_menuDiminuirFonteActionPerformed
        final Font fonteAtual = console.getFont();
        float novoTamanho = fonteAtual.getSize() - 4;
        if (novoTamanho > 12)
        {
            console.setFont(fonteAtual.deriveFont(novoTamanho));
        }
    }//GEN-LAST:event_menuDiminuirFonteActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea console;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem menuAumentarFonte;
    private javax.swing.JPopupMenu menuConsole;
    private javax.swing.JMenuItem menuConsoleCopiar;
    private javax.swing.JMenuItem menuConsoleLimpar;
    private javax.swing.JMenuItem menuDiminuirFonte;
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
        console.requestFocus();
        console.setCaretPosition(console.getText().length());

        this.tipoDado = tipoDado;

        popupFinalizado = false;
        FadingUtils.fadeInBalloon(popupLeia, foo, 200, 24);
        aguardarPopup();

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
