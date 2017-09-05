package programas;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.Programa;

public class testSwitchComCasoContrario extends Programa
{

    public testSwitchComCasoContrario() throws ErroExecucao, InterruptedException
    {
        variaveisInspecionadas = new Object[2];
        vetoresInspecionados = new Vetor[0];
        matrizesInspecionadas = new Matriz[0];
    }

    @Override
    protected void executar(String[] parametros) throws ErroExecucao, InterruptedException
    {
        if (this.interrupcaoSolicitada || Thread.currentThread().isInterrupted())
        {
            throw new InterruptedException();
        }

        realizarParada(5, 31);
        int BOLETO = 1;
        if (variaveisInspecionadas[0] != null)
        {
            variaveisInspecionadas[0] = BOLETO;
        }
        realizarParada(6, 24);
        int opcao = 4;
        if (variaveisInspecionadas[1] != null)
        {
            variaveisInspecionadas[1] = opcao;
        }
        realizarParada(8, 16);
        realizarParada(8, 16);
        {
            boolean ___sw_break___1 = false;
            realizarParada(9, 17);
            if (!___sw_break___1 && opcao == BOLETO)
            {
                realizarParada(10, 16);
                escreva("boleto");
                realizarParada(11, 16);
                ___sw_break___1 = true;

            }

            if (!___sw_break___1 && true)
            {
                realizarParada(13, 16);
                escreva("contrario");

            }

        }

    }

}
