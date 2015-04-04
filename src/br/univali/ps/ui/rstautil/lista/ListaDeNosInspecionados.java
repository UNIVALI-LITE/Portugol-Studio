package br.univali.ps.ui.rstautil.lista;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.rstautil.ComparadorNos;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
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

    private static final String INSTRUCAO = "Arraste uma das variáveis acima \n para este painél se quiser inspecioná-la";
    private DefaultListModel<ItemDaLista> model = new DefaultListModel<>();
    private static final ComparadorNos COMPARADOR_NOS = new ComparadorNos();

    //renderizadores
    private static final RenderizadorDeVetor RENDERIZADOR_DE_VETOR = new RenderizadorDeVetor();
    private static final RenderizadorDeVariavel RENDERIZADOR_DE_VARIAVEL = new RenderizadorDeVariavel();
    private static final RenderizadorDeMatriz RENDERIZADOR_DE_MATRIZ = new RenderizadorDeMatriz();

    protected static ItemDaLista ultimoItemModificado = null;

    boolean programaExecutando = false;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().getSize() <= 0) {
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

    public static abstract class ItemDaLista {

        protected final NoDeclaracao noDeclaracao;

        public ItemDaLista(NoDeclaracao no) {
            this.noDeclaracao = no;
        }

        abstract Component getRendererComponent();

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

        public boolean ehUltimoItemAtualizado() {
            return this == ultimoItemModificado;
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
        private static RenderizadorDeVariavel rendererComponent = RENDERIZADOR_DE_VARIAVEL;

        public ItemDaListaParaVariavel(NoDeclaracaoVariavel no) {
            super(no);
        }

        @Override
        Component getRendererComponent() {
            rendererComponent.setItemDaLista(this);
            return rendererComponent;
        }

        void setValor(Object valor) {
            this.valor = valor;
            ultimoItemModificado = this;
        }

        public String getValor() {
            if (valor == null) {
                return RenderizadorBase.STRING_VAZIA;
            }
            if (valor instanceof Boolean) {
                valor = ((Boolean) valor) ? "verdadeiro" : "falso";
            }
            return valor.toString();
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
        Component getRendererComponent() {
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
//            larguraDasColunas = new int[colunas];
//            for (int i = 0; i < colunas; i++) {
//                larguraDasColunas[i] = 20;
//            }
        }

//        int getLarguraDaColuna(int indiceDaColuna){
//            if(indiceDaColuna >= 0 && indiceDaColuna < larguraDasColunas.length){
//                return larguraDasColunas[indiceDaColuna];
//            }
//            return 20;
//        }
//        
//        void setLarguraDaColuna(int indiceDaColuna, int largura){
//            if(indiceDaColuna >= 0 && indiceDaColuna < larguraDasColunas.length){
//                if(largura > larguraDasColunas[indiceDaColuna]){
//                    larguraDasColunas[indiceDaColuna] = largura;
//                }
//            }
//        }
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
        Component getRendererComponent() {
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

        //protected Font fonteCabecalho = null;
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

            g.setFont(itemDaLista.ehUltimoItemAtualizado() ? FONTE_DESTAQUE : FONTE_NORMAL);
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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (isFocusOwner()) {
                g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
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
        protected void paintComponent(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            super.paintComponent(g);
            if (itemDaLista == null) {
                return;
            }
            Icon icone = itemDaLista.getIcone();
            icone.paintIcon(this, g, 0, MARGEM);
            int larguraDoNome = desenhaNome(g, icone.getIconWidth(), MARGEM);

            //desenha valor
            String stringDoValor = ((ItemDaListaParaVariavel) itemDaLista).getValor();
            if (stringDoValor != null) {
                g.setFont(itemDaLista.ehUltimoItemAtualizado() ? FONTE_DESTAQUE : FONTE_NORMAL);
                FontMetrics metrics = g.getFontMetrics();
                int larguraDoValor = metrics.stringWidth(stringDoValor);
                int larguraDaCaixa = MARGEM + larguraDoValor + MARGEM;

                //pinta fundo de vermelho para destacar
                if (itemDaLista.ehUltimoItemAtualizado()) {
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
                if (item.ehUltimoItemAtualizado() && item.getUltimaColunaAtualizada() == indiceDaColuna && item.getUltimaLinhaAtualizada() == linha) {
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
            return (item.get(linha, coluna) != null) ? item.get(linha, coluna).toString() : RenderizadorBase.STRING_VAZIA;
            //retorna uma string em branco com 4 espaços para representar o valor nulo, assim as células do vetor tem uma largura mesmo quando não existe valor para exibir
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
                xDaLinha = inicioLinhaHorizontal;
                boolean podeDestacarEstaCelula = false;
                for (int c = colunaInicial; c < totalDeColunas; c++) {
                    ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
                    podeDestacarEstaCelula = item.ehUltimoItemAtualizado() && ultimaColunaAlterada == c && ultimaLinhaAlterada == l;

                    int larguraDaColuna = getLarguraDaColuna(c);

                    //pinta a linha e a coluna que contém a última célula alterada para ajudar a indentificar visualmente a alteração
                    if (itemDaLista.ehUltimoItemAtualizado() && (l == ultimaLinhaAlterada || c == ultimaColunaAlterada)) {
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
                    if (itemDaLista.ehUltimoItemAtualizado() && c == ultimaColunaAlterada) {
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
                }

                //desenha índice da linha
                String stringIndiceDaLinha = String.valueOf(l);
                if (itemDaLista.ehUltimoItemAtualizado() && l == ultimaLinhaAlterada) {
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
            if (item.ehUltimoItemAtualizado() && item.getUltimaColunaAtualizada() == indiceDaColuna) {
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
            return (item.get(indice) != null) ? item.get(indice).toString() : RenderizadorBase.STRING_VAZIA;
            //retorna uma string em branco com 4 espaços para representar o valor nulo, assim as células do vetor tem uma largura mesmo quando não existe valor para exibir
        }

        private void desenhaGrade(Graphics g, int totalDeColunas, int colunaInicial, int margemEsquerda) {
            int alturaDaLinha = getHeight() / 2;
            int inicioLinhaHorizontal = margemEsquerda;
            int xDaLinha = inicioLinhaHorizontal;
            int yDaLinha = alturaDaLinha;
            xDaLinha = margemEsquerda;
            for (int c = colunaInicial; c < totalDeColunas; c++) {
                ItemDaListaParaVetor item = ((ItemDaListaParaVetor) itemDaLista);
                boolean podeDestacarEstaColuna = itemDaLista.ehUltimoItemAtualizado() && item.getUltimaColunaAtualizada() == c;
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
        private final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(5, 0, 5, 0);

        public RenderizadorDaLista() {
            panel.setBorder(EMPTY_BORDER);
            UIDefaults defaults = javax.swing.UIManager.getDefaults();
            panel.setBackground(defaults.getColor("List.selectionBackground"));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends ItemDaLista> list, ItemDaLista item, int index, boolean selected, boolean hasFocus) {
            JComponent c = (JComponent)item.getRendererComponent();
            c.setOpaque(false);
            
            panel.removeAll();
            panel.add(c, BorderLayout.CENTER);
            
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
            repaint();
        }
    }

    private boolean contemNo(NoDeclaracao no) {
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
    }

    @Override
    public void highlightLinha(int linha) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public
            void highlightDetalhadoAtual(int linha, int coluna, int tamanho) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private class TratadorDeArrastamento extends TransferHandler {

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            List<NoDeclaracao> nosTransferidos = null;
            try {
                nosTransferidos = (List<NoDeclaracao>) support.getTransferable().getTransferData(AbaCodigoFonte.NoTransferable.NO_DATA_FLAVOR);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            if (!support.isDrop()) {
                return false;
            }

            for (NoDeclaracao no : nosTransferidos) {
                if (!contemNo(no)) {
                    support.setShowDropLocation(true);
                    return true;//basta que um dos nós transferidos ainda não esteja no inspetor e deve ser possível adicionar este nó na lista
                }
            }
            return false;
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

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
                    adicionaNoModelDaLista(noTransferido);
                    importou = true;
                }
            }
            return importou;
        }
    }

    private void adicionaNoModelDaLista(NoDeclaracao noTransferido) {
        if (noTransferido instanceof NoDeclaracaoVariavel) {
            model.addElement(new ItemDaListaParaVariavel((NoDeclaracaoVariavel) noTransferido));
        } else if (noTransferido instanceof NoDeclaracaoVetor) {
            NoDeclaracaoVetor declaracaoVetor = ((NoDeclaracaoVetor) noTransferido);
            if (declaracaoVetor.getTamanho() != null) {
                int colunas = ((NoInteiro) declaracaoVetor.getTamanho()).getValor();
                model.addElement(new ItemDaListaParaVetor(colunas, declaracaoVetor));
            }
        } else if (noTransferido instanceof NoDeclaracaoMatriz) {
            NoDeclaracaoMatriz declaracaoMatriz = ((NoDeclaracaoMatriz) noTransferido);
            if (((NoInteiro) declaracaoMatriz.getNumeroColunas()) != null && ((NoInteiro) declaracaoMatriz.getNumeroLinhas()) != null) {
                int colunas = ((NoInteiro) declaracaoMatriz.getNumeroColunas()).getValor();
                int linhas = ((NoInteiro) declaracaoMatriz.getNumeroLinhas()).getValor();
                model.addElement(new ItemDaListaParaMatriz(linhas, colunas, declaracaoMatriz));
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
                if (model.isEmpty()) {
                    return;//não existem símbolos sendo inspecionados
                }
                String name = pce.getPropertyName();
                if (RSyntaxTextArea.SYNTAX_STYLE_PROPERTY.equals(name) || PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO.equals(name)) {
                    Programa programa = (Programa) pce.getNewValue();
                    final ArvoreSintaticaAbstrataPrograma ast = programa.getArvoreSintaticaAbstrata();
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
                                    adicionaNoModelDaLista(no);
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

        frame.add(lista, BorderLayout.CENTER);
        frame.pack();
    }

}
