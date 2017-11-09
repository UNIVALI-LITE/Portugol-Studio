package br.univali.ps.ui.swing.weblaf.jOptionPane;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author lite
 */
public class QuestionDialog extends javax.swing.JDialog implements Themeable{
    int pX, pY;
    private int resposta = -1;
    private static QuestionDialog dialog;
    /**
     * Creates new form QuestionDialog
     * @param parent
     * @param modal
     * @param text
     */
    private QuestionDialog(){
        super((JFrame)null, true);
        initComponents();
        setup();
    }
    public static QuestionDialog getInstance(){
        if(dialog==null){
            dialog = new QuestionDialog();  
        }
        dialog.reset();
        return dialog;
    }
    private void setup()
    {
        jPanel3.setBorder(new CompoundBorder(new LineBorder(ColorController.COR_PRINCIPAL, 1),new LineBorder(ColorController.FUNDO_ESCURO, 5)));
        setLocationRelativeTo(null);
        configurarCores();
        WeblafUtils.configurarBotao(webButton1, ColorController.FUNDO_ESCURO, Color.white, ColorController.VERMELHO, Color.orange,5);
        WeblafUtils.configurarBotao(botaoCancelar, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
        WeblafUtils.configurarBotao(botaoSim, ColorController.AMARELO, ColorController.FUNDO_ESCURO, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 5, true);
        WeblafUtils.configurarBotao(botaoNao, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.VERMELHO, ColorController.COR_LETRA_TITULO, 5, true);
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                // Get x,y and store them
                pX = me.getX();
                pY = me.getY();
            }
            @Override
             public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,getLocation().y + me.getY() - pY);
            }
        });

        titleLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX,getLocation().y + me.getY() - pY);
            }
        });
        
        botaoSim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resposta = JOptionPane.YES_OPTION;
                setVisible(false);
            }
        });
        
        botaoNao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resposta = JOptionPane.NO_OPTION;
                setVisible(false);
            }
        });
        
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resposta = JOptionPane.CANCEL_OPTION;
                setVisible(false);
            }
        });
        
        webButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resposta = JOptionPane.CLOSED_OPTION;
                setVisible(false);
            }
        });
    }
    
    private void reset()
    {
        textLabel.setText("");
        buttonsPane.removeAll();
        resposta = -1;
        jLabel1.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "big_logo.png"));
    }
    
    public void showMessage(String text, int type)
    {
        switch (type) {
            case JOptionPane.WARNING_MESSAGE:
                jLabel1.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "big_warning.png"));
                break;
            case JOptionPane.ERROR_MESSAGE:
                jLabel1.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "big_error.png"));
                break;
            default:
                jLabel1.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "big_logo.png"));
                break;
        }
        showMessage(text);
    }
    
    public void showMessage(String text) {
        textLabel.setText("<html><body>"+text+"</body></html>");
        buttonsPane.add(botaoSim);        
        setVisible(true);
        pack();
    }
    
    public int showConfirmMessage(String text, int type)
    {
        switch (type) {
            case JOptionPane.WARNING_MESSAGE:
                jLabel1.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "big_warning.png"));
                break;
            case JOptionPane.ERROR_MESSAGE:
                jLabel1.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "big_error.png"));
                break;
            default:
                jLabel1.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "big_logo.png"));
                break;
        }
        return showConfirmMessage(text);
    }
    
    public int showConfirmMessage(String text) {
        textLabel.setText("<html><body>"+text+"</body></html>");
        buttonsPane.add(botaoSim);
        buttonsPane.add(botaoNao);
        buttonsPane.add(botaoCancelar);
        setVisible(true);
        pack();
        return resposta;
    }
        
    @Override
    public void configurarCores() {
        titleLabel.setForeground(ColorController.COR_LETRA_TITULO);
        textLabel.setForeground(ColorController.COR_LETRA);
        titlePane.setBackground(ColorController.FUNDO_ESCURO);
        mainpane.setBackground(ColorController.COR_PRINCIPAL);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoCancelar = new com.alee.laf.button.WebButton();
        botaoNao = new com.alee.laf.button.WebButton();
        botaoSim = new com.alee.laf.button.WebButton();
        jPanel3 = new javax.swing.JPanel();
        mainpane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        buttonsPane = new javax.swing.JPanel();
        textLabel = new javax.swing.JLabel();
        titlePane = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        webButton1 = new com.alee.laf.button.WebButton();

        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });

        botaoNao.setText("Não");

        botaoSim.setText("Sim");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(400, 160));

        jPanel3.setLayout(new java.awt.BorderLayout());

        mainpane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        mainpane.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/grande/big_logo.png"))); // NOI18N
        mainpane.add(jLabel1, java.awt.BorderLayout.LINE_START);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        buttonsPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        buttonsPane.setOpaque(false);
        buttonsPane.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));
        jPanel1.add(buttonsPane, java.awt.BorderLayout.SOUTH);

        textLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textLabel.setText("taxtLabel");
        jPanel1.add(textLabel, java.awt.BorderLayout.CENTER);

        mainpane.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel3.add(mainpane, java.awt.BorderLayout.CENTER);

        titlePane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        titlePane.setLayout(new java.awt.BorderLayout());

        titleLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/light_pix.png"))); // NOI18N
        titleLabel.setText("Portugol Studio");
        titlePane.add(titleLabel, java.awt.BorderLayout.CENTER);

        webButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/window_close.png"))); // NOI18N
        webButton1.setFocusable(false);
        webButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton1ActionPerformed(evt);
            }
        });
        titlePane.add(webButton1, java.awt.BorderLayout.EAST);

        jPanel3.add(titlePane, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void webButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton1ActionPerformed
        
    }//GEN-LAST:event_webButton1ActionPerformed

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        
    }//GEN-LAST:event_botaoCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoCancelar;
    private com.alee.laf.button.WebButton botaoNao;
    private com.alee.laf.button.WebButton botaoSim;
    private javax.swing.JPanel buttonsPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel mainpane;
    private javax.swing.JLabel textLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePane;
    private com.alee.laf.button.WebButton webButton1;
    // End of variables declaration//GEN-END:variables
    
}
