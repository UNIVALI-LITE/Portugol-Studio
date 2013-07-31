/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.ui.acoes;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RecordableTextAction;


/**
 *
 * @author Fillipi Pelz
 */
public class AcaoColar extends Acao implements FocusListener{

    public AcaoColar()
    {
        super("Trecho colado com sucesso!");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
    }

    public void iniciar()
    {
        RecordableTextAction rta = RTextArea.getAction(RTextArea.PASTE_ACTION);
        rta.putValue(Acao.SMALL_ICON, this.getValue(Acao.SMALL_ICON));
        setIconeGrande((Icon) this.getValue(Acao.SMALL_ICON));
    }  

     @Override
    protected void executar(ActionEvent e) throws Exception
    {
        RTextArea.getAction(RTextArea.PASTE_ACTION).actionPerformed(e);
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        this.setEnabled(true);
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        this.setEnabled(false);
    }


}
