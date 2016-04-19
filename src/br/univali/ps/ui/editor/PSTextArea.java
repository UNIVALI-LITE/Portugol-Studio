package br.univali.ps.ui.editor;

import br.univali.ps.ui.ColorController;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.WebLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.LineNumberList;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextAreaUI;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * @author elieser
 */
public class PSTextArea extends RSyntaxTextArea {

    private static final Logger LOGGER = Logger.getLogger(PSTextArea.class.getName());

    private static Icon iconePontoDeParadaAtivado = null;//IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png");
    //cria o ícone ativado a partir do arquivo de ícone para evitar um erro no NetBeans
    //quando se abre a aba de projeto da AbaDeCodigoFonte. 
    private static Icon iconePontoDeParadaDesativado = null;//criaIconeDesativado(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png"));

    private List<IconePontoDeParada> pontosDeParada = new ArrayList<>();

    private List<PSTextAreaListener> listeners = new ArrayList<>();

    public PSTextArea() {
        this(new RSyntaxDocument("cpp"));
    }

    public PSTextArea(RSyntaxDocument doc) {
        super(doc);
//        setBorder(null);
//
//        setTransferHandler(new RTATextTransferHandler() {//usa a própria classe to RSyntax mas modifica a criação da dragImage
//            @Override
//            public Image getDragImage() {
//                String textoSelecionado = getSelectedText();
//
//                FontMetrics metrics = getFontMetrics(getFont());
//                final int MARGEM = 5;
//                int larguraDotexto = MARGEM + metrics.stringWidth(textoSelecionado) + MARGEM;
//                int alturaDoTexto = MARGEM + metrics.getHeight() + MARGEM;
//                BufferedImage image = new BufferedImage(larguraDotexto, alturaDoTexto, BufferedImage.TYPE_INT_ARGB);
//                Graphics2D g = (Graphics2D) image.getGraphics();
//                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//                g.setColor(Color.LIGHT_GRAY);
//                g.fillRect(0, 0, larguraDotexto - 1, alturaDoTexto - 1);
//                g.setFont(getFont());
//                g.setColor(Color.DARK_GRAY);
//                g.drawString(textoSelecionado, MARGEM, alturaDoTexto - metrics.getAscent());
//                return image;
//            }
//
//            @Override
//            public Point getDragImageOffset() {
//                Point p = super.getDragImageOffset();
//                p.translate(-16, 0);//deixa a imagem ao lado do ícone do cursor do mouse
//                return p;
//            }
//
//        });
//        setDragEnabled(true);
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

    /**
     * *
     * É chamado sempre que o programa é recompilado para criar os
     * ícones de ponto de parada desativados nas linhas que são paráveis
     *
     * @param linhasParaveis
     */
    public void criarPontosDeParadaDesativados(Set<Integer> linhasParaveis) {
        //remove todos os pontos de parada existentes
        Set<Integer> pontosAtivados = getLinhasComPontoDeParadaAtivados();
        RSyntaxUtilities.getGutter(this).removeAllTrackingIcons();
        pontosDeParada.clear();
        for (Integer linha : linhasParaveis) {
            adicionaPontoDeParada(linha);
            if(pontosAtivados.contains(linha)){
                alternaStatusDoPontoDeParada(linha);
            }
        }
    }

    private class IconePontoDeParada {

        private GutterIconInfo gutterInfo;
        private boolean ativado;

        public IconePontoDeParada(GutterIconInfo gutterInfo, boolean estaAtivado) {
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
            adicionaPontoDeParada(linha);
        }
        try {
            IconePontoDeParada pontoDeParada = getIconePontoDeParada(linha);
            if (pontoDeParada != null) {
                if (pontoDeParada.estaAtivado() != ativado) {
                    alternaStatusDoPontoDeParada(pontoDeParada);
                }
            }
        } catch (BadLocationException e) {

        }
        disparaPontosDeParadaAtualizados();
    }

    private IconePontoDeParada getIconePontoDeParada(int linha) throws BadLocationException {
        for (IconePontoDeParada pontoDeParada : pontosDeParada) {
            GutterIconInfo gutterInfo = pontoDeParada.getGutterInfo();
            int linhaDoGutterInfo = getLineOfOffset(gutterInfo.getMarkedOffset()) + 1;
            if (linhaDoGutterInfo == linha) {
                return pontoDeParada;
            }
        }
        return null;
    }

    private void alternaStatusDoPontoDeParada(IconePontoDeParada pontoDeParada) throws BadLocationException {
        if (pontoDeParada != null) {
            Gutter gutter = RSyntaxUtilities.getGutter(this);
            gutter.removeTrackingIcon(pontoDeParada.getGutterInfo());//remove o ícone antigo para criar um outro no mesmo lugar do gutter
            boolean novoEstado = !pontoDeParada.estaAtivado();//alterna o estado
            int linha = getLineOfOffset(pontoDeParada.getGutterInfo().getMarkedOffset());
            GutterIconInfo novoGutterInfo = gutter.addLineTrackingIcon(linha, novoEstado ? iconePontoDeParadaAtivado : iconePontoDeParadaDesativado);
            pontoDeParada.setaStatus(novoEstado, novoGutterInfo);
            disparaPontosDeParadaAtualizados();
        }
    }

    public void alternaStatusDoPontoDeParada(int linhaClicada) {
        try {
            IconePontoDeParada iconePontoDeParada = getIconePontoDeParada(linhaClicada);
            if (iconePontoDeParada != null) {
                alternaStatusDoPontoDeParada(iconePontoDeParada);
            }
        } catch (BadLocationException e) {

        }
    }

    private void disparaPontosDeParadaAtualizados() {
        for (PSTextAreaListener listener : listeners) {
            listener.pontosDeParadaAtualizados(getLinhasComPontoDeParadaAtivados());
        }
    }

    public Set<Integer> getLinhasComPontoDeParadaAtivados() {
        Set<Integer> pontos = new HashSet<>();
        try {
            for (IconePontoDeParada pontoDeParada : pontosDeParada) {
                if (pontoDeParada.estaAtivado()) {
                    int linha = getLineOfOffset(pontoDeParada.getGutterInfo().getMarkedOffset()) + 1;
                    pontos.add(linha);
                }
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
        if (icon == null) {
            return null;
        }
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        icon.paintIcon(null, image.getGraphics(), 0, 0);
        return new ImageIcon(GrayFilter.createDisabledImage(image));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //herdando de FoldingAwareIconRowHeader para corrigir o bug que acontecia nos pontos de parada quanto o código estava dobrado
    private class PSIconRowHeader extends FoldingAwareIconRowHeader {

        static final String DICA_DOS_PONTOS_DE_PARADA = "Clique para pausar a execução do programa na linha ";

        public PSIconRowHeader(RSyntaxTextArea textArea) {
            super(textArea);
            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    PSTextArea.this.trataCliqueNoNumeroDaLinha(me.getPoint());
                }

            });
            //deixa a cor da componente onde aparecem os ícones dos pontos de parada com uma cor mais suave
            setOpaque(true);
            setBackground(ColorController.COR_PRINCIPAL);
        }

        @Override
        public String getToolTipText(MouseEvent e) {
            try {
                int offset = textArea.viewToModel(e.getPoint());
                int linha = textArea.getLineOfOffset(offset) + 1;
                IconePontoDeParada pontoDeParada = getIconePontoDeParada(linha);
                if (pontoDeParada != null && !pontoDeParada.estaAtivado()) {
                    return DICA_DOS_PONTOS_DE_PARADA + linha;
                }
            } catch (BadLocationException ex) {

            }
            return null;
        }

    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //private IconRowHeader iconRowHeader;
    private class PSTextAreaEditorKit extends RSyntaxTextAreaEditorKit {

        private PSTextArea textArea;
        private LineNumberList numberList;

        @Override
        public IconRowHeader createIconRowHeader(RTextArea textArea) {
            return new PSIconRowHeader((RSyntaxTextArea)textArea);
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
            for (IconePontoDeParada pontoDeParada : pontosDeParada) {
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
                pontosDeParada.add(new IconePontoDeParada(gutterInfo, false));//os pontos são sempre criados como desativados
            } catch (BadLocationException e) {
                //
            }
        }
    }
}
