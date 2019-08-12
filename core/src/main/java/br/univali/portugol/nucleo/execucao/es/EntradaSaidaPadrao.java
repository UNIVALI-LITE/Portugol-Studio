package br.univali.portugol.nucleo.execucao.es;

import br.univali.portugol.nucleo.asa.TipoDado;
import java.util.Scanner;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class EntradaSaidaPadrao implements Entrada, Saida
{
    public EntradaSaidaPadrao()
    {

    }

    @Override
    public void solicitaEntrada(TipoDado tipoDado, Armazenador armazenador) throws InterruptedException
    {
        Scanner in = new Scanner(System.in);

        switch (tipoDado)
        {
            case CADEIA:
                armazenador.setValor(in.next());
                break;
            case CARACTER:
                armazenador.setValor(in.next().charAt(0));
                break;
            case INTEIRO:
                armazenador.setValor(in.nextInt());
                break;
            case LOGICO:
                String s = in.next();

                armazenador.setValor(s.equals("verdadeiro") ? true : s.equals("falso") ? false : null);
                break;
            case REAL:
                armazenador.setValor(in.nextDouble());
            default:
                break;
        }

        armazenador.setValor(null);
        in.close();
    }

    
    
    @Override
    public void limpar() throws InterruptedException
    {
        for (int i = 0; i < 50; i++)
        {
            System.out.println("");
        }
    }

    @Override
    public void escrever(String valor) throws InterruptedException
    {
        System.out.print(valor);
    }

    @Override
    public void escrever(boolean valor) throws InterruptedException
    {
        System.out.print(valor ? "verdadeiro" : "falso");
    }

    @Override
    public void escrever(int valor) throws InterruptedException
    {
        System.out.print(valor);
    }

    @Override
    public void escrever(double valor) throws InterruptedException
    {
        System.out.print(valor);
    }

    @Override
    public void escrever(char valor) throws InterruptedException
    {
        System.out.print(valor);
    }
}
