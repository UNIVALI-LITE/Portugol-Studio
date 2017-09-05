/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elieser
 */
class ItemDaListaParaMatriz extends ItemDaLista {
    
    private static final RenderizadorDeMatriz RENDERER = new RenderizadorDeMatriz();
    
    private Object[][] valores;
    private int ultimaLinhaAtualizada = -1;
    private int ultimaColunaAtualizada = -1;
    private boolean valoresForamInicializados = false;
    private final List<ItemDaListaParaMatrizListener> listeners;
    private String stringDimensoes = "[ ? ][ ? ]";

    public ItemDaListaParaMatriz(int linhas, int colunas, NoDeclaracaoMatriz no) {
        super(no);
        this.listeners = new ArrayList<>();
        inicializaDimensoes(linhas, colunas);
    }
    
    public void addListener(ItemDaListaParaMatrizListener listener)
    {
        if (!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }

    @Override
    public void atualiza(Programa programa) {
        int idInspecao = getIdParaInspecao();
        if (valoresForamInicializados) // atualiza apenas o último valor alterado na matriz
        {
            Object valor = programa.getValorNaMatrizInspecionada(idInspecao); // último valor modificado
            int coluna = programa.getUltimaColunaAlteradaNaMatriz(idInspecao);
            int linha = programa.getUltimaLinhaAlteradaNaMatriz(idInspecao);
            set(valor, linha, coluna);
        }
        else // atualiza todos os valores da matriz, isso acontece uma vez só quando a execução passa pela declaração da matriz
        {
            int linhas = programa.getLinhasDaMatriz(idInspecao);
            int colunas = programa.getColunasDaMatriz(idInspecao);
            
            inicializaDimensoes(linhas, linhas); //se necessário
            
            boolean valoresSaoValidos = false;
            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    Object valor = programa.getValorNaMatrizInspecionada(idInspecao, i, j);
                    set(valor, i, j);
                    
                    /** Desconsidera as atualizações se só houverem objetos nulos na matriz. 
                        Isso é necessário porque em uma execução passo a passo o Inspetor pode solicitar 
                        esses valores antes que a execução do programa tenha passado pela linha que inicialia
                        a matriz. Nesse caso todos os valores da matriz são nulos, e não faria sentido considerar que a matriz
                        já está inicializada nesse caso. É necessário esperar até que a matriz tenha sido preenchida com valores
                        válidos para só então considerá-la como inicializada.
                    */
                    valoresSaoValidos |= valor != Programa.OBJETO_NULO; 
                }
            }
            if (valoresSaoValidos)
            {
                valoresForamInicializados = true;
            }
        }
    }

    public ItemDaListaParaMatriz(NoDeclaracaoParametro declaracaoParametro) {
        super(declaracaoParametro);
        valores = new Object[0][0];
        listeners = new ArrayList<>();
    }

    public boolean dimensoesForamInicializadas() {
        return valores != null && valores.length > 0 && valores[0].length > 0;
    }

    public final void inicializaDimensoes(int linhas, int colunas) {
        if (linhas < 0) {
            throw new IllegalArgumentException("quantidade inválida de linhas: " + linhas);
        }
        if (colunas < 0) {
            throw new IllegalArgumentException("quantidade inválida de colunas: " + colunas);
        }
        if (valores == null || (valores != null && valores.length != linhas))
        {
            valores = new Object[linhas][colunas];
            limpa();
            
            for (ItemDaListaParaMatrizListener listener : listeners) {
                listener.matrizRedimensionada();
            }
        }
        atualizaStringDimensoes();
    }

    public int getUltimaColunaAtualizada() {
        return ultimaColunaAtualizada;
    }

    public int getUltimaLinhaAtualizada() {
        return ultimaLinhaAtualizada;
    }

    int getLinhas() {
        if (valores != null)
        {
            return valores.length;
        }
        
        return 0;
    }

    int getColunas() {
        if (valores != null && valores.length > 0) {
            return valores[0].length;
        }
        return 0;
    }

    protected Object get(int linha, int coluna) {
        if (linha >= 0 && linha < valores.length && coluna >= 0 && coluna < valores[0].length) {
            return valores[linha][coluna];
        }
        return null;
    }

    protected void set(Object valor, int linha, int coluna) {
        if (valor == Programa.OBJETO_NULO || valores == null)
        {
            return;
        }
        
        boolean linhaValida = linha >= 0 && linha < valores.length;
        boolean colunaValida = coluna >= 0 && valores.length > 0 && coluna < valores[0].length;
        if (linhaValida && colunaValida) {
            if(valores[linha][coluna] != valor)
            {
                valores[linha][coluna] = valor;
                if (valoresForamInicializados)
                {
                    ultimaColunaAtualizada = coluna;
                    ultimaLinhaAtualizada = linha;
                }
                InspetorDeSimbolos.ultimoItemModificado = this;
            }
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
        
        valoresForamInicializados = false;
    }

    public String getStringDimensoes() 
    {
        return stringDimensoes;
    }

    private void atualizaStringDimensoes() 
    {
        if (dimensoesForamInicializadas())
        {
            String linhas = String.valueOf(getLinhas());
            String colunas = String.valueOf(getColunas());
            stringDimensoes = " [ " + linhas + " ][ " + colunas + " ]";
        }
    }

    @Override
    RenderizadorBase getRendererComponent() 
    {
        RENDERER.setItemDaLista(this);
        return RENDERER;
    }
    
}
