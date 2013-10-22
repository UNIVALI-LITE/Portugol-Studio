package br.univali.ps;

import br.univali.ps.exception.CarregamentoDeExercicioException;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.Aba;
import br.univali.ps.ui.AbaCodigoFonte;
import br.univali.ps.ui.AbaListener;
import br.univali.ps.ui.PainelTabuladoPrincipal;
import br.univali.ps.ui.TelaPrincipal;
import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Elieser
 */
public class TelaPrincipalApplet extends javax.swing.JApplet implements TelaPrincipal
{
    private static final long serialVersionUID = 1L;
    private final PainelTabuladoPrincipal painelTabulado = new PainelTabuladoPrincipal();
    private Exercicio exercicioAtual = null;

    private void criaAbaParaRealizacaoDeExercicio(final int idDoExercicio, String urlDoXmlDoExercicio) throws MalformedURLException, IOException
    {
        if (exercicioAtual == null)
        {
            painelTabulado.addChangeListener(new ChangeListener()
            {
                @Override//quando a aba do novo exercício é criada e selecionada...
                public void stateChanged(ChangeEvent e)
                {
                    if (exercicioAtual == null)
                    {
                        AbaCodigoFonte abaDoCodigo = (AbaCodigoFonte) painelTabulado.getAbaSelecionada();
                        exercicioAtual = new Exercicio(abaDoCodigo);
                        painelTabulado.setTitleAt(painelTabulado.getSelectedIndex(), "Exercício " + idDoExercicio);
                        abaDoCodigo.adicionarAbaListener(new AbaListener()
                        {
                            @Override
                            public boolean fechandoAba(Aba aba)
                            {
                                exercicioAtual = null;
                                return true;
                            }
                        });
                    }
                }
            });

            try
            {
                String conteudoDoXmlDoExercicio = CarregadorDeArquivo.getConteudoDoArquivo(urlDoXmlDoExercicio);
                PortugolStudio.getInstancia().abrirArquivoCodigoFonte(conteudoDoXmlDoExercicio);
            }
            catch (CarregamentoDeExercicioException ex)
            {
                ex.printStackTrace();
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex) ;
            }
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Já existe um exercício aberto! Você pode resolver apenas um exercício de cada vez!");
        }
    }

    private static class CarregadorDeArquivo
    {
        public static String getConteudoDoArquivo(String urlDoArquivo) throws CarregamentoDeExercicioException
        {
            try
            {
                InputStream is = null;
                BufferedOutputStream bos = null;
                String conteudoDoArquivo = "";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try{
                    is = new BufferedInputStream(new URL(urlDoArquivo).openStream());
                    
                    bos = new BufferedOutputStream(baos);
                    int byteLido = -1;
                    while ((byteLido = is.read()) != -1)
                    {
                        bos.write(byteLido);
                    }
                }
                finally{
                    bos.flush();
                    conteudoDoArquivo = new String(baos.toByteArray());
                    is.close();
                    bos.close();
                    baos.close();//por precaução :)
                    return conteudoDoArquivo;
                }
            }
            catch (Exception e)
            {
                throw new CarregamentoDeExercicioException(urlDoArquivo);
            }
        }
    }

    public class Exercicio
    {
        //private final int idDaQuestao;
        private final AbaCodigoFonte abaDoExercicio;

        public String getCodigoFonteDoAluno()
        {
            return abaDoExercicio.getCodigoFonte();
        }

//        public int getIdDaQuestao()
//        {
//            return idDaQuestao;
//        }

        public Exercicio(AbaCodigoFonte abaDoExercicio)
        {
            //this.idDaQuestao = idDaQuestao;
            this.abaDoExercicio = abaDoExercicio;
        }
    }

    /**
     *
     * @return Retorna uma Instância de exercício contendo a Questão que o aluno
     * estava resolvendo, o código que o aluno escreveu, etc
     */
    public Exercicio getExercicioAtual()
    {
        if (exercicioAtual != null)
        {
            return exercicioAtual;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Não existe nenhum exercício aberto no momento!");
        }

        return null;
    }

    private boolean idDoExercicioFoiPassadoComoParametro()
    {
        String idDaQuestao = getParameter("idDaQuestao");
        return idDaQuestao != null && !idDaQuestao.isEmpty();
    }

    private void carregaExercicio()
    {
        String idDoExercicio = getParameter("idDaQuestao");
        AppletUtils.exibeMensagemNaConsoleJava("O applet recebeu o id da questão " + idDoExercicio);
        try
        {
            int id = Integer.parseInt(idDoExercicio);
            //String caminhoBaseDosExercicios = "http://localhost:8080/Alice/exercicios";
            String caminhoBaseDosExercicios = getCodeBase() + getParameter("pathDosExercicios");
            if (caminhoBaseDosExercicios == null)
            {
                throw new IllegalArgumentException("O caminho base dos exercícios não foi passado como parâmetro para o applet!");
            }
            String urlDoExercicio = AppletUtils.getCaminhoDoArquivoDoExercicio(caminhoBaseDosExercicios, id);
            criaAbaParaRealizacaoDeExercicio(id, urlDoExercicio);
        }
        catch (NumberFormatException ex)
        {
            String mensagemDaExcecao = "Não foi possível obter o número da questão (" + ex.getMessage() + ")";
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new Exception(mensagemDaExcecao, ex));
        }
        catch (Exception e)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(e);
        }
    }

    @Override
    public void init()
    {
        initComponents();
        PortugolStudio portugol = PortugolStudio.getInstancia();
        portugol.iniciar(null, TelaPrincipalApplet.this);
        setLayout(new BorderLayout());
        add(portugol.getTelaPrincipal().getPainelTabulado(), BorderLayout.CENTER);
        if (idDoExercicioFoiPassadoComoParametro())
        {
            carregaExercicio();
        }
    }

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public PainelTabuladoPrincipal getPainelTabulado()
    {
        return painelTabulado;
    }

    @Override
    public void setArquivosIniciais(List<File> arquivos)
    {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    static
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            //ex.printStackTrace();
        }
    }
}

