package programas;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.Programa;

public class testeLeiaComParametroPorReferencia extends Programa
{

    private final int[] REFS_INT = new int[1];

    private final int INDICE_X_0 = 0;

    public testeLeiaComParametroPorReferencia() throws ErroExecucao, InterruptedException
    {
    }

    @Override
    protected void executar(String[] parametros) throws ErroExecucao, InterruptedException
    {
        REFS_INT[INDICE_X_0] = 0;
        ler(INDICE_X_0);
        escreva("valor digitado: ", REFS_INT[INDICE_X_0]);

    }

    private int ler(int x) throws ErroExecucao, InterruptedException
    {
        REFS_INT[x] = leiaInteiro();
        return REFS_INT[x];

    }

}
