/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas.objetos;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gabriel Schade
 */
public class CacheObjetos {


    private static final int NUMERO_MAXIMO_OBJETOS = 128;

    private final List<Objeto> objetos;

    private CacheObjetos()
    {
        objetos = new ArrayList<>();
    }

    public static CacheObjetos criar()
    {
        return new CacheObjetos();
    }

    private int obterProximoIndiceLivre() throws ErroExecucaoBiblioteca
    {   
        int size = objetos.size();
        return size >= 0 ? 
                size
                :lancarExcecaoLimiteObjetos();
    }

    private int lancarExcecaoLimiteObjetos() throws ErroExecucaoBiblioteca
    {
        throw new ErroExecucaoBiblioteca("O número máximo de objetos que podem ser criados foi atingido");
    }
   
    public Objeto obterObjeto(int endereco) throws ErroExecucaoBiblioteca
    {
        return endereco >= 0 && endereco < objetos.size() ?
                objetos.get(endereco)
                : lancarExcecaoEnderecoNaoApontaParaUmObjeto();
    }
    
    private Objeto lancarExcecaoEnderecoNaoApontaParaUmObjeto() throws ErroExecucaoBiblioteca
    {
        throw new ErroExecucaoBiblioteca("O endereço de memória especificado não aponta para um objeto");
    }

    public void liberar()
    {
        objetos.clear();
    }

    public void liberarObjeto(int endereco) throws ErroExecucaoBiblioteca 
    {
        if(endereco >= 0 && endereco < objetos.size() )
            objetos.remove(endereco);
        else
            lancarExcecaoEnderecoNaoApontaParaUmObjeto();
    }

    public int criarObjeto(Objeto objeto) throws ErroExecucaoBiblioteca
    {
        int indice = obterProximoIndiceLivre();

        objetos.add(indice, objeto);

        return indice;
    }
}
