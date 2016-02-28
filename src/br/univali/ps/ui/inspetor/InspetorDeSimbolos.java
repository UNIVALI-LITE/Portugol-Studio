package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Ponteiro;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.BuscadorDeEscopo;
import br.univali.ps.ui.rstautil.ComparadorNos;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.ProcuradorDeDeclaracao;
import com.alee.laf.WebLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author elieser
 */
public class InspetorDeSimbolos extends JList<ItemDaLista> implements ObservadorExecucao {

    private static final String INSTRUCAO = "Arraste uma variável para este \npainél se quiser inspecioná-la";
    private DefaultListModel<ItemDaLista> model = new DefaultListModel<>();
    private static final ComparadorNos COMPARADOR_NOS = new ComparadorNos();

    protected static ItemDaLista ultimoItemModificado = null;

    boolean programaExecutando = false;

    private final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(10, 0, 10, 0);

    private JTextArea textArea;//necessário para tratar a importação de variáveis para o inspetor de símbolos diretamente do código fonte
    private Programa ultimoProgramaCompilado;//referência para o programa compilado, 
    //utilizada para procurar variáveis no programa quando o usuário arrasta uma variável
    //do código fonte para o inspetor de símbolos

    private List<InspetorDeSimbolosListener> listeners = new ArrayList<>();

    public InspetorDeSimbolos() {
        model.clear();
        setModel(model);
        setDropMode(DropMode.ON);
        setTransferHandler(new TratadorDeArrastamento());
        setCellRenderer(new RenderizadorDaLista());
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        instalaObservadores();
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
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
                    int indices[] = getSelectedIndices();
                    int modelSize = model.getSize();
                    boolean listaModificada = false;
                    for (int i = indices.length - 1; i >= 0; i--) {
                        int indice = indices[i];
                        if (indice >= 0 && indice < modelSize) {
                            model.remove(indice);
                            listaModificada = true;
                        }
                    }
                    if (listaModificada) {
                        notificaMudancaNaLista();
                    }
                }
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

    public void addListener(InspetorDeSimbolosListener listener) {
        listeners.add(listener);
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    private void desenhaInstrucaoParaArrastarSimbolos(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        //g.setFont(RenderizadorBase.FONTE_NORMAL. );
        g.setColor(Color.GRAY);
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

    private void recriaCacheDaAlturaDosItems() {
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
        private final Color COR_DA_ZEBRA = new Color(0, 0, 0, 0.028f);
        private final Color COR_DA_SELECAO = new Color(0, 0, 0, 0.05f);

        public RenderizadorDaLista() {
            panel.setBorder(EMPTY_BORDER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ItemDaLista> list, ItemDaLista item, int index, boolean selected, boolean hasFocus) {
            JComponent c = (JComponent) item.getRendererComponent();
            c.setOpaque(false);

            panel.removeAll();
            panel.add(c, BorderLayout.CENTER); //o componente que renderiza o item da lista foi inserido em um painel e este painel 
            //usa uma EmptyBorder para separar verticalmente os items da lista, assim os items não ficam muito "grudados" uns nos outros.

            //pinta o fundo quando está com o foco no item da lista ou o index é par (zebra)
            boolean indiceImpar = index % 2 != 0;

            boolean pintaSelecao = hasFocus || list.getSelectionModel().isSelectedIndex(index);
            if (pintaSelecao) {
                panel.setBackground(COR_DA_SELECAO);
            } else if (indiceImpar) {
                panel.setBackground(COR_DA_ZEBRA);
            }
            //desenha o fundo do componente quando está com foco ou quando o índice do 
            //componente é impar (faz o zebramento)
            panel.setOpaque(pintaSelecao || indiceImpar);
            return panel;
            //existem 3 tipos de ItemDaLista (para variáveis, para vetores e para matrizes)
            //cada subclasse de ItemDaLista retorna um renderer component diferente.
        }
    }

    private boolean mesmoNo(NoDeclaracao no1, NoDeclaracao no2, boolean consideraEscopo) {
        if (!consideraEscopo) {
            return COMPARADOR_NOS.compare(no1, no2) > 0;
        }
        try {
            Comparator<NoDeclaracao> comparadorDeNos = new ComparadorSimplificadoDeNos();
            int hashCodeEscopoNo1 = BuscadorDeEscopo.getHashCodeDoObjectDeEscopo(no1, ultimoProgramaCompilado, comparadorDeNos);
            int hashCodeEscopoNo2 = BuscadorDeEscopo.getHashCodeDoObjectDeEscopo(no2, ultimoProgramaCompilado, comparadorDeNos);
            boolean mesmoEscopo = hashCodeEscopoNo1 == hashCodeEscopoNo2;
            boolean mesmoNome = no1.getNome().equals(no2.getNome());
            boolean mesmoTipo = no1.getTipoDado() == no2.getTipoDado();
            return mesmoEscopo && mesmoNome && mesmoTipo;
        } catch (ExcecaoVisitaASA e) {

        }
        return false;
    }

    //este comparador compara apenas nome e tipo das declarações. Ele é usado somente quando o escopo das declarações também está sendo considerado
    //para determinar se dois nós representam a mesma informação
    private class ComparadorSimplificadoDeNos implements Comparator<NoDeclaracao> {

        @Override
        public int compare(NoDeclaracao t, NoDeclaracao t1) {
            boolean mesmoNome = t.getNome().equals(t1.getNome());
            boolean mesmoTipoDeDados = t.getTipoDado() == t1.getTipoDado();
            boolean mesmaClasse = t.getClass().equals(t1.getClass());
            return (mesmoNome && mesmoTipoDeDados && mesmaClasse) ? 1 : -1;
        }

    }

    private boolean simboloEhPermitido(Simbolo simbolo) {
        if (simbolo instanceof Ponteiro) {
            return simboloEhPermitido(((Ponteiro) simbolo).getSimboloApontado());
        }
        NoDeclaracao declaracao = simbolo.getOrigemDoSimbolo();
        return ((simbolo instanceof Variavel && (declaracao instanceof NoDeclaracaoVariavel || declaracao instanceof NoDeclaracaoParametro))
                || (simbolo instanceof Variavel && (declaracao instanceof NoDeclaracaoParametro || declaracao instanceof NoDeclaracaoParametro))
                || (simbolo instanceof Vetor && (declaracao instanceof NoDeclaracaoVetor || declaracao instanceof NoDeclaracaoParametro))
                || (simbolo instanceof Matriz && (declaracao instanceof NoDeclaracaoMatriz || declaracao instanceof NoDeclaracaoParametro)));
    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao) {
        programaExecutando = false;
        //limpa os valores no fim da execução
        for (int i = 0; i < model.getSize(); i++) {
            model.getElementAt(i).limpa();
        }
        ultimoItemModificado = null;
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

    @Override
    public void simboloRemovido(final Simbolo simbolo) {
        if (!programaExecutando) {//não remove os símbolos durante a execução do programa
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (simboloEhPermitido(simbolo)) {
                        ItemDaLista item = getItemDoNo((NoDeclaracaoVariavel) simbolo.getOrigemDoSimbolo());
                        if (item != null) {
                            model.removeElement(item);
                            notificaMudancaNaLista();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void simboloDeclarado(Simbolo simbolo) {
        if (simboloEhPermitido(simbolo)) {
            List<Simbolo> lista = new ArrayList<>();
            lista.add(simbolo);

            estaInicializando = simbolo.getOrigemDoSimbolo().getInicializacao() != null;
            simbolosAlterados(lista);
            estaInicializando = false;
        }
    }

    private boolean estaInicializando = false;

    private void alteraVariavel(Variavel variavel, ItemDaListaParaVariavel item) {
        item.setValor(variavel.getValor());
    }

    private void alteraMatriz(Matriz matriz, ItemDaListaParaMatriz item) {
        if (!item.dimensoesForamInicializadas()) {//só aconte quando é um parâmetro
            item.inicializaDimensoes(matriz.getNumeroLinhas(), matriz.getNumeroColunas());
            recriaCacheDaAlturaDosItems();
        }
        //quando está inicializando todas as posições da matriz são setadas, quando não 
        //está apenas a última posição modificada na matriz é atualizada (cada loop tem apenas uma iteração)
        int indiceInicialLinha = (estaInicializando) ? 0 : matriz.getUltimaLinhaModificada();
        int indiceFinalLinha = (estaInicializando) ? matriz.getNumeroLinhas() : indiceInicialLinha + 1;
        int indiceInicialColuna = (estaInicializando) ? 0 : matriz.getUltimaColunaModificada();
        int indiceFinalColuna = (estaInicializando) ? matriz.getNumeroColunas() : indiceInicialColuna + 1;
        for (int linha = indiceInicialLinha; linha < indiceFinalLinha; linha++) {
            for (int coluna = indiceInicialColuna; coluna < indiceFinalColuna; coluna++) {
                Object valor = matriz.getValor(linha, coluna);
                item.set(valor, linha, coluna);
            }
        }
    }

    private void alteraVetor(Vetor vetor, ItemDaListaParaVetor item) {
        //quando está inicializando todas as posições do vetor são setadas, quando não 
        //está apenas a última posição modificada no vetor é atualizada (o loop tem apenas uma iteração)
        if (!item.numeroDeColunasFoiInicializado()) {
            item.setNumeroDeColunas(vetor.getTamanho());
        }
        int indiceInicial = (estaInicializando) ? 0 : vetor.getUltimoIndiceModificado();
        int indiceFinal = (estaInicializando) ? vetor.getTamanho() : indiceInicial + 1;
        for (int i = indiceInicial; i < indiceFinal; i++) {
            Object valor = vetor.getValor(i);
            item.set(valor, i);
        }
    }

    @Override
    public void simbolosAlterados(List<Simbolo> simbolos) {
        boolean itemsAlterados = false;
        for (Simbolo simbolo : simbolos) {
            if (simboloEhPermitido(simbolo)) {
                NoDeclaracao noDeclaracao = (NoDeclaracao) simbolo.getOrigemDoSimbolo();
                ItemDaLista itemDaLista = getItemDoNo(noDeclaracao);
                if (itemDaLista != null) {
                    if (itemDaLista.ehVariavel()) {
                        Variavel variavel = (!(simbolo instanceof Ponteiro)) ? (Variavel) simbolo : (Variavel) ((Ponteiro) simbolo).getSimboloApontado();
                        alteraVariavel(variavel, (ItemDaListaParaVariavel) itemDaLista);
                    } else if (itemDaLista.ehMatriz()) {
                        Matriz matriz = !(simbolo instanceof Ponteiro) ? (Matriz) simbolo : (Matriz) ((Ponteiro) simbolo).getSimboloApontado();
                        alteraMatriz(matriz, ((ItemDaListaParaMatriz) itemDaLista));
                    } else {
                        Vetor vetor = !(simbolo instanceof Ponteiro) ? (Vetor) simbolo : (Vetor) ((Ponteiro) simbolo).getSimboloApontado();
                        alteraVetor(vetor, (ItemDaListaParaVetor) itemDaLista);
                    }
                    itemsAlterados = true;
                }
            }
        }
        if (itemsAlterados) {
            redesenhaItemsDaLista();
        }
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
                    if (item.podeRepintar()) {
                        bounds.translate(0, offset);//desloca o retângulo para a posição vertical onde o item está na lista
                        repaint(bounds);
                        item.atualizaMomentoDaUltimaPintura();
                    }

                    offset += bounds.height + insets.bottom;
                }
            }
        });

    }

    public boolean contemNo(NoDeclaracao no, boolean consideraEscopo) {
        if (no == null) {
            return false;
        }
        return getItemDoNo(no, consideraEscopo) != null;
    }

    public boolean contemNo(NoDeclaracao no) {
        return contemNo(no, false);
    }

    private ItemDaLista getItemDoNo(NoDeclaracao no, boolean consideraEscopo) {
        for (int i = 0; i < model.getSize(); i++) {
            ItemDaLista item = model.getElementAt(i);
            if (mesmoNo(item.getNoDeclaracao(), no, consideraEscopo)) {
                return item;
            }
        }
        return null;
    }

    private ItemDaLista getItemDoNo(NoDeclaracao no) {
        return getItemDoNo(no, false);
    }

    @Override
    public void execucaoIniciada(Programa programa) {
        programaExecutando = true;
        setStatusDoDestaqueNosSimbolosInspecionados(false);
    }

    @Override
    public void highlightLinha(int linha) {
        for (int i = 0; i < model.getSize(); i++) {
            ItemDaLista item = model.getElementAt(i);
            item.resetaTempoDaUltimaAtualizacao();
            item.setDesenhaDestaques(true);
        }
        repaint();
    }

    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class TratadorDeArrastamento extends TransferHandler {

        private boolean podeImportartNosArrastadosDaTree(TransferHandler.TransferSupport support) {
            List<NoDeclaracao> nosTransferidos = null;
            try {
                nosTransferidos = (List<NoDeclaracao>) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            for (NoDeclaracao no : nosTransferidos) {
                if (!no.constante() && !contemNo(no)) {
                    support.setShowDropLocation(true);
                    return true;//basta que um dos nós transferidos ainda não esteja no inspetor e deve ser possível adicionar este nó na lista
                }
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
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            boolean importou = false;
            for (NoDeclaracao noTransferido : nosTransferidos) {
                if (!contemNo(noTransferido)) {
                    adicionaNo(noTransferido);
                    importou = true;
                }
            }
            return importou;
        }

        private boolean importaStringArrastada(TransferHandler.TransferSupport support) {
            try {
                String stringArrastada = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                if (stringArrastada.equals(textArea.getSelectedText())) {
                    if (stringArrastada.isEmpty() || ultimoProgramaCompilado == null) {
                        return false;
                    }
                    int linha = textArea.getLineOfOffset(textArea.getSelectionStart()) + 1;
                    int coluna = textArea.getSelectionStart() - textArea.getLineStartOffset(linha - 1);
                    int tamanhoDoTexto = textArea.getSelectionEnd() - textArea.getSelectionStart();
                    ProcuradorDeDeclaracao procuradorDeDeclaracao = new ProcuradorDeDeclaracao(stringArrastada, linha, coluna, tamanhoDoTexto);
                    ultimoProgramaCompilado.getArvoreSintaticaAbstrata().aceitar(procuradorDeDeclaracao);
                    if (procuradorDeDeclaracao.encontrou()) {
                        adicionaNo(procuradorDeDeclaracao.getNoDeclaracao());
                    }
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            boolean arrastandoNosDaJTree = support.getTransferable().isDataFlavorSupported(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);
            boolean importou = false;
            if (arrastandoNosDaJTree) {
                importou = importaNosArrastadosDaJTree(support);
            } else {
                importou = importaStringArrastada(support);
            }
            if (importou) {
                notificaMudancaNaLista();
            }
            return importou;
        }
    }

    private boolean adicionaNoVariavel(NoDeclaracaoVariavel noTransferido) {
        ItemDaListaParaVariavel item = new ItemDaListaParaVariavel((NoDeclaracaoVariavel) noTransferido);
        model.addElement(item);
        return true;
    }

    private boolean adicionaNoVetor(NoDeclaracaoVetor declaracaoVetor) {
        int colunas = -1;
        if (declaracaoVetor.getTamanho() != null) {
            colunas = ((NoInteiro) declaracaoVetor.getTamanho()).getValor();
        } else if (declaracaoVetor.getInicializacao() != null) {
            colunas = ((NoVetor) declaracaoVetor.getInicializacao()).getValores().size();
        }
        if (colunas > 0) {
            ItemDaListaParaVetor item = new ItemDaListaParaVetor(colunas, declaracaoVetor);
            model.addElement(item);
            return true;
        }
        return false;
    }

    private boolean adicionaNoMatriz(NoDeclaracaoMatriz noTransferido) {
        int colunas = -1, linhas = -1;
        if (noTransferido.getNumeroColunas() != null && noTransferido.getNumeroLinhas() != null) {
            colunas = ((NoInteiro) noTransferido.getNumeroColunas()).getValor();
            linhas = ((NoInteiro) noTransferido.getNumeroLinhas()).getValor();
        } else if (noTransferido.getInicializacao() != null) {
            List<List<Object>> valores = ((NoMatriz) noTransferido.getInicializacao()).getValores();
            linhas = valores.size();
            colunas = valores.get(0).size();
        }
        if (colunas > 0 && linhas > 0) {
            model.addElement(new ItemDaListaParaMatriz(linhas, colunas, noTransferido));
            return true;
        }
        return false;
    }

    private boolean adicionaNoParametro(NoDeclaracaoParametro declaracaoParametro) {
        ItemDaLista item = null;
        Quantificador quantificador = declaracaoParametro.getQuantificador();
        if (quantificador == Quantificador.VALOR) {
            item = new ItemDaListaParaVariavel(declaracaoParametro);
        } else if (quantificador == Quantificador.VETOR) {
            item = new ItemDaListaParaVetor(declaracaoParametro);
        } else if (quantificador == Quantificador.MATRIZ) {
            item = new ItemDaListaParaMatriz(declaracaoParametro);
        }
        if (item != null) {
            model.addElement(item);
            return true;
        }
        return false;
    }

    public void adicionaNo(NoDeclaracao noTransferido) {
        boolean simboloInserido = false;
        if (noTransferido instanceof NoDeclaracaoVariavel) {
            simboloInserido = adicionaNoVariavel((NoDeclaracaoVariavel) noTransferido);
        } else if (noTransferido instanceof NoDeclaracaoParametro) {
            simboloInserido = adicionaNoParametro((NoDeclaracaoParametro) noTransferido);
        } else if (noTransferido instanceof NoDeclaracaoVetor) {
            simboloInserido = adicionaNoVetor((NoDeclaracaoVetor) noTransferido);
        } else if (noTransferido instanceof NoDeclaracaoMatriz) {
            simboloInserido = adicionaNoMatriz((NoDeclaracaoMatriz) noTransferido);
        }
        if (simboloInserido) {
            //altera o destaque do símbolo recém inserido
            model.get(model.getSize() - 1).setDesenhaDestaques(!programaExecutando);
        }
    }

    private void notificaMudancaNaLista() {
        for (InspetorDeSimbolosListener listener : listeners) {
            listener.listaDeSimbolosInpecionadosFoiModificada();
        }
    }

    private class TarefaDeReconstrucaoDaListaDeNosInspecionados implements Runnable {

        @Override
        public void run() {
            if (ultimoProgramaCompilado == null) {
                return;
            }
            try {

                //verifica quais nós devem ser mantidos no inspetor, os demais são apagados
                final List<NoDeclaracao> nosQueSeraoMantidos = new ArrayList<>();

                ultimoProgramaCompilado.getArvoreSintaticaAbstrata().aceitar(new VisitanteNulo() {

                    @Override
                    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
                        List<NoDeclaracaoParametro> parametros = declaracaoFuncao.getParametros();
                        for (NoDeclaracaoParametro parametro : parametros) {
                            visitar(parametro);
                        }
                        return super.visitar(declaracaoFuncao);
                    }

                    @Override
                    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
                        if (contemNo(noDeclaracaoVariavel, true)) {
                            nosQueSeraoMantidos.add(noDeclaracaoVariavel);
                        }
                        return null;
                    }

                    @Override
                    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA {
                        if (contemNo(noDeclaracaoParametro, true)) {
                            nosQueSeraoMantidos.add(noDeclaracaoParametro);
                        }
                        return null;
                    }

                    @Override
                    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
                        if (contemNo(noDeclaracaoMatriz, true)) {
                            nosQueSeraoMantidos.add(noDeclaracaoMatriz);
                        }
                        return null;
                    }

                    @Override
                    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
                        if (contemNo(noDeclaracaoVetor, true)) {
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

    //sempre que o código fonte é alterado este listener é disparado. Toda a árvore sintática
    //é recriada e é necessário verificar se os símbolos que estão no inspetor ainda existem
    //no código. Também é possível que os símbolos tenham sido renomeados, ou que o tipo deles
    //tenha mudado.
    public void observar(RSyntaxTextArea textArea) {
        PortugolParser.getParser(textArea).addPropertyChangeListener(PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                String name = pce.getPropertyName();
                if (RSyntaxTextArea.SYNTAX_STYLE_PROPERTY.equals(name) || PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO.equals(name)) {
                    ultimoProgramaCompilado = (Programa) pce.getNewValue();
                    if (model.isEmpty()) {
                        return;//não existem símbolos sendo inspecionados, não é necessário reconstruir a lista de símbolos inspecionados
                    }
                    SwingUtilities.invokeLater(new TarefaDeReconstrucaoDaListaDeNosInspecionados());
                }
            }
        });
    }

    public static void main(String args[]) {
        WebLookAndFeel.install();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());

        final InspetorDeSimbolos lista = new InspetorDeSimbolos();

        ItemDaListaParaVariavel itemVariavel = new ItemDaListaParaVariavel(new NoDeclaracaoVariavel("variavel", TipoDado.INTEIRO, false));
        itemVariavel.setValor(53);
        lista.model.addElement(itemVariavel);
        lista.model.addElement(new ItemDaListaParaVariavel(new NoDeclaracaoVariavel("outra variável", TipoDado.LOGICO, false)));
        ItemDaListaParaVetor itemVetor = new ItemDaListaParaVetor(15, new NoDeclaracaoVetor("teste", TipoDado.INTEIRO, new NoInteiro(15), false));
        itemVetor.set(34, 12);
        lista.model.addElement(itemVetor);
        lista.model.addElement(new ItemDaListaParaVetor(5, new NoDeclaracaoVetor("outro vetor", TipoDado.REAL, new NoInteiro(3), false)));
        ItemDaListaParaMatriz itemMatriz = new ItemDaListaParaMatriz(30, 30, new NoDeclaracaoMatriz("teste 2", TipoDado.INTEIRO, new NoInteiro(30), new NoInteiro(30), false));
        lista.model.addElement(itemMatriz);
        lista.model.addElement(new ItemDaListaParaMatriz(4, 4, new NoDeclaracaoMatriz("umNomeDeVariável bem grande", TipoDado.INTEIRO, new NoInteiro(4), new NoInteiro(4), false)));
        itemMatriz.set(345, 14, 14);
        lista.setPreferredSize(new Dimension(300, 600));

        //itemVetor.set(78, 11);
        //itemVetor.set(34, 10);
        //itemVariavel.setValor(54);
        lista.redesenhaItemsDaLista();

        frame.add(lista, BorderLayout.CENTER);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new BoxLayout(panelBotoes, BoxLayout.X_AXIS));
        JButton botaoAumentarFonte = new JButton(new AbstractAction("+") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                lista.setTamanhoDaFonte(RenderizadorBase.FONTE_NORMAL.getSize() + 2);
            }
        });
        JButton botaoDiminuirFonte = new JButton(new AbstractAction("-") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                lista.setTamanhoDaFonte(RenderizadorBase.FONTE_NORMAL.getSize() - 2);
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
