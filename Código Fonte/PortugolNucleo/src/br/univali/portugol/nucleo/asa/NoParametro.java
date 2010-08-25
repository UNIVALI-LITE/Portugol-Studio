package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.Quantificador;
import br.univali.portugol.nucleo.TipoDado;

public class NoParametro
{	
    public enum ModoAcesso { POR_VALOR, POR_REFERENCIA };

    private int indice;
    private String nome;
    private TipoDado tipoDado;
    private ModoAcesso modoAcesso;
    private Quantificador quantificador;

    private Token tokenNome;
    private Token tokenTipoDado;

    public NoParametro(String nome, TipoDado tipoDado, Quantificador quantificador, ModoAcesso modoAcesso)
    {
        this.nome = nome;
        this.tipoDado = tipoDado;
        this.quantificador = quantificador;
        this.modoAcesso = modoAcesso;
    }

    public int getIndice()
    {
        return indice;
    }
	
    public String getNome()
    {
        return nome;
    }

    public TipoDado getTipoDado()
    {
        return tipoDado;
    }

    public Quantificador getQuantificador()
    {
        return quantificador;
    }

    public ModoAcesso getModoAcesso()
    {
        return modoAcesso;
    }

    public Token getTokenNome()
    {
        return tokenNome;
    }

    public Token getTokenTipoDado()
    {
        return tokenTipoDado;
    }

    public void setIndice(int indice)
    {
        this.indice = indice;
    }

    public void setTokenNome(Token token)
    {
        this.tokenNome = token;
    }

    public void setTokenTipoDado(Token token)
    {
        this.tokenTipoDado = token;
    }
}
