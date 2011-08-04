package br.univali.ps.ui.exemplojtable.exemplo2;

import br.univali.portugol.nucleo.excecoes.ListaMensagens;
import br.univali.portugol.nucleo.excecoes.Mensagem;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Luiz Fernando Noschang
 */

public class ModeloExemplo2 extends AbstractTableModel
{
    private List<Mensagem> mensagens;
    private static final String[] colunas = {"","Descrição","Arquivo","Linha","Coluna","Sugestão"};



    public ModeloExemplo2()
    {
        mensagens = new ArrayList<Mensagem>();
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
        return Mensagem.class;
    }


    @Override
    public Object getValueAt(int linha, int coluna)
    {
        return mensagens.get(linha);
    }
}
