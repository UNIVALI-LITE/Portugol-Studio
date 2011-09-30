package br.univali.ps.ui.acoes;

import br.univali.ps.ui.AbaCodigoFonte;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

public class AcaoNovoArquivo extends Acao{

    JTabbedPane painelTabulado;

    AcaoNovoArquivo()
    {
        super("Arquivo criado com sucesso");
    }

    public void configurar(JTabbedPane painelTabulado)
    {
        this.painelTabulado = painelTabulado;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        new AbaCodigoFonte(painelTabulado);
    }
}
