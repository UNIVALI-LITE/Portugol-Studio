package br.univali.ps.ui.swing;

import com.alee.laf.table.WebTableHeaderUI;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

//esta UI customizada é utilizada para desenhar a linha de baixo do header da tabela com uma cor mais suave
public final class PSTableHeaderUI extends WebTableHeaderUI
{
    private static final Color COR_DA_LINHA_DE_BAIXO = new Color(176, 176, 176);

    @Override
    public void paint(Graphics g, JComponent c)
    {
        super.paint(g, c);
        g.setColor(COR_DA_LINHA_DE_BAIXO);
        g.drawLine(0, header.getHeight() - 1, header.getWidth(), header.getHeight() - 1);
    }
}
