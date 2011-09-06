package br.univali.ps.ui.swing.aba;

public class AbaClosingEvent
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
