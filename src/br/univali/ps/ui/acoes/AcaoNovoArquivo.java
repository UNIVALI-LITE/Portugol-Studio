package br.univali.ps.ui.acoes;

import br.univali.ps.ui.AbaCodigoFonte;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

public final class AcaoNovoArquivo extends Acao
{
    private JTabbedPane painelTabulado;

    AcaoNovoArquivo()
    {
        super("Arquivo criado com sucesso");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
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
