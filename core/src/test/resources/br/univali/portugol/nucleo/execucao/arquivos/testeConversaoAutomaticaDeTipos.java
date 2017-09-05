package programas;

import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.bibliotecas.Graficos;

public class testeConversaoAutomaticaDeTipos extends Programa
{
    private final Graficos Graficos = new Graficos();
    private double a;
    private int b;

    public testeConversaoAutomaticaDeTipos() throws ErroExecucao, InterruptedException
    {
    }

    @Override
    protected void inicializar () throws ErroExecucao, InterruptedException {
        a = 0.0;
    }
    
    @Override
    protected void executar(String[] parametros) throws ErroExecucao, InterruptedException
    {
        b = (int) a;
        a = b;
        b = trunca(a);
        a = trunca(b);
        b = (int) testa((int) a);
        a = testa(b);
        b = (int) ((a + 5.0) * a);
        testa((int) ((a + 5.0) * a));
        Graficos.desenhar_imagem((int) ((a + 5.0) * a), 1, 0);
        
    }

    private int trunca(double x) throws ErroExecucao, InterruptedException
    {
        return (int) x;
    }

    private double testa(int x) throws ErroExecucao, InterruptedException
    {
        return x;
    }
}
