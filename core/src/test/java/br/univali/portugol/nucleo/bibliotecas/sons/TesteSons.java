package br.univali.portugol.nucleo.bibliotecas.sons;

import br.univali.portugol.nucleo.bibliotecas.Sons;

/**
 * @author elieser
 * Esta classe não é um teste unitário padrão. É um teste 'auditivo'. Se o teste funcionar
 * você ouvirá sons, se não ouvir nada (e seu sistema de som estiver funcionando) então
 * o teste falhou. Você deverá ouvir um com de fundo e um som de sino tocando repetidamente, e
 * o volume do som do sino ficará aumentando e diminuindo.
 * Depois de alguns segundos o som de fundo é interrompido.
 */
public class TesteSons
{
    public static void main(String args[]) throws Exception
    {
        final Sons sons = new Sons();
        final Integer somDeFundo = sons.carregar_som("../Portugol-Studio-Recursos/exemplos/jogos/corrida/sons/musica_jogo.mp3");
        final Integer somDoSino = sons.carregar_som("../Portugol-Studio-Recursos/exemplos/jogos/corrida/sons/som_freio.mp3");
        final Integer somDoTrafego = sons.carregar_som("../Portugol-Studio-Recursos/exemplos/jogos/corrida/sons/som_trafego.mp3");
        
        sons.reproduzir_som(somDoTrafego, true);
        Integer reproducaoSino = sons.reproduzir_som(somDoSino, true);//testa a repetição do som
        Integer reproducaoSomDeFundo = sons.reproduzir_som(somDeFundo, true);
        
        sons.definir_volume_reproducao(reproducaoSomDeFundo, 80);
        sons.definir_volume(90);
        
        int volume = 100;
        for (int i = 0; i < 20; i++)
        {
            Thread.sleep(1000);
            sons.definir_volume_reproducao(reproducaoSino, volume);
            
            volume -= 3;
            if (volume <= 10)
                volume = 100;
            
            if (i == 5)
            {
                sons.interromper_som(reproducaoSomDeFundo);
            }
        }
    }
}
    

