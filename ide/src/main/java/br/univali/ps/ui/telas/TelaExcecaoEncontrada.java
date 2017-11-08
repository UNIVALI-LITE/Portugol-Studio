package br.univali.ps.ui.telas;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.utils.WebConnectionUtils;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author Adson Esteves
 */
public class TelaExcecaoEncontrada extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form TelaExcecaoEncontrada
     */
    
    private JDialog telaCustomBorder;
    
    public TelaExcecaoEncontrada(JDialog telaPrincipal) {
        initComponents();
        configurarCores();
        telaCustomBorder = telaPrincipal;
        botaoCopiarErro.setAction(new AbstractAction("Copiar Erro")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Clipboard areaTransferencia = Toolkit.getDefaultToolkit().getSystemClipboard();
                areaTransferencia.setContents(new StringSelection(areaTextoStackTrace.getText()), null);
                FabricaDicasInterface.criarTooltipEstatica(botaoCopiarErro, "Copiado");
            }
        });
        botaoReportarBug.setAction(new AbstractAction("Reportar Erro")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WebConnectionUtils.abrirSite("https://github.com/UNIVALI-LITE/Portugol-Studio/issues/new?title=Erro%20numero%20xxx&body=%3E%20Esta%20issue%20foi%20gerada%20automaticamente%0A%0A[pressione%20o%20botao%20%22copiar%20erro%22%20no%20Portugol%20Studio%20e%20cole%20o%20erro%20aqui%20antes%20de%20enviar]");
            }
        });
        areaTextoStackTrace.setEditable(false);
        scrollStackTrace.setVisible(false);
        
        configurarLink(labelDiscord);
        configurarLink(labelFacebook);
        configurarLink(labelGmail);
        FabricaDicasInterface.criarTooltip(labelDiscord, "Nosso servidor no Discord");
        FabricaDicasInterface.criarTooltip(labelFacebook, "A página do laboratório no Facebook");
        FabricaDicasInterface.criarTooltip(labelGmail, "Nosso e-mail");
        
    }

    @Override
    public void configurarCores() {
        paineInferior.setBackground(ColorController.FUNDO_ESCURO);
        jLabel1.setForeground(ColorController.COR_LETRA_TITULO);
        painelPrincipal.setBackground(ColorController.FUNDO_CLARO);
        painelExplicacao.setBackground(ColorController.FUNDO_CLARO);
        erroTitulo.setBackground(ColorController.FUNDO_MEDIO);
        labelTitulo.setForeground(ColorController.COR_LETRA);
        erroDesc.setBackground(ColorController.FUNDO_CLARO);
        labelDescricaoReport.setForeground(ColorController.COR_LETRA);
        painelStackTrace.setBackground(ColorController.FUNDO_MEDIO);
        painelBotao.setBackground(ColorController.FUNDO_CLARO);
        areaTextoStackTrace.setBackground(ColorController.FUNDO_MEDIO);
        areaTextoStackTrace.setForeground(ColorController.COR_LETRA);
        if(WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configurarBotao(botaoAbrirStack, ColorController.AMARELO, ColorController.FUNDO_ESCURO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configurarBotao(botaoCopiarErro, ColorController.AMARELO, ColorController.FUNDO_ESCURO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configurarBotao(botaoReportarBug, ColorController.AMARELO, ColorController.FUNDO_ESCURO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 2, true);
            WeblafUtils.configuraWebLaf(scrollStackTrace);
        }
    }

    public JDialog getTelaCustomBorder() {
        return telaCustomBorder;
    }

    public JTextArea getAreaTextoStackTrace() {
        return areaTextoStackTrace;
    }
    
    private void configurarLink(final JLabel rotulo)
    {
        rotulo.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                WebConnectionUtils.abrirSite(rotulo.getName());
            }
        });
    }
    
    public static void main(String[] args) {
        WeblafUtils.instalaWeblaf();
        TelaCustomBorder main = new TelaCustomBorder("Erro Encontrado");
        TelaExcecaoEncontrada tee = new TelaExcecaoEncontrada(main);
        main.setPanel(tee);
        tee.areaTextoStackTrace.setText("Exception in thread \"main\" java.lang.NullPointerException\n" +
"	at br.univali.ps.ui.inspetor.InspetorDeSimbolos.main(InspetorDeSimbolos.java:765)\n" +
"C:\\Users\\Adson Esteves\\Documents\\Github\\Portugol-Studio\\nbproject\\build-impl.xml:1116: The following error occurred while executing this line:\n" +
"C:\\Users\\Adson Esteves\\Documents\\Github\\Portugol-Studio\\nbproject\\build-impl.xml:830: Java returned: 1");
        main.setVisible(true);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelExplicacao = new javax.swing.JPanel();
        erroTitulo = new javax.swing.JPanel();
        labelTitulo = new javax.swing.JLabel();
        erroDesc = new javax.swing.JPanel();
        labelDescricaoReport = new javax.swing.JLabel();
        painelStackTrace = new javax.swing.JPanel();
        painelBotao = new javax.swing.JPanel();
        botaoAbrirStack = new com.alee.laf.button.WebButton();
        botaoCopiarErro = new com.alee.laf.button.WebButton();
        botaoReportarBug = new com.alee.laf.button.WebButton();
        scrollStackTrace = new javax.swing.JScrollPane();
        areaTextoStackTrace = new javax.swing.JTextArea();
        paineInferior = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        labelDiscord = new javax.swing.JLabel();
        labelFacebook = new javax.swing.JLabel();
        labelGmail = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        painelPrincipal.setLayout(new java.awt.BorderLayout());

        painelExplicacao.setLayout(new java.awt.BorderLayout());

        labelTitulo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        labelTitulo.setText("<html><body><div style=\"text-align: center\">Um erro desconhecido foi encontrado! Ajude-nos a Resolvê-lo!</div></body></html>");

        javax.swing.GroupLayout erroTituloLayout = new javax.swing.GroupLayout(erroTitulo);
        erroTitulo.setLayout(erroTituloLayout);
        erroTituloLayout.setHorizontalGroup(
            erroTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(erroTituloLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        erroTituloLayout.setVerticalGroup(
            erroTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        painelExplicacao.add(erroTitulo, java.awt.BorderLayout.NORTH);

        labelDescricaoReport.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        labelDescricaoReport.setText("<html><body><div style=\"text-align: center\">Recomendamos que nos avise do erro copiando-o e nos enviando através de uma Issue no Github apertando no botão \"Reportar Erro\"</div></body></html>");

        javax.swing.GroupLayout erroDescLayout = new javax.swing.GroupLayout(erroDesc);
        erroDesc.setLayout(erroDescLayout);
        erroDescLayout.setHorizontalGroup(
            erroDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, erroDescLayout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(labelDescricaoReport, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        erroDescLayout.setVerticalGroup(
            erroDescLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(erroDescLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDescricaoReport, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelExplicacao.add(erroDesc, java.awt.BorderLayout.SOUTH);

        painelPrincipal.add(painelExplicacao, java.awt.BorderLayout.NORTH);

        painelStackTrace.setLayout(new java.awt.BorderLayout());

        painelBotao.setPreferredSize(new java.awt.Dimension(403, 30));

        botaoAbrirStack.setText("Mostrar Erros");
        botaoAbrirStack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAbrirStackActionPerformed(evt);
            }
        });

        botaoCopiarErro.setText("Copiar Erro");

        botaoReportarBug.setText("Reportar Erro");

        javax.swing.GroupLayout painelBotaoLayout = new javax.swing.GroupLayout(painelBotao);
        painelBotao.setLayout(painelBotaoLayout);
        painelBotaoLayout.setHorizontalGroup(
            painelBotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botaoAbrirStack, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(botaoCopiarErro, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(botaoReportarBug, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelBotaoLayout.setVerticalGroup(
            painelBotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelBotaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(botaoAbrirStack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(botaoCopiarErro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(botaoReportarBug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        painelStackTrace.add(painelBotao, java.awt.BorderLayout.NORTH);

        areaTextoStackTrace.setColumns(20);
        areaTextoStackTrace.setRows(5);
        scrollStackTrace.setViewportView(areaTextoStackTrace);

        painelStackTrace.add(scrollStackTrace, java.awt.BorderLayout.SOUTH);

        painelPrincipal.add(painelStackTrace, java.awt.BorderLayout.CENTER);

        add(painelPrincipal, java.awt.BorderLayout.NORTH);

        paineInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Outras formas de contato:");
        paineInferior.add(jLabel1);

        labelDiscord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDiscord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/discord.png"))); // NOI18N
        labelDiscord.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelDiscord.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelDiscord.setName("https://discord.gg/TPXmZtb"); // NOI18N
        labelDiscord.setPreferredSize(new java.awt.Dimension(50, 50));
        paineInferior.add(labelDiscord);

        labelFacebook.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFacebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/fb.png"))); // NOI18N
        labelFacebook.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelFacebook.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelFacebook.setName("https://www.facebook.com/univalilite/"); // NOI18N
        labelFacebook.setPreferredSize(new java.awt.Dimension(50, 50));
        paineInferior.add(labelFacebook);

        labelGmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/gmail.png"))); // NOI18N
        labelGmail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelGmail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelGmail.setName("mailto:portugol.studio@gmail.com"); // NOI18N
        labelGmail.setPreferredSize(new java.awt.Dimension(50, 50));
        paineInferior.add(labelGmail);

        add(paineInferior, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAbrirStackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAbrirStackActionPerformed
        scrollStackTrace.setVisible(!scrollStackTrace.isVisible());
        telaCustomBorder.pack();
    }//GEN-LAST:event_botaoAbrirStackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaTextoStackTrace;
    private com.alee.laf.button.WebButton botaoAbrirStack;
    private com.alee.laf.button.WebButton botaoCopiarErro;
    private com.alee.laf.button.WebButton botaoReportarBug;
    private javax.swing.JPanel erroDesc;
    private javax.swing.JPanel erroTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelDescricaoReport;
    private javax.swing.JLabel labelDiscord;
    private javax.swing.JLabel labelFacebook;
    private javax.swing.JLabel labelGmail;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JPanel paineInferior;
    private javax.swing.JPanel painelBotao;
    private javax.swing.JPanel painelExplicacao;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelStackTrace;
    private javax.swing.JScrollPane scrollStackTrace;
    // End of variables declaration//GEN-END:variables
}
