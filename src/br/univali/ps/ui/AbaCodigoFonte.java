package br.univali.ps.ui;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.ui.acoes.AcaoColar;
import br.univali.ps.ui.acoes.AcaoCopiar;
import br.univali.ps.ui.acoes.AcaoDesfazer;
import br.univali.ps.ui.acoes.AcaoRecortar;
import br.univali.ps.ui.acoes.AcaoRefazer;
import br.univali.ps.ui.acoes.AcaoSalvarArquivo;
import br.univali.ps.ui.acoes.FabricaAcao;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class AbaCodigoFonte extends Aba implements PortugolDocumentoListener, AbaListener, AbaMensagemCompiladorListener, ObservadorExecucao {

    private Programa programa = null;
    private AcaoSalvarArquivo acaoSalvarArquivo = (AcaoSalvarArquivo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarArquivo.class);
    private AcaoDesfazer acaoDesfazer = (AcaoDesfazer) FabricaAcao.getInstancia().criarAcao(AcaoDesfazer.class);
    private AcaoRefazer acaoRefazer = (AcaoRefazer) FabricaAcao.getInstancia().criarAcao(AcaoRefazer.class);
    private AcaoRecortar acaoRecortar = (AcaoRecortar) FabricaAcao.getInstancia().criarAcao(AcaoRecortar.class);
    private AcaoCopiar acaoCopiar = (AcaoCopiar) FabricaAcao.getInstancia().criarAcao(AcaoCopiar.class);
    private AcaoColar acaoColar = (AcaoColar) FabricaAcao.getInstancia().criarAcao(AcaoColar.class);
    private Icon lampadaAcesa = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png"); 
    private Icon lampadaApagada = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code_off.png"); 
    
    
    public AbaCodigoFonte(JTabbedPane painelTabulado) {
        super(painelTabulado);
        initComponents();
        configurarAcoes();
        editor.getPortugolDocumento().addPortugolDocumentoListener(AbaCodigoFonte.this);
        acaoSalvarArquivo.configurar(editor.getPortugolDocumento());
        acaoSalvarArquivo.setEnabled(editor.getPortugolDocumento().isChanged());
        adicionarAbaListener(AbaCodigoFonte.this);
        jPainelSeparador.setDividerLocation(480);
        this.addComponentListener(new AdaptadorComponente());
        painelSaida.getMensagemCompilador().adicionaAbaMensagemCompiladorListener(AbaCodigoFonte.this);
        btnExecutar.setEnabled(true);
    }

    public void setPortugolDocumento(PortugolDocumento portugolDocumento) {
        portugolDocumento.addPortugolDocumentoListener(this);
        editor.setPortugolDocumento(portugolDocumento);
        acaoSalvarArquivo.configurar(portugolDocumento);
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
        btnExecutar = new javax.swing.JButton();
        btnInterromper = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(5, 32767));
        lblParametros = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(5, 32767));
        txtParametros = new javax.swing.JTextField();
        jSeparador3 = new javax.swing.JToolBar.Separator();
        jPainelSeparador = new javax.swing.JSplitPane();
        painelSaida = new br.univali.ps.ui.PainelSaida();
        editor = new br.univali.ps.ui.Editor();

        setLayout(new java.awt.BorderLayout());

        barraFerramenta.setFloatable(false);
        barraFerramenta.setOpaque(false);

        btnSalvar.setAction(acaoSalvarArquivo);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setHideActionText(true);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnSalvar);

        btnDesfazer.setAction(acaoDesfazer);
        btnDesfazer.setFocusPainted(false);
        btnDesfazer.setHideActionText(true);
        barraFerramenta.add(btnDesfazer);

        btnRefazer.setAction(acaoRefazer);
        btnRefazer.setFocusPainted(false);
        btnRefazer.setHideActionText(true);
        btnRefazer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefazer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnRefazer);
        barraFerramenta.add(jSeparador1);

        btnRecortar.setAction(acaoRecortar);
        btnRecortar.setFocusPainted(false);
        btnRecortar.setHideActionText(true);
        btnRecortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRecortar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnRecortar);

        btnCopiar.setAction(acaoCopiar);
        btnCopiar.setFocusPainted(false);
        btnCopiar.setHideActionText(true);
        btnCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnCopiar);

        btnColar.setAction(acaoColar);
        btnColar.setFocusPainted(false);
        btnColar.setHideActionText(true);
        btnColar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnColar);
        barraFerramenta.add(jSeparador2);

        btnExecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_play_blue.png"))); // NOI18N
        btnExecutar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_play.png"))); // NOI18N
        btnExecutar.setEnabled(false);
        btnExecutar.setFocusPainted(false);
        btnExecutar.setHideActionText(true);
        btnExecutar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExecutar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecutarActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnExecutar);

        btnInterromper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_stop_blue.png"))); // NOI18N
        btnInterromper.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_stop.png"))); // NOI18N
        btnInterromper.setEnabled(false);
        btnInterromper.setFocusPainted(false);
        btnInterromper.setHideActionText(true);
        btnInterromper.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInterromper.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInterromper.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInterromperActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnInterromper);
        barraFerramenta.add(filler1);

        lblParametros.setText("Parâmetros:");
        barraFerramenta.add(lblParametros);
        barraFerramenta.add(filler2);

        txtParametros.setPreferredSize(new java.awt.Dimension(200, 28));
        barraFerramenta.add(txtParametros);
        barraFerramenta.add(jSeparador3);

        add(barraFerramenta, java.awt.BorderLayout.PAGE_START);

        jPainelSeparador.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jPainelSeparador.setBottomComponent(painelSaida);
        jPainelSeparador.setLeftComponent(editor);

        add(jPainelSeparador, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecutarActionPerformed
    executar();
}//GEN-LAST:event_btnExecutarActionPerformed

    private void btnInterromperActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInterromperActionPerformed
        if (programa != null) {
            programa.interromper();
        }
}//GEN-LAST:event_btnInterromperActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramenta;
    private javax.swing.JButton btnColar;
    private javax.swing.JButton btnCopiar;
    private javax.swing.JButton btnDesfazer;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnInterromper;
    private javax.swing.JButton btnRecortar;
    private javax.swing.JButton btnRefazer;
    private javax.swing.JButton btnSalvar;
    private br.univali.ps.ui.Editor editor;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JSplitPane jPainelSeparador;
    private javax.swing.JToolBar.Separator jSeparador1;
    private javax.swing.JToolBar.Separator jSeparador2;
    private javax.swing.JToolBar.Separator jSeparador3;
    private javax.swing.JLabel lblParametros;
    private br.univali.ps.ui.PainelSaida painelSaida;
    private javax.swing.JTextField txtParametros;
    // End of variables declaration//GEN-END:variables

    private void configurarAcoes() {
        acaoDesfazer.iniciar();
        acaoRefazer.iniciar();
        acaoRecortar.iniciar();
        acaoCopiar.iniciar();
        acaoColar.iniciar();
    }

    @Override
    public void documentoModificado(boolean status) {
        acaoSalvarArquivo.setEnabled(status);
        programa = null;
        if (status) {
            cabecalho.setForegroung(Color.RED);
            cabecalho.setIcone(lampadaApagada);
        } else {
            cabecalho.setForegroung(Color.BLACK);
            cabecalho.setIcone(lampadaAcesa);
        }
    }

    @Override
    public boolean fechandoAba(Aba aba) 
    {
        if ((programa != null) && programa.isExecutando())
        {
            JOptionPane.showMessageDialog(this, String.format("O programa desta aba (%s) ainda está em execução.\nEncerre o programa antes de fechar a aba.", aba.cabecalho.getTitulo()), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (editor.getPortugolDocumento().isChanged()) 
        {
            int resp = JOptionPane.showConfirmDialog(this, "O documento possui modificações, deseja Salva-las?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);
            
            if (resp == JOptionPane.YES_OPTION) acaoSalvarArquivo.actionPerformed(null);
            else 
            if (resp == JOptionPane.CANCEL_OPTION) return false;
        }        
            
        return true;
    }

    @Override
    public void posicionarCursor(int linha, int coluna) {
        editor.posicionaCursor(linha, coluna);
    }

    @Override
    public void nomeArquivoAlterado(String nome) {
        cabecalho.setTitulo(nome);
    }

    public PortugolDocumento getPortugolDocumento() {
        return editor.getPortugolDocumento();
    }

    private void executar() {
        AbaMensagemCompilador abaMensagem = painelSaida.getMensagemCompilador();
        abaMensagem.limpar();
        
        try {
            String codigo = editor.getPortugolDocumento().getCodigoFonte();
            if (programa == null)
                this.programa = Portugol.compilar(codigo);

            programa.setEntrada(painelSaida.getConsole());
            programa.setSaida(painelSaida.getConsole());

            programa.adicionarObservadorExecucao(this);

            programa.executar(getParametros());

        } catch (ErroCompilacao erroCompilacao) {
            ResultadoAnalise resultadoAnalise = erroCompilacao.getResultadoAnalise();

            if (resultadoAnalise.getNumeroTotalErros() > 0) {
                abaMensagem.atualizar(resultadoAnalise);
                abaMensagem.selecionar();
            }
        }
    }

    @Override
    public void execucaoIniciada(Programa programa) {
        btnExecutar.setEnabled(false);
        btnInterromper.setEnabled(true);
        
        painelSaida.getConsole().selecionar();
        painelSaida.getConsole().limpar();
        painelSaida.getConsole().setExecutandoPrograma(true);
    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
    {
        try
        {
            AbaConsole console = painelSaida.getConsole();
            
            if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.NORMAL) 
                console.escrever("\nPrograma finalizado. Tempo de execução: " + resultadoExecucao.getTempoExecucao() + " milissegundos");
            
            else 
            
            if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.ERRO)
                console.escrever("\nErro: " + resultadoExecucao.getErro().getMensagem());
            
            else 
                
            if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.INTERRUPCAO)
                console.escrever("\nO programa foi interrompido!");
        }
        catch (Exception e)
        {
            // Nada a fazer
        }
        
        btnExecutar.setEnabled(true);
        btnInterromper.setEnabled(false);
        painelSaida.getConsole().setExecutandoPrograma(false);
    }

    private class AdaptadorComponente extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            jPainelSeparador.setDividerLocation(AbaCodigoFonte.this.getHeight() - 300);
        }
        
        @Override
        public void componentShown(ComponentEvent ce) {
            editor.requestFocus();
        }
    }
    
    private String[] getParametros()
    {
        String textoParametros = txtParametros.getText().trim();
        
        if (textoParametros.length() > 0)
            return textoParametros.split(" ");
        
        return null;
    }

    public AcaoCopiar getAcaoCopiar() {
        return acaoCopiar;
    }

    public AcaoDesfazer getAcaoDesfazer() {
        return acaoDesfazer;
    }

    public AcaoColar getAcaoColar() {
        return acaoColar;
    }

    public AcaoRecortar getAcaoRecortar() {
        return acaoRecortar;
    }

    public AcaoRefazer getAcaoRefazer() {
        return acaoRefazer;
    }

    public AcaoSalvarArquivo getAcaoSalvarArquivo() {
        return acaoSalvarArquivo;
    }
    
}
