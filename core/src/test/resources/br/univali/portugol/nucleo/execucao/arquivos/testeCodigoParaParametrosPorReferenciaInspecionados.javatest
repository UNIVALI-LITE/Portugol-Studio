package programas;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.Programa;

public class testeCodigoParaParametrosPorReferenciaInspecionados extends Programa
{
    private final int[] REFS_INT = new int[1];

    private final int INDICE_X_0 = 0;

    public testeCodigoParaParametrosPorReferenciaInspecionados() throws ErroExecucao, InterruptedException
    {
        variaveisInspecionadas = new Object[2];
        vetoresInspecionados = new Vetor[0];
        matrizesInspecionadas = new Matriz[0];
    }

    @Override
    protected void executar(String[] parametros) throws ErroExecucao, InterruptedException
    {
        REFS_INT[INDICE_X_0] = 1;
        if (variaveisInspecionadas[0] != null) {
            variaveisInspecionadas[0] = REFS_INT[INDICE_X_0];
        }
        
        teste(INDICE_X_0);

    }

    private void teste(int a) throws ErroExecucao, InterruptedException
    {
        if (variaveisInspecionadas[1] != null) {
            variaveisInspecionadas[1] = REFS_INT[a];
        }
        
        REFS_INT[a] = 10;
        
        if (variaveisInspecionadas[1] != null) {
            variaveisInspecionadas[1] = REFS_INT[a];
        }
    }
}
