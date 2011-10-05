package br.univali.ps.ui;

import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.Entrada;
import br.univali.portugol.nucleo.execucao.Saida;
import br.univali.ps.ui.util.IconFactory;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AbaConsole extends Aba implements Saida, Entrada {

    private StringBuffer stringBuffer;
    private TipoDado tipoDado;
    private boolean executandoPrograma = false;

    public AbaConsole(JTabbedPane painelTabulado) {
        super(painelTabulado);
        cabecalho.setBotaoFecharVisivel(false);
        cabecalho.setTitulo("Console");
        cabecalho.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "application_xp_terminal.png"));
        initComponents();
        console.setComponentPopupMenu(menuConsole);
        this.menuConsoleLimpar.setText("Limpar");
        this.menuConsoleCopiar.setText("Copiar");
        console.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent ce) {
                jScrollPane1.getVerticalScrollBar().setValue(ce.getComponent().getHeight());
            }
            
        });
        console.getDocument().addDocumentListener(new DocumentListener() {
        
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarItensMenuConsole();
                if (stringBuffer != null) {
                    String texto = null;

                    try {
                        texto = console.getText(e.getOffset(), e.getLength());
                    } catch (Exception ex) {
                    }

                    if (texto.equals("\n")) {
                        console.setEditable(false);
                    } else {
                        stringBuffer.append(texto);
                    }

                }
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

    /*
     * Por algum motivo o método atualizarItensMenuConsole()
     * buga o limpar() se for chamado durante a execução do programa.
     * 
     * Para prevenir o bug, desativamos a execução deste método
     * enquanto o programa está rodando.
     * 
     * Para que o menu seja atualizado corretamente ao terminar a
     * execução do programa, o método setExecutandoPrograma()
     * chama manualmente o método atualizarItensMenuConsole()
     * 
     * Testei atualizar usando um SwingWorker. Funciona perfeitamente,
     * porém deixa o escreva muito mais lento. Por isso, optei por deixar
     * como está, atualizando somente no final da execução.
     * 
     * Talvez possamos remover o método setExecutando() e tornar o 
     * método atualizarItensMenuConsole() público para acessá-lo nos
     * métodos execucaoIniciada() e execucaoEncerrada()
     * 
     */
    
    public void setExecutandoPrograma(boolean executandoPrograma) 
    {
        this.executandoPrograma = executandoPrograma;
        atualizarItensMenuConsole();
    }

    private void atualizarItensMenuConsole() 
    {
        if (!executandoPrograma)
        {
            if (console.getText() != null)
            {
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
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuConsole = new javax.swing.JPopupMenu();
        menuConsoleLimpar = new javax.swing.JMenuItem();
        menuConsoleCopiar = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();

        menuConsoleLimpar.setText("jMenuItem1");
        menuConsoleLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsoleLimparActionPerformed(evt);
            }
        });
        menuConsole.add(menuConsoleLimpar);

        menuConsoleCopiar.setText("jMenuItem2");
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
        jScrollPane1.setViewportView(console);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void menuConsoleLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsoleLimparActionPerformed
        limpar();
    }//GEN-LAST:event_menuConsoleLimparActionPerformed

    private void menuConsoleCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsoleCopiarActionPerformed
        console.copy();
    }//GEN-LAST:event_menuConsoleCopiarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea console;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu menuConsole;
    private javax.swing.JMenuItem menuConsoleCopiar;
    private javax.swing.JMenuItem menuConsoleLimpar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void limpar() 
    {
        console.setText(null);        
    }
    
    public void escreveConsole(String texto) throws Exception
    {
        ManipuladorSaida manipuladorSaida = new ManipuladorSaida(texto);
        manipuladorSaida.execute();
        manipuladorSaida.get();
    }

    @Override
    public void escrever(String valor) throws Exception
    {
        escreveConsole(valor);
    }

    @Override
    public void escrever(boolean valor)  throws Exception
    {
        escreveConsole((valor) ? "verdadeiro" : "falso");
    }

    @Override
    public void escrever(int valor) throws Exception 
    {
        escreveConsole(String.valueOf(valor));
    }

    @Override
    public void escrever(double valor) throws Exception 
    {
        escreveConsole(String.valueOf(valor));
    }

    @Override
    public void escrever(char valor) throws Exception
    {
        escreveConsole(String.valueOf(valor));
    }

    @Override
    public Object ler(TipoDado tipoDado) throws Exception {
        this.stringBuffer = new StringBuffer();
        
        this.tipoDado = tipoDado;
        console.setEditable(true);
        console.requestFocus();
        console.setCaretPosition(console.getText().length());
        ManipuladorEntrada manipuladorConsole = new ManipuladorEntrada();
        manipuladorConsole.execute();
        manipuladorConsole.get();
        
        return obterValorEntrada(stringBuffer.toString());
    }

    private Object obterValorEntrada(String entrada) {
        try {
            if (tipoDado == TipoDado.INTEIRO) {
                return Integer.parseInt(entrada);
            } else if (tipoDado == TipoDado.REAL) {
                return Double.parseDouble(entrada);
            } else if (tipoDado == TipoDado.CARACTER) {
                return entrada.charAt(0);
            } else if (tipoDado == TipoDado.LOGICO) {
                if (entrada.equals("falso")) {
                    return false;
                } else if (entrada.equals("verdadeiro")) {
                    return true;
                }
            }

            return entrada;
        } catch (Exception e) {
            //TODO interroper;
            return null;
        }
    }
    
    private class ManipuladorEntrada extends SwingWorker {

        @Override
        protected Object doInBackground() throws Exception {
            
            //while (!swingTerminado) {
            //    try {
            //        Thread.sleep(10);
            //    } catch (InterruptedException ex) {
            //       swingTerminado = true;
            //    }
            //}
            return null;
        }        

    }
    
    private class ManipuladorSaida extends SwingWorker {

        String valorSaida;

        public ManipuladorSaida(String valorSaida) {
            this.valorSaida = valorSaida;
        }
        
        @Override
        protected Object doInBackground() throws Exception {
            console.append(valorSaida);
            return true;
        }        

    } 
}
