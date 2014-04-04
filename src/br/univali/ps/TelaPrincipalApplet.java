package br.univali.ps;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.abas.AbaCodigoFonteDoApplet;
import java.awt.BorderLayout;
import javax.swing.JApplet;

/**
 *
 * @author Elieser
 */
public class TelaPrincipalApplet extends JApplet implements TelaPrincipal
{
    private static final long serialVersionUID = 1L;
    private AbaCodigoFonte abaCodigoFonte;

    public TelaPrincipalApplet()
    {

    }

    @Override
    public void exibir()
    {
        initComponents();

        abaCodigoFonte = new AbaCodigoFonteDoApplet(getIdDoAluno(), getIdDaQuestao());

        setLayout(new BorderLayout());
        add(abaCodigoFonte, BorderLayout.CENTER);
        //if (recebeuOsParametros()) {
        carregaExercicio();
        //}
    }

    @Override
    public void bloquear()
    {
        
    }

    @Override
    public void desbloquear()
    {
        
    }

//    private boolean recebeuOsParametros() {
//        String idDaQuestao = getParameter("idDaQuestao");
//        String idDoAluno = getParameter("idDoAluno");
//        return idDaQuestao != null && !idDaQuestao.isEmpty() && idDoAluno != null && !idDoAluno.isEmpty();
//    }
    private void carregaExercicio()
    {
        int idDaQuestao = getIdDaQuestao();
        AppletUtils.exibeMensagemNaConsoleJava("O applet recebeu o id da questão " + idDaQuestao);
        try
        {
            String caminhoBaseDosExercicios = "http://localhost:8084/exercicios";
            //String caminhoBaseDosExercicios = getCodeBase() + getParameter("pathDosExercicios");
            if (caminhoBaseDosExercicios == null)
            {
                throw new IllegalArgumentException("O caminho base dos exercícios não foi passado como parâmetro para o applet!");
            }
            String urlDoExercicio = AppletUtils.getCaminhoDoArquivoDoExercicio(caminhoBaseDosExercicios, idDaQuestao);
            
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

    private int getIdDaQuestao()
    {
        String idDaQuestao = getParameter("idDaQuestao");
        if (idDaQuestao != null)
        {
            return Integer.parseInt(idDaQuestao);
        }
        return 1;
    }

    private int getIdDoAluno()
    {
        String idDoaluno = getParameter("idDoAluno");
        if (idDoaluno != null)
        {
            return Integer.parseInt(idDoaluno);
        }
        return 0;
    }

    @Override
    public void init()
    {
        PortugolStudio.getInstancia().iniciar();
    }

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
}
