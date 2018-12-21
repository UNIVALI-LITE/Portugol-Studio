package br.univali.portugol.nucleo.execucao.erros.mensagens;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;

/**
 *
 * @author Fillipi Pelz
 * @author Luiz Fernando Noschang
 *
 */
public final class ErroEstouroPilha extends ErroExecucao
{
	private static final long serialVersionUID = 1L;
	private String codigo = "ErroExecucao.ErroEstouroPilha";

	public ErroEstouroPilha()
	{
		super.setCodigo(codigo);
	}

	@Override
	protected String construirMensagem()
	{		
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("Ocorreu um estouro de pilha de memória no programa.");
        stringBuilder.append("\n");
        stringBuilder.append("Isto significa que existe alguma função do programa que está sendo chamada de forma recursiva sem uma condição de parada.");
        stringBuilder.append("\n\n");
        stringBuilder.append("O código abaixo, por exemplo, gera um estouro de pilha, pois a função \"teste\" chama ela mesma para sempre:");
        stringBuilder.append("\n\n");               


		stringBuilder.append("programa\n");
		stringBuilder.append("{\n");
		
		stringBuilder.append("    inteiro contador = 0\n\n");
		
		stringBuilder.append("    funcao inicio()\n");
		stringBuilder.append("    {\n");
		stringBuilder.append("        teste()\n");
		stringBuilder.append("    }\n\n");
		
		stringBuilder.append("    funcao teste()\n");
		stringBuilder.append("    {\n");
		stringBuilder.append("        contador = contador + 1\n\n");
		
		stringBuilder.append("        escreva(\"Teste \" + contador + \"\\n\")\n\n");
		
		stringBuilder.append("        teste()\n");
		stringBuilder.append("    }\n");
		stringBuilder.append("}\n");
		
		stringBuilder.append("\nNeste exemplo, o problema pode ser resolvido criando uma condição que impeça a função \"teste\" de ser chamada para sempre: \n\n");
		
		stringBuilder.append("programa\n");
		stringBuilder.append("{\n");
		
		stringBuilder.append("    inteiro contador = 0\n\n");
		
		stringBuilder.append("    funcao inicio()\n");
		stringBuilder.append("    {\n");
		stringBuilder.append("        teste()\n");
		stringBuilder.append("    }\n\n");
		
		stringBuilder.append("    funcao teste()\n");
		stringBuilder.append("    {\n");
		stringBuilder.append("        contador = contador + 1\n\n");
		
		stringBuilder.append("        escreva(\"Teste \" + contador + \"\\n\")\n\n");
		stringBuilder.append("        se (contador < 100)\n");
		stringBuilder.append("        {\n");
		stringBuilder.append("            teste()\n");
		stringBuilder.append("        }\n");
		stringBuilder.append("    }\n");
		stringBuilder.append("}\n");
		

		return stringBuilder.toString();
	}
}
