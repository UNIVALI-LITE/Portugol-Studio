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
    private boolean valoresForamInicializados = false;

    public ItemDaListaParaVetor(int colunas, NoDeclaracaoVetor no) {
        super(no);
        setNumeroDeColunas(colunas);
    }

    public ItemDaListaParaVetor(NoDeclaracaoParametro declaracaoParametro) 
    {
        super(declaracaoParametro);
        valores = new Object[0]; 
    }  //cria um array vazio para que os métodos não gerem uma NUllPointerException ao acessaverem um atributo que seria nulo caso não fosse inicializado

    void setNumeroDeColunas(int colunas) {
        if (colunas < 0) {
            throw new IllegalArgumentException("quantidade inválida de colunas: " + colunas);
        }
        if (valores == null || colunas != valores.length)
        {
            valores = new Object[colunas];
        }
    }

    int getColunas() {
        return valores.length;
    }

    protected Object get(int coluna) {
        if (coluna >= 0 && coluna < valores.length) {
            return valores[coluna];
        }
        return null;
    }

    protected void set(Object valor, int coluna) {
        boolean colunaValida = coluna >= 0 && coluna < valores.length;
        if (colunaValida && valores[coluna] != valor && valor != Programa.OBJETO_NULO) {
            valores[coluna] = valor;
            if (valoresForamInicializados)
            {
                ultimaColunaAtualizada = coluna;
            }
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
        if (valoresForamInicializados) // atualiza apenas o último elemento alterado no vetor
        {
            Object valor = programa.getValorNoVetorInspecionado(ID); // último valor modificado
            int coluna = programa.getUltimaColunaAlteradaNoVetor(ID);
            set(valor, coluna);
        }
        else // elementos da view ainda não foram inicializados, é necessário coletar todos os dados do vetor para exibí-los na view
        {
            int tamanhoVetor = programa.getTamanhoVetor(ID);
            setNumeroDeColunas(tamanhoVetor); //inicializa a view se necessário
            
            boolean valoresSaoValidos = false;
            for (int coluna = 0; coluna < tamanhoVetor; coluna++) {
                Object valor = programa.getValorNoVetorInspecionado(ID, coluna); 
                set(valor, coluna);
                
                /** Desconsidera as atualizações se só houverem objetos nulos no vetor. 
                    Isso é necessário porque em uma execução passo a
                    passo o Inspetor pode solicitar esses valores antes que a
                    execução do programa tenha passado pela linha que inicialia o
                    vetor. Nesse caso todos os valores são nulos, e
                    não faria sentido considerar que o vetor já está
                    inicializado. É necessário esperar até que o vetor tenha sido preenchido
                    com valores válidos para só então considerá-lo como inicializado.
                 */
                valoresSaoValidos |= valor != Programa.OBJETO_NULO; 
            }
            
            if (valoresSaoValidos)
            {
                valoresForamInicializados = true;
            }
        }
    }

}
