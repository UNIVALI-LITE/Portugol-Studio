/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.avisos;

import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.simbolos.Simbolo;

/**
 *
 * @author Adson Esteves
 */
public class AvisoMatrizPodeSerVetor extends AvisoAnalise {

    private NoDeclaracao declaracao;
    private NoDeclaracaoParametro noDeclaracaoParametro;
    private int tamanhoX;
    private int tamanhoY;
    private int tamanho;
    
    public AvisoMatrizPodeSerVetor(NoDeclaracao declaracao, int tamanho, int tamanhoX, int tamanhoY)
    {
        super(declaracao.getTrechoCodigoFonteNome());
        
        this.declaracao = declaracao;
        this.tamanho = tamanho;
        this.tamanhoX = tamanhoX;
        this.tamanhoY = tamanhoY;
        this.getMensagem();
    }

    @Override
    protected String construirMensagem() {
        return "A matriz "+declaracao.getNome()+" tem tamanho ["+tamanhoX+"]["+tamanhoY+"] e pode ser substituida por um vetor de tamanho ["+tamanho+"]";
    }
    
}
