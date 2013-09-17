package br.univali.ps.ui;

import br.univali.portugol.corretor.dinamico.Unmarshal;
import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.FileHandle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.Timer;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TelaProgressoAba extends JDialog
{
    private final PainelTabulado painelTabulado;

    public TelaProgressoAba(PainelTabulado painel)
    {
        super();
        setModal(true);
        setLocationRelativeTo(null);
        painelTabulado = painel;
        initComponents();
    }

    public void criarNovoCodigoFonte()
    {
        rotuloStatus.setText("Criando novo arquivo de código fonte, por favor aguarde...");

        barraProgresso.setValue(0);
        barraProgresso.setMaximum(100);

        SwingWorker worker = new CriadorArquivos(painelTabulado);
        worker.execute();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void abrirArquivosCodigoFonte(List<File> arquivos)
    {
        atualizarStatusArquivo(arquivos.size());

        barraProgresso.setValue(0);
        barraProgresso.setMaximum(arquivos.size());

        SwingWorker worker = new CarregadorArquivos(arquivos);
        worker.execute();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atualizarStatusArquivo(int total)
    {
        if (total == 1)
        {
            rotuloStatus.setText("Abrindo o arquivo de código fonte, por favor aguarde...");
        }
        else
        {
            rotuloStatus.setText("Abrindo os arquivos de código fonte, por favor aguarde...");
        }
    }

    private class CriadorArquivos extends SwingWorker<Object, AbaCodigoFonte>
    {
        private PainelTabulado painelTabulado;

        public CriadorArquivos(PainelTabulado painelTabulado)
        {
            this.painelTabulado = painelTabulado;
        }

        @Override
        protected Object doInBackground() throws Exception
        {
            publish(new AbaCodigoFonte());

            return null;
        }

        @Override
        protected void process(List<AbaCodigoFonte> abas)
        {
            AbaCodigoFonte abaCodigoFonte = abas.get(0);

            abaCodigoFonte.adicionar(painelTabulado);
            abaCodigoFonte.selecionar();

            barraProgresso.setValue(100);
        }

        @Override
        protected void done()
        {
            Timer timer = new Timer(750, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    setVisible(false);
                }
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    private class CarregadorArquivos extends SwingWorker<Object, AbaCodigoFonte>
    {
        private List<File> arquivos;
        private int contador = 0;

        public CarregadorArquivos(List<File> arquivos)
        {
            this.arquivos = arquivos;
        }

        @Override
        protected Object doInBackground() throws Exception
        {
            for (int indice = 0; indice < arquivos.size(); indice++)
            {
                File arquivo = arquivos.get(indice);

                try
                {
                    AbaCodigoFonte abaCodigoFonte;

                    if (obterExtensaoArquivo(arquivo).equals("pex") || obterExtensaoArquivo(arquivo).equals("xml"))
                    {
                        Unmarshal u = new Unmarshal();
                        Questao q = u.execute(new FileInputStream(arquivo));

                        abaCodigoFonte = new AbaCodigoFonte();
                        abaCodigoFonte.setQuestao(q);
                    }
                    else
                    {
                        String codigoFonte = FileHandle.open(arquivo);
                        abaCodigoFonte = new AbaCodigoFonte();
                        abaCodigoFonte.setCodigoFonte(codigoFonte, arquivo, true);
                    }

                    publish(abaCodigoFonte);
                }
                catch (Exception ex)
                {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(String.format("Erro ao abrir o arquivo '%d'", arquivo.getPath()), ExcecaoAplicacao.Tipo.ERRO));
                }
            }

            return null;
        }

        @Override
        protected void process(List<AbaCodigoFonte> chunks)
        {
            for (final AbaCodigoFonte aba : chunks)
            {
                contador += 1;

                aba.adicionar(painelTabulado);
                barraProgresso.setValue(contador);
            }
        }

        @Override
        protected void done()
        {
            Timer timer = new Timer(750, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    setVisible(false);
                }
            });

            timer.setRepeats(false);
            timer.start();
        }

        private String obterExtensaoArquivo(File file)
        {
            String fileName = file.getName();
            return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rotuloStatus = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setResizable(false);

        rotuloStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rotuloStatus.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barraProgresso, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rotuloStatus)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(rotuloStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barraProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JLabel rotuloStatus;
    // End of variables declaration//GEN-END:variables
}
