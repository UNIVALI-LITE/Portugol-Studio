package br.univali.portugol.nucleo.bibliotecas.graficos.operacoes;

import br.univali.portugol.nucleo.bibliotecas.graficos.operacoes.cache.CacheOperacoesGraficas;
import java.awt.Graphics2D;

/**
 *
 * @author Luiz Fernando
 */
public abstract class OperacaoGrafica
{
    private final CacheOperacoesGraficas cache;
    
    public OperacaoGrafica(CacheOperacoesGraficas cache)
    {
        this.cache = cache;
    }
    
    /***
     * Devolve a opearação para a cache
     */
    public final void liberar()
    {
        cache.devolver(this);
    }
    
    /***
     * Libera os recursos mais pesados para evitar retenção de memória, mas mantém a operação na cache.
     */
    public void liberarRecursos(){
        // Usando uma implementação vazia para evitar que todas as subclasses sejam obrigadas a implementar métodos vazios apenas para cumprir 'o contrato'
    }
    
    public void executar(Graphics2D graficos)
    {
        desenhar(graficos);
    }
    
    protected abstract void desenhar(Graphics2D graficos);
 
   
}