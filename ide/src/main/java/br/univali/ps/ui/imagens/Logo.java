package br.univali.ps.ui.imagens;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import java.awt.Cursor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fillipi Domingos Pelz
 * @author Luiz Fernando Noschang
 */
public class Logo extends javax.swing.JPanel
{
    public Logo()
    {
        initComponents();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                // Redimensiona painel para caber o texto do label
                validate();
            }
        });

        FabricaDicasInterface.criarTooltip(Logo.this, "Visitar página oficial");
        versao.setText(PortugolStudio.getInstancia().getVersao());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        versao = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();

        versao.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        versao.setForeground(new java.awt.Color(255, 194, 0));
        versao.setText("versao.properties");

        setBackground(new java.awt.Color(35, 72, 127));
        setMaximumSize(new java.awt.Dimension(310, 110));
        setMinimumSize(new java.awt.Dimension(310, 50));
        setPreferredSize(new java.awt.Dimension(310, 100));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(new java.awt.GridBagLayout());

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_ps.png"))); // NOI18N
        logo.setMaximumSize(new java.awt.Dimension(293, 150));
        logo.setMinimumSize(new java.awt.Dimension(293, 50));
        logo.setName("labelLogo"); // NOI18N
        add(logo, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseClicked
    {//GEN-HEADEREND:event_formMouseClicked
        try
        {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://lite.acad.univali.br/portugol/"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(AbaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel logo;
    private javax.swing.JLabel versao;
    // End of variables declaration//GEN-END:variables
}
