package br.univali.ps.ui;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.controller.PortugolControlador;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.acoes.Acao;
import br.univali.ps.ui.acoes.AcaoColar;
import br.univali.ps.ui.swing.aba.Aba;
import br.univali.ps.ui.swing.aba.AbaClosingEvent;
import br.univali.ps.ui.swing.aba.AbaListener;
import java.awt.Component;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;

public class Editor extends javax.swing.JPanel implements AbaListener, ChangeListener {

    PortugolControlador controlador;

    public Editor(PortugolControlador controller) {
        initComponents();
        acumuladorAba.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.controlador = controller;
    }

    public void novaAba(String titulo, RSyntaxDocument documento) {
        Aba tab = new Aba(acumuladorAba, titulo, documento);
        tab.addTabListener(this);
        acumuladorAba.add(tab);
        acumuladorAba.setSelectedIndex(acumuladorAba.indexOfComponent(tab));
        acumuladorAba.addChangeListener(this);
    }

    public void fecharTodasAbas() {
        for (Component componet : acumuladorAba.getComponents()) {
            if (componet instanceof Aba) {
                ((Aba) componet).close();
            }
        }
    }

    private Aba abaSelecionada() throws Exception {
        if (acumuladorAba.getTabCount() > 0) {
            return (Aba) acumuladorAba.getSelectedComponent();
        }
        throw new Exception(new IllegalStateException("Não há aba aberta"));
    }

    public void fecharAbaSelecionada() {
        try {
            abaSelecionada().close();
        } catch (Exception ex) {}
    }

    public Document getDocumentAbaSelecionada() {
        try {
            return abaSelecionada().getDocument();
        } catch (Exception ex) {
            return null;
        }
    }

    public void posicionaCursor(int linha, int coluna) {
        try {
            JTextArea textArea = abaSelecionada().getTextArea();
            textArea.setCaretPosition(0);
            try {
                while (textArea.getLineOfOffset(textArea.getCaretPosition()) < (linha - 1)) {
                    textArea.setCaretPosition(textArea.getCaretPosition() + 1);
                }
                textArea.setCaretPosition(textArea.getCaretPosition() + coluna);
            } catch (BadLocationException ex) {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
        } catch (Exception ex) {}
    }

    public void setTituloAbaSelecionada(String titulo) {
        ((Aba) acumuladorAba.getSelectedComponent()).setTitulo(titulo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        acumuladorAba = new javax.swing.JTabbedPane();

        setLayout(new java.awt.BorderLayout());
        add(acumuladorAba, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane acumuladorAba;
    // End of variables declaration//GEN-END:variables

    @Override
    public void tabClosing(AbaClosingEvent evt) {
        if (((PortugolDocumento)evt.getAba().getDocument()).isChanged()) {
            int resp = JOptionPane.showConfirmDialog(this, "O documento possui modificações, deseja Salva-las?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                controlador.salvar();
                evt.setCanClose(true);
            } else if (resp == JOptionPane.NO_OPTION) {
                evt.setCanClose(true);
            } else {
                evt.setCanClose(false);
            }
        } else {
            evt.setCanClose(true);
        }
    }

    public void selecionarAbaArquivo(File arquivo) {
        for (Component componet : acumuladorAba.getComponents()) {
            if (componet instanceof Aba) {
                PortugolDocumento document = (PortugolDocumento) ((Aba) componet).getDocument();
                if (document.getFile() != null && document.getFile().getPath().equals(arquivo.getPath())) {
                    acumuladorAba.setSelectedComponent(componet);
                    return;
                }
            }
        }
    }

    public void stateChanged(ChangeEvent ce) {
        Aba aba;
        try {
            aba = abaSelecionada();

            if (aba != null) {
                controlador.documentoSelecionado(aba.getDocument());
            }
        } catch (Exception ex) {
            controlador.nenhumDocumentoAberto();
        }
    }

    public void configurarFocusListener(Acao acaoColar) {
        
        try {
            abaSelecionada().getTextArea().addFocusListener((AcaoColar) acaoColar);
        } catch (Exception ex) {
            ex.printStackTrace();
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        }
    }
    
}
