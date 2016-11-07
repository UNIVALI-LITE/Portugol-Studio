package br.univali.ps.ui.swing.weblaf;

import com.alee.laf.table.WebTableHeaderUI;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

//esta UI customizada é utilizada para desenhar a linha de baixo do header da tabela com uma cor mais suave
public final class PSTableHeaderUI extends WebTableHeaderUI
{

    @Override
    public void paint(Graphics g, JComponent c)
    {
        super.paint(g, c);
    }
}
