package br.univali.ps.ui.acoes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

public class AcaoSalvarComo extends Acao{

    Container parent;
    JFileChooser chooser;
    AcaoSalvarArquivo acaoSalvarArquivo;

    public AcaoSalvarComo()
    {
        super("arquivo salvo com sucesso");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt S"));
    }

    public void configurar(AcaoSalvarArquivo salvarArquivo, Container parent, JFileChooser chooser)
    {
        this.parent = parent;
        this.chooser = chooser;
        this.acaoSalvarArquivo = salvarArquivo;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            File arquivo = chooser.getSelectedFile();
            if (!arquivo.getName().endsWith(".por")){
                arquivo = new File(arquivo.getPath().concat(".por"));
            }
            acaoSalvarArquivo.getDocumento().setFile(arquivo);
            acaoSalvarArquivo.actionPerformed(e);
        }
        else
        {
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }


}
