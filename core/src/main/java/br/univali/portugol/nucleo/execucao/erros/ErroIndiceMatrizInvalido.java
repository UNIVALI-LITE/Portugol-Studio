package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.simbolos.Matriz;

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
public final class ErroIndiceMatrizInvalido extends ErroExecucao
{           
    private int linhas;
    private int linhaAcessada;
    private String nomeMatriz;
    private final int colunas;
    private final int colunaAcessada;
    private String codigo = "ErroExecucao.ErroIndiceMatrizInvalido";
    
    /**
     * 
     * @param tamanhoVetor       o tamanho do vetor acessado.
     * @param indiceAcessado     o indice do vetor que o programa tentou acessar.
     * @param nomeVetor          o nome do vetor acessado.
     * 
     * @since 1.0
     */    
    public ErroIndiceMatrizInvalido(Matriz matriz,int linha, int coluna) 
    {
        this.linhas = matriz.getNumeroLinhas();
        this.colunas = matriz.getNumeroColunas();
        this.linhaAcessada = linha;
        this.colunaAcessada = coluna;
        this.nomeMatriz = matriz.getNome();   
        super.setCodigo(codigo);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem() 
    {
        StringBuilder construtorTexto = new StringBuilder();
        
        construtorTexto.append("A posição [");
        construtorTexto.append(linhaAcessada);
        construtorTexto.append("][");
        construtorTexto.append(colunaAcessada);
        construtorTexto.append("] é inválida para a matriz \"");
        construtorTexto.append(nomeMatriz);
        construtorTexto.append("\". A matriz ");
        
        if (linhas == 0 && colunas == 0)
            construtorTexto.append("não possui elementos.");
        
        else
        {
            construtorTexto.append("possui ");
            construtorTexto.append(linhas);
            
            if (linhas == 1)
                construtorTexto.append(" linha e ");        
            else 
                construtorTexto.append(" linhas e ");
            

            construtorTexto.append(colunas);
            
            if (colunas == 1)
                construtorTexto.append(" coluna.");        
            else 
                construtorTexto.append(" colunas.");
        }
        
        return construtorTexto.toString();
    }
}
