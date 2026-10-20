package br.univali.portugol.nucleo.execucao.erros.mensagens;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 * Erro gerado quando um vetor ou matriz é declarado em tempo de execução
 * com um tamanho inválido (zero, negativo ou acima do máximo permitido).
 * <p>
 * Exemplo:
 * <code><pre>
 *
 *      funcao inicio()
 *      {
 *           inteiro n
 *           leia(n)             // usuário digita 0 ou -5
 *           inteiro vetor[n]    // Erro: tamanho inválido
 *      }
 *
 * </pre></code>
 *
 * @since 2.8
 */
public final class ErroTamanhoVetorMatrizInvalido extends ErroExecucao
{
	private static final long serialVersionUID = 1L;
	private String codigo = "ErroExecucao.ErroTamanhoVetorMatrizInvalido";

    public ErroTamanhoVetorMatrizInvalido()
    {
    	super.setCodigo(codigo);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Você tentou declarar um vetor ou matriz com um tamanho inválido.\n\n");
        stringBuilder.append("O tamanho precisa ser um número inteiro positivo (maior que zero).\n");
        stringBuilder.append("Verifique o valor digitado ou o valor da variável usada como tamanho.\n\n");

        stringBuilder.append("Exemplo válido com tamanho dinâmico:\n\n");

		stringBuilder.append("programa\n");
		stringBuilder.append("{\n");

		stringBuilder.append("    funcao inicio()\n");
		stringBuilder.append("    {\n");
		stringBuilder.append("        inteiro n\n");
		stringBuilder.append("        leia(n)\n");
		stringBuilder.append("        inteiro vetor[n]\n");
		stringBuilder.append("    }\n\n");

		stringBuilder.append("}\n");

        return stringBuilder.toString();
    }
}
