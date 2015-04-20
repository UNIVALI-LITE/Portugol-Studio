package br.univali.ps.ui.weblaf;

import br.univali.ps.ui.PainelTabuladoPrincipal;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.table.WebTableHeaderUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author elieser
 */
public class TesteWeblaf {

    private static class PSTableHeaderUI extends WebTableHeaderUI {

        private static final Color corDaLinhaDeBaixo = new Color(154, 154, 154);

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            g.setColor(corDaLinhaDeBaixo);
            g.drawLine(0, header.getHeight() - 1, header.getWidth(), header.getHeight() - 1);
        }

    }

    public static void main(final String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                WebLookAndFeel.install();

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLayout(new BorderLayout());

                JPanel painelConteudo = new JPanel();
                painelConteudo.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
                WeblafUtils.configuraWeblaf(painelConteudo, Color.WHITE);

                JTable table = new JTable();
                table.getTableHeader().setUI(new PSTableHeaderUI());
                table.setModel(new DefaultTableModel(new Object[][]{{"asd", "asd"}, {"asds", "asd"}}, new Object[]{"A", "B"}));

                JScrollPane scroll = new JScrollPane(table);
                scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                WeblafUtils.configuraWebLaf(scroll);

                JTabbedPane tabbedPane = new PainelTabuladoPrincipal();
                tabbedPane.addTab("teste", scroll);
                tabbedPane.addTab("teste 2", AbaCodigoFonte.novaAba());
                
                painelConteudo.add(tabbedPane);

//                JPanel painel = new JPanel(new BorderLayout());
//                painel.setPreferredSize(new Dimension(2000, 2000));
//                
//                WeblafUtils.configuraWeblaf(painel, Color.LIGHT_GRAY);
//                
//                BarraDeBotoesExpansivel barraDeBotoesExpansivel = new BarraDeBotoesExpansivel();
//                BarraDeBotoesExpansivel.Acao acao = BarraDeBotoesExpansivel.criaAcao(new AbstractAction("asd") {
//                    
//                    @Override
//                    public void actionPerformed(ActionEvent ae) {
//                        JOptionPane.showMessageDialog(null, "asdasd");
//                    }
//                }, WebComboBoxStyle.collapseIcon);
//                barraDeBotoesExpansivel.adicionaAcao(acao);
//
//                painel.add(barraDeBotoesExpansivel, BorderLayout.EAST);
//                
//                painelConteudo.add(scroll);
                frame.add(painelConteudo, BorderLayout.CENTER);
                frame.setVisible(true);

            }
        });
    }

}
