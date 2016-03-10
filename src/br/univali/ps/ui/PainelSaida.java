package br.univali.ps.ui;

import br.univali.ps.ui.abas.AbaConsole;
import br.univali.ps.ui.abas.AbaMensagemCompilador;
import br.univali.ps.ui.abas.AbaEnunciado;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPaneUI;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.TabbedPaneUI;

public final class PainelSaida extends PainelTabulado {

    private final AbaConsole abaConsole;
    private final AbaMensagemCompilador abaMensagensCompilador;
    private AbaEnunciado abaEnunciado;

    public PainelSaida() {
        super();
        initComponents();

        abaConsole = new AbaConsole();
        add(abaConsole);
        
        //abaConsole.setBackground(Color.BLUE);

        abaMensagensCompilador = new AbaMensagemCompilador();
        this.add(abaMensagensCompilador);

        if (WeblafUtils.weblafEstaInstalado()) {
          setUI(criaUi());
        }
        
        setSelectedIndex(0);//deixa a console aparecendo quando abre uma nova aba
    }

    //@Override
    protected TabbedPaneUI criaUi() {
        return new PsTabbedPaneUI();
    }

    private static class PsTabbedPaneUI extends WebTabbedPaneUI {

        public PsTabbedPaneUI() {
            setTabbedPaneStyle(TabbedPaneStyle.standalone);
            setBackgroundPainterAt(0, WeblafUtils.criaPainterComCorSolida(WeblafUtils.BACKGROUND_ESCURO, false, false, false, false));
            setBackgroundPainterAt(1, WeblafUtils.criaPainterComCorSolida(WeblafUtils.BACKGROUND_ESCURO, false, false, false, false));
        }
    }
    
    public AbaConsole getConsole() {
        return abaConsole;
    }

    public AbaMensagemCompilador getAbaMensagensCompilador() {
        return abaMensagensCompilador;
    }

    public AbaEnunciado getEnunciadoAba() {
        return abaEnunciado;
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
