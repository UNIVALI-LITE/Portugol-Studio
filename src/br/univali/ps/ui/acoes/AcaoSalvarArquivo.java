package br.univali.ps.ui.acoes;

import br.univali.ps.exception.NullFileOnSaveExcpetion;
import br.univali.ps.ui.util.FileHandle;
import java.awt.event.ActionEvent;
import java.io.File;

public class AcaoSalvarArquivo extends Acao
{

    private File file = null;
    private String text;

    public AcaoSalvarArquivo()
    {
        super("Arquivo salvo com sucesso");
    }

    public void setup(File file, String text)
    {
        this.file = file;
        this.text = text;
    }

    public File getFile()
    {
        return file;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        try {
            FileHandle.save(text, file);
        }
        catch(NullPointerException ex)
        {
            throw new NullFileOnSaveExcpetion();
        }
    }
}
