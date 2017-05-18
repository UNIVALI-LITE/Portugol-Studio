/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.paineis.PainelLicenca;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.PSOutTabbedPaneUI;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.telas.utils.Licencas;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.utils.WebConnectionUtils;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author Adson Estevesa
 */
public class TelaLicencas extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form TelaLicencas
     */
    private final List<PainelLicenca> paineis = new ArrayList<>();
    private Action acaoSair;
    private boolean carregado = false;
    
    public TelaLicencas(TelaCustomBorder dialog)
    {
        super(null, true);

        initComponents();
        if(WeblafUtils.weblafEstaInstalado()){
            WeblafUtils.configurarBotao(webButton1, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 10);
        }
        if (!Configuracoes.getInstancia().isTemaDark())
        {
            rotuloCarregando.setIcon(new ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Portugol/grande/load.gif")));
        } 
        dialog.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                if (carregado)
                {
                    redefinirPaineis();
                }
                else
                {
                    criarPaineis();
                }
            }
        });

        configurarAcaoSair();
        configurarCores();
    }
    
    @Override
    public void configurarCores()
    {
        rotuloCarregando.setBackground(ColorController.COR_PRINCIPAL);
        painelCarregamento.setBackground(ColorController.COR_PRINCIPAL);
        setBackground(ColorController.FUNDO_CLARO);
        painelAlinhamento.setBackground(ColorController.COR_PRINCIPAL);
        painelTabulado.setUI(new PSOutTabbedPaneUI());
        painelAlinhamento.setForeground(ColorController.COR_LETRA);
        painelTabulado.setForeground(ColorController.COR_LETRA);
        painelBotoes.setForeground(ColorController.COR_LETRA);
    }

    private void configurarAcaoSair()
    {
        String nome = "Sair";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        acaoSair = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        };

        getActionMap().put(nome, acaoSair);
        getInputMap().put(atalho, nome);
    }

    private void redefinirPaineis()
    {
        painelTabulado.setSelectedIndex(0);

        for (PainelLicenca painel : paineis)
        {
            painel.redefinir();
        }
    }

    private void criarPaineis()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Licencas licencas = Licencas.carregar();
                               
                for (final Licencas.Recurso recurso : licencas.getRecursos())
                {
                    if (recurso.getNome().equals("Portugol Studio"))
                    {
                        recurso.setVersao(PortugolStudio.getInstancia().getVersao());
                    }

                    SwingUtilities.invokeLater(() ->
                    {
                        PainelLicenca painelLicenca = new PainelLicenca(recurso);
                        paineis.add(painelLicenca);
                        painelLicenca.setBackground(ColorController.COR_PRINCIPAL);
                        painelLicenca.setForeground(ColorController.COR_LETRA);
                        painelTabulado.add(recurso.getNome(), painelLicenca);
                        painelTabulado.setIconAt(painelTabulado.getTabCount() - 1, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));
                    });
                }
                SwingUtilities.invokeLater(() ->
                {
                    remove(painelCarregamento);
                    add(painelAlinhamento, BorderLayout.CENTER);
                    validate();
                    redefinirPaineis();
                    carregado = true;
                });
            }
        });
        thread.start();
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelAlinhamento = new javax.swing.JPanel();
        painelTabulado = new javax.swing.JTabbedPane();
        painelBotoes = new javax.swing.JPanel();
        webButton1 = new com.alee.laf.button.WebButton();
        painelCarregamento = new javax.swing.JPanel();
        rotuloCarregando = new javax.swing.JLabel();

        painelAlinhamento.setBackground(new java.awt.Color(228, 241, 254));
        painelAlinhamento.setLayout(new java.awt.BorderLayout());

        painelTabulado.setBackground(new java.awt.Color(228, 241, 254));
        painelTabulado.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        painelTabulado.setFocusable(false);
        painelAlinhamento.add(painelTabulado, java.awt.BorderLayout.CENTER);

        painelBotoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.setPreferredSize(new java.awt.Dimension(10, 48));
        painelBotoes.setLayout(new java.awt.BorderLayout());

        webButton1.setText("Visitar Página");
        webButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton1ActionPerformed(evt);
            }
        });
        painelBotoes.add(webButton1, java.awt.BorderLayout.EAST);

        painelAlinhamento.add(painelBotoes, java.awt.BorderLayout.SOUTH);

        setLayout(new java.awt.BorderLayout());

        painelCarregamento.setBackground(new java.awt.Color(255, 255, 255));
        painelCarregamento.setOpaque(false);
        painelCarregamento.setLayout(new java.awt.BorderLayout());

        rotuloCarregando.setBackground(new java.awt.Color(0, 0, 0));
        rotuloCarregando.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        rotuloCarregando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloCarregando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/grande/load.gif"))); // NOI18N
        rotuloCarregando.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rotuloCarregando.setOpaque(true);
        painelCarregamento.add(rotuloCarregando, java.awt.BorderLayout.CENTER);

        add(painelCarregamento, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void webButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton1ActionPerformed
        PainelLicenca painelLicenca = (PainelLicenca) painelTabulado.getSelectedComponent();
        Licencas.Recurso recurso = painelLicenca.getRecurso();
        WebConnectionUtils.abrirSite(recurso.getUrl());
    }//GEN-LAST:event_webButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel painelAlinhamento;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelCarregamento;
    private javax.swing.JTabbedPane painelTabulado;
    private javax.swing.JLabel rotuloCarregando;
    private com.alee.laf.button.WebButton webButton1;
    // End of variables declaration//GEN-END:variables
}
