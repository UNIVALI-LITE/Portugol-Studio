/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 *
 * @author Alisson
 */
public class ErroInteiroForaDoIntervalo extends ErroSintatico
{
    private final String numero;

    public ErroInteiroForaDoIntervalo(int linha, int coluna, String numero)
    {
        super(linha, coluna, "ErroSintatico.ErroInteiroForaDoIntervalo");
        this.numero = numero;
    }

    @Override
    protected String construirMensagem()
    {
        return "O número " + numero +" está fora do intervalo de 32 bits. Digite um número entre: "+ Integer.MIN_VALUE + " e "+ Integer.MAX_VALUE;
    }
    
}
