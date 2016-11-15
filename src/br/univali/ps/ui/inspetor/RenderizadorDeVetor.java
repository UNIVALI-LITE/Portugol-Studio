package br.univali.ps.ui.inspetor;

import static br.univali.ps.ui.inspetor.RenderizadorBase.FONTE_NORMAL;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.Icon;

/**
 *
 * @author elieser
 */
class RenderizadorDeVetor extends RenderizadorBase {

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
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
            Icon icone = itemDaLista.getIcone();
            FontMetrics metrics = g.getFontMetrics(FONTE_NORMAL);
            int yDoIcone = 1 + metrics.getHeight() / 2 - icone.getIconHeight() / 2;
            int x = MARGEM_HORIZONTAL;
            icone.paintIcon(this, g, x, yDoIcone);
            g.setColor(corTexto);

            x += icone.getIconWidth() + MARGEM_HORIZONTAL;
            desenhaNome(g, x, 0);
            
            int totalDeColunas = ((ItemDaListaParaVetor) itemDaLista).getColunas();
            int margemEsquerda = MARGEM_HORIZONTAL * 2;
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
        int larguraUtilDoComponente = getWidth() - MARGEM_HORIZONTAL*2;
        int indiceDaColuna = 0;
        do {
            xDaColuna += getLarguraDaColuna(indiceDaColuna++);
            if (xDaColuna > larguraUtilDoComponente) {
                rolagem++;
            }
        } while (indiceDaColuna <= ultimaColunaAtualizada && indiceDaColuna < totalDeColunas);
        return rolagem;
    }

    /**
     * *
     * @param indiceDaColuna
     * @return calcula a largura da string que representa o valor da coluna e a
     * largura do índice da coluna. Retorna a maior largura encontrada.
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
            g.setColor(podeDestacarEstaColuna ? corTextoDestacado : corTexto);
            g.drawString(stringDoValor, xDoValor, yDoValor);

            //desenha a string do índice
            String stringDoIndice = String.valueOf(c);
            if (podeDestacarEstaColuna) {
                g.setFont(FONTE_CABECALHO_DESTAQUE);
                g.setColor(COR_DO_CABECALHO_DESTACADO);
            } else {
                g.setFont(FONTE_CABECALHO);
                g.setColor(corGrade);
            }
            int larguraDoIndice = g.getFontMetrics().stringWidth(stringDoIndice);
            g.drawString(stringDoIndice, xDaLinha + larguraDaColuna / 2 - larguraDoIndice / 2, yDaLinha - 2);//desenha índice 
            //}

                //linha vertical - não desenha a primeira linha vertical quando a primeira
            //coluna que será desenhada não é a primeira coluna do vetor
            g.setColor(corGrade);
            if (!ehPrimeiraColunaComRolagem) {
                g.drawLine(xDaLinha, yDaLinha + 1, xDaLinha, yDaLinha + alturaDaLinha - 1);
            }

            //desenha a linha horizontal
            g.setColor(corGrade);
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
