package br.univali.ps.ui;

import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.abas.AbaAjuda;
import br.univali.ps.ui.abas.AbaDocumentacaoBiblioteca;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.weblaf.PSWebTabbedPaneUI;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.tabbedpane.WebTabbedPaneUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.TabbedPaneUI;

public final class PainelTabuladoPrincipal extends PainelTabulado {

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

    public PainelTabuladoPrincipal() {
        initComponents();
        abaAjuda = new AbaAjuda();
        setBackground(WeblafUtils.BACKGROUND);
    }

    private class UIPainelTabuladoPrincipal extends PSWebTabbedPaneUI {

        //cores das linhas que são desenhadas embaixo das tabs
        private final Color COR_DA_BORDA_DE_BAIXO_DA_ABA_PRINCIPAL = new Color(214, 214, 214);
        private final Color COR_DA_BORDA_DE_BAIXO_DAS_ABAS = new Color(187, 187, 187);
        
        public UIPainelTabuladoPrincipal() {
            setTabBorderColor(WeblafUtils.COR_DA_BORDA_ORIGINAL_NO_WEBLAF);
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {

            final Insets insets = tabPane.getInsets();
            insets.top += 1;
            if (tabPlacement == JTabbedPane.TOP || tabPlacement == JTabbedPane.BOTTOM) {
                insets.right += 1;
            } else {
                insets.bottom += 1;
            }

            int altura = getTabAreaLength(tabPlacement);
            
            
            if (getTabCount() > 1) {//se existe mais de uma aba
                g.setColor(COR_DA_BORDA_DE_BAIXO_DAS_ABAS);
                int larguraDasTabsDaEsquerda = getLarguraDasTabs(0, getSelectedIndex() - 1);
                int larguraDaTabSelecionada = getTabBounds(tabPane, getSelectedIndex()).width;
                //int larguraDasTabsDaDireita = getLarguraDasTabs(getSelectedIndex(), getTabCount()-1);
                int largutaTotalDasTabs = getLarguraDasTabs(0, getTabCount() - 1);
                
                //desenha linha a esquerda da tab selecionada
                g.drawLine(insets.left, insets.top + altura, larguraDasTabsDaEsquerda, insets.top + altura);

                //desenha linha a direita da tab selecionada
                g.drawLine(larguraDasTabsDaEsquerda + larguraDaTabSelecionada + 2, insets.top + altura, largutaTotalDasTabs, insets.top + altura);
                
                //desenha linha na parte onde não existem tabs, esta linha vai desaparecendo
                
                int larguraDaLinhaTransparente = 250;
                Color cores[] = {COR_DA_BORDA_DE_BAIXO_DAS_ABAS, getBackground()};
                float distribuicaoDasCores[] = {0.1f, 0.5f};
                ((Graphics2D)g).setPaint(new LinearGradientPaint(largutaTotalDasTabs, 0f, largutaTotalDasTabs + larguraDaLinhaTransparente, 2f, distribuicaoDasCores, cores));
                //g.setColor(new Color(0, 0, 0, 0.06f));
                g.drawLine(largutaTotalDasTabs, insets.top + altura, largutaTotalDasTabs + larguraDaLinhaTransparente, insets.top + altura);
                
            } else {//existe apenas a aba principal
                g.setColor(COR_DA_BORDA_DE_BAIXO_DA_ABA_PRINCIPAL);
                g.drawLine(0, insets.top + altura + 1, getWidth() - 1, insets.top + altura + 1);
            }

        }

        private int getLarguraDasTabs(int indiceInicial, int indiceFinal) {
            int larguraDasTabs = 0;
            for (int i = indiceInicial; i <= indiceFinal; i++) {
                larguraDasTabs += getTabBounds(tabPane, i).width;
            }
            return larguraDasTabs;
        }
    }

    @Override
    protected TabbedPaneUI criaUi() {
        return new WebTabbedPaneUI();// new UIPainelTabuladoPrincipal();
    }

    public void setAbaInicial(AbaInicial abaInicial) {
        this.abaInicial = abaInicial;
        add(abaInicial);
        //setTabComponentAt(indexOfComponent(abaInicial), abaInicial.getCabecalho());
        setSelectedComponent(abaInicial);

        //abaInicial.adicionar(PainelTabuladoPrincipal.this);
        //abaInicial.inicializar();
        configurarAcoes();
    }

    public AbaInicial getAbaInicial() {
        if (abaInicial == null) {
            throw new IllegalStateException("A abaInicial precisa ser setada no PainelTabuladoPrincipal!");
        }
        return abaInicial;
    }

    public AbaAjuda getAbaAjuda() {
        return abaAjuda;
    }

    private void configurarAcoes() {
        configurarAcaoSelecionarAbaEsquerda();
        configurarAcaoSelecionarAbaDireita();

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
                PainelTabuladoPrincipal.this.selecionarAbaAnterior();
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
                PainelTabuladoPrincipal.this.selecionarProximaAba();
            }
        };

        this.getActionMap().put(nome, acaoSelecionarAbaDireita);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoFecharAbaAtual() {
        KeyStroke atalho = KeyStroke.getKeyStroke("control Q");
        String nome = "Fechar aba atual";

        acaoFecharAbaAtual = new AbstractAction(nome) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Aba aba = PainelTabuladoPrincipal.this.getAbaSelecionada();

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
                        PainelTabuladoPrincipal.this.fecharTodasAbas(classeAba);
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
            this.add(abaAjuda);
        }

        abaAjuda.selecionar();
    }

    private void exibirAbaDocumentacao() {
        if (abaDocumentacao == null) {
            abaDocumentacao = new AbaDocumentacaoBiblioteca();
            this.add(abaDocumentacao);
        } else if (!this.temAbaAberta(AbaDocumentacaoBiblioteca.class)) {
            //abaDocumentacao.adicionar(this);
            this.add(abaDocumentacao);
        }

        abaDocumentacao.selecionar();
    }

    public static void main(final String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                WeblafUtils.instalaWeblaf();

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLayout(new BorderLayout());

                PainelTabuladoPrincipal painelTabuladoPrincipal = new PainelTabuladoPrincipal();
                painelTabuladoPrincipal.add(AbaCodigoFonte.novaAba());
                painelTabuladoPrincipal.add(AbaCodigoFonte.novaAba());
                painelTabuladoPrincipal.add(AbaCodigoFonte.novaAba());
                painelTabuladoPrincipal.setSelectedIndex(1);

                frame.add(painelTabuladoPrincipal, BorderLayout.CENTER);
                frame.setVisible(true);

            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setFocusable(false);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
