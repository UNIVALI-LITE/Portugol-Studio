package br.univali.portugol.nucleo.bibliotecas.sons;

import br.univali.portugol.nucleo.bibliotecas.Sons;

/**
 * @author elieser
 * Esta classe não é um teste unitário padrão. É um teste 'auditivo'. Se o teste funcionar
 * você ouvirá o som da ignição de um carro repetidamente. O teste consiste em executar os novos
 * sons sem que os anteriores sejam interrompidos.
 */
public class TesteSons3
{
    public static void main(String args[]) throws Exception
    {
        Sons sons = new Sons();

        Integer somDaIgnicao = sons.carregar_som("../Portugol-Studio-Recursos/exemplos/jogos/corrida/sons/som_ligar.mp3");
        for (int i = 0; i < 5; i++)
        {
            sons.reproduzir_som(somDaIgnicao, false);
            Thread.sleep(2500);
        }

    }
}
    

