package br.univali.ps.ui.editor;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.WebLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.JTextComponent;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaEditorKit;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaUI;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.LineNumberList;
import org.fife.ui.rtextarea.RTATextTransferHandler;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextAreaUI;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author elieser
 */
public class PSTextArea extends RSyntaxTextArea {

    private static final Logger LOGGER = Logger.getLogger(PSTextArea.class.getName());
    private Icon iconeDoPontoDeParada;
    private List<GutterIconInfo> pontosDeParada;

    private List<PSTextAreaListener> listeners = new ArrayList<>();

    public PSTextArea(RSyntaxDocument doc) {
        super(doc);
        setBorder(null);

        this.pontosDeParada = new ArrayList<>();
        setTransferHandler(new RTATextTransferHandler() {//usa a própria classe to RSyntax mas modifica a criação da dragImage
            @Override
            public Image getDragImage() {
                String textoSelecionado = getSelectedText();

                FontMetrics metrics = getFontMetrics(getFont());
                final int MARGEM = 5;
                int larguraDotexto = MARGEM + metrics.stringWidth(textoSelecionado) + MARGEM;
                int alturaDoTexto = MARGEM + metrics.getHeight() + MARGEM;
                BufferedImage image = new BufferedImage(larguraDotexto, alturaDoTexto, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) image.getGraphics();
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, larguraDotexto - 1, alturaDoTexto - 1);
                g.setFont(getFont());
                g.setColor(Color.DARK_GRAY);
                g.drawString(textoSelecionado, MARGEM, alturaDoTexto - metrics.getAscent());
                return image;
            }

            @Override
            public Point getDragImageOffset() {
                Point p = super.getDragImageOffset();
                p.translate(-16, 0);//deixa a imagem ao lado do ícone do cursor do mouse
                return p;
            }

        });
        setDragEnabled(true);
        getDocument().addDocumentListener(new DocumentListenerPontosDeParada());
    }

    public void atualizaEstadoDosPontosDeParada(Collection<Integer> pontosDeParada, Set<Integer> linhasParaveis) {
        for (Integer linhaDoPontoDeParada : pontosDeParada) {
            if (!linhasParaveis.contains(linhaDoPontoDeParada)) {
                alternaPontoDeParada(linhaDoPontoDeParada);
            }
        }
    }

    private class DocumentListenerPontosDeParada implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent de) {
            adicionaPontosDeParaEmTodasAsLinhas();
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            adicionaPontosDeParaEmTodasAsLinhas();
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            adicionaPontosDeParaEmTodasAsLinhas();
        }

    }

    public void addListenter(PSTextAreaListener l) {
        listeners.add(l);
    }

    public void setIconeDosBreakPoints(Icon icone) {
        this.iconeDoPontoDeParada = icone;
    }

    @Override
    protected RTextAreaUI createRTextAreaUI() {

        return new PSTextAreaUI(this);
    }

    private void trataCliqueNoNumeroDaLinha(Point clique) {
        int linhaClicada = pontoParaLinha(clique);
        //System.out.println("linha clicada: " + linhaClicada);
        alternaPontoDeParada(linhaClicada);
    }

    public void alternaPontoDeParada(int linha) {
        try {
            Gutter gutter = RSyntaxUtilities.getGutter(this);

            //tentar remover
            for (GutterIconInfo gutterInfo : pontosDeParada) {
                if (getLineOfOffset(gutterInfo.getMarkedOffset()) + 1 == linha) {
                    gutter.removeTrackingIcon(gutterInfo);
                    pontosDeParada.remove(gutterInfo);
                    disparaPontosDeParadaAtualizados();
                    return;
                }
            }

            //se não removeu então está inserindo. A inserção acontece em linha-1 porque o gutter conta as linhas a partir do zero
            GutterIconInfo iconeInfo = gutter.addLineTrackingIcon(linha - 1, iconeDoPontoDeParada);
            pontosDeParada.add(iconeInfo);
            disparaPontosDeParadaAtualizados();

        } catch (BadLocationException e) {
            //
        }
    }

    private void disparaPontosDeParadaAtualizados() {
        for (PSTextAreaListener listener : listeners) {
            listener.pontosDeParaAtualizados(getLinhasComPontoDeParada());
        }
    }

    public Set<Integer> getLinhasComPontoDeParada() {
        Set<Integer> pontos = new HashSet<>();
        try {
            for (GutterIconInfo gutterInfo : pontosDeParada) {
                int linha = getLineOfOffset(gutterInfo.getMarkedOffset()) + 1;
                pontos.add(linha);
            }
        } catch (BadLocationException ex) {
        }
        return pontos;
    }
    //++++++++++++++++++++++++++++++++++

    void removePontosDeParadaInvalidos(Set<Integer> linhasComPontosDeParadaValidos) {
        try {
            List<GutterIconInfo> infos = new ArrayList<>(pontosDeParada);
            for (GutterIconInfo info : infos) {
                int linhaNoGutter = getLineOfOffset(info.getMarkedOffset()) + 1;
                if (!linhasComPontosDeParadaValidos.contains(linhaNoGutter)) {
                    alternaPontoDeParada(linhaNoGutter);
                }
            }
        } catch (BadLocationException e) {
            //
        }
    }

    public void setarTema(Theme tema) {
        tema.apply(this);
    }

    //++++++++++++++++++++++++++++++++++
    private class PSTextAreaEditorKit extends RSyntaxTextAreaEditorKit {

        private PSTextArea textArea;
        private LineNumberList numberList;
        private IconRowHeader iconRowHeader;

        @Override
        public IconRowHeader createIconRowHeader(RTextArea textArea) {
            iconRowHeader = super.createIconRowHeader(textArea);
            //iconRowHeader.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            iconRowHeader.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    PSTextAreaEditorKit.this.textArea.trataCliqueNoNumeroDaLinha(me.getPoint());
                }

            });
            //deixa a cor da componente onde aparecem os ícones dos pontos de parada com uma cor mais suave
            Color corDeFundo = WeblafUtils.COR_DO_PAINEL_PRINCIPAL;
            iconRowHeader.setBackground(corDeFundo);

            return iconRowHeader;
        }

        @Override
        public LineNumberList createLineNumberList(RTextArea textArea) {
            this.textArea = (PSTextArea) textArea;
            numberList = super.createLineNumberList(textArea);
            //numberList.setBorder(BorderFactory.createLineBorder(Color.RED));
            numberList.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    PSTextAreaEditorKit.this.textArea.trataCliqueNoNumeroDaLinha(me.getPoint());
                }

            });
            return numberList;
        }

    }

    private int pontoParaLinha(Point p) {
        int linha = 0;
        try {
            int offs = viewToModel(p);
            if (offs > -1) {
                linha = getLineOfOffset(offs) + 1;
            }
        } catch (BadLocationException ble) {
            ble.printStackTrace(); // Never happens
        }
        return linha;
    }

    private class PSTextAreaUI extends RSyntaxTextAreaUI {

        public PSTextAreaUI(JComponent rSyntaxTextArea) {
            super(rSyntaxTextArea);
        }

        @Override
        public EditorKit getEditorKit(JTextComponent tc) {
            return new PSTextAreaEditorKit();
        }

    }

    private boolean existePontoDeParadaNaLinha(int linha) {
        try {
            for (GutterIconInfo info : pontosDeParada) {
                if (getLineOfOffset(info.getMarkedOffset()) + 1 == linha) {
                    return true;
                }
            }
        } catch (BadLocationException e) {

        }
        return false;
    }

    private void adicionaPontosDeParaEmTodasAsLinhas() {
        Gutter gutter = RSyntaxUtilities.getGutter(this);
        try {
            for (int i = 0; i < getLineCount(); i++) {
                if (!existePontoDeParadaNaLinha(i + 1)) {
                    GutterIconInfo iconeInfo = gutter.addLineTrackingIcon(i, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "cores.png"));
                    pontosDeParada.add(iconeInfo);
                }
            }
            disparaPontosDeParadaAtualizados();
        } catch (Exception e) {

        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                WebLookAndFeel.install();
                JFrame frame = new JFrame("Teste PsTextArea");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                PSTextArea textArea = new PSTextArea(new PortugolDocumento());
                textArea.setIconeDosBreakPoints(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png"));
                textArea.setText("asd\nteste\ntoste\ntuste");
                final RTextScrollPane scrollPane = new RTextScrollPane(textArea, true);
                scrollPane.setViewportBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                scrollPane.setFoldIndicatorEnabled(true);
                scrollPane.setIconRowHeaderEnabled(true);

                JPanel panel = new JPanel(new BorderLayout());
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                panel.add(scrollPane);
                frame.add(panel, BorderLayout.CENTER);

                frame.setVisible(true);
            }
        });

    }

}
