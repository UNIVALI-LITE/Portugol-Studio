package br.univali.ps.ui;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.Entrada;
import br.univali.portugol.nucleo.execucao.Saida;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import br.univali.ps.controller.PortugolControlador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;

public class PainelSaida extends javax.swing.JPanel implements Saida, Entrada 
{
    private String entradaBuffer = "Digite um valor";
    private PortugolControlador controle;
    
    public PainelSaida()
    {
        initComponents();
        //this.controle = controle;
//        tabelaMensagens.setDefaultRenderer(Mensagem.class, new RenderizadorMensagem());
        tabelaMensagens.addMouseListener(new MouseListener());
        console.setComponentPopupMenu(menuConsole);
        console.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarItensMenuConsole();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarItensMenuConsole();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
       
    }

    private void atualizarItensMenuConsole() {
        if (console.getText().length() > 0) {
            menuConsoleLimpar.setEnabled(true);

            int selecao = console.getSelectionEnd() - console.getSelectionStart();

            if (selecao > 0) {
                menuConsoleCopiar.setEnabled(true);
            } else {
                menuConsoleCopiar.setEnabled(false);
            }
        } else {
            menuConsoleLimpar.setEnabled(false);
            menuConsoleCopiar.setEnabled(false);
        }
    }

    public void setModelSaidaErros(TableModel tableModel)
    {
        tabelaMensagens.setModel(tableModel);
        tableModel.addTableModelListener(tabelaMensagens);
    }

    public void limpar()
    {
        console.setText(null);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuConsole = new javax.swing.JPopupMenu();
        menuConsoleLimpar = new javax.swing.JMenuItem();
        menuConsoleCopiar = new javax.swing.JMenuItem();
        abas = new javax.swing.JTabbedPane();
        jScrollPaneConsole = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        jScrollPaneTabelaMensagens = new javax.swing.JScrollPane();
        tabelaMensagens = new javax.swing.JTable();

        menuConsoleLimpar.setText("Limpar");
        menuConsoleLimpar.setEnabled(false);
        menuConsoleLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsoleLimparActionPerformed(evt);
            }
        });
        menuConsole.add(menuConsoleLimpar);

        menuConsoleCopiar.setText("Copiar");
        menuConsoleCopiar.setEnabled(false);
        menuConsoleCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsoleCopiarActionPerformed(evt);
            }
        });
        menuConsole.add(menuConsoleCopiar);

        setLayout(new java.awt.BorderLayout());

        console.setColumns(20);
        console.setEditable(false);
        console.setRows(5);
        jScrollPaneConsole.setViewportView(console);

        abas.addTab("Console", jScrollPaneConsole);

        tabelaMensagens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneTabelaMensagens.setViewportView(tabelaMensagens);

        abas.addTab("Mensagens", jScrollPaneTabelaMensagens);

        add(abas, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void menuConsoleLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsoleLimparActionPerformed
        limpar();
}//GEN-LAST:event_menuConsoleLimparActionPerformed

    private void menuConsoleCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsoleCopiarActionPerformed
        console.copy();
}//GEN-LAST:event_menuConsoleCopiarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane abas;
    private javax.swing.JTextArea console;
    private javax.swing.JScrollPane jScrollPaneConsole;
    private javax.swing.JScrollPane jScrollPaneTabelaMensagens;
    private javax.swing.JPopupMenu menuConsole;
    private javax.swing.JMenuItem menuConsoleCopiar;
    private javax.swing.JMenuItem menuConsoleLimpar;
    private javax.swing.JTable tabelaMensagens;
    // End of variables declaration                   

    // End of variables declaration//GEN-END:variables
    public void mostrarConsole() {
        abas.setSelectedIndex(0);
    }

    public void mostrarTabelaMensagem() {
        abas.setSelectedIndex(1);
    }

    @Override
    public void escrever(String valor) {
        console.append(valor);
    }

    @Override
    public void escrever(boolean valor) {
        console.append((valor) ? "verdadeiro" : "falso");
    }

    @Override
    public void escrever(int valor) {
        console.append(String.valueOf(valor));
    }

    @Override
    public void escrever(double valor) {
        console.append(String.valueOf(valor));
    }

    @Override
    public void escrever(char valor) {
        console.append(String.valueOf(valor));
    }

    @Override
    public Object ler(TipoDado tipoDado) {
        return null;
    }
    
    private class MouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {

            if (me.getClickCount() >= 2) {

                Mensagem m = (Mensagem) tabelaMensagens.getModel().getValueAt(tabelaMensagens.getSelectedRow(), 0);
                //controle.setCursor(m);
            }
        }
    }

}
