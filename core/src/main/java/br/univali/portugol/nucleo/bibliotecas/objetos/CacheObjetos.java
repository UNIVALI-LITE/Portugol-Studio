/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas.objetos;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Gabriel Schade
 */
public class CacheObjetos {

    private final HashMap<Integer, Objeto> objetos;
    private final List<Integer> idsLivres;
    private final int LIMITE = Integer.MAX_VALUE;

    private CacheObjetos()
    {
        objetos = new HashMap<>();
        idsLivres = new ArrayList<>();
    }

    public static CacheObjetos criar()
    {
        return new CacheObjetos();
    }

    private int obterProximoIndiceLivre() throws ErroExecucaoBiblioteca
    {
        if (objetos.size() - idsLivres.size() >= LIMITE) {
            return lancarExcecaoLimiteObjetos();
        } else {
            return idsLivres.isEmpty() ? objetos.size() : idsLivres.remove(idsLivres.size() - 1);
        }
    }

    private int lancarExcecaoLimiteObjetos() throws ErroExecucaoBiblioteca
    {
        throw new ErroExecucaoBiblioteca("O número máximo de objetos que podem ser criados foi atingido");
    }
   
    public Objeto obterObjeto(int endereco) throws ErroExecucaoBiblioteca
    {
        return objetos.containsKey(endereco) ?
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
        if (objetos.containsKey(endereco)) {
            objetos.remove(endereco);
            idsLivres.add(endereco);
        }
        else
            lancarExcecaoEnderecoNaoApontaParaUmObjeto();
    }

    public int criarObjeto(Objeto objeto) throws ErroExecucaoBiblioteca
    {
        int indice = obterProximoIndiceLivre();
        objetos.put(indice, objeto);
        return indice;
    }
}
