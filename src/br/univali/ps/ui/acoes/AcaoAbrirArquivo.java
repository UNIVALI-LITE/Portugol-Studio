package br.univali.ps.ui.acoes;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.ui.AbaCodigoFonte;
import br.univali.ps.ui.util.FileHandle;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

public class AcaoAbrirArquivo extends Acao
{
    
    private JFileChooser chooser;
    private Container parent;
    private JTabbedPane painelTabulado;
  

    public AcaoAbrirArquivo()
    {
        super("Arquivo aberto com sucesso!");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));
    }

    public void configurar(JTabbedPane painelTabulado, Container parent, JFileChooser chooser)
    {
        this.painelTabulado = painelTabulado;
        this.parent = parent;
        this.chooser = chooser;
    }
   

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            File[] arquivos = chooser.getSelectedFiles();
            for (int i = 0; i < arquivos.length; i++) {
                File arquivo = arquivos[i];
                String codigoFonte = FileHandle.open(arquivo);
                PortugolDocumento portugolDocument = new PortugolDocumento();
                portugolDocument.insertString(0, codigoFonte, null);
                AbaCodigoFonte abaCodigoFonte = new AbaCodigoFonte(painelTabulado);
                abaCodigoFonte.setPortugolDocumento(portugolDocument);
                portugolDocument.setChanged(false);
                portugolDocument.setFile(arquivo);
            }
        }
        else
        {
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }       
}
