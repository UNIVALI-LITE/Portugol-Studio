/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.acoes;

import br.univali.ps.controller.PortugolControladorTelaPrincipal;
import java.awt.Container;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

/**
 *
 * @author Fillipi Pelz
 */
public class AcaoAbrirArquivo extends Acao
{
    
    private JFileChooser chooser;
    private Container parent;
    private PortugolControladorTelaPrincipal controle;
  

    public AcaoAbrirArquivo()
    {
        super("Arquivo aberto com sucesso!");
    }

    public void configurar(PortugolControladorTelaPrincipal controle, Container parent, JFileChooser chooser)
    {
        this.controle = controle;
        this.parent = parent;
        this.chooser = chooser;
    }
   

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            controle.abrir(chooser.getSelectedFiles());
        }
        else
        {
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }       
}
