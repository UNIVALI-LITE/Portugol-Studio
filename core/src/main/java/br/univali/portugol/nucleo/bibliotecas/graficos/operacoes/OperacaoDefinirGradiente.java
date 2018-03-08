package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.SuperficieDesenho;
import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Alisson
 */
public class OperacaoDefinirGradiente extends OperacaoGrafica{

    private SuperficieDesenho superficieDesenho;
    private int tipo;
    private int cor1;
    private int cor2;    
    
    public OperacaoDefinirGradiente(CacheOperacoesGraficas<OperacaoDefinirGradiente> cache)
    {
        super(cache);
    }
    
    void setParametros(SuperficieDesenho superficieDesenho, int tipo, int cor1, int cor2)
    {
        this.superficieDesenho = superficieDesenho;
        this.cor1 = cor1;
        this.cor2 = cor2;
        this.tipo = tipo;
    }
    @Override
    protected void desenhar(Graphics2D graficos) {
        superficieDesenho.registrarGradiente(tipo, cor1, cor2);
    }
    
}
