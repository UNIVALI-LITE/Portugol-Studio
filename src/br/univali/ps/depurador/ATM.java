/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.depurador;

import br.univali.portugol.nucleo.asa.TipoDado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Carlos A. Krueger
 */
public class ATM extends AbstractTableModel
{
    
    HashMap<String,SimbVo> variaveis;

    public ATM()
    {
        variaveis  = new HashMap<String,SimbVo>();
    }

    
    
   //------------------
   //Metodos depurador
   //------------------
    
    
    //Adiciona elemento
    public void addSimbolo(String nome, TipoDado tipo)
    {
        SimbVo variavel = new SimbVo();
        variavel.setNome(nome);
        variavel.setTipo(tipo);
        this.variaveis.put(nome, variavel);  
        fireTableDataChanged();
    }
    
    public void addSimbolo (SimbVo variavel)
    {
        this.variaveis.put(variavel.getNome(), variavel);
        fireTableDataChanged();
    }
    
    
    //Adiciona uma lista de elementos ou uma Hashmap
    public void addLista (List <SimbVo> variaveis)
    {
        for (int i = 0 ; i < variaveis.size() ; i ++){
           this.variaveis.put(variaveis.get(i).getNome(), variaveis.get(i));
        }
        fireTableDataChanged();
    }
    
     public void addLista (HashMap <String,SimbVo> variaveis)
    {
        this.variaveis.putAll(variaveis);
        fireTableDataChanged();
    }
    
     
    //Altera os elementos   
    public void alteraSimbolo (String nome, Object valor)
    {
        this.variaveis.get(nome).setValor(valor);
        fireTableDataChanged();
    }

    public void alteraSimbolo (SimbVo variavel,Object valor)
    {      
        this.variaveis.get(variavel.getNome()).setValor(valor);
        fireTableDataChanged();
    }
    
    //Reseta a tabela atual
    public void resetaTabela ()
    {
        this.variaveis.clear();
        fireTableDataChanged();
    }
    
    
 
    //----------------------------
    //Metodos Abstract Table Model
    //----------------------------
   
    @Override  
    public int getRowCount() 
    {     
        return this.variaveis.size();
    }
    
    @Override
    public int getColumnCount() 
    {       
        return 3;
    }
 
    @Override
    public String getColumnName(int column) 
    {
        switch (column){
            case 0:{
                return "Nome";
            }
            case 1:{
                return "Tipo";
            }
            case 2:{
                return "Valor";
            }
        }
        return " ";
    }
    
    @Override
    public Class getColumnClass(int columnIndex) 
    {       
        switch (columnIndex){
            case 0:{
                return String.class;               
            }
            case 1:{
                return Enum.class;                
            }
            case 2:{
                return Object.class;                
            }
        }
        return String.class;
    }
 
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {       
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        List<SimbVo> values = new ArrayList<SimbVo>(variaveis.values());
        
         switch (columnIndex){
            case 0:{
                return values.get(rowIndex).getNome();              
            }
            case 1:{
                return values.get(rowIndex).getTipo();       
            }
            case 2:{
                return values.get(rowIndex).getValor();                
            }
        }
        return null;
    }
}
