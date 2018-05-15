package br.univali.ps.ui.rstautil.tree;

import br.univali.ps.ui.utils.IconFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class SearchTextField extends JTextField
{
    private static final Icon SEARCH_ICON = getIcon();
    private int searchDelay = 500;

    private final Insets originalInsets;
    private String placeholder = "Localizar (Control + L)";
    private Action searchAction;
    
    private Timer searchTimer;

    private static Icon getIcon()
    {
        Icon icon = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "find.png");
        
        if (icon == null)
        {
            BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.getGraphics();
            
            graphics.setColor(new Color(0, 0, 0, 0));
            graphics.clearRect(0, 0, 16, 16);
            graphics.dispose();
            
            icon = new ImageIcon(image);
        }
        
        return icon;
    }
    
    public SearchTextField()
    {
        initComponents();
        
        setText("");

        this.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                SearchTextField.this.repaint();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                SearchTextField.this.repaint();
            }
        });
        
        originalInsets = getInsets();
        int textX = originalInsets.left + SEARCH_ICON.getIconWidth() + 2;
        setMargin(new Insets(2, textX, 2, 2));
        getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                doSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                doSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                doSearch();
            }
            
            private void doSearch()
            {
                if (searchTimer == null)
                {
                    searchTimer = new Timer(searchDelay, (ActionEvent e) ->
                    {
                        if (getSearchAction() != null)
                        {
                            getSearchAction().actionPerformed(e);
                        }
                    });
                    
                    searchTimer.setInitialDelay(searchDelay);
                    searchTimer.setRepeats(false);
                    searchTimer.start();
                }
                else
                {
                    searchTimer.restart();
                }
            }
        });
    }

    public Action getSearchAction()
    {
        return searchAction;
    }

    public void setSearchAction(Action searchAction)
    {
        this.searchAction = searchAction;
    }
    
    public String getPlaceholder()
    {
        return placeholder;
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder = placeholder;
    }

    public int getSearchDelay() {
        return searchDelay;
    }

    public void setSearchDelay(int search_delay) {
        this.searchDelay = search_delay;
    }  
    
    private boolean podePintarPlaceholder()
    {
        return !this.hasFocus() && this.getText().equals("");
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (podePintarPlaceholder())
        {
            Font fonte = getFont();
            g.setFont(fonte);
            g.setColor(getForeground());
            FontMetrics metrics = getFontMetrics(fonte);
            int textoY = getHeight()/2 + (metrics.getDescent() + metrics.getAscent())/2;
            int textoX = (SEARCH_ICON != null) ? (SEARCH_ICON.getIconWidth() * 2) : originalInsets.left;
            g.drawString(placeholder, textoX, textoY);
            
        }
        
        if (SEARCH_ICON != null)
        {
            int iconHeight = SEARCH_ICON.getIconHeight();

            int x = originalInsets.left;
            int y = (this.getHeight() - iconHeight) / 2;

            SEARCH_ICON.paintIcon(this, g, x, y);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
