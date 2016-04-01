package br.univali.ps.ui.paineis;

import br.univali.ps.ui.telas.utils.Licencas;
import javax.swing.SwingUtilities;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class PainelLicenca extends javax.swing.JPanel
{
    private final String htmlRotulos = "<html><body><div><b>%s:</b> %s</div></body></html>";
    private final Licencas.Recurso recurso;
    
    public PainelLicenca(Licencas.Recurso recurso)
    {
        initComponents();
        
        jLNome.setText(String.format(htmlRotulos, "Nome", recurso.getNome()));
        jLVersao.setText(String.format(htmlRotulos, "Versão", recurso.getVersao()));
        jLUrl.setText(String.format(htmlRotulos, "Página Web", recurso.getUrl()));
        
        jTADescricao.setText(recurso.getDescricao());
        jTALicenca.setText(recurso.getLicenca());        
        
        this.recurso = recurso;
    }

    public Licencas.Recurso getRecurso()
    {
        return recurso;
    }
    
    public void redefinir()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                jSPLicenca.getVerticalScrollBar().setValue(0);
                jSPLicenca.getHorizontalScrollBar().setValue(0);
                
                jSPDescricao.getVerticalScrollBar().setValue(0);
                jSPDescricao.getHorizontalScrollBar().setValue(0);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPInformacoes = new javax.swing.JPanel();
        jLNome = new javax.swing.JLabel();
        jLVersao = new javax.swing.JLabel();
        jLUrl = new javax.swing.JLabel();
        jLDescricao = new javax.swing.JLabel();
        jSPDescricao = new javax.swing.JScrollPane();
        jTADescricao = new javax.swing.JTextArea();
        jLLicenca = new javax.swing.JLabel();
        jSPLicenca = new javax.swing.JScrollPane();
        jTALicenca = new javax.swing.JTextArea();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jPInformacoes.setBackground(new java.awt.Color(255, 255, 255));
        jPInformacoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        java.awt.GridBagLayout jPInformacoesLayout = new java.awt.GridBagLayout();
        jPInformacoesLayout.columnWidths = new int[] {0};
        jPInformacoesLayout.rowHeights = new int[] {0, 8, 0, 8, 0, 8, 0, 8, 0, 8, 0, 8, 0};
        jPInformacoes.setLayout(jPInformacoesLayout);

        jLNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLNome.setText("<html><body><b>Nome:</b> RSyntaxTextArea</body></html>");
        jLNome.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jLNome, gridBagConstraints);

        jLVersao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLVersao.setText("<html><body><b>Versão:</b> 2.5.0</body></html>");
        jLVersao.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jLVersao, gridBagConstraints);

        jLUrl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLUrl.setText("<html><body><b>Página Web:</b>http://umrecurso.com.br/recurso</body></html>");
        jLUrl.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jLUrl, gridBagConstraints);

        jLDescricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLDescricao.setText("<html><body><b>Descrição:</b></body></html>");
        jLDescricao.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        jPInformacoes.add(jLDescricao, gridBagConstraints);

        jSPDescricao.setBackground(new java.awt.Color(228, 241, 254));
        jSPDescricao.setBorder(null);
        jSPDescricao.setMaximumSize(new java.awt.Dimension(32767, 92));
        jSPDescricao.setMinimumSize(new java.awt.Dimension(39, 92));
        jSPDescricao.setPreferredSize(new java.awt.Dimension(166, 92));

        jTADescricao.setEditable(false);
        jTADescricao.setBackground(new java.awt.Color(228, 241, 254));
        jTADescricao.setColumns(20);
        jTADescricao.setLineWrap(true);
        jTADescricao.setWrapStyleWord(true);
        jTADescricao.setAutoscrolls(false);
        jTADescricao.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jTADescricao.setFocusable(false);
        jTADescricao.setMinimumSize(new java.awt.Dimension(100, 50));
        jTADescricao.setRequestFocusEnabled(false);
        jTADescricao.setVerifyInputWhenFocusTarget(false);
        jSPDescricao.setViewportView(jTADescricao);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jSPDescricao, gridBagConstraints);

        jLLicenca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLLicenca.setText("<html><body><b>Licença:</b></body></html>");
        jLLicenca.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jLLicenca, gridBagConstraints);

        jSPLicenca.setBackground(new java.awt.Color(228, 241, 254));
        jSPLicenca.setBorder(null);

        jTALicenca.setEditable(false);
        jTALicenca.setBackground(new java.awt.Color(228, 241, 254));
        jTALicenca.setColumns(20);
        jTALicenca.setRows(5);
        jTALicenca.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jTALicenca.setFocusable(false);
        jTALicenca.setVerifyInputWhenFocusTarget(false);
        jSPLicenca.setViewportView(jTALicenca);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPInformacoes.add(jSPLicenca, gridBagConstraints);

        add(jPInformacoes, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLDescricao;
    private javax.swing.JLabel jLLicenca;
    private javax.swing.JLabel jLNome;
    private javax.swing.JLabel jLUrl;
    private javax.swing.JLabel jLVersao;
    private javax.swing.JPanel jPInformacoes;
    private javax.swing.JScrollPane jSPDescricao;
    private javax.swing.JScrollPane jSPLicenca;
    private javax.swing.JTextArea jTADescricao;
    private javax.swing.JTextArea jTALicenca;
    // End of variables declaration//GEN-END:variables
}
