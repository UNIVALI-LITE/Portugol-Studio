package br.univali.ps.ui;

import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.portugol.corretor.dinamico.Unmarshal;
import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.FileHandle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.xml.bind.JAXBException;

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

    //@todo este método não deveria estar nessa classe
    public AbaCodigoFonte criarNovoCodigoFonte()
    {
        rotuloStatus.setText("Criando novo arquivo de código fonte, por favor aguarde...");

        barraProgresso.setValue(0);
        barraProgresso.setMaximum(100);
        
        
        
        CriadorArquivos worker = new CriadorArquivos(painelTabulado);
        worker.execute();
        AbaCodigoFonte abaCodigoFonte = null;
        try{
             abaCodigoFonte = (AbaCodigoFonte)worker.get();
            return abaCodigoFonte;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        setLocationRelativeTo(null);
        setVisible(true);
        return abaCodigoFonte;
    }

    //@todo este método também não deveria estar aqui
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

    //@todo muito provavelmente também não deveria estar aqui
    public void abrirCodigoFonte(final String codigoFonte)
    {
        atualizarStatusArquivo(1);

        barraProgresso.setValue(0);
        barraProgresso.setMaximum(barraProgresso.getMaximum());

        //esse trecho de código foi executado em uma PrivilegedAction
        //para corrigir o problema que acontecia quando o Applet tentava
        //usar Reflection para instânciar uma Questao a partir do XML do arquivo pex.
        AccessController.doPrivileged(new PrivilegedAction<Void>()
        {
            @Override
            public Void run()
            {
                SwingWorker worker = new CarregadorArquivos(codigoFonte);
                worker.execute();

                setLocationRelativeTo(null);
                setVisible(true);
                return null;
            }
        });
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
            try{
                AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.criaNovaAba();
                publish(abaCodigoFonte);
                return null;
            }
            catch(Exception ex){
                ex.printStackTrace();
                throw ex;
            }
        }

        @Override
        protected void process(List<AbaCodigoFonte> abas)
        {
            AbaCodigoFonte abaCodigoFonte = abas.get(0);

            //abaCodigoFonte.adicionar(painelTabulado);
            painelTabulado.add(abaCodigoFonte);
            abaCodigoFonte.setPainelTabulado(painelTabulado);
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

    private enum TipoCarregamento
    {
        LISTA_ARQUIVOS, CODIGO_FONTE
    };

    private Questao getQuestaoFromPex(String pexContent) throws JAXBException
    {
        Unmarshal u = new Unmarshal();
        InputStream is = new ByteArrayInputStream(pexContent.getBytes());
        Questao q = u.execute(is);
        return q;
    }

    private class CarregadorArquivos extends SwingWorker<Object, AbaCodigoFonte>
    {
        private TipoCarregamento tipoCarregamento;
        private List<File> arquivos;
        private String arquivoPex;
        private int contador = 0;

        public CarregadorArquivos(List<File> arquivos)
        {
            this.tipoCarregamento = TipoCarregamento.LISTA_ARQUIVOS;
            this.arquivos = arquivos;
        }

        public CarregadorArquivos(String arquivoPex)
        {
            this.tipoCarregamento = TipoCarregamento.CODIGO_FONTE;
            this.arquivoPex = arquivoPex;
        }

        @Override
        protected Object doInBackground() throws Exception
        {
            System.out.println("doInBackground...");
            switch (tipoCarregamento)
            {
                case CODIGO_FONTE:
                {
                    try
                    {
                        Questao q = getQuestaoFromPex(arquivoPex);
                        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.criaNovaAba();
                        abaCodigoFonte.setQuestao(q);
                        abaCodigoFonte.setRemovivel(false);
                        publish(abaCodigoFonte);
                        //return abaCodigoFonte;
                    }
                    catch (final Exception e)
                    {
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                JOptionPane.showMessageDialog(rootPane, e);
                            }
                        });
                    }

                    break;
                }
                case LISTA_ARQUIVOS:
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

                                abaCodigoFonte = AbaCodigoFonte.criaNovaAba();
                                abaCodigoFonte.setQuestao(q);
                            }
                            else
                            {
                                String codigoFonte = FileHandle.open(arquivo);
                                abaCodigoFonte = AbaCodigoFonte.criaNovaAba();
                                abaCodigoFonte.setCodigoFonte(codigoFonte, arquivo, true);
                            }

                            publish(abaCodigoFonte);
                        }
                        catch (Exception ex)
                        {
                            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(String.format("Erro ao abrir o arquivo '%d'", arquivo.getPath()), ExcecaoAplicacao.Tipo.ERRO));
                        }
                    }

                    break;
                }
            }

            return null;
        }

        @Override
        protected void process(List<AbaCodigoFonte> chunks)
        {
            System.out.println("process..");
            for (final AbaCodigoFonte aba : chunks)
            {
                contador += 1;
                painelTabulado.add(aba);
                //aba.adicionar(painelTabulado);
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
