package br.univali.ps.ui.swing.aba;

public class AbaClosingEvent
{

    private boolean canClose = false;
    private Aba aba;

    public AbaClosingEvent(Aba aba) {
        this.aba = aba;
    }

    public Aba getAba() {
        return aba;
    }
    
    public void setCanClose(boolean canClose)
    {
        this.canClose = canClose;
    }

    public boolean isCanClose()
    {
        return canClose;
    }
}
