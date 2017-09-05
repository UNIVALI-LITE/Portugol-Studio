package br.univali.portugol.ajuda;

/**
 *
 * @author Luiz Fernando Noschang
 */
public interface ObservadorCarregamentoAjuda
{
    public void carregamentoAjudaIniciado(int numeroTopicos);
    
    public void carregamentoTopicoIniciado(int indiceTopico);
    
    public void carregamentoTopicoFinalizado(int indiceTopico);
    
    public void carregamentoAjudaFinalizado();
}
