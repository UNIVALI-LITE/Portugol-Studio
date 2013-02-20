package br.univali.ps.ui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JTabbedPane;

public class AbaInicial extends Aba {

    public AbaInicial(JTabbedPane painelTabulado) {
        super(painelTabulado);
        initComponents();
        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent ce) {
                if (ce.getComponent().getHeight() - 70 > 130)
                    logoUnivali.setLocation(logoUnivali.getX(), ce.getComponent().getHeight() - 70);
            }
            
        });
        
    }

    @Override
    protected CabecalhoAba criarCabecalho() {
        return new BotoesControleAba(this);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        logo1 = new br.univali.ps.ui.imagens.Logo();
        logoUnivali = new javax.swing.JLabel();
        fundo = new javax.swing.JLabel();

        setLayout(null);
        add(logo1);
        logo1.setBounds(20, 20, 330, 150);

        logoUnivali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/univali.png"))); // NOI18N
        add(logoUnivali);
        logoUnivali.setBounds(20, 590, 80, 80);

        fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/Light and line_4_Blue_1280 x 1024-1.jpg"))); // NOI18N
        fundo.setText("jLabel4");
        add(fundo);
        fundo.setBounds(0, 0, 1280, 1024);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fundo;
    private br.univali.ps.ui.imagens.Logo logo1;
    private javax.swing.JLabel logoUnivali;
    // End of variables declaration//GEN-END:variables
}
