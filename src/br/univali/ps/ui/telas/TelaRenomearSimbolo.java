/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.telas;

import br.univali.portugol.nucleo.CausaErroAoTentarObterDeclaracaoDoSimbolo;
import br.univali.portugol.nucleo.ErroAoRenomearSimbolo;
import br.univali.portugol.nucleo.ErroAoTentarObterDeclaracaoDoSimbolo;
import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import static br.univali.ps.ui.editor.Editor.main;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Color;
import java.awt.Cursor;
import static java.awt.SystemColor.info;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Adson Estevesa
 */
public class TelaRenomearSimbolo extends javax.swing.JPanel {

    /**
     * Creates new form TelaRenomearSimbolo
     */
    private static final int ATRASO_ATUALIZACAO_STATUS = 350;

    private String codigoFonte;
    private int linha;
    private int coluna;
    private NoDeclaracao declaracaoDoSimbolo;

    private boolean aceitou = false;
    private Timer timer = null;

    private Action acaoAceitar;
    private Action acaoCancelar;
    
    private TelaCustomBorder dialog;

    public TelaRenomearSimbolo(TelaCustomBorder dialog)
    {
        initComponents();
        configurarCores();
        this.dialog = dialog;

        botaoAceitar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        campoNovoNome.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                dispararTimerAtualizacao();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                dispararTimerAtualizacao();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                dispararTimerAtualizacao();
            }
        });

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                SwingUtilities.invokeLater(()
                        -> 
                        {
                            campoNovoNome.requestFocusInWindow();
                });
            }
        });

        criarAcoes();
    }
    
    private void configurarCores(){
        if(WeblafUtils.weblafEstaInstalado()){
            WeblafUtils.configuraWebLaf(info);
            WeblafUtils.configurarBotao(botaoAceitar, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 4);
            WeblafUtils.configurarBotao(botaoCancelar, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA_TITULO, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 4);
            WeblafUtils.configuraWebLaf(campoNomeAtual, 2, 2);
            WeblafUtils.configuraWebLaf(campoNovoNome, 2, 2);
        }
        setBackground(ColorController.FUNDO_CLARO);
        setForeground(ColorController.COR_LETRA);
        setBackground(ColorController.FUNDO_CLARO);
        setForeground(ColorController.COR_LETRA);
        jLabel1.setForeground(ColorController.COR_LETRA);
        jLabel2.setForeground(ColorController.COR_LETRA);
        info.setBackground(ColorController.FUNDO_CLARO.darker());
        info.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    private void criarAcoes()
    {
        acaoAceitar = new AbstractAction("Aceitar", botaoAceitar.getIcon())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                aceitou = true;
                dialog.dispose();
            }
        };

        botaoAceitar.setAction(acaoAceitar);

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Aceitar");
        getActionMap().put("Aceitar", acaoAceitar);

        acaoCancelar = new AbstractAction("Cancelar", botaoCancelar.getIcon())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (timer != null && timer.isRunning())
                {
                    timer.stop();
                }

                aceitou = false;
                dialog.dispose();
            }
        };

        botaoCancelar.setAction(acaoCancelar);

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancelar");
        getActionMap().put("Cancelar", acaoCancelar);
    }

    private void atualizarInformacoes()
    {
        info.setForeground(Color.BLACK);
        info.setVisible(true);

        if (campoNovoNome.getText().trim().length() > 0)
        {
            try
            {
                boolean gerouErros = false;
                String codigoRenomeado = Portugol.renomearSimbolo(codigoFonte, linha, coluna, campoNovoNome.getText().trim());

                try
                {
                    Portugol.compilarParaAnalise(codigoRenomeado);
                }
                catch (ErroCompilacao erro)
                {
                    info.setForeground(ColorController.VERMELHO);
                    info.setText("O nome que você escolheu irá gerar erros no programa");

                    gerouErros = true;
                }

                if (!gerouErros)
                {
                    info.setVisible(false);
                }

                acaoAceitar.setEnabled(true);
            }
            catch (ErroAoRenomearSimbolo ex)
            {
                info.setForeground(ColorController.VERMELHO);
                info.setText(String.format("Erro ao renomear: %s", ex.getMessage()));
                acaoAceitar.setEnabled(false);
            }
        }
        else
        {
            if (declaracaoDoSimbolo instanceof NoDeclaracaoVariavel)
            {
                info.setText(String.format("Você não informou o novo nome da variável \"%s\"", declaracaoDoSimbolo.getNome()));
            }
            else if (declaracaoDoSimbolo instanceof NoDeclaracaoVetor)
            {
                info.setText(String.format("Você não informou o novo nome do vetor \"%s\"", declaracaoDoSimbolo.getNome()));
            }
            else if (declaracaoDoSimbolo instanceof NoDeclaracaoMatriz)
            {
                info.setText(String.format("Você não informou o novo nome da matriz \"%s\"", declaracaoDoSimbolo.getNome()));
            }
            else if (declaracaoDoSimbolo instanceof NoDeclaracaoFuncao)
            {
                info.setText(String.format("Você não informou o novo nome da função \"%s\"", declaracaoDoSimbolo.getNome()));
            }
            else if (declaracaoDoSimbolo instanceof NoDeclaracaoParametro)
            {
                info.setText(String.format("Você não informou o novo nome do parâmetro \"%s\"", declaracaoDoSimbolo.getNome()));
            }

            info.setForeground(ColorController.VERMELHO);
            acaoAceitar.setEnabled(false);
        }
    }

    private void dispararTimerAtualizacao()
    {
        acaoAceitar.setEnabled(false);

        if (timer == null)
        {
            timer = new Timer(ATRASO_ATUALIZACAO_STATUS, (ActionEvent e)
                    -> 
                    {
                        atualizarInformacoes();
            });

            timer.setRepeats(false);
            timer.setInitialDelay(ATRASO_ATUALIZACAO_STATUS);
            timer.start();
        }
        else
        {
            timer.restart();
        }
    }

    public void exibir(String codigoFonte, int linha, int coluna) throws ExcecaoAplicacao
    {
        try
        {
            declaracaoDoSimbolo = Portugol.obterDeclaracaoDoSimbolo(codigoFonte, linha, coluna);
            this.codigoFonte = codigoFonte;
            this.linha = linha;
            this.coluna = coluna;
            aceitou = false;

            campoNomeAtual.setText(declaracaoDoSimbolo.getNome());
            campoNovoNome.setText("");
            acaoAceitar.setEnabled(false);

            definirTituloJanela();

            atualizarInformacoes();

            dialog.setVisible(true);
        }
        catch (ErroAoTentarObterDeclaracaoDoSimbolo ex)
        {
            if (ex.getCausa() == CausaErroAoTentarObterDeclaracaoDoSimbolo.PROGRAMA_CONTEM_ERROS)
            {
                throw new ExcecaoAplicacao("Não é possível renomear um programa que contém erros. Arrume os erros e tente novamente", ExcecaoAplicacao.Tipo.ERRO);
            }
            else if (ex.getCausa() == CausaErroAoTentarObterDeclaracaoDoSimbolo.SIMBOLO_NAO_ENCONTRADO)
            {
                throw new ExcecaoAplicacao("Não é possível renomear pois o cursor do teclado não está posicionado sobre o nome de uma variável, vetor, matriz ou função", ExcecaoAplicacao.Tipo.ERRO);
            }
            else
            {
                throw new ExcecaoAplicacao(ex, ExcecaoAplicacao.Tipo.ERRO);
            }
        }
    }

    private void definirTituloJanela()
    {
        if (declaracaoDoSimbolo instanceof NoDeclaracaoVariavel)
        {
            if (declaracaoDoSimbolo.constante())
            {
                dialog.setTitle("Renomear constante");
            }
            else
            {
                dialog.setTitle("Renomear variável");
            }
        }
        else if (declaracaoDoSimbolo instanceof NoDeclaracaoVetor)
        {
            dialog.setTitle("Renomear vetor");
        }
        else if (declaracaoDoSimbolo instanceof NoDeclaracaoMatriz)
        {
            dialog.setTitle("Renomear matriz");
        }
        else if (declaracaoDoSimbolo instanceof NoDeclaracaoFuncao)
        {
            dialog.setTitle("Renomear função");
        }
        else if (declaracaoDoSimbolo instanceof NoDeclaracaoParametro)
        {
            NoDeclaracaoParametro parametro = (NoDeclaracaoParametro) declaracaoDoSimbolo;

            if (parametro.getQuantificador() == Quantificador.VALOR)
            {
                if (parametro.constante())
                {
                    dialog.setTitle("Renomear constante");
                }
                else
                {
                    dialog.setTitle("Renomear variável");
                }
            }
            else if (parametro.getQuantificador() == Quantificador.VETOR)
            {
                dialog.setTitle("Renomear vetor");
            }
            else if (parametro.getQuantificador() == Quantificador.MATRIZ)
            {
                dialog.setTitle("Renomear matriz");
            }
        }
    }

    public boolean usuarioAceitouRenomear()
    {
        return aceitou;
    }

    public String getNovoNome()
    {
        return campoNovoNome.getText().trim();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        variaveis = new javax.swing.JPanel();
        aviso = new javax.swing.JPanel();
        info = new javax.swing.JTextArea();
        nomes = new javax.swing.JPanel();
        novoNome = new javax.swing.JPanel();
        campoNovoNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nomeAtual = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        campoNomeAtual = new javax.swing.JTextField();
        botoes = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        botaoCancelar = new com.alee.laf.button.WebButton();
        botaoAceitar = new com.alee.laf.button.WebButton();

        setMinimumSize(new java.awt.Dimension(160, 160));
        setPreferredSize(new java.awt.Dimension(350, 175));
        setLayout(new java.awt.BorderLayout());

        variaveis.setOpaque(false);
        variaveis.setLayout(new java.awt.BorderLayout());

        aviso.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        aviso.setOpaque(false);
        aviso.setLayout(new java.awt.BorderLayout());

        info.setEditable(false);
        info.setColumns(20);
        info.setLineWrap(true);
        info.setRows(2);
        info.setText("Você deve informar um novo nome");
        info.setWrapStyleWord(true);
        info.setBorder(null);
        info.setPreferredSize(new java.awt.Dimension(140, 50));
        aviso.add(info, java.awt.BorderLayout.CENTER);

        variaveis.add(aviso, java.awt.BorderLayout.CENTER);

        nomes.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        nomes.setOpaque(false);
        nomes.setLayout(new java.awt.BorderLayout());

        novoNome.setOpaque(false);
        novoNome.setLayout(new java.awt.BorderLayout());
        novoNome.add(campoNovoNome, java.awt.BorderLayout.CENTER);

        jLabel2.setText("Novo nome:");
        novoNome.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        nomes.add(novoNome, java.awt.BorderLayout.SOUTH);

        nomeAtual.setOpaque(false);
        nomeAtual.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Nome atual:");
        nomeAtual.add(jLabel1, java.awt.BorderLayout.NORTH);

        campoNomeAtual.setEditable(false);
        nomeAtual.add(campoNomeAtual, java.awt.BorderLayout.PAGE_END);

        nomes.add(nomeAtual, java.awt.BorderLayout.NORTH);

        variaveis.add(nomes, java.awt.BorderLayout.NORTH);

        add(variaveis, java.awt.BorderLayout.CENTER);

        botoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        botoes.setOpaque(false);
        botoes.setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        botaoCancelar.setText("Cancelar");
        jPanel1.add(botaoCancelar);

        botaoAceitar.setText("Aceitar");
        botaoAceitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAceitarActionPerformed(evt);
            }
        });
        jPanel1.add(botaoAceitar);

        botoes.add(jPanel1, java.awt.BorderLayout.EAST);

        add(botoes, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAceitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAceitarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoAceitarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aviso;
    private com.alee.laf.button.WebButton botaoAceitar;
    private com.alee.laf.button.WebButton botaoCancelar;
    private javax.swing.JPanel botoes;
    private javax.swing.JTextField campoNomeAtual;
    private javax.swing.JTextField campoNovoNome;
    private javax.swing.JTextArea info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel nomeAtual;
    private javax.swing.JPanel nomes;
    private javax.swing.JPanel novoNome;
    private javax.swing.JPanel variaveis;
    // End of variables declaration//GEN-END:variables
}
