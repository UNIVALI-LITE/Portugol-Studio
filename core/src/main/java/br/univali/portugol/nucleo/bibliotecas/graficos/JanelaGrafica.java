package br.univali.portugol.nucleo.bibliotecas.graficos;

import br.univali.portugol.nucleo.bibliotecas.Mouse;
import br.univali.portugol.nucleo.bibliotecas.Teclado;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import java.awt.Image;

/**
 *
 * @author Luiz Fernando Noschang
 */
public interface JanelaGrafica extends Mouse.InstaladorMouse, Teclado.InstaladorTeclado
{
    public void exibir(boolean manterVisivel) throws ErroExecucaoBiblioteca;

    public void ocultar() throws ErroExecucaoBiblioteca;

    public void definirTitulo(String titulo) throws ErroExecucaoBiblioteca;

    public void definirIcone(Image icone) throws ErroExecucaoBiblioteca;

    public void minimizar() throws ErroExecucaoBiblioteca;

    public void restaurar() throws ErroExecucaoBiblioteca;

    public void ocultarBorda() throws ErroExecucaoBiblioteca;

    public void exibirBorda() throws ErroExecucaoBiblioteca;

    public void definirDimensoes(int largura, int altura) throws ErroExecucaoBiblioteca;
    
    public SuperficieDesenho getSuperficieDesenho();
    
    public boolean estaVisivel();
    
    public void fechar();
    
    public int getLargura() throws ErroExecucaoBiblioteca;
    
    public int getAltura() throws ErroExecucaoBiblioteca;
    
    public void entrarModoTelaCheia() throws ErroExecucaoBiblioteca;
    
    public void sairModoTelaCheia() throws ErroExecucaoBiblioteca;
}
