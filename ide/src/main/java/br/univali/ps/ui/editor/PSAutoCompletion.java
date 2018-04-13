/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.editor;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.rstautil.completion.ProvedorConclusaoCodigoBibliotecas;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import com.alee.utils.swing.DocumentChangeListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;

/**
 *
 * @author 5663296
 */
public class PSAutoCompletion extends AutoCompletion{
    
    private JWindow popupWindow;
    private JWindow descWindow;
    
    public PSAutoCompletion(CompletionProvider provider) {
        super(provider);
    }
    
    private JWindow getPopupWindow(){
        if (popupWindow ==  null){
        
            try {
                Class acClass = AutoCompletion.class;
                Field fieldPopupWindow = acClass.getDeclaredField("popupWindow");
                fieldPopupWindow.setAccessible(true);
                popupWindow = (JWindow) fieldPopupWindow.get(PSAutoCompletion.this);
            }
            catch (Exception e){
                throw new RuntimeException("errou", e);
            }
        }
        
        return popupWindow;
    }    
    private JWindow getDescWindow(JWindow popupWindow){
        if (descWindow ==  null){
        
            try {
                Class acClass = Class.forName("org.fife.ui.autocomplete.AutoCompletePopupWindow");
                Field fieldDescWindow = acClass.getDeclaredField("descWindow");
                fieldDescWindow.setAccessible(true);
                descWindow = (JWindow) fieldDescWindow.get(popupWindow);
            }
            catch (Exception e){
                throw new RuntimeException("errou aqui tb", e);
            }
        }
        
        return descWindow;
    }   

    private boolean webLAFconfiguradoPopupWindow = false;
    private boolean webLAFconfiguradoDescWindow = false;
    
    
    @Override
    protected int refreshPopupWindow() {
        int x = super.refreshPopupWindow();
        //System.out.println( ((JLayeredPane)((Container)getPopupWindow().getComponent(0)).getComponent(1)).getComponent(0).getClass()+" cpompos");
        if(!webLAFconfiguradoPopupWindow){
            if(getPopupWindow()!= null){
                if(getPopupWindow().getComponent(0)  != null){
                    JRootPane rootPane = (JRootPane) getPopupWindow().getComponent(0);
                    if(rootPane.getContentPane().getComponent(0)!= null){
                        JScrollPane scrollPane = (JScrollPane) rootPane.getContentPane().getComponent(0);
                        scrollPane.setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                        WeblafUtils.configuraWebLaf(scrollPane);
                        rootPane.getContentPane().setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                        
                        JList lista = (JList) scrollPane.getViewport().getView();
                        
                        lista.setFixedCellHeight(30);
                        lista.setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                        
                        getPopupWindow().addComponentListener(new ComponentAdapter(){
                            @Override
                            public void componentShown(ComponentEvent ce) {
                                super.componentShown(ce); //To change body of generated methods, choose Tools | Templates.
                                if(!webLAFconfiguradoDescWindow){
                                    JRootPane rootPane1 = (JRootPane) getDescWindow(popupWindow).getComponent(0);
                                    JScrollPane scrollPane = (JScrollPane) rootPane1.getContentPane().getComponent(0);

                                    scrollPane.setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                                    rootPane1.getContentPane().getComponent(1).setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                                    JPanel  panel = (JPanel)rootPane1.getContentPane().getComponent(1);
                                    JToolBar  bar = (JToolBar)panel.getComponent(0);
                                    JPanel sizeGrip = (JPanel)panel.getComponent(1);
                                    sizeGrip.setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                                    WeblafUtils.configuraWeblaf(bar);
                                    ((JButton) bar.getComponent(0)).setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "aba_anterior.png"));
                                    ((JButton) bar.getComponent(1)).setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "proxima_aba.png"));
//                                    WeblafUtils.configurarBotao((WebButton) bar.getComponent(0));
//                                    WeblafUtils.configurarBotao((WebButton) bar.getComponent(1));
                                    
                                    
                                    
                                    for (Component component : ((Container)rootPane1.getContentPane().getComponent(1)).getComponents()) {
                                        System.out.println(component.getClass());
                                    }
                                    JEditorPane editorPane = (JEditorPane) ((Container)scrollPane.getComponent(0)).getComponent(0);
                                    editorPane.setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                                    editorPane.setForeground(ColorController.AMARELO);
                                    HTMLEditorKit kit = new HTMLEditorKit();
                                    editorPane.setEditorKit(kit);
                                    StyleSheet styleSheet = kit.getStyleSheet();                                                                        
                                    
                                    if(Configuracoes.getInstancia().isTemaDark())
                                    {
                                        styleSheet.addRule("body {color:#cdcdcd;}");
                                        styleSheet.addRule(".type-inteiro {color:#45BDFF;}");
                                        styleSheet.addRule(".type-real {color:#00EFC0;}");
                                        styleSheet.addRule(".type-logico {color:#FFC200;}");
                                        styleSheet.addRule(".type-cadeia {color:#BA02F6;}");
                                        styleSheet.addRule(".type-caracter {color:#F0433B;}");

                                        styleSheet.addRule("h4 {color:#FFC200;}");
                                        styleSheet.addRule(".function {color:#FFC200;}");
                                        styleSheet.addRule("a {color:#FFC200;}");
                                        styleSheet.addRule("em {color:#bbbbbb;}");
                                        styleSheet.addRule(".type-name {color:#888888;}");
                                    }
                                    else
                                    {
                                        styleSheet.addRule("body {color:#000000;}");
                                        styleSheet.addRule(".type-inteiro {color:#6400C8;}");
                                        styleSheet.addRule(".type-real {color:#6400C8;}");
                                        styleSheet.addRule(".type-logico {color:#0000ff;}");
                                        styleSheet.addRule(".type-cadeia {color:#DC009C;}");
                                        styleSheet.addRule(".type-caracter {color:#DC009C;}");

                                        styleSheet.addRule("h4 {color:#ee8800;}");
                                        styleSheet.addRule(".function {color:#ad8000;}");
                                        styleSheet.addRule("a {color:#ee8800;}");
                                        styleSheet.addRule("em {color:#cc6600;}");
                                        styleSheet.addRule(".type-name {color:#008080;}");
                                    }
                                    
                                    editorPane.getDocument().addDocumentListener(new DocumentChangeListener() {
                                        @Override
                                        public void documentChanged(DocumentEvent de) {
                                            try {
//                                                System.out.println(editorPane.getText());
//                                                System.out.println(((HTMLDocument)editorPane.getDocument()).getText(0, ((HTMLDocument)editorPane.getDocument()).getLength()));
//                                                System.out.println(((HTMLDocument)editorPane.getDocument()).getStyleSheet());
                                            } catch (Exception ex) {
                                                Logger.getLogger(PSAutoCompletion.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    });
                                    
                                    
                                    //((Container)scrollPane.getComponent(1)).getComponent(0).setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                                    
                                    WeblafUtils.configuraWebLaf(scrollPane);
                                    rootPane1.getContentPane().setBackground(ColorController.FUNDO_BOTOES_EXPANSIVEIS);
                                    webLAFconfiguradoDescWindow = true;
                                }
                            }
                            
                        });
                        webLAFconfiguradoPopupWindow = true;
                    }
                }
            }

        }
        
        
        
        //getPopupWindow().setBackground(Color.CYAN);
        
        return x;
    }
    
    public final static String toHexString(Color colour) throws NullPointerException {
        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
        if (hexColour.length() < 6) {
          hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
        }
        return "#" + hexColour;
    }
    
    
    @Override
    protected String getReplacementText(Completion c, Document doc, int start, int len)
    {
        String texto = super.getReplacementText(c, doc, start, len);

        if (c.getProvider() instanceof ProvedorConclusaoCodigoBibliotecas)
        {
            try
            {
                texto = doc.getText(start, len);

                texto = texto.substring(0, texto.lastIndexOf("."));
                texto = texto.concat(".").concat(super.getReplacementText(c, doc, start, len));

                return texto;
            }
            catch (BadLocationException ex)
            {

            }
        }

        return texto;
    }
}
