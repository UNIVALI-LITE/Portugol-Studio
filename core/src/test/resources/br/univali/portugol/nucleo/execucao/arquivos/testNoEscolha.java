package programas;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.Programa;

public class testNoEscolha extends Programa
{

    public testNoEscolha() throws ErroExecucao, InterruptedException
    {
    }

    @Override
    protected void executar(String[] parametros) throws ErroExecucao, InterruptedException
    {
        int opcao = 1;
        switch (opcao)
        {
            case 1:
                escreva("caso 1");
                break;
            case 2:
                escreva("caso 2");
                break;
            case 3:
                escreva("Tchau!\n");
                break;
            default:
                escreva("Opção Inválida!\n");
        }
    }
}
