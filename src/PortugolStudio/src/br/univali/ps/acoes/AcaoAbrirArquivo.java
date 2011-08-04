/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.acoes;

import br.univali.ps.ui.util.FileHandle;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Fillipi Pelz
 */
public class AcaoAbrirArquivo extends Acao
{
    
    private JFileChooser chooser;
    private Container parent;

    private String textoArquivo;
    private String tituloArquivo;
    private File arquivo;

    public AcaoAbrirArquivo()
    {
        super("Arquivo aberto com sucesso!");
    }

    public void configurar(Container parent, JFileChooser chooser)
    {
        this.parent = parent;
        this.chooser = chooser;
    }

    public String getTextoArquivo()
    {
        return textoArquivo;
    }

    public String getTituloArquivo()
    {
        return tituloArquivo;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            arquivo = chooser.getSelectedFile();
            textoArquivo = FileHandle.open(arquivo);
            tituloArquivo = arquivo.getName();
        }
        else
        {
            arquivo = null;
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }

    public File getFile()
    {
        return arquivo;
    }

    
}
