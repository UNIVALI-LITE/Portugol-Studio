package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 * Erro gerado pelo interpretaor do Portugol quando uma vetor é acessado em
 * uma posição inválida.
 * <p>
 * Um índice/posição de vetor é inválido quando seu valor é negativo ou 
 * maior/igual ao tamanho do vetor.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploIndiceInvalido()
 *      {
 *           inteiro vetor[3] = {1, 2, 3}
 *  
 *           escreva(vetor[-1])   // Erro
 *           escreva(vetor[0])    // OK
 *           escreva(vetor[1])    // OK
 *           escreva(vetor[2])    // OK
 *           escreva(vetor[3])    // Erro
 *           escreva(vetor[4])    // Erro
 *      }
 * 
 * </pre></code>
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public final class ErroIndiceVetorInvalido extends ErroExecucao
{           
    private int tamanhoVetor;
    private int indiceAcessado;
    private String nomeVetor;
    
    /**
     * 
     * @param tamanhoVetor       o tamanho do vetor acessado.
     * @param indiceAcessado     o indice do vetor que o programa tentou acessar.
     * @param nomeVetor          o nome do vetor acessado.
     * 
     * @since 1.0
     */    
    public ErroIndiceVetorInvalido(int tamanhoVetor, int indiceAcessado, String nomeVetor) 
    {
        this.tamanhoVetor = tamanhoVetor;
        this.indiceAcessado = indiceAcessado;
        this.nomeVetor = nomeVetor;                
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem() 
    {
        StringBuilder construtorTexto = new StringBuilder();
        
        construtorTexto.append("O índice [");
        construtorTexto.append(indiceAcessado);
        construtorTexto.append("] é inválido para o vetor \"");
        construtorTexto.append(nomeVetor);
        construtorTexto.append("\". O vetor ");
        
        if (tamanhoVetor == 0)
            construtorTexto.append("não possui elementos.");
        
        else
        {
            construtorTexto.append("possui ");
            construtorTexto.append(tamanhoVetor);
            
            if (tamanhoVetor == 1)
            construtorTexto.append(" elemento.");
        
            else construtorTexto.append(" elementos.");
        }
        
        return construtorTexto.toString();
    }
}
