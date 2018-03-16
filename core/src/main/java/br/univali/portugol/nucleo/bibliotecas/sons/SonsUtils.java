package br.univali.portugol.nucleo.bibliotecas.sons;

/**
 * @author Elieser
 */
public class SonsUtils
{

    public static float linearParaExponencial(float volumeLinear)
    {
        return (float)(Math.pow(volumeLinear, 3));
    }

    public static float poweredGainToLinear(float volumeExponencial)
    {
        return (float)(Math.pow(volumeExponencial, 1.0/3.0));
    }
    
    public static double decibelParaLinear(float decibel)
    {
        // db-to-linear(x) = 10^(x / 20)
        return Math.pow(10, decibel / 20);
    }

    public static float linearParaDecibel(float valorLinear)
    {
        // linear-to-db(x) = log(x) * 20
        return (float)(Math.log10(valorLinear) * 20.0f);
    }
}
