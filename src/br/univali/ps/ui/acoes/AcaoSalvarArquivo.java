package br.univali.ps.ui.acoes;

import br.univali.ps.controller.PortugolControladorTelaPrincipal;
import br.univali.ps.dominio.PortugolDocumento;
import java.awt.event.ActionEvent;

public class AcaoSalvarArquivo extends Acao
{

    private PortugolControladorTelaPrincipal controlador;
    private PortugolDocumento documento;
    
    public AcaoSalvarArquivo()
    {
        super("Arquivo salvo com sucesso");
    }

    public void configurar(PortugolControladorTelaPrincipal controlador, PortugolDocumento documento){
        this.controlador = controlador;
        this.documento = documento;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {       
        controlador.salvar(documento);
    }
}
