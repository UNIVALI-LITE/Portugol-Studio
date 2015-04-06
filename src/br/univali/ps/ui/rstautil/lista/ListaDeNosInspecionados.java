package br.univali.ps.ui.rstautil.lista;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoValor;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.editor.PSTextArea;
import br.univali.ps.ui.rstautil.ComparadorNos;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.ProcuradorDeDeclaracao;
import br.univali.ps.ui.util.IconFactory;
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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIDefaults;
import javax.swing.border.Border;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 *
 * @author elieser
 */
public class ListaDeNosInspecionados extends JList<ListaDeNosInspecionados.ItemDaLista> implements ObservadorExecucao {

    private static final String INSTRUCAO = "Arraste uma variável para \n este painél se quiser inspecioná-la";
    private DefaultListModel<ItemDaLista> model = new DefaultListModel<>();
    private static final ComparadorNos COMPARADOR_NOS = new ComparadorNos();

    //renderizadores, servem para renderizar as coisas renderizáveis através de um processo de renderização :)
    private static final RenderizadorDeVetor RENDERIZADOR_DE_VETOR = new RenderizadorDeVetor();
    private static final RenderizadorDeVariavel RENDERIZADOR_DE_VARIAVEL = new RenderizadorDeVariavel();
    private static final RenderizadorDeMatriz RENDERIZADOR_DE_MATRIZ = new RenderizadorDeMatriz();

    protected static ItemDaLista ultimoItemModificado = null;

    boolean programaExecutando = false;

    private final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(0, 0, 10, 0);

    private JTextArea textArea;//necessário para tratar a importação de variáveis para o inspetor de símbolos diretamente do código fonte
    private Programa ultimoProgramaCompilado;//referência para o programa compilado, 
    //utilizada para procurar variáveis no programa quando o usuário arrasta uma variável
    //do código fonte para o inspetor de símbolos

    public ListaDeNosInspecionados() {
        model.clear();
        setModel(model);
        setDropMode(DropMode.ON);
        setTransferHandler(new TratadorDeArrastamento());
        setCellRenderer(new RenderizadorDaLista());
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
                    for (int i = indices.length - 1; i >= 0; i--) {
                        int indice = indices[i];
                        if (indice >= 0 && indice < modelSize) {
                            model.remove(indice);
                        }
                    }
                }
            }

        });
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().getSize() <= 0) {
            //desenha a instrução para arrastar os símbolos para a lista
            g.setColor(Color.GRAY);
            FontMetrics metrics = g.getFontMetrics();
            String texto = INSTRUCAO.replace("\n", "");
            int larguraInstrucao = metrics.stringWidth(texto);
            if (larguraInstrucao <= getWidth()) {
                int x = getWidth() / 2 - larguraInstrucao / 2;
                g.drawString(texto, x, getHeight() / 2);
            } else {//separa o texto em duas linhas
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
            return noDeclaracao instanceof NoDeclaracaoVetor;
        }

        boolean ehMatriz() {
            return noDeclaracao instanceof NoDeclaracaoMatriz;
        }

        boolean ehVariavel() {
            return noDeclaracao instanceof NoDeclaracaoVariavel;
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
            if (linhas <= 0) {
                throw new IllegalArgumentException("quantidade inválida de linhas: " + linhas);
            }
            if (colunas <= 0) {
                throw new IllegalArgumentException("quantidade inválida de colunas: " + colunas);
            }
            valores = new Object[linhas][colunas];
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
            ListaDeNosInspecionados.RENDERIZADOR_DE_VETOR.setItemDaLista(this);
            return RENDERIZADOR_DE_VETOR;
        }

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private static abstract class RenderizadorBase extends JComponent {

        protected final Color COR_DA_GRADE = Color.LIGHT_GRAY;
        protected final Color COR_DO_TEXTO = Color.DARK_GRAY;
        protected final Color COR_DO_TEXTO_DESTACADO = Color.BLACK;
        protected final Color COR_DO_CABECALHO_DESTACADO = Color.GRAY;
        protected final Color COR_DO_FUNDO_EM_DESTAQUE = new Color(1, 0, 0, 0.2f);//vermelho claro

        private static final String STRING_VAZIA = "    ";//usada para representar posições em branco dos vetores e matrizes

        protected static final int MARGEM = 5;

        protected ItemDaLista itemDaLista;

        protected static final Font FONTE_NORMAL = Font.decode("Verdana-12");
        protected static final Font FONTE_DESTAQUE = FONTE_NORMAL.deriveFont(Font.BOLD);
        protected static final Font FONTE_CABECALHO = FONTE_NORMAL.deriveFont(10f);
        protected static final Font FONTE_CABECALHO_DESTAQUE = FONTE_CABECALHO.deriveFont(Font.BOLD);

        public RenderizadorBase() {
            super();
        }

        /**
         * @param g
         * @param y a posição onde o nome será desenha no eixo y
         * @return a largura da string do nome para que ela possa ser usada como
         * margem para o resto do desenho
         */
        protected int desenhaNome(Graphics g, int x, int y) {

            g.setFont(itemDaLista.podeDesenharDestaque() ? FONTE_DESTAQUE : FONTE_NORMAL);
            String stringDoNome = itemDaLista.getNome();
            FontMetrics metrics = g.getFontMetrics();
            int larguraDoNome = metrics.stringWidth(stringDoNome);

            g.drawString(stringDoNome, x, y + metrics.getAscent());
            return larguraDoNome;
        }

        void setItemDaLista(ItemDaLista itemDaLista) {
            this.itemDaLista = itemDaLista;
            setPreferredSize(new Dimension(300, getAlturaPreferida()));
        }

        protected abstract int getAlturaPreferida();

        protected String processaStringDoValor(Object valor) {
            if (valor != null) {
                if (itemDaLista.getTipo() == TipoDado.LOGICO) {
                    return (Boolean) valor ? "verdadeiro" : "falso";
                } else if (itemDaLista.getTipo() == TipoDado.REAL) {
                    //usando Locale.English para usa o ponto ao invés da vírgula como
                    //separador das casas decimais
                    return String.format(Locale.ENGLISH, "%.1f", valor);
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
            return 20;
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
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            super.paintComponent(g);
            if (itemDaLista == null) {
                return;
            }
            Icon icone = itemDaLista.getIcone();
            icone.paintIcon(this, g, 0, MARGEM);
            int larguraDoNome = desenhaNome(g, icone.getIconWidth(), MARGEM);

            //desenha valor
            String stringDoValor = processaStringDoValor(((ItemDaListaParaVariavel) itemDaLista).getValor());
            if (stringDoValor != null) {
                g.setFont((itemDaLista.podeDesenharDestaque()) ? FONTE_DESTAQUE : FONTE_NORMAL);
                FontMetrics metrics = g.getFontMetrics();
                int larguraDoValor = metrics.stringWidth(stringDoValor);
                int larguraDaCaixa = MARGEM + larguraDoValor + MARGEM;

                //pinta fundo de vermelho para destacar
                if (itemDaLista.podeDesenharDestaque()) {
                    g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
                    g.fillRect(icone.getIconWidth() + larguraDoNome + MARGEM + 1, MARGEM + 1, larguraDaCaixa - 1, getHeight() - 2 - MARGEM);
                }

                g.setColor(Color.BLACK);
                g.drawString(stringDoValor, icone.getIconWidth() + larguraDoNome + MARGEM + MARGEM, metrics.getDescent() + metrics.getAscent() + 1);

                //desenha caixa do valor
                g.setColor(COR_DA_GRADE);
                g.drawRect(icone.getIconWidth() + larguraDoNome + MARGEM, MARGEM, larguraDaCaixa, getHeight() - 1 - MARGEM);
            }

        }

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private static class RenderizadorDeMatriz extends RenderizadorBase {

        private static final Color COR_DA_LINHA_E_COLUNA_EM_DESTAQUE = new Color(1, 0, 0, 0.075f);

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
            FontMetrics metrics = getFontMetrics(FONTE_NORMAL);
            ItemDaListaParaMatriz item = (ItemDaListaParaMatriz) itemDaLista;
            int linhas = Math.min(item.getLinhas(), 4) + 1;//no máximo 5 linhas são exibidas, incluindo a linha de cabeçalho
            return metrics.getHeight() * linhas;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (itemDaLista != null) {
                Icon icone = itemDaLista.getIcone();
                int alturaDaLinha = g.getFontMetrics().getHeight();
                icone.paintIcon(this, g, 0, alturaDaLinha);
                int larguraDoNome = desenhaNome(g, icone.getIconWidth(), alturaDaLinha);
                int totalDeColunas = ((ItemDaListaParaMatriz) itemDaLista).getColunas();
                int totalDeLinhas = ((ItemDaListaParaMatriz) itemDaLista).getLinhas();
                int margemEsquerda = icone.getIconWidth() + larguraDoNome + MARGEM;
                int colunaInicial = calculaRolagemDasColunas(margemEsquerda);
                int linhaInicial = calculaRolagemDasLinhas();
                desenhaGrade(g, totalDeLinhas, totalDeColunas, colunaInicial, linhaInicial, margemEsquerda);
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
            } while (indiceDaLinha <= ultimaLinhaAtualizada + 1 && indiceDaLinha < totalDeLinhas);
            if (ultimaLinhaAtualizada >= totalDeLinhas - 1) {//se é a última linha
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
            } while (indiceDaColuna <= ultimaColunaAtualizada + 1 && indiceDaColuna < totalDeColunas);
            if (ultimaColunaAtualizada >= totalDeColunas - 1) {//se é a última coluna
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

                int larguraDoValor = MARGEM + metricsDoValor.stringWidth(stringDoValor) + MARGEM;
                int larguraDoIndice = MARGEM + metricsDoIndice.stringWidth(stringDoIndice) + MARGEM;
                int larguraDaStringVazia = MARGEM + metricsDoValor.stringWidth(RenderizadorBase.STRING_VAZIA) + MARGEM; //a largura retornada nunca será menor que a largura da string vazia
                int max = Math.max(Math.max(larguraDaStringVazia, larguraDoIndice), larguraDoValor);
                if (max > maiorLargura) {
                    maiorLargura = max;
                }
            }
            return maiorLargura;//retorna a maior largura entre todas os valores de todas as linhas
        }

        private String getStringDoValor(int linha, int coluna) {
            ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
            Object valor = item.get(linha, coluna);
            return processaStringDoValor(valor);
        }

        private void desenhaGrade(Graphics g, int totalDeLinhas, int totalDeColunas, int colunaInicial, int linhaInicial, int margemEsquerda) {
            int alturaDaLinha = getFontMetrics(FONTE_NORMAL).getHeight();
            int larguraMaximaDoIndiceDeLinha = MARGEM + getFontMetrics(FONTE_DESTAQUE).stringWidth(String.valueOf(totalDeLinhas - 1));//obtém a largura da string do maior índice de linha
            int inicioLinhaHorizontal = margemEsquerda + larguraMaximaDoIndiceDeLinha - 3;
            int xDaLinha = inicioLinhaHorizontal;
            int ultimaLinhaAlterada = ((ItemDaListaParaMatriz) itemDaLista).getUltimaLinhaAtualizada();
            int ultimaColunaAlterada = ((ItemDaListaParaMatriz) itemDaLista).getUltimaColunaAtualizada();
            for (int l = linhaInicial; l < totalDeLinhas; l++) {
                int yDaLinha = ((l - linhaInicial) + 1) * alturaDaLinha;
                if (yDaLinha > getHeight()) {//se a linha não estará vísivel
                    break;
                }
                xDaLinha = inicioLinhaHorizontal;
                boolean podeDestacarEstaCelula = false;
                for (int c = colunaInicial; c < totalDeColunas; c++) {
                    ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
                    podeDestacarEstaCelula = item.podeDesenharDestaque() && ultimaColunaAlterada == c && ultimaLinhaAlterada == l;

                    int larguraDaColuna = getLarguraDaColuna(c);

                    //pinta a linha e a coluna que contém a última célula alterada para ajudar a indentificar visualmente a alteração
                    if (itemDaLista.podeDesenharDestaque() && (l == ultimaLinhaAlterada || c == ultimaColunaAlterada)) {
                        if (l == ultimaLinhaAlterada && c == ultimaColunaAlterada) {//se é exatamente a ultima célula alterada usa uma cor mais forte no fundo
                            g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
                        } else {
                            g.setColor(COR_DA_LINHA_E_COLUNA_EM_DESTAQUE);
                        }
                        g.fillRect(xDaLinha + 1, yDaLinha + 1, larguraDaColuna - 1, alturaDaLinha - 1);
                    }

                    //desenha o valor da célula
                    String stringDoValor = getStringDoValor(l, c);
                    g.setFont(podeDestacarEstaCelula ? FONTE_DESTAQUE : FONTE_NORMAL);
                    FontMetrics metrics = g.getFontMetrics();
                    int xDoValor = xDaLinha + larguraDaColuna / 2 - metrics.stringWidth(stringDoValor) / 2;
                    int yDoValor = alturaDaLinha + (alturaDaLinha * (l - linhaInicial)) + metrics.getAscent() + metrics.getDescent() - 3;
                    g.setColor(podeDestacarEstaCelula ? COR_DO_TEXTO_DESTACADO : COR_DO_TEXTO);
                    g.drawString(stringDoValor, xDoValor, yDoValor);

                    //linha vertical
                    g.setColor(COR_DA_GRADE);
                    g.drawLine(xDaLinha, alturaDaLinha, xDaLinha, getHeight());

                    //desenha os índices
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
                    g.drawString(stringIndiceDaColuna, xDaLinha + larguraDaColuna / 2 - larguraDoIndiceDeColuna / 2, 13);//desenha índice 

                    xDaLinha += larguraDaColuna;

                    if (xDaLinha > getWidth()) {
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
                        alturaDaLinha + (alturaDaLinha * (l - linhaInicial)) + metrics.getAscent() + metrics.getDescent());

                //desenha a linha horizontal
                g.setColor(COR_DA_GRADE);
                g.drawLine(inicioLinhaHorizontal, yDaLinha, xDaLinha, yDaLinha);//linha horizontal
            }

            //desenha a borda direita
            g.setColor(COR_DA_GRADE);
            g.drawLine(xDaLinha, alturaDaLinha, xDaLinha, getHeight());

            //desenha a borda embaixo
            g.drawLine(inicioLinhaHorizontal, getHeight() - 1, xDaLinha, getHeight() - 1);
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
            return metrics.getHeight() * 2;//2 linhas, uma com os valores e outra com os índices
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (itemDaLista != null) {
                Icon icone = itemDaLista.getIcone();
                icone.paintIcon(this, g, 0, g.getFontMetrics().getHeight());
                int larguraDoNome = desenhaNome(g, icone.getIconWidth(), getHeight() / 2);
                int totalDeColunas = ((ItemDaListaParaVetor) itemDaLista).getColunas();
                int margemEsquerda = icone.getIconWidth() + larguraDoNome + MARGEM;
                int colunaInicial = calculaRolagem(margemEsquerda);
                desenhaGrade(g, totalDeColunas, colunaInicial, margemEsquerda);
            }
        }

        private int calculaRolagem(int margemEsquerda) {
            ItemDaListaParaVetor item = ((ItemDaListaParaVetor) itemDaLista);
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
            } while (indiceDaColuna <= ultimaColunaAtualizada + 1 && indiceDaColuna < totalDeColunas);
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
            int larguraDoValor = MARGEM + metricsDoValor.stringWidth(stringDoValor) + MARGEM;
            int larguraDoIndice = MARGEM + metricsDoIndice.stringWidth(stringDoIndice) + MARGEM;
            int larguraDaStringVazia = MARGEM + metricsDoValor.stringWidth(RenderizadorBase.STRING_VAZIA) + MARGEM; //a largura retornada nunca será menor que a largura da string vazia
            return Math.max(Math.max(larguraDaStringVazia, larguraDoIndice), larguraDoValor);
        }

        private String getStringDoValor(int indice) {
            ItemDaListaParaVetor item = ((ItemDaListaParaVetor) itemDaLista);
            return processaStringDoValor(item.get(indice));
        }

        private void desenhaGrade(Graphics g, int totalDeColunas, int colunaInicial, int margemEsquerda) {
            int alturaDaLinha = getHeight() / 2;
            int inicioLinhaHorizontal = margemEsquerda;
            int xDaLinha = inicioLinhaHorizontal;
            int yDaLinha = alturaDaLinha;
            xDaLinha = margemEsquerda;
            for (int c = colunaInicial; c < totalDeColunas; c++) {
                ItemDaListaParaVetor item = ((ItemDaListaParaVetor) itemDaLista);
                boolean podeDestacarEstaColuna = itemDaLista.podeDesenharDestaque() && item.getUltimaColunaAtualizada() == c;
                int larguraDaColuna = getLarguraDaColuna(c);

                //pinta o fundo da coluna que foi alterada por último
                if (podeDestacarEstaColuna) {
                    g.setColor(COR_DO_FUNDO_EM_DESTAQUE);
                    g.fillRect(xDaLinha + 1, yDaLinha + 1, larguraDaColuna - 1, alturaDaLinha - 2);
                }

                //desenha o valor da coluna
                String stringDoValor = getStringDoValor(c);
                g.setFont(podeDestacarEstaColuna ? FONTE_DESTAQUE : FONTE_NORMAL);
                FontMetrics metrics = g.getFontMetrics();
                int xDoValor = xDaLinha + larguraDaColuna / 2 - metrics.stringWidth(stringDoValor) / 2;
                int yDoValor = MARGEM + alturaDaLinha * 2 - metrics.getAscent() + metrics.getDescent() + 2;
                g.setColor(podeDestacarEstaColuna ? COR_DO_TEXTO_DESTACADO : COR_DO_TEXTO);
                g.drawString(stringDoValor, xDoValor, yDoValor);

                //linha vertical
                g.setColor(COR_DA_GRADE);
                g.drawLine(xDaLinha, (c > 0) ? (alturaDaLinha - 3) : alturaDaLinha, xDaLinha, getHeight());

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
                g.drawString(stringDoIndice, xDaLinha + larguraDaColuna / 2 - larguraDoIndice / 2, 13);//desenha índice 

                xDaLinha += larguraDaColuna;
            }

            //desenha a linha horizontal
            g.setColor(COR_DA_GRADE);
            g.drawLine(inicioLinhaHorizontal, yDaLinha, xDaLinha, yDaLinha);//linha horizontal

            //desenha a borda direita
            g.setColor(COR_DA_GRADE);
            g.drawLine(xDaLinha, alturaDaLinha, xDaLinha, getHeight());

            //desenha a borda embaixo
            g.drawLine(inicioLinhaHorizontal, getHeight() - 1, xDaLinha, getHeight() - 1);
        }

    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class RenderizadorDaLista implements ListCellRenderer<ItemDaLista> {

        private final JPanel panel = new JPanel(new BorderLayout());

        public RenderizadorDaLista() {
            panel.setBorder(EMPTY_BORDER);
            UIDefaults defaults = javax.swing.UIManager.getDefaults();
            panel.setBackground(defaults.getColor("List.selectionBackground"));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ItemDaLista> list, ItemDaLista item, int index, boolean selected, boolean hasFocus) {
            JComponent c = (JComponent) item.getRendererComponent();
            c.setOpaque(false);

            panel.removeAll();
            panel.add(c, BorderLayout.CENTER); //o componente que renderiza o item da lista foi inserido em um painel e este painel usa uma EmptyBorder
            //para separar verticalmente os items da lista, assim os items não ficam muito "grudados" uns nos outros.

            if (hasFocus) {
                panel.setOpaque(true);

            } else {
                panel.setOpaque(false);
            }
            return panel;
            //existem 3 tipos de ItemDaLista (para variáveis, para vetores e para matrizes)
            //cada subclasse de ItemDaLista retorna um renderer component diferente.
        }
    }

    private boolean mesmoNo(NoDeclaracao no1, NoDeclaracao no2) {
        return COMPARADOR_NOS.compare(no1, no2) > 0;
    }

    private boolean simboloEhPermitido(Simbolo simbolo) {
        return simbolo != null
                && ((simbolo instanceof Variavel && simbolo.getOrigemDoSimbolo() instanceof NoDeclaracaoVariavel)
                || (simbolo instanceof Vetor && simbolo.getOrigemDoSimbolo() instanceof NoDeclaracaoVetor)
                || (simbolo instanceof Matriz && simbolo.getOrigemDoSimbolo() instanceof NoDeclaracaoMatriz));
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
                        }
                    }
                }
            });
        }
    }

    @Override
    public void simboloDeclarado(Simbolo simbolo) {
        if (simbolo instanceof Variavel) {
            List<Simbolo> lista = new ArrayList<Simbolo>();
            lista.add(simbolo);
            simbolosAlterados(lista);
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
                        ((ItemDaListaParaVariavel) itemDaLista).setValor(((Variavel) simbolo).getValor());
                    } else if (itemDaLista.ehMatriz()) {
                        Matriz matriz = (Matriz) simbolo;
                        int linha = matriz.getUltimaLinhaModificada();
                        int coluna = matriz.getUltimaColunaModificada();
                        Object valor = matriz.getValor(linha, coluna);
                        ((ItemDaListaParaMatriz) itemDaLista).set(valor, linha, coluna);
                    } else {
                        Vetor vetor = (Vetor) simbolo;
                        int indice = vetor.getUltimoIndiceModificado();
                        Object valor = vetor.getValor(indice);
                        ((ItemDaListaParaVetor) itemDaLista).set(valor, indice);
                    }
                    itemsAlterados = true;
                }
            }
        }
        if (itemsAlterados) {
            //desenha apenas as regiões dos items que podem ser repintados. Os itens são repintados apenas umas poucas vezes por segundo para
            //evitar problemas de desempenho quando o usuário estiver inspecionados variáveis que são alteradas várias vezes por segundo em um jogo, por exemplo
            redenhaItemsDaLista();
            //repaint();
        }
    }

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
                        bounds.translate(0, offset);//desloca o retângulo para a posição onde o item está na lista
                        repaint(bounds);
                        item.guardaTempoDaUltimaPintura();
                    }
                    offset += yOriginal + bounds.height + insets.bottom;
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
                    int coluna = textArea.getSelectionStart() - textArea.getLineStartOffset(linha-1);
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
            if (arrastandoNosDaJTree) {
                return importaNosArrastadosDaJTree(support);
            }
            return importaStringArrastada(support);
        }
    }

    private boolean adicionaNoVariavel(NoDeclaracaoVariavel noTransferido) {
        ItemDaListaParaVariavel item = new ItemDaListaParaVariavel((NoDeclaracaoVariavel) noTransferido);
        model.addElement(item);
//        if(noTransferido.getInicializacao() != null){
//            if(noTransferido.getInicializacao() instanceof NoValor){
//                //item.setValor(((NoValor)noTransferido.getInicializacao()).getValor());
//            }
//        }
        return true;
    }

    private boolean adicionaNoVetor(NoDeclaracaoVetor noTransferido) {
        NoDeclaracaoVetor declaracaoVetor = noTransferido;
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

    public void adicionaNo(NoDeclaracao noTransferido) {
        boolean simboloInserido = false;
        if (noTransferido instanceof NoDeclaracaoVariavel) {
            simboloInserido = adicionaNoVariavel((NoDeclaracaoVariavel) noTransferido);
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
                    final ArvoreSintaticaAbstrataPrograma ast = ultimoProgramaCompilado.getArvoreSintaticaAbstrata();
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            //model.clear();

                            try {
                                final List<NoDeclaracao> nosInspecionados = new ArrayList<>();
                                ast.aceitar(new VisitanteNulo() {

                                    @Override
                                    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
                                        if (contemNo(noDeclaracaoVariavel)) {
                                            nosInspecionados.add(noDeclaracaoVariavel);
                                        }

                                        return null;
                                    }

                                    @Override
                                    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA {
                                        if (contemNo(noDeclaracaoMatriz)) {
                                            nosInspecionados.add(noDeclaracaoMatriz);
                                        }
                                        return null;
                                    }

                                    @Override
                                    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA {
                                        if (contemNo(noDeclaracaoVetor)) {
                                            nosInspecionados.add(noDeclaracaoVetor);
                                        }
                                        return null;
                                    }

                                });
                                model.clear();
                                for (NoDeclaracao no : nosInspecionados) {
                                    adicionaNo(no);
                                }
                            } catch (Exception e) {
                                //Logger.getLogger(ListaDeNosInspecionados.class.getName()).log(Level.WARNING, null, e);
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

        ListaDeNosInspecionados lista = new ListaDeNosInspecionados();

        ItemDaListaParaVariavel item = new ItemDaListaParaVariavel(new NoDeclaracaoVariavel("variavel", TipoDado.INTEIRO, false));
        item.setValor(53);
        lista.model.addElement(item);
        lista.model.addElement(new ItemDaListaParaVariavel(new NoDeclaracaoVariavel("outra variável", TipoDado.LOGICO, false)));
        ItemDaListaParaVetor itemVetor = new ItemDaListaParaVetor(15, new NoDeclaracaoVetor("teste", TipoDado.INTEIRO, new NoInteiro(15), false));
        itemVetor.set(34, 12);
        lista.model.addElement(itemVetor);
        lista.model.addElement(new ItemDaListaParaVetor(5, new NoDeclaracaoVetor("outro vetor", TipoDado.REAL, new NoInteiro(3), false)));
        ItemDaListaParaMatriz itemMatriz = new ItemDaListaParaMatriz(13, 13, new NoDeclaracaoMatriz("teste 2", TipoDado.INTEIRO, new NoInteiro(13), new NoInteiro(13), false));
        lista.model.addElement(itemMatriz);
        itemMatriz.set(345, 12, 12);
        lista.setPreferredSize(new Dimension(300, 600));

        itemVetor.set(34, 14);

        lista.redenhaItemsDaLista();

        frame.add(lista, BorderLayout.CENTER);
        frame.pack();
    }
}
