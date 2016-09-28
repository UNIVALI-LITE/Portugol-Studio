package br.univali.ps.ui.inspetor;

import static br.univali.ps.ui.inspetor.RenderizadorBase.FONTE_NORMAL;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.Icon;

/**
 *
 * @author elieser
 */
class RenderizadorDeMatriz extends RenderizadorBase {

    private static final Color COR_DA_LINHA_E_COLUNA_EM_DESTAQUE = new Color(0, 0, 0, 0.045f);

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
        return alturaDoNome + MARGEM + alturaDoCabecalho + (metrics.getHeight() * linhasDeConteudo);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (itemDaLista != null) {
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            Icon icone = itemDaLista.getIcone();
            FontMetrics metrics = g.getFontMetrics(FONTE_NORMAL);

            int yDoIcone = 1 + metrics.getHeight() / 2 - icone.getIconHeight() / 2;
            int x = MARGEM;
            icone.paintIcon(this, g, x, yDoIcone);

            g.setColor( itemDaLista.podeDesenharDestaque() ? COR_TEXTO_DESTACADO : COR_NOME);
            x += icone.getIconWidth() + MARGEM;
            int larguraNome = desenhaNome(g, x, 0);
            
            //desenha dimensões da matriz
            x += larguraNome + MARGEM;
            String stringDimensoes = ((ItemDaListaParaMatriz)itemDaLista).getStringDimensoes();
            g.setFont(FONTE_CABECALHO);
            g.setColor(COR_NOME);
            g.drawString(stringDimensoes, x, metrics.getAscent());
            
            int totalDeColunas = ((ItemDaListaParaMatriz) itemDaLista).getColunas();
            int totalDeLinhas = ((ItemDaListaParaMatriz) itemDaLista).getLinhas();
            int margemEsquerda = MARGEM * 2;
            int margemSuperior = getFontMetrics(FONTE_CABECALHO).getAscent() + MARGEM;
            int colunaInicial = calculaRolagemDasColunas(margemEsquerda);
            int linhaInicial = calculaRolagemDasLinhas();

            desenhaGrade(g, totalDeLinhas, totalDeColunas, colunaInicial, linhaInicial, margemEsquerda, margemSuperior);
        }
    }

    private int calculaRolagemDasLinhas() {
        ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
        int totalDeLinhas = item.getLinhas();
        int alturaDaLinha = getFontMetrics(FONTE_NORMAL).getHeight();
        int yDaLinha = alturaDaLinha + getAlturaDoCabecalho();// já inicia do cabeçalho em diante
        int ultimaLinhaAtualizada = item.getUltimaLinhaAtualizada();
        int rolavemVertical = 0;//conta quantas células é preciso deslocar para que a última célula atualizada fique visível no componente
        Insets insets = getInsets();
        int alturaDoComponente = getHeight() - (insets.top + insets.bottom);
        int indiceDaLinha = 1;//pula o cabeçalho
        do {
            yDaLinha += alturaDaLinha;
            if (yDaLinha > alturaDoComponente) {
                rolavemVertical++;
            }
            indiceDaLinha++;
        } while (indiceDaLinha <= ultimaLinhaAtualizada && indiceDaLinha < totalDeLinhas);
        return rolavemVertical;
    }

    private int calculaRolagemDasColunas(int margemEsquerda) {
        ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
        int totalDeColunas = item.getColunas();
        int xDaColuna = margemEsquerda;
        int ultimaColunaAtualizada = item.getUltimaColunaAtualizada();
        int rolagem = 0;//conta quantas células é preciso deslocar para que a última célula atualizada fique visível no componente
        int larguraDoComponente = getWidth() - MARGEM*2;
        int indiceDaColuna = 0;
        do {
            xDaColuna += getLarguraDaColuna(indiceDaColuna++);
            if (xDaColuna > larguraDoComponente) {
                rolagem++;
            }
        } while (indiceDaColuna <= ultimaColunaAtualizada && indiceDaColuna < totalDeColunas);

        boolean precisaDeRolagem = xDaColuna > larguraDoComponente;
        if (precisaDeRolagem && ultimaColunaAtualizada >= totalDeColunas - 1) {//se é a última coluna
            rolagem++;
        }
        return rolagem;
    }

    /**
     * *
     * @param indiceDaColuna
     * @return calcula a largura da string que representa o valor da coluna e a
     * largura do índice da coluna. Retorna a maior largura encontrada.
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

    private void desenhaFundoDaCelula(Graphics g, int linha, int coluna, int xDaLinha, int yDaLinha, int larguraDaColuna, int alturaDaLinha) {
        ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
        if (item.podeDesenharDestaque()) {
            int ultimaLinhaAlterada = item.getUltimaLinhaAtualizada();
            int ultimaColunaAlterada = item.getUltimaColunaAtualizada();
            //se é exatamente a ultima célula alterada usa uma cor mais forte no fundo
            if (linha == ultimaLinhaAlterada && coluna == ultimaColunaAlterada) {
                g.setColor(COR_DO_FUNDO_EM_DESTAQUE);//pinta o fundo de vermelho
                g.fillRect(xDaLinha, yDaLinha, larguraDaColuna, alturaDaLinha);
            } else {
                //pinta a linha e a coluna que contém a última célula alterada para ajudar a indentificar visualmente a alteração
                //mas só destaca a linha e coluna alterada em matrizes grandes para evitar confusão visual
                boolean matrizGrande = item.getLinhas() > 3 && item.getColunas() > 3;
                boolean podeDestacarLinha = linha == ultimaLinhaAlterada && matrizGrande;
                boolean podeDestacarColuna = coluna == ultimaColunaAlterada && matrizGrande;
                if (podeDestacarLinha || podeDestacarColuna) {
                    g.setColor(COR_DA_LINHA_E_COLUNA_EM_DESTAQUE);//desenha o "caminho" até a celula destacada
                    g.fillRect(xDaLinha, yDaLinha, larguraDaColuna, alturaDaLinha);
                }

            }
        }
    }

    private void desenhaValorDaCelula(Graphics g, String stringDoValor, int linha, int xDaLinha, int larguraDaColuna, int alturaDoCabecalho, int alturaDaLinha, int linhaInicial, boolean podeDestacarEstaCelula) {
        //String stringDoValor = getStringDoValor(l, c);
        g.setFont(podeDestacarEstaCelula ? FONTE_DESTAQUE : FONTE_NORMAL);
        FontMetrics metrics = g.getFontMetrics();
        int xDoValor = xDaLinha + larguraDaColuna / 2 - metrics.stringWidth(stringDoValor) / 2;
        int yDoValor = alturaDoCabecalho + (alturaDaLinha * (linha - linhaInicial + 1)) + metrics.getAscent() - metrics.getDescent() + MARGEM;
        g.setColor(podeDestacarEstaCelula ? COR_TEXTO_DESTACADO : COR_TEXTO);
        g.drawString(stringDoValor, xDoValor, yDoValor);
    }

    private void desenhaLinhaVerticalDaCelula(Graphics g, int xDaLinha, int yDaLinha, int alturaDaLinha, boolean ehLinhaDeCimaComRolagem, boolean ehLinhaDeBaixoComRolagem, boolean ehPrimeiraColunaComRolagem) {
        g.setColor(COR_GRADE);
        Stroke tracejadoPadrao = ((Graphics2D) g).getStroke();

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
    }

    private void desenhaLinhaHorizontalDaCelula(Graphics g, int xDaLinha, int yDaLinha, int larguraDaColuna, boolean ehLinhaDeCimaComRolagem, boolean ehPrimeiraColunaComRolagem, boolean ehUltimaColunaComRolagem) {
        Stroke tracejadoPadrao = ((Graphics2D) g).getStroke();
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
    }

    private void desenhaIndiceDaColuna(Graphics g, int colunaAtual, int xDaLinha, int yDaLinha, int larguraDaColuna) {
        ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
        String stringIndiceDaColuna = String.valueOf(colunaAtual);
        if (itemDaLista.podeDesenharDestaque() && colunaAtual == item.getUltimaColunaAtualizada()) {
            g.setFont(FONTE_CABECALHO_DESTAQUE);
            g.setColor(COR_DO_CABECALHO_DESTACADO);
        } else {
            g.setFont(FONTE_CABECALHO);
            g.setColor(COR_GRADE);
        }
        int larguraDoIndiceDeColuna = g.getFontMetrics().stringWidth(stringIndiceDaColuna);
        //índice da coluna
        g.drawString(stringIndiceDaColuna, xDaLinha + larguraDaColuna / 2 - larguraDoIndiceDeColuna / 2, yDaLinha - 2);//desenha índice 
    }

    private void desenhaIndiceDaLinha(Graphics g, int linhaAtual, int linhaInicial, int inicioLinhaHorizontal, int margemSuperior, int alturaDaLinha) {
        ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
        String stringIndiceDaLinha = String.valueOf(linhaAtual);
        if (itemDaLista.podeDesenharDestaque() && linhaAtual == item.getUltimaLinhaAtualizada()) {
            g.setFont(FONTE_CABECALHO_DESTAQUE);
            g.setColor(COR_DO_CABECALHO_DESTACADO);
        } else {
            g.setFont(FONTE_CABECALHO);
            g.setColor(COR_GRADE);
        }
        FontMetrics metrics = g.getFontMetrics();
        int largura = metrics.stringWidth(stringIndiceDaLinha);
        int x = inicioLinhaHorizontal - largura - 2;
        int y = margemSuperior + alturaDaLinha + (alturaDaLinha * (linhaAtual - linhaInicial)) + metrics.getAscent() + metrics.getDescent();
        g.drawString(stringIndiceDaLinha, x, y );
    }

    private void desenhaBordaDireita(Graphics g, int linhaInicial, int totalDeLinhas, int xDaLinha, int alturaDaLinha, int margemSuperior, int indiceDaUltimaColunaDesenhada, int indiceDaUltimaLinhaDesenhada, int totalDeColunas ){
        g.setColor(COR_GRADE);
        if (indiceDaUltimaColunaDesenhada == totalDeColunas - 1) {
            int offsetSuperior = (linhaInicial > 0) ? alturaDaLinha : 0;
            int offsetInferior = (indiceDaUltimaLinhaDesenhada < totalDeLinhas - 1) ? alturaDaLinha : 0;
            g.drawLine(xDaLinha, alturaDaLinha + margemSuperior + offsetSuperior, xDaLinha, getHeight() - 1 - offsetInferior);
        }
    }
    
    private void desenhaBordaEmbaixo(Graphics g, int indiceDaUltimaLinhaDesenhada, int inicioLinhaHorizontal, int xDaLinha){
        if (indiceDaUltimaLinhaDesenhada == ((ItemDaListaParaMatriz) itemDaLista).getLinhas() - 1) {
            g.drawLine(inicioLinhaHorizontal, getHeight() - 1, xDaLinha, getHeight() - 1);
        }
    }
    
    private int getAlturaDoCabecalho() {
        return getFontMetrics(FONTE_CABECALHO).getHeight();
    }
    
    private void desenhaGrade(Graphics g, int totalDeLinhas, int totalDeColunas, int colunaInicial, int linhaInicial, int margemEsquerda, int margemSuperior) {
        int alturaDaLinha = getFontMetrics(FONTE_NORMAL).getHeight();
        int alturaDoCabecalho = getAlturaDoCabecalho();
        int larguraMaximaDoIndiceDeLinha = MARGEM + getFontMetrics(FONTE_DESTAQUE).stringWidth(String.valueOf(totalDeLinhas - 1));//obtém a largura da string do maior índice de linha
        int inicioLinhaHorizontal = margemEsquerda + larguraMaximaDoIndiceDeLinha - 3;
        int xDaLinha = inicioLinhaHorizontal;
        int ultimaLinhaAlterada = ((ItemDaListaParaMatriz) itemDaLista).getUltimaLinhaAtualizada();
        int ultimaColunaAlterada = ((ItemDaListaParaMatriz) itemDaLista).getUltimaColunaAtualizada();
        int indiceDaUltimaLinhaDesenhada = 0;
        int indiceDaUltimaColunaDesenhada = 0;
        for (int linhaAtual = linhaInicial; linhaAtual < totalDeLinhas; linhaAtual++) {
            int yDaLinha = ((linhaAtual - linhaInicial) + 1) * alturaDaLinha + margemSuperior;
            if (yDaLinha >= getHeight()) {//se a linha não estará vísivel
                break;
            }
            indiceDaUltimaLinhaDesenhada = linhaAtual;

            xDaLinha = inicioLinhaHorizontal;
            for (int colunaAtual = colunaInicial; colunaAtual < totalDeColunas; colunaAtual++) {
                ItemDaListaParaMatriz item = ((ItemDaListaParaMatriz) itemDaLista);
                boolean podeDestacarEstaCelula = item.podeDesenharDestaque() && ultimaColunaAlterada == colunaAtual && ultimaLinhaAlterada == linhaAtual;

                int larguraDaColuna = getLarguraDaColuna(colunaAtual);
                int larguraDaProximaColuna = (colunaAtual < totalDeColunas - 1) ? getLarguraDaColuna(colunaAtual + 1) : 0;
                indiceDaUltimaColunaDesenhada = colunaAtual;

                boolean ehLinhaDeBaixoComRolagem = yDaLinha + alturaDaLinha >= getHeight() && linhaAtual < totalDeLinhas - 1;
                boolean ehLinhaDeCimaComRolagem = linhaAtual == linhaInicial && linhaInicial > 0;
                boolean ehPrimeiraColunaComRolagem = colunaAtual == colunaInicial && colunaInicial > 0;
                boolean ehUltimaColunaComRolagem = xDaLinha + larguraDaColuna + larguraDaProximaColuna > getWidth();

                desenhaFundoDaCelula(g, linhaAtual, colunaAtual, xDaLinha, yDaLinha, larguraDaColuna, alturaDaLinha);
                desenhaValorDaCelula(g, getStringDoValor(linhaAtual, colunaAtual), linhaAtual, xDaLinha, larguraDaColuna, alturaDoCabecalho, alturaDaLinha, linhaInicial, podeDestacarEstaCelula);
                desenhaLinhaVerticalDaCelula(g, xDaLinha, yDaLinha, alturaDaLinha, ehLinhaDeCimaComRolagem, ehLinhaDeBaixoComRolagem, ehPrimeiraColunaComRolagem);
                desenhaLinhaHorizontalDaCelula(g, xDaLinha, yDaLinha, larguraDaColuna, ehLinhaDeCimaComRolagem, ehPrimeiraColunaComRolagem, ehUltimaColunaComRolagem);
                if (linhaAtual == linhaInicial) {//se é a primeira linha
                    desenhaIndiceDaColuna(g, colunaAtual, xDaLinha, yDaLinha, larguraDaColuna);
                }

                xDaLinha += larguraDaColuna;

                if (xDaLinha + larguraDaProximaColuna > getWidth()) {
                    break;//as próximas colunas não estarão vísiveis e não precisam ser desenhadas
                }
            }

            desenhaIndiceDaLinha(g, linhaAtual, linhaInicial, inicioLinhaHorizontal, margemSuperior, alturaDaLinha);
        }

        desenhaBordaDireita(g, linhaInicial, totalDeLinhas, xDaLinha, alturaDaLinha, margemSuperior, indiceDaUltimaColunaDesenhada, indiceDaUltimaLinhaDesenhada, totalDeColunas);
        desenhaBordaEmbaixo(g, indiceDaUltimaLinhaDesenhada, inicioLinhaHorizontal, xDaLinha);
    }

}
