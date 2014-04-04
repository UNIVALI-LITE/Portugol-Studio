package br.univali.ps.ui.abas;

import br.univali.ps.ui.FabricaDicasInterface;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.java.balloontip.BalloonTip;

public final class AbaCodigoFonteDoApplet extends AbaCodigoFonte
{
    private final int idDoAluno;
    private final int idDaQuestao;

    private Action acaoEnviarAlgoritmo;

    /**
     * Estas abas sempre são utilizados em situações onde um deterinado aluno está resolvendo uma questão/exercício
     *
     * @param idDoAluno   O id do aluno que está fazendo o exercício. Este ID é passado como parâmetro para o Applet.
     * @param idDaQuestao O Id da questão que está sendo resolvida. Esta Id da questão tem que ser enviando para o servidor do Alice.
     */
    public AbaCodigoFonteDoApplet(final int idDoAluno, final int idDaQuestao)
    {
        super();

        this.idDoAluno = idDoAluno;
        this.idDaQuestao = idDaQuestao;

        getBtnSalvar().setVisible(false);
        getBtnSalvarComo().setVisible(false);
        getBtnEnviarAlgoritmo().setVisible(true);
        getBtnFixarBarraFerramentas().setVisible(false);

        getDivisorEditorArvore().setDividerLocation(getDivisorEditorArvore().getMinimumDividerLocation());
    }

    @Override
    protected void configurarAcoes()
    {
        super.configurarAcoes();
        configurarAcaoEnviarAlgoritmo();
    }

    private void configurarAcaoEnviarAlgoritmo()
    {
        final String nome = "Enviar algortimo...";
        //final KeyStroke atalho = KeyStroke.getKeyStroke("");

        acaoEnviarAlgoritmo = new AbstractAction(nome, null)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Enviando para o servidor...");
                System.out.println("Id do Aluno: " + idDoAluno);
                System.out.println("Id da Questão: " + idDaQuestao);
            }
        };

        getBtnEnviarAlgoritmo().setAction(acaoEnviarAlgoritmo);

        getActionMap().put(nome, acaoEnviarAlgoritmo);
        //getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    @Override
    protected void criarDicasInterface()
    {
        super.criarDicasInterface();
        FabricaDicasInterface.criarDicaInterface(getBtnEnviarAlgoritmo(), "Envia seu código ao servidor do Alice para que ele seja corrigido!", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.EAST);
    }

    @Override
    protected void configurarSeletorArquivo()
    {
        //não faz nada para evitar que a classe File seja utilizada no applet, exigindo a assinatura do mesmo.
    }
}
