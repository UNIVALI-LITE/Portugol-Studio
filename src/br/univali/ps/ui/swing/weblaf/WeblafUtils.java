package br.univali.ps.ui.swing.weblaf;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.WebHeaderRenderer;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.checkbox.WebCheckBoxUI;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.combobox.WebComboBoxUI;
import com.alee.laf.panel.WebPanelUI;
import com.alee.laf.progressbar.WebProgressBarUI;
import com.alee.laf.scroll.WebScrollBarUI;
import com.alee.laf.scroll.WebScrollPaneUI;
import com.alee.extended.painter.Painter;
import com.alee.extended.painter.PainterListener;
import com.alee.laf.table.WebTableStyle;
import com.alee.laf.text.WebTextFieldUI;
import com.alee.laf.toolbar.WebToolBarUI;
import com.alee.managers.style.skin.web.WebDecorationPainter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author elieser
 */
public class WeblafUtils {

    public static final Integer DEFAULT_MARGIN = 10;
    public static final Color COR_DAS_BORDAS = new Color(200, 200, 200);
    public static final Color BACKGROUND_CLARO = new Color(250, 250, 250);
    public static final Color BACKGROUND_ESCURO = new Color(243, 243, 243);
    public static final Color COR_DA_BORDA_ORIGINAL_NO_WEBLAF = StyleConstants.borderColor;
    


     public static void configuraWeblaf(JToolBar barraDeFerramentas) {
        if (!WeblafUtils.weblafEstaInstalado()) {
            return;
        }

        ((WebToolBarUI) barraDeFerramentas.getUI()).setUndecorated(true);
        for (int i = 0; i < barraDeFerramentas.getComponentCount(); i++) {
            Component componente = barraDeFerramentas.getComponent(i);
            if (componente instanceof AbstractButton) {
                AbstractButton botao = (AbstractButton) barraDeFerramentas.getComponent(i);
                ((WebButtonUI) botao.getUI()).setRolloverDecoratedOnly(true);

            }
        }
    }
    
    public static void configuraWebLaf(WebComboBox field) {
       if (!WeblafUtils.weblafEstaInstalado()) {
           return;
       }
       ((WebComboBoxUI) field.getUI()).setDrawBorder(false);
       ((WebComboBoxUI) field.getUI()).setRound(0);
//       ((WebComboBoxUI) field.getUI()).setExpandedBgColor(ColorController.COR_DESTAQUE);
//       ((WebComboBoxUI) field.getUI()).setWebColoredBackground(false);
//       ((WebComboBoxUI) field.getUI()).s 
       
//       field.setBorder(new EmptyBorder(15,15,15,15));
//       field.setOpaque(true);
//       field.setBackground(ColorController.COR_DESTAQUE);
//       field.setForeground(ColorController.FUNDO_ESCURO);
    }
    public static void configuraWebLaf(JTextArea field) {
       if (!WeblafUtils.weblafEstaInstalado()) {
           return;
       }
//       ((WebTextAreaUI) field.getUI()).setDrawBorder(false);
//       ((WebTextAreaUI) field.getUI()).setDrawBackground(true);
       field.setBorder(new EmptyBorder(15,15,15,15));
       field.setOpaque(true);
       field.setBackground(ColorController.COR_DESTAQUE);
       field.setForeground(ColorController.COR_LETRA);
    }
    
    public static void configuraWebLaf(JProgressBar field) {
       if (!WeblafUtils.weblafEstaInstalado()) {
           return;
       }
       ((WebProgressBarUI)field.getUI()).setProgressTopColor(ColorController.PROGRESS_BAR);
       ((WebProgressBarUI)field.getUI()).setProgressBottomColor(ColorController.PROGRESS_BAR);
       ((WebProgressBarUI)field.getUI()).setBgBottom(ColorController.FUNDO_ESCURO);
       ((WebProgressBarUI)field.getUI()).setBgTop(ColorController.FUNDO_ESCURO);
       ((WebProgressBarUI)field.getUI()).setIndeterminateBorder(null);
       ((WebProgressBarUI)field.getUI()).setPaintIndeterminateBorder(false);
       ((WebProgressBarUI)field.getUI()).setInnerRound(0);
       ((WebProgressBarUI)field.getUI()).setRound(0);
       ((WebProgressBarUI)field.getUI()).setHighlightWhite(ColorController.PROGRESS_BAR);
       ((WebProgressBarUI)field.getUI()).setShadeWidth(0);
       ((WebProgressBarUI)field.getUI()).setHighlightDarkWhite(ColorController.PROGRESS_BAR);
       field.setBorder(new EmptyBorder(15,15,15,15));
       field.setOpaque(true);
       field.setBackground(ColorController.COR_DESTAQUE);
       field.setForeground(ColorController.COR_LETRA);
       field.setBorderPainted(false);
    }
    
    public static void configuraWebLaf(JTextField field) {
       if (!WeblafUtils.weblafEstaInstalado()) {
           return;
       }
       ((WebTextFieldUI) field.getUI()).setDrawBorder(false);
       ((WebTextFieldUI) field.getUI()).setDrawBackground(true);
       field.setBorder(new EmptyBorder(15,15,15,15));
       field.setOpaque(true);
       field.setBackground(ColorController.COR_DESTAQUE);
       field.setForeground(ColorController.COR_LETRA);
    }
    public static void configuraWebLaf(JTextField field, int margin, int leftMargin) {
       if (!WeblafUtils.weblafEstaInstalado()) {
           return;
       }
       ((WebTextFieldUI) field.getUI()).setDrawBorder(false);
       ((WebTextFieldUI) field.getUI()).setDrawBackground(true);
       field.setBorder(new EmptyBorder(margin, leftMargin, margin, margin));
       field.setOpaque(true);
       field.setBackground(ColorController.COR_DESTAQUE);
       field.setForeground(ColorController.COR_LETRA);
    }
    
    public static void configuraWebLaf(JCheckBox field) {
       if (!WeblafUtils.weblafEstaInstalado()) {
           return;
       }
       ((WebCheckBoxUI) field.getUI()).setBorderColor(ColorController.COR_PRINCIPAL);
       ((WebCheckBoxUI) field.getUI()).setRound(0);
       ((WebCheckBoxUI) field.getUI()).setDarkBorderColor(ColorController.COR_PRINCIPAL);
       ((WebCheckBoxUI) field.getUI()).setShadeWidth(0);
       ((WebCheckBoxUI) field.getUI()).setBottomBgColor(ColorController.COR_PRINCIPAL);
       ((WebCheckBoxUI) field.getUI()).setTopBgColor(ColorController.COR_PRINCIPAL);
       ((WebCheckBoxUI) field.getUI()).setBottomSelectedBgColor(ColorController.COR_DESTAQUE);
       ((WebCheckBoxUI) field.getUI()).setTopSelectedBgColor(ColorController.COR_DESTAQUE);
//       ((WebCheckBoxUI) field.getUI()).setDrawBackground(true);
       field.setOpaque(false);
//       field.setBackground(ColorController.COR_DESTAQUE);
       field.setForeground(ColorController.COR_LETRA);
    }

   
    public static void configuraWebLaf(JScrollPane scroll) {
        if (!WeblafUtils.weblafEstaInstalado()) {
            return;
        }
        scroll.getViewport().setOpaque(false);
        ((WebScrollPaneUI) scroll.getUI()).setDrawBorder(false);
        ((WebScrollBarUI) scroll.getHorizontalScrollBar().getUI()).setPaintTrack(false);
        ((WebScrollBarUI) scroll.getVerticalScrollBar().getUI()).setPaintTrack(false);
        scroll.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, null);
        scroll.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, null);
        scroll.setCorner(ScrollPaneConstants.UPPER_TRAILING_CORNER, null);
        scroll.setCorner(ScrollPaneConstants.UPPER_LEADING_CORNER, null);
        scroll.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, null);
        scroll.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, null);
        scroll.setCorner(ScrollPaneConstants.LOWER_TRAILING_CORNER, null);
        scroll.setCorner(ScrollPaneConstants.LOWER_LEADING_CORNER, null);
        
        ScrollPaneLayout layout = (ScrollPaneLayout) scroll.getLayout();
        JScrollBar horizontal = layout.getHorizontalScrollBar();
        JScrollBar vertical = layout.getVerticalScrollBar();
        vertical.setUI(new PSScrollBarUI());
        horizontal.setUI(new PSScrollBarUI());
        
        //((WebScrollBarUI) vertical.getUI()).setPainter(painter);

        //instala um layout no scrollPane que sempre deixa um pequeno espaço
        //no canto superior direito para que seja exibido o botão de ações
//        scroll.setLayout(new ScrollPaneLayout() {
//
//            @Override
//            public void layoutContainer(Container cntnr) {
//                super.layoutContainer(cntnr);
//                if (vsb.isVisible()) {
//                    Dimension tamanho = vsb.getSize();
//                    Point localizacao = vsb.getLocation();
//                    localizacao.y += 20;
//                    localizacao.x++;
//                    tamanho.height -= 20;
//                    vsb.setSize(tamanho);
//                    vsb.setLocation(localizacao);
//                }
//            }
//
//        });

    }


    public static void configuraWeblaf(JPanel painel, final Color corDeFundo, boolean bordaEsquerda, boolean bordaDireita, boolean bordaDeCima, boolean bordaDeBaixo) {
        if (!WeblafUtils.weblafEstaInstalado()) {
            return;
        }
        if (corDeFundo != null) {
            ((WebPanelUI) painel.getUI()).setPainter(criaPainterComCorSolida(corDeFundo, bordaEsquerda, bordaDireita, bordaDeCima, bordaDeBaixo));
        }
        ((WebPanelUI) painel.getUI()).setUndecorated(bordaDeBaixo && bordaDeCima && bordaEsquerda && bordaDireita);
        ((WebPanelUI) painel.getUI()).setPaintSides(bordaDeCima, bordaEsquerda, bordaDeBaixo, bordaDireita);
    }

    public static WebDecorationPainter criaPainterComCorSolida(Color corDeFundo, boolean bordaEsquerda, boolean bordaDireita, boolean bordaDeCima, boolean bordaDeBaixo) {
        return new PainterDeCorSolida(corDeFundo, bordaEsquerda, bordaDireita, bordaDeCima, bordaDeBaixo);
    }

    private static class PainterDeCorSolida extends WebDecorationPainter<JComponent> {

        private final Color corDeFundo;

        public PainterDeCorSolida(Color corDeFundo, boolean bordaEsquerda, boolean bordaDireita, boolean bordaDeCima, boolean bordaDeBaixo) {
            super();
            this.corDeFundo = corDeFundo;
            if (!bordaEsquerda && !bordaDireita && !bordaDeBaixo && !bordaDeCima) {
                shadeWidth = 0;
                borderColor = null;
                disabledBorderColor = null;
            }
            setPaintSides(bordaDeCima, bordaEsquerda, bordaDeBaixo, bordaDireita);
        }

        @Override
        protected void paintBackground(final Graphics2D g2d, final Rectangle bounds, final JComponent c, final Shape backgroundShape) {
            g2d.setPaint(corDeFundo);
            g2d.fill(backgroundShape);
        }

    }

    public static void configuraWeblaf(JPanel painel, final Color corDeFundo) {
        configuraWeblaf(painel, corDeFundo, true, true, true, true);
    }

    public static void configuraWeblaf(JPanel painel) {
        configuraWeblaf(painel, null);
    }
    
    public static void configuraWebLaf(JTable field) {
       if (!WeblafUtils.weblafEstaInstalado()) {
           return;
       }
//       ((WebTableUI) field.getUI()).setScrollPaneBackgroundColor(ColorController.PROGRESS_BAR);
       field.getTableHeader().setDefaultRenderer(new WebHeaderRenderer());
       field.getTableHeader().setForeground(ColorController.COR_LETRA);
       field.getTableHeader().setResizingAllowed(false);
       field.getTableHeader().setReorderingAllowed(false);
       field.setShowGrid(false);
       field.setIntercellSpacing(new Dimension(0, 0));
       field.setRowHeight(20);
    }
    public static void configuraWebLaf(JTable field, TableCellRenderer renderer, int columns) {
       if (!WeblafUtils.weblafEstaInstalado()) {
           return;
       }
//       ((WebTableUI) field.getUI()).setScrollPaneBackgroundColor(ColorController.PROGRESS_BAR);
       field.getTableHeader().setDefaultRenderer(new WebHeaderRenderer());
       field.getTableHeader().setForeground(ColorController.COR_LETRA);
       field.getTableHeader().setResizingAllowed(false);
       field.getTableHeader().setReorderingAllowed(false);
       field.setShowGrid(false);
       field.setIntercellSpacing(new Dimension(0, 0));
       field.setRowHeight(20);
       field.getTableHeader().setEnabled(true);
       for (int i = 0; i < columns; i++)
       {
           field.getColumnModel().getColumn(i).setCellRenderer(renderer);
       }
    }
    
    public static void configuraWebTables()
    {
        WebTableStyle.headerBottomBgColor = ColorController.COR_PRINCIPAL;
        WebTableStyle.headerTopBgColor =  ColorController.COR_PRINCIPAL;
        WebTableStyle.headerBottomLineColor = null;
        WebTableStyle.headerTopLineColor = ColorController.COR_PRINCIPAL;
        WebTableStyle.background=ColorController.FUNDO_ESCURO;
        WebTableStyle.foreground = ColorController.COR_LETRA;
        WebTableStyle.headerMargin = new Insets(0,0,0,0);
        WebTableStyle.gridColor = null;
        WebTableStyle.showHorizontalLines = false;
        WebTableStyle.showVerticalLines = false;
    }

    public static void instalaWeblaf() {
        if (!weblafEstaInstalado()) {
//            Icon info = new ImageIcon();
//            try {
//                info = new ImageIcon(ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("br/univali/ps/ui/icones/grande/lite/ajuda.png")));
//            } catch (IOException ex) {
//                Logger.getLogger(WeblafUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
            configuraWebTables();
            StyleConstants.darkBorderColor = null;//define a cor de borda do weblaf globalmente
            WebLookAndFeel.install();
            WebLookAndFeel.setDecorateDialogs(false);
            WebLookAndFeel.setDecorateFrames(false);
            Locale.setDefault(new Locale("pt", "br"));//corrige o idioma nos diálogos de pesquisar e substituir
            UIManager.put("SplitPane.supportsOneTouchButtons", true);//oneTouchButton nos splitPanes em todas as plataformas
        }
    }
    
    public  static void configurarToogleBotao(WebToggleButton botao, Color corBgPrincipal, Color corTexto, Color corBgHover, Color corTextoHover){
        Insets insets= new Insets(10, 20, 10, 10);
        configurarToogleBotao(botao, corBgPrincipal, corTexto, corBgHover, corTextoHover, insets);
    }
    public  static void configurarToogleBotao(WebToggleButton botao, Color corBgPrincipal, Color corTexto, Color corBgHover, Color corTextoHover,  Integer margin){
        Insets insets= new Insets(margin, margin, margin, margin);
        configurarToogleBotao(botao, corBgPrincipal, corTexto, corBgHover, corTextoHover, insets);
    }    
public  static void configurarToogleBotao(WebToggleButton botao, Color corBgPrincipal, Color corTexto, Color corBgHover, Color corTextoHover,  Insets margin){
        botao.setMargin(margin);
//        botao.setFontSize ( 20 );
        botao.setRound ( 0 );
        botao.setShadeWidth ( 0 );
        botao.setInnerShadeWidth ( 0 );
        botao.setDrawSides ( false, false, false, false );
        botao.setForeground ( corTexto );
        botao.setTopBgColor (corBgPrincipal);
        botao.setBottomBgColor (corBgPrincipal);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setSelectedForeground(corTextoHover);
        botao.setTopSelectedBgColor(corBgHover);
        botao.setBottomSelectedBgColor(corBgHover);
    }
    public  static void configurarToogleBotao(WebToggleButton botao, Color corBgPrincipal, Color corTexto, Color corBgHover, Color corTextoHover,  Insets margin, Integer size){
        botao.setPreferredWidth(size);
        configurarToogleBotao(botao, corBgPrincipal, corTexto, corBgHover, corTextoHover, margin);
    }
    
    public  static void configurarBotao(WebButton botao, Color corBgPrincipal, Color corTexto, Color corBgHover, Color corTextoHover,  Integer margin){
        
        botao.setMargin (margin);
//        botao.setFontSize ( 20 );
        botao.setRound ( 0 );
        botao.setShadeWidth ( 0 );
        botao.setInnerShadeWidth ( 0 );
        botao.setDrawSides ( false, false, false, false );
        botao.setForeground ( corTexto );
        botao.setSelectedForeground (corTextoHover);
        botao.setTopBgColor (corBgPrincipal);
        botao.setTopSelectedBgColor (corBgHover );
        botao.setBottomBgColor (corBgPrincipal);
        botao.setBottomSelectedBgColor (corBgHover);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setTopBgColor(corBgHover);
                botao.setBottomBgColor(corBgHover);
                botao.setForeground(corTextoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setTopBgColor(corBgPrincipal);
                botao.setBottomBgColor(corBgPrincipal);
                botao.setForeground(corTexto);
            }
            
        });
    }
    public  static void configurarBotao(WebButton botao, Color corBgPrincipal, Color corTexto, Integer margin){
        configurarBotao(botao, corBgPrincipal, corTexto, ColorController.COR_LETRA, ColorController.COR_PRINCIPAL, margin);
    }
    public  static void configurarBotao(WebButton botao, Color corPrincipal, Color corTexto){
        configurarBotao(botao, corPrincipal, corTexto, DEFAULT_MARGIN);
    }
    public  static void configurarBotao(WebButton botao, Color corPrincipal, Integer margin){
        configurarBotao(botao, corPrincipal, ColorController.COR_LETRA, margin);
    }
    public  static void configurarBotao(WebButton botao, Integer margin){
        configurarBotao(botao, ColorController.FUNDO_ESCURO, ColorController.COR_PRINCIPAL, margin);
    }
    public  static void configurarBotao(WebButton botao, Color corPrincipal){
        configurarBotao(botao, corPrincipal, ColorController.COR_LETRA);
    }
    
    public  static void configurarBotao(WebButton botao){
        configurarBotao(botao, ColorController.FUNDO_ESCURO);
    }
    
    public static boolean weblafEstaInstalado() {
        return WebLookAndFeel.isInstalled();
    }

}
