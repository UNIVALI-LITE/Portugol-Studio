package br.univali.ps.ui.editor;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.JTextComponent;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaEditorKit;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaUI;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.LineNumberList;
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
    private Map<Integer, GutterIconInfo> pontosDeParada;

    public PSTextArea(RSyntaxDocument doc) {
        super(doc);
        this.pontosDeParada = new HashMap<>();
                
        //setSize(1, 600);
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

    private void alternaPontoDeParada(int linha) {
        try {
            Gutter gutter = RSyntaxUtilities.getGutter(this);
            GutterIconInfo infosDoIcone = pontosDeParada.get(linha);
            if (infosDoIcone != null) {//break point existe na linha
                gutter.removeTrackingIcon(infosDoIcone);
                pontosDeParada.remove(linha);
            } else {//break point não existia e será criado
                if (iconeDoPontoDeParada == null) {
                    LOGGER.warning("icone do break point não foi setado! Utilize o método setIconeDosBreakPoints");
                }
                GutterIconInfo iconeInfo = gutter.addLineTrackingIcon(linha, iconeDoPontoDeParada);
                pontosDeParada.put(linha, iconeInfo);

            }
        } catch (BadLocationException e) {
            //
        }
    }

    public Set<Integer> getLinhasComPontoDeParada() {
        return new HashSet<>(pontosDeParada.keySet());
    }

    //++++++++++++++++++++++++++++++++++
    private static class PSTextAreaEditorKit extends RSyntaxTextAreaEditorKit {

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

//    @Override
//    public Dimension getSize() {
//        Dimension size = super.getSize();
//        if(size.height == 0 || size.width == 0){
//            Dimension preferred = super.getPreferredSize();
//            return new Dimension(preferred.width, preferred.height);
//        }
//        return size;
//    }
    private int pontoParaLinha(Point p) {
        int linha = 0;
        try {
            int offs = viewToModel(p);
            if (offs > -1) {
                linha = getLineOfOffset(offs);
            }
        } catch (BadLocationException ble) {
            ble.printStackTrace(); // Never happens
        }
        return linha;
    }

    private static class PSTextAreaUI extends RSyntaxTextAreaUI {

        private final EditorKit kit = new PSTextAreaEditorKit();

        public PSTextAreaUI(JComponent rSyntaxTextArea) {
            super(rSyntaxTextArea);
        }

        public static ComponentUI createUI(JComponent textArea) {
            return new PSTextAreaUI(textArea);
        }

        @Override
        public EditorKit getEditorKit(JTextComponent tc) {
            return kit;
        }

    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("Teste PsTextArea");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                PSTextArea textArea = new PSTextArea(new PortugolDocumento());
                textArea.setIconeDosBreakPoints(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png"));
                textArea.setText("asd\nteste\ntoste\ntuste");
                RTextScrollPane scrollPane = new RTextScrollPane(textArea, true);
                scrollPane.setFoldIndicatorEnabled(true);
                scrollPane.setIconRowHeaderEnabled(true);
                frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

                frame.setVisible(true);
            }
        });

    }

}
