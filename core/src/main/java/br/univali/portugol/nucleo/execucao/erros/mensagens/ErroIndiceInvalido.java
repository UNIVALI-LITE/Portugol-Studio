package br.univali.portugol.nucleo.execucao.erros.mensagens;

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
public final class ErroIndiceInvalido extends ErroExecucao
{           
	private static final long serialVersionUID = 1L;
	
    public ErroIndiceInvalido() 
    {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem() 
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("Você tentou acessar um índice de vetor ou matriz inválido.\n\n");
        stringBuilder.append("No caso dos vetores, o índice deve ser menor que o número de elementos que o vetor possui.\n");        
        stringBuilder.append("Por exemplo, se foi declarado um vetor com 5 elementos (inteiro vetor[5]), o maior índice possível é 4.\n\n");
                
        stringBuilder.append("No caso das matrizes, o índice utilizado para acessar a linha da matriz deve ser menor que a quantidade de linhas que ela possui.\n");
        stringBuilder.append("Da mesma forma, o índice utilzado para acessar a coluna da matriz deve ser menor que a quantidade de colunas que ela possui.\n\n");
        
        stringBuilder.append("Por exemplo, se foi declarada uma matriz com 3 linhas e 8 colunas (inteiro matriz[3][8]), ");
        stringBuilder.append("o maior índice possível para a linha é 2 e o maior índice possível para a coluna é 7.\n\n");
        
        stringBuilder.append("Além disso, o índice de um vetor ou matriz não pode ser negativo. Portanto, todos os comandos abaixo são inválidos e geram erro na execução do programa:\n\n");
        
		stringBuilder.append("programa\n");
		stringBuilder.append("{\n");
		
		stringBuilder.append("    funcao inicio()\n");
		stringBuilder.append("    {\n");
		stringBuilder.append("        inteiro vetor[6]\n");
		stringBuilder.append("        inteiro matriz[5][4]\n\n");
		
		stringBuilder.append("        escreva(vetor[-1])\n");
		stringBuilder.append("        escreva(matriz[-1][2])\n");
		stringBuilder.append("        escreva(matriz[3][-1])\n");
		stringBuilder.append("        escreva(matriz[-1][-1])\n");
		stringBuilder.append("    }\n\n");
		
		stringBuilder.append("}\n");
        
        return stringBuilder.toString();
    }
}
