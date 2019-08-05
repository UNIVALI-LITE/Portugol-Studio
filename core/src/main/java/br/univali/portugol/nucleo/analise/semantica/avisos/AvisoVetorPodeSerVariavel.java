/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.avisos;

import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;

/**
 *
 * @author Adson Esteves
 */
public class AvisoVetorPodeSerVariavel extends AvisoAnalise{

    private NoDeclaracaoBase declaracao;
    private int tamanho;
    private String codigo = "AvisoSemantico.AvisoVetorPodeSerVariavel";
    
    public AvisoVetorPodeSerVariavel(NoDeclaracaoBase declaracao, int tamanho)
    {
        super(declaracao.getTrechoCodigoFonteNome());
        
        this.declaracao = declaracao;
        this.tamanho = tamanho;
        this.getMensagem();
        super.setCodigo(codigo);
    }

    @Override
    protected String construirMensagem() {
        return "O Vetor "+declaracao.getNome()+" tem tamanho ["+tamanho+"] e pode ser substituido por uma variável";
    }
    
}
