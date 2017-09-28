package br.univali.portugol.nucleo.bibliotecas.sons;

import br.univali.portugol.nucleo.bibliotecas.Sons;
import java.lang.reflect.Method;
import org.junit.Test;

/**
 * @author elieser Esta classe não é um teste unitário padrão. É um teste
 * 'auditivo'. Se o teste funcionar você ouvirá sons de bateria tocando na mais
 * perfeita e sublime sincronia musical. Se a sincronia não estiver perfeita ou
 * sublime o teste falhou :)
     */
public class TesteSons
{
    private void testeMultiplosSons() throws Exception
    {
        Sons sons = new Sons();

        for (int i = 0; i < 32; i++)
        {
            System.out.println("Reproduzindo som " + i);
            Integer somDaIgnicao = sons.carregar_som("../ide/src/main/assets/exemplos/jogos/corrida/sons/som_ligar.mp3");
            Integer indiceDaReproducao = sons.reproduzir_som(somDaIgnicao, false);
            if (indiceDaReproducao == null){
                System.out.println("Índice da reprodução era nulo!");
                System.exit(-1);
            }
            Thread.sleep(5);
        }
        Thread.sleep(10000);
    }
    
    private void testeSons() throws Exception
    {
        final Sons sons = new Sons();
        final Integer somDeFundo = sons.carregar_som("../ide/src/main/assets/exemplos/jogos/corrida/sons/musica_jogo.mp3");
        final Integer somDoSino = sons.carregar_som("../ide/src/main/assets/exemplos/jogos/corrida/sons/som_freio.mp3");
        final Integer somDoTrafego = sons.carregar_som("../ide/src/main/assets/exemplos/jogos/corrida/sons/som_trafego.mp3");
        
        sons.reproduzir_som(somDoTrafego, true);
        Integer reproducaoSino = sons.reproduzir_som(somDoSino, true);//testa a repetição do som
        Integer reproducaoSomDeFundo = sons.reproduzir_som(somDeFundo, true);
        
        sons.definir_volume_reproducao(reproducaoSomDeFundo, 80);
        sons.definir_volume(90);
        
        int volume = 100;
        for (int i = 0; i < 10; i++)
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
    
    private void testeSons2() throws Exception
    {
        Sons sons = new Sons();
        Integer bumbo = sons.carregar_som("../ide/src/main/assets/exemplos/musica/bateria/sons/bumbo.mp3");
        Integer hihat = sons.carregar_som("../ide/src/main/assets/exemplos/musica/bateria/sons/chimbal.mp3");
        Integer caixa = sons.carregar_som("../ide/src/main/assets/exemplos/musica/bateria/sons/caixa.mp3");

        Runtime runtime = Runtime.getRuntime();
        int iteracoes = 4;
        long totalMemoriaUsada = 0;
        for (int compasso = 1; compasso < iteracoes; compasso++)
        {
            for (int tempo = 1; tempo <= 8; tempo++) // 4 tempos em um compasso
            {
                sons.reproduzir_som(hihat, false);
                if (tempo == 1 || (tempo == 4 && compasso % 2 == 0)) //no tempo forte do compasso toca o bumbo
                    sons.reproduzir_som(bumbo, false);
                else if (tempo == 5 || (tempo == 8 && compasso % 2 == 0)) // toca a caixa no tempo 3 do compasso			
                    sons.reproduzir_som(caixa, false);
                
                Thread.sleep(150);
            }
            long memoriaUsada = runtime.totalMemory() - runtime.freeMemory();
            totalMemoriaUsada += memoriaUsada;
            System.out.println("Memória: " + (memoriaUsada/1024/1024) + " MB");
        }
        System.out.println("Média memória usada: " + ((totalMemoriaUsada/1024/1024)/iteracoes));
        sons.finalizar();
        
    }
    
    private void testeSons3() throws Exception
    {
        Sons sons = new Sons();

        Integer somDaIgnicao = sons.carregar_som("../ide/src/main/assets/exemplos/jogos/corrida/sons/som_ligar.mp3");
        for (int i = 0; i < 2; i++)
        {
            sons.reproduzir_som(somDaIgnicao, false);
            Thread.sleep(2500);
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        TesteSons app = new TesteSons();
        app.testeSons();
        app.testeSons2();
        app.testeSons3();
        app.testeMultiplosSons();
    }
}
