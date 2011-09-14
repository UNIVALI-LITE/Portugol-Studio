package br.univali.ps.ui.exemplojtable.exemplo1;

import br.univali.portugol.nucleo.excecoes.Aviso;
import br.univali.portugol.nucleo.excecoes.Erro;
import br.univali.portugol.nucleo.excecoes.ListaMensagens;
import br.univali.portugol.nucleo.excecoes.Mensagem;
import br.univali.ps.ui.util.IconFactory;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Luiz Fernando Noschang
 */

public class ModeloExemplo1 extends AbstractTableModel
{
    private List<Mensagem> mensagens;
    private static final String[] colunas = {"","Descrição","Arquivo","Linha","Coluna","Sugestão"};

    private Icon iconeErro;
    private Icon iconeAviso;
    private Icon iconeSugestao;

    public ModeloExemplo1()
    {
        mensagens = new ArrayList<Mensagem>();
        iconeErro = IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "exclamation.png");
        iconeAviso = IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "error.png");
        iconeSugestao = IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "lightbulb.png");
    }

    @Override
    public int getColumnCount()
    {
        return colunas.length;
    }

    @Override
    public String getColumnName(int indice)
    {
        return colunas[indice];
    }

    @Override
    public int getRowCount()
    {
        return mensagens.size();
    }

    public void limpar()
    {
        mensagens.clear();
        fireTableDataChanged();
    }

    public void adicionar(ListaMensagens listaMensagens)
    {
        for (Mensagem mensagem: listaMensagens)
            mensagens.add(mensagem);

        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int indice)
    {
        if (indice == 0) return Icon.class;

        return super.getColumnClass(indice);
    }



    @Override
    public Object getValueAt(int linha, int coluna)
    {
        final Mensagem mensagem = mensagens.get(linha);

        if (coluna == 0)
        {
            if (mensagem instanceof Erro) return iconeErro;
            if (mensagem instanceof Aviso) return iconeAviso;
        }

        else

        if (coluna == 1) return mensagem.getMensagem();
        else
        if (coluna == 2) return mensagem.getArquivo().getName();
        else
        if (coluna == 3) return mensagem.getLinha();
        else
        if (coluna == 4) return mensagem.getColuna();
        else
        if (coluna == 5) return new JButton(new AbstractAction("Sugestão", iconeSugestao)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Sugestão para a mensagem " + mensagem.getClass().getSimpleName());
            }
        });

        return null;
    }
}
