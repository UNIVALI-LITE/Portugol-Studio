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
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.WeblafUtils;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/20/2016
 */
public class TelaRenomearSimbolo extends JDialog
{
    private static final int ATRASO_ATUALIZACAO_STATUS = 350;

    private String codigoFonte;
    private int linha;
    private int coluna;
    private NoDeclaracao declaracaoDoSimbolo;

    private boolean aceitou = false;
    private Timer timer = null;

    private Action acaoAceitar;
    private Action acaoCancelar;

    public TelaRenomearSimbolo()
    {
        initComponents();

        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_GRANDES + "/light-bulb.png")));
        }
        catch (IOException ioe)
        {
        }
        
        if(WeblafUtils.weblafEstaInstalado()){
            WeblafUtils.configurarBotao(botaoAceitar);
            WeblafUtils.configurarBotao(botaoCancelar);
        }

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

    private void criarAcoes()
    {
        acaoAceitar = new AbstractAction("Aceitar", botaoAceitar.getIcon())
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                aceitou = true;
                dispose();
            }
        };

        botaoAceitar.setAction(acaoAceitar);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Aceitar");
        getRootPane().getActionMap().put("Aceitar", acaoAceitar);

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
                dispose();
            }
        };

        botaoCancelar.setAction(acaoCancelar);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancelar");
        getRootPane().getActionMap().put("Cancelar", acaoCancelar);
    }

    private void atualizarInformacoes()
    {
        labelInfo.setForeground(Color.BLACK);
        labelInfo.setVisible(true);

        if (campoNovoNome.getText().trim().length() > 0)
        {
            try
            {
                boolean gerouErros = false;
                String codigoRenomeado = Portugol.renomearSimbolo(codigoFonte, linha, coluna, campoNovoNome.getText().trim());

                try
                {
                    Portugol.compilar(codigoRenomeado);
                }
                catch (ErroCompilacao erro)
                {
                    labelInfo.setForeground(Color.RED);
                    labelInfo.setText("O nome que você escolheu irá gerar erros no programa");

                    gerouErros = true;
                }

                if (!gerouErros)
                {
                    labelInfo.setVisible(false);
                }

                acaoAceitar.setEnabled(true);
            }
            catch (ErroAoRenomearSimbolo ex)
            {
                labelInfo.setForeground(Color.RED);
                labelInfo.setText(String.format("Erro ao renomear: %s", ex.getMessage()));
                acaoAceitar.setEnabled(false);
            }
        }
        else
        {
            if (declaracaoDoSimbolo instanceof NoDeclaracaoVariavel)
            {
                labelInfo.setText(String.format("Você não informou o novo nome da variável \"%s\"", declaracaoDoSimbolo.getNome()));
            }
            else if (declaracaoDoSimbolo instanceof NoDeclaracaoVetor)
            {
                labelInfo.setText(String.format("Você não informou o novo nome do vetor \"%s\"", declaracaoDoSimbolo.getNome()));
            }
            else if (declaracaoDoSimbolo instanceof NoDeclaracaoMatriz)
            {
                labelInfo.setText(String.format("Você não informou o novo nome da matriz \"%s\"", declaracaoDoSimbolo.getNome()));
            }
            else if (declaracaoDoSimbolo instanceof NoDeclaracaoFuncao)
            {
                labelInfo.setText(String.format("Você não informou o novo nome da função \"%s\"", declaracaoDoSimbolo.getNome()));
            }
            else if (declaracaoDoSimbolo instanceof NoDeclaracaoParametro)
            {
                labelInfo.setText(String.format("Você não informou o novo nome do parâmetro \"%s\"", declaracaoDoSimbolo.getNome()));
            }

            labelInfo.setForeground(Color.RED);
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

            setVisible(true);
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
                setTitle("Renomear constante");
            }
            else
            {
                setTitle("Renomear variável");
            }
        }
        else if (declaracaoDoSimbolo instanceof NoDeclaracaoVetor)
        {
            setTitle("Renomear vetor");
        }
        else if (declaracaoDoSimbolo instanceof NoDeclaracaoMatriz)
        {
            setTitle("Renomear matriz");
        }
        else if (declaracaoDoSimbolo instanceof NoDeclaracaoFuncao)
        {
            setTitle("Renomear função");
        }
        else if (declaracaoDoSimbolo instanceof NoDeclaracaoParametro)
        {
            NoDeclaracaoParametro parametro = (NoDeclaracaoParametro) declaracaoDoSimbolo;

            if (parametro.getQuantificador() == Quantificador.VALOR)
            {
                if (parametro.constante())
                {
                    setTitle("Renomear constante");
                }
                else
                {
                    setTitle("Renomear variável");
                }
            }
            else if (parametro.getQuantificador() == Quantificador.VETOR)
            {
                setTitle("Renomear vetor");
            }
            else if (parametro.getQuantificador() == Quantificador.MATRIZ)
            {
                setTitle("Renomear matriz");
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new javax.swing.JPanel();
        variaveis = new javax.swing.JPanel();
        aviso = new javax.swing.JPanel();
        labelInfo = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setPreferredSize(null);
        setResizable(false);

        main.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        main.setPreferredSize(new java.awt.Dimension(350, 175));
        main.setLayout(new java.awt.BorderLayout());

        variaveis.setLayout(new java.awt.BorderLayout());

        aviso.setLayout(new java.awt.BorderLayout());

        labelInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInfo.setText("Você deve informar um novo nome");
        labelInfo.setPreferredSize(new java.awt.Dimension(194, 30));
        aviso.add(labelInfo, java.awt.BorderLayout.CENTER);

        variaveis.add(aviso, java.awt.BorderLayout.CENTER);

        nomes.setLayout(new java.awt.BorderLayout());

        novoNome.setLayout(new java.awt.BorderLayout());
        novoNome.add(campoNovoNome, java.awt.BorderLayout.CENTER);

        jLabel2.setText("Novo nome:");
        novoNome.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        nomes.add(novoNome, java.awt.BorderLayout.SOUTH);

        nomeAtual.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Nome atual:");
        nomeAtual.add(jLabel1, java.awt.BorderLayout.NORTH);

        campoNomeAtual.setEditable(false);
        nomeAtual.add(campoNomeAtual, java.awt.BorderLayout.PAGE_END);

        nomes.add(nomeAtual, java.awt.BorderLayout.NORTH);

        variaveis.add(nomes, java.awt.BorderLayout.NORTH);

        main.add(variaveis, java.awt.BorderLayout.CENTER);

        botoes.setLayout(new java.awt.BorderLayout());

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

        main.add(botoes, java.awt.BorderLayout.SOUTH);

        getContentPane().add(main, java.awt.BorderLayout.CENTER);

        pack();
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JPanel main;
    private javax.swing.JPanel nomeAtual;
    private javax.swing.JPanel nomes;
    private javax.swing.JPanel novoNome;
    private javax.swing.JPanel variaveis;
    // End of variables declaration//GEN-END:variables
}
