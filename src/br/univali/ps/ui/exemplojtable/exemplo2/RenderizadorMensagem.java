package br.univali.ps.ui.exemplojtable.exemplo2;

import br.univali.portugol.nucleo.excecoes.Aviso;
import br.univali.portugol.nucleo.excecoes.Erro;
import br.univali.portugol.nucleo.excecoes.Mensagem;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Luiz Fernando Noschang
 */

public class RenderizadorMensagem extends DefaultTableCellRenderer
{
    private Icon iconeErro;
    private Icon iconeAviso;
    private Icon iconeSugestao;
    private JButton botaoSugestao;

    public RenderizadorMensagem()
    {
        iconeErro = IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "exclamation.png");
        iconeAviso = IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "error.png");
        iconeSugestao = IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "lightbulb.png");

        botaoSugestao = new JButton(new AbstractAction("", iconeSugestao)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Sugestão para a mensagem!");
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable tabela, Object valor, boolean selecionado, boolean temFoco, int linha, int coluna)
    {
        JLabel rotulo = (JLabel) super.getTableCellRendererComponent(tabela, valor, selecionado, temFoco, linha, coluna);
        Mensagem mensagem = (Mensagem) valor;

        if (coluna == 0) renderizarColunaTipo(rotulo, mensagem);
        else
        if (coluna == 1) renderizarColunaMensagem(rotulo, mensagem);
        else
        if (coluna == 2) renderizarColunaArquivo(rotulo, mensagem);
        else
        if (coluna == 3) renderizarColunaLinha(rotulo, mensagem);
        else
        if (coluna == 4) renderizarColunaColuna(rotulo, mensagem);
        else
        if (coluna == 5)
        {
            return botaoSugestao;
        }

        return rotulo;
    }

    private void renderizarColunaTipo(JLabel rotulo, Mensagem mensagem)
    {
        rotulo.setText("");

        if (mensagem instanceof Erro) rotulo.setIcon(iconeErro);
        else
        if (mensagem instanceof Aviso) rotulo.setIcon(iconeAviso);
    }

    private void renderizarColunaMensagem(JLabel rotulo, Mensagem mensagem)
    {
        rotulo.setIcon(null);
        rotulo.setText(mensagem.getMensagem());
    }

    private void renderizarColunaArquivo(JLabel rotulo, Mensagem mensagem)
    {
        rotulo.setIcon(null);
        rotulo.setText(mensagem.getArquivo().getName());
    }

    private void renderizarColunaLinha(JLabel rotulo, Mensagem mensagem)
    {
        rotulo.setIcon(null);
        rotulo.setText(Integer.toString(mensagem.getLinha()));
    }

    private void renderizarColunaColuna(JLabel rotulo, Mensagem mensagem)
    {
        rotulo.setIcon(null);
        rotulo.setText(Integer.toString(mensagem.getColuna()));
    }
}
