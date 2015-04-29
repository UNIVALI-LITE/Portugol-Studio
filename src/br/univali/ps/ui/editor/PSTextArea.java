package br.univali.ps.ui.editor;

import br.univali.ps.dominio.PortugolDocumento;
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
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
 * @author elieser
 */
public class PSTextArea extends RSyntaxTextArea {

    private static final Logger LOGGER = Logger.getLogger(PSTextArea.class.getName());

    private static Icon iconePontoDeParadaAtivado = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png");
    private static Icon iconePontoDeParadaDesativado = criaIconeDesativado(iconePontoDeParadaAtivado);

    private List<PontoDeParada> pontosDeParada;

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
        
    }

    public void atualizaEstadoDosPontosDeParada(Collection<Integer> pontosDeParada, Set<Integer> linhasParaveis) {
        for (Integer linhaDoPontoDeParada : pontosDeParada) {
            boolean linhaEhParavel = linhasParaveis.contains(linhaDoPontoDeParada);
            setaStatusDoPontoDeParada(linhaDoPontoDeParada, linhaEhParavel);

        }
    }

    public void addListenter(PSTextAreaListener l) {
        listeners.add(l);
    }

    public void setIconeDosBreakPoints(Icon icone) {
        iconePontoDeParadaAtivado = icone;
        iconePontoDeParadaDesativado = criaIconeDesativado(icone);
    }

    @Override
    protected RTextAreaUI createRTextAreaUI() {

        return new PSTextAreaUI(this);
    }

    private void trataCliqueNoNumeroDaLinha(Point clique) {
        int linhaClicada = pontoParaLinha(clique);
        alternaStatusDoPontoDeParada(linhaClicada);//ARRUMAR ISSO
    }

    private class PontoDeParada {

        private GutterIconInfo gutterInfo;
        private boolean ativado;

        public PontoDeParada(GutterIconInfo gutterInfo, boolean estaAtivado) {
            this.gutterInfo = gutterInfo;
            this.ativado = estaAtivado;
        }

        public GutterIconInfo getGutterInfo() {
            return gutterInfo;
        }

        public void setaStatus(boolean ativado, GutterIconInfo novoGutterIconInfo) {
            this.ativado = ativado;
            this.gutterInfo = novoGutterIconInfo;
        }

        public boolean estaAtivado() {
            return ativado;
        }
    }

    public void setaStatusDoPontoDeParada(int linha, boolean ativado) {
        if (!existePontoDeParadaNaLinha(linha)) {
            //adicionaPontoDeParada(linhaClicada);
            throw new IllegalStateException("Não existe ponto de parada na linha clicada!");
        }
        try {
            PontoDeParada pontoDeParada = getPontoDeParada(linha);
            if (pontoDeParada != null) {
                if (pontoDeParada.estaAtivado() != ativado) {
                    alternaStatusDoPontoDeParada(pontoDeParada);
                }
            }
        } catch (BadLocationException e) {

        }
    }

    private PontoDeParada getPontoDeParada(int linha) throws BadLocationException {
        for (PontoDeParada pontoDeParada : pontosDeParada) {
            GutterIconInfo gutterInfo = pontoDeParada.getGutterInfo();
            int linhaDoGutterInfo = getLineOfOffset(gutterInfo.getMarkedOffset()) + 1;
            if (linhaDoGutterInfo == linha) {
                return pontoDeParada;
            }
        }
        return null;
    }

    private void alternaStatusDoPontoDeParada(PontoDeParada pontoDeParada) throws BadLocationException {
        if (pontoDeParada != null) {
            Gutter gutter = RSyntaxUtilities.getGutter(this);
            gutter.removeTrackingIcon(pontoDeParada.getGutterInfo());//remove o ícone antigo para criar um outro no mesmo lugar do gutter
            boolean novoEstado = !pontoDeParada.estaAtivado();//alterna o estado
            int linha = getLineOfOffset(pontoDeParada.getGutterInfo().getMarkedOffset());
            GutterIconInfo novoGutterInfo = gutter.addLineTrackingIcon(linha, novoEstado ? iconePontoDeParadaAtivado : iconePontoDeParadaDesativado);
            pontoDeParada.setaStatus(novoEstado, novoGutterInfo);
        }
    }

    public void alternaStatusDoPontoDeParada(int linhaClicada) {
        if (!existePontoDeParadaNaLinha(linhaClicada)) {
            //adicionaPontoDeParada(linhaClicada);
            throw new IllegalStateException("Não existe ponto de parada na linha clicada!");
        }
        try {
            alternaStatusDoPontoDeParada(getPontoDeParada(linhaClicada));
        } catch (BadLocationException e) {

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
            for (PontoDeParada pontoDeParada : pontosDeParada) {
                int linha = getLineOfOffset(pontoDeParada.getGutterInfo().getMarkedOffset()) + 1;
                pontos.add(linha);
            }
        } catch (BadLocationException ex) {
        }
        return pontos;
    }
    //++++++++++++++++++++++++++++++++++

//    void removePontosDeParadaInvalidos(Set<Integer> linhasComPontosDeParadaValidos) {
//        try {
//            List<GutterIconInfo> infos = new ArrayList<>(pontosDeParada);
//            for (GutterIconInfo info : infos) {
//                int linhaNoGutter = getLineOfOffset(info.getMarkedOffset()) + 1;
//                if (!linhasComPontosDeParadaValidos.contains(linhaNoGutter)) {
//                    setarPontoDeParada(linhaNoGutter);
//                }
//            }
//        } catch (BadLocationException e) {
//            //
//        }
//    }
    public void setarTema(Theme tema) {
        tema.apply(this);
    }

    //++++++++++++++++++++++++++++++++++
    private static Icon criaIconeDesativado(Icon icon) {
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        icon.paintIcon(null, image.getGraphics(), 0, 0);
        return new ImageIcon(GrayFilter.createDisabledImage(image));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * *
     * Esta classe é necessária para que seja possível pegar o GutterIconInfo
     * criado e "embrulhá-lo" em uma instância de PsGutterIconInfo, que permite
     * trocar o ícone do ponto de parada.
     */
    private class PSIconRowHeader extends IconRowHeader {

        public PSIconRowHeader(RTextArea textArea) {
            super(textArea);
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    PSTextArea.this.trataCliqueNoNumeroDaLinha(me.getPoint());
                }

            });
            //deixa a cor da componente onde aparecem os ícones dos pontos de parada com uma cor mais suave
            setOpaque(true);
            setBackground(WeblafUtils.COR_DO_PAINEL_PRINCIPAL);
        }

    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private IconRowHeader iconRowHeader;

    private class PSTextAreaEditorKit extends RSyntaxTextAreaEditorKit {

        private PSTextArea textArea;
        private LineNumberList numberList;

        @Override
        public IconRowHeader createIconRowHeader(RTextArea textArea) {
            iconRowHeader = new PSIconRowHeader(textArea);// super.createIconRowHeader(textArea);
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

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class PSTextAreaUI extends RSyntaxTextAreaUI {

        public PSTextAreaUI(JComponent rSyntaxTextArea) {
            super(rSyntaxTextArea);
        }

        @Override
        public EditorKit getEditorKit(JTextComponent tc) {
            return new PSTextAreaEditorKit();
        }

    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private boolean existePontoDeParadaNaLinha(int linha) {
        try {
            for (PontoDeParada pontoDeParada : pontosDeParada) {
                if (getLineOfOffset(pontoDeParada.getGutterInfo().getMarkedOffset()) + 1 == linha) {
                    return true;
                }
            }
        } catch (BadLocationException e) {

        }
        return false;
    }

    private void adicionaPontoDeParada(int indiceDaLinha) {
        if (!existePontoDeParadaNaLinha(indiceDaLinha)) {
            try {
                Gutter gutter = RSyntaxUtilities.getGutter(this);
                GutterIconInfo gutterInfo = gutter.addLineTrackingIcon(indiceDaLinha - 1, iconePontoDeParadaDesativado);
                pontosDeParada.add(new PontoDeParada(gutterInfo, false));//os pontos são sempre criados como desativados
            } catch (BadLocationException e) {
                //
            }
        }
    }

    private void adicionaPontosDeParaEmTodasAsLinhas() {
        try {
            for (int linha = 1; linha <= getLineCount(); linha++) {
                adicionaPontoDeParada(linha);
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
