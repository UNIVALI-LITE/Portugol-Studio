package br.univali.ps.ui.acoes;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;

public abstract class Acao extends AbstractAction{
    
    private String msgSucesso;
    private List<AcaoListener> listeners;
    private Icon IconeGrande;

    Acao(String msgSucesso)
    {
        listeners = new LinkedList<AcaoListener>();
        this.msgSucesso = msgSucesso;
    }

    public void removerIconeGrande(){
        putValue(AbstractAction.LARGE_ICON_KEY, null);
    }
    public void restaurarIconeGrande(){
        putValue(AbstractAction.LARGE_ICON_KEY, IconeGrande);
    }

    protected abstract void executar(ActionEvent e) throws Exception;

    @Override
    public final void actionPerformed(ActionEvent e)
    {
        try
        {
            executar(e);
            dispararAcaoExecutadaSucesso();
        }
        catch (Exception ex)
        {
            dispararAcaoFalhou(ex);
        }
    }

    final void setTitulo(String titulo)
    {
        putValue(AbstractAction.NAME, titulo);
    }

    final void setIconeGrande(Icon icone)
    {
        putValue(AbstractAction.LARGE_ICON_KEY, icone);
        IconeGrande = icone;
    }

    final void setIcone(Icon icone)
    {
        putValue(AbstractAction.SMALL_ICON, icone);
    }


    public final void adicionarListener(AcaoListener listener)
    {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public final void removerListener(AcaoListener listener)
    {
        listeners.remove(listener);
    }

    private void dispararAcaoExecutadaSucesso()
    {
        for (AcaoListener listener: listeners)
            listener.acaoExecutadaSucesso(this, msgSucesso);
    }

    private void dispararAcaoFalhou(Exception causa)
    {
        for (AcaoListener listener: listeners)
            listener.acaoFalhou(this, causa);
    }
}
