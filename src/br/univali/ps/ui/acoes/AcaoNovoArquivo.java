package br.univali.ps.ui.acoes;

import br.univali.ps.controller.PortugolControlador;
import java.awt.event.ActionEvent;

public class AcaoNovoArquivo extends Acao{

    PortugolControlador controlador;

    AcaoNovoArquivo()
    {
        super("Arquivo criado com sucesso");
    }

    public void setup(PortugolControlador controlador)
    {
        this.controlador = controlador;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        controlador.novo();
    }
}
