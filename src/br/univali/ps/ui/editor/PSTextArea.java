package br.univali.ps.ui.editor;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
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
    private List<GutterIconInfo> pontosDeParada;

    private List<PSTextAreaListener> listeners = new ArrayList<>();

    public PSTextArea(RSyntaxDocument doc) {
        super(doc);
        this.pontosDeParada = new ArrayList<>();
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
            GutterIconInfo iconeInfo = gutter.addLineTrackingIcon(linha-1, iconeDoPontoDeParada);
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
                linha = getLineOfOffset(offs) + 1;
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
