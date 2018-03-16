package br.univali.ps.ui.editor;

import br.univali.ps.ui.swing.ColorController;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.JTextComponent;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.IconRowHeader;
import org.fife.ui.rtextarea.LineNumberList;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextAreaUI;

/**
 * @author elieser
 */
public class PSTextArea extends RSyntaxTextArea {

    private static final Logger LOGGER = Logger.getLogger(PSTextArea.class.getName());

    private static Icon iconePontoDeParadaAtivado = null;
    private static Icon iconePontoDeParadaDesativado = null;

    private final List<IconePontoDeParada> pontosDeParada = new ArrayList<>();

    private final List<PSTextAreaListener> listeners = new ArrayList<>();

    public PSTextArea() {
        this(new RSyntaxDocument("cpp"));
    }

    public PSTextArea(RSyntaxDocument doc) {
        super(doc);
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Iterator<IconePontoDeParada> iterator = pontosDeParada.iterator();
                //remove pontosdeParada que estavam em linhas deletadas
                while (iterator.hasNext()) {
                    IconePontoDeParada iconePontoDeParada = iterator.next();
                    if(iconePontoDeParada.linhaMudou() && iconePontoDeParada.estaAtivado())
                    {
                        Gutter gutter = RSyntaxUtilities.getGutter(PSTextArea.this);
                        gutter.removeTrackingIcon(iconePontoDeParada.getGutterInfo());
                        iterator.remove();
                    }
                 }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });
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
    public void criarPontosDeParadaDesativados(Set<Integer> linhasParaveis){
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
        private int offsetOriginal;

        public IconePontoDeParada(GutterIconInfo gutterInfo, boolean estaAtivado) {
            this.gutterInfo = gutterInfo;
            this.ativado = estaAtivado;
            this.offsetOriginal = gutterInfo.getMarkedOffset();
        }

        boolean markerOffsetMudou() {
            return gutterInfo.getMarkedOffset() != offsetOriginal;
        }
        
        boolean linhaMudou()
        {            
            try {
                return getLineOfOffset(gutterInfo.getMarkedOffset()) != getLineOfOffset(offsetOriginal);
            } catch (BadLocationException ex) {
                return true;
            }
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
