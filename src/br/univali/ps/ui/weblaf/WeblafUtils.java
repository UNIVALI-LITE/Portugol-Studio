package br.univali.ps.ui.weblaf;

import br.univali.ps.ui.ColorController;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.checkbox.WebCheckBoxUI;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.combobox.WebComboBoxUI;
import com.alee.laf.panel.WebPanelUI;
import com.alee.laf.scroll.WebScrollBarUI;
import com.alee.laf.scroll.WebScrollPaneUI;
import com.alee.laf.text.WebTextFieldUI;
import com.alee.laf.toolbar.WebToolBarUI;
import com.alee.managers.style.skin.web.WebDecorationPainter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

    public static void instalaWeblaf() {
        if (!weblafEstaInstalado()) {
            
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
        configurarBotao(botao, corPrincipal, ColorController.COR_PRINCIPAL);
    }
    
    public  static void configurarBotao(WebButton botao){
        configurarBotao(botao, ColorController.FUNDO_ESCURO);
    }
    
    public static boolean weblafEstaInstalado() {
        return WebLookAndFeel.isInstalled();
    }

}
