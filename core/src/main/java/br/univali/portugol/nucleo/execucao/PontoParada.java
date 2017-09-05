package br.univali.portugol.nucleo.execucao;

import br.univali.portugol.nucleo.asa.No;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class PontoParada
{
    private final No no;
    private boolean ativo = false;

    public PontoParada(No no)
    {
        this.no = no;
    }

    public No getNo()
    {
        return no;
    }
    
    public void setAtivo(boolean ativo){
        this.ativo = ativo;
    }
    
    public boolean estaAtivo(){
        return this.ativo;
    }
}
