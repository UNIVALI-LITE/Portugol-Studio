package br.univali.ps.ui.swing.tabs;

public class TabClosingEvent
{

    private boolean canClose = false;

    public void setCanClose(boolean canClose)
    {
        this.canClose = canClose;
    }

    public boolean isCanClose()
    {
        return canClose;
    }
}
