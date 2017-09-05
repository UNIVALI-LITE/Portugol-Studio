package programas;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.Programa;

public class testSwitchAninhadoUsandoVariaveis extends Programa
{
    public testSwitchAninhadoUsandoVariaveis() throws ErroExecucao, InterruptedException
    {
    }

    @Override
    protected void executar(String[] parametros) throws ErroExecucao, InterruptedException
    {
        int caso1 = 1;
        int caso42 = 42;
        int x = 1;
        int y = 42;
        {
            boolean ___sw_break___1 = false;
            if (!___sw_break___1 && x == caso1)
            {
                {
                    boolean ___sw_break___2 = false;
                    if (!___sw_break___2 && y == caso42)
                    {
                        escreva("42 ");

                    }

                }
                escreva("1 ");

            }
        }
    }
}
