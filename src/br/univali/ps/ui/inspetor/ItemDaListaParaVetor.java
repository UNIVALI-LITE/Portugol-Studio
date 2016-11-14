/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;

/**
 *
 * @author elieser
 */
class ItemDaListaParaVetor extends ItemDaLista {

    private static final RenderizadorDeVetor RENDERER = new RenderizadorDeVetor();

    private Object[] valores;
    private int ultimaColunaAtualizada = -1;
    //private int larguraDasColunas[];

    public ItemDaListaParaVetor(int colunas, NoDeclaracaoVetor no) {
        super(no);
        setNumeroDeColunas(colunas);
    }

    public ItemDaListaParaVetor(NoDeclaracaoParametro declaracaoParametro) {
        super(declaracaoParametro);
        valores = new Object[0]; //cria um array vazio para que os métodos não gerem uma NUllPointerException ao acessaverem um atributo que seria nulo caso não fosse inicializado
    } //cria um array vazio para que os métodos não gerem uma NUllPointerException ao acessaverem um atributo que seria nulo caso não fosse inicializado

    /**
     * *
     * @param colunas Este método é invocado no evento de simbolosAlterados
     * somente quando este ItemDaListaParaVetor armazena uma instancia de
     * NoDeclaracaoParametro. Quando este item está ligado com um
     * NoDeclaracaoVetor é possível obter o tamanho do vetor já na inicialização
     * do objeto, mas quando este item está ligado com um NoDeclaracaoParametro
     * é necessário aguardar até que a primeira atualização do parâmetro
     * aconteça para so então obter a quantidade de colunas do vetor.
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
            InspetorDeSimbolos.ultimoItemModificado = this;
        }
    }

    @Override
    public String getNome() {
        String nome = super.getNome();
        String colunas = numeroDeColunasFoiInicializado() ? String.valueOf(getColunas()) : " ? ";
        return nome + " [ " + colunas + " ]";
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
        RENDERER.setItemDaLista(this);
        return RENDERER;
    }

    //quando este item armazena um NoDeclaracaoParametro só é possível saber a quantidade de colunas
    //na primeira atualização dos valores do parâmetro, nesse caso o número de colunas é zero (não setado) até que o método
    //setNumeroDeColunas seja invocado durante a atualização dos símbolos
    boolean numeroDeColunasFoiInicializado() {
        return valores.length > 0;
    }

    @Override
    public void atualiza(Programa programa) {
        int ID = getIdParaInspecao();
        Object valor = programa.getValorNoVetorInspecionado(ID); // último valor modificado
        int coluna = programa.getUltimaColunaAlteradaNoVetor(ID);
        set(valor, coluna);
    }

}
