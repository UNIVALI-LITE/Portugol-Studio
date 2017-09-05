/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Vetor;

/**
 *
 * @author LITE
 */
public class ErroTamanhoMaximoVetor extends ErroSemantico
{
    private final int tamanhoDeclarado;
    private final String nomeVetor;

    public ErroTamanhoMaximoVetor(int tamanhoDeclarado, String nomeVetor, TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
        this.tamanhoDeclarado = tamanhoDeclarado;
        this.nomeVetor = nomeVetor;
    }

    @Override
    protected String construirMensagem()
    {
        return "O vetor '"+nomeVetor+"' está sendo declarado com "+tamanhoDeclarado+" posições, porém o tamanho máximo de um vetor é "+Vetor.TAMANHO_MAXIMO+". Informe um tamanho entre 1 e "+Vetor.TAMANHO_MAXIMO+" para corrigir o problema";
    }
    
}
