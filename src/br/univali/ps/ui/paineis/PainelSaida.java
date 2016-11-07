package br.univali.ps.ui.paineis;

import br.univali.ps.ui.paineis.utils.PainelTabulado;
import br.univali.ps.ui.abas.AbaConsole;
import br.univali.ps.ui.abas.AbaMensagemCompilador;
import br.univali.ps.ui.swing.weblaf.PSOutTabbedPaneUI;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.tabbedpane.WebTabbedPaneUI;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public final class PainelSaida extends PainelTabulado {

    private final AbaConsole abaConsole;
    private final AbaMensagemCompilador abaMensagensCompilador;

    public PainelSaida() {
        super();
        initComponents();
        
        abaConsole = new AbaConsole();
        
        add(abaConsole);
        
        //abaConsole.setBackground(Color.BLUE);

        abaMensagensCompilador = new AbaMensagemCompilador();
        this.add(abaMensagensCompilador);

        if (WeblafUtils.weblafEstaInstalado()) {
          ((WebTabbedPaneUI)getUI()).setShadeWidth(0);
        }
        
        selecionaConsole(); //deixa a console aparecendo quando abre uma nova aba
        
        this.setUI(new PSOutTabbedPaneUI());
    }
    
    public void selecionaConsole()
    {
        setSelectedIndex(0);        
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg); //To change body of generated methods, choose Tools | Templates.
        if(abaConsole!=null){
            abaConsole.setBackground(bg);
        }
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg); //To change body of generated methods, choose Tools | Templates.
        if(abaConsole!=null){
            abaConsole.setForeground(fg);
        }
    }
    
    
    
    
    public AbaConsole getConsole() {
        return abaConsole;
    }

    public AbaMensagemCompilador getAbaMensagensCompilador() {
        return abaMensagensCompilador;
    }
    
    public static void main(final String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                WeblafUtils.instalaWeblaf();

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLayout(new BorderLayout());

                JPanel painelConteudo = new JPanel();
                WeblafUtils.configuraWeblaf(painelConteudo, Color.WHITE);

                PainelSaida painelSaida = new PainelSaida();

                painelConteudo.add(painelSaida);

                frame.add(painelConteudo, BorderLayout.CENTER);
                frame.setVisible(true);

            }
        });
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 8, 8, 5));
        setName(""); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
