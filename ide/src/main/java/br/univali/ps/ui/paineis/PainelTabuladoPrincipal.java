package br.univali.ps.ui.paineis;

import br.univali.ps.ui.paineis.utils.PainelTabulado;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.abas.abaAjuda.AbaAjuda;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.abas.CabecalhoAdicionarAba;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public final class PainelTabuladoPrincipal extends PainelTabulado implements Themeable{

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

    private AbaInicial abaInicial;

    public PainelTabuladoPrincipal() {
        initComponents();
        abaAjuda = new AbaAjuda();
        configurarCores();      
        getCabecalhosAba().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                // Get x,y and store them
                PortugolStudio.getInstancia().getTelaPrincipal().pX = me.getX();
                PortugolStudio.getInstancia().getTelaPrincipal().pY = me.getY();

            }

            @Override
             public void mouseDragged(MouseEvent me) {

                 SwingUtilities.invokeLater(() -> {
                     if(!Lancador.getInstance().isMaximazed()){
                        JFrame frame = Lancador.getInstance().getJFrame();
                        frame.setLocation(frame.getLocation().x + me.getX() - PortugolStudio.getInstancia().getTelaPrincipal().pX,frame.getLocation().y + me.getY() - PortugolStudio.getInstancia().getTelaPrincipal().pY);
                     }
                 });

            }
            @Override
            public void mouseClicked(MouseEvent me) {
                SwingUtilities.invokeLater(() ->{
                    if(me.getClickCount() == 2){
                        if(Lancador.getInstance().isMaximazed()){
                            Lancador.getInstance().maximize(false);
                        }else{
                            Lancador.getInstance().maximize(true);
                        }

                    }
                });
            } 
        });

        getCabecalhosAba().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                SwingUtilities.invokeLater(() -> {
                    if(!Lancador.getInstance().isMaximazed()){
                        JFrame frame = Lancador.getInstance().getJFrame();
                        frame.setLocation(frame.getLocation().x + me.getX() - PortugolStudio.getInstancia().getTelaPrincipal().pX,frame.getLocation().y + me.getY() - PortugolStudio.getInstancia().getTelaPrincipal().pY);
                        if(Lancador.getInstance().getQtdMonitores() == 1) {
                        	Lancador.getInstance().snapToEdge(me);
                        }
                    }
                });

            }
        });
    }
        
    @Override
    public void configurarCores() {
        getEspacador().setBackground(ColorController.FUNDO_ESCURO);
        getEspacador().setForeground(ColorController.COR_LETRA);
        getEspacador().setPreferredSize(new Dimension(130, 30));
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

    public AbaAjuda getAbaAjuda() {
        return abaAjuda;
    }

    private void configurarAcoes() {
        configurarAcaoSelecionarAbaEsquerda();
        configurarAcaoSelecionarAbaDireita();
        configurarAcaoMouseWheel();

        configurarAcaoFecharAbaAtual();
        configurarAcaoFecharTodasAbas();

        configurarAcaoExibirAjuda();

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
        this.addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                PainelTabuladoPrincipal painelTabulado = (PainelTabuladoPrincipal) e.getSource();
                int units = e.getWheelRotation();
                int indexAnterior = painelTabulado.getAbaSelecionadaNumber();
                int indexNovo = indexAnterior + units;
                if(indexAnterior >= 1){
                    if (indexNovo < 1)
                        painelTabulado.setAbaAtual(1);
                    else if (indexNovo >= painelTabulado.getAbaContainer().getComponentCount())
                        painelTabulado.setAbaAtual(painelTabulado.getAbaContainer().getComponentCount() - 1);
                    else
                        painelTabulado.setAbaAtual(indexNovo);
                }
            }
        });
    }
    
    private void configurarAcaoFecharAbaAtual() {
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
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK), nome);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK), nome);
    }

    @Override
    public Aba adicionaAoCabecalho(Aba aba) {
        if(getCabecalhosAba().getComponentCount()>0){
            getCabecalhosAba().remove(getCabecalhosAba().getComponentCount()-1);
        }
        super.adicionaAoCabecalho(aba);
        getCabecalhosAba().add(new CabecalhoAdicionarAba());
        
        return aba;
    }
    
    
    private void configurarAcaoFecharTodasAbas() {
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
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK+InputEvent.SHIFT_MASK), nome);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK+InputEvent.SHIFT_MASK), nome);
    }

    private void configurarAcaoExibirAjuda() {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0);

        acaoExibirAjuda = new AbstractAction(ACAO_EXIBIR_AJUDA) {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirAbaAjuda();
            }
        };

        this.getActionMap().put(ACAO_EXIBIR_AJUDA, acaoExibirAjuda);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, ACAO_EXIBIR_AJUDA);
    }

    @Override
    public Aba mudarParaAba(Aba aba) {
        if (getAbaSelecionada() instanceof AbaCodigoFonte){
            AbaCodigoFonte abaAtual = (AbaCodigoFonte)getAbaSelecionada();
            if(abaAtual.editorEstaExpandido()){
                abaAtual.getMiniBarra().getBotaoRetrair().doClick();
            }
        }
        return super.mudarParaAba(aba); //To change body of generated methods, choose Tools | Templates.
    }
    private void configurarAcaoExibirTelaSobre() {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F12,0);
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
        exibirAbaAjuda();
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
