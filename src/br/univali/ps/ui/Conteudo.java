package br.univali.ps.ui;

import br.univali.ps.controller.ControladorListener;
import br.univali.ps.controller.PortugolControlador;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.ui.acoes.AcaoColar;
import br.univali.ps.ui.acoes.AcaoCopiar;
import br.univali.ps.ui.acoes.AcaoDesfazer;
import br.univali.ps.ui.acoes.AcaoRecortar;
import br.univali.ps.ui.acoes.AcaoRefazer;
import br.univali.ps.ui.acoes.AcaoSalvarArquivo;

public class Conteudo extends javax.swing.JPanel implements ControladorListener, PortugolDocumentoListener{

    private AcaoSalvarArquivo acaoSalvarArquivo = new AcaoSalvarArquivo();
    private AcaoDesfazer acaoDesfazer = new AcaoDesfazer();
    private AcaoRefazer acaoRefazer = new AcaoRefazer();
    private AcaoRecortar acaoRecortar = new AcaoRecortar();
    private AcaoCopiar acaoCopiar = new AcaoCopiar();
    private AcaoColar acaoColar = new AcaoColar();
    private PortugolControlador controle;

    /** Creates new form Conteudo */
    public Conteudo() {
        initComponents();
        acaoSalvarArquivo.removerIconeGrande();
        btnSalvar.setAction(acaoSalvarArquivo);
        btnDesfazer.setAction(acaoDesfazer);
        btnRefazer.setAction(acaoRefazer);
        btnRecortar.setAction(acaoRecortar);
        btnCopiar.setAction(acaoCopiar);
        btnColar.setAction(acaoColar);
        configurarAcoes();
    }
    
    public void setPortugolControlador(PortugolControlador portugolControlador){
        controle = portugolControlador;
        editor.getPortugolDocumento().addPortugolDocumentoListener(this);
        acaoSalvarArquivo.configurar(controle,editor.getPortugolDocumento());
    }
    
    public Editor getEditor(){
        return editor;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        undoRedoBar = new javax.swing.JToolBar();
        btnSalvar = new javax.swing.JButton();
        btnDesfazer = new javax.swing.JButton();
        btnRefazer = new javax.swing.JButton();
        jSeparador1 = new javax.swing.JToolBar.Separator();
        btnRecortar = new javax.swing.JButton();
        btnCopiar = new javax.swing.JButton();
        btnColar = new javax.swing.JButton();
        jSeparador2 = new javax.swing.JToolBar.Separator();
        btnCompile = new javax.swing.JButton();
        btnDebug = new javax.swing.JButton();
        jPainelSeparador = new javax.swing.JSplitPane();
        painelSaida1 = new br.univali.ps.ui.PainelSaida();
        editor = new br.univali.ps.ui.Editor();

        setLayout(new java.awt.BorderLayout());

        undoRedoBar.setFloatable(false);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/disk.png"))); // NOI18N
        btnSalvar.setFocusable(false);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoRedoBar.add(btnSalvar);

        btnDesfazer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/arrow_undo.png"))); // NOI18N
        btnDesfazer.setFocusable(false);
        btnDesfazer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDesfazer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoRedoBar.add(btnDesfazer);

        btnRefazer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/arrow_redo.png"))); // NOI18N
        btnRefazer.setFocusable(false);
        btnRefazer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefazer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoRedoBar.add(btnRefazer);
        undoRedoBar.add(jSeparador1);

        btnRecortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/cut_red.png"))); // NOI18N
        btnRecortar.setFocusable(false);
        btnRecortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRecortar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoRedoBar.add(btnRecortar);

        btnCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/page_white_copy.png"))); // NOI18N
        btnCopiar.setFocusable(false);
        btnCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoRedoBar.add(btnCopiar);

        btnColar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/page_white_paste.png"))); // NOI18N
        btnColar.setFocusable(false);
        btnColar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoRedoBar.add(btnColar);
        undoRedoBar.add(jSeparador2);

        btnCompile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/control_play_blue.png"))); // NOI18N
        btnCompile.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/control_play.png"))); // NOI18N
        btnCompile.setEnabled(false);
        btnCompile.setFocusable(false);
        btnCompile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompileActionPerformed(evt);
            }
        });
        undoRedoBar.add(btnCompile);

        btnDebug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/control_stop_blue.png"))); // NOI18N
        btnDebug.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/control_stop.png"))); // NOI18N
        btnDebug.setEnabled(false);
        btnDebug.setFocusable(false);
        btnDebug.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDebug.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDebug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDebugActionPerformed(evt);
            }
        });
        undoRedoBar.add(btnDebug);

        add(undoRedoBar, java.awt.BorderLayout.PAGE_START);

        jPainelSeparador.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jPainelSeparador.setBottomComponent(painelSaida1);
        jPainelSeparador.setLeftComponent(editor);

        add(jPainelSeparador, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompileActionPerformed
        controle.executar(editor.getPortugolDocumento());
}//GEN-LAST:event_btnCompileActionPerformed

    private void btnDebugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDebugActionPerformed
        controle.interromper(editor.getPortugolDocumento());
}//GEN-LAST:event_btnDebugActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnColar;
    private javax.swing.JButton btnCompile;
    private javax.swing.JButton btnCopiar;
    private javax.swing.JButton btnDebug;
    private javax.swing.JButton btnDesfazer;
    private javax.swing.JButton btnRecortar;
    private javax.swing.JButton btnRefazer;
    private javax.swing.JButton btnSalvar;
    private br.univali.ps.ui.Editor editor;
    private javax.swing.JSplitPane jPainelSeparador;
    private javax.swing.JToolBar.Separator jSeparador1;
    private javax.swing.JToolBar.Separator jSeparador2;
    private br.univali.ps.ui.PainelSaida painelSaida1;
    private javax.swing.JToolBar undoRedoBar;
    // End of variables declaration//GEN-END:variables

    private void configurarAcoes() {
        acaoDesfazer.iniciar();
        acaoRefazer.iniciar();
        acaoRecortar.iniciar();
        acaoCopiar.iniciar();
        acaoColar.iniciar();
    }

    @Override
    public void ArquivoSalvo() {
        btnSalvar.setEnabled(false);
    }

    public void documentoModificado(boolean status) {
        btnSalvar.setEnabled(status);
    }

}
