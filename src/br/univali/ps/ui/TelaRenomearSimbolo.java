package br.univali.ps.ui;

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
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
            
            atualizarInformacoes();

            setVisible(true);
        }
        catch (ErroAoTentarObterDeclaracaoDoSimbolo ex)
        {
            throw new ExcecaoAplicacao(ex.getMensagem(), ExcecaoAplicacao.Tipo.ERRO);
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
    private void initComponents()
    {

        painelCampos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        campoNomeAtual = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        campoNovoNome = new javax.swing.JTextField();
        labelInfo = new javax.swing.JLabel();
        botaoAceitar = new javax.swing.JButton();
        botaoCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        painelCampos.setBorder(javax.swing.BorderFactory.createTitledBorder("Renomear:"));

        jLabel1.setText("Nome atual:");

        campoNomeAtual.setEditable(false);

        jLabel2.setText("Novo nome:");

        labelInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInfo.setText("Você deve informar um novo nome");

        javax.swing.GroupLayout painelCamposLayout = new javax.swing.GroupLayout(painelCampos);
        painelCampos.setLayout(painelCamposLayout);
        painelCamposLayout.setHorizontalGroup(
            painelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCamposLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(campoNomeAtual, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelCamposLayout.createSequentialGroup()
                        .addGroup(painelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(campoNovoNome, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        painelCamposLayout.setVerticalGroup(
            painelCamposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCamposLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoNomeAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoNovoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        botaoAceitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/yes.png"))); // NOI18N
        botaoAceitar.setText("Aceitar");

        botaoCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close.png"))); // NOI18N
        botaoCancelar.setText("Cancelar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(painelCampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 324, Short.MAX_VALUE)
                        .addComponent(botaoCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoAceitar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoAceitar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoAceitar;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JTextField campoNomeAtual;
    private javax.swing.JTextField campoNovoNome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JPanel painelCampos;
    // End of variables declaration//GEN-END:variables
}
