/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.acoes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Fillipi Pelz
 */
public class AcaoSalvarComo extends Acao{

    Container parent;
    JFileChooser chooser;

    File file = null;

    public AcaoSalvarComo()
    {
        super("arquivo salvo com sucesso");
    }

    public void setup(Container parent, JFileChooser chooser)
    {
        this.parent = parent;
        this.chooser = chooser;
    }

    public File getFile()
    {
        return file;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            file = chooser.getSelectedFile();
        }
        else
        {
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }


}
