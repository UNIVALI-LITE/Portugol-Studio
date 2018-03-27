package br.univali.ps.ui.paineis;

import br.univali.ps.ui.paineis.utils.PainelTabulado;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.abas.abaAjuda.AbaAjuda;
import br.univali.ps.ui.abas.abaBibliotecas.AbaDocumentacaoBiblioteca;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public final class PainelTabuladoPrincipal extends PainelTabulado{

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
    }
//    @Override
//    protected TabbedPaneUI criaUi() {
//        return new WebTabbedPaneUI();// new UIPainelTabuladoPrincipal();
//    }

    public void setAbaInicial(AbaInicial abaInicial) {
        this.abaInicial = abaInicial;
        adicionaAba(abaInicial);
        //setTabComponentAt(indexOfComponent(abaInicial), abaInicial.getCabecalho());

        //abaInicial.adicionar(PainelTabuladoPrincipal.this);
        //abaInicial.inicializar();
        configuraTrocaAba();
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
    
    private void configuraTrocaAba()
    {
//        addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                if(getSelectedIndex()==indexOfComponent(abaInicial))
//                {
//                    getAbaInicial().getPainelExemplos().atualizarRecentes();
//                }
////                if(getAbaSelecionada() instanceof AbaCodigoFonte)
////                {
////                    AbaCodigoFonte acf = (AbaCodigoFonte) getAbaSelecionada();
////                    acf.getEditor().getSuporteLinguagemPortugol().atualizar(acf.getEditor().getTextArea());
////                    System.out.println("FIRE");
////                }                
//            }
//        });
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
    
    private void configurarAcaoMouseWheel(){
//        this.addMouseWheelListener(new MouseWheelListener(){
//            @Override
//            public void mouseWheelMoved(MouseWheelEvent e) {
//                PainelTabuladoPrincipal painelTabulado = (PainelTabuladoPrincipal) e.getSource();
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
//            }
//        });
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
                painelTabuladoPrincipal.setAbaAtual(1);
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
