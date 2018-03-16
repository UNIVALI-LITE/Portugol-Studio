package br.univali.portugol.nucleo.bibliotecas.graficos;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

/**
 *
 * @author Luiz Fernando Noschang
 */
public interface SuperficieDesenho
{
    public static final class InformacaoGradiente
    {
        public Color cor1;
        public Color cor2;
        public int tipo;
    }
    
    public void redimensionar(int largura, int altura);

    public void definirOpacidade(int opacidade);

    public void limpar() throws ErroExecucaoBiblioteca;

    public void desenharRetangulo(int x, int y, int largura, int altura, boolean arredondarCantos, boolean preencher);

    public void desenharElipse(int x, int y, int largura, int altura, boolean preencher);

    public void desenharLinha(int x1, int y1, int x2, int y2);

    public void desenharTexto(String texto, int x, int y);

    public void desenharPoligono(int[][] pontos, boolean preencher);

    public void desenharPonto(int x, int y);

    public void renderizar() throws ErroExecucaoBiblioteca;

    public void definirCor(int cor);
    
    public void definirGradiente(int tipo, int cor, int cor2);

    public void definirFonteTexto(String fonte) throws ErroExecucaoBiblioteca;
    
    public void registrarFonteCarregada(Font fonte);

    public void definirTamanhoTexto(double tamanho);

    public void definirEstiloTexto(boolean italico, boolean negrito, boolean sublinhado) throws ErroExecucaoBiblioteca;

    public void desenharImagem(int x, int y, BufferedImage imagem);

    public void desenharPorcaoImagem(int x, int y, int xi, int yi, int largura, int altura, BufferedImage imagem);

    public int alturaTexto(String texto) throws ErroExecucaoBiblioteca;

    public int larguraTexto(String texto) throws ErroExecucaoBiblioteca;

    public void definirRotacao(int graus);

    public void instalarMouse(MouseAdapter observadorMouse);
    
    public BufferedImage renderizarImagem(int largura, int altura);
    
    public void registrarGradiente(int tipo, int cor1, int cor2);
    
    public InformacaoGradiente getInformacaoGradiente();
    
    public void removerGradiente();
}
