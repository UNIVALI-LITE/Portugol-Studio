package br.univali.ps.ui.acoes;

import br.univali.ps.controller.PortugolControlador;
import java.awt.event.ActionEvent;

public class AcaoSalvarArquivo extends Acao
{

    private PortugolControlador controlador;

    public AcaoSalvarArquivo()
    {
        super("Arquivo salvo com sucesso");
    }

    public void setup(PortugolControlador controlador){
        this.controlador = controlador;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {       
        controlador.salvar();
    }
}
