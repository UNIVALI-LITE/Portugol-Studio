package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.asa.VisitanteNulo;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoInicializavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoInspecionavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.ProcuradorDeDeclaracao;
import br.univali.ps.ui.swing.ColorController;
import com.alee.laf.WebLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.border.Border;

/**
 * @author elieser
 */
public class InspetorDeSimbolos extends JList<ItemDaLista> implements ObservadorExecucao {

    private static final Logger LOGGER = Logger.getLogger(InspetorDeSimbolos.class.getName());
    private static final String INSTRUCAO = "Arraste uma variável para este \npainél se quiser inspecioná-la";
    private final DefaultListModel<ItemDaLista> model = new DefaultListModel<>();

    protected static ItemDaLista ultimoItemModificado = null;

    boolean programaExecutando = false;

    private final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(10, 0, 10, 0);

    private JTextArea textArea;//necessário para tratar a importação de variáveis para o inspetor de símbolos diretamente do código fonte
    
    private Programa programa;//referência para o programa compilado, 
    //utilizada para procurar variáveis no programa quando o usuário arrasta uma variável
    //do código fonte para o inspetor de símbolos

    private final Timer timerAtualizacao;
    private static final int TEMPO_ATUALIZACAO = 250;
    private int indexHovered = -1;
    
    public InspetorDeSimbolos() {
        model.clear();
        setModel(model);
        setDropMode(DropMode.ON);
        setTransferHandler(new TratadorDeArrastamento());
        setCellRenderer(new RenderizadorDaLista());
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        instalaObservadores();
        timerAtualizacao = new Timer(TEMPO_ATUALIZACAO, (ev) -> { // atualiza inspetor a cada TEMPO_ATUALIZACAO ms quando o programa está executando
            atualizaValoresVariaveisInspecionadas();
        });
        
        timerAtualizacao.setRepeats(true);
    }

    public void reseta() 
    {
        model.clear();
        resetaDestaqueDosSimbolos();
    }
    
    public void setPrograma(Programa programa)
    {
        /***
            Sempre que o código fonte é alterado a AbaCodigoFonte seta o 'programa' no inspetor de símbolos.
            Toda a árvore sintática é recriada e é necessário verificar se os símbolos que 
            estão no inspetor ainda existem no código. Também é possível que os símbolos 
            tenham sido renomeados, ou que o tipo deles tenha mudado.
        */ 
    
        if (this.programa == programa)
        {
            return;
        }
        
        this.programa = programa;
        
        if (!model.isEmpty()) //só resconstrói a lista de símbolos se existem símbolos sendo inspecionados
        {
            SwingUtilities.invokeLater(new TarefaReconstrucaoNosInspecionados());
        }
    }
    
    private void instalaObservadores() {
        addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(FocusEvent fe) {
                clearSelection();
            }

        });
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) 
            {
                if (ke.getKeyCode() == KeyEvent.VK_DELETE) 
                {
                    int indices[] = getSelectedIndices();
                    int modelSize = model.getSize();
                    for (int i = indices.length - 1; i >= 0; i--) 
                    {
                        int indice = indices[i];
                        if (indice >= 0 && indice < modelSize) 
                        {
                            model.remove(indice);
                        }
                    }
                }
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point p = new Point(e.getX(),e.getY());
                int index = locationToIndex(p);
                if(indexHovered != index)
                {
                    indexHovered = locationToIndex(p);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public List<NoDeclaracao> getNosInspecionados() {
        List<NoDeclaracao> nosInpecionados = new ArrayList<>();
        for (int i = 0; i < model.getSize(); i++) {
            nosInpecionados.add(model.get(i).getNoDeclaracao());
        }
        return nosInpecionados;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    private void desenhaInstrucaoParaArrastarSimbolos(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        //g.setFont(RenderizadorBase.FONTE_NORMAL. );
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        FontMetrics metrics = g.getFontMetrics();
        String texto = INSTRUCAO.replace("\n", "");
        int larguraInstrucao = metrics.stringWidth(texto);
        if (larguraInstrucao <= getWidth()) {//pode desenhar tudo na mesma linha?
            int x = getWidth() / 2 - larguraInstrucao / 2;
            g.drawString(texto, x, getHeight() / 2);
        } else {//separa o texto em váras linhas
            String[] linhas = INSTRUCAO.split("\n");
            int y = getHeight() / 2 - (linhas.length / 2 * metrics.getHeight());
            for (int i = 0; i < linhas.length; i++) {
                String string = linhas[i].trim();
                int x = getWidth() / 2 - metrics.stringWidth(string) / 2;
                g.drawString(string, x, y);
                y += metrics.getHeight();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().getSize() <= 0) {
            desenhaInstrucaoParaArrastarSimbolos(g);
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void setTamanhoDaFonte(float tamanho) {
        RenderizadorBase.setTamanhoDaFonte(tamanho);
        recriaCacheDaAlturaDosItems();
        repaint();
    }

    void recriaCacheDaAlturaDosItems() {
        //hack para forçar a JList a refazer a cache. Sem essas linhas o componente não reflete a mudança no tamanho da fonte adequadamente.
        //Idéia retirada desse post: http://stackoverflow.com/questions/7306295/swing-jlist-with-multiline-text-and-dynamic-height?lq=1
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                setFixedCellHeight(10);//provavelmente poderia ser qualquer outro valor positivo
                setFixedCellHeight(-1);
            }
        });

    }

    private class RenderizadorDaLista implements ListCellRenderer<ItemDaLista> {

        private final JPanel panel = new JPanel(new BorderLayout());

        public RenderizadorDaLista() {
            panel.setBorder(EMPTY_BORDER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ItemDaLista> list, ItemDaLista item, int index, boolean selected, boolean hasFocus) {
            RenderizadorBase renderizador = item.getRendererComponent();

            renderizador.setOpaque(true);

            panel.removeAll();
            panel.add(renderizador, BorderLayout.CENTER); //o componente que renderiza o item da lista foi inserido em um painel e este painel 
            //usa uma EmptyBorder para separar verticalmente os items da lista, assim os items não ficam muito "grudados" uns nos outros.


            boolean pintaSelecao = hasFocus || list.getSelectionModel().isSelectedIndex(index);
            if (pintaSelecao) {
                if(Configuracoes.getInstancia().isTemaDark())
                {
                    panel.setBackground(ColorController.FUNDO_ESCURO);
                }
                else{
                    panel.setBackground(ColorController.COR_DESTAQUE);
                }
                
            } else if(index == indexHovered) {
                panel.setBackground(ColorController.COR_CONSOLE);
            }else{
                panel.setBackground(ColorController.TRANSPARENTE);
            }
            return panel;
            //existem 3 tipos de ItemDaLista (para variáveis, para vetores e para matrizes)
            //cada subclasse de ItemDaLista retorna um renderer component diferente.
        }
    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao) {
        programaExecutando = false;
        //limpa os valores no fim da execução
        for (int i = 0; i < model.getSize(); i++) {
            model.getElementAt(i).limpa();
        }
        ultimoItemModificado = null;
        timerAtualizacao.stop();
        setStatusDoDestaqueNosSimbolosInspecionados(true);
        repaint();
    }

    private void setStatusDoDestaqueNosSimbolosInspecionados(boolean statusDoDestaque) {
        for (int i = 0; i < model.getSize(); i++) {
            model.get(i).setDesenhaDestaques(statusDoDestaque);
        }
    }

    public void resetaDestaqueDosSimbolos() {
        setStatusDoDestaqueNosSimbolosInspecionados(false);
        repaint();
    }

    /**
     * *
     * desenha apenas as regiões dos items que podem ser repintados. Os itens
     * são repintados apenas umas poucas vezes por segundo para evitar problemas
     * de desempenho quando o usuário estiver inspecionados variáveis que são
     * alteradas várias vezes por segundo em um jogo, por exemplo.
     */
    private void redesenhaItemsDaLista() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int totalDeItems = model.getSize();
                int offset = 0;
                for (int i = 0; i < totalDeItems; i++) {
                    ItemDaLista item = model.getElementAt(i);
                    RenderizadorBase renderizador = item.getRendererComponent();
                    int alturaDoRenderizador = renderizador.getAlturaPreferida();
                    Rectangle bounds = new Rectangle(0, 0, getWidth(), alturaDoRenderizador);

                    Insets insets = EMPTY_BORDER.getBorderInsets(renderizador);
                    offset += insets.top;
                    bounds.translate(0, offset);//desloca o retângulo para a posição vertical onde o item está na lista
                    repaint(bounds);
                    offset += bounds.height + insets.bottom;
                }
            }
        });

    }

    public boolean contemNo(NoDeclaracao no) {
        if (no == null) {
            return false;
        }
        return getItemDoNo(no) != null;
    }

    private ItemDaLista getItemDoNo(NoDeclaracao no) {
        for (int i = 0; i < model.getSize(); i++) {
            ItemDaLista item = model.getElementAt(i);
            if (mesmoNo(item.getNoDeclaracao(), no)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void execucaoIniciada(Programa programa) {
        programaExecutando = true;
        setStatusDoDestaqueNosSimbolosInspecionados(false);
        timerAtualizacao.start();
    }

    private void atualizaValoresVariaveisInspecionadas()
    {
        SwingUtilities.invokeLater(() -> {
            
            if (programa == null)
            {
                return;
            }
            
            for (int i = 0; i < model.getSize(); i++) 
            {
                ItemDaLista item = model.getElementAt(i);
                item.atualiza(programa);
                
                item.setDesenhaDestaques(true);
            }
        
            redesenhaItemsDaLista();
        });
    }
    
    @Override
    public void highlightLinha(int linha) 
    {
        atualizaValoresVariaveisInspecionadas();
    }

    @Override
    public void execucaoPausada() 
    {
        atualizaValoresVariaveisInspecionadas();
    }

    @Override
    public void execucaoResumida() {
        
    }
    
    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho) {
        
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class TratadorDeArrastamento extends TransferHandler {

        private boolean podeImportartNosArrastadosDaTree(TransferHandler.TransferSupport support) {
            List<NoDeclaracao> nosTransferidos = null;
            try {
                nosTransferidos = (List<NoDeclaracao>) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
                for (NoDeclaracao no : nosTransferidos) {
                    if (!contemNo(no)) {
                        support.setShowDropLocation(true);
                        return true;//basta que um dos nós transferidos ainda não esteja no inspetor e deve ser possível adicionar este nó na lista
                    }
                }
            } catch (IOException | UnsupportedFlavorException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
            return false;
        }

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            DataFlavor flavors[] = support.getDataFlavors();
            boolean podeImportar = false;
            for (DataFlavor flavor : flavors) {
                //aceita apenas texto (que é arrastado do editor) ou uma lista de NoDeclaracao
                if (flavor.isFlavorTextType() || flavor == AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR) {
                    podeImportar = true;
                    break;
                }
            }
            if (!support.isDrop() || !podeImportar) {
                return false;
            }

            boolean arrastandoNosDaJTree = support.getTransferable().isDataFlavorSupported(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
            if (arrastandoNosDaJTree) {
                return podeImportartNosArrastadosDaTree(support);
            }

            return true;//suporta importação de string
        }

        private boolean importaNosArrastadosDaJTree(TransferHandler.TransferSupport support) {
            List<NoDeclaracao> nosTransferidos = null;
            try {
                nosTransferidos = (List<NoDeclaracao>) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);

                boolean importou = false;
                for (NoDeclaracao noTransferido : nosTransferidos) {
                    if (!contemNo(noTransferido)) {
                        adicionaNo(noTransferido);
                        importou = true;
                    }
                }
                return importou;
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            }
            return false;
        }

        private boolean importaStringArrastada(TransferHandler.TransferSupport support) {
            try {
                String stringArrastada = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                if (stringArrastada.equals(textArea.getSelectedText())) {
                    if (stringArrastada.isEmpty() || programa == null) {
                        return false;
                    }
                    int linha = textArea.getLineOfOffset(textArea.getSelectionStart()) + 1;
                    int coluna = textArea.getSelectionStart() - textArea.getLineStartOffset(linha - 1);
                    int tamanhoDoTexto = textArea.getSelectionEnd() - textArea.getSelectionStart();
                    ProcuradorDeDeclaracao procuradorDeDeclaracao = new ProcuradorDeDeclaracao(stringArrastada, linha, coluna, tamanhoDoTexto);
                    programa.getArvoreSintaticaAbstrata().aceitar(procuradorDeDeclaracao);
                    NoDeclaracao no = procuradorDeDeclaracao.getNoDeclaracao();
                    if (procuradorDeDeclaracao.encontrou() && !contemNo(no)) {
                        adicionaNo(no);
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) 
        {
            if (!canImport(support)) 
            {
                return false;
            }

            boolean arrastandoNosDaJTree = support.getTransferable().isDataFlavorSupported(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
            boolean importou;
            if (arrastandoNosDaJTree) 
            {
                importou = importaNosArrastadosDaJTree(support);
            }
            else 
            {
                importou = importaStringArrastada(support);
            }
            return importou;
        }
    }

    private boolean adicionaNoVariavel(NoDeclaracaoVariavel noTransferido) {
        ItemDaListaParaVariavel item = new ItemDaListaParaVariavel((NoDeclaracaoVariavel) noTransferido);
        model.addElement(item);
        if (programa != null)
        {
            programa.inspecionaVariavel(noTransferido.getIdParaInspecao());
        }
        return true;
    }

    private boolean adicionaNoVetor(NoDeclaracaoVetor declaracaoVetor) throws ExcecaoVisitaASA {
        int colunas = -1;
        if (declaracaoVetor.getTamanho() != null) {
            colunas = obtemValorDeExpressaoDoTipoInteiro(declaracaoVetor.getTamanho());
        } else if (declaracaoVetor.getInicializacao() != null) {
            colunas = ((NoVetor) declaracaoVetor.getInicializacao()).getValores().size();
        }
        if (colunas > 0) {
            ItemDaListaParaVetor item = new ItemDaListaParaVetor(colunas, declaracaoVetor);
            model.addElement(item);
            if (programa != null)
            {
                programa.inspecionaVetor(declaracaoVetor.getIdParaInspecao(), colunas);
            }
            return true;
        }
        return false;
    }

    private Integer obtemValorDeExpressaoDoTipoInteiro(NoExpressao expressao) throws ExcecaoVisitaASA {
        if (expressao == null) {
            return null;
        }

        if (expressao instanceof NoInteiro) {
            return ((NoInteiro) expressao).getValor();
        }

        //se a expressão é uma referência para uma variável é necessário encontrar a declaração da variável para obter o seu valor
        if (expressao instanceof NoReferenciaVariavel) {
            if (programa == null) //se não existe um programa então não é possível encontrar um símbolo
            {
                return null;
            }

            NoReferenciaVariavel noReferencia = (NoReferenciaVariavel) expressao;
            TrechoCodigoFonte trechoFonte = noReferencia.getTrechoCodigoFonte();
            int linha = trechoFonte.getLinha();
            int coluna = trechoFonte.getColuna();
            int tamanho = trechoFonte.getTamanhoTexto();
            String nomeDoSimbolo = noReferencia.getNome();
            ProcuradorDeDeclaracao procuradorDeDeclaracao = new ProcuradorDeDeclaracao(nomeDoSimbolo, linha, coluna, tamanho);
            programa.getArvoreSintaticaAbstrata().aceitar(procuradorDeDeclaracao);
            if (procuradorDeDeclaracao.encontrou()) {

                NoDeclaracao noDeclaracao = procuradorDeDeclaracao.getNoDeclaracao();

                if (noDeclaracao instanceof NoDeclaracaoInicializavel) {
                    return obtemValorDeExpressaoDoTipoInteiro(((NoDeclaracaoInicializavel) noDeclaracao).getInicializacao());
                }
            }
        }
        return null;
    }

    private boolean adicionaNoMatriz(NoDeclaracaoMatriz noTransferido) throws ExcecaoVisitaASA {
        int colunas = -1, linhas = -1;
        if (noTransferido.getNumeroColunas() != null && noTransferido.getNumeroLinhas() != null) {
            colunas = obtemValorDeExpressaoDoTipoInteiro(noTransferido.getNumeroColunas());
            linhas = obtemValorDeExpressaoDoTipoInteiro(noTransferido.getNumeroLinhas());
        } else if (noTransferido.getInicializacao() != null) {
            List<List<Object>> valores = ((NoMatriz) noTransferido.getInicializacao()).getValores();
            linhas = valores.size();
            colunas = valores.get(0).size();
        }
        if (colunas > 0 && linhas > 0) {
            ItemDaListaParaMatriz item = new ItemDaListaParaMatriz(linhas, colunas, noTransferido);
            model.addElement(item);
            
            item.addListener(() -> { //recria a cache das alturas dos itens sempre que uma matriz é redimensionada
                recriaCacheDaAlturaDosItems();
            });
            
            if (programa != null)
            {
                programa.inspecionaMatriz(noTransferido.getIdParaInspecao(), linhas, colunas);
            }
            return true;
        }
        return false;
    }

    private boolean adicionaNoParametro(NoDeclaracaoParametro declaracaoParametro) {
        ItemDaLista item = null;
        Quantificador quantificador = declaracaoParametro.getQuantificador();
        if (null == quantificador)
            return false;
        
        switch (quantificador) {
            case VALOR:
                item = new ItemDaListaParaVariavel(declaracaoParametro);
                break;
            case VETOR:
                item = new ItemDaListaParaVetor(declaracaoParametro);
                break;
            case MATRIZ:
                ItemDaListaParaMatriz itemMatriz = new ItemDaListaParaMatriz(declaracaoParametro);
                itemMatriz.addListener(() -> { recriaCacheDaAlturaDosItems(); });
                item = itemMatriz;
                break;
            default:
                break;
        }
        if (item != null) {
            model.addElement(item);
            if (programa != null)
            {
                int idInspecao = item.getIdParaInspecao();
                if (item.ehVariavel())
                {
                    programa.inspecionaVariavel(idInspecao);
                }
                else if (item.ehVetor())
                {
                    ItemDaListaParaVetor itemVetor = (ItemDaListaParaVetor)item;
                    programa.inspecionaVetor(idInspecao, itemVetor.getColunas());
                }
                else // matriz
                {
                    ItemDaListaParaMatriz itemMatriz = (ItemDaListaParaMatriz)item;
                    int linhas = itemMatriz.getLinhas();
                    int colunas = itemMatriz.getColunas();
                    programa.inspecionaMatriz(idInspecao, linhas, colunas);
                }
            }
            return true;
        }
        return false;
    }

    public void adicionaNo(NoDeclaracao noTransferido) throws ExcecaoVisitaASA 
    {
        boolean simboloInserido = false;
        if (noTransferido instanceof NoDeclaracaoVariavel) 
        {
            simboloInserido = adicionaNoVariavel((NoDeclaracaoVariavel) noTransferido);
        } 
        else if (noTransferido instanceof NoDeclaracaoParametro) 
        {
            simboloInserido = adicionaNoParametro((NoDeclaracaoParametro) noTransferido);
        } 
        else if (noTransferido instanceof NoDeclaracaoVetor) 
        {
            simboloInserido = adicionaNoVetor((NoDeclaracaoVetor) noTransferido);
        } 
        else if (noTransferido instanceof NoDeclaracaoMatriz) 
        {
            simboloInserido = adicionaNoMatriz((NoDeclaracaoMatriz) noTransferido);
        }
        if (simboloInserido) 
        {
            //altera o destaque do símbolo recém inserido
            model.get(model.getSize() - 1).setDesenhaDestaques(!programaExecutando);
            redesenhaItemsDaLista();
        }
    }

    private class TarefaReconstrucaoNosInspecionados implements Runnable {

        @Override
        public void run() {
            if (programa == null) {
                return;
            }
            try {

                LOGGER.log(Level.INFO, "Reconstruindo lista de nós inspecionados");
                
                //verifica quais nós devem ser mantidos no inspetor, os demais são apagados
                final List<NoDeclaracao> nosQueSeraoMantidos = new ArrayList<>();

                programa.getArvoreSintaticaAbstrata().aceitar(new VisitanteNulo() {

                    @Override
                    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
                        if (contemNo(noDeclaracaoVariavel)) {
                            nosQueSeraoMantidos.add(noDeclaracaoVariavel);
                        }
                        return null;
                    }

                    @Override
                    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA {
                        if (contemNo(noDeclaracaoParametro)) {
                            nosQueSeraoMantidos.add(noDeclaracaoParametro);
                        }
                        return null;
                    }

                    @Override
                    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
                        if (contemNo(noDeclaracaoMatriz)) {
                            nosQueSeraoMantidos.add(noDeclaracaoMatriz);
                        }
                        return null;
                    }

                    @Override
                    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
                        if (contemNo(noDeclaracaoVetor)) {
                            nosQueSeraoMantidos.add(noDeclaracaoVetor);
                        }
                        return null;
                    }

                });
                
                model.clear();
                
                for (NoDeclaracao no : nosQueSeraoMantidos) {
                    adicionaNo(no);
                }
                
            } catch (ExcecaoVisitaASA e) {
                e.printStackTrace(System.err);
            }

        }
    }
    
    private boolean nosTemMesmoEscopo(NoDeclaracao no1, NoDeclaracao no2) {
        int id1 = ((NoDeclaracaoInspecionavel)no1).getIdParaInspecao();
        int id2 = ((NoDeclaracaoInspecionavel)no2).getIdParaInspecao();
        
        return id1 == id2;
    }
    
    private boolean mesmoNo(NoDeclaracao no1, NoDeclaracao no2) {
        boolean mesmoEscopo = nosTemMesmoEscopo(no1, no2);
        boolean mesmoNome = no1.getNome().equals(no2.getNome());
        boolean mesmoTipo = no1.getTipoDado() == no2.getTipoDado();
        return mesmoEscopo && mesmoNome && mesmoTipo;
    }

    public static void main(String args[]) {
        WebLookAndFeel.install();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        

        final InspetorDeSimbolos inspetor = new InspetorDeSimbolos();
        inspetor.setBackground(ColorController.COR_CONSOLE);

        ItemDaListaParaVariavel itemVariavel = new ItemDaListaParaVariavel(new NoDeclaracaoVariavel("variavel", TipoDado.INTEIRO, false));
        itemVariavel.setValor(53);
        inspetor.model.addElement(itemVariavel);
        inspetor.model.addElement(new ItemDaListaParaVariavel(new NoDeclaracaoVariavel("outra variável", TipoDado.LOGICO, false)));
        ItemDaListaParaVetor itemVetor = new ItemDaListaParaVetor(15, new NoDeclaracaoVetor("teste", TipoDado.INTEIRO, new NoInteiro(15), false));
        itemVetor.set(34, 12);
        itemVetor.set(34, 0);
        inspetor.model.addElement(itemVetor);

        inspetor.model.addElement(new ItemDaListaParaVetor(5, new NoDeclaracaoVetor("outro vetor", TipoDado.REAL, new NoInteiro(3), false)));

        ItemDaListaParaMatriz itemMatriz = new ItemDaListaParaMatriz(30, 30, new NoDeclaracaoMatriz("teste 2", TipoDado.INTEIRO, new NoInteiro(30), new NoInteiro(30), false));
        inspetor.model.addElement(itemMatriz);

        inspetor.model.addElement(new ItemDaListaParaMatriz(4, 4, new NoDeclaracaoMatriz("umNomeDeVariável bem grande", TipoDado.INTEIRO, new NoInteiro(4), new NoInteiro(4), false)));
        itemMatriz.set(345, 0, 1);
        
        inspetor.setPreferredSize(new Dimension(300, 600));

        //itemVetor.set(34, 10);
        //itemVariavel.setValor(54);
        inspetor.redesenhaItemsDaLista();

        frame.add(inspetor, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new BoxLayout(panelBotoes, BoxLayout.X_AXIS));
        
        JButton botaoAumentarFonte = new JButton(new AbstractAction("+") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Font fonteNormal = RenderizadorBase.getFonte(RenderizadorBase.TipoFonte.NORMAL);
                inspetor.setTamanhoDaFonte(fonteNormal.getSize() + 2);
            }
        });
        
        JButton botaoDiminuirFonte = new JButton(new AbstractAction("-") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Font fonteNormal = RenderizadorBase.getFonte(RenderizadorBase.TipoFonte.NORMAL);
                inspetor.setTamanhoDaFonte(fonteNormal.getSize() - 2);
            }
        });
        
        panelBotoes.add(botaoAumentarFonte);
        panelBotoes.add(botaoDiminuirFonte);
        botaoAumentarFonte.doClick();
        botaoAumentarFonte.doClick();
        frame.add(panelBotoes, BorderLayout.SOUTH);
        frame.pack();
    }
}
