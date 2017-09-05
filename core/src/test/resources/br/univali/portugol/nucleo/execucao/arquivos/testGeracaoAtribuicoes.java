package programas;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.Programa;

public class testGeracaoAtribuicoes extends Programa
{
    private boolean notas[][];
    
    private int cores[];

    private final int vetor[] = new int[]{1, 2};
    private final int matriz[][] = new int[][]{{1}, {2}};
    
    public testGeracaoAtribuicoes() throws ErroExecucao, InterruptedException
    {
    }
    
     @Override
    protected void inicializar () throws ErroExecucao, InterruptedException {
        notas = new boolean[][]{{true, false},{false, false},{true, false}};
        cores = new int[]{-14364777,-4046236,-4867209,-15193197};
    }

    @Override
    protected void executar(String[] parametros) throws ErroExecucao, InterruptedException
    {
        int x = (10 / 2);
        int a[] = new int[]{1, 2, 3};
        int u[] = new int[3];
        int v[][] = new int[2][2];
        int b[][] =new int[][]{{1, 2, 3}, {4, 5, 6}};
        int c = a[0];
        int d = b[0][1];
        int f = x;
        b[0][0] = 1;
        u[1] = 1;
    }
}
