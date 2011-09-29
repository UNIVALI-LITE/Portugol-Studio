package br.univali.ps.ui;

import br.univali.ps.controller.PortugolControladorTelaPrincipal;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.ui.acoes.AcaoColar;
import br.univali.ps.ui.acoes.AcaoCopiar;
import br.univali.ps.ui.acoes.AcaoDesfazer;
import br.univali.ps.ui.acoes.AcaoRecortar;
import br.univali.ps.ui.acoes.AcaoRefazer;
import br.univali.ps.ui.acoes.AcaoSalvarArquivo;
import br.univali.ps.ui.acoes.FabricaAcao;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class AbaCodigoFonte extends Aba implements PortugolDocumentoListener, AbaListener, AbaMensagemCompiladorListener {

    private AcaoSalvarArquivo acaoSalvarArquivo = (AcaoSalvarArquivo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarArquivo.class);
    private AcaoDesfazer acaoDesfazer = (AcaoDesfazer) FabricaAcao.getInstancia().criarAcao(AcaoDesfazer.class);
    private AcaoRefazer acaoRefazer = (AcaoRefazer) FabricaAcao.getInstancia().criarAcao(AcaoRefazer.class);
    private AcaoRecortar acaoRecortar = (AcaoRecortar) FabricaAcao.getInstancia().criarAcao(AcaoRecortar.class);
    private AcaoCopiar acaoCopiar = (AcaoCopiar) FabricaAcao.getInstancia().criarAcao(AcaoCopiar.class);
    private AcaoColar acaoColar = (AcaoColar) FabricaAcao.getInstancia().criarAcao(AcaoColar.class);
    private PortugolControladorTelaPrincipal controle;

    /** Creates new form Conteudo */
    public AbaCodigoFonte(JTabbedPane painelTabulado) {
        super(painelTabulado);
        initComponents();
        configurarAcoes();
        adicionarAbaListener(this);
        jPainelSeparador.setDividerLocation(480);
        this.addComponentListener(new AdaptadorComponente());
        painelSaida.getMensagemCompilador().adicionaAbaMensagemCompiladorListener(this);
        editor.requestFocusInWindow();
    }
    
    public void setPortugolControlador(PortugolControladorTelaPrincipal portugolControlador){
        controle = portugolControlador;
        editor.getPortugolDocumento().addPortugolDocumentoListener(this);
        acaoSalvarArquivo.configurar(controle,editor.getPortugolDocumento());
        this.btnCompile.setEnabled(true);
    }
    
    public void setPortugolDocumento(PortugolDocumento portugolDocumento){
        portugolDocumento.addPortugolDocumentoListener(this);
        editor.setPortugolDocumento(portugolDocumento);
        acaoSalvarArquivo.configurar(controle, portugolDocumento);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barraFerramenta = new javax.swing.JToolBar();
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
        painelSaida = new br.univali.ps.ui.PainelSaida();
        editor = new br.univali.ps.ui.Editor();

        setLayout(new java.awt.BorderLayout());

        barraFerramenta.setFloatable(false);
        barraFerramenta.setOpaque(false);

        btnSalvar.setAction(acaoSalvarArquivo);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnSalvar);

        btnDesfazer.setAction(acaoDesfazer);
        btnDesfazer.setFocusPainted(false);
        barraFerramenta.add(btnDesfazer);

        btnRefazer.setAction(acaoRefazer);
        btnRefazer.setFocusPainted(false);
        btnRefazer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefazer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnRefazer);
        barraFerramenta.add(jSeparador1);

        btnRecortar.setAction(acaoRecortar);
        btnRecortar.setFocusPainted(false);
        btnRecortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRecortar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnRecortar);

        btnCopiar.setAction(acaoCopiar);
        btnCopiar.setFocusPainted(false);
        btnCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnCopiar);

        btnColar.setAction(acaoColar);
        btnColar.setFocusPainted(false);
        btnColar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnColar);
        barraFerramenta.add(jSeparador2);

        btnCompile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_play_blue.png"))); // NOI18N
        btnCompile.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_play.png"))); // NOI18N
        btnCompile.setEnabled(false);
        btnCompile.setFocusPainted(false);
        btnCompile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompileActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnCompile);

        btnDebug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_stop_blue.png"))); // NOI18N
        btnDebug.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_stop.png"))); // NOI18N
        btnDebug.setEnabled(false);
        btnDebug.setFocusPainted(false);
        btnDebug.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDebug.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDebug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDebugActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnDebug);

        add(barraFerramenta, java.awt.BorderLayout.PAGE_START);

        jPainelSeparador.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jPainelSeparador.setBottomComponent(painelSaida);
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
    private javax.swing.JToolBar barraFerramenta;
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
    private br.univali.ps.ui.PainelSaida painelSaida;
    // End of variables declaration//GEN-END:variables

    private void configurarAcoes() {
        acaoDesfazer.iniciar();
        acaoRefazer.iniciar();
        acaoRecortar.iniciar();
        acaoCopiar.iniciar();
        acaoColar.iniciar();
        btnDesfazer.setText("");
        btnRefazer.setText("");
        btnRecortar.setText("");
        btnColar.setText("");
        btnCopiar.setText("");
        btnSalvar.setText("");
    }

    @Override
    public void documentoModificado(boolean status) {
        acaoSalvarArquivo.setEnabled(status);
        if (status)
            cabecalho.setForegroung(Color.RED);
        else 
            cabecalho.setForegroung(Color.BLACK);
    }

    @Override
    public boolean fechandoAba(Aba aba) {
    if (editor.getPortugolDocumento().isChanged()) {
            int resp = JOptionPane.showConfirmDialog(this, "O documento possui modificações, deseja Salva-las?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                controle.salvar(editor.getPortugolDocumento());
                return (true);
            } else if (resp == JOptionPane.NO_OPTION) {
                return (true);
            } else {
                return (false);
            }
        } else {
            return (true);
        }   
    }

    public void posicionarCursor(int linha, int coluna) {
        editor.posicionaCursor(linha, coluna);
    }

    public void nomeArquivoAlterado(String nome) {
        cabecalho.setTitulo(nome);
    }

    public AcaoSalvarArquivo getAcaoSalvar() {
        return acaoSalvarArquivo;
    }

    public PortugolDocumento getPortugolDocumento() {
        return editor.getPortugolDocumento();
    }

    private class AdaptadorComponente extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            jPainelSeparador.setDividerLocation(AbaCodigoFonte.this.getHeight() - 300);
        }
    }
}
