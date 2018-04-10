/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.editor;

import br.univali.ps.ui.rstautil.completion.ProvedorConclusaoCodigoBibliotecas;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import java.lang.reflect.Field;
import javax.swing.JList;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionProvider;

/**
 *
 * @author 5663296
 */
public class PSAutoComplition extends AutoCompletion{
    
    private JWindow popupWindow;
    
    public PSAutoComplition(CompletionProvider provider) {
        super(provider);
    }
    
    private JWindow getPopupWindow(){
        if (popupWindow ==  null){
        
            try {
                Class acClass = AutoCompletion.class;
                Field fieldPopupWindow = acClass.getDeclaredField("popupWindow");
                fieldPopupWindow.setAccessible(true);
                popupWindow = (JWindow) fieldPopupWindow.get(PSAutoComplition.this);
            }
            catch (Exception e){
                throw new RuntimeException("errou", e);
            }
        }
        
        return popupWindow;
    }    

    private boolean webLAFconfigurado = false;
    
    @Override
    protected int refreshPopupWindow() {
        int x = super.refreshPopupWindow();
        //System.out.println( ((JLayeredPane)((Container)getPopupWindow().getComponent(0)).getComponent(1)).getComponent(0).getClass()+" cpompos");
        if(!webLAFconfigurado){
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
                        
                        
                        webLAFconfigurado = true;
                    }
                }
            }

        }
        
        
        
        //getPopupWindow().setBackground(Color.CYAN);
        
        return x;
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
