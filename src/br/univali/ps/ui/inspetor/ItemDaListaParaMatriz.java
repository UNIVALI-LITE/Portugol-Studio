/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;

/**
 *
 * @author elieser
 */
class ItemDaListaParaMatriz extends ItemDaLista {
    
    private static final RenderizadorDeMatriz RENDERER = new RenderizadorDeMatriz();
    
    private Object[][] valores;
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
            InspetorDeSimbolos.ultimoItemModificado = this;
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
        RENDERER.setItemDaLista(this);
        return RENDERER;
    }
    
}
