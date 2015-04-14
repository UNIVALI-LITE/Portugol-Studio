package br.univali.ps.ui.rstautil.inspetor;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
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
import br.univali.ps.ui.util.IconFactory;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
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
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.Icon;
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
public class InspetorDeSimbolos extends JList<InspetorDeSimbolos.ItemDaLista> implements ObservadorExecucao {

    private static final String INSTRUCAO = "Arraste uma variável \npara este painél se\n quiser inspecioná-la";
    private DefaultListModel<ItemDaLista> model = new DefaultListModel<>();
    private static final ComparadorNos COMPARADOR_NOS = new ComparadorNos();

    //renderizadores, servem para renderizar as coisas renderizáveis através de um processo de renderização :)
    private static final RenderizadorDeVetor RENDERIZADOR_DE_VETOR = new RenderizadorDeVetor();
    private static final RenderizadorDeVariavel RENDERIZADOR_DE_VARIAVEL = new RenderizadorDeVariavel();
    private static final RenderizadorDeMatriz RENDERIZADOR_DE_MATRIZ = new RenderizadorDeMatriz();

    protected static ItemDaLista ultimoItemModificado = null;

    boolean programaExecutando = false;

    private final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(20, 0, 20, 0);

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

    public void addListener(InspetorDeSimbolosListener listener) {
        listeners.add(listener);
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().getSize() <= 0) {
            //desenha a instrução para arrastar os símbolos para a lista
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            g.setFont(RenderizadorBase.FONTE_NORMAL);
            g.setColor(Color.GRAY);
            FontMetrics metrics = g.getFontMetrics();
            String texto = INSTRUCAO.replace("\n", "");
            int larguraInstrucao = metrics.stringWidth(texto);
            if (larguraInstrucao <= getWidth()) {
                int x = getWidth() / 2 - larguraInstrucao / 2;
                g.drawString(texto, x, getHeight() / 2);
            } else {//separa o texto em váras linhas
                String[] linhas = INSTRUCAO.split("\n");
                int y = getHeight() / 2;
                for (int i = 0; i < linhas.length; i++) {
                    String string = linhas[i].trim();
                    int x = getWidth() / 2 - metrics.stringWidth(string) / 2;
                    g.drawString(string, x, y);
                    y += metrics.getHeight();
                }
            }
        }
    }

    public abstract static class ItemDaLista {

        protected final NoDeclaracao noDeclaracao;
        private long ultimaPintura = 0;//timestamp da ultima atualização
        protected static final long TEMPO_ENTRE_PINTURAS = 200;//no máximo 4 pinturas por segundo

        protected boolean desenhaDestaques = true;//quando o programa está em execução o desenho dos destaques é desativado.
        //Não adiatanda destacar a última variável atualizada com o programa em execução já que o destaque fica "pulando" freneticamente
        //de uma variável para outra

        public ItemDaLista(NoDeclaracao no) {
            this.noDeclaracao = no;
        }

        public boolean podeRepintar() {
            return System.currentTimeMillis() - ultimaPintura >= TEMPO_ENTRE_PINTURAS;
        }

        public void setDesenhaDestaques(boolean statusDosDestaques) {
            desenhaDestaques = statusDosDestaques;
        }

        void resetaTempoDaUltimaAtualizacao() {
            //resta o momento da atualização de maneira que a próxima chamada para o método podeRepintar retorna true
            ultimaPintura = System.currentTimeMillis() - TEMPO_ENTRE_PINTURAS;
        }

        void guardaTempoDaUltimaPintura() {
            ultimaPintura = System.currentTimeMillis();
        }

        abstract RenderizadorBase getRendererComponent();

        Icon getIcone() {
            if (ehVariavel()) {
                return getIcon(getTipo());
            } else if (ehVetor()) {
                return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "vetor.gif");
            }
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "matriz.gif");
        }

        private Icon getIcon(TipoDado tipoDado) {
            String iconName = "unknown.png";
            if (tipoDado != null) {
                iconName = tipoDado.getNome() + ".png";
            }
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, iconName);
        }

        boolean ehVetor() {
            return noDeclaracao instanceof NoDeclaracaoVetor
                    || (noDeclaracao instanceof NoDeclaracaoParametro && ((NoDeclaracaoParametro) noDeclaracao).getQuantificador() == Quantificador.VETOR);
        }

        boolean ehMatriz() {
            return noDeclaracao instanceof NoDeclaracaoMatriz
                    || (noDeclaracao instanceof NoDeclaracaoParametro && ((NoDeclaracaoParametro) noDeclaracao).getQuantificador() == Quantificador.MATRIZ);
        }

        boolean ehVariavel() {
            return noDeclaracao instanceof NoDeclaracaoVariavel
                    || (noDeclaracao instanceof NoDeclaracaoParametro && ((NoDeclaracaoParametro) noDeclaracao).getQuantificador() == Quantificador.VALOR);
        }

        public boolean podeDesenharDestaque() {
            return this == ultimoItemModificado && desenhaDestaques;
        }

        public String getNome() {
            return noDeclaracao.getNome();
        }

        public TipoDado getTipo() {
            return noDeclaracao.getTipoDado();
        }

        public NoDeclaracao getNoDeclaracao() {
            return noDeclaracao;
        }

        public abstract void limpa();
    }

    private static class ItemDaListaParaVariavel extends ItemDaLista {

        private Object valor;
        private final RenderizadorDeVariavel rendererComponent = RENDERIZADOR_DE_VARIAVEL;

        public ItemDaListaParaVariavel(NoDeclaracaoParametro noParametro) {
            super(noParametro);
        }

        public ItemDaListaParaVariavel(NoDeclaracaoVariavel no) {
            super(no);
        }

        @Override
        RenderizadorBase getRendererComponent() {
            rendererComponent.setItemDaLista(this);
            return rendererComponent;
        }

        void setValor(Object valor) {
            this.valor = valor;
            ultimoItemModificado = this;
        }

        public Object getValor() {
            return valor;
        }

        @Override
        public void limpa() {
            valor = null;
        }
    }

    private static class ItemDaListaParaMatriz extends ItemDaLista {

        private Object valores[][];
        private int ultimaLinhaAtualizada = -1;
        private int ultimaColunaAtualizada = -1;

        public ItemDaListaParaMatriz(int linhas, int colunas, NoDeclaracaoMatriz no) {
            super(no);
            inicializaDimensoes(linhas, colunas);
        }

        public ItemDaListaParaMatriz(NoDeclaracaoParametro declaracaoParametro) {
            super(declaracaoParametro);
            valores = new Object[0][0];
        }

        public boolean dimensoesForamInicializadas() {
            return valores.length > 0 && valores[0].length > 0;
        }

        public final void inicializaDimensoes(int linhas, int colunas) {
            if (linhas <= 0) {
                throw new IllegalArgumentException("quantidade inválida de linhas: " + linhas);
            }
            if (colunas <= 0) {
                throw new IllegalArgumentException("quantidade inválida de colunas: " + colunas);
            }
            valores = new Object[linhas][colunas];
            limpa();
        }

        public int getUltimaColunaAtualizada() {
            return ultimaColunaAtualizada;
        }

        public int getUltimaLinhaAtualizada() {
            return ultimaLinhaAtualizada;
        }

        int getLinhas() {
            return valores.length;
        }

        int getColunas() {
            if (valores.length > 0) {
                return valores[0].length;
            }
            return 0;
        }

        Object get(int linha, int coluna) {
            if (linha >= 0 && linha < valores.length && coluna >= 0 && coluna < valores[0].length) {
                return valores[linha][coluna];
            }
            return null;
        }

        void set(Object valor, int linha, int coluna) {
            if (linha >= 0 && linha < valores.length && coluna >= 0 && coluna < valores[0].length) {
                valores[linha][coluna] = valor;
                ultimaColunaAtualizada = coluna;
                ultimaLinhaAtualizada = linha;
                ultimoItemModificado = this;
            }
        }

        @Override
        public void limpa() {
            for (Object[] linha : valores) {
                for (int c = 0; c < linha.length; c++) {
                    linha[c] = null;
                }
            }
            ultimaColunaAtualizada = -1;
            ultimaLinhaAtualizada = -1;
        }

        @Override
        public String getNome() {
            String nome = super.getNome();
            String linhas = dimensoesForamInicializadas() ? String.valueOf(getLinhas()) : " ? ";
            String colunas = dimensoesForamInicializadas() ? String.valueOf(getColunas()) : " ? ";
            return nome + " [" + linhas + "][" + colunas + "]";
        }

        @Override
        RenderizadorBase getRendererComponent() {
            RENDERIZADOR_DE_MATRIZ.setItemDaLista(this);
            return RENDERIZADOR_DE_MATRIZ;
        }

    }

    private static class ItemDaListaParaVetor extends ItemDaLista {

        private Object valores[];
        private int ultimaColunaAtualizada = -1;
        //private int larguraDasColunas[];

        public ItemDaListaParaVetor(int colunas, NoDeclaracaoVetor no) {
            super(no);
            setNumeroDeColunas(colunas);
        }

        public ItemDaListaParaVetor(NoDeclaracaoParametro declaracaoParametro) {
            super(declaracaoParametro);
            valores = new Object[0];//cria um array vazio para que os métodos não gerem uma NUllPointerException ao acessaverem um atributo que seria nulo caso não fosse inicializado
        }

        /**
         * *
         * @param colunas Este método é invocado no evento de simbolosAlterados
         * somente quando este ItemDaListaParaVetor armazena uma instancia de
         * NoDeclaracaoParametro. Quando este item está ligado com um
         * NoDeclaracaoVetor é possível obter o tamanho do vetor já na
         * inicialização do objeto, mas quando este item está ligado com um
         * NoDeclaracaoParametro é necessário aguardar até que a primeira
         * atualização do parâmetro aconteça para so então obter a quantidade de
         * colunas do vetor.
         */
        void setNumeroDeColunas(int colunas) {
            if (colunas <= 0) {
                throw new IllegalArgumentException("quantidade inválida de colunas: " + colunas);
            }
            valores = new Object[colunas];
        }

        int getColunas() {
            return valores.length;
        }

        Object get(int coluna) {
            if (coluna >= 0 && coluna < valores.length) {
                return valores[coluna];
            }
            return null;
        }

        void set(Object valor, int coluna) {
            if (coluna >= 0 && coluna < valores.length) {
                valores[coluna] = valor;
                ultimaColunaAtualizada = coluna;
                ultimoItemModificado = this;
            }
        }

        @Override
        public String getNome() {
            String nome = super.getNome();
            String colunas = numeroDeColunasFoiInicializado() ? String.valueOf(getColunas()) : " ? ";
            return nome + " [" + colunas + "]";
        }
        
        @Override
        public void limpa() {
            for (int c = 0; c < valores.length; c++) {
                valores[c] = null;
            }
        }

        int getUltimaColunaAtualizada() {
            return ultimaColunaAtualizada;
        }

        @Override
        RenderizadorBase getRendererComponent() {
            InspetorDeSimbolos.RENDERIZADOR_DE_VETOR.setItemDaLista(this);
            return RENDERIZADOR_DE_VETOR;
        }

        //quando este item armazena um NoDeclaracaoParametro só é possível saber a quantidade de colunas
        //na primeira atualização dos valores do parâmetro, nesse caso o número de colunas é zero (não setado) até que o método
        //setNumeroDeColunas seja invocado durante a atualização dos símbolos
        private boolean numeroDeColunasFoiInicializado() {
            return valores.length > 0;
        }

    }

    private static class ItemDaListaParaReferencia extends ItemDaLista {

        private ItemDaLista itemReferenciado;

        public ItemDaListaParaReferencia(ItemDaLista itemReferenciado, NoDeclaracao no) {
            super(no);
            this.itemReferenciado = itemReferenciado;
        }

        @Override
        RenderizadorBase getRendererComponent() {
            return itemReferenciado.getRendererComponent();
        }

        @Override
        public void limpa() {
            itemReferenciado.limpa();
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
                setFixedCellHeight(10);
                setFixedCellHeight(-1);
            }
        });

    }

    private static abstract class RenderizadorBase extends JComponent {

        protected final Color COR_DA_GRADE = new Color(0, 0, 0, 0.3f);
        protected final Color COR_DO_TEXTO = Color.DARK_GRAY;
        protected final Color COR_DO_TEXTO_DESTACADO = Color.BLACK;
        protected final Color COR_DO_CABECALHO_DESTACADO = new Color(0, 0, 0, 0.5f);
        protected final Color COR_DO_FUNDO_EM_DESTAQUE = new Color(1, 0, 0, 0.3f);//vermelho claro
        protected final Stroke TRACEJADO = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5, 1, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 0);

        private static final String STRING_VAZIA = "    ";//usada para representar posições em branco dos vetores e matrizes

        protected static final int MARGEM_HORIZONTAL = 5;

        protected ItemDaLista itemDaLista;

        protected static Font FONTE_NORMAL;
        protected static Font FONTE_DESTAQUE;
        protected static Font FONTE_CABECALHO;
        protected static Font FONTE_CABECALHO_DESTAQUE;

        public RenderizadorBase() {
            super();
            FONTE_NORMAL = Font.decode("Verdana");
            setTamanhoDaFonte(12f);
            //setTamanhoDaFonte(18);
        }

        static void setTamanhoDaFonte(float tamanho) {
            FONTE_NORMAL = FONTE_NORMAL.deriveFont(tamanho);
            FONTE_DESTAQUE = FONTE_NORMAL.deriveFont(Font.BOLD);
            FONTE_CABECALHO = FONTE_NORMAL.deriveFont(10f);
            FONTE_CABECALHO_DESTAQUE = FONTE_CABECALHO.deriveFont(Font.BOLD);
        }

        /**
         * @param g
         * @param topo a posição onde o nome será desenha no eixo y
         * @return a largura da string do nome para que ela possa ser usada como
         * margem para o resto do desenho
         */
        protected int desenhaNome(Graphics g, int x, int topo) {
            g.setFont(itemDaLista.podeDesenharDestaque() ? FONTE_DESTAQUE : FONTE_NORMAL);
            String stringDoNome = itemDaLista.getNome();
            FontMetrics metrics = g.getFontMetrics();
            int larguraDoNome = metrics.stringWidth(stringDoNome);

            g.drawString(stringDoNome, x, topo + metrics.getAscent());
            return larguraDoNome;
        }

        void atualizaDimensoes() {
            setPreferredSize(new Dimension(100, getAlturaPreferida()));
        }

        void setItemDaLista(ItemDaLista itemDaLista) {
            this.itemDaLista = itemDaLista;
            atualizaDimensoes();
        }

        protected abstract int getAlturaPreferida();

        protected String processaStringDoValor(Object valor) {
            if (valor != null) {
                if (itemDaLista.getTipo() == TipoDado.LOGICO) {
                    return (Boolean) valor ? "verdadeiro" : "falso";
                } else if (itemDaLista.getTipo() == TipoDado.REAL) {
                    //usando Locale.English para usar o ponto ao invés da vírgula como separador das casas decimais
                    return String.format(Locale.ENGLISH, "%.1f", valor);
                } else if (itemDaLista.getTipo() == TipoDado.CADEIA) {
                    String string = valor.toString();
                    if (string.length() > 7) {
                        return string.substring(0, 7) + "...";//retorna somente os primeiros 7 caracteres
                    }
                    return string;
                }
                return valor.toString();
            }
            return STRING_VAZIA;
        }

        @Override
        protected void paintComponent(Graphics gr) {
            super.paintComponent(gr);
            if (isFocusOwner()) {
                gr.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            }

        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private static class RenderizadorDeVariavel extends RenderizadorBase {

        @Override
        protected int getAlturaPreferida() {
            FontMetrics metrics = getFontMetrics(FONTE_NORMAL);
            return metrics.getHeight();
        }

        @Override
        void setItemDaLista(ItemDaLista itemDaLista) {
            if (!(itemDaLista instanceof ItemDaListaParaVariavel)) {
                throw new IllegalArgumentException("Item da lista não é um item de variável!");
            }
            super.setItemDaLista(itemDaLista);
        }

        @Override
        protected void paintComponent(Graphics gr) {
            Graphics2D g = (Graphics2D) gr;
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            super.paintComponent(g);
            if (itemDaLista == null) {
                return;
            }
            Icon icone = itemDaLista.getIcone();
            icone.paintIcon(this, g, 0, getHeight() / 2 - icone.getIconHeight() / 2);
            int larguraDoNome = desenhaNome(g, icone.getIconWidth(), 0);

            //desenha valor
            String stringDoValor = processaStringDoValor(((ItemDaListaParaVariavel) itemDaLista).getValor());

            g.setFont((itemDaLista.podeDesenharDestaque()) ? FONTE_DESTAQUE : FONTE_NORMAL);
            FontMetrics metrics = g.getFontMetrics();
            int larguraDoValor = metrics.stringWidth(stringDoValor);
            int larguraDaCaixa = MARGEM_HORIZONTAL + larguraDoValor + MARGEM_HORIZONTAL;

            //pinta fundo de vermelho para destacar
            if (itemDaLista.podeDesenharDestaque()) {
                g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
                g.fillRect(icone.getIconWidth() + larguraDoNome + MARGEM_HORIZONTAL + 1, 0, larguraDaCaixa - 1, getHeight() - 1);

                //desenha caixa do valor
                g.setColor(COR_DA_GRADE);
                g.drawRect(icone.getIconWidth() + larguraDoNome + MARGEM_HORIZONTAL, 0, larguraDaCaixa, getHeight() - 1);
            }

            //desenha valor
            g.setColor(Color.BLACK);
            g.drawString(stringDoValor, icone.getIconWidth() + larguraDoNome + MARGEM_HORIZONTAL + MARGEM_HORIZONTAL, metrics.getAscent());

        }

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private static class RenderizadorDeMatriz extends RenderizadorBase {

        private static final Color COR_DA_LINHA_E_COLUNA_EM_DESTAQUE = new Color(1, 0, 0, 0.085f);

        public RenderizadorDeMatriz() {
            super();
        }

        @Override
        void setItemDaLista(ItemDaLista itemDaLista) {
            if (!(itemDaLista instanceof ItemDaListaParaMatriz)) {
                throw new IllegalArgumentException("Item da lista não é um item de matriz!");
            }
            super.setItemDaLista(itemDaLista);
        }

        @Override
        protected int getAlturaPreferida() {
            if (itemDaLista == null) {
                return 20;//retorna um valor default só para ter o que desenhar, em geral isso não deveria acontecer
            }
            FontMetrics metrics = getFontMetrics(FONTE_NORMAL);
            ItemDaListaParaMatriz item = (ItemDaListaParaMatriz) itemDaLista;
            int alturaDoNome = metrics.getAscent();
            int alturaDoCabecalho = getFontMetrics(FONTE_CABECALHO).getHeight();
            int linhasDeConteudo = Math.min(item.getLinhas(), 4);//no máximo 6 linhas são exibidas, incluindo a linha do nome da matriz, a linha de cabeçalho e 4 linhas de conteúdo
            return alturaDoNome + alturaDoCabecalho + (metrics.getHeight() * linhasDeConteudo);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (itemDaLista != null) {
                Icon icone = itemDaLista.getIcone();
                FontMetrics metrics = g.getFontMetrics(FONTE_NORMAL);
                //int alturaDaLinha = metrics.getHeight() ;
                int yDoIcone = 1 + metrics.getHeight() / 2 - icone.getIconHeight() / 2;
                icone.paintIcon(this, g, 0, yDoIcone);
                //g.drawRect(0, yDoIcone, icone.getIconWidth(), icone.getIconHeight());
                desenhaNome(g, icone.getIconWidth(), 0);
                int totalDeColunas = ((ItemDaListaParaMatriz) itemDaLista).getColunas();
                int totalDeLinhas = ((ItemDaListaParaMatriz) itemDaLista).getLinhas();
                int margemEsquerda = MARGEM_HORIZONTAL;
                int margemSuperior = getFontMetrics(FONTE_CABECALHO).getAscent();
                int colunaInicial = calculaRolagemDasColunas(margemEsquerda);
                int linhaInicial = calculaRolagemDasLinhas();

                desenhaGrade(g, totalDeLinhas, totalDeColunas, colunaInicial, linhaInicial, margemEsquerda, margemSuperior);
            }
        }

        private int calculaRolagemDasLinhas() {
            ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
            int totalDeLinhas = item.getLinhas();
            int alturaDaLinha = getFontMetrics(FONTE_NORMAL).getHeight();
            int yDaLinha = alturaDaLinha;//já inicia do cabeçalho em diante
            int ultimaLinhaAtualizada = item.getUltimaLinhaAtualizada();
            int rolavemVertical = 0;//conta quantas células é preciso deslocar para que a última célula atualizada fique visível no componente
            int alturaDoComponente = getHeight();
            int indiceDaLinha = 1;//pula o cabeçalho
            do {
                yDaLinha += alturaDaLinha;
                if (yDaLinha > alturaDoComponente) {
                    rolavemVertical++;
                }
                indiceDaLinha++;
            } while (indiceDaLinha <= ultimaLinhaAtualizada + 2 && indiceDaLinha < totalDeLinhas);
            boolean precisaDeRolagem = yDaLinha > alturaDoComponente;
            if (precisaDeRolagem && ultimaLinhaAtualizada >= totalDeLinhas - 1) {//se é a última linha
                rolavemVertical++;
            }
            return rolavemVertical;
        }

        private int calculaRolagemDasColunas(int margemEsquerda) {
            ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
            int totalDeColunas = item.getColunas();
            int xDaColuna = margemEsquerda;
            int ultimaColunaAtualizada = item.getUltimaColunaAtualizada();
            int rolagem = 0;//conta quantas células é preciso deslocar para que a última célula atualizada fique visível no componente
            int larguraDoComponente = getWidth();
            int indiceDaColuna = 0;
            do {
                xDaColuna += getLarguraDaColuna(indiceDaColuna++);
                if (xDaColuna > larguraDoComponente) {
                    rolagem++;
                }
            } while (indiceDaColuna <= ultimaColunaAtualizada + 2 && indiceDaColuna < totalDeColunas);

            boolean precisaDeRolagem = xDaColuna > larguraDoComponente;
            if (precisaDeRolagem && ultimaColunaAtualizada >= totalDeColunas - 1) {//se é a última coluna
                rolagem++;
            }
            return rolagem;
        }

        /**
         * *
         * @param indiceDaColuna
         * @return calcula a largura da string que representa o valor da coluna
         * e a largura do índice da coluna. Retorna a maior largura encontrada.
         */
        private int getLarguraDaColuna(int indiceDaColuna) {
            ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
            int totalDeLinhas = item.getLinhas();
            int maiorLargura = 0;
            for (int linha = 0; linha < totalDeLinhas; linha++) {
                String stringDoValor = getStringDoValor(linha, indiceDaColuna);
                String stringDoIndice = String.valueOf(indiceDaColuna);
                FontMetrics metricsDoValor = getFontMetrics(FONTE_NORMAL);
                FontMetrics metricsDoIndice = getFontMetrics(FONTE_CABECALHO);
                if (item.podeDesenharDestaque() && item.getUltimaColunaAtualizada() == indiceDaColuna && item.getUltimaLinhaAtualizada() == linha) {
                    metricsDoIndice = getFontMetrics(FONTE_CABECALHO_DESTAQUE);
                    metricsDoValor = getFontMetrics(FONTE_DESTAQUE);
                }

                int larguraDoValor = MARGEM_HORIZONTAL + metricsDoValor.stringWidth(stringDoValor) + MARGEM_HORIZONTAL;
                int larguraDoIndice = MARGEM_HORIZONTAL + metricsDoIndice.stringWidth(stringDoIndice) + MARGEM_HORIZONTAL;
                int larguraDaStringVazia = MARGEM_HORIZONTAL + metricsDoValor.stringWidth(RenderizadorBase.STRING_VAZIA) + MARGEM_HORIZONTAL; //a largura retornada nunca será menor que a largura da string vazia
                int max = Math.max(Math.max(larguraDaStringVazia, larguraDoIndice), larguraDoValor);
                if (max > maiorLargura) {
                    maiorLargura = max;
                }
            }
            return maiorLargura;//retorna a maior largura entre todas os valores de todas as linhas
        }

        private int getLarguraMaximaDasLinhas() {
            ItemDaListaParaMatriz item = (ItemDaListaParaMatriz) itemDaLista;
            int larguraMaxima = 0;
            for (int l = 0; l < item.getLinhas(); l++) {
                larguraMaxima += getLarguraDaLinha(l);
            }
            return larguraMaxima;
        }

        private int getLarguraDaLinha(int indiceDaLinha) {
            ItemDaListaParaMatriz item = (ItemDaListaParaMatriz) itemDaLista;
            if (indiceDaLinha >= 0 && indiceDaLinha < item.getLinhas()) {
                int larguraDaLinha = 0;
                for (int coluna = 0; coluna < item.getColunas(); coluna++) {
                    larguraDaLinha += getLarguraDaColuna(coluna);
                }
                return larguraDaLinha;
            }
            return 0;
        }

        private String getStringDoValor(int linha, int coluna) {
            ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
            Object valor = item.get(linha, coluna);
            return processaStringDoValor(valor);
        }

        private void desenhaGrade(Graphics g, int totalDeLinhas, int totalDeColunas, int colunaInicial, int linhaInicial, int margemEsquerda, int margemSuperior) {
            int alturaDaLinha = getFontMetrics(FONTE_NORMAL).getHeight();
            int alturaDoCabecalho = getFontMetrics(FONTE_CABECALHO).getHeight();
            int larguraMaximaDoIndiceDeLinha = MARGEM_HORIZONTAL + getFontMetrics(FONTE_DESTAQUE).stringWidth(String.valueOf(totalDeLinhas - 1));//obtém a largura da string do maior índice de linha
            int inicioLinhaHorizontal = margemEsquerda + larguraMaximaDoIndiceDeLinha - 3;
            int xDaLinha = inicioLinhaHorizontal;
            int ultimaLinhaAlterada = ((ItemDaListaParaMatriz) itemDaLista).getUltimaLinhaAtualizada();
            int ultimaColunaAlterada = ((ItemDaListaParaMatriz) itemDaLista).getUltimaColunaAtualizada();
            int indiceDaUltimaLinhaDesenhada = 0;
            int indiceDaUltimaColunaDesenhada = 0;
            for (int l = linhaInicial; l < totalDeLinhas; l++) {
                int yDaLinha = ((l - linhaInicial) + 1) * alturaDaLinha + margemSuperior;
                if (yDaLinha > getHeight()) {//se a linha não estará vísivel
                    break;
                }
                indiceDaUltimaLinhaDesenhada = l;

                xDaLinha = inicioLinhaHorizontal;
                boolean podeDestacarEstaCelula = false;
                for (int c = colunaInicial; c < totalDeColunas; c++) {
                    ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
                    podeDestacarEstaCelula = item.podeDesenharDestaque() && ultimaColunaAlterada == c && ultimaLinhaAlterada == l;

                    int larguraDaColuna = getLarguraDaColuna(c);
                    int larguraDaProximaColuna = (c < totalDeColunas - 1) ? getLarguraDaColuna(c + 1) : 0;
                    indiceDaUltimaColunaDesenhada = c;

                    //pinta a linha e a coluna que contém a última célula alterada para ajudar a indentificar visualmente a alteração
                    if (itemDaLista.podeDesenharDestaque()) {
                        //se é exatamente a ultima célula alterada usa uma cor mais forte no fundo
                        if (l == ultimaLinhaAlterada && c == ultimaColunaAlterada) {
                            g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
                            g.fillRect(xDaLinha + 1, yDaLinha + 1, larguraDaColuna - 1, alturaDaLinha - 1);
                        } else {
                            //só destaca a linha e coluna alterada em matrizes grandes para evitar confusão visual
                            boolean matrizGrande = item.getLinhas() > 3 && item.getColunas() > 3;
                            boolean podeDestacarLinha = l == ultimaLinhaAlterada && matrizGrande;
                            boolean podeDestacarColuna = c == ultimaColunaAlterada && matrizGrande;
                            if (podeDestacarLinha || podeDestacarColuna) {
                                g.setColor(COR_DA_LINHA_E_COLUNA_EM_DESTAQUE);
                                g.fillRect(xDaLinha + 1, yDaLinha + 1, larguraDaColuna - 1, alturaDaLinha - 1);
                            }

                        }
                    }

                    //desenha o valor da célula
                    String stringDoValor = getStringDoValor(l, c);
                    g.setFont(podeDestacarEstaCelula ? FONTE_DESTAQUE : FONTE_NORMAL);
                    FontMetrics metrics = g.getFontMetrics();
                    int xDoValor = xDaLinha + larguraDaColuna / 2 - metrics.stringWidth(stringDoValor) / 2;
                    int yDoValor = alturaDoCabecalho + (alturaDaLinha * (l - linhaInicial + 1)) + metrics.getAscent() - metrics.getDescent();
                    g.setColor(podeDestacarEstaCelula ? COR_DO_TEXTO_DESTACADO : COR_DO_TEXTO);
                    g.drawString(stringDoValor, xDoValor, yDoValor);

                    //linha vertical
                    g.setColor(COR_DA_GRADE);
                    Stroke tracejadoPadrao = ((Graphics2D) g).getStroke();
                    boolean ehLinhaDeBaixoComRolagem = yDaLinha + alturaDaLinha >= getHeight() && l < totalDeLinhas - 1;
                    boolean ehLinhaDeCimaComRolagem = l == linhaInicial && linhaInicial > 0;
                    boolean ehPrimeiraColunaComRolagem = c == colunaInicial && colunaInicial > 0;
                    boolean ehUltimaColunaComRolagem = xDaLinha + larguraDaColuna + larguraDaProximaColuna > getWidth();
                    if (ehLinhaDeBaixoComRolagem || ehLinhaDeCimaComRolagem) {//esta é a última linha? (verifica se a próxima linha já estará fora da tela)
                        ((Graphics2D) g).setStroke(TRACEJADO);
                    }
                    if (!ehPrimeiraColunaComRolagem) {//não desenha linha vertical na primeira coluna quando a matriz tem rolagem
                        if (!ehLinhaDeCimaComRolagem) {
                            int offset = ehLinhaDeBaixoComRolagem ? 0 : 1;
                            g.drawLine(xDaLinha, yDaLinha + offset, xDaLinha, yDaLinha + alturaDaLinha - offset);
                        } else {
                            g.drawLine(xDaLinha, yDaLinha + alturaDaLinha, xDaLinha, yDaLinha);//essa linha é desenhada de baixo para cima para que o tracejado fique correto
                        }
                    }
                    ((Graphics2D) g).setStroke(tracejadoPadrao);//reseta o tipo de tracejado 

                    //linha horizontal
                    if (ehPrimeiraColunaComRolagem || ehUltimaColunaComRolagem) {
                        ((Graphics2D) g).setStroke(TRACEJADO);
                    }
                    if (!ehLinhaDeCimaComRolagem) {
                        if (!ehPrimeiraColunaComRolagem) {
                            g.drawLine(xDaLinha, yDaLinha, xDaLinha + larguraDaColuna, yDaLinha);
                        } else {
                            g.drawLine(xDaLinha + larguraDaColuna, yDaLinha, xDaLinha, yDaLinha);//desenha da direita para esquerda por causa do tracejado
                        }
                    }
                    ((Graphics2D) g).setStroke(tracejadoPadrao);//reseta o tipo de tracejado 

                    //desenha os índices
                    if (l == linhaInicial) {
                        String stringIndiceDaColuna = String.valueOf(c);
                        if (itemDaLista.podeDesenharDestaque() && c == ultimaColunaAlterada) {
                            g.setFont(FONTE_CABECALHO_DESTAQUE);
                            g.setColor(COR_DO_CABECALHO_DESTACADO);
                        } else {
                            g.setFont(FONTE_CABECALHO);
                            g.setColor(COR_DA_GRADE);
                        }
                        int larguraDoIndiceDeColuna = g.getFontMetrics().stringWidth(stringIndiceDaColuna);
                        //índice da coluna
                        g.drawString(stringIndiceDaColuna, xDaLinha + larguraDaColuna / 2 - larguraDoIndiceDeColuna / 2, yDaLinha - 2);//desenha índice 
                    }

                    xDaLinha += larguraDaColuna;

                    if (xDaLinha + larguraDaProximaColuna > getWidth()) {
                        break;//as próximas colunas não estarão vísiveis e não precisam ser desenhadas
                    }
                }

                //desenha índice da linha
                String stringIndiceDaLinha = String.valueOf(l);
                if (itemDaLista.podeDesenharDestaque() && l == ultimaLinhaAlterada) {
                    g.setFont(FONTE_CABECALHO_DESTAQUE);
                    g.setColor(COR_DO_CABECALHO_DESTACADO);
                } else {
                    g.setFont(FONTE_CABECALHO);
                    g.setColor(COR_DA_GRADE);
                }
                FontMetrics metrics = g.getFontMetrics();
                int largura = metrics.stringWidth(stringIndiceDaLinha);
                g.drawString(stringIndiceDaLinha,
                        inicioLinhaHorizontal - largura - 2, //x
                        margemSuperior + alturaDaLinha + (alturaDaLinha * (l - linhaInicial)) + metrics.getAscent() + metrics.getDescent());
            }

            g.setColor(COR_DA_GRADE);

            //desenha a borda direita
            if (indiceDaUltimaColunaDesenhada == totalDeColunas - 1) {
                int offsetSuperior = (linhaInicial > 0) ? alturaDaLinha : 0;
                int offsetInferior = (indiceDaUltimaLinhaDesenhada < totalDeLinhas - 1) ? alturaDaLinha : 0;
                g.drawLine(xDaLinha, alturaDaLinha + margemSuperior + offsetSuperior, xDaLinha, getHeight() - 1 - offsetInferior);
            }

            //desenha a borda embaixo
            if (indiceDaUltimaLinhaDesenhada == ((ItemDaListaParaMatriz) itemDaLista).getLinhas() - 1) {
                g.drawLine(inicioLinhaHorizontal, getHeight() - 1, xDaLinha, getHeight() - 1);
            }
        }

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private static class RenderizadorDeVetor extends RenderizadorBase {

        public RenderizadorDeVetor() {
            super();
        }

        @Override
        void setItemDaLista(ItemDaLista itemDaLista) {
            if (!(itemDaLista instanceof ItemDaListaParaVetor)) {
                throw new IllegalArgumentException("Item da lista não é um item de vetor!");
            }
            super.setItemDaLista(itemDaLista);
        }

        @Override
        protected int getAlturaPreferida() {
            FontMetrics metrics = getFontMetrics(FONTE_NORMAL);
            int alturaDoNome = metrics.getAscent();
            int alturaCabecalho = getFontMetrics(FONTE_CABECALHO).getHeight();
            return alturaDoNome + alturaCabecalho + metrics.getHeight() + 1;//2 linhas, uma com os valores e outra com os índices
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (itemDaLista != null) {
                Icon icone = itemDaLista.getIcone();
                FontMetrics metrics = g.getFontMetrics(FONTE_NORMAL);
                int yDoIcone = 1 + metrics.getHeight() / 2 - icone.getIconHeight() / 2;
                icone.paintIcon(this, g, 0, yDoIcone);
                desenhaNome(g, icone.getIconWidth(), 0);
                int totalDeColunas = ((ItemDaListaParaVetor) itemDaLista).getColunas();
                int margemEsquerda = MARGEM_HORIZONTAL;// icone.getIconWidth() + larguraDoNome + MARGEM;
                int margemSuperior = metrics.getAscent();
                int colunaInicial = calculaRolagem(margemEsquerda);
                desenhaGrade(g, totalDeColunas, colunaInicial, margemEsquerda, margemSuperior);
            }
        }

        private int calculaRolagem(int margemEsquerda) {
            ItemDaListaParaVetor item = ((ItemDaListaParaVetor) itemDaLista);
            int totalDeColunas = item.getColunas();
            int xDaColuna = margemEsquerda;
            int ultimaColunaAtualizada = item.getUltimaColunaAtualizada();
            int rolagem = 0;//conta quantas células é preciso deslocar para que a última coluna atualizada fique visível no componente
            int larguraDoComponente = getWidth();
            int indiceDaColuna = 0;
            do {
                xDaColuna += getLarguraDaColuna(indiceDaColuna++);
                if (xDaColuna > larguraDoComponente) {
                    rolagem++;
                }
            } while (indiceDaColuna <= ultimaColunaAtualizada + 2 && indiceDaColuna < totalDeColunas);
            return rolagem;
        }

        /**
         * *
         * @param indiceDaColuna
         * @return calcula a largura da string que representa o valor da coluna
         * e a largura do índice da coluna. Retorna a maior largura encontrada.
         */
        private int getLarguraDaColuna(int indiceDaColuna) {
            ItemDaListaParaVetor item = ((ItemDaListaParaVetor) itemDaLista);
            String stringDoValor = getStringDoValor(indiceDaColuna);
            String stringDoIndice = String.valueOf(indiceDaColuna);
            FontMetrics metricsDoValor = getFontMetrics(FONTE_NORMAL);
            FontMetrics metricsDoIndice = getFontMetrics(FONTE_CABECALHO);
            if (item.podeDesenharDestaque() && item.getUltimaColunaAtualizada() == indiceDaColuna) {
                metricsDoIndice = getFontMetrics(FONTE_CABECALHO_DESTAQUE);
                metricsDoValor = getFontMetrics(FONTE_DESTAQUE);
            }
            int larguraDoValor = MARGEM_HORIZONTAL + metricsDoValor.stringWidth(stringDoValor) + MARGEM_HORIZONTAL;
            int larguraDoIndice = MARGEM_HORIZONTAL + metricsDoIndice.stringWidth(stringDoIndice) + MARGEM_HORIZONTAL;
            int larguraDaStringVazia = MARGEM_HORIZONTAL + metricsDoValor.stringWidth(RenderizadorBase.STRING_VAZIA) + MARGEM_HORIZONTAL; //a largura retornada nunca será menor que a largura da string vazia
            return Math.max(Math.max(larguraDaStringVazia, larguraDoIndice), larguraDoValor);
        }

        private String getStringDoValor(int indice) {
            ItemDaListaParaVetor item = ((ItemDaListaParaVetor) itemDaLista);
            return processaStringDoValor(item.get(indice));
        }

        private void desenhaGrade(Graphics g, int totalDeColunas, int colunaInicial, int margemEsquerda, int margemSuperior) {
            int alturaDaLinha = g.getFontMetrics(FONTE_NORMAL).getHeight();
            int inicioLinhaHorizontal = margemEsquerda;
            int xDaLinha = inicioLinhaHorizontal;
            int yDaLinha = g.getFontMetrics(FONTE_CABECALHO).getHeight() + margemSuperior;
            xDaLinha = margemEsquerda;
            int indiceDaUltimaColunaDesenhada = 0;
            for (int c = colunaInicial; c < totalDeColunas; c++) {
                ItemDaListaParaVetor item = ((ItemDaListaParaVetor) itemDaLista);
                boolean podeDestacarEstaColuna = itemDaLista.podeDesenharDestaque() && item.getUltimaColunaAtualizada() == c;
                int larguraDaColuna = getLarguraDaColuna(c);
                int larguraDaProximaColuna = (c < totalDeColunas - 1) ? getLarguraDaColuna(c + 1) : 0;
                if (xDaLinha + larguraDaColuna > getWidth()) {
                    break;//as próximas colunas não cabem no componente
                }

                //pinta o fundo da coluna que foi alterada por último
                if (podeDestacarEstaColuna) {
                    g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
                    g.fillRect(xDaLinha + 1, yDaLinha + 1, larguraDaColuna - 1, alturaDaLinha);
                }
                indiceDaUltimaColunaDesenhada = c;

                boolean ehPrimeiraColunaComRolagem = colunaInicial > 0 && c == colunaInicial;
                boolean ehUltimaColunaComRolagem = c < totalDeColunas - 1 && (xDaLinha + larguraDaColuna + larguraDaProximaColuna) > getWidth();

                //if (!ehPrimeiraColunaComRolagem && !ehUltimaColunaComRolagem) {
                //desenha o valor da coluna
                String stringDoValor = getStringDoValor(c);
                g.setFont(podeDestacarEstaColuna ? FONTE_DESTAQUE : FONTE_NORMAL);
                FontMetrics metrics = g.getFontMetrics();
                int xDoValor = xDaLinha + larguraDaColuna / 2 - metrics.stringWidth(stringDoValor) / 2;
                int yDoValor = yDaLinha + alturaDaLinha - metrics.getDescent();
                g.setColor(podeDestacarEstaColuna ? COR_DO_TEXTO_DESTACADO : COR_DO_TEXTO);
                g.drawString(stringDoValor, xDoValor, yDoValor);

                //desenha a string do índice
                String stringDoIndice = String.valueOf(c);
                if (podeDestacarEstaColuna) {
                    g.setFont(FONTE_CABECALHO_DESTAQUE);
                    g.setColor(COR_DO_CABECALHO_DESTACADO);
                } else {
                    g.setFont(FONTE_CABECALHO);
                    g.setColor(COR_DA_GRADE);
                }
                int larguraDoIndice = g.getFontMetrics().stringWidth(stringDoIndice);
                g.drawString(stringDoIndice, xDaLinha + larguraDaColuna / 2 - larguraDoIndice / 2, yDaLinha - 2);//desenha índice 
                //}

                //linha vertical - não desenha a primeira linha vertical quando a primeira
                //coluna que será desenhada não é a primeira coluna do vetor
                g.setColor(COR_DA_GRADE);
                if (!ehPrimeiraColunaComRolagem) {
                    g.drawLine(xDaLinha, yDaLinha + 1, xDaLinha, yDaLinha + alturaDaLinha - 1);
                }

                //desenha a linha horizontal
                g.setColor(COR_DA_GRADE);
                Stroke tracejadoPadrao = ((Graphics2D) g).getStroke();
                if (ehPrimeiraColunaComRolagem || ehUltimaColunaComRolagem) {
                    ((Graphics2D) g).setStroke(TRACEJADO);
                }
                int x1 = (!ehPrimeiraColunaComRolagem) ? xDaLinha : xDaLinha + larguraDaColuna;
                int x2 = (!ehPrimeiraColunaComRolagem) ? xDaLinha + larguraDaColuna : xDaLinha;
                g.drawLine(x1, yDaLinha, x2, yDaLinha);//linha horizontal
                g.drawLine(x1, yDaLinha + alturaDaLinha, x2, yDaLinha + alturaDaLinha);//linha horizontal

                xDaLinha += larguraDaColuna;

                ((Graphics2D) g).setStroke(tracejadoPadrao);//restaura o tracejado
            }

            //desenha a borda direita caso a última coluna do vetor esteja vísivel
            if (indiceDaUltimaColunaDesenhada == ((ItemDaListaParaVetor) itemDaLista).getColunas() - 1) {
                g.drawLine(xDaLinha, yDaLinha + 1, xDaLinha, yDaLinha + alturaDaLinha - 1);
            }
        }

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class RenderizadorDaLista implements ListCellRenderer<ItemDaLista> {

        private final JPanel panel = new JPanel(new BorderLayout());
        private final Color COR_DA_ZEBRA = new Color(0, 0, 0, 0.09f);
        private final Color COR_DA_SELECAO = new Color(0, 0, 0, 0.15f);

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

//    private boolean mesmoNo(NoDeclaracao no1, NoDeclaracao no2) {
//        return mesmoNo(no1, no2, false);
//    }
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
            List<Simbolo> lista = new ArrayList<Simbolo>();
            lista.add(simbolo);

            estaInicializando = simbolo.getOrigemDoSimbolo().getInicializacao() != null;
            simbolosAlterados(lista);
            estaInicializando = false;
        }
    }

    private boolean estaInicializando = false;

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
                        ((ItemDaListaParaVariavel) itemDaLista).setValor(variavel.getValor());
                    } else if (itemDaLista.ehMatriz()) {
                        Matriz matriz = !(simbolo instanceof Ponteiro) ? (Matriz) simbolo : (Matriz) ((Ponteiro) simbolo).getSimboloApontado();
                        ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
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
                    } else {
                        Vetor vetor = !(simbolo instanceof Ponteiro) ? (Vetor) simbolo : (Vetor) ((Ponteiro) simbolo).getSimboloApontado();
                        //quando está inicializando todas as posições do vetor são setadas, quando não 
                        //está apenas a última posição modificada no vetor é atualizada (o loop tem apenas uma iteração)
                        ItemDaListaParaVetor item = (ItemDaListaParaVetor) itemDaLista;
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
                    itemsAlterados = true;
                }
            }
        }
        if (itemsAlterados) {
            redenhaItemsDaLista();
        }
    }

    /**
     * *
     * desenha apenas as regiões dos items que podem ser repintados. Os itens
     * são repintados apenas umas poucas vezes por segundo para evitar problemas
     * de desempenho quando o usuário estiver inspecionados variáveis que são
     * alteradas várias vezes por segundo em um jogo, por exemplo.
     */
    private void redenhaItemsDaLista() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int size = model.getSize();
                int offset = 0;
                for (int i = 0; i < size; i++) {
                    ItemDaLista item = model.getElementAt(i);
                    RenderizadorBase renderizador = item.getRendererComponent();
                    int alturaDoRenderizador = renderizador.getAlturaPreferida();
                    Rectangle bounds = new Rectangle(0, 0, getWidth(), alturaDoRenderizador);
                    Insets insets = EMPTY_BORDER.getBorderInsets(renderizador);
                    int yOriginal = insets.top;
                    if (item.podeRepintar()) {
                        bounds.translate(0, offset);//desloca o retângulo para a posição vertical onde o item está na lista
                        repaint(bounds);
                        item.guardaTempoDaUltimaPintura();
                    }
                    offset += yOriginal + bounds.height + insets.bottom;
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
        NoDeclaracaoMatriz declaracaoMatriz = ((NoDeclaracaoMatriz) noTransferido);
        int colunas = -1, linhas = -1;
        if (declaracaoMatriz.getNumeroColunas() != null && declaracaoMatriz.getNumeroLinhas() != null) {
            colunas = ((NoInteiro) declaracaoMatriz.getNumeroColunas()).getValor();
            linhas = ((NoInteiro) declaracaoMatriz.getNumeroLinhas()).getValor();
        } else if (declaracaoMatriz.getInicializacao() != null) {
            List<List<Object>> valores = ((NoMatriz) declaracaoMatriz.getInicializacao()).getValores();
            linhas = valores.size();
            colunas = valores.get(0).size();
        }
        if (colunas > 0 && linhas > 0) {
            model.addElement(new ItemDaListaParaMatriz(linhas, colunas, declaracaoMatriz));
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
                        return;//não existem símbolos sendo inspecionados, não é necessário re-adicionar os símbolos
                    }
                    
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
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
                            } catch (Exception e) {
                                e.printStackTrace(System.err);
                            }

                        }
                    });

                }
            }
        });
    }

    public static void main(String args[]) {
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
        itemMatriz.set(345, 28, 23);
        lista.setPreferredSize(new Dimension(300, 600));

        itemVetor.set(78, 11);
        itemVetor.set(34, 10);
        //itemVariavel.setValor(54);
        lista.redenhaItemsDaLista();

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
