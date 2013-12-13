package br.univali.ps.ui.acoes;

import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.abas.AbaCodigoFonteDoApplet;
import java.awt.event.ActionEvent;

public class AcaoEnviarAlgoritmo extends Acao{

    private int idDaQuestao;
    private int idDoAluno;
    private String codigoDoAluno;
    
    public AcaoEnviarAlgoritmo() {
        super("Algoritmo enviado com sucesso!!");
    }

    @Override
    protected void executar(ActionEvent e) throws Exception {
        System.out.println("enviando para o servidor...");
        //setEnabled(false);
    }

    public void inicializa(int idDaQuestao, int idDoAluno, AbaCodigoFonte abaCodigoFonte) {
        this.idDaQuestao = idDaQuestao;
        this.idDoAluno = idDoAluno;
        this.codigoDoAluno = abaCodigoFonte.getCodigoFonte();
    }
    
}
