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

        gradiente1 = new br.univali.ps.ui.imagens.Gradiente();
        logo1 = new br.univali.ps.ui.imagens.Logo();
        logoUnivali = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        gradiente1.setLayout(null);
        gradiente1.add(logo1);
        logo1.setBounds(20, 20, 330, 150);

        logoUnivali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/univali.png"))); // NOI18N
        gradiente1.add(logoUnivali);
        logoUnivali.setBounds(50, 600, 80, 80);

        add(gradiente1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.univali.ps.ui.imagens.Gradiente gradiente1;
    private br.univali.ps.ui.imagens.Logo logo1;
    private javax.swing.JLabel logoUnivali;
    // End of variables declaration//GEN-END:variables
}
