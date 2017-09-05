package br.univali.portugol.nucleo.bibliotecas.sons;

import br.univali.portugol.nucleo.bibliotecas.Sons;

/**
 * @author elieser Esta classe não é um teste unitário padrão. É um teste
 * 'auditivo'. Se o teste funcionar você ouvirá sons de bateria tocando na mais
 * perfeita e sublime sincronia musical. Se a sincronia não estiver perfeita ou
 * sublime o teste falhou :)
     */
public class TesteSons2
{
    public static void main(String args[]) throws Exception
    {
        Sons sons = new Sons();
        Integer bumbo = sons.carregar_som("../Portugol-Studio-Recursos/exemplos/musica/bateria/sons/bumbo.mp3");
        Integer hihat = sons.carregar_som("../Portugol-Studio-Recursos/exemplos/musica/bateria/sons/chimbal.mp3");
        Integer caixa = sons.carregar_som("../Portugol-Studio-Recursos/exemplos/musica/bateria/sons/caixa.mp3");

        Runtime runtime = Runtime.getRuntime();
        int iteracoes = 16;
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
}
