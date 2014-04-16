package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.PortugolStudio;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public final class TelaLicencas extends javax.swing.JDialog
{
    private final List<PainelLicenca> paineis;

    public TelaLicencas()
    {
        super((JFrame) null, true);

        initComponents();        
        setTitle("Licenças");
        setSize(640, 550);        
        setLocationRelativeTo(null);
        
        paineis = criarPaineis();
        
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                redefinirPaineis();
            }
        });
    }

    private void redefinirPaineis()
    {
        painelTabulado.setSelectedIndex(0);
        
        for (PainelLicenca painel : paineis)
        {
            painel.redefinir();
        }
    }

    private List<PainelLicenca> criarPaineis()
    {
        List<PainelLicenca> listaPaineis = new ArrayList<>();
        Licencas licencas = Licencas.carregar();

        for (Licencas.Recurso recurso : licencas.getRecursos())
        {
            if (recurso.getNome().equals("Portugol Studio"))
            {
                recurso.setVersao(PortugolStudio.getInstancia().getVersao());
            }
            
            PainelLicenca painelLicenca = new PainelLicenca(recurso);
            listaPaineis.add(painelLicenca);

            painelTabulado.add(recurso.getNome(), painelLicenca);
        }

        return listaPaineis;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelConteudo = new javax.swing.JPanel();
        painelTabulado = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(659, 526));
        setResizable(false);

        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        painelTabulado.setFocusable(false);
        painelConteudo.add(painelTabulado, java.awt.BorderLayout.CENTER);

        getContentPane().add(painelConteudo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JTabbedPane painelTabulado;
    // End of variables declaration//GEN-END:variables
}
