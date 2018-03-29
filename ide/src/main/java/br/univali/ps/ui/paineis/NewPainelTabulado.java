/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.paineis;

import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.abas.BotoesControleAba;
import br.univali.ps.ui.abas.CabecalhoAba;
import br.univali.ps.ui.abas.CabecalhoAdicionarAba;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Adson Esteves
 */
public class NewPainelTabulado extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form NewPainelTabulado
     */
    
    public NewPainelTabulado() {
        initComponents();
        configurarCores();
    }
    
    public Aba adicionaAba(Aba aba)
    {
        abaContainer.add(aba, aba.getName());
        adicionaAoCabecalho(aba);
        mudarParaAba(aba);
        aba.setPainelTabulado(this);
        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
        return aba;
    }
    
    public Aba adicionaAoCabecalho(Aba aba)
    {
        cabecalhosAba.add(aba.getCabecalho());             
        resizeCabecalho();
        CabecalhoAba cabecalhoAba = aba.getCabecalho();
        cabecalhoAba.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                aba.selecionar();
            }
        });
        FabricaDicasInterface.criarTooltipEstatica(cabecalhoAba, cabecalhoAba.getTitulo());
        return aba;
    }
    
    private void resizeCabecalho()
    {
        int newSize = 200;
        if(cabecalhosAba.getComponentCount()>1){
            
            int canvas = cabecalhosAba.getWidth()-espacador.getWidth();            
            if(canvas > 0){
                for (Component component : cabecalhosAba.getComponents()) {
                    if(!(component instanceof BotoesControleAba) && !(component instanceof CabecalhoAdicionarAba)){
                        calculaTamanhoCabecalho((CabecalhoAba) component);
                    }
                }
                int used = 0;
                for (Component component : cabecalhosAba.getComponents()) {
                    used+=component.getPreferredSize().getWidth();
                }
                if(used>=canvas){              
                    newSize = canvas/cabecalhosAba.getComponentCount();
                    newSize -= 2;
                    for (Component component : cabecalhosAba.getComponents()) {
                        if(!(component instanceof BotoesControleAba) && !(component instanceof CabecalhoAdicionarAba)){
                            CabecalhoAba cabecalho = (CabecalhoAba) component;
                            cabecalho.setMaxWidth(newSize);
                            cabecalho.setPreferredSize(new Dimension(newSize, 26));
                        }
                    }
                    
                }
            }            
        }
    }
    
    public void calculaTamanhoCabecalho(CabecalhoAba cabAba)
    {        
        int size = 0;
        cabAba.setBorder(new EmptyBorder(2, 2, 2, 2));
        for (Component component : cabAba.getComponents()) {
            size+=component.getPreferredSize().getWidth();
        }        
        int newWidth = size + 20;        
        cabAba.setPreferredSize(new Dimension(newWidth, 26));
    }
    
    public Aba mudarParaAba(Aba aba)
    {        
        CardLayout cl = (CardLayout) abaContainer.getLayout();
        cl.show(abaContainer, aba.getName());
        trocouAba(aba);
        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
        return aba;
    } 
    
    public boolean contemAba(Aba aba)
    {
        Component[] components = abaContainer.getComponents();
        for (int i = 0; i < components.length; i++) {
            if(components[i] instanceof Aba)
            {                
                if((Aba)components[i] == aba)
                {                    
                    return true;
                }
            }
        }
        return false;
    }
    
    public Aba getAbaSelecionada() {
        for (Component component : getAbaContainer().getComponents()) {
            if(component instanceof Aba)
            {
                if(component.isVisible())
                {
                    return (Aba)component;
                }                
            }            
        }
        return null;
    }
    
    public Integer getAbaSelecionadaNumber() {
        
        Component[] components = abaContainer.getComponents();
        for (int i = 0; i < components.length; i++) {
            if(components[i] instanceof Aba)
            {                
                if(((Aba)components[i]).isVisible())
                {                    
                    return i;
                }
            }
        }
        return null;
    }
    
    public void setAbaAtual(int abaNumber)
    {             
        Aba aba = (Aba)abaContainer.getComponents()[abaNumber];
        mudarParaAba(aba);
    }
    
    public void setAbaAtual(Aba aba)
    {
        mudarParaAba(aba);
    }

    public JPanel getAbaContainer() {
        return abaContainer;
    }

    public JPanel getCabecalhosAba() {
        return cabecalhosAba;
    }
    
    public void trocouAba(Aba aba){
        
    }

    @Override
    public void remove(Component comp) {
        if(comp instanceof Aba)
        {
            this.getAbaContainer().remove((Aba)comp);
            this.getCabecalhosAba().remove(((Aba) comp).getCabecalho());
            this.resizeCabecalho();
        }
        else
        {
            super.remove(comp);
        }
    }

    public JPanel getEspacador() {
        return espacador;
    }
    
    @Override
    public void configurarCores() {
        abaContainer.setBackground(ColorController.COR_CONSOLE);
        abaContainer.setForeground(ColorController.COR_LETRA);
        cabecalhosAba.setBackground(ColorController.COR_PRINCIPAL);
        cabecalhosAba.setForeground(ColorController.COR_LETRA);
        espacador.setBackground(ColorController.COR_PRINCIPAL);
        espacador.setForeground(ColorController.COR_LETRA);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        espacador = new javax.swing.JPanel();
        cabecalhosAba = new javax.swing.JPanel();
        abaContainer = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        espacador.setPreferredSize(new java.awt.Dimension(0, 30));
        jPanel1.add(espacador, java.awt.BorderLayout.EAST);

        cabecalhosAba.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        cabecalhosAba.setMaximumSize(new java.awt.Dimension(32767, 40));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 0);
        flowLayout1.setAlignOnBaseline(true);
        cabecalhosAba.setLayout(flowLayout1);
        jPanel1.add(cabecalhosAba, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        abaContainer.setLayout(new java.awt.CardLayout());
        add(abaContainer, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel abaContainer;
    private javax.swing.JPanel cabecalhosAba;
    private javax.swing.JPanel espacador;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    
}
