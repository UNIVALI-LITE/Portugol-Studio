/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.ui;

import br.univali.portugol.nucleo.excecoes.Mensagem;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Fillipi Pelz
 */
public class ListMessagesModel extends DefaultTableModel{

    String[] columns = {"","Descrição","Arquivo","Linha","Coluna","Sugestão"};

    List<Mensagem> mensagens;

    public ListMessagesModel()
    {
          mensagens  = new ArrayList<Mensagem>();
    }

    public void addMensagem(Mensagem msg)
    {
        if (!mensagens.contains(msg))
        {
            mensagens.add(msg);
        }
    }

    @Override
    public int getRowCount()
    {
        if (mensagens != null)
            return mensagens.size();
        else
            return 0;
    }

    @Override
    public int getColumnCount()
    {
        return columns.length;
    }

    @Override
      public String getColumnName(int col){
          return columns[col];
    } 

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (columnIndex == 0)
            return mensagens.get(rowIndex).getClass();
        if (columnIndex == 1)
            return mensagens.get(rowIndex).getMessage();
        else if (columnIndex == 3)
            return mensagens.get(rowIndex).getLinha();
        else if (columnIndex == 4)
            return mensagens.get(rowIndex).getColuna();
        else
            return "";
    }

}
