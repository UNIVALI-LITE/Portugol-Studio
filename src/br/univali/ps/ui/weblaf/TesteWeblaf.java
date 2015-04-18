package br.univali.ps.ui.weblaf;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.combobox.WebComboBoxStyle;
import com.alee.laf.panel.WebPanelStyle;
import com.alee.laf.panel.WebPanelUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author elieser
 */
public class TesteWeblaf {

    public static void main(final String args[]){
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                WebLookAndFeel.install();
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLayout(new BorderLayout());
                
                JPanel painel = new JPanel(new BorderLayout());
                
                WeblafUtils.configuraWeblaf(painel, Color.LIGHT_GRAY);
                
                BarraDeBotoesExpansivel barraDeBotoesExpansivel = new BarraDeBotoesExpansivel();
                BarraDeBotoesExpansivel.Acao acao = BarraDeBotoesExpansivel.criaAcao(new AbstractAction("asd") {
                    
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JOptionPane.showMessageDialog(null, "asdasd");
                    }
                }, WebComboBoxStyle.collapseIcon);
                barraDeBotoesExpansivel.adicionaAcao(acao);

                painel.add(barraDeBotoesExpansivel, BorderLayout.EAST);
                frame.add(painel, BorderLayout.CENTER );
                frame.setVisible(true);
                
            }
        });
    }
    
}
