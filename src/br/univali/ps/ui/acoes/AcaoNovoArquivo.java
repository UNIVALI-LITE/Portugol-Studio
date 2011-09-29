package br.univali.ps.ui.acoes;

import br.univali.ps.controller.PortugolControladorTelaPrincipal;
import java.awt.event.ActionEvent;

public class AcaoNovoArquivo extends Acao{

    PortugolControladorTelaPrincipal controlador;

    AcaoNovoArquivo()
    {
        super("Arquivo criado com sucesso");
    }

    public void configurar(PortugolControladorTelaPrincipal controlador)
    {
        this.controlador = controlador;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        controlador.novo();
    }
}
