/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.ui.acoes;

import br.univali.ps.controller.PortugolControlador;
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
    PortugolControlador controlador;

    public AcaoSalvarComo()
    {
        super("arquivo salvo com sucesso");
    }

    public void setup(PortugolControlador controlador, Container parent, JFileChooser chooser)
    {
        this.parent = parent;
        this.chooser = chooser;
        this.controlador = controlador;
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
            controlador.salvarComo(arquivo);
        }
        else
        {
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }


}
