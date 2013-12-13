package br.univali.ps.ui.abas;

import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.acoes.AcaoEnviarAlgoritmo;
import br.univali.ps.ui.acoes.FabricaAcao;
import net.java.balloontip.BalloonTip;

public class AbaCodigoFonteDoApplet extends AbaCodigoFonte{

    private AcaoEnviarAlgoritmo acaoEnviarAlgoritmo;
    private final int idDoAluno;
    private final int idDaQuestao;

/**
 * Estas abas sempre são utilizados em situações onde um deterinado aluno está resolvendo uma questão/exercício
 * @param idDoAluno O id do aluno que está fazendo o exercício. Este ID é passado como parâmetro para o Applet.
 * @param idDaQuestao  O Id da questão que está sendo resolvida. Esta Id da questão tem que ser enviando para o servidor do Alice.
 */
    public AbaCodigoFonteDoApplet(int idDoAluno, int idDaQuestao) {
        super();
        this.idDoAluno = idDoAluno;
        this.idDaQuestao = idDaQuestao;
        
        getBtnSalvar().setVisible(false);
        getBtnSalvarComo().setVisible(false);
        getBtnEnviarAlgoritmo().setVisible(true);
        getBtnFixarBarraFerramentas().setVisible(false);
        
        getPainelCorretor().setVisible(false);
        getDivisorEditorArvore().setDividerLocation(getDivisorEditorArvore().getMinimumDividerLocation());
    }

    
    @Override
    protected void configurarAcoes() {
        super.configurarAcoes(); 
        acaoEnviarAlgoritmo = (AcaoEnviarAlgoritmo) FabricaAcao.getInstancia().criarAcao(AcaoEnviarAlgoritmo.class);
        acaoEnviarAlgoritmo.inicializa(idDaQuestao, idDoAluno, this);
        getBtnEnviarAlgoritmo().setAction(acaoEnviarAlgoritmo);
        
    }

    @Override
    protected void criarDicasInterface() {
        super.criarDicasInterface(); 
        FabricaDicasInterface.criarDicaInterface(getBtnEnviarAlgoritmo(), "Envia seu código ao servidor do Alice para que ele seja corrigido!", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.EAST);
    }

    @Override
    protected void configurarSeletorArquivo() {
        //não faz nada para evitar que a classe File seja utilizada no applet, exigindo a assinatura do mesmo.
    }

}
