/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.paineis;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.abas.abaAjuda.AbaAjuda;
import br.univali.ps.ui.abas.abaBibliotecas.AbaDocumentacaoBiblioteca;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author Adson Esteves
 */
public class NewPainelTabulado extends javax.swing.JPanel implements Themeable{

    /**
     * Creates new form NewPainelTabulado
     */
    
    public static final String ACAO_EXIBIR_AJUDA = "Exibir ajuda";
    public static final String ACAO_EXIBIR_DOCUMENTACAO_BIBLIOTECA = "Documentação das bibliotecas";

    private Action acaoSelecionarAbaDireita;
    private Action acaoSelecionarAbaEsquerda;
    private Action acaoFecharAbaAtual;
    private Action acaoFecharTodasAbas;
    private Action acaoExibirAjuda;
    private Action acaoExibirTelaSobre;
    private Action acaoExibirDocumentacaoBiblioteca;

    private final AbaAjuda abaAjuda;// = new AbaAjuda();
    private AbaDocumentacaoBiblioteca abaDocumentacao;

    private AbaInicial abaInicial;
    
    public NewPainelTabulado() {
        initComponents();
        configurarCores();
        abaAjuda = new AbaAjuda();
    }
    
    public Aba adicionaAba(Aba aba)
    {
        abaContainer.add(aba, aba.getName());
        adicionaAoCabecalho(aba);
        mudarParaAba(aba);
        aba.setPainelTabulado(this);
        aba.invalidate();
        aba.repaint();
        return aba;
    }
    
    private Aba adicionaAoCabecalho(Aba aba)
    {
        cabecalhosAba.add(aba.getCabecalho());        
        return aba;
    }
    
    public Aba mudarParaAba(Aba aba)
    {
        
        CardLayout cl = (CardLayout) abaContainer.getLayout();
        cl.show(abaContainer, aba.getName());
        aba.invalidate();
        aba.repaint();
        return aba;
    }
    
    public void fecharTodasAbas(Class<? extends Aba> classe) {
        Component[] components = abaContainer.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass() == classe) {
                ((Aba) components[i]).fechar();
            }
        }
    }
    
    public void setAbaInicial(AbaInicial abaInicial) {
        this.abaInicial = abaInicial;
        adicionaAba(abaInicial);
        configurarAcoes();
    }
    
    public AbaInicial getAbaInicial() {
        if (abaInicial == null) {
            throw new IllegalStateException("A abaInicial precisa ser setada no PainelTabuladoPrincipal!");
        }
        return abaInicial;
    }
    
    private void configurarAcoes() {
        configurarAcaoSelecionarAbaEsquerda();
        configurarAcaoSelecionarAbaDireita();
        configurarAcaoMouseWheel();

        configurarAcaoFecharAbaAtual();
        configurarAcaoFecharTodasAbas();

        configurarAcaoExibirAjuda();
        configurarAcaoExibirDocumentacaoBiblioteca();
        configurarAcaoExibirTelaSobre();
    }

    private void configurarAcaoSelecionarAbaEsquerda() {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK);
        String nome = "Selecionar aba à esquerda";

        acaoSelecionarAbaEsquerda = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewPainelTabulado.this.selecionarAbaAnterior();
            }
        };

        this.getActionMap().put(nome, acaoSelecionarAbaEsquerda);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoSelecionarAbaDireita() {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK);
        String nome = "Selecionar aba á direita";

        acaoSelecionarAbaDireita = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewPainelTabulado.this.selecionarProximaAba();
            }
        };

        this.getActionMap().put(nome, acaoSelecionarAbaDireita);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void configurarAcaoMouseWheel(){
        this.addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
//                NewPainelTabulado painelTabulado = (NewPainelTabulado) e.getSource();
//                int units = e.getWheelRotation();
//                int indexAnterior = painelTabulado.getSelectedIndex();
//                int indexNovo = indexAnterior + units;
//                if(painelTabulado.getSelectedIndex() >= 1){
//                    if (indexNovo < 1)
//                        painelTabulado.setSelectedIndex(1);
//                    else if (indexNovo >= painelTabulado.getTabCount())
//                        painelTabulado.setSelectedIndex(painelTabulado.getTabCount() - 1);
//                    else
//                        painelTabulado.setSelectedIndex(indexNovo);
//                }
            }
        });
    }
    
    private void configurarAcaoFecharAbaAtual() {
        KeyStroke atalho = KeyStroke.getKeyStroke("control Q");
        String nome = "Fechar aba atual";

        acaoFecharAbaAtual = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Aba aba = NewPainelTabulado.this.getAbaSelecionada();

                if (aba != null && aba.getClass() != AbaInicial.class) {
                    aba.fechar();
                }
            }
        };

        this.getActionMap().put(nome, acaoFecharAbaAtual);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoFecharTodasAbas() {
        KeyStroke atalho = KeyStroke.getKeyStroke("shift control Q");
        String nome = "Fechar todas as abas";

        acaoFecharTodasAbas = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Class<? extends Aba> classeAba : Aba.classesFilhas()) {
                    if (classeAba != AbaInicial.class) {
                        NewPainelTabulado.this.fecharTodasAbas(classeAba);
                    }
                }
            }
        };

        this.getActionMap().put(nome, acaoFecharTodasAbas);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoExibirDocumentacaoBiblioteca() {
        KeyStroke atalho = KeyStroke.getKeyStroke("shift F1");

        acaoExibirDocumentacaoBiblioteca = new AbstractAction(ACAO_EXIBIR_DOCUMENTACAO_BIBLIOTECA) {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirAbaDocumentacao();
            }
        };

        this.getActionMap().put(ACAO_EXIBIR_DOCUMENTACAO_BIBLIOTECA, acaoExibirDocumentacaoBiblioteca);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, ACAO_EXIBIR_DOCUMENTACAO_BIBLIOTECA);
    }

    private void configurarAcaoExibirAjuda() {
        KeyStroke atalho = KeyStroke.getKeyStroke("F1");

        acaoExibirAjuda = new AbstractAction(ACAO_EXIBIR_AJUDA) {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirAbaAjuda();
            }
        };

        this.getActionMap().put(ACAO_EXIBIR_AJUDA, acaoExibirAjuda);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, ACAO_EXIBIR_AJUDA);
    }

    private void configurarAcaoExibirTelaSobre() {
        KeyStroke atalho = KeyStroke.getKeyStroke("F12");
        String nome = "Exibir tela sobre";

        acaoExibirTelaSobre = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                PortugolStudio.getInstancia().getTelaSobre().setVisible(true);
            }
        };

        this.getActionMap().put(nome, acaoExibirTelaSobre);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void exibirAbaAjuda() {
        if (!this.temAbaAberta(AbaAjuda.class)) {
            //abaAjuda.adicionar(this);
            this.adicionaAba(abaAjuda);
        }

        abaAjuda.selecionar();
    }

    private void exibirAbaDocumentacao() {
        if (abaDocumentacao == null) {
            abaDocumentacao = new AbaDocumentacaoBiblioteca();
            this.adicionaAba(abaDocumentacao);
        } else if (!this.temAbaAberta(AbaDocumentacaoBiblioteca.class)) {
            //abaDocumentacao.adicionar(this);
            this.adicionaAba(abaDocumentacao);
        }

        abaDocumentacao.selecionar();
    }
    
    public boolean temAbaAberta(Class<? extends Aba> classe) {
        Component[] components = abaContainer.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i].getClass() == classe) {
                return true;
            }
        }

        return false;
    }
    
    public void selecionarAbaAnterior() {
        Component[] components = abaContainer.getComponents();
        for (int i = 0; i < components.length; i++) {
            if(components[i] instanceof Aba)
            {                
                if (components[i].isVisible()) {
                    ((Aba) components[i]).setVisible(false);
                    ((Aba) components[Math.max(0, i - 1)]).setVisible(true);
                    return;
                }
            }
        }
    }

    public void selecionarProximaAba() {
        Component[] components = abaContainer.getComponents();
        for (int i = 0; i < components.length; i++) {
            if(components[i] instanceof Aba)
            {                
                if (components[i].isVisible()) {
                    ((Aba) components[i]).setVisible(false);
                    ((Aba) components[Math.min(i + 1, components.length - 1)]).setVisible(true);
                    return;
                }
            }
        }
    }
    
    public Aba getAbaSelecionada() {
        for (Component component : abaContainer.getComponents()) {
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
    
    public List<Aba> getAbas(Class<? extends Aba> classe) {
        List<Aba> abas = new ArrayList<>();

        for (Component componente : abaContainer.getComponents()) {
            if (componente.getClass() == classe) {
                abas.add((Aba) componente);
            }
        }
        return abas;
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

    public JPanel getAbaContainer() {
        return abaContainer;
    }

    public JPanel getCabecalhosAba() {
        return cabecalhosAba;
    }  
    
    
    @Override
    public void configurarCores() {
        abaContainer.setBackground(ColorController.FUNDO_MEDIO);
        abaContainer.setForeground(ColorController.COR_LETRA);
        cabecalhosAba.setBackground(ColorController.FUNDO_ESCURO);
        cabecalhosAba.setForeground(ColorController.COR_LETRA);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cabecalhosAba = new javax.swing.JPanel();
        abaContainer = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        cabecalhosAba.setMaximumSize(new java.awt.Dimension(32767, 40));
        cabecalhosAba.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));
        add(cabecalhosAba, java.awt.BorderLayout.NORTH);

        abaContainer.setLayout(new java.awt.CardLayout());
        add(abaContainer, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel abaContainer;
    private javax.swing.JPanel cabecalhosAba;
    // End of variables declaration//GEN-END:variables

    
}
