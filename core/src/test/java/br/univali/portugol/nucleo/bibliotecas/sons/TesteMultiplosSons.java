package br.univali.portugol.nucleo.bibliotecas.sons;

import br.univali.portugol.nucleo.bibliotecas.Sons;

/**
 * @author elieser Esta classe testa se é possível criar vários sons simultâneos
 * sem que ocorra uma LineUnavailableException.
 */
public class TesteMultiplosSons
{
    public static void main(String args[]) throws Exception
    {
        Sons sons = new Sons();

        for (int i = 0; i < 32; i++)
        {
            System.out.println("Reproduzindo som " + i);
            Integer somDaIgnicao = sons.carregar_som("../Portugol-Studio-Recursos/exemplos/jogos/corrida/sons/som_ligar.mp3");
            Integer indiceDaReproducao = sons.reproduzir_som(somDaIgnicao, false);
            if (indiceDaReproducao == null){
                System.out.println("Índice da reprodução era nulo!");
                System.exit(-1);
            }
            Thread.sleep(5);
        }
        Thread.sleep(10000);
    }
}
